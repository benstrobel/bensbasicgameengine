// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private boolean [] key;
    private boolean keyupdate = false;

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
    public static final int LEFT = 43;
    public static final int UP = 44;
    public static final int RIGHT = 45;
    public static final int DOWN = 46;
    public static final int DOT = 47;
    public static final int BACKSPACE = 48;


    public KeyListener(){
        key = new boolean [49];
    }

    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[Q] = true; keyupdate = true; break;}
            case KeyEvent.VK_W:{key[W] = true; keyupdate = true; break;}
            case KeyEvent.VK_E:{key[E] = true; keyupdate = true; break;}
            case KeyEvent.VK_R:{key[R] = true; keyupdate = true; break;}
            case KeyEvent.VK_T:{key[T] = true; keyupdate = true; break;}
            case KeyEvent.VK_Z:{key[Z] = true; keyupdate = true; break;}
            case KeyEvent.VK_U:{key[U] = true; keyupdate = true; break;}
            case KeyEvent.VK_I:{key[I] = true; keyupdate = true; break;}
            case KeyEvent.VK_O:{key[O] = true; keyupdate = true; break;}
            case KeyEvent.VK_P:{key[P] = true; keyupdate = true; break;}
            case KeyEvent.VK_A:{key[A] = true; keyupdate = true; break;}
            case KeyEvent.VK_S:{key[S] = true; keyupdate = true; break;}
            case KeyEvent.VK_D:{key[D] = true; keyupdate = true; break;}
            case KeyEvent.VK_F:{key[F] = true; keyupdate = true; break;}
            case KeyEvent.VK_G:{key[G] = true; keyupdate = true; break;}
            case KeyEvent.VK_H:{key[H] = true; keyupdate = true; break;}
            case KeyEvent.VK_J:{key[J] = true; keyupdate = true; break;}
            case KeyEvent.VK_K:{key[K] = true; keyupdate = true; break;}
            case KeyEvent.VK_L:{key[L] = true; keyupdate = true; break;}
            case KeyEvent.VK_Y:{key[Y] = true; keyupdate = true; break;}
            case KeyEvent.VK_X:{key[X] = true; keyupdate = true; break;}
            case KeyEvent.VK_C:{key[C] = true; keyupdate = true; break;}
            case KeyEvent.VK_V:{key[V] = true; keyupdate = true; break;}
            case KeyEvent.VK_B:{key[B] = true; keyupdate = true; break;}
            case KeyEvent.VK_N:{key[N] = true; keyupdate = true; break;}
            case KeyEvent.VK_M:{key[M] = true; keyupdate = true; break;}
            case KeyEvent.VK_1:{key[ONE] = true; keyupdate = true; break;}
            case KeyEvent.VK_2:{key[TWO] = true; keyupdate = true; break;}
            case KeyEvent.VK_3:{key[THREE] = true; keyupdate = true; break;}
            case KeyEvent.VK_4:{key[FOUR] = true; keyupdate = true; break;}
            case KeyEvent.VK_5:{key[FIVE] = true; keyupdate = true; break;}
            case KeyEvent.VK_6:{key[SIX] = true; keyupdate = true; break;}
            case KeyEvent.VK_7:{key[SEVEN] = true; keyupdate = true; break;}
            case KeyEvent.VK_8:{key[EIGHT] = true; keyupdate = true; break;}
            case KeyEvent.VK_9:{key[NINE] = true; keyupdate = true; break;}
            case KeyEvent.VK_0:{key[ZERO] = true; keyupdate = true; break;}
            case KeyEvent.VK_ESCAPE:{key[ESC] = true; keyupdate = true; break;}
            case KeyEvent.VK_SPACE:{key[SPACE] = true; keyupdate = true; break;}
            case KeyEvent.VK_SHIFT:{key[SHIFT] = true; keyupdate = true; break;}
            case KeyEvent.VK_ENTER:{key[ENTER] = true; keyupdate = true; break;}
            case KeyEvent.VK_CONTROL:{key[CTRL] = true; keyupdate = true; break;}
            case KeyEvent.VK_ALT:{key[ALT] = true; keyupdate = true; break;}
            case KeyEvent.VK_TAB:{key[TAB] = true; keyupdate = true; break;}
            case KeyEvent.VK_LEFT:{key[LEFT] = true; keyupdate = true; break;}
            case KeyEvent.VK_UP:{key[UP] = true; keyupdate = true; break;}
            case KeyEvent.VK_RIGHT:{key[RIGHT] = true; keyupdate = true; break;}
            case KeyEvent.VK_DOWN:{key[DOWN] = true; keyupdate = true; break;}
            case KeyEvent.VK_PERIOD:{key[DOT] = true; keyupdate = true; break;}
            case KeyEvent.VK_BACK_SPACE:{key[BACKSPACE] = true; keyupdate = true; break;}
        }
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:{key[Q] = false; keyupdate = true; break;}
            case KeyEvent.VK_W:{key[W] = false; keyupdate = true; break;}
            case KeyEvent.VK_E:{key[E] = false; keyupdate = true; break;}
            case KeyEvent.VK_R:{key[R] = false; keyupdate = true; break;}
            case KeyEvent.VK_T:{key[T] = false; keyupdate = true; break;}
            case KeyEvent.VK_Z:{key[Z] = false; keyupdate = true; break;}
            case KeyEvent.VK_U:{key[U] = false; keyupdate = true; break;}
            case KeyEvent.VK_I:{key[I] = false; keyupdate = true; break;}
            case KeyEvent.VK_O:{key[O] = false; keyupdate = true; break;}
            case KeyEvent.VK_P:{key[P] = false; keyupdate = true; break;}
            case KeyEvent.VK_A:{key[A] = false; keyupdate = true; break;}
            case KeyEvent.VK_S:{key[S] = false; keyupdate = true; break;}
            case KeyEvent.VK_D:{key[D] = false; keyupdate = true; break;}
            case KeyEvent.VK_F:{key[F] = false; keyupdate = true; break;}
            case KeyEvent.VK_G:{key[G] = false; keyupdate = true; break;}
            case KeyEvent.VK_H:{key[H] = false; keyupdate = true; break;}
            case KeyEvent.VK_J:{key[J] = false; keyupdate = true; break;}
            case KeyEvent.VK_K:{key[K] = false; keyupdate = true; break;}
            case KeyEvent.VK_L:{key[L] = false; keyupdate = true; break;}
            case KeyEvent.VK_Y:{key[Y] = false; keyupdate = true; break;}
            case KeyEvent.VK_X:{key[X] = false; keyupdate = true; break;}
            case KeyEvent.VK_C:{key[C] = false; keyupdate = true; break;}
            case KeyEvent.VK_V:{key[V] = false; keyupdate = true; break;}
            case KeyEvent.VK_B:{key[B] = false; keyupdate = true; break;}
            case KeyEvent.VK_N:{key[N] = false; keyupdate = true; break;}
            case KeyEvent.VK_M:{key[M] = false; keyupdate = true; break;}
            case KeyEvent.VK_1:{key[ONE] = false; keyupdate = true; break;}
            case KeyEvent.VK_2:{key[TWO] = false; keyupdate = true; break;}
            case KeyEvent.VK_3:{key[THREE] = false; keyupdate = true; break;}
            case KeyEvent.VK_4:{key[FOUR] = false; keyupdate = true; break;}
            case KeyEvent.VK_5:{key[FIVE] = false; keyupdate = true; break;}
            case KeyEvent.VK_6:{key[SIX] = false; keyupdate = true; break;}
            case KeyEvent.VK_7:{key[SEVEN] = false; keyupdate = true; break;}
            case KeyEvent.VK_8:{key[EIGHT] = false; keyupdate = true; break;}
            case KeyEvent.VK_9:{key[NINE] = false; keyupdate = true; break;}
            case KeyEvent.VK_0:{key[ZERO] = false; keyupdate = true; break;}
            case KeyEvent.VK_ESCAPE:{key[ESC] = false; keyupdate = true; break;}
            case KeyEvent.VK_SPACE:{key[SPACE] = false; keyupdate = true; break;}
            case KeyEvent.VK_SHIFT:{key[SHIFT] = false; keyupdate = true; break;}
            case KeyEvent.VK_ENTER:{key[ENTER] = false; keyupdate = true; break;}
            case KeyEvent.VK_CONTROL:{key[CTRL] = false; keyupdate = true; break;}
            case KeyEvent.VK_ALT:{key[ALT] = false; keyupdate = true; break;}
            case KeyEvent.VK_TAB:{key[TAB] = false; keyupdate = true; break;}
            case KeyEvent.VK_LEFT:{key[LEFT] = false; keyupdate = true; break;}
            case KeyEvent.VK_UP:{key[UP] = false; keyupdate = true; break;}
            case KeyEvent.VK_RIGHT:{key[RIGHT] = false; keyupdate = true; break;}
            case KeyEvent.VK_DOWN:{key[DOWN] = false; keyupdate = true; break;}
            case KeyEvent.VK_PERIOD:{key[DOT] = false; keyupdate = true; break;}
            case KeyEvent.VK_BACK_SPACE:{key[BACKSPACE] = false; keyupdate = true; break;}
        }
    }

    public boolean isKeyupdate() {
        return keyupdate;
    }

    public boolean[] getKeysAndReset() {
        keyupdate = false;
        return key;
    }

    public void lostFocus(){
        for(int i = 0; i < key.length;i++){
            key[i] = false;
        }
        keyupdate = true;
    }
}
