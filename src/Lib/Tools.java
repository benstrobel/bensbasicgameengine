package Lib;

public class Tools {

    public static void threadsleep(long ms){
        if(ms < 1){return;}
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
