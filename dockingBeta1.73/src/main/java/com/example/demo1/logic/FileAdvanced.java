package com.example.demo1.logic;

import java.io.File;

public class FileAdvanced {
    private File file;
    private boolean isContainsRequiredString;
    private int ID;
    private static int lastID=0;

    public FileAdvanced(File file) {
        lastID++;
        this.file = file;
        isContainsRequiredString = false;
        this.ID = lastID;
    }

    public static void reloadID(){
        lastID = 0;
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isContainsRequiredString() {
        return isContainsRequiredString;
    }

    public void setContainsRequiredString(boolean containsRequiredString) {
        isContainsRequiredString = containsRequiredString;
    }

    public static int getLastID() {
        return lastID;
    }

    public int getID() {
        return ID;
    }

    public long length(){
        return this.getFile().length();
    }
}
