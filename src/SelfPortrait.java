import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.*;
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
    private static final int WIDTH = 660;
    private static final int HEIGHT = 1000;

    public SelfPortrait() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            double scale = Math.min(getWidth() / (double) WIDTH, getHeight() / (double) HEIGHT);
            g.translate((getWidth() - WIDTH * scale) / 2, (getHeight() - HEIGHT * scale) / 2);
            g.scale(scale, scale);
            drawPortrait(g);
        } finally {
            g.dispose();
        }
    }

    // All geometry is drawn in the reference photograph's 660 x 1000 composition.
    private static Color color(int rgb) { return new Color(rgb); }

    private static void fill(Graphics2D g, int rgb, Shape shape) {
        g.setColor(color(rgb));
        g.fill(shape);
    }

    private static void stroke(Graphics2D g, int rgb, float width, Shape shape) {
        g.setColor(color(rgb));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(shape);
    }

    /** General paths define the stole, collar, tie, and other irregular silhouettes. */
    private static GeneralPath polygon(double... points) {
        GeneralPath path = new GeneralPath();
        path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) path.lineTo(points[i], points[i + 1]);
        path.closePath();
        return path;
    }

    private static void drawPortrait(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(new RadialGradientPaint(new Point2D.Double(300, 510), 560,
                new float[] {0, 1}, new Color[] {color(0x424752), color(0x171b20)}));
        g.fill(new Rectangle2D.Double(0, 0, WIDTH, HEIGHT));
        drawGown(g);
        drawHead(g);
        drawCollarAndStole(g);
        drawHandsAndWatch(g);
    }

    private static void drawGown(Graphics2D g) {
        GeneralPath gown = new GeneralPath();
        gown.moveTo(252, 420);
        gown.curveTo(211, 440, 130, 468, 112, 496);
        gown.curveTo(64, 562, 59, 715, 66, 801);
        gown.curveTo(71, 886, 68, 953, 89, 1000);
        gown.lineTo(614, 1000);
        gown.curveTo(651, 918, 641, 811, 624, 726);
        gown.curveTo(611, 634, 597, 540, 554, 487);
        gown.curveTo(512, 457, 442, 433, 394, 417);
        gown.closePath();
        g.setPaint(new GradientPaint(95, 620, color(0xc2cbe4), 455, 570, color(0xf5f7ff)));
        g.fill(gown);
        // Broad shadow folds use cubic curves; narrow seams use quadratic curves.
        fill(g, 0xb4bed8, polygon(121, 542, 111, 746, 147, 811, 226, 858,
                181, 803, 150, 736));
        fill(g, 0xd3dbed, polygon(551, 543, 579, 725, 580, 801, 484, 874,
                559, 838, 610, 787));
        fill(g, 0xe5eaf8, polygon(143, 851, 258, 908, 280, 952, 217, 970,
                100, 943));
        fill(g, 0xd4dcef, polygon(499, 792, 462, 882, 363, 947, 448, 970,
                581, 906, 601, 851));
        for (int i = 0; i < 6; i++) {
            double x = 148 + i * 29;
            stroke(g, 0xd0d8ee, 2, new QuadCurve2D.Double(x, 582, x - 7, 713, x + 18, 868));
        }
        stroke(g, 0xbec8df, 2, new CubicCurve2D.Double(327, 603, 308, 728, 322, 876, 346, 970));
        stroke(g, 0xffffff, 3, new CubicCurve2D.Double(332, 608, 320, 734, 332, 872, 351, 961));
        stroke(g, 0xc0cae0, 2, new QuadCurve2D.Double(119, 808, 178, 835, 232, 850));
        stroke(g, 0xffffff, 3, new QuadCurve2D.Double(441, 910, 521, 900, 588, 840));
    }

    private static void drawHead(Graphics2D g) {
        GeneralPath neck = new GeneralPath();
        neck.moveTo(263, 365); neck.lineTo(262, 414);
        neck.curveTo(270, 443, 304, 462, 327, 464);
        neck.curveTo(353, 451, 378, 425, 381, 410);
        neck.lineTo(375, 355); neck.closePath();
        g.setPaint(new GradientPaint(270, 370, color(0xb8816c), 340, 453, color(0xe3b19a)));
        g.fill(neck);
        fill(g, 0xc28a77, new Ellipse2D.Double(214, 292, 32, 58));
        fill(g, 0xd9a18b, new Ellipse2D.Double(382, 286, 29, 56));
        stroke(g, 0x9d685d, 3, new Arc2D.Double(221, 301, 16, 35, 70, 220, Arc2D.OPEN));
        stroke(g, 0xa77465, 3, new Arc2D.Double(388, 295, 15, 36, -80, 220, Arc2D.OPEN));
        GeneralPath face = new GeneralPath();
        face.moveTo(234, 239);
        face.curveTo(258, 212, 351, 209, 387, 239);
        face.curveTo(398, 265, 392, 310, 384, 341);
        face.curveTo(379, 374, 356, 406, 320, 409);
        face.curveTo(286, 413, 260, 389, 247, 357);
        face.curveTo(232, 326, 230, 272, 234, 239); face.closePath();
        g.setPaint(new GradientPaint(235, 315, color(0xc5927f), 365, 296, color(0xedc0a9)));
        g.fill(face);
        fill(g, 0xd5a18c, new Ellipse2D.Double(248, 306, 39, 37));
        fill(g, 0xe7b49e, new Ellipse2D.Double(343, 302, 35, 38));
        drawEyes(g);
        stroke(g, 0xb77e70, 2, new CubicCurve2D.Double(308, 289, 309, 305, 295, 316, 298, 324));
        stroke(g, 0xb58070, 2, new QuadCurve2D.Double(320, 292, 322, 313, 335, 325));
        fill(g, 0xa66d60, new Ellipse2D.Double(295, 324, 11, 5));
        fill(g, 0xa66d60, new Ellipse2D.Double(323, 323, 10, 5));
        stroke(g, 0xecc0a7, 4, new QuadCurve2D.Double(305, 325, 314, 330, 322, 324));
        // Curved lips and teeth preserve the reference's open smile.
        GeneralPath smile = new GeneralPath();
        smile.moveTo(281, 351); smile.curveTo(301, 339, 332, 340, 352, 350);
        smile.curveTo(340, 374, 298, 381, 281, 351); smile.closePath();
        fill(g, 0x815453, smile);
        GeneralPath teeth = new GeneralPath();
        teeth.moveTo(287, 351); teeth.curveTo(308, 347, 331, 346, 346, 351);
        teeth.lineTo(341, 358); teeth.curveTo(319, 363, 303, 362, 291, 358); teeth.closePath();
        fill(g, 0xfff5e7, teeth);
        for (int x = 297; x <= 337; x += 8)
            stroke(g, 0xcfbfb7, 0.8f, new Line2D.Double(x, 350, x + 1, 360));
        stroke(g, 0xbe837c, 3, new CubicCurve2D.Double(291, 367, 304, 375, 332, 374, 343, 363));
        stroke(g, 0xbc8d7a, 1.5f, new QuadCurve2D.Double(278, 342, 275, 353, 281, 362));
        drawHair(g);
    }

    private static void drawEyes(Graphics2D g) {
        fill(g, 0x3b3433, polygon(249, 270, 258, 264, 279, 262, 294, 269, 293, 273,
                277, 268, 260, 269));
        fill(g, 0x393232, polygon(325, 267, 338, 261, 358, 260, 375, 269, 373, 273,
                355, 267, 338, 267));
        for (int side = 0; side < 2; side++) {
            double x = side == 0 ? 253 : 330;
            GeneralPath eye = new GeneralPath();
            eye.moveTo(x, 287); eye.quadTo(x + 18, 273, x + 39, 285);
            eye.quadTo(x + 20, 294, x, 287); eye.closePath();
            fill(g, 0xe7ddd5, eye);
            Area iris = new Area(new Ellipse2D.Double(x + 13, 278, 14, 14));
            iris.intersect(new Area(eye)); // Clip the iris to the eyelid opening.
            fill(g, 0x332c2a, iris);
            fill(g, 0x13191c, new Ellipse2D.Double(x + 17, 282, 6, 6));
            // Small squares are eye catchlights, not unrelated decorative shapes.
            fill(g, 0xffffff, new Rectangle2D.Double(x + 18, 282, 2, 2));
            stroke(g, 0x624a43, 2, new QuadCurve2D.Double(x, 287, x + 18, 273, x + 39, 285));
            stroke(g, 0xb38677, 1.5f, new QuadCurve2D.Double(x, 295, x + 20, 300, x + 39, 292));
        }
    }

    private static void drawHair(Graphics2D g) {
        GeneralPath hair = new GeneralPath();
        hair.moveTo(229, 293); hair.curveTo(226, 274, 220, 264, 219, 244);
        hair.curveTo(200, 219, 222, 179, 264, 166);
        hair.curveTo(288, 144, 343, 152, 375, 171);
        hair.curveTo(408, 187, 417, 215, 404, 250);
        hair.lineTo(390, 287); hair.lineTo(383, 243);
        hair.lineTo(373, 232); hair.lineTo(351, 233); hair.lineTo(341, 228);
        hair.lineTo(331, 233); hair.lineTo(318, 230); hair.lineTo(302, 235);
        hair.lineTo(274, 234); hair.lineTo(252, 239); hair.lineTo(238, 255);
        hair.lineTo(234, 294); hair.closePath();
        g.setPaint(new GradientPaint(275, 154, color(0x24292a), 310, 246, color(0x0c1114)));
        g.fill(hair);
        Graphics2D texture = (Graphics2D) g.create();
        texture.clip(hair);
        for (int i = 0; i < 34; i++) {
            double x = 224 + i * 5;
            stroke(texture, i % 3 == 0 ? 0x3c4140 : 0x252b2c, 1.2f,
                    new CubicCurve2D.Double(x, 232, x - 14, 210, x + 11, 175, 305 + (x - 300) * .6, 161));
        }
        texture.dispose();
    }

    private static void drawCollarAndStole(Graphics2D g) {
        fill(g, 0xf6f8ff, polygon(261, 408, 281, 437, 327, 466, 375, 430,
                392, 406, 405, 441, 376, 491, 326, 542, 282, 485, 250, 438));
        fill(g, 0xcbd3e4, polygon(265, 422, 301, 466, 297, 494, 281, 468));
        fill(g, 0xdce2ef, polygon(381, 424, 342, 466, 358, 492, 388, 453));
        fill(g, 0x1f293f, polygon(310, 468, 335, 466, 353, 480, 340, 502,
                327, 504, 302, 482));
        fill(g, 0x242e46, polygon(319, 498, 337, 499, 345, 522, 328, 552, 312, 527));
        stroke(g, 0x424a61, 2, new Line2D.Double(310, 480, 340, 476));
        fill(g, 0xf4f6fc, polygon(194, 454, 253, 427, 327, 550, 401, 428,
                472, 449, 328, 584));
        GeneralPath stole = polygon(126, 488, 194, 456, 327, 576, 474, 447,
                540, 481, 328, 603);
        g.setPaint(new GradientPaint(155, 460, color(0x376392), 420, 591, color(0x204572)));
        g.fill(stole);
        stroke(g, 0x152b48, 3, new Line2D.Double(127, 489, 328, 602));
        stroke(g, 0x152b48, 3, new Line2D.Double(328, 602, 540, 482));
        // Rectangular cord and individual line strands construct the gold tassel.
        fill(g, 0xcda13b, new Rectangle2D.Double(325, 603, 4, 24));
        fill(g, 0xe7bb39, new Ellipse2D.Double(322, 619, 10, 17));
        for (int i = 0; i < 13; i++)
            stroke(g, i % 2 == 0 ? 0xfbd661 : 0xc99622, 1.5f,
                    new Line2D.Double(324 + i * .5, 633, 320 + i * 1.5, 680 - i % 3 * 3));
        drawBadge(g);
    }

    private static void drawBadge(Graphics2D g) {
        Graphics2D badge = (Graphics2D) g.create();
        badge.translate(423, 501);
        badge.rotate(-.20); // A local transformation aligns the seal with the stole.
        fill(badge, 0xf6f2df, new Ellipse2D.Double(-31, -31, 62, 62));
        stroke(badge, 0x77705f, 1.5f, new Ellipse2D.Double(-28, -28, 56, 56));
        fill(badge, 0xeac64d, new Ellipse2D.Double(-23, -23, 46, 46));
        stroke(badge, 0x3562a1, 2, new Ellipse2D.Double(-21, -21, 42, 42));
        badge.setColor(color(0x3867a6));
        badge.setFont(new java.awt.Font(java.awt.Font.SERIF, java.awt.Font.BOLD, 19));
        badge.drawString("UST", -21, 7);
        // Stylized seal lettering: the tiny inscription is simplified intentionally.
        for (int i = 0; i < 24; i++) {
            double angle = i * Math.PI / 12;
            double x = Math.sin(angle) * 26, y = Math.cos(angle) * 26;
            stroke(badge, 0x565b60, 1, new Line2D.Double(x, y, x * .94, y * .94));
        }
        badge.dispose();
    }

    private static void drawHandsAndWatch(Graphics2D g) {
        fill(g, 0xc18d77, polygon(233, 959, 272, 948, 282, 969, 315, 985,
                332, 1000, 239, 1000, 222, 976));
        GeneralPath hand = new GeneralPath();
        hand.moveTo(301, 992); hand.curveTo(309, 975, 332, 972, 350, 970);
        hand.curveTo(371, 954, 395, 949, 409, 959);
        hand.lineTo(451, 986); hand.lineTo(449, 1000); hand.lineTo(297, 1000);
        hand.closePath();
        g.setPaint(new GradientPaint(326, 974, color(0xdba38d), 414, 998, color(0xb17b6a)));
        g.fill(hand);
        stroke(g, 0xa97564, 2, new QuadCurve2D.Double(318, 989, 336, 987, 354, 991));
        Graphics2D watch = (Graphics2D) g.create();
        watch.translate(252, 961); watch.rotate(-.3);
        fill(watch, 0x24282f, new Rectangle2D.Double(-24, -4, 48, 25));
        fill(watch, 0xb2b4b6, new Ellipse2D.Double(-22, -16, 44, 32));
        fill(watch, 0xf2f0ec, new Ellipse2D.Double(-19, -13, 38, 26));
        stroke(watch, 0x515665, 1, new Line2D.Double(0, 0, 0, -9));
        stroke(watch, 0x515665, 1, new Line2D.Double(0, 0, 11, 3));
        watch.dispose();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2 && args[0].equals("--render")) {
            Path output = Path.of(args[1]);
            if (output.toAbsolutePath().getParent() != null) {
                Files.createDirectories(output.toAbsolutePath().getParent());
            }
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
