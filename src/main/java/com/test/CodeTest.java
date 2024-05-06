package com.test;

import java.util.*;
public class CodeTest {

    public static void main(String[] args) {
        CodeTest ct = new CodeTest();
        ct.testTopKCollections();
    }

    private void testTopKCollections() {
        List<File> files  = Arrays.asList(
                new File("A", 5, "t1"),
                new File("B", 7, "t2"),
                new File("C", 6, "t1"),
                new File("D", 12, "t3"),
                new File("E", 2, "t2")
        );
        //t1 = 11, t2 - 9, t3 - 12 k
        List<String> topK = getTopKCollection(files, 2);
        topK.forEach(System.out::println);
    }

    public List<String>  getTopKCollection(List<File> files, int k) {
        Map<String, Integer> tagFiles = new HashMap<>();
        for(File file: files) {
            Integer currSize =  tagFiles.getOrDefault(file.collectionName, 0);
            tagFiles.put(file.collectionName, currSize+file.size);
        }
        return getTagsSortedByFileCountPq(tagFiles, k);
        /*List<TagSize> sortedTagSizeList = getTagsSortedByFileCount(tagFiles);
        List<String> topKTags = new ArrayList<>();
        for(int i = sortedTagsByFileCount.size()-1; i >= 0; i--) {
            topKTags.add(sortedTagsByFileCount.get(i));
            k -= 1;
            if(k == 0) {
                break;
            }
        }
        return topKTags;*/
    }

    public List<String> getTagsSortedByFileCountPq(Map<String, Integer> tagFiles, int k) {
        PriorityQueue<TagSize> pq = new PriorityQueue<>((a, b) -> a.tagSize - b.tagSize);
        for(Map.Entry<String, Integer> entry: tagFiles.entrySet()) {
            pq.offer(new TagSize(entry.getKey(), entry.getValue()));
            if(pq.size() > k) {
                pq.poll();
            }
        }
        List<String> tags = new ArrayList<>();
        while(!pq.isEmpty()) {
            tags.add(pq.poll().tagName);
        }
        return tags;
    }
    public List<String> getTagsSortedByFileCount(Map<String, Integer> tagFiles) {
        List<TagSize> tagSizeList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: tagFiles.entrySet()) {
            tagSizeList.add(new TagSize(entry.getKey(), entry.getValue()));
        }
        Collections.sort(tagSizeList, (a, b) -> a.tagSize - b.tagSize);
        List<String> files = new ArrayList<>();
        for(TagSize ts: tagSizeList) {
            files.add(ts.tagName);
        }

        return files;

    }
    public record TagSize(String tagName, int tagSize){};
    public record File(String name, Integer size, String collectionName){};
}