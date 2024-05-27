import java.awt.Graphics;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.*;

public class Canvas extends JPanel {
    private List<Particle> particles = new ArrayList<>();
    private int width, height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void draw(Graphics g) {
        for (Particle particle : particles) {
            particle.updatePosition();
            particle.bounceOffWalls(width, height);
            g.fillOval((int)(particle.x - particle.radius), (int)(particle.y - particle.radius), (int)(2 * particle.radius), (int)(2 * particle.radius));
        }
    }
}

