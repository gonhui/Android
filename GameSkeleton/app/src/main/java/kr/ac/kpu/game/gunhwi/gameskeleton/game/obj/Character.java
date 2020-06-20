package kr.ac.kpu.game.gunhwi.gameskeleton.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.gunhwi.gameskeleton.R;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.iface.BoxCollidable;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.AnimObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.util.CollisionHelper;
import kr.ac.kpu.game.gunhwi.gameskeleton.game.scene.SecondScene;

public class Character extends AnimObject implements BoxCollidable {

    private static final float JUMP_POWER = -1500;
    private static final float GRAVITY_SPEED = 4500;
    private static final String TAG = Character.class.getSimpleName();
    private static final float SLIDE_TIME = 1.0f;
    private static final float HIT_TIME = 0.3f;
    private final FrameAnimationBitmap fabNormal;
    private final FrameAnimationBitmap fabMoveR;
    private final FrameAnimationBitmap fabMoveL;
    private final FrameAnimationBitmap fabDie;
    private final FrameAnimationBitmap fabHit;

    private int jumpCount = 10; // 10 if falling
    private float speed;
    private float base;
    private float stateTime;
    private AnimState state;
    private float hitTime;

    public Character(float x, float y) {
        super(x, y, -50, -50, R.mipmap.cookie_run, 12, 0);
        base = y;

        fabNormal = fab;
        fabMoveR = new FrameAnimationBitmap(R.mipmap.cookie_run, 12, 0);
        fabMoveL = new FrameAnimationBitmap(R.mipmap.cookie_run_l, 12, 0);
        fabDie = new FrameAnimationBitmap(R.mipmap.cookie_slide, 12, 2);
        fabHit = new FrameAnimationBitmap(R.mipmap.cookie_hit, 12, 2);

        stateTime = -1;
    }

    public void right() {
            setAnimState(AnimState.right);
    }

    public void left() {
            setAnimState(AnimState.left);

    }

    public enum AnimState {
        normal, right, left//, hit
    }
    public void setAnimState(AnimState state) {
        this.state = state;

        switch (state) {
            case normal: fab = fabNormal; break;
            case right:   fab = fabMoveR;   break;
            case left:  fab = fabMoveL;  break;
        }
    }

    @Override
    public void update() {
        if (jumpCount > 0) {
            float timeDiffSeconds = GameTimer.getTimeDiffSeconds();
            y += speed * timeDiffSeconds;
            speed += GRAVITY_SPEED * timeDiffSeconds;
        }

        SecondScene scene = SecondScene.get();
        float footY = y + height / 2;
        Platform platform = scene.getPlatformAt(x, footY);
        if (platform != null) {

            RectF rect = new RectF();
            platform.getBox(rect);
//            Log.d(TAG, "Platform box = " + rect);

            float ptop = platform.getTop();
            if (jumpCount > 0) {
//                Log.d(TAG, "Platform box = " + rect + " footY = " + footY + " ptop=" + ptop);
                if (speed > 0 && footY >= ptop) {
//                    Log.d(TAG, " Stopping at the platform");
                    y = ptop - height / 2;
                    jumpCount = 0;
                    speed = 0;
                    setAnimState(AnimState.normal);
                }
            } else {
                if (footY < ptop) {
//                    Log.d(TAG, " Start to fall down");
                    jumpCount = 10; // falling down
                    stateTime = -1;
                    setAnimState(AnimState.normal);
                }
            }
        } else {
//            Log.d(TAG, " No platform. Falling down");
            jumpCount = 10;
        }

        if (state == AnimState.right) {
            x+= 10;
        }

        if (state == AnimState.left) {
            x-= 10;
        }

        if (hitTime > 0) {
            hitTime -= GameTimer.getTimeDiffSeconds();
            if (hitTime < 0) {
                hitTime = 0;
            }
        }

        checkItemCollision();
        checkObstacleCollision();
    }

    private void checkItemCollision() {

        ArrayList<GameObject> items = SecondScene.get().getGameWorld().objectsAtLayer(SecondScene.Layer.item.ordinal());
        for (GameObject obj : items) {
            if (!(obj instanceof CandyItem)) {
                continue;
            }
            CandyItem candy = (CandyItem) obj;
            if (CollisionHelper.collides(this, candy)) {
                candy.remove();
                SecondScene.get().addScore(candy.getScore());
            }
        }
    }
    private void checkObstacleCollision() {

        ArrayList<GameObject> obstacles = SecondScene.get().getGameWorld().objectsAtLayer(SecondScene.Layer.obstacle.ordinal());
        for (GameObject obj : obstacles) {
            if (!(obj instanceof Obstacle)) {
                continue;
            }
            Obstacle obstacle = (Obstacle) obj;
            if (!obstacle.isEnabled()){
                continue;
            }
            if (CollisionHelper.collides(this, obstacle)) {
                //obstacle.remove();
                //SecondScene.get().addScore(obstacle.getScore());
                obstacle.setEnabled(false);
//                this.setAnimState(AnimState.hit);
                hitTime = HIT_TIME;

                Log.d(TAG, "Collision: " + obstacle);
                SecondScene.get().decreaseLife();
//                int power = obstacle.getPower();
//                decreaseLife(power);
            }
        }
    }

//    //@Override
//    public boolean onTouchEvent(MotionEvent e) {
//        if (e.getAction() != MotionEvent.ACTION_DOWN) {
//            return false;
//        }
//        float tx = e.getX();
////        Log.d(TAG, "TouchEvent:" + e.getAction() + " - " + tx + "/" + UiBridge.metrics.center.x);
//        if (tx < UiBridge.metrics.center.x) {
//        } else {
//        }
//        return false;
//    }
    @Override
    public void getBox(RectF rect) {
        int hw = width / 2;
        int hh = height / 2;
        if (fab == fabDie) {
            rect.left = x - hw;
            rect.top = y;
            rect.right = x + hw;
            rect.bottom = y + hh;
        } else {
            rect.left = x - hw;
            rect.top = y - hh;
            rect.right = x + hw;
            rect.bottom = y + hh;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;

        FrameAnimationBitmap fab = hitTime > 0 ? this.fabHit : this.fab;
        fab.draw(canvas, dstRect, null);
    }
}
