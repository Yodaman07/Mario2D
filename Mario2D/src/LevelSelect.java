import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LevelSelect extends JPanel{
	
	
	
	public static ArrayList<Point> levelRects;
	public static ArrayList<String> levelPaths;;
	
	private Image levelsImages, bgImage;
	private AffineTransform tx;
	 
	Font myFont = new Font("Courier", Font.BOLD, 14);
	
	
	public LevelSelect() {
		levelRects = new ArrayList<Point>();
		levelPaths = getLevelPaths();

		levelsImages = getImage("/imgs/BlankButton.png");
		bgImage = getImage("/imgs/LevelScreen.png");
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		init(0, 0); 				//initialize the location of the image
		
		int offset = 0;
		for (String level: levelPaths) {
			levelRects.add(new Point(225+offset, 300));	
			offset+=130;
		}
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(myFont);
		
		init(0,0);
		g2.drawImage(bgImage, tx, null);
		
		for (int i = 0; i < levelPaths.size(); i++) {
			init(levelRects.get(i).x, levelRects.get(i).y);
			g2.drawImage(levelsImages, tx, null);
			String name = levelPaths.get(i);//substring gets rid of the .json
			g2.drawString(name.substring(0, name.length()-5), levelRects.get(i).x+10, levelRects.get(i).y+25+5);
		}
		
	}
	
	
	public static ArrayList<String> getLevelPaths() {
		ArrayList<String> result = new ArrayList<String>();
		
		//https://www.geeksforgeeks.org/how-to-list-all-files-in-a-directory-in-java/
	    // Path of the specific directory 
	    String directoryPath = "src/levels";
	    File directory = new File(directoryPath);
	    File[] files = directory.listFiles();
	    
	    if (files != null) {
	    	for (File f: files) {
	    		if (!f.getName().equals(".DS_Store")) {
	    			result.add(f.getName());
	    		}
	    	}
	    }
	    return result;
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = LevelSelect.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
	}	
	

}
