import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.JPanel;
import java.util.Iterator;

public class Canvas extends JPanel{
        private List<Particle> particles;
        private int fps;
        private int frames;
        private long lastTime;
        private ParticleSimulation ps;

    public Canvas() {
        particles = new ArrayList<>();
        lastTime = System.currentTimeMillis();
        frames = 0;
        fps = 0;
    }

    public void addParticle(Particle p) {
        particles.add(p);
        p.setIsBatch3(ps.batchOption3.isSelected());
        repaint(); // Repaint the canvas to show the new particle
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void removeParticle(Particle p) {
        particles.remove(p);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle particle = it.next();
            if (particle.checkTarget()) {
                it.remove();
            } else {
                particle.update(this); //update the particle's position
                particle.draw(g, getHeight()); //draw particle
            }
        }

        //get FPS
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 500) { //Update every 0.5 seconds
            fps = (int) (frames/ ((currentTime-lastTime) / 1000.0)); //calculate FPS
            frames = 0;
            lastTime = currentTime;
        }

        //draw FPS
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + fps, 10, 10);
    }
    
}
