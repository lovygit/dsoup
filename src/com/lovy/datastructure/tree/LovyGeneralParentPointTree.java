package com.lovy.datastructure.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyGeneralParentPointTree<T> {
    private int maxSize=16;
    private int size=0;
    private LovyGeneralParentPointNode[] tree=new LovyGeneralParentPointNode[maxSize];

    public LovyGeneralParentPointTree(){}

    public LovyGeneralParentPointTree(int maxSize){
        this.maxSize=maxSize;
        tree=new LovyGeneralParentPointNode[maxSize];
    }

    public static <AnyType> LovyGeneralParentPointTree<AnyType> createTree(List<AnyType[]> pairs){
        LovyGeneralParentPointTree<AnyType> parentPointTree=new LovyGeneralParentPointTree<AnyType>(pairs.size()*2);

        for(int i=0;i<pairs.size();i++){
            AnyType[] currentPair=pairs.get(i);

            AnyType valA=currentPair[0];
            AnyType valB=currentPair[1];

            boolean containsA=false;
            int indexA=-1;
            boolean containsB=false;
            int indexB=-1;

            for(int j=0;j<parentPointTree.size;j++){
                if(parentPointTree.tree[j].getVal().equals(valA)) {
                    containsA=true;
                    indexA=j;
                }
                if(parentPointTree.tree[j].getVal().equals(valB)){
                    containsB=true;
                    indexB=j;
                }

                if(containsA||containsB)
                    break;
            }

            if(containsA&&containsB)
                continue;

            if(!containsA&&!containsB){
                parentPointTree.tree[parentPointTree.size++]=new LovyGeneralParentPointNode(valA);
                if(!valA.equals(valB))
                    parentPointTree.tree[parentPointTree.size++]=new LovyGeneralParentPointNode(valB,parentPointTree.size-2);
            }
            else if(containsA){
                parentPointTree.tree[parentPointTree.size++]=new LovyGeneralParentPointNode(valB,indexA);
            }
            else{
                parentPointTree.tree[parentPointTree.size++]=new LovyGeneralParentPointNode(valA,indexB);
            }
        }
        return parentPointTree;
    }



    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<this.size;i++){
            builder.append("(");
            builder.append(this.tree[i].getVal());
            builder.append(",");
            builder.append(this.tree[i].getParentIndex());
            builder.append(")");
        }
        return builder.toString();
    }


    /**
     * the content of file d:/treepair.lovy  RA\nRB\nRC\nAD\nAE\nCF\nFG\nFH\nFK
     * @param
     * @throws Exception
     */


    public static List<Character[]> getTestData(String fileName) throws Exception{
        BufferedReader reader=new BufferedReader(new FileReader(new File(fileName)));

        List<String> lines=new ArrayList<>();

        String line="";
        while((line=reader.readLine())!=null){
            lines.add(line);
        }

        reader.close();

        List<Character[]> pairs=new ArrayList<>();

//        System.out.println(lines);
        for(String ln:lines){
            Character[] pair=new Character[]{ln.charAt(0),ln.charAt(1)};
            pairs.add(pair);
        }

        return pairs;
    }

    public static void test() throws Exception {

    }
}
