import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.JPanel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Canvas extends JPanel {
    private List<Particle> particles;
    private int fps;
    private int frames;
    private long lastTime;
    private final ExecutorService es;

    public Canvas() {
        particles = new ArrayList<>();
        lastTime = System.currentTimeMillis();
        frames = 0;
        fps = 0;
        es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void addParticle(Particle p) {
        synchronized (particles) {
            particles.add(p);
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void removeParticle(Particle p) {
        synchronized (particles) {
            particles.remove(p);
        }
        repaint();
    }

    public void updateParticles() {
        List<Particle> particlesToRemove = new ArrayList<>();
    
        synchronized (particles) {
            for (Particle particle : particles) {
                particle.update(this);
                if (particle.checkTarget()) {
                    particlesToRemove.add(particle);
                }
            }
            particles.removeAll(particlesToRemove);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Draw particles
        synchronized (particles) {
            for (Particle particle : particles) {
                particle.draw(g, getHeight());
            }
        }
    
        // Calculate and display FPS
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) { // Update every 1 second
            fps = (int) (frames / ((currentTime - lastTime) / 1000.0)); // Calculate FPS
            frames = 0;
            lastTime = currentTime;
        }
    
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + fps, 10, 10);
    }

    // shutdown ExecutorService when the application exits
    public void shutdown() {
        es.shutdown();
        try {
            if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            es.shutdownNow();
        }
    }
}
