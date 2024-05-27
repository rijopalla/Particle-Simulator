
public class Particle {
    public double x, y;
    public double angle; //angle in degrees
    public double velocity; //velocity in pixels per second


    public Particle(double x, double y, double velocity, double angle) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.angle  = Math.toRadians(angle);
    }

    void updatePosition(double dt) {
        x += velocity * Math.cos(angle) * dt;
        y += velocity * Math.sin(angle) * dt;
    }
    
}

