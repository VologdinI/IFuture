package com.example.demo1.logic.text;

import com.example.demo1.logic.FileAdvanced;
import com.example.demo1.logic.TreePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TextEfficientReader {
    //для постепенной подгрузки текста в редактор - меньшей загрузке сервера

    private TreePackage treePackage;
    @Value("${bufferForTextEfficient}")
    public  int bufferForTextEfficient;//выражается в строках

    //пока сделаем просто чтение теста из файла по id
    private boolean isTreePackageHaveId(TreePackage treePackage, int id)  {
        List<FileAdvanced> fileList = treePackage.getListOfAllFiles();
        for (int i = 0; i < fileList.size(); i++) {
            if(fileList.get(i).getID()==id){
                return true;
            }
        }
        return false;
    }

    private File findFileById(TreePackage treePackage, int id)  {
        File file = treePackage.getFileOfPackage().getFile();
        List<FileAdvanced> fileList = treePackage.getListOfAllFiles();
        for (int i = 0; i < fileList.size(); i++) {
            if(fileList.get(i).getID()==id){
                return fileList.get(i).getFile();
            }
        }
        return file;//не дойдет сюда, из-за isTreePackageHaveId
    }

    private String getContentOfFile(File file){
        String sReturn = "";
        try(FileReader fileReader= new FileReader(file)) {
            Scanner scanner = new Scanner(fileReader);
            while(scanner.hasNextLine())
                sReturn+=scanner.nextLine()+"\n";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sReturn;
    }

    public String sendContentOfFile(TreePackage treePackage, int id){
        String sReturn="";
        if(isTreePackageHaveId(treePackage, id))
            sReturn += getContentOfFile(findFileById(treePackage, id));
        else
            sReturn = "no such file";
        return sReturn;
    }
}
