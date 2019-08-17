package net.proselyte.bookmanager.controller;

import net.proselyte.bookmanager.logic.*;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.InputTestFrontEnd;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.ThreadsTestFrontEnd;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.ThreadsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
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
        return "inputShowFrom";
    }

    /*@Autowired
    TextSearcher textSearcher;*/

    ThreadsWrapper threadsWrapper;

    @RequestMapping("/processFrom")
    public String showForm(@ModelAttribute Input input, Model
            model){
        LogFinder logFinder =context.getBean("logFinder", LogFinder.class);
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        TreePackage treePackage=logFinder.findLocalStuff(prePackage, input.getExtension());
        input.setGoalText(input.getGoalText()+" 101");
        treePackage.setLevelOfAllPackages();
        model.addAttribute("treePackage",treePackage);
        TreePackage.setTreePackage(treePackage);

        TextSearcher textSearcher=context.getBean("textSearcher",TextSearcher.class);
        List<List<File>> listList=logFinder.getListsOfFilesWithPriority(treePackage.getFileList());

        threadsWrapper=context.getBean("threadsWrapper",ThreadsWrapper.class);
        Thread thread= new Thread(threadsWrapper);
        model.addAttribute("threadsWrapper",threadsWrapper);
        thread.start();

        return "inputShowFrom";
    }


    @RequestMapping("/testGreen")
    @ResponseBody
    public String showGreen(){
        System.out.println("Folder magic");
        String s="";

        if(threadsWrapper.getThreads()==null||threadsWrapper==null)
            return "Nothing";
        else{
            for (int i = 0; i <threadsWrapper.getThreads().size() ; i++) {
                s+=threadsWrapper.getThreads().get(i).getState().toString()+" ";
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
    public String showTime(@ModelAttribute Thread threads){


        System.out.println("Ajax magic");
        Date date = new Date();
        return date.toString();
    }
}
