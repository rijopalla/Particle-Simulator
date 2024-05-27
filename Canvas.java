import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class Canvas {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private List<Particle> particles = new ArrayList<>();

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void draw(Graphics g) {
        for (Particle p : particles) {
            g.fillOval((int)p.x - 5, (int)p.y - 5, 10, 10); // Draw particle as a small circle
        }
    }

    public void update(double dt) {
        for (Particle p : particles) {
            p.updatePosition(dt);

            // Handle collisions
            if (p.x < 0 || p.x > WIDTH) {
                p.angle = Math.PI - p.angle; // Reflect horizontally
            }
            if (p.y < 0 || p.y > HEIGHT) {
                p.angle = -p.angle; // Reflect vertically
            }
        }
    }
}
