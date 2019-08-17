package net.proselyte.bookmanager.logic.сoncurrency.study;

import net.proselyte.bookmanager.controller.Config;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.InputTestFrontEnd;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.ThreadsTestFrontEnd;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.ThreadsWrapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultiRunApp {
    public static void undestandJoin(){
        DataSourceLoader dataSourceLoader= new DataSourceLoader();
        NetWorkConnectionLoader netWorkConnectionLoade= new NetWorkConnectionLoader();
        Thread thread0= new Thread(dataSourceLoader,"DataSourceLoader");
        Thread thread1= new Thread(netWorkConnectionLoade,"NetWorkConnectionLoader");

        thread0.start();
        thread1.start();
        try {
            thread1.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        try {
            thread0.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        for (int i = 0; i <3 ; i++) {

        }

        System.out.printf("Main has bean loaded %s \n",new Date());

    }
    public static void testThreads(){
        ThreadsWrapper threadsWrapper= new ThreadsWrapper();
        Thread thread= new Thread(threadsWrapper);
        thread.run();
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();//metronome
        //cant get smth from inner class;
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date());
                threadsWrapper.getStates();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    public static String testInnerThread(){
        CounterThread counterThread= new CounterThread();
        Thread thread=new Thread(counterThread);
        thread.run();
        return "yeah";
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        /*CounterThread counterThread=context.getBean("counterThread", CounterThread.class);
        Thread thread= new Thread(counterThread);*/
        //thread.run();
    }
}
