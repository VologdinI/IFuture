package net.proselyte.bookmanager.controller;

import net.proselyte.bookmanager.logic.Finder;
import net.proselyte.bookmanager.logic.Input;
import net.proselyte.bookmanager.logic.LogFinder;
import net.proselyte.bookmanager.logic.TreePackage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
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

    @RequestMapping("/processFrom")
    public String showForm(@ModelAttribute Input input, Model
            model, @ModelAttribute ArrayList<String> vegetables){
        Finder finder =context.getBean("logFinder", LogFinder.class);
        String prePackage="c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\";
        TreePackage treePackage=finder.findLocalStuff(prePackage, input.getExtension());
        File file =treePackage.getFileList().get(0);
        List<String> packages=new ArrayList<>();
        for (int i = 0; i < treePackage.getTreePackageList().size(); i++) {
            packages.add(treePackage.getTreePackageList().get(i).getFileOfPackage().getName());
        }
        model.addAttribute("packages", packages);
        context.close();
        input.setGoalText(input.getGoalText()+" 101");
        treePackage.setLevelOfAllPackages();
        model.addAttribute("treePackage",treePackage);
        TreePackage.setTreePackage(treePackage);
        return "inputShowFrom";
    }
}
