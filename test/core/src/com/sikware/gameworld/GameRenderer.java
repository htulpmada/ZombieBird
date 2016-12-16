package com.sikware.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sikware.gameobjects.Bird;
import com.sikware.zbhelpers.AssetLoader;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameRenderer {

    private GameWorld myworld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int gameHeight;
    private int midPointY;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myworld=world;

        this.gameHeight=gameHeight;
        this.midPointY=midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer= new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    public void render(float runTime){

        Bird bird = myworld.getBird();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeType.Filled);
        //draw background
        shapeRenderer.setColor(55/255.0f, 80/255.0f, 100/255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);
        //draw grass
        shapeRenderer.setColor(111/255.0f, 186/255.0f, 45/255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);
        //draw dirt
        shapeRenderer.setColor(147/255.0f, 80/255.0f, 27/255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        //end shapeRenderer
        shapeRenderer.end();

        //bein batcher
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg, 0, midPointY +23, 136, 43);
        batcher.enableBlending();
        batcher.draw((TextureRegion) AssetLoader.birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

        batcher.end();
    }

}
