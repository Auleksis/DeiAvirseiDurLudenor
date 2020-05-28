package civ.utils;

import java.io.File;
import java.util.ArrayList;

public interface IFileManager {
    void writeFile(String data, String name);
    String readFile(String name);
    File getFile(String name);
    File [] getallSaves();
    void deleteFile(String filename);
}
