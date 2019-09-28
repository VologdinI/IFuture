package com.example.demo1.logic.text;

import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.TreePackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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

    public TextSearcher(FileAdvanced file, String requiredString, TreePackage treePackage) {
        this.file = file;
        this.requiredString = requiredString;
        this.treePackage = treePackage;
        isContaisReqS = false;
        ID = -1;
    }

    private boolean findStringInFile(File file, String requiredString)  {
        try(FileReader fileReader= new FileReader(file)) {
            Scanner scanner = new Scanner(fileReader);
            String s = "";
            int i = 0;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.contains(requiredString))
                    return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        isContaisReqS = findStringInFile(file.getFile(), requiredString);
        if(isContaisReqS)
        synchronized (treePackage) {
            treePackage.setHighLevelsIsContainsRequiredString(file.getID());
        }
    }
}
