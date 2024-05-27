import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class ParticleSimulation extends JPanel {
    private Canvas canvas;
    private JTextField  startXField, startYField, velocityField, startThetaField;
    private JButton addButton;
    private JRadioButton batchOption1, batchOption2, batchOption3;

    public ParticleSimulation() {
        setLayout(new BorderLayout());

        // Create sidebar panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1)); // Adjust grid layout as needed
        
        //initial
        // Add components to sidebar
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

        addButton = new JButton("Add Particle");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double startX = Double.parseDouble(startXField.getText());
                    double startY = Double.parseDouble(startYField.getText());
                    double velocity = Double.parseDouble(velocityField.getText());
                    double startTheta = Double.parseDouble(startThetaField.getText());

                    //make sure particles are w/in bounds
                    startX = Math.min(startX, canvas.getWidth() - 5);
                    startY = Math.min(startY, canvas.getHeight() - 5);

                    canvas.addParticle(new Particle(startX, startY, velocity, startTheta));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ParticleSimulation.this, "Invalid input. Please check your entries.");
                }
            }
        });

        //adding particles in batches
        JPanel batchPanel = new JPanel();
        batchPanel.setLayout(new BoxLayout(batchPanel, BoxLayout.Y_AXIS));

        batchOption1 = new JRadioButton("Form 1 (Start & End Point)");
        batchOption2 = new JRadioButton("Form 2 (Angle)");
        batchOption3 = new JRadioButton("Form 3 (Velocity)");

        ButtonGroup batchGroup = new ButtonGroup();
        batchGroup.add(batchOption1);
        batchGroup.add(batchOption1);
        batchGroup.add(batchOption1);

        batchPanel.add(batchOption1);
        batchPanel.add(batchOption2);
        batchPanel.add(batchOption3);

        sidebar.add(new JLabel("Adding particles in batches: "));
        sidebar.add(batchPanel);
        sidebar.add(addButton);

        //TODO: add logic when adding particles in batches


        // Main simulation panel
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(1280, 720));

        // Add sidebar and simulation panel to main panel
        add(sidebar, BorderLayout.EAST);
        add(canvas, BorderLayout.CENTER);

        // Set up a timer to repaint the canvas periodically
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                canvas.repaint();
            }
        }, 0, 16); // Approximately 60 FPS
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Particle Simulation");
            ParticleSimulation ps = new ParticleSimulation();
            frame.setContentPane(ps);
            frame.pack();
            frame.setSize(1500, 720); // Adjust size to accommodate sidebar
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
