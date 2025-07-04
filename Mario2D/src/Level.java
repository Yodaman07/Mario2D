import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Level {

	private String type;
	private String name;
	private int id;
	private ArrayList<HashMap<String, Integer>> blockLayout;
	private ArrayList<HashMap<String, Integer>> entityLayout;
	// pre decode

	// post decode
	private transient ArrayList<StaticTexture> blocks = new ArrayList<StaticTexture>();
	private transient ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public transient Mario mario;
	public transient MarioYoshi mYoshi;
	public transient Yoshi yoshi;
	public transient Flag flag;
	
	private transient AffineTransform tx;
	private transient Image bg = getImage("/imgs/Background1.png");

	// transient means it won't be serialized

	@Override
	public String toString() {
		return "Level [type=" + type + ", name=" + name + ", id=" + id + ", blockLayout=" + blockLayout
				+ ", entityLayout=" + entityLayout + "]";
	}

	public Level() {
		
	}
	// Load level with LevelLoader

	public Level(String type, String name, int id) {
		this.type = type;
		this.name = name;
		this.id = id;

		blockLayout = new ArrayList<HashMap<String, Integer>>();
		entityLayout = new ArrayList<HashMap<String, Integer>>();
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		init(0, 0); 				//initialize the location of the image
	}// When building the level via editor

	public void paint(Graphics g, int c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(c, 0);
		g2.drawImage(bg, tx, null);

		for (StaticTexture b : blocks) {
			b.paint(g2);
		}
		for (Enemy e : enemies) {
			e.paint(g2);
		}

		if (mario != null) {
			mario.paint(g2);
		}
		if (mYoshi != null && mYoshi.isPlaying()) {
			mYoshi.paint(g2);
		}
		if (yoshi != null) {
			yoshi.paint(g2);
		}
		if(flag != null) {
			flag.paint(g2);
		}
		
	}

	public void loadBlocks() {
		blocks.clear(); // Remember to clear the arrayList before you load it
		for (HashMap<String, Integer> block : blockLayout) {
			int x = block.get("x");
			int y = block.get("y");
			switch (block.get("id")) {
				case 1: // Orange_Brick.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Orange_Brick.png"));
					break;
				case 2: // Brick.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Brick.png"));
					break;
				case 3: // Luck_Block.png
					blocks.add(new StaticTexture(Level.cTp(x, 32), Level.cTp(y, 32), "/imgs/Lucky_Block.png"));
					break;
			}
		}
	}

	public void loadEntities() {
		enemies.clear();
		mario = null;
		yoshi = null;
		mYoshi = null;
		flag = null;
		for (HashMap<String, Integer> entity : entityLayout) {
			int x = entity.get("x");
			int y = entity.get("y");

			switch (entity.get("id")) {
				case 0: // Mario
					int jump = entity.get("jump");
					mario = new Mario(Level.cTp(x, 32), Level.cTp(y, 32), 20);
					break;
				case 1: // Goomba
					int velocity = entity.get("velocity");
					int dist = entity.get("walk_distance");
					enemies.add(new Goomba(Level.cTp(x, 32), Level.cTp(y, 32), velocity, dist));
					break;
				case 2: 
					mYoshi = new MarioYoshi(Level.cTp(x, 32), Level.cTp(y, 32), 25);
					break;
				case 3:
					int velocity1 = entity.get("velocity");
					int dist1 = entity.get("walk_distance");
					yoshi = new Yoshi(Level.cTp(x, 32), Level.cTp(y, 32), velocity1, dist1);
					break;
				case 4:
					flag = new Flag(Level.cTp(x, 32), Level.cTp(y, 32));
					break;
			}

		}

	}

	// TODO: ADD ERROR THROWING
	// Blocks MUST be loaded beforehand
	public ArrayList<StaticTexture> getBlocks() {return blocks;}

	public ArrayList<Enemy> getEnemies() {return enemies;}

	public void overwriteBlockLayout(ArrayList<HashMap<String, Integer>> newLayout) {blockLayout = newLayout;}
	public ArrayList<HashMap<String, Integer>> getBlockLayout() {return blockLayout;}

	// Will OVERWRITE the current block and entity layout - To be used in the editor
	public void overwriteEntityLayout(ArrayList<HashMap<String, Integer>> newEntites) {entityLayout = newEntites;}

	public ArrayList<HashMap<String, Integer>> getEntityLayout() {return entityLayout;}

	public void makeMarYoshi() {
		mario.setVx(0);
		mYoshi.setPlaying(true);
		mYoshi.setX(mario.getX());
		mYoshi.setY(mario.getY());
		mYoshi.setAccel(3);
		yoshi.setX(-100);
		yoshi.setY(-500);
		mario.setJump(0);
		mario.setAccel(0);
		yoshi.setAccel(0);
		mario.setX(-100);
		mario.setY(-500);
	}

	public void remakeMario() {
		mYoshi.setPlaying(false);
		yoshi.setX(mYoshi.getX());
		yoshi.setY(mYoshi.getY() - 10);
		mario.setAccel(3);
		yoshi.setAccel(3);
		mario.setX(mYoshi.getX());
		mario.setY(mYoshi.getY() + 10);

		mYoshi.setAccel(0);
		mYoshi.setJump(0);
		mYoshi.setVx(0);
		mYoshi.setX(-100);
		mYoshi.setY(-500);

		yoshi.setFalling(true);
		mario.setFalling(false);
		mario.jump();

	}

	public String getName() {return this.name;}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Level.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

	
	
	public static int cTp(int coord, int scaler) { // map coordinates to pixel values --> this game will use a "scaler"
													// of 32 pixels
		// CoordToPixel
		// The frame will start at 0,0 being the top left and going to the right and
		// down as the coords increase
		return coord * scaler;
	}

	public static int pTc(int pixel, int scaler) { // maps pixel values to coordinate values
		return (pixel / scaler);
	}

}
