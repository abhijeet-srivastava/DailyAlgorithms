package com.walmart;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystemImpl implements FileSystem {

    private File  root;
    private class File {
        String name;
        boolean isFile;
        String content;
        Map<String, File> files;

        public File(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            if(!isFile) {
                files = new HashMap<>();
            }
        }
    }

    public FileSystemImpl() {
        this.root = new File("/", false);
    }

    @Override
    public List<String> ls(String path) {
        String[] absPath = path.split("/");
        File current = root;
        for(int i = 1; i < absPath.length ; i++) {
            String fileName = absPath[i];
            current =  current.files.get(fileName);
        }
        if(current.isFile) {
            return Arrays.asList(current.name);
        } else {
            return current.files.keySet().stream().sorted().collect(Collectors.toList());
        }
    }

    @Override
    public void mkdir(String path) {
        String[] absPath = path.split("/");
        File current = root;
        for(int i = 1; i < absPath.length; i++) {
            String fileName = absPath[i];
            if(!current.files.containsKey(fileName)) {
                File file = new File(fileName, false);
                current.files.put(fileName, file);
            }
            current = current.files.get(fileName);
        }
    }

    @Override
    public void addContentToFile(String filePath, String content) {
        String[] absPath = filePath.split("/");
        File current = root;
        for(int i = 1; i < absPath.length - 1; i++) {
            String fileName = absPath[i];
            if(!current.files.containsKey(fileName)) {
                File file = new File(fileName, false);
                current.files.put(fileName, file);
            }
            current = current.files.get(fileName);
        }
        String finalFileName = absPath[absPath.length-1];
        if(current.files.containsKey(finalFileName)) {
            current.files.get(finalFileName).content += content;
        } else {
            File finalFile = new File(finalFileName, true);
            finalFile.content = content;
            current.files.put(finalFileName, finalFile);
        }
    }

    @Override
    public String readContentFromFile(String filePath) {
        String[] absPath = filePath.split("/");
        File current = root;
        for(int i = 1; i < absPath.length; i++) {
            String fileName = absPath[i];
            current = current.files.get(fileName);
        }
        return current.content;
    }
}
