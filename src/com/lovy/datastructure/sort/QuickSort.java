package com.lovy.datastructure.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by asus on 2017/5/3.
 */
public class QuickSort {
    private static void quickSort2(int[] arr,int left,int right){
        if(left>=right)
            return;

        int i=left;
        int j=right;
        int tmp;
        int flag=arr[(left+right)/2];
        do{
            //the judge condition include the '=',because the val=flag should also compare with other values...
            while((arr[i]<flag)&&(i<right)) i++;
            while((flag<arr[j])&&(j>left)) j--;
            if(i<j){
                tmp=arr[i];
                arr[i]=arr[j];
                arr[j]=tmp;
                i++;
                j--;
            }
        }while(i<j);

        quickSort2(arr,left,Math.min(j,right-1));
        quickSort2(arr,Math.max(left+1,i),right);
    }


    private static int partition(int[] arr,int p,int r){
        int pivot=arr[r];
        int i=p;

        for(int j=p;j<r;j++)
            if(arr[j]<=pivot){
                if(i<j){
                    int tmp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=tmp;
                }
                i++;
            }

        arr[r]=arr[i];
        arr[i]=pivot;

        return i;
    }


    public static void quickSort3(int[] arr,int left,int right){
        if(left<right){
            int pos=partition(arr,left,right);
            quickSort3(arr,left,pos-1);
            quickSort3(arr,pos+1,right);
        }
    }

    private static int randomPartition(int[] arr,int p,int r){
        if(p==r)
            return p;

        int q=new Random().nextInt(r-p)+p;
        int tmp=arr[r];
        arr[r]=arr[q];
        arr[q]=tmp;
        return partition(arr,p,r);
    }

    public static void quickSortRandom(int[] arr,int left,int right){
        if(left<right){
            int pos=randomPartition(arr,left,right);
            quickSortRandom(arr,left,pos-1);
            quickSortRandom(arr,pos+1,right);
        }
    }


    /**
     * find the ist smallest element
     */

    public static int randomSelect(int[] arr,int left,int right,int i){
        if(left==right)
            return arr[left];

        int q=randomPartition(arr,left,right);

        int k=q-left+1;

        if(k==i)
            return arr[q];
        else if(i<k)
            return randomSelect(arr,left,q-1,i);
        else
            return randomSelect(arr,q+1,right,i-k);
    }


    public static void test(){
        Random random=new Random();
        for(int i=0;i<10;i++){
            int len=Math.max(1,random.nextInt(20));
            int[] arr=new int[len];
            for(int k=0;k<arr.length;k++)
                arr[k]=random.nextInt(100);

            quickSortRandom(arr,0,arr.length-1);

            System.out.println("After random sort: "+Arrays.toString(arr));

            int pos=randomSelect(arr,0,arr.length-1,(arr.length+1)/2);
            System.out.println("The median item: "+pos);
        }
    }
}
