import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Editor extends JPanel implements ActionListener, MouseListener {

	
	public HashMap<String, Integer> options = new HashMap<String, Integer>();
	public int selectedId = 0; //NULL
	public Level level;
		
    JFrame f;
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
    	options.put("Orange Brick", 1);
    	options.put("Brick", 2);
    	options.put("Lucky Block", 3);
    }


    public void initPanel(){
       JPanel panel = new JPanel();
       panel.setBackground(Color.cyan);
       panel.setSize(600, 50);
       panel.setLocation(100, 0);
       
       
       options.forEach((String name, Integer id)->{
    	   JButton btn = new JButton(name);
    	   btn.addActionListener(this);
    	   panel.add(btn);
       });
       
       f.add(panel);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);  
        level.paint(g);

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
        
        
        options.forEach((String name, Integer id)->{
     	   if (event.equals(name)) {
     		   selectedId = id;
     	   }
        });
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println();
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		
		HashMap<String, Integer> blockInfo = new HashMap<String, Integer>();
		blockInfo.put("id", selectedId);
		blockInfo.put("x", Level.pTc(p.x, 32));
		blockInfo.put("y", Level.pTc(p.y, 32) - 2);
		
		ArrayList<HashMap<String, Integer>> currentLayout = level.getCurrentLayout();
		currentLayout.add(blockInfo);
		level.updateBlockLayout(currentLayout);
		level.loadBlocks();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
