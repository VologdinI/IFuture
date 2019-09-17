package com.example.demo1.logic;

import java.io.File;

public interface Searcher {
    public String findStringInFile(File file, String requiredString) throws Exception;
    public String getSpaceNearGoalPlace();
}
