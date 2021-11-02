package com.walmart;

import java.util.List;

public interface FileSystem {
    List<String> ls(String path);

    void mkdir(String path);

    void addContentToFile(String filePath, String content);

    String readContentFromFile(String filePath);

}
