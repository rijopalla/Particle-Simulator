import javax.swing.*;
import java.awt.*;

public class ParticleSimulation extends JPanel {
    private Canvas canvas;
    private long lastFPSTime = System.currentTimeMillis();
    private int frames = 0;

    public ParticleSimulation() {
        canvas = new Canvas();
        setPreferredSize(new Dimension(1280, 720));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvas.draw(g);
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFPSTime >= 500) { // Update FPS every 0.5 seconds
            String fpsText = "FPS: " + ((double)frames / (currentTime - lastFPSTime) * 1000);
            g.drawString(fpsText, 10, 20);
            lastFPSTime = currentTime;
            frames = 0;
        }
    }

    public void addParticles(int n, double startX, double startY, double endX, double endY, double v, double startTheta, double endTheta) {
        double dx = (endX - startX) / (n - 1);
        double dy = (endY - startY) / (n - 1);
        double dTheta = (endTheta - startTheta) / (n - 1);
        for (int i = 0; i < n; i++) {
            double x = startX + i * dx;
            double y = startY + i * dy;
            double theta = startTheta + i * dTheta;
            canvas.addParticle(new Particle(x, y, v, theta));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Particle Simulation");
            ParticleSimulation sim = new ParticleSimulation();
            frame.setContentPane(sim);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Example usage
            sim.addParticles(10, 0, 0, 1280, 720, 50, 0, 360);
        });
    }
}
