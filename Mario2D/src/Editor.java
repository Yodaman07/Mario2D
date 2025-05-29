import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Editor extends JPanel implements ActionListener, MouseListener {

	
	public HashMap<String, Integer> blockOptions = new HashMap<String, Integer>();
	public int selectedId = 0; //NULL
	public MODE mode = MODE.ADD;
	public Level level;
		
    public JFrame f;
    public JPanel panel;
    
    public Editor(){
    	this.configHashMap();
    	
    	f = new JFrame("Level Editor");
        level = new Level("level", "anothertest", 102);
        initPanel();
    	
        
        f.setSize(800, 512); // init code
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(this);
        f.addMouseListener(this);
        f.setVisible(true);
        
        
        
        System.out.println("Initialized");
    }
    
    public void configHashMap() { //BASED OFF OF VALUES FROM LEVEL.JAVA
    	blockOptions.put("Orange Brick", 1);
    	blockOptions.put("Brick", 2);
    	blockOptions.put("Lucky Block", 3);
    }


    public void initPanel(){
       panel = new JPanel();
       panel.setBackground(Color.cyan);
       panel.setSize(500, 50);
       panel.setLocation(100, 0);
       
       
       //BLOCK OPTION
       blockOptions.forEach((String name, Integer id)->{
    	   JButton btn = new JButton(name);
    	   btn.addActionListener(this);
    	   panel.add(btn);
       });
       
       //OPTIONS BAR
       JPanel optionPanel = new JPanel();
       optionPanel.setBackground(Color.pink);
       optionPanel.setSize(100,75);
       optionPanel.setLocation(700,0);
       
       JButton save = new JButton("Save");
       JButton delete = new JButton("Delete");
       save.addActionListener(this);
       delete.addActionListener(this);
       optionPanel.add(save);
       optionPanel.add(delete);
       
       
       f.add(optionPanel);
       f.add(panel);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);  
        level.paint(g);

        
        g.setColor(Color.black);
        for (int i = 0; i < Frame.width; i+=32) {
        	g.drawLine(i, 0, i, Frame.height);
        }
        
        for (int i = 0; i < Frame.height; i+=32) {
        	g.drawLine(0, i, Frame.width, i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
    	repaint();
    	f.repaint();
        String event = e.getActionCommand();
        System.out.println(event);
        
        
        blockOptions.forEach((String name, Integer id)->{
     	   if (event.equals(name)) {
     		   mode = MODE.ADD;
     		   selectedId = id;
     	   }
        });
        
        if (event.equals("Delete")) {
        	mode = MODE.DELETE;
        }
    }

	@Override
	public void mousePressed(MouseEvent e) {
//		//You need to click twice whenever you change blocks, i'm not fully sure why
//		Point p = MouseInfo.getPointerInfo().getLocation();
//		int x = Level.pTc(p.x, 32);
//		int y = Level.pTc(p.y, 32) - 2;
//		System.out.println("A");
//		
//		if (mode.equals(MODE.ADD)) {
//			HashMap<String, Integer> blockInfo = new HashMap<String, Integer>();
//			blockInfo.put("id", selectedId);
//			blockInfo.put("x", x);
//			blockInfo.put("y", y);
//			
//			ArrayList<HashMap<String, Integer>> currentLayout = level.getCurrentLayout();
//			currentLayout.add(blockInfo);
//			level.overwriteBlockLayout(currentLayout);
//			level.loadBlocks();
//			System.out.println(level.getBlocks());
//		}else if (mode.equals(MODE.DELETE)) {
//			System.out.println("Delete at x: " + x + " and y: " + y );
//			ArrayList<HashMap<String, Integer>> currentLayout = level.getCurrentLayout();
//			
//			int index = getIndexToRemove(currentLayout, x, y);
//			if (index != -1) {
//				System.out.println(currentLayout);
//				currentLayout.remove(index);
//				System.out.println(currentLayout);
//			}
//			
//			level.overwriteBlockLayout(currentLayout);
//			level.loadBlocks();
//			System.out.println(level.getBlocks());
//		}
//		
//		f.repaint();
//		repaint();
	}
	
	private int getIndexToRemove(ArrayList<HashMap<String, Integer>> layout, int x, int y) {
		
		for (int i = 0; i < layout.size(); i++) {
			if ((layout.get(i).get("x") == x) && (layout.get(i).get("y") == y)) {
				System.out.println("MATCH");
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//You need to click twice whenever you change blocks, i'm not fully sure why
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x = Level.pTc(p.x, 32);
		int y = Level.pTc(p.y, 32) - 2;
		System.out.println("A");
		
		if (mode.equals(MODE.ADD)) {
			HashMap<String, Integer> blockInfo = new HashMap<String, Integer>();
			blockInfo.put("id", selectedId);
			blockInfo.put("x", x);
			blockInfo.put("y", y);
			
			ArrayList<HashMap<String, Integer>> currentLayout = level.getCurrentLayout();
			currentLayout.add(blockInfo);
			level.overwriteBlockLayout(currentLayout);
			level.loadBlocks();
			System.out.println(level.getBlocks());
		}else if (mode.equals(MODE.DELETE)) {
			System.out.println("Delete at x: " + x + " and y: " + y );
			ArrayList<HashMap<String, Integer>> currentLayout = level.getCurrentLayout();
			
			int index = getIndexToRemove(currentLayout, x, y);
			if (index != -1) {
				System.out.println(currentLayout);
				currentLayout.remove(index);
				System.out.println(currentLayout);
			}
			
			level.overwriteBlockLayout(currentLayout);
			level.loadBlocks();
			System.out.println(level.getBlocks());
		}
		
		f.repaint();
//		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}


enum MODE{
	ADD,
	MODIFY,
	DELETE
}
