package com.example.demo1.controller;


import com.example.demo1.logic.*;
import com.example.demo1.logic.download.DataCrawler;
import com.example.demo1.logic.text.TextEfficientReader;
import com.example.demo1.logic.text.TextSearcher;
import com.example.demo1.logic.сoncurrency.ThreadsWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Controller
public class HomeController {
    private static AnnotationConfigApplicationContext context;
    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }
    public void setContext(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void makeInitStuff(){
        context=new AnnotationConfigApplicationContext(Config.class);
        ipStorage = new HashSet<>();
        System.out.println(prePackage);
        System.out.println(preCloudPackage);
        System.out.println(preUsersPackage);
        LogFinder.makePackages("c:\\",prePackage.substring(3));
        LogFinder.makePackages("c:\\",preCloudPackage.substring(3));
        LogFinder.makePackages("c:\\",preUsersPackage.substring(3));
        System.out.println("Init Stuff");
    }

    @PreDestroy
    public void makeEndStuff(){
        context.close();
        System.out.println("End Stuff");
    }

    @RequestMapping("/")
    public String showPage(Model model){
        Input input= new Input();
        model.addAttribute("input",input);
        return "inputShow";
    }

    @GetMapping("getMoreData")
    @ResponseBody
    public String readMore(@RequestParam(value="ip") String ip, @RequestParam(value="id") String id) {
        TreePackage treePackage = context.getBean("treePackage", TreePackage.class);
        return treePackage.getNextBlock(Integer.parseInt(id));
    }
    @GetMapping("getFirstData")
    @ResponseBody
    public String readFirstTime(@RequestParam(value="ip") String ip, @RequestParam(value="id") String id) {
        TreePackage treePackage = context.getBean("treePackage", TreePackage.class);
        return treePackage.getNextBlock(Integer.parseInt(id));
    }

    @RequestMapping("/sForm")
    public String getIdOfFile(@ModelAttribute String s){
        return s;
    }
    ThreadsWrapper threadsWrapper;
    List<FileAdvanced> fileAdvancedList;
    HashSet<String> ipStorage;

    @Value("${serverLogRepository}")
    String prePackage;
    @Value("${cloudLogRepository}")
    String preCloudPackage;
    @Value("${usersLogRepository}")
    String preUsersPackage;
    //пользователь закрыл страницу
    @RequestMapping("/endSession")
    @ResponseBody
    public String stuffTest(String ip){//сделать void
        System.out.println(ip);
        String path = preUsersPackage + ip + File.separator;
        if(ipStorage.contains(ip))
            LogFinder.deleteFolder(path);
        ipStorage.remove(ip);
        return "yeah";
    }

    @RequestMapping("/getTreePackageBeforeAnalysis")
    @ResponseBody
    public String showForm(@RequestParam(value="goal") String goal, @RequestParam(value="ip") String ip,  @ModelAttribute Input input, Model
            model){
        String sReq = input.getGoalText();
        FileAdvanced.reloadID();
        DataCrawler dataCrawler =context.getBean("dataCrawler", DataCrawler.class);
        LogFinder logFinder =context.getBean("logFinder", LogFinder.class);
        TreePackage treePackage = context.getBean("treePackage", TreePackage.class);
        switch (goal) {
            case "server":{
                logFinder.fillTreePackage(prePackage, input.getExtension());
            }
                break;
            case "net":{
                dataCrawler.downloadToRep();
                logFinder.fillTreePackage( preCloudPackage, input.getExtension());
            }
                break;
            default:{

                String usersPath = preUsersPackage + ip + "\\";
                logFinder.fillTreePackage(usersPath, input.getExtension());
            }
        }
        Config.setsReq(sReq);
        String tpAfterLogFinder = treePackage.toJson();
        fileAdvancedList = treePackage.getListOfAllFiles();
        threadsWrapper=context.getBean("threadsWrapper",ThreadsWrapper.class);
        Thread thread= new Thread(threadsWrapper);
        thread.start();
        System.out.println("send JSON before");
        return tpAfterLogFinder;
    }


    @RequestMapping("/threadsStates")
    @ResponseBody
    public String showGreen(){
        System.out.println("Folder magic");
        String s="";
        boolean b = true;
        if(threadsWrapper==null||threadsWrapper.getThreads()==null)
            return "Nothing";
        else{
            for (int i = 0; i <threadsWrapper.getThreads().size() ; i++) {
                s+=threadsWrapper.getTextSearchers().get(i).getFileAdvanced().getFile().getName()
                        +"|"+threadsWrapper.getThreads().get(i).getState().toString()
                        +"|"+threadsWrapper.getTextSearchers().get(i).isContaisReqS()+" ";
            }
            for (int i = 0; i <threadsWrapper.getThreads().size() ; i++) {
                if(threadsWrapper.getThreads().get(i).getState()!=Thread.State.TERMINATED)
                    b = false;
            }
        }
        if(b)
            return "done";
        else
            return s;
    }

    @RequestMapping("/getTreePackage")
    @ResponseBody
    public String showTime(@ModelAttribute Thread threads) throws IOException {
        System.out.println("send JSON after");
        String jsonString="";
        TreePackage treePackage = context.getBean("treePackage", TreePackage.class);
        if(treePackage!=null) {
            Gson gson = new Gson();
            jsonString = gson.toJson(treePackage);
        } else {
            jsonString+="nothing";
        }
        return jsonString;
    }
    //магия
    @RequestMapping("/fileFromClient")
    public String getStuff(@RequestParam(value="id") int id, JsonFile jsonFile){
        jsonFile.writeContentToFile(preUsersPackage);
        ipStorage.add(jsonFile.getIp());
        return "dssds";
    }

}
