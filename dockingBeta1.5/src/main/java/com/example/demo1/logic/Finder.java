package com.example.demo1.logic;

import java.io.File;
import java.util.List;

public interface Finder{
    public TreePackage findLocalStuff(String goalPackage, String extension);
    public boolean isFileWithExtentionInside(File file, String extention);
}
