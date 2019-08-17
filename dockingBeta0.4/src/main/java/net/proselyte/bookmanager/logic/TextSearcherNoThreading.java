package net.proselyte.bookmanager.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TextSearcherNoThreading implements Runnable{

    String requiredString;

    List<TextSearcherFileIsString> listResults;



    List<TextSearcherFileIsString> listResultsBuffered;

    List<File> files;

    public List<TextSearcherFileIsString> getListResultsBuffered() {
        return listResultsBuffered;
    }

    public void setListResultsBuffered(List<TextSearcherFileIsString> listResultsBuffered) {
        this.listResultsBuffered = listResultsBuffered;
    }

    public List<TextSearcherFileIsString> getListResults() {
        return listResults;
    }

    public void setListResults(List<TextSearcherFileIsString> listResults) {
        this.listResults = listResults;
    }

    public List<File> getFiles() {
        return files;
    }

    public TextSearcherNoThreading(String requiredString, List<File> files) {
        this.requiredString = requiredString;
        this.files=files;
        listResults=new ArrayList<>();
    }

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    @Override
    public void run() {
        /*for (int i = 0; i < files.size()-1; i++) {
            listResults.add(defineStringInFile(files.get(i),requiredString));
        }*/
        for (int i = 0; i < files.size()-1; i++) {
            listResults.add(defineStringInFileWithBuffer(files.get(i),requiredString));
        }
        for (int i = 0; i < files.size()-1; i++) {
            System.out.println(listResults.get(i).getFile().getName()+" "+listResults.get(i).isContainsRequiredString());
        }
    }


    private TextSearcherFileIsString defineStringInFile(File file, String requiredString)  {
        Date date = new Date();
        TextSearcherFileIsString tsfis=new TextSearcherFileIsString();
        tsfis.setFile(file);
        tsfis.setContainsRequiredString(false);
        try(FileReader fileReader= new FileReader(file)) {//BufferInputStream <10% faster then FileReader
            Scanner scanner = new Scanner(fileReader);
            String s = "";
            int i = 0;
            int j=0;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.contains(requiredString)){
                    tsfis.setContainsRequiredString(true);
                    break;
                }
                i++;
                if(i%500000==0) {
                    j++;
                    System.out.println("BigBoy" + j);
                }
            }
            Date date1 = new Date();
            System.out.println(file.getName()+" "+(date1.getTime() - date.getTime()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tsfis;
    }
    private TextSearcherFileIsString defineStringInFileWithBuffer(File file, String requiredString) {
        Date date = new Date();
        char reqChar=requiredString.toCharArray()[0];
        TextSearcherFileIsString tsfis=new TextSearcherFileIsString();
        tsfis.setFile(file);
        tsfis.setContainsRequiredString(false);
        try (
            FileInputStream fileInputStream = new FileInputStream
                    (file)
                    ){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                if(reqChar==(char)i) {
                    tsfis.setContainsRequiredString(true);
                    break;
                }
            }
            Date date1 = new Date();
            System.out.println((date1.getTime() - date.getTime()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return tsfis;
    }
}
