package net.proselyte.bookmanager.logic;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TreePackage {
    private File fileOfPackage;
    private List<TreePackage> treePackageList;
    private List<File> fileList;
    private String levelOfPackage;
    private static TreePackage treePackage;

    public String getLevelOfPackage() {
        return levelOfPackage;
    }

    public void setLevelOfPackage(String levelOfPackage) {
        this.levelOfPackage = levelOfPackage;
    }

    public static TreePackage getTreePackage() {
        return treePackage;
    }

    public static void setTreePackage(TreePackage treePackage) {
        TreePackage.treePackage = treePackage;
    }

    public TreePackage(File fileOfPackage, List<File> fileList) {
        this.fileOfPackage = fileOfPackage;
        this.fileList = fileList;
        this.treePackageList=null;
    }

    public TreePackage() {
    }
    public List<TreePackage> getTreePackageList() {
        return treePackageList;
    }

    public void setTreePackageList(List<TreePackage> treePackageList) {
        this.treePackageList = treePackageList;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public File getFileOfPackage() {
        return fileOfPackage;
    }

    public void setFileOfPackage(File fileOfPackage) {
        this.fileOfPackage = fileOfPackage;
    }

    public String getLeverOfPackage() {
        return levelOfPackage;
    }

    public void setLeverOfPackage(String leverOfPackage) {
        this.levelOfPackage=leverOfPackage;
    }
    public void setLevelOfAllSubpackages( String folder) {
            folder="sub"+folder;
            for (int i = 0; i <this.getTreePackageList().size() ; i++) {
                int number=i+1;
                this.getTreePackageList().get(i).setLeverOfPackage(folder+number);
                this.getTreePackageList().get(i).setLevelOfAllSubpackages(folder);
            }
    }
    public void setLevelOfAllPackages() {
        this.setLeverOfPackage("mainfolder");
        this.setLevelOfAllSubpackages("folder");
    }

    public List<File> getListOfAllFiles(){
        List<File> files= new ArrayList<>();
        files.addAll(this.fileList);
        for (int i = 0; i <this.getTreePackageList().size() ; i++) {
            files.addAll(this.getTreePackageList().get(i).getListOfAllFiles());
        }
        return files;
    }

}
