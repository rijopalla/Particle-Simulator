import java.awt.*;

public class Particle {
    private double x, y, velocity, theta;
    private double targetX, targetY, targetTheta, targetVelocity;
    private int diameter = 5; // particle size
    private boolean hasTarget; // if added through batch options
    private boolean isBatch3 = false; // added through the third batch option

    public Particle(double x, double y, double velocity, double theta) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.theta = Math.toRadians(theta);
    }

    // constructor for batch option 1
    public Particle(double x, double y, double velocity, double theta, double targetX, double targetY) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.targetX = targetX;
        this.targetY = targetY;
        this.hasTarget = true;
        computeTheta();
    }

    // constructor for batch option 2
    public Particle(double x, double y, double velocity, double startTheta, double targetTheta) {
        this(x, y, velocity, startTheta);
        this.targetTheta = Math.toRadians(targetTheta);
    }

    // constructor for batch option 3
    public Particle(double x, double y, double startVelocity, double theta, double targetVelocity, boolean isBatch3) {
        this.x = x; 
        this.y = y;
        this.velocity = startVelocity;
        this.theta = Math.toRadians(theta);
        this.targetVelocity = targetVelocity;
        this.isBatch3 = isBatch3;
    }

    public void update(Canvas canvas) {
        x += velocity * Math.cos(theta);
        y += velocity * Math.sin(theta);
    
        // check for boundary collision and reflect
        if (x <= 0 || x >= canvas.getWidth() - diameter) {
            theta = Math.PI - theta; // reflect horizontally
        }
        if (y <= 0 || y >= canvas.getHeight() - diameter) {
            theta = -theta; // reflect vertically
        }
    
        // make sure particles stay within boundaries after reflection
        x = Math.max(0, Math.min(x, canvas.getWidth() - diameter));
        y = Math.max(0, Math.min(y, canvas.getHeight() - diameter));
    
        // increment velocity towards targetVelocity
        if (isBatch3 && velocity < targetVelocity) {
            velocity += 0.1;
        }
    }

    public void draw(Graphics g, int canvasHeight) { 
        int drawY = canvasHeight - (int)y - diameter; // invert y-coordinates to meet specs where coordinate (0,0) should be on the bottom left
        g.fillOval((int)x, drawY, diameter, diameter); 
    }

    public boolean checkTarget() {
        if (hasTarget) { //batchOption1:
            double dx = targetX - x;
            double dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < velocity) {
                return true; // target has been reached
            } else {
                x += velocity * dx / distance;
                y += velocity * dy / distance;
                return false; // target not yet reached
            }
        }

        // check if target theta is reached
        if (!Double.isNaN(targetTheta) && Math.abs(theta - targetTheta) < 0.01) {
            return true;
        }

        // check if target velocity is reached
        if (isBatch3 && velocity >= targetVelocity) {
            System.out.println(velocity);
            return true;
        }

        return false; 
    }

    private void computeTheta() {
        double dx = targetX - x;
        double dy = targetY - y;
        this.theta = Math.atan2(dy, dx);
    }

    @Override
    public String toString() {
        return String.format("Particle[x=%.2f, y=%.2f, velocity=%.2f, theta=%.2f]", x, y, velocity, Math.toDegrees(theta));
    }

    // Getters and setters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean isBatch3() {
        return isBatch3;
    }

    public void setIsBatch3(boolean isBatch3) {
        this.isBatch3 = isBatch3;
    }

    public void setTargetTheta(double targetTheta) {
        this.targetTheta = Math.toRadians(targetTheta);
    }
}
