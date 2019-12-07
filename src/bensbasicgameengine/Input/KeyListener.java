package bensbasicgameengine.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private boolean [] key;
    private boolean keypressed = false;

    public static final int Q = 0;
    public static final int W = 1;
    public static final int E = 2;
    public static final int R = 3;
    public static final int T = 4;
    public static final int Z = 5;
    public static final int U = 6;
    public static final int I = 7;
    public static final int O = 8;
    public static final int P = 9;
    public static final int A = 10;
    public static final int S = 11;
    public static final int D = 12;
    public static final int F = 13;
    public static final int G = 14;
    public static final int H = 15;
    public static final int J = 16;
    public static final int K = 17;
    public static final int L = 18;
    public static final int Y = 19;
    public static final int X = 20;
    public static final int C = 21;
    public static final int V = 22;
    public static final int B = 23;
    public static final int N = 24;
    public static final int M = 25;
    public static final int ONE = 26;
    public static final int TWO = 27;
    public static final int THREE = 28;
    public static final int FOUR = 29;
    public static final int FIVE = 30;
    public static final int SIX = 31;
    public static final int SEVEN = 32;
    public static final int EIGHT = 33;
    public static final int NINE = 34;
    public static final int ZERO = 35;
    public static final int ESC = 36;
    public static final int SPACE = 37;
    public static final int SHIFT = 38;
    public static final int ENTER = 39;
    public static final int CTRL = 40;
    public static final int ALT = 41;
    public static final int TAB = 42;

    public KeyListener(){
        key = new boolean [43];
    }

    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[Q] = true; keypressed = true; break;}
            case KeyEvent.VK_W:{key[W] = true; keypressed = true; break;}
            case KeyEvent.VK_E:{key[E] = true; keypressed = true; break;}
            case KeyEvent.VK_R:{key[R] = true; keypressed = true; break;}
            case KeyEvent.VK_T:{key[T] = true; keypressed = true; break;}
            case KeyEvent.VK_Z:{key[Z] = true; keypressed = true; break;}
            case KeyEvent.VK_U:{key[U] = true; keypressed = true; break;}
            case KeyEvent.VK_I:{key[I] = true; keypressed = true; break;}
            case KeyEvent.VK_O:{key[O] = true; keypressed = true; break;}
            case KeyEvent.VK_P:{key[P] = true; keypressed = true; break;}
            case KeyEvent.VK_A:{key[A] = true; keypressed = true; break;}
            case KeyEvent.VK_S:{key[S] = true; keypressed = true; break;}
            case KeyEvent.VK_D:{key[D] = true; keypressed = true; break;}
            case KeyEvent.VK_F:{key[F] = true; keypressed = true; break;}
            case KeyEvent.VK_G:{key[G] = true; keypressed = true; break;}
            case KeyEvent.VK_H:{key[H] = true; keypressed = true; break;}
            case KeyEvent.VK_J:{key[J] = true; keypressed = true; break;}
            case KeyEvent.VK_K:{key[K] = true; keypressed = true; break;}
            case KeyEvent.VK_L:{key[L] = true; keypressed = true; break;}
            case KeyEvent.VK_Y:{key[Y] = true; keypressed = true; break;}
            case KeyEvent.VK_X:{key[X] = true; keypressed = true; break;}
            case KeyEvent.VK_C:{key[C] = true; keypressed = true; break;}
            case KeyEvent.VK_V:{key[V] = true; keypressed = true; break;}
            case KeyEvent.VK_B:{key[B] = true; keypressed = true; break;}
            case KeyEvent.VK_N:{key[N] = true; keypressed = true; break;}
            case KeyEvent.VK_M:{key[M] = true; keypressed = true; break;}
            case KeyEvent.VK_1:{key[ONE] = true; keypressed = true; break;}
            case KeyEvent.VK_2:{key[TWO] = true; keypressed = true; break;}
            case KeyEvent.VK_3:{key[THREE] = true; keypressed = true; break;}
            case KeyEvent.VK_4:{key[FOUR] = true; keypressed = true; break;}
            case KeyEvent.VK_5:{key[FIVE] = true; keypressed = true; break;}
            case KeyEvent.VK_6:{key[SIX] = true; keypressed = true; break;}
            case KeyEvent.VK_7:{key[SEVEN] = true; keypressed = true; break;}
            case KeyEvent.VK_8:{key[EIGHT] = true; keypressed = true; break;}
            case KeyEvent.VK_9:{key[NINE] = true; keypressed = true; break;}
            case KeyEvent.VK_0:{key[ZERO] = true; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[ESC] = true; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[SPACE] = true; keypressed = true; break;}
            case KeyEvent.VK_SHIFT:{key[SHIFT] = true; keypressed = true; break;}
            case KeyEvent.VK_ENTER:{key[ENTER] = true; keypressed = true; break;}
            case KeyEvent.VK_CONTROL:{key[CTRL] = true; keypressed = true; break;}
            case KeyEvent.VK_ALT:{key[ALT] = true; keypressed = true; break;}
            case KeyEvent.VK_TAB:{key[TAB] = true; keypressed = true; break;}
        }
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[Q] = false; keypressed = true; break;}
            case KeyEvent.VK_W:{key[W] = false; keypressed = true; break;}
            case KeyEvent.VK_E:{key[E] = false; keypressed = true; break;}
            case KeyEvent.VK_R:{key[R] = false; keypressed = true; break;}
            case KeyEvent.VK_T:{key[T] = false; keypressed = true; break;}
            case KeyEvent.VK_Z:{key[Z] = false; keypressed = true; break;}
            case KeyEvent.VK_U:{key[U] = false; keypressed = true; break;}
            case KeyEvent.VK_I:{key[I] = false; keypressed = true; break;}
            case KeyEvent.VK_O:{key[O] = false; keypressed = true; break;}
            case KeyEvent.VK_P:{key[P] = false; keypressed = true; break;}
            case KeyEvent.VK_A:{key[A] = false; keypressed = true; break;}
            case KeyEvent.VK_S:{key[S] = false; keypressed = true; break;}
            case KeyEvent.VK_D:{key[D] = false; keypressed = true; break;}
            case KeyEvent.VK_F:{key[F] = false; keypressed = true; break;}
            case KeyEvent.VK_G:{key[G] = false; keypressed = true; break;}
            case KeyEvent.VK_H:{key[H] = false; keypressed = true; break;}
            case KeyEvent.VK_J:{key[J] = false; keypressed = true; break;}
            case KeyEvent.VK_K:{key[K] = false; keypressed = true; break;}
            case KeyEvent.VK_L:{key[L] = false; keypressed = true; break;}
            case KeyEvent.VK_Y:{key[Y] = false; keypressed = true; break;}
            case KeyEvent.VK_X:{key[X] = false; keypressed = true; break;}
            case KeyEvent.VK_C:{key[C] = false; keypressed = true; break;}
            case KeyEvent.VK_V:{key[V] = false; keypressed = true; break;}
            case KeyEvent.VK_B:{key[B] = false; keypressed = true; break;}
            case KeyEvent.VK_N:{key[N] = false; keypressed = true; break;}
            case KeyEvent.VK_M:{key[M] = false; keypressed = true; break;}
            case KeyEvent.VK_1:{key[ONE] = false; keypressed = true; break;}
            case KeyEvent.VK_2:{key[TWO] = false; keypressed = true; break;}
            case KeyEvent.VK_3:{key[THREE] = false; keypressed = true; break;}
            case KeyEvent.VK_4:{key[FOUR] = false; keypressed = true; break;}
            case KeyEvent.VK_5:{key[FIVE] = false; keypressed = true; break;}
            case KeyEvent.VK_6:{key[SIX] = false; keypressed = true; break;}
            case KeyEvent.VK_7:{key[SEVEN] = false; keypressed = true; break;}
            case KeyEvent.VK_8:{key[EIGHT] = false; keypressed = true; break;}
            case KeyEvent.VK_9:{key[NINE] = false; keypressed = true; break;}
            case KeyEvent.VK_0:{key[ZERO] = false; keypressed = true; break;}
            case KeyEvent.VK_ESCAPE:{key[ESC] = false; keypressed = true; break;}
            case KeyEvent.VK_SPACE:{key[SPACE] = false; keypressed = true; break;}
            case KeyEvent.VK_SHIFT:{key[SHIFT] = false; keypressed = true; break;}
            case KeyEvent.VK_ENTER:{key[ENTER] = false; keypressed = true; break;}
            case KeyEvent.VK_CONTROL:{key[CTRL] = false; keypressed = true; break;}
            case KeyEvent.VK_ALT:{key[ALT] = false; keypressed = true; break;}
            case KeyEvent.VK_TAB:{key[TAB] = false; keypressed = true; break;}
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
