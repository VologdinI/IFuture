package net.proselyte.bookmanager.logic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception{
        /*String log=".log";
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        String packWithInsideStuff="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\HiddenLogInside\\";
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(Config.class);
        Finder finder =context.getBean("logFinder",LogFinder.class);
        TreePackage treePackage=finder.findLocalStuff(prePackage, log);

        Searcher searcher= context.getBean("searcher",TextSearcher.class);
        String requieredString="[2";
        File file =treePackage.getFileList().get(0);
        String sFile=searcher.findStringInFile(file, requieredString);
        System.out.println(sFile.contains(requieredString));
        System.out.println((searcher).getSpaceNearGoalPlace());
        context.close();*/
    }
}
