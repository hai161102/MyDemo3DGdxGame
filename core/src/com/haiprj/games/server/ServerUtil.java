package com.haiprj.games.server;

import com.haiprj.gamebase.utils.server.SocketConnectionUtil;
import com.haiprj.games.interfaces.ServerCallback;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ServerUtil {

    private static ServerUtil instance;
    public String key = "";
    private ServerUtil() {
        SocketConnectionUtil.getInstance();
    }
    public static ServerUtil getInstance() {
        if (instance == null) instance = new ServerUtil();
        return instance;
    }

    public Emitter on(String key, ServerCallback callback) {
        this.key = key;
        return SocketConnectionUtil.getInstance().emitter.on(key, callback);
    }

    @SuppressWarnings({"VulnerableCodeUsages", "DataFlowIssue"})
    public void emit(String key, String id, Object... data) {
        List<JSONObject> list = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = new JSONObject();
            for (Field declaredField : datum.getClass().getDeclaredFields()) {
                if (!java.lang.reflect.Modifier.isStatic(declaredField.getModifiers())) {
//                    try {
//                        System.out.println(declaredField.get(datum));
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
                    try {
                        jsonObject.put(declaredField.getName(), declaredField.get(datum));
                    } catch (JSONException | IllegalAccessException ignored) {

                    }
                }
            }
            list.add(jsonObject);
        }
        JSONObject[] jsonObjects = list.toArray(new JSONObject[0]);
        SocketConnectionUtil.getInstance().socket.emit(key, parseData(id, (Object[]) jsonObjects));
    }

    @SuppressWarnings("VulnerableCodeUsages")
    public static JSONObject parseData(String key, Object... data) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", key);
            jsonObject.put("data", data);
            return jsonObject;
        } catch (JSONException var4) {
            return null;
        }
    }


}
