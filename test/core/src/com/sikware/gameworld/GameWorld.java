package com.sikware.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sikware.gameobjects.Bird;
import com.sikware.gameobjects.ScrollHandler;
import com.sikware.zbhelpers.AssetLoader;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;

    public enum GameState{MENU,READY,RUNNING,GAMEOVER,HIGHSCORE}

    private GameState currentState;

    public GameWorld(int midPointY){
        currentState= GameState.MENU;
        this.midPointY=midPointY;
        bird=new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY+66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }

    public void update(float delta){
        runTime+=delta;
        switch (currentState){
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
               updateRunning(delta);
                break;
            default:
                break;
        }
    }

    public void updateReady(float delta){
        bird.updateReady(delta);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta){
        if(delta > .15f){delta=.15f;}
        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;

            if(score > AssetLoader.getHighScore()){
                AssetLoader.setHighScore(score);
                currentState=GameState.HIGHSCORE;
            }
        }
    }

    public void restart(){
        currentState=GameState.READY;
        score=0;
        bird.onRestart(midPointY-5);
        scroller.onRestart();
        currentState=GameState.READY;
    }

    public int getMidPointY(){return midPointY;}
    public ScrollHandler getSccroller(){return scroller;}
    public void start(){currentState=GameState.RUNNING;}
    public void ready(){currentState=GameState.READY;}
    public boolean isReady(){return currentState==GameState.READY;}
    public boolean isHightScore(){return currentState==GameState.HIGHSCORE;}
    public boolean isGameOver(){return currentState==GameState.GAMEOVER;}
    public boolean isMenu(){return currentState==GameState.MENU;}
    public boolean isRunning(){return currentState==GameState.RUNNING;}
    public int getScore(){return score;}
    public void addScore(int increment){score += increment;}
    public Bird getBird(){return bird;}
    public ScrollHandler getScroller(){return scroller;}

}
