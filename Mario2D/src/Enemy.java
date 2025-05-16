import java.awt.event.KeyEvent;

public class Enemy {
	protected int x, y;
	protected double size;
	protected double velocity;
	
	public Enemy(int x, int y, double size, double v) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.velocity = v;
	}
	
	public void keyPressed(KeyEvent e) {
		return;
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

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double v) {
		velocity = v;
	}

	
}
