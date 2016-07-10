package cat.pseudocodi.psolving.constraints;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class GoogleMapsDemo {

    public static void main(String[] args) throws IOException {
        JFrame test = new JFrame("Google Maps");
        try {
            String latitude = "41.7029476";
            String longitude = "1.3663467";
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                    + latitude
                    + ","
                    + longitude
                    + "&zoom=8&size=1024x768&scale=2&maptype=roadmap";
            String destinationFile = "image.jpg";
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH));
        test.add(new JLabel(imageIcon));
        test.setVisible(true);
        test.pack();
    }
}