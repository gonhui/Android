package kr.ac.kpu.game.gunhwi.gameskeleton.game.obj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.res.bitmap.SharedBitmap;

public class LifeObject extends GameObject {
    private static final String TAG = LifeObject.class.getSimpleName();
    public static final int COOKIE_LIFE = 5;
    public static final int SCORE_INCREASE_DURATION = 100;
    private final SharedBitmap sbmp;
    private final int heartWidth;
    private final int maxLife;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private int life;
    private RectF leftMostRect;
    private RectF rect = new RectF();
    private Rect srcRect = new Rect();

    public LifeObject(int resId, RectF leftMostRect, int maxLife) {
        sbmp = SharedBitmap.load(resId, false);
        heartWidth = sbmp.getWidth() / 2;
        int heartHeight = sbmp.getHeight();
        this.leftMostRect = new RectF(leftMostRect);
        if (this.leftMostRect.bottom == 0) {
            float width = this.leftMostRect.width();
            float height = width / heartWidth * heartHeight;
            this.leftMostRect.bottom = this.leftMostRect.top + height;
        }
        srcRect.top = 0;
        srcRect.bottom = heartHeight;
        this.maxLife = maxLife;
        this.life = maxLife;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        rect.set(leftMostRect);
        float width = rect.width();
        for (int i = 0; i < maxLife; i++) {
            srcRect.left = i < life ? heartWidth : 0;
            srcRect.right = srcRect.left + heartWidth;
            canvas.drawBitmap(sbmp.getBitmap(), srcRect, rect, null);
            rect.left += width;
            rect.right += width;
        }
    }

    public void reset() {
        life = maxLife;
    }

    public int decreaseLife() {
        Log.d(TAG, "Life: " + (life - 1));
        return --life;
    }
}
