import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Yoshi {
	private int x, y;
	private double size;
	private double stepSize;
	private double jump;
	private Image forward, backward;
	private AffineTransform tx;
	
	public Yoshi(int x, int y, double size, double stepSize, double jump) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.stepSize = stepSize;
		this.jump = jump;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(forward, tx, null);
		
		
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(size, size);
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

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public double getJump() {
		return jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
	}
	
}
