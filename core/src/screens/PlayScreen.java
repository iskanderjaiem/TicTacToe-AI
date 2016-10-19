package screens;

import extras.Extras;
import gameObjects.Explosion;
import gameObjects.InfoBar;
import gameObjects.PauseButton;
import gameObjects.ScrollingSprite;
import tn.iskanderjaiem.cubetoe.CubeToeGame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class PlayScreen implements Screen {

	private CubeToeGame game;
	private SpriteBatch batch;
	private Sprite bgSand0, bg, bgSquare, ice1, ice2;
	private ScrollingSprite bgSand1, bgSand2, bgSand3;
	private float time = 0;
	private PauseButton pauseButton;
	boolean explosionIsAnimated = false;
	boolean pointerJoystick = false;
	boolean pointerFireBall = false;

	Board2 b = new Board2();

	public PlayScreen(CubeToeGame game) {
		this.game = game;
		// LOGIC
		int choice = 2;

		Random rand = new Random();
		System.out.println("===> " + rand.nextInt(3));
		if (choice == 1) {
			Point p = new Point(rand.nextInt(3), rand.nextInt(3));
			b.placeAMove(p, 1);
			b.displayBoard();
		}
	}

	@Override
	public void show() {

		batch = new SpriteBatch();

		bg = new Sprite(new Texture(Gdx.files.internal("bg1.png")));
		bg.setSize(Extras.xUnite(1024), Extras.yUnite(512));

		bgSand0 = new Sprite(new Texture(Gdx.files.internal("bgSand0.png")));
		bgSand0.setSize(Extras.xUnite(bgSand0.getWidth()), Extras.yUnite(bgSand0.getHeight()));
		bgSand0.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bgSand1 = new ScrollingSprite(new Texture(Gdx.files.internal("bgSand1.png")));
		bgSand1.setSize(Extras.xUnite(bgSand1.getWidth() / 2), Extras.yUnite(bgSand1.getHeight() / 2));
		bgSand1.setPosition(0, Extras.yUnite(70));
		bgSand1.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bgSand2 = new ScrollingSprite(new Texture(Gdx.files.internal("bgSand2.png")));
		bgSand2.setSize(Extras.xUnite(bgSand2.getWidth() / 2), Extras.yUnite(bgSand2.getHeight() / 2));
		bgSand2.setPosition(0, Extras.yUnite(70));
		bgSand2.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bgSand3 = new ScrollingSprite(new Texture(Gdx.files.internal("bgSand3.png")));
		bgSand3.setSize(Extras.xUnite(bgSand3.getWidth() / 2), Extras.yUnite(bgSand3.getHeight() / 2));
		bgSand3.setPosition(0, Extras.yUnite(70));
		bgSand3.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bgSquare = new Sprite(new Texture(Gdx.files.internal("square.png")));
		bgSquare.setSize(Extras.xUnite(256), Extras.yUnite(256));
		bgSquare.setPosition(Extras.xUnite(Extras.xUnite(400 - bgSquare.getWidth() / 2)),
				Extras.yUnite(Extras.yUnite(480 - 100 - bgSquare.getHeight())));
		bgSquare.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		ice1 = new Sprite(new Texture(Gdx.files.internal("ice1.png")));
		ice1.setSize(Extras.xUnite(70), Extras.yUnite(70));
		ice2 = new Sprite(new Texture(Gdx.files.internal("ice2.png")));
		ice2.setSize(Extras.xUnite(70), Extras.yUnite(70));

		// Button PAUSE
		Sprite pauseButtonSprite = new Sprite(new Texture(Gdx.files.internal("pause.png")));
		pauseButtonSprite.setPosition(Extras.xUnite(710), Extras.yUnite(480 - 80));
		pauseButtonSprite.setSize(Extras.xUnite(70), Extras.yUnite(70));
		Sprite pauseButtonSpriteTouched = new Sprite(pauseButtonSprite);
		pauseButtonSprite.setAlpha(0.2f);
		pauseButton = new PauseButton(pauseButtonSprite, pauseButtonSpriteTouched);

	}

	@Override
	public void render(float delta) {
		time += delta;

		Gdx.gl.glClearColor(255, 255, 255, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		bg.draw(batch);
		bgSand3.animate(batch, 0.2f);
		bgSand2.animate(batch, 0.22f);
		bgSand1.animate(batch, 0.3f);
		bgSand0.draw(batch);
		bgSquare.draw(batch);
		pauseButton.draw(game, batch);
		// LOGIC

		showCubes(b.getBoard(), batch);
		if (!b.isGameOver()) {
			if (Gdx.input.isTouched()) {
				System.out.print("Your move: ");
				int moveKey = getTouchedKey();
				if (moveKey != 0) {
					Point userMove = Board.intToPoint(moveKey);
					System.out.println(moveKey);

					b.placeAMove(userMove, 2); // 2 for O and O is the user
					// add sprite here
					b.displayBoard();

					// COMPUTER
					if (b.isGameOver()) {
						// gameOver
					} else {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						b.callMinimax(0, 1);
						for (PointsAndScores pas : b.rootsChildrenScores) {
							System.out.println("Point: " + pas.point + " Score: " + pas.score);
						}
						b.placeAMove(b.returnBestMove(), 1);
						b.displayBoard();
					}
				}
			}
		} else if (b.hasXWon()) {
			System.out.println("You lost!");
		} else if (b.hasOWon()) {
			System.out.println("You win!");
		} else {
			System.out.println("It's a draw!");
		}

		batch.end();
	}

	public void showCubes(int[][] board, SpriteBatch batch) {

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				Sprite sp = new Sprite();
				if (board[i][j] == 2) {
					sp = new Sprite(ice2);
				} else if (board[i][j] == 1) {
					sp = new Sprite(ice1);
				}

				if (board[i][j] == 2 || board[i][j] == 1)
					switch (i) {
					case 0:
						switch (j) {
						case 0: {
							sp.setPosition(290, 260 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 1: {
							sp.setPosition(360, 260 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 2: {
							sp.setPosition(430, 260 + 70 / 2);
							sp.draw(batch);
						}
							break;
						}
						break;

					case 1:
						switch (j) {
						case 0: {
							sp.setPosition(290, 185 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 1: {
							sp.setPosition(360, 185 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 2: {
							sp.setPosition(430, 185 + 70 / 2);
							sp.draw(batch);
						}
							break;
						}
						break;

					case 2:
						switch (j) {
						case 0: {
							sp.setPosition(290, 123 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 1: {
							sp.setPosition(360, 123 + 70 / 2);
							sp.draw(batch);
						}
							break;

						case 2: {
							sp.setPosition(430, 123 + 70 / 2);
							sp.draw(batch);
						}
							break;
						}
						break;

					}
			}
		}

	}

	public int getTouchedKey() {
		if (Gdx.input.getX() > 290 && Gdx.input.getX() <= 350 && Gdx.input.getY() > 251 && Gdx.input.getY() < 330)
			return 1;
		else if (Gdx.input.getX() >= 351 && Gdx.input.getX() <= 435 && Gdx.input.getY() > 251 && Gdx.input.getY() < 330)
			return 2;
		else if (Gdx.input.getX() > 435 && Gdx.input.getX() < 500 && Gdx.input.getY() > 251 && Gdx.input.getY() < 330)
			return 3;

		else if (Gdx.input.getX() > 290 && Gdx.input.getX() <= 350 && Gdx.input.getY() > 185 && Gdx.input.getY() < 251)
			return 4;
		else if (Gdx.input.getX() >= 351 && Gdx.input.getX() <= 435 && Gdx.input.getY() > 185 && Gdx.input.getY() < 251)
			return 5;
		else if (Gdx.input.getX() > 435 && Gdx.input.getX() < 500 && Gdx.input.getY() > 185 && Gdx.input.getY() < 251)
			return 6;
		else if (Gdx.input.getX() > 290 && Gdx.input.getX() <= 350 && Gdx.input.getY() > 123 && Gdx.input.getY() < 185)
			return 7;
		else if (Gdx.input.getX() >= 351 && Gdx.input.getX() <= 435 && Gdx.input.getY() > 123 && Gdx.input.getY() < 185)
			return 8;
		else if (Gdx.input.getX() > 435 && Gdx.input.getX() < 500 && Gdx.input.getY() > 123 && Gdx.input.getY() < 185)
			return 9;

		else
			return 0;
	}

	@Override
	public void resize(int width, int height) {
		show();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
