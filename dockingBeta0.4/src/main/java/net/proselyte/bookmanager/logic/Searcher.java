package net.proselyte.bookmanager.logic;

import java.io.File;

public interface Searcher {
    public String findStringInFile(File file, String requiredString) throws Exception;
    public String getSpaceNearGoalPlace();
}
