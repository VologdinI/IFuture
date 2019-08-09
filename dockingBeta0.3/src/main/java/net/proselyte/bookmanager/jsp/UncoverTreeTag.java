package net.proselyte.bookmanager.jsp;

import net.proselyte.bookmanager.controller.Config;
import net.proselyte.bookmanager.logic.Finder;
import net.proselyte.bookmanager.logic.LogFinder;
import net.proselyte.bookmanager.logic.TreePackage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.reflect.generics.tree.Tree;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UncoverTreeTag extends SimpleTagSupport {

    private String thisTreePackageToString(TreePackage treePackage){
        String sReturn="";
        sReturn+="    <li>\n";
        sReturn+="      <label for=\""+treePackage.getLevelOfPackage()+"\">"+treePackage.getFileOfPackage().getName()+"</label>\n";
        sReturn+="          <input type=\"checkbox\" id=\""+treePackage.getLevelOfPackage()+"\" />\n";

        return sReturn;
    }
    public String getFoldersFrom(List<TreePackage> treePackages,List<File> files){
        String sReturn="";
        if(treePackages.size()!=0||files.size()!=0)
        sReturn += "<ul class=\"table-wrapper\">\n";
        if(treePackages.size()!=0) {
            for (int i = 0; i < treePackages.size(); i++) {
                sReturn += "<li>\n";
                sReturn += "      <label for=\"" + treePackages.get(i).getLevelOfPackage()
                        + "\">" + treePackages.get(i).getFileOfPackage().getName() + "</label>\n";
                sReturn += "      <input type=\"checkbox\" id=\"" + treePackages.get(i).getLevelOfPackage() + "\" />\n";
                sReturn += getFoldersFrom(treePackages.get(i).getTreePackageList(),treePackages.get(i).getFileList());
                sReturn += "</li>\n";
            }
        }
        if(files.size()!=0){
            for (int i = 0; i < files.size(); i++) {
                sReturn+="<li class=\"file\"><a href=\"\">"+files.get(i).getName()+"</a>\n" +
                            "                                    </li>";
            }
        }

        if(treePackages.size()!=0||files.size()!=0)
        sReturn += "</ul>\n";
        return sReturn;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        TreePackage treePackage= TreePackage.getTreePackage();
        out.println(treePackage.getTreePackage().getFileOfPackage().getName());
        out.println("<br/>");

        List<TreePackage> treePackages=new ArrayList<>();
        treePackages.add(treePackage);
        String s=getFoldersFrom(treePackages,new ArrayList<>());

        File file=new File("c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\test.txt");
        FileWriter fw = new FileWriter( file);
        fw.write(s);
        fw.close();

        s=s.substring("<ul class=\"table-wrapper\">".length());
        s="<ul class=\"table-tree\" cellspacing=\"0\">\n"+s;
        out.println(s);
        getJspBody().invoke(null);

    }
}
