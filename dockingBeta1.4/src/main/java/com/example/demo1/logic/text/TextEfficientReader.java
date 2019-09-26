package com.example.demo1.logic.text;

import com.example.demo1.logic.TreePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

@Component
public class TextEfficientReader {
    //для постепенной подгрузки текста в редактор - меньшей загрузке сервера

    private TreePackage treePackage;
    @Value("${bufferForTextEfficient}")
    public  int bufferForTextEfficient;//выражается в строках

    //пока сделаем просто чтение теста из файла по id
    private boolean isTreePackageHaveId(TreePackage treePackage, int id)  {
        for (int i = 0; i < treePackage.getFileAdvancedList().size(); i++) {
            if(treePackage.getFileAdvancedList().get(i).getID()==id){
                return true;
            }
        }
        for (int i = 0; i < treePackage.getTreePackageList().size(); i++) {
            return isTreePackageHaveId(treePackage.getTreePackageList().get(i), id);
        }
        return false;
    }

    private File findFileById(TreePackage treePackage, int id)  {
        File file = treePackage.getFileOfPackage().getFile();
        for (int i = 0; i < treePackage.getFileAdvancedList().size(); i++) {
            if(treePackage.getFileAdvancedList().get(i).getID()==id){
                return treePackage.getFileAdvancedList().get(i).getFile();
            }
        }
        for (int i = 0; i < treePackage.getTreePackageList().size(); i++) {
            return findFileById(treePackage.getTreePackageList().get(i), id);
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
    /*String s = "";
        try(FileReader fileReader= new FileReader(file)) {
        Scanner scanner = new Scanner(fileReader);
        int i = 0;
        for (int j = 0; j < lines; j++) {
            scanner.nextLine();
            if (!scanner.hasNextLine()) {
                break;
            }
        }
        s+=scanner.nextLine();
    }
        catch (Exception e){
        e.printStackTrace();
    }
        return s;*/
}
