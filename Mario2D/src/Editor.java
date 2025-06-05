import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Editor extends JPanel implements ActionListener, MouseListener {

	private HashMap<String, Integer> blockOptions = new HashMap<String, Integer>();
	private HashMap<String, Integer> entityOptions = new HashMap<String, Integer>();
	private ArrayList<HashMap<String, Integer>> entityAttributes = new ArrayList<HashMap<String, Integer>>();

	private int selectedId = 0; // NULL
	private TYPE selectedType; // BLOCK or ENTITY

	private boolean hasBeenSaved = false;;

	private MODE mode = MODE.ADD;
	private Level level;

	private JFrame f;
	private JPanel panel;
	
	private int cameraX = 0; 

	public Editor() {

		this.configOptions();
		this.promptCreation();
		
		System.out.println("Initialized");
	}

	public void configOptions() { // BASED OFF OF VALUES FROM LEVEL.JAVA
		blockOptions.put("Orange Brick", 1);
		blockOptions.put("Brick", 2);
		blockOptions.put("Lucky Block", 3);

		entityOptions.put("Mario", 0);
		entityOptions.put("Goomba", 1);
		entityOptions.put("Yoshi", 3);
		entityOptions.put("Flag", 4);

		entityAttributes.add(0, new HashMap<>(Map.of("jump", 0))); // mario
		entityAttributes.add(1, new HashMap<>(Map.of("velocity", 0, "walk_distance", 0))); // goomba
		entityAttributes.add(2, new HashMap<String, Integer>()); //MARIO YOSHI (but he isn't used)
		entityAttributes.add(3, new HashMap<>(Map.of("velocity", 0, "walk_distance", 0))); // yoshi
		entityAttributes.add(4, new HashMap<String, Integer>()); // Flag
		
	}

	public void initPanel() {
		panel = new JPanel(); // BLOCK OPTION PANEL
		panel.setBackground(Color.cyan);
		panel.setSize(500, 50);
		panel.setLocation(100, 500);

		// BLOCK OPTION
		blockOptions.forEach((String name, Integer id) -> {
			JButton btn = new JButton(name);
			btn.addActionListener(this);
			panel.add(btn);
		});

		// OPTIONS BAR
		JPanel optionPanel = new JPanel();
		optionPanel.setBackground(Color.pink);
		optionPanel.setSize(100, 100);
		optionPanel.setLocation(700, 500);

		JButton save = new JButton("Save");
		JButton delete = new JButton("Delete");
		JButton close = new JButton("Close");
		save.addActionListener(this);
		delete.addActionListener(this);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f.dispose();
			}
		});
		
		optionPanel.add(save);
		optionPanel.add(delete);
		optionPanel.add(close);

		// Entity Panel
		JPanel entityPanel = new JPanel();
		entityPanel.setBackground(Color.GRAY);
		entityPanel.setSize(96, 350);
		entityPanel.setLocation(816, 40);
		entityOptions.forEach((String name, Integer id) -> {
			JButton btn = new JButton(name);
			btn.addActionListener(this);
			entityPanel.add(btn);
		});

		f.add(optionPanel);
		f.add(panel);
		f.add(entityPanel);
	}

	public void initFrame(String levelName) {
		f = new JFrame("Level Editor - Making: " + levelName);
		level = new Level("level", levelName, 102);

		initPanel();

		f.setSize(800 + 128, 512 + 128); // init code
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(this);
		f.addMouseListener(this);
		f.setVisible(true);
		
		
//		f.setFocusable(true); //https://forums.oracle.com/ords/apexds/post/mouselistener-working-but-not-keylistener-5214
	}

	public void promptCreation() {

		JFrame popUp = new JFrame("Create a New Level");
		JPanel panel = new JPanel();

		popUp.setSize(400, 100); // init code
		popUp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		popUp.setVisible(true);

		JTextField name = new JTextField("LevelName", 10);

		JButton btn = new JButton("Create Level");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initFrame(name.getText());
				popUp.dispose();
			}
		});

		panel.add(name);
		panel.add(btn);

		popUp.add(panel);
		popUp.pack();

	}

	public void initAttributePrompt(int id, int x, int y) { // will prompt entity info and spawn the entity after
		JFrame popUp = new JFrame("Fill out entity attributes");
		JPanel panel = new JPanel();

		popUp.setSize(400, 100); // init code
		popUp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		popUp.setVisible(true);

		ArrayList<JTextField> inputReferences = new ArrayList<JTextField>();
		// keep track of the input objects (they are in a for loop so you can't access
		// all immediately)

		entityAttributes.get(id).forEach((String val_name, Integer val) -> {
			JLabel attribute = new JLabel(val_name);
			JTextField value = new JTextField("value", 10);
			inputReferences.add(value);

			panel.add(attribute);
			panel.add(value);
		});

		JButton btn = new JButton("Submit");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.repaint();
				repaint();
				if (!inputReferences.isEmpty()) {
					Object[] keyArray = entityAttributes.get(id).keySet().toArray();
	
					for (int i = 0; i < keyArray.length; i++) {
						try {
							int value = Integer.valueOf(inputReferences.get(i).getText());
							entityAttributes.get(id).put(keyArray[i].toString(), value);
							System.out.println(entityAttributes);
							System.out.println("Information added");
							popUp.dispose();
						} catch (NumberFormatException exception) {
							System.out.println("NOT A VALID VALUE");
						}
					}
					// end of for loop
				}else {
					popUp.dispose();
				}

				addEntity(x, y);
				
			}
		});

		panel.add(btn);
		popUp.add(panel);
		popUp.pack();

	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		level.paint(g);
		g.translate(cameraX, 0);

		g.setColor(Color.black);
		for (int i = 0; i <= Frame.width; i += 32) {
			g.drawLine(i, 0, i, Frame.height - 32);
		}

		for (int i = 0; i < Frame.height; i += 32) {
			g.drawLine(0, i, Frame.width, i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { // I.E. a button press
		repaint();
		f.repaint();
		String event = e.getActionCommand();
		System.out.println(event + " event");

		if (blockOptions.containsKey(event)) { // Figures out what type is selected
			selectedType = TYPE.BLOCK;

			blockOptions.forEach((String name, Integer id) -> {
				if (event.equals(name)) {
					mode = MODE.ADD;
					selectedId = id;
				}
			});
		} else if (entityOptions.containsKey(event)) {
			selectedType = TYPE.ENTITY;

			entityOptions.forEach((String name, Integer id) -> {
				if (event.equals(name)) {
					mode = MODE.ADD;
					selectedId = id;
				}
			});
		}

		// NOTE: Add, delete, and save are protected and can't be the name of any block or entity
		if (event.equals("Delete")) { mode = MODE.DELETE;}

		if (event.equals("Save")) {
			this.hasBeenSaved = true;
			LevelLoader ll = new LevelLoader();
			ArrayList<HashMap<String, Integer>> layout = level.getEntityLayout();
			layout.add(new HashMap<>(Map.of("x", 100, "y", 100, "id", 2))); //Force place a mario yoshi off screen
			level.overwriteEntityLayout(layout);
			
			ll.save("src/levels/" + level.getName() + ".json", level);
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) { // mouse press
		Point p = MouseInfo.getPointerInfo().getLocation();
		System.out.println(p);
		System.out.println(e.getLocationOnScreen());

		int x = Level.pTc(p.x, 32);
		int y = Level.pTc(p.y, 32) - 2;
		if (!inBounds(x, y)) {return;}

		if (mode.equals(MODE.ADD)) {
			if (selectedType == TYPE.BLOCK) {this.addBlock(x, y);} 
			else if (selectedType == TYPE.ENTITY) {initAttributePrompt(selectedId, x, y);}

		} else if (mode.equals(MODE.DELETE)) {
			this.removeBlockOrEntity(x, y);;
		}

		f.repaint();
		repaint();
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		f.repaint();
		repaint();
	}

	public boolean inBounds(int x, int y) {return (x >= 0 && x < 25) && (y >= 0 && y < 15);}

	public void addBlock(int x, int y) {
		HashMap<String, Integer> blockInfo = new HashMap<String, Integer>();
		blockInfo.put("id", selectedId);
		blockInfo.put("x", x);
		blockInfo.put("y", y);

		ArrayList<HashMap<String, Integer>> currentLayout = level.getBlockLayout();
		currentLayout.add(blockInfo);
		level.overwriteBlockLayout(currentLayout);
		level.loadBlocks();

	}

	public void addEntity(int x, int y) {
		System.out.println(selectedId);
		HashMap<String, Integer> entityInfo = new HashMap<String, Integer>();
		entityInfo.put("id", selectedId);
		entityInfo.put("x", x);
		entityInfo.put("y", y);

		// For defining special attributes like jump, walk distance, velocity, etc
		entityAttributes.get(selectedId).forEach((String name, Integer value) -> {
			entityInfo.put(name, value);
		});
		System.out.println(entityInfo);
		ArrayList<HashMap<String, Integer>> currentLayout = level.getEntityLayout();
		currentLayout.add(entityInfo);
		level.overwriteEntityLayout(currentLayout);
		level.loadEntities();

	}
	
	public void removeBlockOrEntity(int x, int y) { //checking to remove blocks or entity
		//If entity and blocks overlap, will remove the block first
		ArrayList<HashMap<String, Integer>> blockLayout = level.getBlockLayout();
		
		
		int index = getIndexToRemove(blockLayout, x, y);
		if (index != -1) {
			blockLayout.remove(index);
			level.overwriteBlockLayout(blockLayout);
			level.loadBlocks();
			return;
		}
		
		ArrayList<HashMap<String, Integer>> entityLayout = level.getEntityLayout();
		index = getIndexToRemove(entityLayout, x, y);
		if (index != -1) {
			entityLayout.remove(index);
			level.overwriteEntityLayout(entityLayout);
			level.loadEntities();
			return;
		}
		
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
};
	
enum MODE {
	ADD,
	MODIFY,
	DELETE
}

enum TYPE {
	BLOCK,
	ENTITY
}
