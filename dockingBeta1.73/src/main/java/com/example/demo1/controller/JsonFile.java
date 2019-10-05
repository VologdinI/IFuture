package com.example.demo1.controller;


import com.example.demo1.logic.LogFinder;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JsonFile {
    private int id;
    private String content;
    private String localPath;
    private String ip;

    public JsonFile(int id, String content, String localPath, String ip) {
        this.id = id;
        this.content = content;
        this.localPath = localPath;
        this.ip = ip;
    }

    public JsonFile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void writeContentToFile(String prePackage){
        String path = this.getIp()+ File.separator + LogFinder.changeSlashes(this.getLocalPath());
        LogFinder.makePackagesWithFile(prePackage, path);//записываем файлы в нужные места
        //File file = new File(path);
        try(FileOutputStream fos=new FileOutputStream(prePackage+path))
        {
            // перевод строки в байты
            byte[] buffer = content.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("The file has been written");
    }

}
