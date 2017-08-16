package com.lovy.datastructure.sort;

import java.util.Arrays;

/**
 * Created by asus on 2017/6/11.
 */
public class ShellSort {
    public static <AnyType extends Comparable<? super AnyType>> AnyType[] shellSort(AnyType[] a){
        int j;
        for(int gap=a.length/2;gap>0;gap/=2) {
            for (int i = gap; i < a.length; i++) {
                AnyType tmp = a[i];
                for (j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap)
                    a[j] = a[j - gap];
                a[j] = tmp;
            }
            System.out.println(Arrays.toString(a));
        }
        return a;
    }
}
