package com.example.demo1.logic.text;

import com.example.demo1.logic.FileAdvanced;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("c:\\Users\\Kano\\Downloads\\LogRepository\\serverLogRepository\\logExample\\testTS.log");
        FileAdvanced fileAdvanced = new FileAdvanced(file);
        TextSearcher textSearcher = new TextSearcher(fileAdvanced, "[2");

        /*System.out.println(textSearcher.makeMoreOverlaps());
        System.out.println(textSearcher.makeMoreOverlaps());
        System.out.println(textSearcher.makeMoreOverlaps());*/
        int q;
        while ((q =textSearcher.makeMoreOverlaps())>0){
            System.out.println(q);
        }
    }
}
