package com.example.demo1.logic.text;


import com.example.demo1.controller.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

public class TextTesterApp {
    public static void main(String[] args) throws Exception{
        File file = new File("c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\BigBoyInside\\SmallBoyButRealMan.log");
        //TextEfficientReader textEfficientReader = new TextEfficientReader(file);
        RandomAccessFile f = new RandomAccessFile(file, "rw");
        long aPositionWhereIWantToGo = 9000000*30;
        f.seek(aPositionWhereIWantToGo); // this basically reads n bytes in the file
        //f.write("Yeah".getBytes());
        byte[] buf = new byte[4];
        f.read(buf,0,4);
        char[] ch = new char[4];
        for (int i = 0; i < 4; i++) {
            ch[i] = (char)buf[i];
        }
        System.out.println(ch);
        f.close();

        /*double d =(Math.random()*1000);
        System.out.println(d);
        System.out.println((long)(d));
        Date date = new Date();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        TextEfficientReader textEfficientReader = context.getBean("textEfficientReader", TextEfficientReader.class);
        //textEfficientReader.findStringInFile(10000 );
        Date date1 = new Date();//9 миллионов строк в 450 Мб
        System.out.println(((date1.getTime()-date.getTime())/1000));//36*/
    }
}
