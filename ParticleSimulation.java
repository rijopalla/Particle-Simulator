import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;


public class ParticleSimulation extends JPanel {
    private Canvas canvas;
    private JTextField numParticlesField, startXField, startYField, velocityField, startThetaField;
    private JButton addButton;

    public ParticleSimulation() {
        setLayout(new BorderLayout());

        // Create sidebar panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 2)); // Adjust grid layout as needed

        // Add components to sidebar
        sidebar.add(new JLabel("Number of Particles:"));
        numParticlesField = new JTextField();
        sidebar.add(numParticlesField);

        sidebar.add(new JLabel("Start X Position:"));
        startXField = new JTextField();
        sidebar.add(startXField);

        sidebar.add(new JLabel("Start Y Position:"));
        startYField = new JTextField();
        sidebar.add(startYField);

        sidebar.add(new JLabel("Velocity:"));
        velocityField = new JTextField();
        sidebar.add(velocityField);

        sidebar.add(new JLabel("Start Angle (degrees):"));
        startThetaField = new JTextField();
        sidebar.add(startThetaField);

        addButton = new JButton("Add Particles");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numParticles = Integer.parseInt(numParticlesField.getText());
                    double startX = Double.parseDouble(startXField.getText());
                    double startY = Double.parseDouble(startYField.getText());
                    double velocity = Double.parseDouble(velocityField.getText());
                    double startTheta = Double.parseDouble(startThetaField.getText());

                    for (int i = 0; i < numParticles; i++) {
                        canvas.addParticle(new Particle(startX, startY, velocity, startTheta));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ParticleSimulation.this, "Invalid input. Please check your entries.");
                }
            }
        });

        sidebar.add(addButton);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas = new Canvas(getWidth(), getHeight());
                setPreferredSize(new Dimension(1280, 720));
                revalidate(); // Refresh the layout
                repaint(); // Redraw the panel
                timer.cancel(); // Cancel the timer to prevent repeated execution
            }
        }, 100); // Delay initialization slightly to allow layout to complete


        // Main simulation panel
        canvas = new Canvas(getWidth(), getHeight());
        setPreferredSize(new Dimension(1280, 720));

        // Add sidebar and simulation panel to main panel
        add(sidebar, BorderLayout.EAST);
        add(canvas, BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvas.draw(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Particle Simulation");
            ParticleSimulation sim = new ParticleSimulation();
            frame.setContentPane(sim);
            frame.pack();
            frame.setSize(1500, 720); // Adjust size to accommodate sidebar
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
