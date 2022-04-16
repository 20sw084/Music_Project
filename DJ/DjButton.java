//package DJ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DjButton extends JLabel {
	// All Listeners of Buttons
    private ActionListener action;
    private ActionListener startAction;
    private ActionListener endAction;

    boolean repeatActivated = false;

    private String lbl;
    private SubButton mainBtn;
    private SubButton subBtn;

    public void toggleRepeat() {
        repeatActivated = !repeatActivated;
        if (repeatActivated) {
            subBtn.setColor(Color.GREEN);
            startAction.actionPerformed(new ActionEvent(this,1,"a"));
        }
        else {
            subBtn.setColor(Color.gray);
            endAction.actionPerformed(new ActionEvent(this,2,"b"));
        }
    }

    public void press() {
        mainBtn.setColor(new Color(132, 126, 245));
        action.actionPerformed(new ActionEvent(this, 0 , ""));
        Timer timer = new Timer(200,(ActionEvent e) -> {
            mainBtn.setColor(new Color(220, 219, 241));
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void setActionListener(ActionListener al) {
        this.action = al;
    }

    public void setStartActionListener(ActionListener al) {
        this.startAction = al;
    }

    public void setEndActionListener(ActionListener al) {
        this.endAction = al;
    }

    public DjButton(String label) {
        this.lbl = label;
        this.setLayout(null);
        this.setSize(80,80);

        mainBtn = new SubButton(new Color(220, 219, 241),lbl);
        mainBtn.setLocation(0,0);
        mainBtn.setSize(60,80);
        mainBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) { press(); }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(mainBtn);

        subBtn = new SubButton(Color.gray);
        subBtn.setLocation(60,0);
        subBtn.setSize(20,80);
        subBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleRepeat();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(subBtn);
    }
}