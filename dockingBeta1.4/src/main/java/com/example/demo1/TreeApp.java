package com.example.demo1;

import com.example.demo1.logic.LogFinder;
import com.example.demo1.logic.TreePackage;

public class TreeApp {
    public static void main(String[] args) {
        String sReq="frontend";
        LogFinder logFinder = new LogFinder();
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        TreePackage treePackage=logFinder.findLocalStuff(prePackage, ".log");
        TreePackage.setTreePackage(treePackage);
        treePackage.setHighLevelsIsContainsRequiredString(23);

    }
}
