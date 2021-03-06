package com.example.demo1.logic.сoncurrency;


import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.text.TextSearcher;
import com.example.demo1.logic.TreePackage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ThreadsWrapper implements Runnable{
    List<Thread> threads;
    TreePackage treePackage;
    String state;
    String reqString;
    List<TextSearcher> textSearchers;

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
