package zelda;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

	private static int WIDTH = Constants.WINDOW_WIDTH;
	private static int HEIGHT = Constants.WINDOW_HEIGHT;
	
	public static List<Projectile> projectiles = new ArrayList<Projectile>();
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	
	private World world;
	public static Player player;
	
	public Game() {
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		new Spritesheet();
		world = new World();
		player = new Player(32, 32);
		
		for(int i = 0; i < Constants.TOTAL_ENEMIES; i++) {
			int randomX = 32 + new Random().nextInt(WIDTH - 96);
			int randomY = 32 + new Random().nextInt(HEIGHT - 96);
			
			enemies.add(new Enemy(randomX, randomY));
		}
	}
	
	public void tick() {
		player.tick();
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).tick();
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).tick();
		}
	}
	
	public void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.setColor(new Color(0, 135, 13));
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		player.render(graphics);
		world.render(graphics);
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(graphics);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(graphics);
		}
		
		bufferStrategy.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.attack(projectiles);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				tick();
				render();
				
				// 60 FPS.
				Thread.sleep(1000/60);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
