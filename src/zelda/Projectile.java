package zelda;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectile extends Rectangle {

	private BufferedImage RIGHT;
	private BufferedImage LEFT;
	private BufferedImage UP;
	private BufferedImage DOWN;
	private BufferedImage[] EXPLOSION;
	
	private int speed = 8;
	private int currentAnimation = 0;
	private int elapsedAnimationTime = 0;
	private int frameAnimationSpeed = Constants.ANIMATION_SPEED / 5;
	
	public boolean right, up, down, left = false;
	
	public Projectile(int x, int y) {
		super(x + 16, y + 16, 5, 5);
		loadSprits();
	}
	
	public void tick() {
		if (right && !World.isColliding(x + speed, y, width, height)) {
			x+= speed;
		} else if(left && !World.isColliding(x - speed, y, width, height)) {
			x-= speed;
		}
		
		if (up && !World.isColliding(x, y - speed, width, height)) {
			y-= speed;
		} else if(down && !World.isColliding(x, y + speed, width, height)) {
			y+= speed;
		}
		
		// Control projectile explosion animation.
		if (isColliding()) onCollide();
	}
	
	public void render(Graphics graphics) {
		if (isColliding()) {
			int width = EXPLOSION[currentAnimation].getWidth();
			int height = EXPLOSION[currentAnimation].getHeight();
			int centerX = x - (width / 2);
			int centerY = y - (height / 2);
			
			graphics.drawImage(EXPLOSION[currentAnimation], centerX, centerY, width, height, null);
			return;
		}
		
		if (right) {
			graphics.drawImage(RIGHT, x, y, width, height, null);
			return;
		}
		
		if (left) {
			graphics.drawImage(LEFT, x, y, width, height, null);
			return;
		}
		
		if (up) {
			graphics.drawImage(UP, x, y, width, height, null);
			return;
		}
		
		if (down) {
			graphics.drawImage(DOWN, x, y, width, height, null);
			return;
		}
	}
	
	public void onCollide() {
		elapsedAnimationTime++;
		if (elapsedAnimationTime == frameAnimationSpeed) {
			currentAnimation++;
			elapsedAnimationTime = 0;
			
			if (currentAnimation == EXPLOSION.length) {
				Game.projectiles.remove(this);
			}
		}
	}
	
	private void loadSprits() {
		RIGHT = Spritesheet.getSprite(333, 185, 5, 5);
		LEFT = Spritesheet.getSprite(350, 185, 5, 5);
		UP = Spritesheet.getSprite(339, 185, 5, 5);
		DOWN = Spritesheet.getSprite(345, 185, 5, 5);
		
		EXPLOSION = new BufferedImage[5];
		EXPLOSION[0] = Spritesheet.getSprite(129, 207, 7, 7);
		EXPLOSION[1] = Spritesheet.getSprite(140, 207, 9, 11);
		EXPLOSION[2] = Spritesheet.getSprite(155, 207, 11, 13);
		EXPLOSION[3] = Spritesheet.getSprite(171, 208, 13, 15);
		EXPLOSION[4] = Spritesheet.getSprite(189, 208, 16, 18);
	}
	
	private boolean isColliding() {
		return World.isColliding(x + speed, y, width, height) 
				|| World.isColliding(x - speed, y, width, height) 
				|| World.isColliding(x, y - speed, width, height) 
				|| World.isColliding(x, y + speed, width, height);
	}
}
