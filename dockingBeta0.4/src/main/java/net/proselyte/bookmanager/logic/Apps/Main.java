package net.proselyte.bookmanager.logic.Apps;

import net.proselyte.bookmanager.controller.Config;
import net.proselyte.bookmanager.logic.Finder;
import net.proselyte.bookmanager.logic.LogFinder;
import net.proselyte.bookmanager.logic.TextSearcher;
import net.proselyte.bookmanager.logic.TreePackage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
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
        List<List<File>> listList=logFinder.getListsOfFilesWithPriority(treePackage.getListOfAllFiles());
        context.close();
    }
}
