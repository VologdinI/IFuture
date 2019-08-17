package net.proselyte.bookmanager.logic.сoncurrency.preTest;

import java.util.Date;

public class ThreadsTestFrontEnd implements Runnable{

    long time;
    public ThreadsTestFrontEnd(long time) {
        this.time = time;
    }
    @Override
    public void run() {
        Date date= new Date();
        long t=0;
        long dif=(new Date()).getTime()-date.getTime();
        while(dif<time*1000) {
            for (int i = 0; i < 1000 * time; i++) {
                t++;
            }
            dif = (new Date()).getTime() - date.getTime();
        }
    }
}
