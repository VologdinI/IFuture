package net.proselyte.bookmanager.logic.download;

import net.proselyte.bookmanager.logic.TreePackage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class Downloader {

    public static TreePackage downloadUsingStream(String category, String urlStr) throws IOException, ParseException {
        TreePackage treePackage = new TreePackage();
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());



        return treePackage;
    }
}
