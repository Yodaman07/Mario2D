import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Level {
	
	private String type;
	private String name;
	private int id;
	private ArrayList<HashMap<String, Integer>> blockLayout;
	private ArrayList<HashMap<String, Integer>> entities;
	//pre decode
	
	
	//post decode
	private transient ArrayList<StaticTexture> blocks = new ArrayList<StaticTexture>();
	private transient ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private transient Mario mario;
	
	//transient means it won't be serialized
	
	public Level() {}
	//Load level with LevelLoader

	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	
		for  (StaticTexture b: blocks) {b.paint(g2);}
		
		for  (Enemy e: enemies) {e.paint(g2);}
		
	}
	
	public void loadBlocks() {
		
		for (HashMap<String, Integer> block : blockLayout) {
			int x = block.get("x");
			int y = block.get("y");
			switch (block.get("id")){
				case 1: //Orange_Brick.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Orange_Brick.png"));
					break;
				case 2:
					break;
				case 3:
					break;
			}
		}
	}
	
	public void loadEntities() {
		for (HashMap<String, Integer> entity : entities) {
			int x = entity.get("x");
			int y = entity.get("y");
			if (entity.get("type") == 11) { //type 11 is enemy
				
				switch (entity.get("id")){
					case 1: //Goomba
						enemies.add(new Goomba(Level.cTp(x, 32), Level.cTp(y, 32), 2, 50));
						break;
					case 2:
						break;
					case 3:
						break;
				}
				
			}else if (entity.get("type") == 10) { //type 10 is mario
				
				
			}
	
		}
		
	}
	
	
	
	@Override
	public String toString() {
		return "Level [type=" + type + ", name=" + name + ", id=" + id + ", blockLayout=" + blockLayout
				+ ", entities=" + entities + "]";
	}


	public static int cTp(int coord, int scaler) { //map coordinates to pixel values --> this game will use a "scaler" of 32 pixels
		//CoordToPixel 
		//The frame will start at 0,0 being the top left and going to the right and down as the coords increase
		return coord*scaler;
	}
}


