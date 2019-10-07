package com.example.demo1.logic.download;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class DataCrawler {
    @Value("${cloudLogRepository}")
    private String pathForSave;
    @Value("${urlOfSource}")
    private String urlPath;

    private static final int BUFFER_SIZE = 1024;

    public void downloadToRep() {
        byte[] buffer = new byte[BUFFER_SIZE];
        final File dstDir = new File(pathForSave);
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        try {
            URL url = new URL(urlPath);
            // Получаем содержимое ZIP архива
            final ZipInputStream zis = new ZipInputStream(url.openStream());
            ZipEntry ze = zis.getNextEntry();
            String nextFileName;
            while (ze != null) {
                nextFileName = ze.getName();
                File nextFile = new File(pathForSave + File.separator + nextFileName);
                //System.out.println("Распаковка: " + nextFile.getAbsolutePath());
                if (ze.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    // Создание всех родительских каталогов
                    new File(nextFile.getParent()).mkdirs();
                    // Запись
                    try (FileOutputStream fos = new FileOutputStream(nextFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
