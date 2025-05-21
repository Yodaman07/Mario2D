import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Brick{
	
	private boolean breakable;
	int x, y;
	double size;
	private Image image;
	private AffineTransform tx;
	
	public Brick(boolean b, int x, int y, double size, String img) {
		breakable = b;
		this.x = x;
		this.y = y;
		this.size = size;
		image = Toolkit.getDefaultToolkit().getImage(img);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(image, tx, null);
		
		
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(size, size);
	}
	
	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean breakable) {
		this.breakable = breakable;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	
	
	
	
	
	
}
