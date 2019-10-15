package com.example.demo1.logic;


import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//дерево файловой системы
@Component
public class TreePackage {
    private FileAdvanced fileOfPackage;
    private List<TreePackage> treePackageList;
    private List<FileAdvanced> fileList;

    public TreePackage(FileAdvanced fileOfPackage, List<FileAdvanced> fileList) {
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


    //получить все файлы(не включая папки)
    public List<FileAdvanced> getListOfAllFiles(){
        List<FileAdvanced> files= new ArrayList<>();
        files.addAll(this.fileList);
        for (int i = 0; i <this.getTreePackageList().size() ; i++) {
            files.addAll(this.getTreePackageList().get(i).getListOfAllFiles());
        }
        return files;
    }
    //все родители потомка с данным id получать true в поле isContainsRequiredString
    public boolean setHighLevelsIsContainsRequiredString(int id){
        boolean flag = false;
        for (int i = 0; i < this.treePackageList.size(); i++) {
            if(this.treePackageList.get(i).setHighLevelsIsContainsRequiredString(id))
                flag = true;
            if(this.treePackageList.get(i).getFileOfPackage().isContainsRequiredString())
                flag = true;
        }
        boolean flag1 = false;
        for (int i = 0; i < this.fileList.size(); i++) {
            if(this.fileList.get(i).getID()==id) {
                flag1=true;
                this.fileList.get(i).setContainsRequiredString(true);
            }
            if(this.fileList.get(i).isContainsRequiredString()) {
                flag1=true;
            }
        }
        this.fileOfPackage.setContainsRequiredString(flag1||flag);
        return flag1||flag;
    }

    public String toJson(){
        Gson gson = new Gson();
        String jsonTp="";
        jsonTp += gson.toJson(this);
        return jsonTp;
    }

    public String getNextBlock(int ID){
        String nextBlock = "";
        List<FileAdvanced> fileAdvanceds = this.getListOfAllFiles();
        for (int i = 0; i < fileAdvanceds.size() ; i++) {
            if(fileAdvanceds.get(i).getID() == ID){
                nextBlock = fileAdvanceds.get(i).getTextEfficientReader().getNextBlock();
            }
        }
        return nextBlock;
    }
}
