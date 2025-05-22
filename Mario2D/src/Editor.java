import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Editor extends JPanel implements ActionListener {

    JFrame f;
    public Editor(){
        f = new JFrame("Level Editor");
        initPanel();

        f.setSize(800, 512); // init code
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        System.out.println("Initialized");
    }


    public void initPanel(){
       JPanel panel = new JPanel();
       panel.setBackground(Color.black);
       panel.setSize(400, 200);
       f.add(panel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String event = e.getActionCommand();

    }
}
