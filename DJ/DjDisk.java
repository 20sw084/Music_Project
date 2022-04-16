//package DJ;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DjDisk extends JPanel {
	// For Reading of Disk Image
    BufferedImage diskImage;

    public DjDisk() {
        try {
            diskImage = ImageIO.read(new File("disk.png"));
        } catch (IOException ex) {
            System.out.println("disk image not found.");
        }
        this.setSize(300,300);

        JLabel img = new JLabel(new ImageIcon("disk.png"));
        img.setOpaque(true);
        img.setBackground(new Color(0,0,0,0));
        img.setSize(300,300);
        img.setLocation(0,0);
        this.add(img);
        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
    }
}
