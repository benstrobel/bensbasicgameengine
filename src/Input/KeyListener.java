package Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private boolean [] key;
    private boolean keypressed = false;

    public KeyListener(){
        key = new boolean [6];
        key [0] = false;    //W
        key [1] = false;    //A
        key [2] = false;    //S
        key [3] = false;    //D
        key [4] = false;    //Space
        key [5] = false;    //ESC
    }

    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_W:{key[0] = true; keypressed = true; break;}
            case KeyEvent.VK_A:{key[1] = true; keypressed = true; break;}
            case KeyEvent.VK_S:{key[2] = true; keypressed = true; break;}
            case KeyEvent.VK_D:{key[3] = true; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[4] = true; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[5] = true; keypressed = true; break;}
        }
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_W:{key[0] = false; keypressed = true; break;}
            case KeyEvent.VK_A:{key[1] = false; keypressed = true; break;}
            case KeyEvent.VK_S:{key[2] = false; keypressed = true; break;}
            case KeyEvent.VK_D:{key[3] = false; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[4] = false; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[5] = false; keypressed = true; break;}
        }
    }

    public boolean isKeypressed() {
        return keypressed;
    }

    public boolean[] getKeys() {
        return key;
    }

    public void lostFocus(){
        for(int i = 0; i < key.length;i++){
            key[i] = false;
        }
    }
}
