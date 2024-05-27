import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.JPanel;

public class Canvas extends JPanel{
        private List<Particle> particles;
        private int fps;
        private int frames;
        private long lastTime;

    public Canvas() {
        particles = new ArrayList<>();
        lastTime = System.currentTimeMillis();
        frames = 0;
        fps = 0;
    }

    public void addParticle(Particle p) {
        particles.add(p);
        repaint(); // Repaint the canvas to show the new particle
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle p : particles) {
            p.update(this);
            p.draw(g);
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
