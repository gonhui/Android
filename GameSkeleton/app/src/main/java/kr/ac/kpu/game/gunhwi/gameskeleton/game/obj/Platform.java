package kr.ac.kpu.game.gunhwi.gameskeleton.game.obj;

import android.graphics.Point;
import android.util.Log;

import kr.ac.kpu.game.gunhwi.gameskeleton.R;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.iface.Recyclable;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.RecyclePool;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.res.bitmap.SharedBitmap;

public class Platform extends BitmapObject implements Recyclable {
    private static final int[] RES_IDS = {
            R.mipmap.cookierun_platform_480x48,
            R.mipmap.cookierun_platform_124x120,
            R.mipmap.cookierun_platform_120x40,
    };
    private static final Point[] SIZES = {
            new Point(10, 2),
            new Point(2, 2),
            new Point(3, 1),
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = Platform.class.getSimpleName();

    protected Platform(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
//        super(x + SIZES[typeIndex].x / 2, y + SIZES[typeIndex].y / 2, SIZES[typeIndex].x * unit, SIZES[typeIndex].y * unit, RES_IDS[typeIndex]);
        Log.d(TAG, "Creating Platform instance");
    }
    public static Platform get(float x, float y, int unit, int typeIndex) {
        Point size = SIZES[typeIndex];
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Platform item = (Platform) rpool.get(Platform.class);
        if (item == null) {
            item = new Platform(x + unit * size.x / 2, y + unit * size.y / 2, unit * size.x, unit * size.y, typeIndex);
        } else {
            item.x = x + unit * size.x / 2;
            item.y = y + unit * size.y / 2;
            item.width = unit * size.x;
            item.height = unit * size.y;
            item.sbmp = SharedBitmap.load(RES_IDS[typeIndex]);
        }
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
