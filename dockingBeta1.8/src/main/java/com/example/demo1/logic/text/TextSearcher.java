package com.example.demo1.logic.text;

import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.TreePackage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class TextSearcher implements Runnable{
    static TreePackage treePackage;
    FileAdvanced file;
    String requiredString;
    boolean isContaisReqS;
    int ID;

    public int getID() {
        return ID;
    }

    public FileAdvanced getFileAdvanced() {
        return file;
    }

    public void setFileAdvanced(FileAdvanced file) {
        this.file = file;
    }

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    public boolean isContaisReqS() {
        return isContaisReqS;
    }

    public void setContaisReqS(boolean contaisReqS) {
        isContaisReqS = contaisReqS;
    }

    public TextSearcher() {
    }

    public TextSearcher(FileAdvanced file, String requiredString) {
        this.file = file;
        this.requiredString = requiredString;
        isContaisReqS = false;
        ID = -1;
    }

    public TextSearcher(FileAdvanced file, String requiredString, TreePackage treePackage) {
        this.file = file;
        this.requiredString = requiredString;
        this.treePackage = treePackage;
        isContaisReqS = false;
        ID = -1;
    }
    //2
    //небольшой буфер
    //Java
    public boolean findStringInFile(File file, String requiredString) {
        try {
            FileReader fileReader = new FileReader(file); // поток который подключается к текстовому файлу
            BufferedReader bufferedReader = new BufferedReader(fileReader); // соединяем FileReader с BufferedReader
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                if (line.contains(requiredString)) {
                    System.out.println(line);
                    return true;
                }
            }
            bufferedReader.close(); // закрываем поток
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        synchronized (file) {
            isContaisReqS = findStringInFile(file.getFile(), requiredString);
        }
        if(isContaisReqS)
        synchronized (treePackage) {
            treePackage.setHighLevelsIsContainsRequiredString(file.getID());
        }
    }
}
