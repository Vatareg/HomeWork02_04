package org.incorp.i;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Main {
    final static int SIZE = 10_000_000;
    final static int HALFLIVE = SIZE / 2;

    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        fillArr(arr);
        form0(arr);
        fillArr(arr);
        form1(arr);
    }

    public static void fillArr(float[] arr) {
        Arrays.fill(arr, 1);
    }

    public static void form0(float[] arr) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Vremy: " + (endTime - startTime) + "ms");
        System.out.println(HALFLIVE);
    }

    public static void form1(float[] arr) {
        long starTime = System.currentTimeMillis();
        float[] a1 = new float[HALFLIVE];
        float[] a2 = new float[HALFLIVE];
        System.arraycopy(arr, 0, a1, 0, HALFLIVE);
        System.arraycopy(arr, HALFLIVE, a2, 0, HALFLIVE);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a1[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALFLIVE);
        System.arraycopy(a1, 0, arr, HALFLIVE, HALFLIVE);
        long endTime = System.currentTimeMillis();
        System.out.println("Vremy: " + (endTime - starTime));
        System.out.println(arr[HALFLIVE]);
    }
}
