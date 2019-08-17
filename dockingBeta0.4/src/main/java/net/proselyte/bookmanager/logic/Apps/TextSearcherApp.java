package net.proselyte.bookmanager.logic.Apps;

import net.proselyte.bookmanager.controller.Config;
import net.proselyte.bookmanager.logic.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TextSearcherApp {
    public static void main(String[] args) {
        String log=".log";
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        String packWithInsideStuff="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\HiddenLogInside\\";
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(Config.class);
        LogFinder logFinder =context.getBean("logFinder", LogFinder.class);
        TreePackage treePackage=logFinder.findLocalStuff(prePackage, log);
        File file =treePackage.getFileList().get(0);
        List<String> packages=new ArrayList<>();
        for (int i = 0; i < treePackage.getTreePackageList().size(); i++) {
            packages.add(treePackage.getTreePackageList().get(i).getFileOfPackage().getName());
        }
        treePackage.setLevelOfAllPackages();
        TreePackage.setTreePackage(treePackage);

        TextSearcher textSearcher=context.getBean("textSearcher",TextSearcher.class);
        //List<List<File>> listList=logFinder.getListsOfFilesWithPriority(treePackage.getListOfAllFiles());
        List<File> fileList=treePackage.getListOfAllFiles().stream().sorted(Comparator.comparingLong(File::length)).collect(Collectors.toList());

        TextSearcherNoThreading textSearcherNoThreading= new TextSearcherNoThreading("!",fileList);
        Thread thread= new Thread(textSearcherNoThreading);

        List<TextSearcherFileIsString> list=new ArrayList<>();
        thread.start();
        /*try{
            thread.join(3000);
            list.addAll(textSearcherNoThreading.getListResults());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFile().getName()+" "+list.get(i).isContainsRequiredString());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/

        while(thread.isInterrupted()) {
            list.addAll(textSearcherNoThreading.getListResults());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFile().getName() + " " + list.get(i).isContainsRequiredString());
            }
            list.clear();
            list.addAll(textSearcherNoThreading.getListResultsBuffered());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFile().getName() + " " + list.get(i).isContainsRequiredString());
            }
        }
        context.close();
    }

}
