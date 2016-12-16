package com.sikware.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.sikware.gameobjects.Bird;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameWorld {

    private Bird bird;

    public GameWorld(int midPoint){
        //initialize bird
        bird=new Bird(33, midPoint - 5, 17, 12);
    }


    public void update(float delta){
        bird.update(delta);
    }
    public Bird getBird(){
        return bird;
    }

}
