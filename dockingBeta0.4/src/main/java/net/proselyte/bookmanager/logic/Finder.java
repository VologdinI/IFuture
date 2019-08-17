package net.proselyte.bookmanager.logic;

import java.io.File;
import java.util.List;

public interface Finder{
    public List<String> findStuff(String goalPackage, String extension);
    public TreePackage findLocalStuff(String goalPackage, String extension);
    public boolean isFileWithExtentionInside(File file, String extention);
    public List<List<File>> getFileStructure(File[] files);
}
