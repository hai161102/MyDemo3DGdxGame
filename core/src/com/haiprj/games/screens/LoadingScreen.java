package com.haiprj.games.screens;

import com.badlogic.gdx.graphics.g3d.Model;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.haiprj.gamebase.utils.loader.G3DJLoader;
import com.haiprj.games.Main;

public class LoadingScreen extends BaseScreen {

    Model[] list;

    public LoadingScreen(BaseGame game) {
        super(game);

        Model m1 = G3DJLoader.getInstance().loadModel("./model/actor/actor_all.g3dj");
        Model m2 = G3DJLoader.getInstance().loadModel("./model/monster/monster_all_animation.g3dj");
        Main.listModel.add(m1);
        Main.listModel.add(m2);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
