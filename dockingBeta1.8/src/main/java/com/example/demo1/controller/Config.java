package com.example.demo1.controller;

import com.example.demo1.logic.LogFinder;
import com.example.demo1.logic.download.DataCrawler;
import com.example.demo1.logic.text.TextSearcher;
import com.example.demo1.logic.TreePackage;
import com.example.demo1.logic.text.TextEfficientReader;
import com.example.demo1.logic.сoncurrency.ThreadsWrapper;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;



@Configuration
@EnableScheduling
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class Config {
    private static String sReq;//не знаю, нормально ли это?
    public static String getsReq() {
        return sReq;
    }
    public static void setsReq(String str) {
        sReq = str;
    }

    @Bean
    TreePackage treePackage(){return new TreePackage();}
    @Bean
    DataCrawler dataCrawler(){return new DataCrawler();}
    @Bean
    LogFinder logFinder(){return new LogFinder(treePackage());}
    @Bean
    TextSearcher textSearcher(){return new TextSearcher();}
    @Bean
    @Scope("prototype")
    ThreadsWrapper threadsWrapper(){return new ThreadsWrapper(treePackage(),sReq);}
    /*@Bean
    TextEfficientReader textEfficientReader(){return new TextEfficientReader();}*/

}
