package com.lovy.datastructure.divideandconquer;

import java.util.*;

/**
 * Created by asus on 2017/4/24.
 */
public class Smaller {
    public List<Integer> countSmaller(int[] nums) {
        Pair[] pairs=new Pair[nums.length];
        for(int i=0;i<nums.length;i++){
            pairs[i]=new Pair(nums[i],i);
        }

        Map<Integer,Integer> map=new HashMap<>();
        mergeSort(pairs,0,pairs.length-1,map);

        Collection<Integer> values=map.values();
        List<Integer> smaller=new ArrayList<>();

        for(Integer val:values)
            smaller.add(val);
        smaller.add(0);
        return smaller;
    }

    public void mergeSort(Pair[] pairs,int left,int right,Map<Integer,Integer> map){
        if(left>=right)
            return;

        int middle=(left+right)/2;
        mergeSort(pairs,left,middle,map);
        mergeSort(pairs,middle+1,right,map);


        int n=0;
        for(int i=left,j=middle+1;i<=middle;){
            if(j>right||pairs[j].val>=pairs[i].val){
                map.put(pairs[i].index,map.getOrDefault(pairs[i].index,0)+n);
                i++;
            }else{
                j++;
                n++;
            }
        }
        Arrays.sort(pairs,left,right+1);
    }

    private static class Pair implements Comparable<Pair>{
        private int val;
        private int index;

        public Pair(int val,int index){
            this.val=val;
            this.index=index;
        }


        @Override
        public int compareTo(Pair o) {
            if(val>o.val)
                return 1;
            else if(val<o.val)
                return -1;
            else
                return 0;
        }

        @Override
        public String toString(){
            return "("+val+","+index+")";
        }
    }


    public static void test(){
        Smaller smaller=new Smaller();
        int[] nums={5,2,6,1};
        System.out.println(smaller.countSmaller(nums));
    }
}
