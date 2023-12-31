package com.haiprj.games.socket;

import com.badlogic.gdx.Gdx;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SocketInstance {

    public final static String URL_SOCKET = "http://localhost:8080";
    private static SocketInstance instance;

    public Socket socket;
    private Emitter emitter;

    private SocketInstance() {
        try {
            this.socket = IO.socket(URL_SOCKET);
            this.socket.connect();
            this.emitter = this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Gdx.app.log("Create Emitter", "Success");
                }
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static SocketInstance getInstance() {
        if (instance == null) instance = new SocketInstance();
        return instance;
    }

    public void on(String key, final ActionEmitListener callback){
        if (this.emitter != null) {
            try {
                this.emitter.on(key, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Gdx.app.log("On call emitter", Arrays.toString(args));
                        callback.onResult(args);
                    }
                });
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void disconnect() {
        if (this.socket != null) this.socket.disconnect();
    }

    public interface ActionEmitListener {
        void onResult(Object... args);
    }
}
