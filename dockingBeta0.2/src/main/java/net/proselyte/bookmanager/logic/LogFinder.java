package net.proselyte.bookmanager.logic;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;


public class LogFinder implements Finder{
    @Autowired
    private TreePackage treePackage;
    public LogFinder(TreePackage treePackage) {
        this.treePackage = treePackage;
    }
    @Override
    public List<String> findStuff(String goalPackage, String extension) {
        return null;
    }

    //почему он просит переопределить эти методы? Они из другого интерфейса.
    @Override
    public String findStringInFile(File file, String requiredString) throws Exception {
        return null;
    }
    @Override
    public String getSpaceNearGoalPlace() {
        return null;
    }

    @Override
    public TreePackage findLocalStuff(String goalPackage, String extension) {
        File file = new File(goalPackage);
        File[] files = getListOfFiles(file,extension);
        File[] dirs = getListOfDirs(file,extension);
        List<File> fileList=combineDirOrFiles(files,false);
        List<File> dirList= new ArrayList<>();
        for (int i = 0; i <dirs.length; i++) {
            if(isFileWithExtentionInsideDir(dirs[i],extension))
                dirList.add(dirs[i]);
        }
        TreePackage treePackage= new TreePackage(file,fileList);
        List<TreePackage> treePackageList= new ArrayList<>();
        for (int i = 0; i < dirList.size(); i++) {
            treePackageList.add(findLocalStuff(dirList.get(i).getAbsolutePath(),extension));
        }
        treePackage.setTreePackageList(treePackageList);
        return treePackage;
    }
    public File[] getListOfDirs(File file, String extension){
        File[] files = file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        //File[] files = file.listFiles((dir1, name) -> dir1.isDirectory());
        return files;
    }
    public File[] getListOfFiles(File file, String extension){
        File[] files = file.listFiles(new FilenameFilter() {
            public boolean accept(File file, String name) {
                return name.endsWith(extension);
            }
        });
        return files;
    }
    public List<File> combineDirOrFiles(File[] files, boolean isDir){//выдает массив файлов или папок
        List<File> fileList= new ArrayList<>();
        if(isDir) {
            for (int i = 0; i < files.length; i++)
                if (files[i].isDirectory())
                    fileList.add(files[i]);
        }
        else
            for (int i = 0; i <files.length ; i++)
                if(files[i].isFile())
                    fileList.add(files[i]);
        return fileList;
    }
    public boolean isFileWithExtentionInsideDir(File file,String extention){
        File[] files=getListOfFiles(file,extention);
        File[] dirs=getListOfDirs(file,extention);
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

    @Override
    public List<List<File>> getFileStructure(File[] files) {
        List<List<File>> localStuff=new ArrayList<>();
        int amountOfFolders=0;
        for (int i = 0; i <files.length ; i++) {
            if(files[i].isFile())
                localStuff=new ArrayList<>();
        }
        return null;
    }
}
