package com.example.demo1.logic.text;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class UnzipTestApp {
//работа с инетом
private static final String pathForSave = "c:\\Users\\Kano\\Downloads\\serverLogRepository\\CloudLogRepository";
 private static final int BUFFER_SIZE = 1024;
 public static void main(String[] args) {
     UnzipTestApp app = new UnzipTestApp();
     app.unZip(pathForSave);


 }

private void unZip(final String pathForSave) {
 byte[] buffer = new byte[BUFFER_SIZE];

 // Создаем каталог, куда будут распакованы файлы
 final File dstDir = new File(pathForSave);
 if (!dstDir.exists()) {
  dstDir.mkdir();
 }
 try {
  String urlPath = "https://github.com/VologdinI/CloudLogRepository/archive/master.zip";
  URL url = new URL(urlPath);
  // Получаем содержимое ZIP архива
  final ZipInputStream zis = new ZipInputStream(url.openStream());
  ZipEntry ze = zis.getNextEntry();
  String nextFileName;
  while (ze != null) {
   nextFileName = ze.getName();
   File nextFile = new File(pathForSave + File.separator + nextFileName);
   System.out.println("Распаковываем: " + nextFile.getAbsolutePath());
   // Если мы имеем дело с каталогом - надо его создать. Если
// этого не сделать, то не будут созданы пустые каталоги
// архива
   if (ze.isDirectory()) {
    nextFile.mkdir();
   } else {
    // Создаем все родительские каталоги
    new File(nextFile.getParent()).mkdirs();
    // Записываем содержимое файла
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



