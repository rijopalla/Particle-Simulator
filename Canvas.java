import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.JPanel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Canvas extends JPanel{
        private List<Particle> particles;
        private int fps;
        private int frames;
        private long lastTime;
        private ParticleSimulation ps;
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
            p.setIsBatch3(ps.batchOption3.isSelected());
        }
        // Submit tasks for updating and removing particles
        es.submit(() -> {
            while (true) {
                for (Particle particle : new ArrayList<>(particles)) {
                    if (particle.checkTarget()) {
                        synchronized (particles) {
                            particles.remove(particle);
                        }
                    } else {
                        particle.update(this); // Update the particle's position
                        if (particle.isBatch3()) {
                            particle.draw(getGraphics(), getHeight()); // Draw particle
                        }
                    }
                }
                try {
                    Thread.sleep(16); // Sleep for approximately 16ms to simulate ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Submit tasks to ExecutorService for concurrent execution
        List<Future<?>> futures = new ArrayList<>();
        for (final Particle particle : particles) {
            Future<?> future = es.submit(() -> {
                if (particle.checkTarget()) {
                    synchronized (particles) {
                        particles.remove(particle);
                    }
                } else {
                    particle.update(this); // Update the particle's position
                    particle.draw(g, getHeight()); // Draw particle
                }
            });
            futures.add(future);
        }
        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Get FPS
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 500) { // Update every 0.5 seconds
            fps = (int) (frames / ((currentTime - lastTime) / 1000.0)); // Calculate FPS
            frames = 0;
            lastTime = currentTime;
        }

        // Draw FPS
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + fps, 10, 10);
    }

    // Shutdown ExecutorService when the application exits
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
