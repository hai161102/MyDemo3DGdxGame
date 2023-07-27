package com.haiprj.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.games.screens.MainScreen;
import com.haiprj.games.socket.SocketInstance;

public class Main extends BaseGame {


	@Override
	public void create () {
		super.create();
		SocketInstance.getInstance();
		SocketInstance.getInstance().on("USER_ID", new SocketInstance.ActionEmitListener() {
			@Override
			public void onResult(Object... args) {

			}
		});
		setCurrentScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		SocketInstance.getInstance().disconnect();
	}
}
