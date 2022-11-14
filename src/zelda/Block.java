package zelda;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends Rectangle {
	
	private BufferedImage SPRITE;
	
	public Block(int x, int y) {
		super(x, y, 32, 32);
		SPRITE = Spritesheet.getSprite(314, 185, 16, 16);
	}
	
	public void render(Graphics graphics) {
		graphics.drawImage(SPRITE, x, y, 32, 32, null);
	}
}
