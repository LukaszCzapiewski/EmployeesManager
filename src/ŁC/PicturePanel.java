package ŁC;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class PicturePanel extends JPanel {

    BufferedImage bufferedImage;

    public void setBufferedImage(BufferedImage bufferedImage) {

        this.bufferedImage = bufferedImage;

    }

    public void setImage(String name) {
        try {
            this.bufferedImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\img\\" + name));
        } catch (Exception e) {
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(bufferedImage, 0, 0, null);

    }

}
