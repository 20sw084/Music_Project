package DJ;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class DjPanel2 extends JPanel implements KeyListener {

    Main parent;
    
    int count = 12;
    boolean repeatActivated = false;

    DjButton[] buttons = new DjButton[12];
    Clip[] activeSounds = new Clip[12];

    public void handleEnd(int i) {
        activeSounds[i].stop();
        activeSounds[i].close();
        activeSounds[i] = null;
    }

    public void handleStart(int i) {
        if (activeSounds[i] != null) {
            System.out.println("sound already active!");
            return;
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("C:\\Users\\Lenovo\\Documents\\eclipse-workspace\\FirstHibernate\\src\\DJ\\sounds2/" + String.valueOf(i + 1) + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            activeSounds[i] = clip;
        } catch (UnsupportedAudioFileException e) {
            System.out.println("use wav format audio.");
        } catch (IOException | LineUnavailableException e) {
            System.out.println("error reading audio file.");
        }
    }

    public void playSound(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("C:\\Users\\Lenovo\\Documents\\eclipse-workspace\\FirstHibernate\\src\\DJ\\sounds2/" + String.valueOf(i + 1) + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            //clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public DjPanel2(Main parent) {

        this.parent = parent;
        this.setLayout(null);
        this.setBackground(new Color(202, 178, 206));

        String lbls =  "1234567890-=";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int index = i * 4 + j;
                DjButton button = new DjButton("" + lbls.charAt(index));
                button.setLocation(30 + 90*j,30 + 90*i);
                button.setActionListener((ActionEvent e) -> playSound(index));
                button.setStartActionListener((ActionEvent e) -> handleStart(index));
                button.setEndActionListener((ActionEvent e) -> handleEnd(index));
                this.add(button);
                buttons[index] = button;
            }
        }

        SubButton auxBtn1 = new SubButton(new Color(220, 219, 241),"M");
        auxBtn1.setLocation(120,300);
        auxBtn1.setSize(80,80);
        auxBtn1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleRepeat();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(auxBtn1);

        SubButton page1Btn = new SubButton(new Color(220, 219, 241),"<");
        page1Btn.setLocation(30,300);
        page1Btn.setSize(80,80);
        page1Btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                parent.goToPage1();
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(page1Btn);
    }
    
    public void toggleRepeat() {
        repeatActivated = !repeatActivated;
        if (repeatActivated) {
            handleStartAll();
        }
        else {
            handleEndAll();
        }
    }


    private void handleEndAll() {
        for(int i = 0; i < count; i++) {
            handleEnd(i);
        }
    }

    private void handleStartAll() {
        for(int i = 0; i < count; i++) {
            handleStart(i);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println();

        String row = "qwerasdfzxcv";
        int tIndex = row.indexOf(e.getKeyChar());
        if (tIndex != -1) {
            buttons[tIndex].toggleRepeat();
        }

        String numbers = "1234567890-=";
        int nIndex = numbers.indexOf(e.getKeyChar());
        if (nIndex != -1) {
            buttons[nIndex].press();
        }

        if (e.getKeyCode() == 37) {
            parent.goToPage1();
        }

    }
}
