package com.sikware.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameRenderer {

    private GameWorld myworld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    public GameRenderer(GameWorld world) {
        myworld=world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(){
        Gdx.app.log("GameRenderer","render");
        // 1. draw background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // 2. draw rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // choose color
        shapeRenderer.setColor(87/255.0f, 109/255.0f, 120/255.0f, 1);
        //draw rect from myworld
        shapeRenderer.rect(myworld.getRect().x, myworld.getRect().y, myworld.getRect().width, myworld.getRect().height);
        // have to tell to end EVERY time
        shapeRenderer.end();
        // 3. draw rect outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(255/255.0f, 109/255.0f, 120/255.0f, 1);
        shapeRenderer.rect(myworld.getRect().x, myworld.getRect().y, myworld.getRect().width, myworld.getRect().height);
        shapeRenderer.end();
    }

}
