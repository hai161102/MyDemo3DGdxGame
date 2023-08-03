package com.haiprj.games.scene;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;

public class CustomPointLight extends PointLight {

    public CustomPointLight() {
        this.set(0.8f, 0.8f, 0.8f, 10f, 10f, 10f, 1000f);
    }

    public void addData(Environment environment) {
        environment.add(this);
    }
}
