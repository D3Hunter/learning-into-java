package io.d3hunter.test.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/13.
 */
public class StringInstance {
    public static void main(String[] args) {
        worksFine();
        overflow();
    }

    private static void overflow() {
        List<String> data = new ArrayList<>();
        for (int idx = 0; idx < 1000000; idx++) {
            for (int i = 0; i < (new Random().nextInt() % 10); i++) {
                data.add("1111111111122222222222222222" + i);
            }
        }
        System.out.println("overflow: " + data.size());
    }

    private static void worksFine() {
        List<String> data = new ArrayList<>();
        for (int idx = 0; idx < 1000000; idx++) {
            for (int i = 0; i < (new Random().nextInt() % 10); i++) {
                data.add(("1111111111122222222222222222" + i).intern());
            }
        }
        System.out.println("worksFine: " + data.size());
    }
}
