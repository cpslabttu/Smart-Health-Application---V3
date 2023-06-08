package com.example.cps_lab.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static void zipFolder(String folderPath, String zipFilePath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        File zipFile = new File(zipFilePath);
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));

        zipFolder(folder, folder.getName(), zos);

        zos.close();
    }

    private static void zipFolder(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024];

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, parentFolder + "/" + file.getName(), zos);
            } else {
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));

                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
        }
    }
}
