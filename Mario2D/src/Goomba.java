import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Goomba extends Enemy{
	
	private int walkDistance;
	private Image forward, backward;
	private AffineTransform tx;
	
	public Goomba(int x, int y, double size, double v, int d) {
		super(x, y, size, v);
		walkDistance = d;
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(forward, tx, null);
		
		int traveled = 0;
		x += velocity;
		traveled += velocity;
		
		if(Math.abs(traveled) == walkDistance) {
			velocity *= -1;
			traveled = 0;
			if(velocity < 0) {
				g2.drawImage(backward, tx, null);
			}else {
				g2.drawImage(forward, tx, null);
			}
		}
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(size, size);
	}
	
	
	
	
	
}
