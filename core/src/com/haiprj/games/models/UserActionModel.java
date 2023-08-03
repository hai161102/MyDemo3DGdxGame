package com.haiprj.games.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UserActionModel extends Rectangle {

    private final SpriteBatch batch;
    private final Texture texture;

    public UserActionModel(SpriteBatch batch, Texture texture) {
        this.batch = batch;
        this.texture = texture;
    }

    public UserActionModel(SpriteBatch batch, Texture texture, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.batch = batch;
        this.texture = texture;
    }

    public UserActionModel(SpriteBatch batch, Texture texture, Rectangle rect) {
        super(rect);
        this.batch = batch;
        this.texture = texture;
    }

    public void draw() {
        this.batch.draw(texture, this.x, this.y, this.width, this.height);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
