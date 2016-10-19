package gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import extras.Extras;

public class Explosion {

	private Texture explosionSheet ;   
	private TextureRegion[] explosionFrames;
	private Animation explosionAnimation;
	private TextureRegion currentFrame;      
	private float stateTime;        
	private boolean animate;
	
	public Explosion(Texture explosionSheet, int frameRows, int frameCols){
		this.explosionSheet = explosionSheet;
		 
		TextureRegion[][] tmp = TextureRegion.split(explosionSheet, explosionSheet.getWidth()/frameCols, explosionSheet.getHeight()/frameRows);              
		explosionFrames = new TextureRegion[frameCols * frameRows];
	       
	        
		int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
            	explosionFrames[index++] = tmp[i][j];
            }
        }
        
        explosionAnimation = new Animation(0.005f, explosionFrames); 
        explosionAnimation.setPlayMode(PlayMode.NORMAL);
		stateTime = 0f;
		animate=true;
	}
	
	public void draw(SpriteBatch batch, float delta,float width, float height, float x, float y){
	   stateTime += delta;
       currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
 	   batch.draw(currentFrame,x,y,width,height);
 	   
	}

	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public TextureRegion[] getExplosionFrames() {
		return explosionFrames;
	}

	public void setExplosionFrames(TextureRegion[] explosionFrames) {
		this.explosionFrames = explosionFrames;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	
	
}
