package com.civ.utils;

import com.civ.model.Map.Map;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;

import java.io.File;
import java.io.Serializable;

public interface IFileManager {
    void writeFile(String data, String name);
    String readFile(String name);
    File getFile(String name);
}
