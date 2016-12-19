package com.sikware.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sikware.gameobjects.Bird;
import com.sikware.gameobjects.Grass;
import com.sikware.gameobjects.Pipe;
import com.sikware.gameobjects.ScrollHandler;
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

    //gameObjects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    //gameAssets
    private TextureRegion bg,grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

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

        initGameObjects();
        initAssets();

    }

    public void render(float runTime){

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

        //begin batcher
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY +23, 136, 43);
        drawGrass();
        drawPipes();
        batcher.enableBlending();
        drawSkulls();

        if(bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth()/2.0f, bird.getHeight()/2.0f,bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }
        else{batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime),bird.getX(), bird.getY(), bird.getWidth()/2.0f,bird.getHeight()/2.0f,bird.getWidth(),bird.getHeight(),1 , 1, bird.getRotation());}

        if(myworld.isReady()){
            AssetLoader.shadow.draw(batcher, "Touch me", (136/2) - (42), 76);
            AssetLoader.font.draw(batcher, "Touch me", (136/2) - (42 - 1), 75);
        }
        else{
            if(myworld.isGameOver()||myworld.isHightScore()){
                if(myworld.isGameOver()){
                    AssetLoader.shadow.draw(batcher, "GAME OVER", 25, 56);
                    AssetLoader.font.draw(batcher, "GAME OVER", 24, 55);

                    AssetLoader.shadow.draw(batcher, "High Score", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score", 22, 105);

                    String highScore = AssetLoader.getHighScore()+"";

                    AssetLoader.shadow.draw(batcher, highScore, (136/2)-(3*highScore.length()), 128);
                    AssetLoader.font.draw(batcher, highScore, (136/2)-(3*highScore.length()-1), 127);
                }
                else{
                    AssetLoader.shadow.draw(batcher, "High Score", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score", 18, 55);
                }

                AssetLoader.shadow.draw(batcher, "Try Again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try Again?", 24, 75);

                String score = myworld.getScore()+"";

                AssetLoader.shadow.draw(batcher, score, (136/2) - (3* score.length()),12);
                AssetLoader.font.draw(batcher, score, (136/2) - (3* score.length()-1),11);
            }
        }

        String score = myworld.getScore()+"";

        AssetLoader.shadow.draw(batcher, ""+myworld.getScore(), (136/2)-(3*score.length()), 12);
        AssetLoader.font.draw(batcher, ""+myworld.getScore(), (136/2)-(3*score.length()-1), 11);

        batcher.end();
        /*
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y,bird.getBoundingCircle().radius);
        //shapeRenderer.end();



         // Excuse the mess below. Temporary code for testing bounding
         // rectangles.

        // Bar up for pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
                pipe1.getBarUp().width, pipe1.getBarUp().height);
        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
                pipe2.getBarUp().width, pipe2.getBarUp().height);
        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
                pipe3.getBarUp().width, pipe3.getBarUp().height);

        // Bar down for pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
                pipe1.getBarDown().width, pipe1.getBarDown().height);
        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
                pipe2.getBarDown().width, pipe2.getBarDown().height);
        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
                pipe3.getBarDown().width, pipe3.getBarDown().height);

        // Skull up for Pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getSkullUp().x, pipe1.getSkullUp().y,
                pipe1.getSkullUp().width, pipe1.getSkullUp().height);
        shapeRenderer.rect(pipe2.getSkullUp().x, pipe2.getSkullUp().y,
                pipe2.getSkullUp().width, pipe2.getSkullUp().height);
        shapeRenderer.rect(pipe3.getSkullUp().x, pipe3.getSkullUp().y,
                pipe3.getSkullUp().width, pipe3.getSkullUp().height);

        // Skull down for Pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getSkullDown().x, pipe1.getSkullDown().y,
                pipe1.getSkullDown().width, pipe1.getSkullDown().height);
        shapeRenderer.rect(pipe2.getSkullDown().x, pipe2.getSkullDown().y,
                pipe2.getSkullDown().width, pipe2.getSkullDown().height);
        shapeRenderer.rect(pipe3.getSkullDown().x, pipe3.getSkullDown().y,
                pipe3.getSkullDown().width, pipe3.getSkullDown().height);

        shapeRenderer.end();
*/

    }

    private void initGameObjects(){
        bird=myworld.getBird();
        scroller=myworld.getScroller();
        frontGrass=scroller.getFrontGrass();
        backGrass=scroller.getBackGrass();
        pipe1=scroller.getPipe1();
        pipe2=scroller.getPipe2();
        pipe3=scroller.getPipe3();
    }

    private void initAssets(){
        bg=AssetLoader.bg;
        grass=AssetLoader.grass;
        birdAnimation=AssetLoader.birdAnimation;
        birdMid=AssetLoader.bird;
        birdDown=AssetLoader.birdDown;
        birdUp=AssetLoader.birdUp;
        skullUp=AssetLoader.skullUp;
        skullDown=AssetLoader.skullDown;
        bar=AssetLoader.bar;
    }

    private void drawGrass(){
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
    }
    private void drawSkulls(){
        batcher.draw(skullUp, pipe1.getX()-1, pipe1.getY()+pipe1.getHeight()-14, 24, 14);
        batcher.draw(skullDown, pipe1.getX()-1, pipe1.getY()+pipe1.getHeight()+45, 24, 14);

        batcher.draw(skullUp, pipe2.getX()-1, pipe2.getY()+pipe2.getHeight()-14, 24, 14);
        batcher.draw(skullDown, pipe2.getX()-1, pipe2.getY()+pipe2.getHeight()+45, 24, 14);

        batcher.draw(skullUp, pipe3.getX()-1, pipe3.getY()+pipe3.getHeight()-14, 24, 14);
        batcher.draw(skullDown, pipe3.getX()-1, pipe3.getY()+pipe3.getHeight()+45, 24, 14);

    }
    private void drawPipes(){
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }


}
