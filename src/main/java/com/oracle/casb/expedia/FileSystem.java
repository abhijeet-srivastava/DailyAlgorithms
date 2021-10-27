package com.oracle.casb.expedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSystem {

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.mkdir("/a");
        fs.mkdir("/a/m");
        fs.mkdir("/a/c");
        fs.mkdir("/a/m/d");
        fs.mkdir("/a/m/e");
        fs.mkdir("/a/m/d/f");
        fs.mkdir("/a/m/d/e");

        fs.addContentToFile("/a/m/d/g", "Content of G");
        fs.addContentToFile("/a/m/d/h", "Content of H");
        System.out.printf("Content[%s]\n", fs.readContentofFile("/a/m/d/g"));
        fs.addContentToFile("/a/m/d/g", " Appendend contnet of G");
        System.out.printf("Content[%s]\n", fs.readContentofFile("/a/m/d/g"));

        System.out.printf("Content[%s]\n", fs.readContentofFile("/a/m/d/h"));
        System.out.println(fs.ls("/a/m/d").stream().collect(Collectors.joining(", ", "[", "]")));

    }

    private File root;
    private class File {
        private String name;
        boolean isFile;
        String content;
        Map<String, File> files;

        public File(String name, boolean isFile) {
            this.isFile = isFile;
            this.name = name;
            if(!isFile) {
                files = new HashMap<>();
            }
        }
    }
    public FileSystem() {
        root = new File("root", false);
    }
    public List<String> ls(String path) {
        String[] array = path.split("/");
        File current = root;
        for (int i = 1; i < array.length; i++) {
            String subFileName = array[i];
            if (!current.files.containsKey(subFileName)) {
                throw new RuntimeException("File name [" +subFileName + "] does not exist");
            }
            current = current.files.get(subFileName);
        }
        List<String> result = new ArrayList<>();
        if (current.isFile) {
            result.add(current.name);
        } else {
            result.addAll(current.files.keySet());
        }
        return result;
    }
    public void mkdir(String path) {
        String[] array = path.split("/");
        File current = root;
        for (int i = 1; i < array.length - 1; i++) {
            String subFileName = array[i];
            if (!current.files.containsKey(subFileName)) {
                throw new RuntimeException("File name [" +subFileName + "] does not exist");
            }
            current = current.files.get(subFileName);
        }
        String dirName = array[array.length - 1];
        if (current.files.containsKey(dirName)) {
            throw new RuntimeException("File name [" + dirName + "] already exists at given location");
        }
        current.files.put(dirName, new File(dirName, false));
    }

    public void addContentToFile(String filePath, String content) {
        String[] array = filePath.split("/");
        File current = root;
        for (int i = 1; i < array.length -1 ; i++) {
            String subFileName = array[i];
            if (!current.files.containsKey(subFileName)) {
                throw new RuntimeException("File name [" +subFileName + "] does not exist");
            }
            current = current.files.get(subFileName);
        }
        String fileName = array[array.length-1];
        if (current.files.containsKey(fileName)) {
            File file = current.files.get(fileName);
            if (!file.isFile) {
                throw new RuntimeException("File name [" + file.name + "] is not a text file");
            }
            file.content += content;
        } else {
            File file = new File(fileName, true);
            file.content = content;
            current.files.put(fileName, file);
        }
    }

    public String readContentofFile(String filePath) {
        String[] array = filePath.split("/");
        File current = root;
        for (int i = 1; i < array.length; i++) {
            String subFileName = array[i];
            if (!current.files.containsKey(subFileName)) {
                throw new RuntimeException("File name [" +subFileName + "] does not exist");
            }
            current = current.files.get(subFileName);
        }
        if (!current.isFile) {
            throw new RuntimeException("File name [" +current.name + "] is not a text file");
        }
        return current.content;
    }
}
