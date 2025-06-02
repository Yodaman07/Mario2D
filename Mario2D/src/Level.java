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
	private ArrayList<HashMap<String, Integer>> entityLayout;
	//pre decode
	
	
	//post decode
	private transient ArrayList<StaticTexture> blocks = new ArrayList<StaticTexture>();
	private transient ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public transient Mario mario;
	public transient Yoshi yoshi;
	//transient means it won't be serialized
	
	public Level() {}
	//Load level with LevelLoader
	
	public Level(String type, String name, int id) {
		this.type = type;
		this.name = name;
		this.id = id;
		
		blockLayout = new ArrayList<HashMap<String, Integer>>();
		entityLayout = new ArrayList<HashMap<String, Integer>>();
	}//When building the level via editor

	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	 
		for  (StaticTexture b: blocks) {b.paint(g2);}
		for  (Enemy e: enemies) {e.paint(g2);}
		
		if (mario != null) {
			mario.paint(g2);	
		}
		if (yoshi != null) {
			yoshi.paint(g2);
		}
	}
	
	public void loadBlocks() {
		blocks.clear(); //Remember to clear the arrayList before you load it
		for (HashMap<String, Integer> block : blockLayout) {
			int x = block.get("x");
			int y = block.get("y");
			switch (block.get("id")){
				case 1: //Orange_Brick.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Orange_Brick.png"));
					break;
				case 2: //Brick.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Brick.png"));
					break;
				case 3: //Luck_Block.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Lucky_Block.png"));
					break;
			}
		}
	}
	
	public void loadEntities() {
		for (HashMap<String, Integer> entity : entityLayout) {
			int x = entity.get("x");
			int y = entity.get("y");
			
			switch (entity.get("id")){
				case 0: //Mario
					int jump = entity.get("jump");
					mario = new Mario(Level.cTp(x, 32), Level.cTp(y, 32), 20);
					break;
				case 1: //Goomba
					int velocity = entity.get("velocity");
					int dist = entity.get("walk_distance");
					enemies.add(new Goomba(Level.cTp(x, 32), Level.cTp(y, 32), velocity, dist));
					break;
				case 2:
					yoshi = new Yoshi(Level.cTp(x, 32), Level.cTp(y, 32), 25);
					break;
				case 3:
					break;
			}
	
		}
		
	}

	//TODO: ADD ERROR THROWING
	//Blocks MUST be loaded beforehand
	public ArrayList<StaticTexture> getBlocks(){ return blocks;}
	
	public void overwriteBlockLayout(ArrayList<HashMap<String, Integer>> newLayout) {blockLayout = newLayout;} 
	public ArrayList<HashMap<String, Integer>> getBlockLayout(){return blockLayout;}
	//Will OVERWRITE the current block and entity layout - To be used in the editor
	public void overwriteEntityLayout(ArrayList<HashMap<String, Integer>> newEntites) {entityLayout = newEntites;}
	public ArrayList<HashMap<String, Integer>> getEntityLayout(){return entityLayout;}
	
	
	public String getName() {return this.name;}
	@Override
	public String toString() {
		return "Level [type=" + type + ", name=" + name + ", id=" + id + ", blockLayout=" + blockLayout
				+ ", entities=" + entityLayout + "]";
	}


	public static int cTp(int coord, int scaler) { //map coordinates to pixel values --> this game will use a "scaler" of 32 pixels
		//CoordToPixel 
		//The frame will start at 0,0 being the top left and going to the right and down as the coords increase
		return coord*scaler;
	}
	
	public static int pTc(int pixel, int scaler) { //maps pixel values to coordinate values 
		return (pixel/scaler);
	}
}


