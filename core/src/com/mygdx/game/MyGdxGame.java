package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	private AssetManager manager;  //para centralizar los recursos
	public AssetManager getManager() {
		return manager;
	}




	@Override
	public void create() {
		manager=new AssetManager();
		manager.load("floor.png",Texture.class);
		manager.load("overfloor1.png",Texture.class);
		manager.load("spike.png",Texture.class);
		manager.load("player.png",Texture.class);
		manager.finishLoading();
		setScreen(new GameScreen(this));
	}
}