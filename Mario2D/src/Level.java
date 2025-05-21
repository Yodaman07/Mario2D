import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Level {
	
	private String type;
	private String name;
	private int id;
	private ArrayList<HashMap<String, Integer>> layout;
	private transient ArrayList<StaticTexture> blocks = new ArrayList<StaticTexture>();
	//transient means it won't be serialized
	
	public Level() {}
	//Load level with LevelLoader

	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	
		for  (StaticTexture b: blocks) {
			b.paint(g2);
		}
		
	}
	
	public void mapBlocks() {
		
		for (HashMap<String, Integer> block : layout) {
			switch (block.get("id")){
				case 1: //Orange_Brick.png
					blocks.add(new StaticTexture(Level.cTp(block.get("x"), 32), Level.cTp(block.get("y"), 32), "/imgs/Orange_Brick.png"));
					break;
				case 2:
					break;
				case 3:
					break;
			}
		}
	}
	
	@Override
	public String toString() {
		return "Level [type=" + type + ", name=" + name + ", id=" + id + ", layout=" + layout + "]";
	}
	
	public static int cTp(int coord, int scaler) { //map coordinates to pixel values --> this game will use a "scaler" of 32 pixels
		//CoordToPixel 
		//The frame will start at 0,0 being the top left and going to the right and down as the coords increase
		return coord*scaler;
	}
}


