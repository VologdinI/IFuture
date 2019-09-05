package com.example.demo1.logic;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TreePackage {
    private FileAdvanced fileOfPackage;
    private List<TreePackage> treePackageList;
    private List<FileAdvanced> fileList;
    private static TreePackage treePackage;

    public TreePackage(FileAdvanced fileOfPackage, List<FileAdvanced> fileList) {
        this.fileOfPackage = fileOfPackage;
        this.fileList = fileList;
        this.treePackageList=null;
    }

    public TreePackage() {
    }

    public static TreePackage getTreePackage() {
        return treePackage;
    }

    public static void setTreePackage(TreePackage treePackage) {
        TreePackage.treePackage = treePackage;
    }

    public List<TreePackage> getTreePackageList() {
        return treePackageList;
    }

    public void setTreePackageList(List<TreePackage> treePackageList) {
        this.treePackageList = treePackageList;
    }

    public List<FileAdvanced> getFileAdvancedList() {
        return fileList;
    }

    public void setFileAdvancedList(List<FileAdvanced> fileList) {
        this.fileList = fileList;
    }

    public FileAdvanced getFileOfPackage() {
        return fileOfPackage;
    }

    public void setFileOfPackage(FileAdvanced fileOfPackage) {
        this.fileOfPackage = fileOfPackage;
    }


    public List<FileAdvanced> getListOfAllFiles(){
        List<FileAdvanced> files= new ArrayList<>();
        files.addAll(this.fileList);
        for (int i = 0; i <this.getTreePackageList().size() ; i++) {
            files.addAll(this.getTreePackageList().get(i).getListOfAllFiles());
        }
        return files;
    }

}
