package net.proselyte.bookmanager.logic.сoncurrency.preTest;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadsWrapper implements Runnable{
    List<Thread> threads;
    String state;

    public ThreadsWrapper() {
        state="";
        System.out.println("ThreadsWrapper constructor");
        List<InputTestFrontEnd> list= new ArrayList<>();
        list.add(new InputTestFrontEnd(3));
        list.add(new InputTestFrontEnd(4));
        list.add(new InputTestFrontEnd(17));
        threads = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            threads.add(new Thread(new ThreadsTestFrontEnd(list.get(i).getTime())));
        }
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public String getState() {
        return state;
    }

    @Scheduled(fixedDelay = 1000*3)
    public void getStatesFixedTime(){
        if(threads!=null) {
            String s = "" + threads.size();
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i).getState() == Thread.State.TERMINATED)
                    s += i;
            }
            state=s;
        }
    }
    public String getStatesInString(){
        if(threads==null)
            return "Nothing";
        String s=""+threads.size();
        for (int i = 0; i < threads.size(); i++) {
            if(threads.get(i).getState()== Thread.State.RUNNABLE)
            s+=i;
        }
        return s;
    }
    public void getStates(){
        for (int i = 0; i < threads.size(); i++) {
            System.out.print(threads.get(i).getName()+" "+threads.get(i).getState()+"\n");
        }
    }
    @Override
    public void run() {

        System.out.println(threads.get(0).getState());
        for (int i = 0; i <threads.size() ; i++) {
            threads.get(i).start();
        }
        System.out.println(threads.get(0).getState());
        for (int i = 0; i <threads.size() ; i++) {
            Date date= new Date();
            System.out.println(date.toString()+" "+threads.get(i).getName()+" "+threads.get(i).getId());
            threads.get(i).setPriority(7-i*2);
            /*try {
                threads.get(i).join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/
            System.out.println((new Date()).toString());
        }
        for (int i = 0; i <threads.size() ; i++) {
            Thread.State s=threads.get(i).getState();
            System.out.println(threads.get(i).getState());
        }

    }
}
