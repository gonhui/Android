package kr.ac.kpu.game.gunhwi.gameskeleton.game.scene;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.game.gunhwi.gameskeleton.R;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.gunhwi.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.gunhwi.gameskeleton.game.obj.Ball;
import kr.ac.kpu.game.gunhwi.gameskeleton.game.obj.GameBackground;

public class LobbyScene extends GameScene {
    private static final String TAG = LobbyScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, ui, COUNT
    }

    private Ball ball;
    private GameTimer timer;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());

    }

    @Override
    public void enter() {
        super.enter();
        initObjects();
    }

    private void initObjects() {
        Random rand = new Random();
        int mdpi_100 = UiBridge.x(100);
        for (int i = 0; i < 10; i++) {
            int dx = rand.nextInt(2 * mdpi_100) - 1 * mdpi_100;
            if (dx >= 0) dx++;
            int dy = rand.nextInt(2 * mdpi_100) - 1 * mdpi_100;
            if (dy >= 0) dy++;
            ball = new Ball(mdpi_100, mdpi_100, dx, dy);
            gameWorld.add(Layer.enemy.ordinal(), ball);
        }
        gameWorld.add(Layer.bg.ordinal(), new GameBackground());

        BitmapObject title = new BitmapObject(UiBridge.metrics.center.x, UiBridge.y(160), -150, -150, R.mipmap.title);
        gameWorld.add(Layer.ui.ordinal(), title);
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;

        y += UiBridge.y(100);
        Button button = new Button(cx, y, R.mipmap.btn_start_game, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        button.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                InGameScene scene = new InGameScene();
                scene.push();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button);
        y += UiBridge.y(100);
        gameWorld.add(Layer.ui.ordinal(), new Button(cx, y, R.mipmap.btn_highscore, R.mipmap.blue_round_btn, R.mipmap.red_round_btn));
    }
}
