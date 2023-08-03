package com.haiprj.games.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.haiprj.games.models.UserActionModel;

import java.util.ArrayList;
import java.util.List;

public class UserControlScene {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<UserActionModel> children = new ArrayList<>();
    private final SpriteBatch batch;
    public UserControlScene() {
        this.batch = new SpriteBatch();
        initChildren();
    }

    private void initChildren() {
        children.add(new UserActionModel(batch, new Texture("./badlogic.jpg"), 0, 0, 50, 50));
    }

    public void update(float dt) {
        //do anythings
        this.batch.begin();
        for (UserActionModel child : children) {
            child.draw();
        }
        this.batch.end();
    }

    public void dispose() {
        for (UserActionModel child : children) {
            child.dispose();
        }
        this.batch.dispose();
    }

    public boolean isTouchMe(Vector2 locate) {
        return isTouchMe(locate.x, locate.y);
    }

    public boolean isTouchMe(float x, float y) {
        for (UserActionModel child : this.children) {
            if (child.contains(x, y)) return true;
        }
        return false;
    }
}
