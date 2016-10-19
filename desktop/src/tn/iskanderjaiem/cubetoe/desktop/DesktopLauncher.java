package tn.iskanderjaiem.cubetoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tn.iskanderjaiem.cubetoe.CubeToeGame;

public class DesktopLauncher {
	//Core et desktop//
	public static final int SCALE = 2;
	public static final int WIDTH = 400 * SCALE;
	public static final int HEIGHT =WIDTH * 3/5;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.title="TicTacToe - Iskander Jaiem - 4GL2";
		new LwjglApplication(new CubeToeGame(), config);
	}
}
