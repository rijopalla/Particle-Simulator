import java.awt.*;

public class Particle {
    private double x, y, velocity, theta;
    private double targetX, targetY;
    private int diameter = 5; //particle size
    private boolean hasTarget;

    public Particle(double x, double y, double velocity, double theta) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.theta = Math.toRadians(theta); // Convert to radians
        this.hasTarget = false;
    }

    public Particle(double x, double y, double velocity, double theta, double targetX, double targetY) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.targetX = targetX;
        this.targetY = targetY;
        this.hasTarget = true;
        computeTheta();
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

    public boolean checkTarget() {
        if (!hasTarget) return false;
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < velocity) {
            x = targetX;
            y = targetY;
            return true; //target has been reached
        }

        x += velocity * dx / distance;
        y += velocity * dx / distance;
        return false; //target not yet reached
    }

    private void computeTheta() {
        double dx = targetX - x;
        double dy = targetY - y;
        this.theta = Math.atan2(dy,dx);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    
}
