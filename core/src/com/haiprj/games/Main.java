package com.haiprj.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.game.BaseGame;
import com.haiprj.gamebase.utils.GameUtils;
import com.haiprj.gamebase.utils.server.SocketConnectionUtil;
import com.haiprj.games.models.ActorModel;
import com.haiprj.games.screens.LoadingScreen;
import com.haiprj.games.screens.MainScreen;
import com.haiprj.games.server.ServerUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("VulnerableCodeUsages")
public class Main extends BaseGame {

	public static String myIDSocket = "";
	public static final List<Model> listModel = new ArrayList<>();

	public Main() {
	}

	@Override
	public void create () {
		super.create();
		setCurrentScreen(new LoadingScreen(this));
		ServerUtil.getInstance().on(Const.USER_IN, args -> {
			Main.myIDSocket = (String) args[0];
			System.out.println(Main.myIDSocket);
		}).on(Const.POSITION, args -> {
			JSONObject object = (JSONObject) args[0];
			try {
				String id = object.getString("id");
				if (!Objects.equals(id, myIDSocket)) {
					for (ActorModel actorModel : ((MainScreen) this.currentScreen).getList()) {
						if (Objects.equals(actorModel.getID(), id)) {
							actorModel.setPosition(new Vector3(
									Float.parseFloat(object.getJSONObject("position").getString("x")),
									Float.parseFloat(object.getJSONObject("position").getString("y")),
									Float.parseFloat(object.getJSONObject("position").getString("z"))
							));
							break;
						}
					}
				}

			} catch (JSONException ignored) {

			}
		}).on(Const.DISCONNECT, args -> {
			String id = (String) args[0];
			if (!Objects.equals(id, myIDSocket)) {
				for (ActorModel actorModel : ((MainScreen) this.currentScreen).getList()) {
					if (Objects.equals(actorModel.getID(), id)) ((MainScreen) this.currentScreen).getList().remove(actorModel);
					return;
				}
			}
		}).on(Const.ANOTHER_USER_IN, args -> {
			Gdx.app.postRunnable(() -> {
				addNewPlayer((JSONObject) args[0]);
			});
		}).on(Const.GET_USERs, args -> {
			Gdx.app.postRunnable(() -> {
				setCurrentScreen(new MainScreen(this));
				JSONArray jsonArray = (JSONArray) args[0];
				System.out.println("data: " + jsonArray);
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						addNewPlayer(jsonArray.getJSONObject(i));
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
				}

			});
		});

	}

	@SuppressWarnings("VulnerableCodeUsages")
	private void addNewPlayer(JSONObject object) {
		ActorModel actorModel = new ActorModel(listModel.get(0));
		try {
			actorModel.setID(object.getString("id"));
			for (ActorModel model : ((MainScreen) Main.this.currentScreen).getList()) {
				if (Objects.equals(model.getID(), actorModel.getID())) return;
			}
			Vector3 pos = new Vector3(
					Float.parseFloat(object.getJSONObject("position").getString("x")),
					Float.parseFloat(object.getJSONObject("position").getString("y")),
					Float.parseFloat(object.getJSONObject("position").getString("z"))
			);
			actorModel.setPosition(pos);
			((MainScreen) Main.this.currentScreen).add(actorModel);
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
		SocketConnectionUtil.getInstance().socket.disconnect();
		System.exit(0);
	}
}
