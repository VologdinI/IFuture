package com.example.demo1.controller;


import com.example.demo1.logic.*;
import com.example.demo1.logic.download.DataCrawler;
import com.example.demo1.logic.json.Input;
import com.example.demo1.logic.json.JsonFile;
import com.example.demo1.logic.TreePackage;
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
import java.util.*;

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
        List<String> paths = new ArrayList<>();
        paths.add(prePackage);
        paths.add(preCloudPackage);
        paths.add(preUsersPackage);
        for (int i = 0; i < paths.size(); i++) {
            LogFinder.makePackages("c:\\",paths.get(i).substring(3));
        }
    }

    @PreDestroy
    public void makeEndStuff(){
        context.close();
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

    ThreadsWrapper threadsWrapper;
    HashSet<String> ipStorage;
    HashMap<Integer, Integer> navigator = new HashMap<>();;

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
        String path = preUsersPackage + ip + File.separator;
        if(ipStorage.contains(ip))
            LogFinder.deleteFolder(path);
        ipStorage.remove(ip);
        return "yeah";
    }

    @RequestMapping("/getTreePackageBeforeAnalysis")
    @ResponseBody
    public String showForm(@RequestParam(value="goal") String goal, @RequestParam(value="ip") String ip,  @ModelAttribute Input input){
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
        threadsWrapper=context.getBean("threadsWrapper",ThreadsWrapper.class);
        Thread thread= new Thread(threadsWrapper);
        thread.start();
        return tpAfterLogFinder;
    }

    //состояние потока проверяющего файлы на наличае строки
    @RequestMapping("/threadsStates")
    @ResponseBody
    public String showGreen(){
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
    //тут сприг усиленно ругается при любом варианте, но продолжает работать.
    @RequestMapping("/fileFromClient")
    public String getStuff(JsonFile jsonFile){
        jsonFile.writeContentToFile(preUsersPackage);
        ipStorage.add(jsonFile.getIp());
        return "yeah";
    }


    //отдать следующее вхождение
    @RequestMapping("/getNextBlock")
    @ResponseBody
    public String getNextBlock(@RequestParam(value="id") String id){
        int ID = Integer.parseInt(id);
        return getOverlap(ID,1);
    }
    //отдать следующее вхождение
    @RequestMapping("/getPrevBlock")
    @ResponseBody
    public String getPrevBlock(@RequestParam(value="id") String id){
        int ID = Integer.parseInt(id);
        return getOverlap(ID,-1);
    }
    //отдать следующее вхождение
    @RequestMapping("/makeOverlaps")
    @ResponseBody
    public String makeOverlaps(@RequestParam(value="id") String id) {
        updateMap();
        int ID = Integer.parseInt(id);
        List<TextSearcher> arTextSearchers = threadsWrapper.getTextSearchers();
        for (int i = 0; i < arTextSearchers.size(); i++) {
            if (arTextSearchers.get(i).getFileAdvanced().getID() == ID) {//нашли ID
                int overlaps = arTextSearchers.get(i).makeMoreOverlaps();
                return ""+overlaps;
            }
        }
        return "0";
    }
    private String getOverlap(int ID, int bias){
        updateMap();
        String block = "";
        int numberObBlock = 0;
        numberObBlock = getElemOfMap( ID);
        List<TextSearcher> arTextSearchers = threadsWrapper.getTextSearchers();
        for (int i = 0; i <arTextSearchers.size() ; i++) {
            if(arTextSearchers.get(i).getFileAdvanced().getID() == ID) {//нашли ID
                if((numberObBlock+bias)<0){
                    changeMapElem(ID,bias);
                    block = arTextSearchers.get(i).getArOverlaps().get(getElemOfMap(ID));
                }
                else
                if (arTextSearchers.get(i).getArOverlaps().size() <= numberObBlock+bias) {//если переполнение
                    overflowMapElemIfNeed(ID);
                    block = arTextSearchers.get(i).getArOverlaps().get(0);
                }else{
                    changeMapElem(ID,bias);
                    block = arTextSearchers.get(i).getArOverlaps().get(getElemOfMap(ID));
                }
            }
        }
        return block;
    }
    private void changeMapElem(int ID, int bias){//в зависимости от Overlapa поставить элемент
        Set<Map.Entry<Integer, Integer>> set = navigator.entrySet();
        for (Map.Entry<Integer, Integer> entry : set) {
            if (entry.getKey() == ID) {
                if((entry.getValue()+bias)<0) {
                    int arSize = 0;
                    List<TextSearcher> arTextSearchers = threadsWrapper.getTextSearchers();
                    for (int i = 0; i < arTextSearchers.size(); i++) {
                        if (arTextSearchers.get(i).getFileAdvanced().getID() == ID) {//нашли ID
                            arSize = arTextSearchers.get(i).getArOverlaps().size();
                        }
                        entry.setValue(arSize - 1);
                    }
                }
                else{
                    entry.setValue(entry.getValue()+bias);
                }
            }
        }
    }
    private void overflowMapElemIfNeed(int ID){//в зависимости от Overlapa поставить элемент
        Set<Map.Entry<Integer, Integer>> set = navigator.entrySet();
        for (Map.Entry<Integer, Integer> entry : set) {
            if (entry.getKey() == ID) {
                entry.setValue(0);
            }
        }
    }
    private int getElemOfMap( int ID){//в зависимости от Overlapa поставить элемент
        int numberObBlock = -1;
        Set<Map.Entry<Integer, Integer>> set = navigator.entrySet();
        for (Map.Entry<Integer, Integer> entry : set) {
            if(entry.getKey() == ID){
                numberObBlock = entry.getValue();
            }
        }
        return numberObBlock;
    }
    private void updateMap(){//добавить элемент в мапу если его нет
        List<TextSearcher> arTextSearchers = threadsWrapper.getTextSearchers();
        for (int i = 0; i <arTextSearchers.size() ; i++) {
            boolean b = true;
            for (Integer key : navigator.keySet()) {
                if (arTextSearchers.get(i).getFileAdvanced().getID() == key) {
                    b = false;
                }
            }
            if(b)
                navigator.put(arTextSearchers.get(i).getFileAdvanced().getID(), 0);
        }
    }
}
