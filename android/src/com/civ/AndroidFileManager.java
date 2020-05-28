package com.civ;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import civ.utils.IFileManager;


public class AndroidFileManager implements IFileManager {
    private Context context;

    AndroidFileManager(Context context){
        this.context = context;
    }

    @Override
    public void writeFile(String data, String name) {
        File file = new File(context.getFilesDir(), name);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.close();
            Log.d("MEINTAG", "Successfully saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFile(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(context.getFilesDir(), name);
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
        return new File(context.getFilesDir(), name);
    }

    @Override
    public File[] getallSaves() {
        File folder = new File(context.getFilesDir().toString());
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
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }
}