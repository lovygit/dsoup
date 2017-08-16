package com.lovy.datastructure.tree;


import com.lovy.datastructure.binarytree.LovyNode;
import com.lovy.datastructure.binarytree.LovyTree;
import org.omg.CORBA.Any;

import java.util.List;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyGeneralTree<T> {
    private int maxSize=16;
    private int size=0;
    private LovyGeneralNode<T> root=null;
    private LovyGeneralNode<T>[] tree=new LovyGeneralNode[maxSize];

    public LovyGeneralTree(){}
    public LovyGeneralTree(int maxSize){
        this.maxSize=maxSize;
        tree=new LovyGeneralNode[maxSize];
    }

    public LovyGeneralTree(LovyGeneralNode<T> root){
        this.root=root;
    }


    public LovyGeneralNode<T> getRoot(){
        return this.root;
    }
    public static <AnyType> LovyGeneralTree createLovyGeneralTree(List<AnyType[]> pairs){
        LovyGeneralTree lovyGeneralTree=new LovyGeneralTree(pairs.size()*2);

        for(int i=0;i<pairs.size();i++){
            AnyType[] currentPair=pairs.get(i);

            AnyType valA=currentPair[0];
            AnyType valB=currentPair[1];

            boolean containsA=false;
            boolean containsB=false;

            int indexA=-1;
            int indexB=-1;
            for(int j=0;j<lovyGeneralTree.size;j++){
                if(lovyGeneralTree.tree[j].getVal().equals(valA)){
                    indexA=j;
                    containsA=true;
                }
                if(lovyGeneralTree.tree[j].getVal().equals(valB)){
                    indexB=j;
                    containsB=true;
                }
                if(containsA||containsB)
                    break;
            }

            if(containsA&&containsB)
                continue;

            if(!containsA&&!containsB){
                LovyGeneralNode leftChild=new LovyGeneralNode(valB);
                if(!valA.equals(valB))
                    lovyGeneralTree.tree[lovyGeneralTree.size++]=new LovyGeneralNode(valA,leftChild,null);
                else
                    lovyGeneralTree.tree[lovyGeneralTree.size++]=new LovyGeneralNode(valA);

                if(!valA.equals(valB))
                    lovyGeneralTree.tree[lovyGeneralTree.size++]=leftChild;
            }else{
                if(containsB){
                    indexA=indexB;
                    valB=valA;
                }

                lovyGeneralTree.tree[lovyGeneralTree.size++]=new LovyGeneralNode(valB);
                LovyGeneralNode p=lovyGeneralTree.tree[indexA].getLeft();
                if(p==null)
                    lovyGeneralTree.tree[indexA].setLeft(lovyGeneralTree.tree[lovyGeneralTree.size-1]);
                else{
                    while(p.getNextSibling()!=null)
                        p=p.getNextSibling();

                    p.setNextSibling(lovyGeneralTree.tree[lovyGeneralTree.size-1]);
                }

            }
        }
        lovyGeneralTree.root=(lovyGeneralTree.size==0)?null:lovyGeneralTree.tree[0];
        return lovyGeneralTree;
    }


    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        LovyGeneralNode p;
        for(int i=0;i<this.size;i++){
            builder.append("[ "+this.tree[i].getVal()+" ] >");

            p=this.tree[i].getLeft();

            while(p!=null){
                builder.append(p.getVal()+",");
                p=p.getNextSibling();
            }
            builder.append("\n");
        }
        return builder.toString();
    }

