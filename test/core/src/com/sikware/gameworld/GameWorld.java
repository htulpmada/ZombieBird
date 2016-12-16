package com.sikware.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.sikware.gameobjects.Bird;
import com.sikware.gameobjects.ScrollHandler;
import com.sikware.gameobjects.Scrollable;

/**
 * Created by adam pluth on 12/16/16.
 */

public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;

    public GameWorld(int midPoint){
        //initialize bird
        bird=new Bird(33, midPoint - 5, 17, 12);
        scroller = new ScrollHandler(midPoint+66);
    }

    public void update(float delta){
        bird.update(delta);
        scroller.update(delta);
    }

    public Bird getBird(){return bird;}
    public ScrollHandler getScroller(){return scroller;}

}
