package com.haiprj.games.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.model.BaseModel;
import com.haiprj.games.Const;
import com.haiprj.games.server.ServerUtil;

public abstract class CustomBaseModel extends BaseModel {

    protected String uID;
    protected Vector3 beforePosition;
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
        this.beforePosition = new Vector3(0, 0, 0);
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

    @Override
    public void setPosition(Vector3 position) {
        super.setPosition(position);
        if (this.uID != null && this.position != null && this.beforePosition != null
                && (beforePosition.x != this.position.x || beforePosition.y != this.position.y || this.beforePosition.z != this.position.z)) {
            this.beforePosition.set(this.position);
//            SocketInstance.getInstance().emit(Const.POSITION, this.uID, this.position);
            ServerUtil.getInstance().emit(Const.POSITION, this.uID, this.position);
        }
    }
}
