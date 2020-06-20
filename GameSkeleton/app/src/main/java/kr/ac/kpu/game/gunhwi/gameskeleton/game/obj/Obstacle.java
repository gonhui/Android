package kr.ac.kpu.game.gunhwi.gameskeleton.game.obj;

import android.graphics.Point;
import android.util.Log;

import kr.ac.kpu.game.gunhwi.gameskeleton.R;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.iface.Recyclable;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.RecyclePool;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.res.bitmap.SharedBitmap;

public class Obstacle extends BitmapObject implements Recyclable {
    private static final int[] RES_IDS = {
            R.mipmap.obstacle_63x99,
            R.mipmap.obstacle_81x131,
            R.mipmap.obstacle_87x222,
    };
    private static final Point[] SIZES = {
            new Point(1, 1),
            new Point(1, 2),
            new Point(1, 3),
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = Obstacle.class.getSimpleName();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected boolean enabled;

    protected Obstacle(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
//        super(x + SIZES[typeIndex].x / 2, y + SIZES[typeIndex].y / 2, SIZES[typeIndex].x * unit, SIZES[typeIndex].y * unit, RES_IDS[typeIndex]);
        Log.d(TAG, "Creating Platform instance");
    }
    public static Obstacle get(float x, float y, int unit, int typeIndex) {
        Point size = SIZES[typeIndex];
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Obstacle item = (Obstacle) rpool.get(Obstacle.class);
        if (item == null) {
            item = new Obstacle(x + unit * size.x / 2, y + unit * size.y / 2, unit * size.x, unit * size.y, typeIndex);
        } else {
            item.x = x + unit * size.x / 2;
            item.y = y + unit * size.y / 2;
            item.width = unit * size.x;
            item.height = unit * size.y;
            item.sbmp = SharedBitmap.load(RES_IDS[typeIndex]);
        }
        item.enabled = true;
        return item;
    }

    @Override
    public void update() {
        super.update();
        if (x < -width) {
            remove();
        }
    }

    @Override
    public void recycle() {
    }

    public float getTop() {
        return y - height / 2;
    }
}
