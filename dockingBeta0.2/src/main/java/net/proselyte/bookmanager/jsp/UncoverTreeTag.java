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
import java.io.IOException;
import java.util.List;

public class UncoverTreeTag extends SimpleTagSupport {

    public String getFoldersFrom(TreePackage treePackage){
        String sReturn="";
        List<TreePackage> treePackages=treePackage.getTreePackageList();
        sReturn+="<ol>\n";
        for (int i = 0; i <treePackage.getTreePackageList().size(); i++) {
            sReturn+="<li>\n";
            sReturn+="  <label for="+treePackages.get(i).getLevelOfPackage()+i+">"+
                    treePackages.get(i).getFileOfPackage().getName()+"</label> <input type=\"checkbox\" checked disabled id=" +
                    treePackages.get(i).getLevelOfPackage()+i+" />\n";
            if(treePackages.get(i).getTreePackageList().size()!=0)
            sReturn+=getFoldersFrom(treePackages.get(i));
            sReturn+="</li>\n";
        }
        sReturn+="</ol>\n";
        return sReturn;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        TreePackage treePackage= TreePackage.getTreePackage();
        out.println(treePackage.getTreePackage().getFileOfPackage().getName());
        out.println("<br/>");
        /*List<TreePackage> treePackages=treePackage.getTreePackageList();
        for (int i = 0; i <treePackage.getTreePackageList().size(); i++) {
            out.println("<li>\n");
            out.println("<label for="+treePackages.get(i).getLevelOfPackage()+i+">"+
                    treePackages.get(i).getFileOfPackage().getName()+"</label> <input type=\"checkbox\" checked disabled id=" +
                    treePackages.get(i).getLevelOfPackage()+i+" />\n");
            out.println("</li>");
        }*/
        String s=getFoldersFrom(treePackage);
        s=s.substring(4);
        out.println(s);
        getJspBody().invoke(null);
    }
}
