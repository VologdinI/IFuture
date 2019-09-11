package com.example.demo1.logic.сoncurrency.preTest;


import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.TextSearcher;
import com.example.demo1.logic.TreePackage;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadsWrapper implements Runnable{
    List<Thread> threads;
    TreePackage treePackage;
    String state;
    String reqString;
    List<TextSearcher> textSearchers;

    /*public ThreadsWrapper() {
        state="";
        System.out.println("ThreadsWrapper constructor");
        List<TextSearcher> list= new ArrayList<>();
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\BigBoyInside\\SmallBoy.log";
        String prePackage1="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\setup.log";
        String prePackage2="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\game.log";
        String fe="frontend";
        File f= new File(prePackage);
        File f1= new File(prePackage1);
        File f2= new File(prePackage2);
        list.add(new TextSearcher(f, fe));
        list.add(new TextSearcher(f1, fe));
        list.add(new TextSearcher(f2, fe));
        threads = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            threads.add(new Thread(list.get(i)));
        }
    }*/

    public ThreadsWrapper(){
    }

    public ThreadsWrapper(TreePackage treePackage, String reqString) {
        this.treePackage = treePackage;
        this.reqString = reqString;
        textSearchers = new ArrayList<>();
        List<FileAdvanced> fileAdvanceds = treePackage.getListOfAllFiles();
        for (int i = 0; i < fileAdvanceds.size(); i++) {
            textSearchers.add(new TextSearcher( fileAdvanceds.get(i), reqString, treePackage));
        }
        threads = new ArrayList<>();
        for (int i = 0; i <textSearchers.size() ; i++) {
            threads.add(new Thread(textSearchers.get(i)));
        }
    }

    public List<TextSearcher> getTextSearchers() {
        return textSearchers;
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
        for (int i = 0; i <threads.size() ; i++) {
            threads.get(i).start();
        }

        for (int i = 0; i <threads.size() ; i++) {
            System.out.println(threads.get(i).getState());
        }

    }
}
