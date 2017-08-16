package com.lovy.datastructure.sort;

/**
 * Created by asus on 2017/4/27.
 */
public class LSD {

    private static class Node{
        private int[] key;
        private Node next;
        public Node(int val,int maxLen){
            this.key=encode(val,maxLen);
        }

        public int[] encode(int val,int maxLen){
            int[] key=new int[maxLen];
            int index=maxLen-1;
            while(val>0){
                key[index--]=val%10;
                val/=10;
            }
            return key;
        }

        public int decode(){
            int val=0;
            for(int i=0;i<key.length;i++){
                val=val*10+key[i];
            }
            return val;
        }
    }

    private Node first;

    public void LSDSort(){
        Node p=first;

        for(int i=first.key.length-1;i>=0;i--) {
            Node[] front = new Node[10];
            Node[] rear = new Node[10];

            while(p!=null){
                int digit=p.key[i];
                if(front[digit]==null)
                    front[digit]=p;
                else
                    rear[digit].next=p;
                rear[digit]=p;
                p=p.next;
            }

            p=null;
            for(int j=9;j>=0;j--){
                if(front[j]!=null){
                    rear[j].next=p;
                    p=front[j];
                }
            }
        }
        first=p;
    }

    public static LSD createLSD(int maxLen,int... vals){
        LSD lsd=new LSD();
        if(vals.length==0)
            return lsd;
        lsd.first=new Node(vals[0],maxLen);
        Node p=lsd.first;

        for(int i=1;i<vals.length;i++){
            p.next=new Node(vals[i],maxLen);
            p=p.next;
        }
        return lsd;
    }
    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        Node p=first;
        while(p!=null){
            builder.append(p.decode()+"->");
            p=p.next;
        }
        return builder.toString();
    }
//    public static void test(){
//        LSD lsd=createLSD(4,1000,179,208,306,93,854,55,9,271,33);
//        System.out.println(lsd);
//        lsd.LSDSort();
//        System.out.println(lsd);
//    }

}
