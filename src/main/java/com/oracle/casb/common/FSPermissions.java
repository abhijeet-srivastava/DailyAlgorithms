package com.oracle.casb.common;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

public enum FSPermissions {
    VIEW("View", 2),
    LITE_VIEWER("Lite Viewer", 6),
    EDIT("Edit", 34),
    COPY_DATA("Copy Data", 258),
    PRINT("Print", 10),
    SCREEN_CAPTURE("Screen Capturer", 514),
    MACRO("Macro", 1026),
    FULL_CONTROL("Full  Control", 170);

    private String name;
    private Integer filePermission;

    private static Map<String, FSPermissions> permissionsMap
            = ImmutableMap.<String, FSPermissions>builder()
            .put("View", VIEW)
            .put("Lite Viewer", LITE_VIEWER)
            .put("Edit", EDIT)
            .put("Copy Data", COPY_DATA)
            .put("Print", PRINT)
            .put("Screen Capturer", SCREEN_CAPTURE)
            .put("Macro", MACRO)
            .put("Full  Control", FULL_CONTROL)
            .build();

    public static Integer calculateFilePermissions(Set<FSPermissions> permissionSet) {
        int result = VIEW.filePermission;
        for (FSPermissions permission : permissionSet) {
            /*if (permission == VIEW) {
                continue;
            }*/
            result |= permission.filePermission;
        }
        return Integer.valueOf(result);
    }

    public static FSPermissions getFilePermisison(String permissionName) {
        if (permissionsMap.containsKey(permissionName)) {
            return permissionsMap.get(permissionName);
        } else {
            throw new RuntimeException("Invalid permisison :" + permissionName);
        }
    }

    FSPermissions(String name, Integer filePermission) {
        this.name = name;
        this.filePermission = filePermission;
    }

    public String getName() {
        return name;
    }

    public Integer getFilePermission() {
        return filePermission;
    }
}
