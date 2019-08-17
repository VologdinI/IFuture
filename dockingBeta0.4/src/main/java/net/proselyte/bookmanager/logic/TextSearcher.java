package net.proselyte.bookmanager.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class TextSearcher implements Runnable{
    File file;
    String requiredString;
    boolean isContainsRequiredString;
    List<String> placesWithRequiredString;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    @Override
    public void run() {
        placesWithRequiredString=findStringInFile(file,requiredString);

    }

    private List<String> findStringInFile(File file, String requiredString)  {
        List<String> list = new ArrayList<>();
        try(FileReader fileReader= new FileReader(file)) {
            Scanner scanner = new Scanner(fileReader);
            String s = "";
            int i = 0;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.contains(requiredString))
                    list.add(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //write more for end
    /*private List<String> findStringInFileWithBuffer(File file, String requiredString) {
        Date date = new Date();
        try {
            FileInputStream fileInputStream = new FileInputStream
                    ("c:\\Users\\Kano\\Downloads\\учеба\\проганье\\java\\iFurute\\logExample\\BigBoyInside\\SmallBoy.log");
            ;
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                ;
                char c=(char) i;
            }
            Date date1 = new Date();
            System.out.println((date1.getTime() - date.getTime()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }*/
    public List<Integer> getListOfScrollMarkers(){
        List<Integer> iMarkers= new ArrayList<>();
        List<String> linesNearGoalPlace= new ArrayList<>();
        return iMarkers;
    }


}
