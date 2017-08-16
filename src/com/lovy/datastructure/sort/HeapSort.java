package com.lovy.datastructure.sort;

/**
 * Created by asus on 2017/4/26.
 */
public class HeapSort {
    public void adjust(int[] nums,int root,int n){
        int parent=root;
        int child=root;
        int val=nums[root];
        while(parent*2+1<n){
            child=parent*2+1;
            if(child+1<n&&nums[child+1]>nums[child])
                child++;
            if(nums[child]>val){
                nums[parent]=nums[child];
                parent=child;
            }else
                break;
        }
        nums[parent]=val;
    }

    public int[] heapSort(int[] nums){
        for(int i=nums.length/2-1;i>=0;i--)
            adjust(nums,i,nums.length);
        for(int i=nums.length-2;i>=0;i--){
            int tmp=nums[0];
            nums[0]=nums[i+1];
            nums[i+1]=tmp;
            adjust(nums,0,i+1);
        }
        return nums;
    }
}
