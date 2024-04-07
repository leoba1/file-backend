package com.bai.file;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest
public class ScpApplicationTests {

    @Test
    public void contextLoads() {
        int[] asciiCodes = {81, 85, 73, 67, 75, 83, 79, 82, 84, 69, 68, 68, 65, 84, 65, 83};
        partition(asciiCodes,0,asciiCodes.length-1);
        System.out.println(Arrays.toString(asciiCodes));
    }
    private static int partition(int[] a, int lo, int hi) {
        int pv = a[(lo + hi) / 2];  // 配列の中央の値を pvとして設定
        while (lo <= hi) {
            while (a[lo] < pv) lo++;  // a[lo]が pv 以上になるまで lo を+1
            while (pv < a[hi]) hi--;  // a[hi]が pv 以下になるまで lo を-1
            if (lo <= hi) {
                int temp = a[lo];  a[lo] = a[hi];  a[hi] = temp; // lo と hi を交換
                lo++; hi--;
            }
        }
        return lo;
    }
}
