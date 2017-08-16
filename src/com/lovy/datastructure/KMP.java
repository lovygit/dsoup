package com.lovy.datastructure;

import java.util.Arrays;

/**
 * Created by asus on 2017/4/6.
 */
public class KMP {
    /**
     * If when you match the strs at index <em>i</em>,the next possible available index is next[i]
     * So,array <i>next</i> has <em>two</em> certain element : next[0]=-1,next[1]=0
     * @param pattern
     * @return
     */
    public int[] createNextArray(String pattern){
        int[] next=new int[pattern.length()];
        next[0]=-1;
        next[1]=0;
        int j;
        for(int i=2;i<next.length;i++){
            char preCh=pattern.charAt(i-1);
            j=next[i-1];
            while(j>-1&&preCh!=pattern.charAt(j))
                j=next[j];

            if(j>-1&&preCh==pattern.charAt(j))
                next[i]=j+1;
            else
                next[i]=0;
        }
        return next;
    }

    /**
     * If when you match the strs at index <em>i</em>,the next possible available index is failure[i]+1
     * So ,array<i>failure</i> has <em>one</em> certain element : failure[0]=-1
     * @param pattern
     * @return
     */
    public int[] createFailureArray(String pattern){
        int[] failure=new int[pattern.length()];
        failure[0]=-1;
        int j;
        for(int i=1;i<failure.length;i++){
            j=failure[i-1];
            while(pattern.charAt(i)!=pattern.charAt(j+1)&&j>-1)
                j=failure[j];

            if(pattern.charAt(i)==pattern.charAt(j+1))
                failure[i]=j+1;
            else
                failure[i]=-1;
        }
        return failure;
    }



    public int KMPSearchWithNextArray(String s,String pattern){
        int[] nextArray=createNextArray(pattern);
        int i=0;
        int j=0;

        while(i<s.length()&&j<pattern.length()){
            if(s.charAt(i)==pattern.charAt(j)){
                i++;
                j++;
            }else if(j==0)i++;
            else
                j=nextArray[j];
        }
        if(j==pattern.length())
            return i-j;
        else
            return -1;
    }


    public int KMPSearchWithFailureArray(String s,String pattern){
        int[] failureArray=createFailureArray(pattern);
        int i=0,j=0;
        while(i<s.length()&&j<pattern.length()){
            if(s.charAt(i)==pattern.charAt(j)){
                i++;
                j++;
            }else if(j==0)i++;
            else
                j=failureArray[j-1]+1;//You'd better compare this statement with the corresponding statement in [KMPSearchWithNextArray] method.
        }
        if(j==pattern.length())
            return i-j;
        return -1;
    }

    private static KMP kmp=new KMP();
    public static void test(){
        String pattern="abcabc";
        System.out.println("The [   next] array of [ "+pattern+" ] is "+ Arrays.toString(kmp.createNextArray(pattern)));
        System.out.println("The [failure] array of [ "+pattern+" ] is "+Arrays.toString(kmp.createFailureArray(pattern)));

        String s="****"+pattern;
        System.out.println(kmp.KMPSearchWithNextArray(s,pattern));
        System.out.println(kmp.KMPSearchWithFailureArray(s,pattern));
    }
}
