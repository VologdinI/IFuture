package com.example.demo1.logic.text;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TextSearcherTestApp {
    public static void main(String[] args) throws IOException {
        Date start = new Date();
        String smallBoyPath = "c:\\Users\\Kano\\Downloads\\serverLogRepository\\logExample\\BigBoyInside\\SmallBoy.log";
        String realManPath = "c:\\Users\\Kano\\Downloads\\serverLogRepository\\logExample\\BigBoyInside\\BigBoy.log";
        String path = "c:\\Users\\Kano\\Downloads\\serverLogRepositoryTests\\logExample\\setup.log";
        File file = new File(realManPath);
        TextSearcher textSearcher = new TextSearcher();
        textSearcher.findStringInFile(file, "frontend");
        Date end = new Date();
        System.out.println((end.getTime() - start.getTime()));//1000
        //для тестов был использован smallBoy 447 Мб искомая строка появляется ближе к концу
        //Scanner с использованием contains и лишней строчкой - 31227
        //буффер с использованием contains - 6474 лучшее 6075
        //Apache Commons IO с использованием contains - 7026
        //буффер с использованием KnuthMorrisPrattSearch 7026
        //буффер с использованием BoyerMooreHorspoolSimpleSearch 10350
        //буффер с использованием  BoyerMooreHorspoolSearch 13670
    }
}
