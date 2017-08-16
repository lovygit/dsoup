package com.lovy.datastructure;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyUnionFindSets {
    private int maxSize=16;
    private int size=0;
    private int[] sets=new int[maxSize];//j=sets[i] means that j is parent of i

    public LovyUnionFindSets(){
        for(int i=0;i<sets.length;i++)
            sets[i]=-1;
    }

    public LovyUnionFindSets(int maxSize){
        this.maxSize=maxSize;
        sets=new int[this.maxSize];
        for(int i=0;i<sets.length;i++)
            sets[i]=-1;
    }


    public void union(int i,int j){

        this.size=Math.max(this.size,Math.max(i,j));
        int tmp=sets[i]+sets[j];

        int parentI=parent(i);
        int parentJ=parent(j);
        if(parentI<=parentJ){
            sets[parentI]+=sets[parentJ];
            sets[j]=parentI;
        }else{
            sets[parentJ]+=sets[parentI];
            sets[i]=parentJ;
        }
    }


    public int parent(int i){
        int j=i;
        while(j>=0&&sets[j]>=0){
            j=sets[j];
        }
        int parent=j;

        while(i>=0&&sets[i]>=0) {
            int preParent = sets[i];
            sets[i] = parent;
            i = preParent;
        }
        return parent;
    }

    public static void test(){
        //[<0,2>(10), <3,5>(12), <1,4>(14), <1,3>(16), <5,4>(18), <5,6>(22), <6,4>(24), <6,2>(25), <0,1>(28)]
        LovyUnionFindSets sets=new LovyUnionFindSets();
    }
}
