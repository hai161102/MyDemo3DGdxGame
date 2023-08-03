package com.haiprj.games.interfaces;

import io.socket.emitter.Emitter;

public interface ServerCallback extends Emitter.Listener {

    @Override
    void call(Object... args);

}
