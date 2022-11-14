package zelda;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Enemy extends Rectangle {

	private BufferedImage[] FRONT;
	private BufferedImage[] BACK;
	private BufferedImage[] LEFT;
	private BufferedImage[] RIGHT;
	private BufferedImage LAST_DIRECTION;

	private int speed = Constants.PLAYER_SPEED / 2;
	private int currentAnimation = 0;
	private int elapsedAnimationTime = 0;
	private int frameAnimationSpeed = Constants.ANIMATION_SPEED;

	public boolean right, up, down, left, attack = false;
	private boolean movingRight, movingUp, movingDown, movingLeft = false;

	public Enemy(int x, int y) {
		super(x, y, 32, 32);
		loadSprites();
	}

	public void attack(List<Projectile> projectiles) {
		Projectile projectile = new Projectile(x, y);

		if (movingRight) projectile.right = true;
		if (movingLeft) projectile.left = true;
		if (movingUp) projectile.up = true;
		if (movingDown) projectile.down = true;
		
		projectiles.add(projectile);
	}
	
	public void tick() {
		followPlayer();
		controlEntityMovement();
	}

	public void render(Graphics graphics) {
		// Prevent animation when colliding with some block.
//		if (isColliding()) {
//			graphics.drawImage(LAST_DIRECTION, x, y, 32, 32, null);
//			return;
//		}
		
		// Control moving animation.
		if (down) {
			graphics.drawImage(FRONT[currentAnimation], x, y, 32, 32, null);
			LAST_DIRECTION = FRONT[currentAnimation];
			return;
		}
		
		if (up) {
			graphics.drawImage(BACK[currentAnimation], x, y, 32, 32, null);
			LAST_DIRECTION = BACK[currentAnimation];
			return;
		}
		
		if (right) {
			graphics.drawImage(RIGHT[currentAnimation], x, y, 32, 32, null);
			LAST_DIRECTION = RIGHT[currentAnimation];
			return;
		}
		
		if (left) {
			graphics.drawImage(LEFT[currentAnimation], x, y, 32, 32, null);
			LAST_DIRECTION = LEFT[currentAnimation];
			return;
		}
		
		if (!right || !left || !up || !down) {
			graphics.drawImage(LAST_DIRECTION, x, y, 32, 32, null);
			return;
		}
	}

	private void loadSprites() {
		FRONT = new BufferedImage[2];
		FRONT[0] = Spritesheet.getSprite(234, 208, 16, 16);
		FRONT[1] = Spritesheet.getSprite(250, 208, 16, 16);
		
		BACK = new BufferedImage[2];
		BACK[0] = Spritesheet.getSprite(303, 208, 16, 16);
		BACK[1] = Spritesheet.getSprite(319, 208, 16, 16);
		
		RIGHT = new BufferedImage[2];
		RIGHT[0] = Spritesheet.getSprite(268, 208, 16, 16);
		RIGHT[1] = Spritesheet.getSprite(284, 208, 16, 16);
		
		LEFT = new BufferedImage[2];
		LEFT[0] = Spritesheet.getSprite(336, 208, 16, 16);
		LEFT[1] = Spritesheet.getSprite(352, 208, 16, 16);
		
		LAST_DIRECTION = FRONT[0];
	}

	private void controlEntityMovement() {
		if (right && !World.isColliding(x + speed, y)) {
			x += speed;

			movingRight = true;
			movingLeft = false;
			movingUp = false;
			movingDown = false;
		} else if (left && !World.isColliding(x - speed, y)) {
			x -= speed;

			movingRight = false;
			movingLeft = true;
			movingUp = false;
			movingDown = false;
		}

		if (up && !World.isColliding(x, y - speed)) {
			y -= speed;

			movingRight = false;
			movingLeft = false;
			movingUp = true;
			movingDown = false;
		} else if (down && !World.isColliding(x, y + speed)) {
			y += speed;

			movingRight = false;
			movingLeft = false;
			movingUp = false;
			movingDown = true;
		}

		// Control player movement animation.
		if (right || left || up || down) {
			elapsedAnimationTime++;
			if (elapsedAnimationTime == frameAnimationSpeed) {
				currentAnimation = currentAnimation == 1 ? 0 : 1;
				elapsedAnimationTime = 0;
			}
		}
	}
	
	private void followPlayer() {
		Player player = Game.player;
		
		if (x == player.x) {
			left = false;
			right = false;
		} else if (x < player.x) {
			left = false;
			right = true;
		} else {
			left = true;
			right = false;
		}
		
		if (y == player.y) {
			up = false;
			down = false;
		} else if (y < player.y) {
			up = false;
			down = true;
		} else {
			up = true;
			down = false;
		}
	}

	private boolean isColliding() {
		return World.isColliding(x + speed, y, width, height) 
				|| World.isColliding(x - speed, y, width, height) 
				|| World.isColliding(x, y - speed, width, height) 
				|| World.isColliding(x, y + speed, width, height);
	}
}
