package cat.pseudocodi.psolving.constraints;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Repaint {

    private static final int target = new Color(149, 149, 149).getRGB();
    private static final int yellowRGB = Color.YELLOW.getRGB();
    private static final int blackRGB = Color.BLACK.getRGB();

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Repaint");
        String file = Repaint.class.getResource("vall-daran.png").getFile();


        BufferedImage bi = ImageIO.read(new File(file));
        int width = bi.getWidth();
        int height = bi.getHeight();
//        paintRegion(bi, width, height);
        paintNeighbours(new Point(width / 2, height / 2), bi, new HashSet<Point>());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

//        int[] data = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();

        ImageIcon imageIcon = new ImageIcon(imageInByte);
//        ImageIcon imageIcon = new ImageIcon((new ImageIcon(file)).getImage());
        frame.add(new JLabel(imageIcon));
        frame.setVisible(true);
        frame.pack();
    }

    private static void paintRegion(BufferedImage bi, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bi.getRGB(i, j);
                if (rgb == target) {
                    bi.setRGB(i, j, yellowRGB);
                }
            }
        }
    }

    private static void paintNeighbours(Point p, BufferedImage bi, Set<Point> visited) {
        if (!visited.contains(p)) {
            visited.add(p);
            int color = bi.getRGB(p.x, p.y);
            if (color == target) {
                bi.setRGB(p.x, p.y, yellowRGB);
                paintNeighbours(new Point(p.x - 1, p.y), bi, visited);
                paintNeighbours(new Point(p.x + 1, p.y), bi, visited);
                paintNeighbours(new Point(p.x, p.y - 1), bi, visited);
                paintNeighbours(new Point(p.x, p.y + 1), bi, visited);
            }
        }
    }
}