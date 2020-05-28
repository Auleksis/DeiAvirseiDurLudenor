package com.civ.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import civ.utils.IFileManager;

public class FileHandlerHelper implements IFileManager {
    @Override
    public void writeFile(String data, String name) {
        File folder = new File("C\\saves\\");
        if(!folder.exists()){
            folder.mkdir();
        }
        File file = new File("C:\\saves\\", name);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFile(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File("C:\\saves\\", name);
        try {
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()){
                stringBuilder.append(sc.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public File getFile(String name) {
        return null;
    }

    @Override
    public File[] getallSaves() {
        File folder = new File("C:\\saves\\");
        if(!folder.exists()){
            folder.mkdir();
        }
        File [] saves = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".aus")){
                    return true;
                }
                return false;
            }
        });
        return saves;
    }

    @Override
    public void deleteFile(String filename) {
        File file = new File("C:\\saves\\");
        file.delete();
    }
}
