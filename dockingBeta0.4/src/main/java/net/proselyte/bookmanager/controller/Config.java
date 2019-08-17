package net.proselyte.bookmanager.controller;

import net.proselyte.bookmanager.logic.*;
import net.proselyte.bookmanager.logic.сoncurrency.preTest.ThreadsWrapper;
import net.proselyte.bookmanager.logic.сoncurrency.study.CounterThread;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
public class Config {
    @Bean
    TreePackage treePackage(){return new TreePackage();}
    @Bean
    LogFinder logFinder(){return new LogFinder(treePackage());}
    @Bean
    TextSearcher textSearcher(){return new TextSearcher();}
    @Bean
    ThreadsWrapper threadsWrapper(){return new ThreadsWrapper();}
    @Bean
    CounterThread counterThread(){return new CounterThread();}
}
