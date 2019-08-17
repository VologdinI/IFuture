package net.proselyte.bookmanager.logic.сoncurrency.study;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CounterThread implements Runnable{
    int k=0;
    int p=0;
    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
    @Scheduled(fixedDelay = 1000*3)
    public void showStuff(){
        p++;
        System.out.println("Metronome "+p);
    }

    @Override
    public void run() {
        while(k<100000) {
            k++;
            //if(k==90000)
                System.out.println(k);
        }
        System.out.println(k);
    }
}
