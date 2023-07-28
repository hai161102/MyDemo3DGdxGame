package com.haiprj.games.scene;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.haiprj.gamebase.utils.LightData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomLight extends LightData {

    public CustomLight(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
        super(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
    }

    @Override
    public void update(float dt, ModelInstance... data) {
        super.update(dt, data);
    }
    public void updateLight(float dt, Object... data) {
        final List<ModelInstance> listModel = new ArrayList<>();
        for (Object datum : data) {
            if (datum instanceof ModelInstance || datum instanceof List){
                if (datum instanceof List) {
                    //noinspection unchecked
                    listModel.addAll((Collection<? extends ModelInstance>) datum);
                }
                else listModel.add((ModelInstance) datum);
            }

        }
        ModelInstance[] d = new ModelInstance[listModel.size()];
        for (ModelInstance modelInstance : listModel) {
            d[listModel.indexOf(modelInstance)] = modelInstance;

        }
        this.update(dt, d);
    }
}
