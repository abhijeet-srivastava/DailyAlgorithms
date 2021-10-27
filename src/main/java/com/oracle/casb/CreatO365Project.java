package com.oracle.casb;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatO365Project {


    public static void main(String[] args) {
        CreatO365Project mainClass = new CreatO365Project();
        Integer[] arr = mainClass.createArrayFromNumber(342133);
        System.out.println(Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(", ")));
        //mainClass.debugLongetsOne();
        //mainClass.isValid("aabcbc");
       // mainClass.createProject("/Users/abhijsri/Downloads/mip_sdk_macos_1.1.217/mip_sdk_protection_macos_1.1.217/");
    }

    private void debugLongetsOne() {
        int[] arr = {0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1};
        int res = longestOnes(arr, 3);
    }

    private void createProject(String dir) {
        Path start = Paths.get(dir);
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            stream.forEach(
                    e -> {
                        try {
                            addToRoot(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
            );
            /*List<String> collect = stream
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToRoot( Path path) throws IOException {
        String updateFileName = "";
        try {
            System.out.println(path);
            String rootPath = "/Users/abhijsri/Downloads/mip_sdk_macos_1.1.217/mip_sdk_file_macos_1.1.217/";
            updateFileName = path.toString().replaceAll("\\\\", "/");
            String newFileName = Files.isDirectory(path) ? updateFileName : updateFileName.substring(0, updateFileName.lastIndexOf("/"));
            //System.out.println(newFileName);
            boolean isDir = Files.isDirectory(path) || isDirectory(updateFileName);
            Path newPath = Paths.get(newFileName);
            System.out.println("Creating directory " + newPath);
            if (!Files.exists(newPath)) {
                Files.createDirectories(newPath);
            }
            if (!isDir) {
                String fileName = updateFileName.substring(updateFileName.lastIndexOf('/') + 1);
                Path targetFile = Paths.get( newFileName, fileName);
                Files.copy(path, targetFile);
                System.out.println("Copying file " + path + " in directory " + targetFile);
            }
        } catch (IOException iex) {
            throw iex;
        } catch (Exception ex) {
            System.out.println("Error in processing " + path);
            System.out.println("updateFileName " + updateFileName);
            ex.printStackTrace();
        }
        //System.out.println("Creating directory " + newPath.getFileName());
        //path.getFileSystem().supportedFileAttributeViews().stream().forEach(System.out::println);
    }

    private boolean isDirectory(String fileName) {
        return fileName.charAt(fileName.length() - 1) == '/';
    }

    private void createProject1(String dir) {
        Path start = Paths.get(dir);
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<String> collect = stream
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());

            for (String file : collect) {
                String[] filePath = createFilePath(file);

                if (filePath != null && isRegularFile(filePath[1])) {
                    System.out.println("Copying file from " + filePath[0] + " to " + filePath[1]);
                    FileUtils.copyFile(new File(filePath[0]), new File(filePath[1]));
                }
            }
            //collect.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isRegularFile(String s) {
        Path file = Paths.get(s);
        //return Files.isRegularFile(file);
        return !Files.isDirectory(file);
    }

    private String[] createFilePath(String file) {
        String[] path = file.split("\\\\");
        if (path.length < 2) {
            return null;
        }
        String[] res = new String[2];
        res[0] = file;
        res[1] = path[0]  + (path.length < 2 ? "" : createPath(path));
        System.out.println(file + " <==> " + res[1]);
        return res;
    }

    private String createPath(String[] path) {
        if (path[1].charAt(path[1].length() - 1) != '\\') {
            return "/" + path[1];
        }
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/");
        for (int i = 1; i < path.length - 1; i++) {
            pathBuilder.append(path[i]);
            pathBuilder.append("/");
        }
        try {
            String dirName = path[0] + "//" +  pathBuilder.toString();
            System.out.println("Creating directory " + dirName);
            createDirectory(dirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pathBuilder.append("/");
        pathBuilder.append(path[path.length - 1]);
        return pathBuilder.toString();
    }

    private void createDirectory(String dir) throws IOException {
        Path directory = Paths.get(dir);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
            System.out.println("Directory created" + dir );
        } else {

            System.out.println("Directory already exists" + dir);
        }
    }

    private class O365File {
        Path file;
        File fileName;
        boolean isRegular;
        Set<O365File> childs;

        public O365File(Path file, File fileName, boolean isRegular) {
            this.file = file;
            this.fileName = fileName;
            this.isRegular = isRegular;
            if (!isRegular) {
                childs = new HashSet<>();
            }
        }
    }

    public List<String> commonChars(String[] A) {
        char[] intersection
                = Arrays.stream(A)
                .map(e -> e.toCharArray())
                .reduce((a, b) -> reduceIntersection(a, b))
                .get();
        return charArrToStrList(intersection);


        /*Stream<Character> intersected =  Arrays.stream(A)
                .map(e -> toCharacterStream(e.toCharArray()))
                //.map(e -> e.toCharArray())
                .reduce(( a, b) -> reduceIntersection(a, b))
                //.map(String::valueOf).collect(Collectors.toList());
                .get();
        return intersected.map(String::valueOf).collect(Collectors.toList());*/
    }

    private List<String> charArrToStrList(char[] intersection) {
        List<String> res = new ArrayList<>();
        for (char c : intersection) {
            res.add(String.valueOf(c));
        }
        return res;
    }

    private char[] reduceIntersection(char[] a, char[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        List<Character> list = new ArrayList<>();
        for(int i = 0,  j = 0; i < a.length && j < b.length; ) {
            if (a[i] == b [j]) {
                list.add(Character.valueOf(a[i]));
                i += 1;
                j += 1;
            } else if (a[i] < b[j]) {
                i += 1;
            } else {
                j += 1;
            }
        }
        char[] result = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).charValue();
        }
        return result;
    }

    public boolean isValid(String S) {
        Stack<Character> stack = new Stack<>();
        for (char ch : S.toCharArray()) {
            if (ch == 'c') {
                if (stack.isEmpty() || stack.pop() != 'b') {
                    return false;
                }
                if (stack.isEmpty() || stack.pop() != 'a') {
                    return false;
                }
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    public int longestOnes(int[] A, int K) {
        int res = 0, i = 0;
        for (int j = 0; j < A.length; ++j) {
            if (A[j] == 0) K--;
            if (K < 0 && A[i++] == 0)  K++;
            res = Math.max(res, j - i + 1);
        }
        return res;
    }
    public int longestOnesMe(int[] A, int K) {
        int res = 0, i = 0;
        for (int j = 0; j < A.length; ++j) {
            if (A[j] == 0) {
                K -= 1;
            }
            if (K < 0 ) {
                if(A[i] == 0) {
                    K += 1;
                }
                i += 1;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

    private Integer[] createArrayFromNumber(int number) {
        List<Integer> res = new ArrayList<>();
        while (number != 0) {
            res.add(0, number%10);
            number /= 10;
        }
        return res.toArray(new Integer[res.size()]);
    }

}
