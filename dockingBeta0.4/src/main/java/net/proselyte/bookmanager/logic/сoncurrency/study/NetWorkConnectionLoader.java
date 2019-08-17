package net.proselyte.bookmanager.logic.сoncurrency.study;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NetWorkConnectionLoader implements Runnable{
    @Override
    public void run() {
        System.out.printf("Begging network stuff %s \n",new Date());
        try{
            TimeUnit.SECONDS.sleep(4);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf("Ending network stuff %s \n",new Date());
    }
}
