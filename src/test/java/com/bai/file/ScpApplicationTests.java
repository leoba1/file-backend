package com.bai.file;


import com.bai.file.controller.TempFIleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest
public class ScpApplicationTests {

    @Autowired
    private TempFIleController tempFIleController;

    @Test
    public void contextLoads() {

        tempFIleController.deleteDir();

//        int[] asciiCodes = {81, 85, 73, 67, 75, 83, 79, 82, 84, 69, 68, 68, 65, 84, 65, 83};
//        partition(asciiCodes,0,asciiCodes.length-1);
//        System.out.println(Arrays.toString(asciiCodes));

    }
    private static int partition(int[] a, int lo, int hi) {
        int pv = a[(lo + hi) / 2];
        while (lo <= hi) {
            while (a[lo] < pv) lo++;
            while (pv < a[hi]) hi--;
            if (lo <= hi) {
                int temp = a[lo];  a[lo] = a[hi];  a[hi] = temp;
                lo++; hi--;
            }
        }
        return lo;
    }
}
