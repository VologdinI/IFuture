package com.example.demo1.logic;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class LogFinder implements Finder{
    private TreePackage treePackage;

    @Autowired
    public LogFinder(TreePackage treePackage) {
        this.treePackage = treePackage;
    }

    public void fillTreePackage(String goalPackage, String extension){
        //затирать перед новым заполнением
        treePackage.setFileOfPackage(null);
        treePackage.setTreePackageList(null);
        treePackage.setFileAdvancedList(null);
        treePackage = findLocalStuff(treePackage, goalPackage, extension);
    }
    //костыль во имя спринга
    private TreePackage findLocalStuff(TreePackage treePackage, String goalPackage, String extension) {
        FileAdvanced file = new FileAdvanced(new File(goalPackage));
        FileAdvanced[] files = getListOfFiles(file,extension);
        FileAdvanced[] dirs = getListOfDirs(file);
        List<FileAdvanced> fileList=combineDirOrFiles(files,false);
        List<FileAdvanced> dirList= new ArrayList<>();
        for (int i = 0; i <dirs.length; i++) {
            if(isFileWithExtentionInsideDir(dirs[i],extension))
                dirList.add(dirs[i]);
        }
        if(this.treePackage ==treePackage) {
            treePackage.setFileOfPackage(file);
            treePackage.setFileAdvancedList(fileList);
            treePackage.setTreePackageList(null);
        }
        else
         treePackage= new TreePackage(file,fileList);
        List<TreePackage> treePackageList= new ArrayList<>();
        for (int i = 0; i < dirList.size(); i++) {
            treePackageList.add(findLocalStuff(new TreePackage(),
                    dirList.get(i).getFile().getAbsolutePath(),extension));
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
    //
    public static void makePackages(String existsPath, String pathForCreate){
        ArrayList<String> devidenPath = devidePath(pathForCreate);
        String pathTemp = ""+existsPath;
        for (int i = 0; i <devidenPath.size() ; i++) {
            pathTemp += devidenPath.get(i)+"\\".toCharArray()[0];
            File file = new File(pathTemp);
            file.mkdir();
        }
    }
    public static ArrayList<String> devidePath(String path){
        char[] cLocalPath = path.toCharArray();
        String s = "";
        ArrayList<String> devidenPath = new ArrayList<>();
        for (int i = 0; i < cLocalPath.length; i++) {
            if((cLocalPath[i] != "\\".toCharArray()[0])) {
                s+= cLocalPath[i];
            }else{
                devidenPath.add(s);
                s="";
            }
        }
        return devidenPath;
    }
    public static String changeSlashes(String path){
        char[] cLocalPath = path.toCharArray();
        String sReturn = "";
        for (int i = 0; i <cLocalPath.length ; i++) {
            if(cLocalPath[i]=="/".toCharArray()[0])
                sReturn+="\\";
            else
                sReturn+=cLocalPath[i];
        }
        return sReturn;
    }
    public static void makePackagesWithFile(String existsPath, String pathForCreate) {
        ArrayList<String> devidenPath = devidePath(pathForCreate);
        String localPath = "";
        for (int i = 0; i <devidenPath.size() ; i++) {
            localPath+=devidenPath.get(i)+File.separator;
        }
        makePackages(existsPath, localPath);
        File file = new File(existsPath+pathForCreate);
        try {
            file.createNewFile();
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    public static void deleteFolder(String pathOfFolder){
        File file = new File(pathOfFolder);
        try{
            FileUtils.deleteDirectory(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
