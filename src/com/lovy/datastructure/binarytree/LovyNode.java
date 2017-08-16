package com.lovy.datastructure.binarytree;

/**
 * Created by asus on 2017/4/8.
 */
public class LovyNode<T>{
    protected T val;
    protected LovyNode<T> left,right;

    public LovyNode(){}
    public LovyNode(T val){
        this.val=val;
    }
    public LovyNode(T val,LovyNode<T> left,LovyNode<T> right){
        this.val=val;
        this.left=left;
        this.right=right;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public LovyNode<T> getLeft() {
        return left;
    }

    public void setLeft(LovyNode<T> left) {
        this.left = left;
    }

    public LovyNode<T> getRight() {
        return right;
    }

    public void setRight(LovyNode<T> right) {
        this.right = right;
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null)
            return this==null;

        if(!(obj instanceof LovyNode))
            return false;

        LovyNode<T> another=(LovyNode<T>) obj;

        return another.val.equals(this.val);//&&another.left==this.left&&another.right==this.right;
    }
}