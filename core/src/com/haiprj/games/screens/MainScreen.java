package com.haiprj.games.screens;

import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.haiprj.gamebase.utils.LightData;
import com.haiprj.games.models.ActorModel;
import com.haiprj.games.models.MonsterModel;
import com.haiprj.games.scene.UserControlScene;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import static com.haiprj.gamebase.utils.GameUtils.CONFIG_SIZE;


public class MainScreen extends BaseScreen {

    private final Environment environment;
    private final CameraInputController cameraController;
    private final ModelBatch modelBatch;
    private final ActorModel ninjaModel;
    private final ModelInstance planeInstance;
    private final LightData lightData;
    private UserControlScene userControlScene;

    public MainScreen(BaseGame game) {
        super(game);

        environment = new Environment();
        lightData = new LightData(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                2,
                2,
                0.01f,
                10000);
        modelBatch = new ModelBatch();

        // Create a perspective camera with some sensible defaults
        camera.position.set(0f * CONFIG_SIZE, 10f * CONFIG_SIZE, 50f * CONFIG_SIZE);
        camera.near = 0.01f;
        camera.far = 1000f * CONFIG_SIZE;
        camera.update();

        lightData.setData(environment, camera);
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));

        // Import and instantiate our model (called "myModel.g3dj")
        ModelBuilder modelBuilder = new ModelBuilder();
        ninjaModel = new ActorModel("model/actor/actor_all.g3dj");
        Material material = new Material(ColorAttribute.createDiffuse(new Color(0, 0.8f, 0, 1)));
        Model plane = modelBuilder.createBox(100 * CONFIG_SIZE, 10 * CONFIG_SIZE, 100 * CONFIG_SIZE, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        planeInstance = new ModelInstance(plane);
        planeInstance.transform.translate(0, -5 * CONFIG_SIZE, 0);
        cameraController = new CameraInputController(camera);
        cameraController.scrollFactor *= CONFIG_SIZE;

//        Gdx.input.setInputProcessor(new InputProcessor() {
//            @Override
//            public boolean keyDown(int keycode) {
//                System.out.println(keycode);
//                switch (keycode) {
//                    case 66:
//                        monsterModel.attack();
//                        break;
//                    case 8:
//                        monsterModel.useSkill();
//                        break;
//                    case 19:
//                        ninjaModel.moveFront();
//                        break;
//                    case 20:
//                        monsterModel.moveBack();
//                        break;
//
//                }
//                cameraController.keyDown(keycode);
//                return false;
//            }
//
//            @Override
//            public boolean keyUp(int keycode) {
//                cameraController.keyUp(keycode);
//                return false;
//            }
//
//            @Override
//            public boolean keyTyped(char character) {
//                cameraController.keyTyped(character);
//                return false;
//            }
//
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                cameraController.touchDown(screenX, screenY, pointer, button);
//                return false;
//            }
//
//            @Override
//            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                cameraController.touchUp(screenX, screenY, pointer, button);
//                return false;
//            }
//
//            @Override
//            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//                return false;
//            }
//
//            @Override
//            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                cameraController.touchDragged(screenX, screenY, pointer);
//                return false;
//            }
//
//            @Override
//            public boolean mouseMoved(int screenX, int screenY) {
//                cameraController.mouseMoved(screenX, screenY);
//                return false;
//            }
//
//            @Override
//            public boolean scrolled(float amountX, float amountY) {
////                cameraController.scrolled(amountX, amountY);
//                return false;
//            }
//        });
        Gdx.input.setInputProcessor(cameraController);
        this.userControlScene = new UserControlScene();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Clear the stuff that is left over from the previous render cycle

//        camera.position.set(ninjaModel.getPositionForCam());
        cameraController.update();
        camera.update();

        lightData.update(delta, planeInstance, ninjaModel);


        // Let our ModelBatch take care of efficient rendering of our ModelInstance
        modelBatch.begin(camera);
        modelBatch.render(planeInstance, environment);
        ninjaModel.update(delta, modelBatch, environment);
        modelBatch.end();

//        userControlScene.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
        ninjaModel.dispose();
    }
}
