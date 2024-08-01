package com.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class FileSharing {

    PriorityQueue<Integer> availableUserIds;
    Integer nextUserId;
    Map<Integer, TreeSet<Integer>> ownedChunks;

    public FileSharing(int m) {
        this.availableUserIds = new PriorityQueue<>();
        this.nextUserId = 1; //new AtomicInteger(1);
        this.ownedChunks = new HashMap<>();
    }

    public int join(List<Integer> ownedChunks) {
        int userId = -1;
        if(availableUserIds.isEmpty()) {
            userId = this.nextUserId;
            nextUserId += 1;
        } else {
            userId = availableUserIds.poll();
        }
        this.ownedChunks.computeIfAbsent(userId, e -> new TreeSet<>()).addAll(ownedChunks);
        return userId;
    }

    public void leave(int userID) {
        availableUserIds.add(userID);
        this.ownedChunks.remove(userID);

    }

    public List<Integer> request(int userID, int chunkID) {
        List<Integer> res = new ArrayList<>();
        for(var t: this.ownedChunks.entrySet()) {
            if(t.getValue().contains(chunkID)) {
                res.add(t.getKey());
            }
        }
        this.ownedChunks.computeIfAbsent(userID, e -> new TreeSet<>()).add(chunkID);
        return res;
    }

    public static void main(String[] args) {
        FileSharing fs = new FileSharing(17);
        fs.join(Arrays.asList());
        fs.join(Arrays.asList(6,8,7,15,16,9,10,4,13,12,5,14,1,11,2,17,3));
        fs.join(Arrays.asList(9,11,14,16,10,6,1,15,12));
        fs.join(Arrays.asList());
        fs.join(Arrays.asList(17,10,16));
        fs.request(1,6);
        fs.request(1,1);
        fs.request(1,13);
        fs.request(5,15);
        fs.request(3,5);
        fs.request(2,5);
        fs.request(1,4);
        fs.request(1,7);
        fs.request(2,15);
        fs.request(3,5);
        fs.request(2,1);
    }
}