//
    public static <AnyType> LovyNode<AnyType> createLovyNodeFromLovyGeneralNode(LovyGeneralNode<AnyType> lovyGeneralNode){
        if(lovyGeneralNode==null)
            return null;
        LovyNode<AnyType> root=new LovyNode<AnyType>(lovyGeneralNode.getVal());

        root.setLeft(createLovyNodeFromLovyGeneralNode(lovyGeneralNode.getLeft()));

        LovyGeneralNode<AnyType> p=lovyGeneralNode.getNextSibling();
        LovyNode q=root;
        while(p!=null){
            LovyNode<AnyType> rightNode=createLovyNodeFromLovyGeneralNode(p);
            q.setRight(rightNode);
            p=p.getNextSibling();
            q=q.getRight();
        }
        return root;
    }


    public static <AnyType> LovyTree<AnyType> createLovyTreeFromLovyGeneralTree(LovyGeneralTree<AnyType> lovyGeneralTree){
        LovyTree<AnyType> lovyTree=new LovyTree<AnyType>(createLovyNodeFromLovyGeneralNode(lovyGeneralTree.tree[0]));
        return lovyTree;
    }


    public static <AnyType> LovyTree<AnyType> createLovyTreeFromLovyForest(LovyGeneralTree<AnyType>[] forest){
        if(forest==null||forest.length==0)
            return null;
        LovyTree<AnyType> root=new LovyTree<AnyType>(createLovyNodeFromLovyGeneralNode(forest[0].root));
        LovyNode<AnyType> p=root.getRoot();
        for(int i=1;i<forest.length;i++){
            p.setRight(createLovyNodeFromLovyGeneralNode(forest[i].root));
            p=p.getRight();
        }
        return root;
    }


    public static <AnyType> LovyTree<AnyType>[] createLovyForestFromLovyTree(LovyTree<AnyType> lovyTree){
        int treeNum=lovyTree.getRightestPathLen();
        LovyTree<AnyType>[] forest=new LovyTree[treeNum];

        LovyNode p=lovyTree.getRoot();

        int index=0;
        LovyNode q;
        while(p!=null){
            forest[index++]=new LovyTree<AnyType>(p);
            q=p.getRight();
            p.setRight(null);
            p=q;
        }
        return forest;
    }


    public static <AnyType> LovyGeneralNode<AnyType> createLovyGeneralNodeFromLovyNode(LovyNode<AnyType> root){
        if(root==null)
            return null;
        LovyGeneralNode<AnyType> lovyGeneralNode=new LovyGeneralNode<AnyType>(root.getVal());
        lovyGeneralNode.setLeft(createLovyGeneralNodeFromLovyNode(root.getLeft()));

        LovyNode<AnyType> p=root.getRight();

        LovyGeneralNode<AnyType> q=lovyGeneralNode;

        while(p!=null){
            q.setNextSibling(createLovyGeneralNodeFromLovyNode(p));
            p=p.getRight();
            q=q.getNextSibling();
        }
        return lovyGeneralNode;
    }


    private static <AnyType>  void fillTreeArray(LovyGeneralNode<AnyType> root,LovyGeneralNode<AnyType>[] treeArray,int[] index){
        if(root==null)
            return;
        treeArray[index[0]++]=root;
        fillTreeArray(root.getLeft(),treeArray,index);
        fillTreeArray(root.getNextSibling(),treeArray,index);
    }

    public static <AnyType> LovyGeneralTree<AnyType> createLovyGeneralTreeFromLovyTree(LovyTree<AnyType> lovyTree){
        LovyGeneralTree<AnyType> lovyGeneralTree=new LovyGeneralTree<AnyType>(createLovyGeneralNodeFromLovyNode(lovyTree.getRoot()));

        int[] index=new int[]{0};
        fillTreeArray(lovyGeneralTree.root,lovyGeneralTree.tree,index);

        lovyGeneralTree.size=index[0];

        return lovyGeneralTree;
    }







    /**
     * this is the preorder in <em>general tree</em>
     */
    private static <T> void preorderGeneralTree(LovyGeneralNode<T> root){
        if(root==null)
            return;

        System.out.print(root.getVal()+"->");
        preorderGeneralTree(root.getLeft());
        preorderGeneralTree(root.getNextSibling());
    }


    public void preorderGeneralTree(){
        preorderGeneralTree(this.root);
        System.out.println("STOP");
    }


    /**
     * the preorder of general tree equals the inorder in it's binary tree expression
     */
    private static <T> void postorderGeneralTree(LovyGeneralNode<T> root){
        if(root==null)
            return;

        postorderGeneralTree(root.getLeft());
        System.out.print(root.getVal()+"->");
        postorderGeneralTree(root.getNextSibling());
    }


    public void postorderGeneralTree(){
        postorderGeneralTree(this.root);
        System.out.println("STOP");
    }



    //at this part,we will construct the preorder and inorder method of forest

    public static <AnyType> void preorderForForest(LovyGeneralTree<AnyType>[] forest){
        for(int i=0;i<forest.length;i++){
            preorderGeneralTree(forest[i].root);
        }
        System.out.println("STOP");
    }


    public static <AnyType> void inorderForForest(LovyGeneralTree<AnyType>[] forest){
        for(int i=0;i<forest.length;i++){
            postorderGeneralTree(forest[i].root);
        }
        System.out.println("STOP");
    }








    public static void test()throws Exception{
        LovyGeneralTree<Character>[] forest=new LovyGeneralTree[3];
        for(int i=0;i<3;i++){
            LovyGeneralTree lovyGeneralTree=createLovyGeneralTree(LovyGeneralParentPointTree.getTestData("d:/labrep/tree"+(i+1)+".lvy"));
            forest[i]=lovyGeneralTree;
        }

        createLovyTreeFromLovyForest(forest).inOrder();
        inorderForForest(forest);
    }

}
