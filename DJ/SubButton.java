//package DJ;
import javax.swing.*;
import java.awt.*;

public class SubButton extends JLabel {

    Color bg;
    String text = null;

    public void setColor(Color bg) {
        this.bg = bg;
        repaint();
    }

    public SubButton(Color bg) {
        this.bg = bg;
    }

    public SubButton(Color bg, String text) {
        this.bg = bg;
        this.text = text;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(bg);
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0,0,this.getWidth(),this.getHeight());

        if (text != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(text,30,45);
        }
    }
}
