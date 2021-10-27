package com.oracle.casb;

import com.google.common.collect.ImmutableSet;
import com.oracle.casb.common.FSPermissions;

import java.util.Set;

import static com.oracle.casb.common.FSPermissions.*;

/**
 * Created By : abhijsri
 * Date  : 04/06/18
 **/
public class TestFileAPermissions {

    public static void main(String[] args) {
        TestFileAPermissions testPermission = new TestFileAPermissions();
        testPermission.testFilePermisison();
    }

    private void testFilePermisison() {
        Set<FSPermissions> permissions =ImmutableSet.<FSPermissions>of( SCREEN_CAPTURE, FULL_CONTROL);

        System.out.println("File Permission - " + FSPermissions.calculateFilePermissions(permissions));
    }
}
