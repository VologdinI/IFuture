package com.example.demo1.logic.text;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class TextEfficientReader {
    //для постепенной подгрузки текста в редактор - меньшей загрузке сервера
    private File file;
    @Value("${bufferForTextEfficient}")
    public  int bufferForTextEfficient;//выражается в строках

    public TextEfficientReader(File file) {
        this.file = file;
    }

    public  String findStringInFile(int lines)  {
        String s = "";
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
        return s;
    }
}
