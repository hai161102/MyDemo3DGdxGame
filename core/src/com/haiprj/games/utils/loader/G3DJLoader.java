package com.haiprj.games.utils.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.JsonReader;

public class G3DJLoader {
    private static G3DJLoader instance;
    private final G3dModelLoader g3dModelLoader;
    private G3DJLoader() {
        g3dModelLoader = new G3dModelLoader(new JsonReader());
    }

    public static G3DJLoader getInstance() {
        if (instance == null) instance = new G3DJLoader();
        return instance;
    }

    public Model[] load(String... filename) {
        Model[] models = new Model[filename.length];
        for (int i = 0; i < filename.length; i++) {
            models[i] = load(filename[i]);
        }
        return models;
    }

    public Model load(String filename) {
        try {
            return g3dModelLoader.loadModel(Gdx.files.internal(filename));

        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
