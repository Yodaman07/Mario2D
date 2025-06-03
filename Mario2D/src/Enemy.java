import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Enemy {
	protected int x, y;
	protected double velocity;

	public Enemy(int x, int y, double v) {
		this.x = x;
		this.y = y;
		this.velocity = v;
	}
	
	public void paint(Graphics g) {}//DO NOT REMOVE - Overridden for each enemy
	
	public Rectangle getTopHitbox() {
		return null;	
	}
	
	public Rectangle getHitbox() { return null;}
	
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

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double v) {
		velocity = v;
	}

	
}
