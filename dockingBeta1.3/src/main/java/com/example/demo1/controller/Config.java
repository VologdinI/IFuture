package com.example.demo1.controller;

import com.example.demo1.logic.LogFinder;
import com.example.demo1.logic.TextSearcher;
import com.example.demo1.logic.TreePackage;
import com.example.demo1.logic.text.TextEfficientReader;
import com.example.demo1.logic.сoncurrency.ThreadsWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;


@Configuration
@EnableScheduling
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class Config {
    @Bean
    String reqStr(){return new String();}
    @Bean
    TreePackage treePackage(){return new TreePackage();}
    @Bean
    LogFinder logFinder(){return new LogFinder();}
    @Bean
    TextSearcher textSearcher(){return new TextSearcher();}
    @Bean
    ThreadsWrapper threadsWrapper(){return new ThreadsWrapper();}
    @Bean
    TextEfficientReader textEfficientReader(){return new TextEfficientReader(new File("c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\serverLogRepository\\logExample\\BigBoyInside\\SmallBoy.log"));}
}
