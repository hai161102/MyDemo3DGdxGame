package com.haiprj.games.models;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.haiprj.gamebase.base.model.BaseModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ActorModel extends CustomBaseModel {

    private AnimationKey animationKey = AnimationKey.IDLE;
    private boolean canAction = true;
    public ActorModel(String filename) {
        super(filename);

    }

    public ActorModel(Model model) {
        super(model);
    }

    public ActorModel(ModelInstance copyFrom) {
        super(copyFrom);
    }


    @Override
    protected void init() {
        super.init();
        animationKey = AnimationKey.IDLE;
        setAnimationKey(animationKey);
    }

    @Override
    public void update(float v) {

    }

    public void setAnimationKey(final AnimationKey animationKey) {
        this.animationKey = animationKey;
        if (this.animationKey == AnimationKey.IDLE) {
            this.loopCount = 1000;
        }
        else this.loopCount = 1;

        setAnimation(Objects.requireNonNull(animationKey.getAnimation(this.model)), new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
//                if (animationKey == AnimationKey.WALKING) {
//                    setPosition(getPosition().x, getPosition().y, getPosition().z + GameUtils.CONFIG_SIZE * 10);
//                }
                if (animationKey == AnimationKey.WALKING) {
                    setAnimationKey(AnimationKey.STOP_WALKING);
                    return;
                }

                if (animationKey != AnimationKey.IDLE) {
                    setAnimationKey(AnimationKey.IDLE);
                }
                canAction = true;
            }
            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                if (animationKey == AnimationKey.IDLE) {
                    if (animation.loopCount <= 1) {
                        loopCount = 1000;
                        setAnimationKey(AnimationKey.IDLE);
                    }
                }


            }
        });
    }

    @Override
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        super.update(dt, modelBatch, environment);
    }

    public void moveFront() {
        if (canAction) {
            setAnimationKey(AnimationKey.WALKING);
            canAction = false;
        }
    }

    public enum AnimationKey {
        IDLE,
        DYING,
        KICK,
        ROUND_KICK,
        PUNCH,
        RUN,
        WALKING,
        START_WALKING,
        STOP_WALKING;

        @NotNull
        @Contract(pure = true)
        public String getId() {
            switch (this) {
                case DYING:
                    return "dying";
                case KICK:
                    return "kicking";
                case ROUND_KICK:
                    return "kicking_round";
                case PUNCH:
                    return "punching_combo";
                case RUN:
                    return "running";
                case WALKING:
                    return "walking";
                case START_WALKING:
                    return "start_walking";
                case STOP_WALKING:
                    return "stop_walking";
                case IDLE:
                default:
                    return "idle";
            }
        }
        @Nullable
        public Animation getAnimation(@NotNull Model model) {
            for (Animation animation : model.animations) {
                if (getId().equals(animation.id)) return animation;
            }
            return null;
        }
    }
}
