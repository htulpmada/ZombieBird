package com.sikware.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sikware.gameworld.GameRenderer;
import com.sikware.gameworld.GameWorld;
import com.sikware.zbhelpers.InputHandler;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameScreen implements Screen{
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime=0;


    public GameScreen(){
        float screenWidth=Gdx.graphics.getWidth();
        float screenHeight=Gdx.graphics.getHeight();
        float gameWidth=136;
        float gameHeight= screenHeight/(screenWidth/gameWidth);

        int midPointY= (int) (gameHeight/2);

        Gdx.app.log("GameScreen","Attached");
        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen","show called");
    }

    @Override
    public void render(float delta) {
        runTime+=delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen","resizing");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen","pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen","resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen","hide called");
    }

    @Override
    public void dispose() {

    }
}
