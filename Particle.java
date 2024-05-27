import java.awt.*;

public class Particle {
    private double x, y, velocity, theta;
    private int diameter = 5; //particle size

    public Particle(double x, double y, double velocity, double theta) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.theta = Math.toRadians(theta); // Convert to radians
    }

    public void update(Canvas canvas) {
        x += velocity * Math.cos(theta);
        y += velocity * Math.sin(theta);

        //check for boundary collision and reflect
        if (x <= 0 || x >= canvas.getWidth() - diameter) {
            theta = Math.PI - theta; //reflect horizontally
        }
        if (y <= 0 || y >= canvas.getHeight() - diameter) {
            theta = -theta; //reflect vertically
        }
    }

    public void draw(Graphics g) {
        g.fillOval((int) x, (int) y, 5, 5);
    }
    
}
