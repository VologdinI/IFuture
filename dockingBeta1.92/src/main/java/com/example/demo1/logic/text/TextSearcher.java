package com.example.demo1.logic.text;

import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.TreePackage;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TextSearcher implements Runnable{
    static TreePackage treePackage;
    FileAdvanced file;
    String requiredString;
    boolean isContaisReqS;

    long currentPlaceInText;
    boolean isEndOfFileReached;
    ArrayList<String> arOverlaps;


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

    public ArrayList<String> getArOverlaps() {
        return arOverlaps;
    }

    public TextSearcher() {
    }

    public TextSearcher(FileAdvanced file, String requiredString) {
        this.file = file;
        this.requiredString = requiredString;
        isContaisReqS = false;
        arOverlaps = new ArrayList<>();
        currentPlaceInText = 0;
        isEndOfFileReached = false;
    }

    public TextSearcher(FileAdvanced file, String requiredString, TreePackage treePackage) {
        this.file = file;
        this.requiredString = requiredString;
        this.treePackage = treePackage;
        isContaisReqS = false;
        arOverlaps = new ArrayList<>();
        currentPlaceInText = 0;
        isEndOfFileReached = false;
    }
    //2
    //небольшой буфер
    //Java
    public boolean findStringInFile() {
        BufferedReader bufferedReader = null;
        RandomAccessFile raf = null;
        Reader reader = null;
        try {
            raf = new RandomAccessFile(file.getFile(), "r");
            raf.seek(currentPlaceInText);
            reader = Channels.newReader(raf.getChannel(), "ISO-8859-1");//правоверная, всеобъемлющая кодировка(но русский она инорирует)
            bufferedReader  = new BufferedReader(reader);
            String line;
            while (true) { //(line = bufferedReader.readLine()) != null
                line = bufferedReader.readLine();
                if(line != null) {
                    if (line.contains(requiredString)) {
                        System.out.println(line + " lenght" + line.length());
                        arOverlaps.add(line);
                        currentPlaceInText += line.length();
                        currentPlaceInText+=2;
                        return true;
                    }
                    currentPlaceInText += line.length();
                }
                else
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close(); // закрываем поток
                reader.close();
                raf.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    //идея в том чтобы искать вхождения партиями по N вхождений
    public int makeMoreOverlaps(){
        int i=0;
        while(i < 200&&findStringInFile()) {
            i++;
        }
        return i;
    }

    @Override
    public void run() {
        synchronized (file) {
            isContaisReqS = findStringInFile();
        }
        if(isContaisReqS)
        synchronized (treePackage) {
            treePackage.setHighLevelsIsContainsRequiredString(file.getID());
        }
    }
}
