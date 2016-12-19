package com.sikware.zombiebird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sikware.screens.GameScreen;
import com.sikware.screens.SplashScreen;
import com.sikware.zbhelpers.AssetLoader;

public class ZBGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}
}
