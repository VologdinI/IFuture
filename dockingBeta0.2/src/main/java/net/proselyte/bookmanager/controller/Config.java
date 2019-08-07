package net.proselyte.bookmanager.controller;

import net.proselyte.bookmanager.logic.LogFinder;
import net.proselyte.bookmanager.logic.Searcher;
import net.proselyte.bookmanager.logic.TextSearcher;
import net.proselyte.bookmanager.logic.TreePackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    @Bean
    TreePackage treePackage(){return new TreePackage();}
    @Bean
    LogFinder logFinder(){return new LogFinder(treePackage());}
    @Bean
    Searcher searcher(){return new TextSearcher();}
}
