package com.lovy.datastructure.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by asus on 2017/6/24.
 */
public class MergeSort {
    private static void mergeA(int[] data,int startA,int startB){
        int lenA=startB-startA;
        int lenB=Math.min(data.length-startB,lenA);
        int[] tmp=new int[lenA+lenB];

        int i=startA,j=startB;
        int k=0;
        while(i<startB&&j<startB+lenB){
            if(data[i]<=data[j])
                tmp[k++]=data[i++];
            else
                tmp[k++]=data[j++];
        }

        while(i<startB)
            tmp[k++]=data[i++];
        while(j<startB+lenB)
            tmp[k++]=data[j++];

        for(k=0;k<lenA+lenB;k++)
            data[startA+k]=tmp[k];
    }


    private static void mergeB(int[]  data,int len){
        int i;
        for(i=0;i+2*len<=data.length;i+=2*len)
            mergeA(data,i,i+len);

        if(i+len<data.length)
            mergeA(data,i,i+len);
    }


    private static void merge(int[] data){
        int len=1;
        while(len<data.length){
            mergeB(data,len);
            System.out.println(Arrays.toString(data));
            len*=2;
        }
    }

    public static void test(){
        int[] data=new int[]{3,1,2,8,7,5,9,4,0,6};
        merge(data);
        System.out.println(Arrays.toString(data));
    }
}
