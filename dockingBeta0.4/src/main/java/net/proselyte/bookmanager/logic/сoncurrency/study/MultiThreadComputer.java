package net.proselyte.bookmanager.logic.сoncurrency.study;

public class MultiThreadComputer implements Runnable{


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId());
        for (int i = 0; i <5 ; i++) {
            System.out.printf("%s %d \n",Thread.currentThread().getName(),i);
        }
    }
}
