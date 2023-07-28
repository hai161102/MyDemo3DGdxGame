package com.haiprj.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.haiprj.gamebase.utils.loader.G3DJLoader;
import com.haiprj.games.Main;
import com.haiprj.games.interfaces.LoadingListener;
import com.haiprj.games.models.ActorModel;
import com.haiprj.games.models.CustomBaseModel;
import com.haiprj.games.socket.SocketInstance;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static com.haiprj.games.Main.myIDSocket;

public class LoadingScreen extends BaseScreen {

    private final LoadingListener listener;
    private final Thread thread;
    Model[] list;

    public LoadingScreen(BaseGame game, LoadingListener listener) {
        super(game);
        this.listener = listener;

        Model m1 = G3DJLoader.getInstance().loadModel("./model/actor/actor_all.g3dj");
        Model m2 = G3DJLoader.getInstance().loadModel("./model/monster/monster_all_animation.g3dj");
        Runnable runnable = () -> {
            try {

                this.list = new Model[] {
                        m1,
                        m2
                };
                Thread.sleep(1000);
                Gdx.app.postRunnable(() -> LoadingScreen.this.listener.onLoaded(list));

            } catch (InterruptedException ignored) {
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void dispose() {
        super.dispose();
        thread.interrupt();
    }
}
