//package DJ;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class DjPanel extends JPanel implements KeyListener {

    Main parent;
    private final int nvar=5;
    int count = 9;
    boolean repeatActivated = false;

    DjButton[] buttons = new DjButton[9];
    Clip[] activeSounds = new Clip[9];

    Clip downClip;
    Clip upClip;

    SubButton downBtn;
    SubButton upBtn;

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
                    new File("sounds/" + String.valueOf(i + 1) + ".wav"));
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
                    new File("sounds/" + String.valueOf(i + 1) + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            //clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void diskUpPress() {
        if (upClip != null && upClip.isOpen()) return;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("sounds/up.wav"));
            upClip = AudioSystem.getClip();
//            Thread.sleep(9000);
            upClip.open(audioInputStream);
            upClip.loop(Clip.LOOP_CONTINUOUSLY);
            upBtn.setColor(new Color(217, 0, 255, 144));
            //clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void diskUpRelease() {
        upClip.stop();
        upClip.close();
        upBtn.setColor(new Color(202, 178, 206,100));
    }

    private void diskDownPress() {
        if (downClip != null && downClip.isOpen()) return;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("sounds/down.wav"));
            downClip = AudioSystem.getClip();
            downClip.open(audioInputStream);
            downClip.loop(Clip.LOOP_CONTINUOUSLY);
            downBtn.setColor(new Color(217, 0, 255, 144));
            //clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void diskDownRelease() {
        downClip.stop();
        downClip.close();
        downBtn.setColor(new Color(202, 178, 206,100));
    }

    public DjPanel(Main parent) {

        this.parent = parent;
        this.setLayout(null);
        this.setBackground(new Color(202, 178, 206));

        upBtn = new SubButton(new Color(202, 178, 206,100));
        upBtn.setLocation(275,120);
        upBtn.setSize(60,40);
        upBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                diskUpPress();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                diskUpRelease();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(upBtn);

        downBtn = new SubButton(new Color(202, 178, 206,100));
        downBtn.setLocation(275,180);
        downBtn.setSize(60,40);
        downBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                diskDownPress();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                diskDownRelease();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(downBtn);

        DjDisk disk = new DjDisk();
        disk.setLocation(55,10);
        this.add(disk);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                DjButton button = new DjButton(String.valueOf(index + 1));
                button.setLocation(75 + 90*j,340 + 90*i);
                button.setActionListener((ActionEvent e) -> playSound(index));
                button.setStartActionListener((ActionEvent e) -> handleStart(index));
                button.setEndActionListener((ActionEvent e) -> handleEnd(index));
                this.add(button);
                buttons[index] = button;
            }
        }
        
        SubButton auxBtn1 = new SubButton(new Color(220, 219, 241),"M");
        auxBtn1.setLocation(165,610);
        auxBtn1.setSize(80,80);
        auxBtn1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleRepeat();
                try {
                applyHSA();
                }
                catch(Exception w)
                {
                	
                }
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

        SubButton page2Btn = new SubButton(new Color(220, 219, 241),">");
        page2Btn.setLocation(255,610);
        page2Btn.setSize(80,80);
        page2Btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                parent.goToPage2();
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(page2Btn);

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
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            diskUpPress();
        }
        if (e.getKeyCode() == 40) {
            diskDownPress();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String row = "qweasdzxc";
        int tIndex = row.indexOf(e.getKeyChar());
        if (tIndex != -1) {
            buttons[tIndex].toggleRepeat();
        }

        String numbers = "123456789";
        int nIndex = numbers.indexOf(e.getKeyChar());
        if (nIndex != -1) {
            buttons[nIndex].press();
        }

        if (e.getKeyCode() == 38) {
            diskUpRelease();
        }
        if (e.getKeyCode() == 40) {
            diskDownRelease();
        }

        if (e.getKeyCode() == 39) {
            parent.goToPage2();
        }
    }

    HarmonySearch hsa=new HarmonySearch(); 
    
    public  void applyHSA() {
    	InitializeHarmony();
    	InitializeMemory();
    	while(hsa!=null) {
        ImproviseHarmony();
    	UpdateHarmony();
    	if(true)
    		hsa=null;
    	else
    		break;
    	}
    	try {
    	NewHarmony();
    	hsa.toImplement();
    	}
    	catch(Exception e) {
    	}
    }

    public  void InitializeHarmony() {
    	hsa.setBW(.2);
    }
    
    public  void InitializeMemory() {
        hsa.setNVAR(nvar);
    }
    public  void ImproviseHarmony() {
    	hsa.setHMCR(.9);
    }
    public  void UpdateHarmony() {
    	hsa.setHMS(nvar);
    }
    public  void NewHarmony() {
		try {
    	hsa.setPAR(.4);
		hsa.setMaxIteration(100000);	
		}
		catch(Exception e)
		{	
			
		}
	}
}