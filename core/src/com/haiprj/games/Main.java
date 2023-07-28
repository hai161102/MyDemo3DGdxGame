package com.haiprj.games;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.haiprj.gamebase.utils.loader.G3DJLoader;
import com.haiprj.games.interfaces.LoadingListener;
import com.haiprj.games.models.ActorModel;
import com.haiprj.games.screens.LoadingScreen;
import com.haiprj.games.screens.MainScreen;
import com.haiprj.games.socket.SocketInstance;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

@SuppressWarnings("VulnerableCodeUsages")
public class Main extends BaseGame {

	public static String myIDSocket = "";
	public static final List<Model> listModel = new ArrayList<>();
	private boolean isQueriedServer = false;
	private boolean isLoadedModel = false;
	@SuppressWarnings("VulnerableCodeUsages")
	private JSONArray objects;
	@Override
	public void create () {
		super.create();
		SocketInstance.getInstance();
		SocketInstance.getInstance().onEmitter("USER_ID", (args) -> {
			myIDSocket = args[0].toString();
		});
		SocketInstance.getInstance().onEmitter("ADD_USERs", new SocketInstance.ActionEmitListener() {
			@SuppressWarnings("VulnerableCodeUsages")
			@Override
			public void onResult(Object... args) {
				try {
					System.out.println("On ADD main: " + Arrays.toString(args));
					objects = (JSONArray) args[0];
					isQueriedServer = true;
					loadView();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}
		});
		SocketInstance.getInstance().on("ADD_USER", new SocketInstance.ActionEmitListener() {
			@Override
			public void onResult(Object... args) {
				JSONObject jsonObject = (JSONObject) args[0];
				if (Main.this.currentScreen instanceof MainScreen) {
					addNewPlayer(jsonObject);
				}
			}
		});
		SocketInstance.getInstance().onEmitter("user-disconnect", (args) -> {
			System.out.println("has disconnect");
			String id = args[0].toString();
			for (ActorModel actorModel : ((MainScreen) Main.this.currentScreen).getList()) {
				if (Objects.equals(actorModel.getID(), id)){
					((MainScreen) Main.this.currentScreen).getList().remove(actorModel);
					return;
				}
			}
		});
		setCurrentScreen(new LoadingScreen(this, (models) -> {
			setCurrentScreen(new MainScreen(this));
			System.out.println("List models: " + Arrays.toString(models));
			listModel.addAll(Arrays.asList(models));
			isLoadedModel = true;
			loadView();

		}));

	}

	private void loadView() {
		if (!isLoadedModel || !isQueriedServer) return;
		try {
			for (int i = 0; i < objects.length(); i++) {
				addNewPlayer(objects.getJSONObject(i));
			}

		}catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		isQueriedServer = false;
	}

	@SuppressWarnings("VulnerableCodeUsages")
	private void addNewPlayer(JSONObject object) {
		ActorModel actorModel = new ActorModel(listModel.get(0));
		try {
			actorModel.setID(object.getString("id"));
			Vector3 pos = new Vector3(
					Float.parseFloat(object.getJSONObject("position").getString("x")),
					Float.parseFloat(object.getJSONObject("position").getString("y")),
					Float.parseFloat(object.getJSONObject("position").getString("z"))
			);
			actorModel.setPosition(pos);
			((MainScreen) Main.this.currentScreen).add(actorModel);
			if (Objects.equals(actorModel.getID(), myIDSocket)) {
				((MainScreen) Main.this.currentScreen).setSelf(actorModel);
			}
		} catch (JSONException ignored) {
		}


	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		System.out.println("On dispose");
		SocketInstance.getInstance().disconnect();
		System.exit(0);
	}
}
