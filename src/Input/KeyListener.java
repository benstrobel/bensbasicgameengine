package Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private boolean [] key;
    private boolean keypressed = false;

    public KeyListener(){
        key = new boolean [43];
    }

    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[0] = true; keypressed = true; break;}
            case KeyEvent.VK_W:{key[1] = true; keypressed = true; break;}
            case KeyEvent.VK_E:{key[2] = true; keypressed = true; break;}
            case KeyEvent.VK_R:{key[3] = true; keypressed = true; break;}
            case KeyEvent.VK_T:{key[4] = true; keypressed = true; break;}
            case KeyEvent.VK_Z:{key[5] = true; keypressed = true; break;}
            case KeyEvent.VK_U:{key[6] = true; keypressed = true; break;}
            case KeyEvent.VK_I:{key[7] = true; keypressed = true; break;}
            case KeyEvent.VK_O:{key[8] = true; keypressed = true; break;}
            case KeyEvent.VK_P:{key[9] = true; keypressed = true; break;}
            case KeyEvent.VK_A:{key[10] = true; keypressed = true; break;}
            case KeyEvent.VK_S:{key[11] = true; keypressed = true; break;}
            case KeyEvent.VK_D:{key[12] = true; keypressed = true; break;}
            case KeyEvent.VK_F:{key[13] = true; keypressed = true; break;}
            case KeyEvent.VK_G:{key[14] = true; keypressed = true; break;}
            case KeyEvent.VK_H:{key[15] = true; keypressed = true; break;}
            case KeyEvent.VK_J:{key[16] = true; keypressed = true; break;}
            case KeyEvent.VK_K:{key[17] = true; keypressed = true; break;}
            case KeyEvent.VK_L:{key[18] = true; keypressed = true; break;}
            case KeyEvent.VK_Y:{key[19] = true; keypressed = true; break;}
            case KeyEvent.VK_X:{key[20] = true; keypressed = true; break;}
            case KeyEvent.VK_C:{key[21] = true; keypressed = true; break;}
            case KeyEvent.VK_V:{key[22] = true; keypressed = true; break;}
            case KeyEvent.VK_B:{key[23] = true; keypressed = true; break;}
            case KeyEvent.VK_N:{key[24] = true; keypressed = true; break;}
            case KeyEvent.VK_M:{key[25] = true; keypressed = true; break;}
            case KeyEvent.VK_1:{key[26] = true; keypressed = true; break;}
            case KeyEvent.VK_2:{key[27] = true; keypressed = true; break;}
            case KeyEvent.VK_3:{key[28] = true; keypressed = true; break;}
            case KeyEvent.VK_4:{key[29] = true; keypressed = true; break;}
            case KeyEvent.VK_5:{key[30] = true; keypressed = true; break;}
            case KeyEvent.VK_6:{key[31] = true; keypressed = true; break;}
            case KeyEvent.VK_7:{key[32] = true; keypressed = true; break;}
            case KeyEvent.VK_8:{key[33] = true; keypressed = true; break;}
            case KeyEvent.VK_9:{key[34] = true; keypressed = true; break;}
            case KeyEvent.VK_0:{key[35] = true; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[36] = true; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[37] = true; keypressed = true; break;}
            case KeyEvent.VK_SHIFT:{key[38] = true; keypressed = true; break;}
            case KeyEvent.VK_ENTER:{key[39] = true; keypressed = true; break;}
            case KeyEvent.VK_CONTROL:{key[40] = true; keypressed = true; break;}
            case KeyEvent.VK_ALT:{key[41] = true; keypressed = true; break;}
            case KeyEvent.VK_TAB:{key[42] = true; keypressed = true; break;}
        }
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[0] = false; keypressed = true; break;}
            case KeyEvent.VK_W:{key[1] = false; keypressed = true; break;}
            case KeyEvent.VK_E:{key[2] = false; keypressed = true; break;}
            case KeyEvent.VK_R:{key[3] = false; keypressed = true; break;}
            case KeyEvent.VK_T:{key[4] = false; keypressed = true; break;}
            case KeyEvent.VK_Z:{key[5] = false; keypressed = true; break;}
            case KeyEvent.VK_U:{key[6] = false; keypressed = true; break;}
            case KeyEvent.VK_I:{key[7] = false; keypressed = true; break;}
            case KeyEvent.VK_O:{key[8] = false; keypressed = true; break;}
            case KeyEvent.VK_P:{key[9] = false; keypressed = true; break;}
            case KeyEvent.VK_A:{key[10] = false; keypressed = true; break;}
            case KeyEvent.VK_S:{key[11] = false; keypressed = true; break;}
            case KeyEvent.VK_D:{key[12] = false; keypressed = true; break;}
            case KeyEvent.VK_F:{key[13] = false; keypressed = true; break;}
            case KeyEvent.VK_G:{key[14] = false; keypressed = true; break;}
            case KeyEvent.VK_H:{key[15] = false; keypressed = true; break;}
            case KeyEvent.VK_J:{key[16] = false; keypressed = true; break;}
            case KeyEvent.VK_K:{key[17] = false; keypressed = true; break;}
            case KeyEvent.VK_L:{key[18] = false; keypressed = true; break;}
            case KeyEvent.VK_Y:{key[19] = false; keypressed = true; break;}
            case KeyEvent.VK_X:{key[20] = false; keypressed = true; break;}
            case KeyEvent.VK_C:{key[21] = false; keypressed = true; break;}
            case KeyEvent.VK_V:{key[22] = false; keypressed = true; break;}
            case KeyEvent.VK_B:{key[23] = false; keypressed = true; break;}
            case KeyEvent.VK_N:{key[24] = false; keypressed = true; break;}
            case KeyEvent.VK_M:{key[25] = false; keypressed = true; break;}
            case KeyEvent.VK_1:{key[26] = false; keypressed = true; break;}
            case KeyEvent.VK_2:{key[27] = false; keypressed = true; break;}
            case KeyEvent.VK_3:{key[28] = false; keypressed = true; break;}
            case KeyEvent.VK_4:{key[29] = false; keypressed = true; break;}
            case KeyEvent.VK_5:{key[30] = false; keypressed = true; break;}
            case KeyEvent.VK_6:{key[31] = false; keypressed = true; break;}
            case KeyEvent.VK_7:{key[32] = false; keypressed = true; break;}
            case KeyEvent.VK_8:{key[33] = false; keypressed = true; break;}
            case KeyEvent.VK_9:{key[34] = false; keypressed = true; break;}
            case KeyEvent.VK_0:{key[35] = false; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[36] = false; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[37] = false; keypressed = true; break;}
            case KeyEvent.VK_SHIFT:{key[38] = false; keypressed = true; break;}
            case KeyEvent.VK_ENTER:{key[39] = false; keypressed = true; break;}
            case KeyEvent.VK_CONTROL:{key[40] = false; keypressed = true; break;}
            case KeyEvent.VK_ALT:{key[41] = false; keypressed = true; break;}
            case KeyEvent.VK_TAB:{key[42] = false; keypressed = true; break;}
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
