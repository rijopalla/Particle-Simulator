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

        //make sure particles stay within boundaries after reflection
        x = Math.max(0, Math.min(x, canvas.getWidth() - diameter));
        y = Math.max(0, Math.min(y, canvas.getHeight() - diameter));
    }

    public void draw(Graphics g, int canvasHeight) {
        int drawY = canvasHeight - (int)y - diameter; //invert the y-coordinates to meet specs
        g.fillOval((int)x, drawY, diameter, diameter);
    }
    
}
