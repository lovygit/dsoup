package com.lovy.datastructure.binarytree;

import java.util.*;

/**
 * Created by asus on 2017/4/11.
 */
public class HuffmanTree extends LovyTree<Integer> {

    public HuffmanTree(){}

    public HuffmanTree(LovyNode<Integer> root){
        this.root=root;
    }

    public static HuffmanTree createHuffmanTree(List<LovyNode<Integer>> nodes){
        if(nodes==null||nodes.size()==0)
            return null;

        HuffmanTree huffmanTree=new HuffmanTree();

        while(nodes.size()>1){

            int minIndexA=0;
            int minIndexB=1;

            int flagA=nodes.get(0).val;

            int flagB=nodes.get(1).val;

            for(int i=2;i<nodes.size();i++){
                if(flagA>flagB){
                    if(nodes.get(i).val<flagA){
                        minIndexA=i;
                        flagA=nodes.get(i).val;
                    }
                }else{
                    if(nodes.get(i).val<flagB){
                        minIndexB=i;
                        flagB=nodes.get(i).val;
                    }
                }
            }

            if(minIndexA>minIndexB){
                int tmp=minIndexA;
                minIndexA=minIndexB;
                minIndexB=tmp;
            }

            LovyNode<Integer> newNode=new LovyNode<>(flagA+flagB,nodes.get(minIndexA),nodes.get(minIndexB));

            nodes.remove(minIndexB);
            nodes.remove(minIndexA);

            nodes.add(newNode);
        }
        huffmanTree.root=nodes.get(0);
        return huffmanTree;
    }


    public static HuffmanTree createHuffmanTree(Integer... vals){
        if(vals==null||vals.length==0)
            return null;

        List<LovyNode<Integer>> nodes=new LinkedList<>();

        for(int val:vals){
            nodes.add(new LovyNode<>(val));
        }

        return createHuffmanTree(nodes);
    }


    private void getHuffmanWeight(LovyNode<Integer> root,int depth,int[] weight){
        if(root.left==null&&root.right==null){
            weight[0]+=root.val*depth;
        }else{
            if(root.left!=null)
                getHuffmanWeight(root.left,depth+1,weight);
            if(root.right!=null)
            getHuffmanWeight(root.right,depth+1,weight);
        }
    }

    public int weight(){
        int[] weight=new int[]{0};
        getHuffmanWeight(this.root,0,weight);
        return weight[0];
    }


    public static void test(){

        Map<Integer,Integer>  map=new HashMap<>();
        for(int i=1;i<100;i++){
            Integer[] vals=new Integer[i];
            for(int j=0;j<i;j++)
                vals[j]=j+1;

            HuffmanTree huffmanTree=createHuffmanTree(vals);

//            huffmanTree.print();

            map.put(i,huffmanTree.weight());
        }
        System.out.println(map);

    }
}
