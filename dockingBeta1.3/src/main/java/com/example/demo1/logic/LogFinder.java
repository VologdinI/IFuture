package com.example.demo1.logic;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class LogFinder implements Finder{
    @Override
    public TreePackage findLocalStuff(String goalPackage, String extension) {
        FileAdvanced file = new FileAdvanced(new File(goalPackage));
        FileAdvanced[] files = getListOfFiles(file,extension);
        FileAdvanced[] dirs = getListOfDirs(file);
        List<FileAdvanced> fileList=combineDirOrFiles(files,false);
        List<FileAdvanced> dirList= new ArrayList<>();
        for (int i = 0; i <dirs.length; i++) {
            if(isFileWithExtentionInsideDir(dirs[i],extension))
                dirList.add(dirs[i]);
        }
        TreePackage treePackage= new TreePackage(file,fileList);
        List<TreePackage> treePackageList= new ArrayList<>();
        for (int i = 0; i < dirList.size(); i++) {
            treePackageList.add(findLocalStuff(dirList.get(i).getFile().getAbsolutePath(),extension));
        }
        treePackage.setTreePackageList(treePackageList);
        return treePackage;
    }
    public FileAdvanced[] getListOfDirs(FileAdvanced file){//govnocode refactor
        File[] files = file.getFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        FileAdvanced[] fileAdvanceds=new FileAdvanced[files.length];
        for (int i = 0; i < files.length; i++) {
            fileAdvanceds[i]=new FileAdvanced(files[i]);
        }
        return fileAdvanceds;
    }
    public FileAdvanced[] getListOfFiles(FileAdvanced file, String extension){
        File[] files = file.getFile().listFiles((d, s) -> {
            return s.endsWith(extension);
        });
        FileAdvanced[] fileAdvanceds =new FileAdvanced[files.length];
        for (int i = 0; i < fileAdvanceds.length; i++) {
            fileAdvanceds[i]=new FileAdvanced(files[i]);
        }
        return fileAdvanceds;
    }
    public List<FileAdvanced> combineDirs(FileAdvanced[] files, boolean isDir){//выдает массив папок
        List<FileAdvanced> fileList= new ArrayList<>();
            for (int i = 0; i < files.length; i++)
                if (files[i].getFile().isDirectory())
                    fileList.add(files[i]);
        return fileList;
    }
    //разнести
    public List<FileAdvanced> combineDirOrFiles(FileAdvanced[] files, boolean isDir){//выдает массив файлов или папок
        List<FileAdvanced> fileList= new ArrayList<>();
        if(isDir) {
            for (int i = 0; i < files.length; i++)
                if (files[i].getFile().isDirectory())
                    fileList.add(files[i]);
        }
        else
            for (int i = 0; i <files.length ; i++)
                if(files[i].getFile().isFile())
                    fileList.add(files[i]);
        return fileList;
    }
    public boolean isFileWithExtentionInsideDir(FileAdvanced file,String extention){
        FileAdvanced[] files=getListOfFiles(file,extention);
        FileAdvanced[] dirs=getListOfDirs(file);
        if(files.length>0)
            return true;
        if(dirs.length>0) {
            for (int i = 0; i < dirs.length; i++) {
                if(isFileWithExtentionInsideDir(dirs[i], extention))
                    return true;
            }
        }
        else
            return false;
        return false;//никогда не дойдет сюда
    }
    @Override
    public boolean isFileWithExtentionInside(File file, String extention) {
        File[] files = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".log");
            }
        });
        int iDirAmount=0;
        for (int i = 0; i <files.length ; i++) {
            if(files[i].isDirectory())
            iDirAmount++;
        }
        if((files.length-iDirAmount)!=0)
            return true;
        else
            return false;
    }

    //for concurrency stuff
    public List<List<FileAdvanced>> getListsOfFilesWithPriority(List<FileAdvanced> files){
        List<FileAdvanced> fileList=files.stream().sorted(Comparator.comparingLong(FileAdvanced::length)).collect(Collectors.toList());
        List<List<FileAdvanced>> listListFiles=new ArrayList<>();
        List<FileAdvanced> tempFileList= new ArrayList<>();

        int iSizeBricksForDivisionMB=100;//hardcoded
        int currentSizeOfListKB=0;
        int coefficientBricks=1;//hardcoded
        int i=0;
        while(i<fileList.size()) {
            if(iSizeBricksForDivisionMB*coefficientBricks*1024*1024-currentSizeOfListKB-fileList.get(i).length()>0){
                tempFileList.add(fileList.get(i));
                currentSizeOfListKB+=fileList.get(i).length();
                i++;
            }
            else{
                if(tempFileList.size()==0){
                    coefficientBricks++;
                }else {
                    listListFiles.add(tempFileList);
                    tempFileList = new ArrayList<>();
                }
            }
        }
        return listListFiles;
    }
}
