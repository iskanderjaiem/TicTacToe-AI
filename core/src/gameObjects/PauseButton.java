package gameObjects;

import screens.HomeScreen;
import tn.iskanderjaiem.cubetoe.CubeToeGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class PauseButton extends Button {

	public PauseButton(Sprite btnSprite) {
		super(btnSprite);
	}
	

	public PauseButton(Sprite btnSprite,Sprite btnTouchedSprite){
		super(btnSprite,btnTouchedSprite);
	}
	public void draw(CubeToeGame game, SpriteBatch batch) {
		 if (this.isTouched()){
			 super.getBtnTouchedSprite().draw(batch); 
			 game.setScreen(new HomeScreen(game));
		 }else {
			 super.getSprite().draw(batch);
		 }
		 
	}

}
