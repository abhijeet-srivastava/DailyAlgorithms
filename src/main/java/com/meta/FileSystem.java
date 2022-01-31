package com.meta;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    class Directory {
        String name;
        Integer value;
        Map<String, Directory> childrens;
        public Directory(String name) {
            this.name = name;
            this.childrens = new HashMap<>();
            this.value = -1;
        }
    }
    Directory root;
    public FileSystem() {
        this.root = new Directory("/");
    }

    public boolean createPath(String path, int value) {
        boolean exists = true;
        String[] pathArr = path.split("/");
        Directory current = root;
        int i = 1;
        for(; i < pathArr.length; i++) {
            String subDir = pathArr[i];
            if(subDir.trim().isEmpty()) {
                continue;
            }
            System.out.printf("On level: %s\n", subDir);
            if(!current.childrens.containsKey(subDir)) {
                exists = false;
                break;
            }
            current = current.childrens.get(subDir);
        }
        if(exists) {
            return false;
        }
        for(; i < pathArr.length; i++) {
            String subDir = pathArr[i];
            if(subDir.trim().isEmpty()) {
                continue;
            }
            System.out.printf("Creating On level: %s\n", subDir);
            Directory child = new Directory(subDir);
            //System.out.printf("Adding %s below %s\n", subDir, current.name);
            current.childrens.put(subDir, child);
            current = child;
        }
        System.out.printf("Adding value to : %s\n", current.name);
        current.value = value;
        return true;
    }

    public int get(String path) {
        System.out.printf("Path: [%s]\n", path);
        String[] pathArr = path.split("/");
        Directory current = root;
        for(String subDirName: pathArr) {
            if(subDirName.trim().isEmpty()) {
                continue;
            }
            System.out.printf("Searching On level: %s\n", subDirName);
            if(!current.childrens.containsKey(subDirName)) {
                System.out.printf("Did not find: %s under: %s\n", subDirName, current.name);
                return -1;
            }
            current = current.childrens.get(subDirName);
        }
        System.out.printf("current: [%s]\n", current.name);
        return current.value;
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.createPath("/leet", 1);
        fs.createPath("/leet/code", 2);
        int val = fs.get("/leet/code");
        System.out.printf("Val: %d\n", val);
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */