package zelda;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class World {
	
	private static int WIDTH = Constants.WINDOW_WIDTH;
	private static int HEIGHT = Constants.WINDOW_HEIGHT;
	
	private static int TOTAL_BLOCKS_BY_WINDOW_SIZE = Constants.WINDOW_WIDTH / 32;
	private static List<Block> blocks = new ArrayList<Block>();
	
	public World() {
		// Top wall.
		for(int x = 0; x < TOTAL_BLOCKS_BY_WINDOW_SIZE; x++) {
			blocks.add(new Block(x * 32, 0));
		}
		
		// Bottom wall.
		for(int x = 0; x < TOTAL_BLOCKS_BY_WINDOW_SIZE; x++) {
			blocks.add(new Block(x * 32, HEIGHT - 32));
		}

		// Left wall.
		for(int y = 0; y < TOTAL_BLOCKS_BY_WINDOW_SIZE; y++) {
			blocks.add(new Block(0, y * 32));
		}
		
		// Right wall.
		for(int y = 0; y < TOTAL_BLOCKS_BY_WINDOW_SIZE; y++) {
			blocks.add(new Block(WIDTH - 32, y * 32));
		}
		
		// Random walls.
		int randomQuatityWalls = new Random().nextInt(10);

		for(int walls = 0; walls < randomQuatityWalls; walls++) {
			int randomDirection = new Random().nextInt(10);
			int randomQuantityTiles = new Random().nextInt(5);
			
			int randomStartX = new Random().nextInt(WIDTH - 64);
			int x = randomStartX < 64 ? 64 : randomStartX;
			
			int randomStartY = new Random().nextInt(HEIGHT - 64);
			int y = randomStartY < 64 ? 64 : randomStartY;
			
			if (randomDirection > 5) {  // create vertically.
				for(int i = 0; i < randomQuantityTiles; i++) {
					blocks.add(new Block(x, y + (i * 32)));
				}
			} else { // create horizontally.
				for(int i = 0; i < randomQuantityTiles; i++) {
					blocks.add(new Block(x + (i * 32), y));
				}
			}
		}
	}
	
	public static boolean isColliding(int x, int y) {
		for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			if (block.intersects(new Rectangle(x, y, 32, 32))) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isColliding(int x, int y, int w, int h) {
		for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			if (block.intersects(new Rectangle(x, y, w, h))) {
				return true;
			}
		}
		
		return false;
	}
	
	public void render(Graphics graphics) {
		for (Iterator<Block> i = blocks.iterator(); i.hasNext();) {
			Block block = (Block) i.next();
			block.render(graphics);
		}
	}
}
