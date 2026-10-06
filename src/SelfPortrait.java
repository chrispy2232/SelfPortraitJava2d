import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** A customizable portrait drawn entirely with Java 2D shapes. */
public final class SelfPortrait extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 600;

    public SelfPortrait() {
        setPreferredSize(new Dimension(SIZE, SIZE));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.scale(getWidth() / (double) SIZE, getHeight() / (double) SIZE);
            drawPortrait(g);
        } finally {
            g.dispose();
        }
    }

    private static void drawPortrait(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(228, 239, 244));
        g.fillRect(0, 0, SIZE, SIZE);
        g.setColor(new Color(201, 221, 230));
        g.fillOval(70, 55, 460, 490);
        // Shirt and neck. Change these colors and shapes to make it your own.
        g.setColor(new Color(45, 87, 122));
        g.fillRoundRect(135, 430, 330, 220, 160, 160);
        g.setColor(new Color(211, 151, 111));
        g.fillRoundRect(260, 365, 80, 105, 35, 35);
        // Ears, face, and hair.
        g.setColor(new Color(232, 175, 131));
        g.fillOval(175, 255, 45, 75);
        g.fillOval(380, 255, 45, 75);
        g.fillOval(195, 145, 210, 280);
        g.setColor(new Color(48, 37, 34));
        g.fillArc(190, 115, 220, 185, 0, 180);
        g.fillRoundRect(192, 192, 25, 85, 20, 20);
        g.fillRoundRect(383, 192, 25, 85, 20, 20);
        // Eyebrows and eyes.
        g.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(235, 250, 268, 245);
        g.drawLine(332, 245, 365, 250);
        g.setColor(Color.WHITE);
        g.fillOval(231, 263, 43, 25);
        g.fillOval(326, 263, 43, 25);
        g.setColor(new Color(48, 37, 34));
        g.fillOval(249, 266, 15, 19);
        g.fillOval(337, 266, 15, 19);
        // Nose and smile.
        g.setColor(new Color(179, 113, 83));
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(301, 286, 292, 322);
        g.drawLine(292, 322, 306, 322);
        g.setColor(new Color(124, 62, 55));
        g.drawArc(260, 328, 80, 45, 190, 160);
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2 && args[0].equals("--render")) {
            Path output = Path.of(args[1]);
            if (output.toAbsolutePath().getParent() != null) {
                Files.createDirectories(output.toAbsolutePath().getParent());
            }
            BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            try {
                drawPortrait(g);
            } finally {
                g.dispose();
            }
            if (!ImageIO.write(image, "png", output.toFile())) {
                throw new IOException("PNG image writer is unavailable");
            }
            System.out.println("Portrait saved to " + output);
            return;
        }
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: SelfPortrait [--render output.png]");
        }
        if (GraphicsEnvironment.isHeadless()) {
            throw new IllegalStateException(
                    "No graphical display available. Use --render build/portrait.png instead.");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Java 2D Self Portrait");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SelfPortrait());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
