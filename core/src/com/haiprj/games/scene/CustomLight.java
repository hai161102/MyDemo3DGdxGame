package com.haiprj.games.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.utils.LightData;

public class CustomLight extends LightData {

    public CustomLight(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
        super(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
        this.set(0.8f, 0.8f, 0.8f, -0.5f, -1f, -0.5f);
        this.vector = Vector3.Zero;


    }

    @Override
    public void update(float dt, ModelInstance... data) {
        super.update(dt, data);
    }

    public void updateLightShadow(float dt, Camera camera, Object... data) {
        this.camera = camera;
        this.updateLight(dt, data);
    }
    @Override
    public void dispose() {
        if (this.shadowBatch != null) this.shadowBatch.dispose();
        super.dispose();
    }
}
