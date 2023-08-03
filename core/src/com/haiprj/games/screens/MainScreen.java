package com.haiprj.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.haiprj.games.Const;
import com.haiprj.games.Main;
import com.haiprj.games.models.ActorModel;
import com.haiprj.games.scene.CustomLight;
import com.haiprj.games.scene.CustomPointLight;
import com.haiprj.games.scene.UserControlScene;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.haiprj.gamebase.utils.GameUtils.CONFIG_SIZE;


public class MainScreen extends BaseScreen implements InputProcessor, MouseInputListener {

    private final Environment environment;
    private final ModelBatch modelBatch;
    private final ModelInstance planeInstance;
    private final CustomLight lightData;
    private final List<ActorModel> list = new ArrayList<>();
    private ActorModel self;
    private UserControlScene userControlScene;

    public MainScreen(BaseGame game) {
        super(game);

        environment = new Environment();
        lightData = new CustomLight(
                1024, 1024, Const.MAX_WORLD.x, Const.MAX_WORLD.z, 0.01f, 1000f);
        modelBatch = new ModelBatch();

        // Create a perspective camera with some sensible defaults
        camera.position.set(0f, 32f * CONFIG_SIZE, 32f * CONFIG_SIZE);
        camera.near = 0.01f;
        camera.far = 10000f * CONFIG_SIZE;
        camera.rotate(Vector3.X, -45);
        camera.update();

        lightData.setData(environment, camera);
//        environment.add(pointLight);
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));

        // Import and instantiate our model (called "myModel.g3dj")
        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material(ColorAttribute.createDiffuse(new Color(0, 0.8f, 0, 1)));
        Model plane = modelBuilder.createBox(Const.MAX_WORLD.x, 10 * CONFIG_SIZE, Const.MAX_WORLD.z, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        planeInstance = new ModelInstance(plane);
        planeInstance.transform.translate(0, -5 * CONFIG_SIZE, 0);
        Gdx.input.setInputProcessor(this);
        this.userControlScene = new UserControlScene();
    }
    public void add(ActorModel modelInstance) {
        this.list.add(modelInstance);
        for (ActorModel actorModel : this.list) {
            if (Objects.equals(actorModel.getID(), Main.myIDSocket)) {
                this.self = actorModel;

                break;
            }
        }
    }

    public void addAll(List<ActorModel> actorModels) {
        this.list.addAll(actorModels);
        for (ActorModel actorModel : this.list) {
            if (Objects.equals(actorModel.getID(), Main.myIDSocket)) {
                this.self = actorModel;
                break;
            }
        }
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
        onInput(delta);
        camera.position.set(self.getPosition().x, camera.position.y, self.getPosition().z + 32f * CONFIG_SIZE);
        camera.update();
        lightData.updateLight(delta, planeInstance, list);

        modelBatch.begin(camera);
        modelBatch.render(planeInstance, environment);
        for (ActorModel actorModel : this.list) {
            actorModel.update(delta, modelBatch, environment);
        }
        modelBatch.end();

        userControlScene.update(delta);
    }

    private void onInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.self.goFront(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) this.self.goBack(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.self.rotate(Vector3.Y, 1f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.self.rotate(Vector3.Y, -1f);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        this.lightData.dispose();
        modelBatch.dispose();
        for (ActorModel actorModel : this.list) {
            actorModel.dispose();
        }
    }

    public List<ActorModel> getList() {
        return list;
    }

    public ActorModel getSelf() {
        return self;
    }

    public void setSelf(ActorModel self) {
        this.self = self;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
