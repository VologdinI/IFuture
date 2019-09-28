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
import java.util.List;

@Controller
public class HomeController {
    private boolean workWithNet = false;
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
        //File file = new File();
        //model.addAttribute("file",file);
        return "inputShow";
    }

    @RequestMapping("/workWithNet")
    public void getNetButton(Model model){//@ModelAttribute Boolean b
        workWithNet = true;
    }
    @RequestMapping("/workWithServer")
    public void getServerButton(Model model){//@ModelAttribute Boolean b
        workWithNet = false;
    }

    @GetMapping(path="/{id}")
    @ResponseBody
    public String read(@PathVariable String id) {
        System.out.println("fuck u mvc");
        TextEfficientReader textEfficientReader = context.getBean("textEfficientReader", TextEfficientReader.class);
        return textEfficientReader.sendContentOfFile(treePackage, Integer.parseInt(id));
    }
    /*//с видоса ранглиша
    @PostMapping()
    public void getIdOfFileVideo(@RequestBody IdClass idClass){
        System.out.println(idClass.getID());
    }*/


    /*@Autowired
    TextSearcher textSearcher;*/

    @RequestMapping("/sForm")
    public String getIdOfFile(@ModelAttribute String s){//@ModelAttribute Boolean b
        return s;
    }
    ThreadsWrapper threadsWrapper;
    TreePackage treePackage;
    TreePackage tpAfterLogFinder;
    List<FileAdvanced> fileAdvancedList;
    @Value("${serverLogRepository}")
    String prePackage;// = "c:\\Users\\Kano\\Downloads\\serverLogRepository\\";
    @Value("${cloudLogRepository}")
    String preCloudPackage;

    @RequestMapping("/processFrom")
    @ResponseBody
    public void showForm(@ModelAttribute Input input, Model
            model){
        String sReq = input.getGoalText();
        DataCrawler dataCrawler =context.getBean("dataCrawler", DataCrawler.class);
        LogFinder logFinder =context.getBean("logFinder", LogFinder.class);

        treePackage = context.getBean("treePackage", TreePackage.class);

        if(workWithNet) {
            dataCrawler.downloadToRep();
            tpAfterLogFinder = logFinder.findLocalStuff(preCloudPackage, input.getExtension());
            treePackage = logFinder.findLocalStuff(preCloudPackage, input.getExtension());
        }
        else {
            tpAfterLogFinder = logFinder.findLocalStuff(prePackage, input.getExtension());
            treePackage = logFinder.findLocalStuff(prePackage, input.getExtension());
        }
        TreePackage.setTreePackage(treePackage);
        TextSearcher textSearcher=context.getBean("textSearcher",TextSearcher.class);
        //List<List<File>> listList=logFinder.getListsOfFilesWithPriority(treePackage.getFileList());
        fileAdvancedList = treePackage.getFileAdvancedList();

        threadsWrapper=context.getBean("threadsWrapper",ThreadsWrapper.class);
        //с бинами все не так
        threadsWrapper = new ThreadsWrapper(treePackage, sReq);
        Thread thread= new Thread(threadsWrapper);

        thread.start();
        //return jsonTpAfterLogFinder;
        //return "inputShow";
    }

    @RequestMapping("/sendTpAfterLogFinder")
    @ResponseBody
    public String sendTpAfterLogFinder(){
        System.out.println("send JSON before");
        Gson gson = new Gson();
        String jsonTpAfterLogFinder="";
        jsonTpAfterLogFinder += gson.toJson(tpAfterLogFinder);
        return jsonTpAfterLogFinder;
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
    @RequestMapping("/testAjax")
    public String showTestAjax(){
        return "testAjax";
    }
    @RequestMapping("/getTreePackage")
    @ResponseBody//output direct in stream
    public String showTime(@ModelAttribute Thread threads) throws IOException {
        System.out.println("send JSON after");
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




    @RequestMapping("/testFindFileStuff")
    public String showForm(@ModelAttribute File file, Model
            model) {
        System.out.println(file.getName());
        System.out.println("yeah");
        return "fuck yeah";
    }
}
