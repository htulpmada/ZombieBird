package com.sikware.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by adam pluth on 12/19/16.
 */

public class SimpleButton {

    private float x, y, width, height;
    private TextureRegion buttonUp, buttonDown;
    private Rectangle bounds;
    private boolean isPressed = false;

    public SimpleButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.buttonUp=buttonUp;
        this.buttonDown=buttonDown;
        bounds = new Rectangle(x,y,width,height);
    }

    public boolean isClicked(int screenx, int screeny){
        return bounds.contains(screenx,screeny);
    }

    public void draw(SpriteBatch batcher){
        if(isPressed){
            batcher.draw(buttonDown, x, y, width, height);
        }
        else{
            batcher.draw(buttonUp,x ,y ,width, height);
        }
    }

    public boolean isTouchDown(int screenx, int screeny){
        if(bounds.contains(screenx, screeny)){
            isPressed=true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenx, int screeny){
        if(bounds.contains(screenx,screeny)&& isPressed){
            isPressed=false;
            return true;
        }
        isPressed=false;
        return false;
    }

}
