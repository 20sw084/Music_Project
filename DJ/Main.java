//package DJ;

import javax.swing.*;

public class Main extends JFrame {

    DjPanel p1;
    DjPanel2 p2;

    public Main() {
        this.setTitle("DJ BOX");
        this.setSize(410,740);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        p1 = new DjPanel(this);
        p2 = new DjPanel2(this);

        this.getContentPane().add(p1);
        this.addKeyListener(p1);

        this.setVisible(true);
    }

    public void goToPage2() {
        this.getContentPane().removeAll();
        this.removeKeyListener(this.getKeyListeners()[0]);
        this.getContentPane().add(p2);
        this.addKeyListener(p2);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    public void goToPage1() {
        this.getContentPane().removeAll();
        this.removeKeyListener(this.getKeyListeners()[0]);
        this.getContentPane().add(p1);
        this.addKeyListener(p1);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    public static void main(String[] args) {
        new Main();
    }

}
