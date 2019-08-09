package net.proselyte.bookmanager.logic;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextSearcher implements Searcher{
    @Value("${foo.spaceNearGoalPlace}")
    private String spaceNearGoalPlace;

    public String getSpaceNearGoalPlace() {
        return spaceNearGoalPlace;
    }

    public void setSpaceNearGoalPlace(String spaceNearGoalPlace) {
        this.spaceNearGoalPlace = spaceNearGoalPlace;
    }

    public TextSearcher() {
    }

    public boolean isFileHaveString(){
        return false;
    }
    @Override
    public String findStringInFile(File file, String requiredString) throws Exception {
        FileReader fileReader= new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        String s="";
        while(scanner.hasNextLine()) {
            s = scanner.nextLine();
            if (s.contains(requiredString))
            break;
        }
        return s;
    }
    public List<Integer> getListOfScrollMarkers(){
        List<Integer> iMarkers= new ArrayList<>();
        List<String> linesNearGoalPlace= new ArrayList<>();
        return iMarkers;
    }

    public int findSizeOfFile(File file) {
        return (int)file.length()/(1024*1024);
    }
}
