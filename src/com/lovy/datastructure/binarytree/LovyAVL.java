package com.lovy.datastructure.binarytree;

import java.util.Random;

/**
 * Created by asus on 2017/4/11.
 */
public class LovyAVL<T extends Comparable<? super T>> extends LovyBST<T>{
    public LovyAVL(){}
    public LovyAVL(LovyAVLNode<T> root){
        super(root);
    }

    private LovyAVLNode<T> insert(LovyAVLNode<T> root,T val){
        if(root==null)
            return new LovyAVLNode<T>(val);

        int compareResult=root.val.compareTo(val);

        if(compareResult<0)
            root.setRight(insert((LovyAVLNode<T>) root.right,val));
        else if(compareResult>0)
            root.setLeft(insert((LovyAVLNode<T>)root.left,val));
        else
            ;
        return balance(root);
    }

    /**
     * this rotate method res
     * @param root
     * @return
     */
    public LovyAVLNode<T> rotateWithLeftChild(LovyAVLNode<T> root){
        LovyAVLNode<T> leftChild= (LovyAVLNode<T>) root.left;
        root.left=leftChild.right;
        leftChild.right=root;
        root.setHeight(Math.max(LovyAVLNode.height((LovyAVLNode<T>)root.left),LovyAVLNode.height((LovyAVLNode<T>)root.right))+1);
        leftChild.setHeight(Math.max(LovyAVLNode.height((LovyAVLNode<T>)leftChild.left),LovyAVLNode.height((LovyAVLNode<T>)leftChild.right))+1);
        return leftChild;
    }


    public LovyAVLNode<T> rotateWithRightChild(LovyAVLNode<T> root){
        LovyAVLNode<T> rightChild= (LovyAVLNode<T>) root.getRight();
        root.setRight(rightChild.left);
        rightChild.setLeft(root);
        root.setHeight(Math.max(LovyAVLNode.height((LovyAVLNode<T>)root.left),LovyAVLNode.height((LovyAVLNode<T>)root.right))+1);
        rightChild.setHeight(Math.max(LovyAVLNode.height((LovyAVLNode<T>)rightChild.left),LovyAVLNode.height((LovyAVLNode<T>)rightChild.right))+1);
        return rightChild;
    }


    public LovyAVLNode<T> doubleWithLeftChild(LovyAVLNode<T> root){
        root.setLeft(rotateWithRightChild((LovyAVLNode<T>) root.left));
        return rotateWithLeftChild(root);
    }

    public LovyAVLNode<T> doubleWithRightChild(LovyAVLNode<T> root){
        root.setRight(rotateWithLeftChild((LovyAVLNode<T>)root.right));
        return rotateWithRightChild(root);
    }



    public static <AnyType extends Comparable<? super AnyType>>  boolean contains(LovyAVLNode<AnyType> root,AnyType val){
        while(root!=null){
            int compare=root.getVal().compareTo(val);
            if(compare==0)
                return true;
            else if(compare<0)
                root= (LovyAVLNode<AnyType>) root.left;
            else
                root= (LovyAVLNode<AnyType>) root.right;
        }
        return false;
    }

    public LovyAVLNode<T> balance(LovyAVLNode<T> root){
        if(root==null)
            return null;

        if(LovyAVLNode.height((LovyAVLNode<T>)root.left)-LovyAVLNode.height((LovyAVLNode<T>)root.right)>1){
            if(LovyAVLNode.height((LovyAVLNode<T>)root.left.left)>LovyAVLNode.height((LovyAVLNode<T>)root.left.right))
                root=rotateWithLeftChild(root);
            else
                root=doubleWithLeftChild(root);
        }else if(LovyAVLNode.height((LovyAVLNode<T>)root.right)-LovyAVLNode.height((LovyAVLNode<T>)root.left)>1){
            if(LovyAVLNode.height((LovyAVLNode<T>)root.right.right)>LovyAVLNode.height((LovyAVLNode<T>)root.right.left))
                root=rotateWithRightChild(root);
            else
                root=doubleWithRightChild(root);
        }
        root.setHeight(Math.max(LovyAVLNode.height((LovyAVLNode<T>)root.left),LovyAVLNode.height((LovyAVLNode<T>)root.right))+1);
        return root;
    }

    private boolean checkBalance(LovyAVLNode<T> root){
        if(root==null)
            return true;
        return Math.abs(LovyAVLNode.height((LovyAVLNode<T>)root.left)-LovyAVLNode.height((LovyAVLNode<T>)root.right))<2&&
                checkBalance((LovyAVLNode<T>) root.left)&&checkBalance((LovyAVLNode<T>) root.right);
    }

    public boolean checkBalance(){
        return checkBalance((LovyAVLNode<T>) this.root);
    }


    public static class LovyAVLNode<T extends Comparable<? super T>> extends LovyNode<T> {
        private int height=0;
        public LovyAVLNode(){}
        public LovyAVLNode(T val){
            super(val);
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public static <AnyType extends Comparable<? super AnyType>> int height(LovyAVLNode<AnyType> node){
            return node==null? -1:node.getHeight();
        }
    }

    public static <AnyType extends Comparable<? super AnyType>> LovyAVL<AnyType> createLovyAVL(AnyType ... vals){
        if(vals==null||vals.length==0)
            return null;

        LovyAVL<AnyType> lovyAVL=new LovyAVL<AnyType>();
        for(int i=0;i<vals.length;i++){
            lovyAVL.root=lovyAVL.insert((LovyAVLNode<AnyType>)lovyAVL.root,vals[i]);
        }
        return lovyAVL;
    }



    public T findMin(LovyAVLNode<T> root){
        if(root==null)
            return null;
        while(root.left!=null)
            root= (LovyAVLNode<T>) root.left;
        return root.val;
    }

    public T findMax(LovyAVLNode<T> root){
        if(root==null)
            return null;
        while(root.right!=null)
            root= (LovyAVLNode<T>) root.right;
        return root.val;
    }
    private LovyAVLNode<T> remove(LovyAVLNode<T> root,T val){
        if(root==null)
            return root;
        int compare=val.compareTo(root.val);
        if(compare<0)
            root.setLeft(remove((LovyAVLNode<T>)root.left,val));
        else if(compare>0)
            root.setRight(remove((LovyAVLNode<T>)root.right,val));
        else{
            if(root.left!=null&& root.right!=null){
                root.setVal(findMax(root));
                root.right=remove(root.right,root.val);
            }
            else
                root= (LovyAVLNode<T>) ((root.left!=null)?root.left:root.right);
        }

        return balance(root);
    }

    public void remove(T val){
        this.root=remove((LovyAVLNode<T>)this.root,val);
    }


    public static void test(){
        long timeRecorderA=0;
        long timeRecorderB=0;
        Random random=new Random();
        for(int i=0;i<10;i++){
            Integer[] data=new Integer[100000];
            for(int k=0;k<data.length;k++)
                data[k]=random.nextInt();
            long startTime=System.currentTimeMillis();
            createLovyAVL(data);
            long endTime=System.currentTimeMillis();
            System.out.println("At "+i+" times costed "+(endTime-startTime)+" milliseconds...");
            timeRecorderA+=endTime-startTime;

            startTime=System.currentTimeMillis();
            createLovyTreeInLevel(data);
            endTime=System.currentTimeMillis();
            System.out.println("At "+i+" times costed "+(endTime-startTime)+" milliseconds...");
            timeRecorderB+=endTime-startTime;
        }

        System.out.println("average time "+(timeRecorderA/10));
        System.out.println("average time "+(timeRecorderB/10));
    }


}
