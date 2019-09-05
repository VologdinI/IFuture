package com.example.demo1.controller;


import com.example.demo1.logic.*;
import com.example.demo1.logic.сoncurrency.preTest.ThreadsWrapper;
import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Date;
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

    /*@Autowired
    TextSearcher textSearcher;*/

    ThreadsWrapper threadsWrapper;
    TreePackage treePackage;
    List<FileAdvanced> fileAdvancedList;
    @RequestMapping("/processFrom")
    public String showForm(@ModelAttribute Input input, Model
            model){
        String sReq=context.getBean("reqStr", String.class);
        System.out.println(sReq);
        sReq = "frontend";
        String fuckU=context.getBean("reqStr", String.class);
        LogFinder logFinder =context.getBean("logFinder", LogFinder.class);
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        treePackage=logFinder.findLocalStuff(prePackage, input.getExtension());
        input.setGoalText(input.getGoalText()+" 101");
        model.addAttribute("treePackage",treePackage);
        TreePackage.setTreePackage(treePackage);

        TextSearcher textSearcher=context.getBean("textSearcher",TextSearcher.class);
        //List<List<File>> listList=logFinder.getListsOfFilesWithPriority(treePackage.getFileList());
        fileAdvancedList = treePackage.getFileAdvancedList();

        threadsWrapper=context.getBean("threadsWrapper",ThreadsWrapper.class);
        //с бинами все не так
        threadsWrapper = new ThreadsWrapper(treePackage, sReq);
        Thread thread= new Thread(threadsWrapper);
        model.addAttribute("threadsWrapper",threadsWrapper);
        thread.start();

        return "inputShow";
    }


    @RequestMapping("/testGreen")
    @ResponseBody
    public String showGreen(){
        System.out.println("Folder magic");
        String s="";

        if(threadsWrapper==null||threadsWrapper.getThreads()==null)
            return "Nothing";
        else{
            for (int i = 0; i <threadsWrapper.getThreads().size() ; i++) {
                //fileAdvancedList.get(i).setContainsRequiredString(threadsWrapper.getTextSearchers().get(i).isContaisReqS());
                s+=threadsWrapper.getTextSearchers().get(i).getFile().getName()+"|"+threadsWrapper.getThreads().get(i).getState().toString()+"|"+threadsWrapper.getTextSearchers().get(i).isContaisReqS()+" ";
            }
        }
        //return threadsWrapper.getState();
        return s;
    }
    @RequestMapping("/testAjax")
    public String showTestAjax(){
        return "testAjax";
    }
    @RequestMapping("/getSomeTime")
    @ResponseBody//output direct in stream
    public String showTime(@ModelAttribute Thread threads) throws IOException {
        /*System.out.println("Ajax magic");
        Date date = new Date();
        return date.toString();*/
        String jsonString="";
        if(treePackage!=null) {
            Gson gson = new Gson();
            jsonString = gson.toJson(treePackage);
        } else {
            jsonString+="nothing";
        }
        return jsonString;
    }
}
