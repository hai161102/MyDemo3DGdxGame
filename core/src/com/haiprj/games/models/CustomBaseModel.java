package com.haiprj.games.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.haiprj.gamebase.base.model.BaseModel;

public abstract class CustomBaseModel extends BaseModel {

    protected String uID = "";
    public CustomBaseModel(Model model) {
        super(model);
    }

    public CustomBaseModel(String path) {
        super(path);
    }

    public CustomBaseModel(ModelInstance copyFrom) {
        super(copyFrom);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void update(float v) {

    }

    public String getID() {
        return uID;
    }

    public void setID(String uID) {
        this.uID = uID;
    }
}
