package com.lovy.datastructure.tree;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyGeneralNode<T> {
    private T val;
    private LovyGeneralNode<T> left;
    private LovyGeneralNode<T> nextSibling;

    public LovyGeneralNode(){}

    public LovyGeneralNode(T val){
        this.val=val;
    }

    public LovyGeneralNode(T val,LovyGeneralNode<T> left,LovyGeneralNode<T> nextSibling){
        this.val=val;
        this.left=left;
        this.nextSibling=nextSibling;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public LovyGeneralNode<T> getLeft() {
        return left;
    }

    public void setLeft(LovyGeneralNode<T> left) {
        this.left = left;
    }

    public LovyGeneralNode<T> getNextSibling() {
        return nextSibling;
    }

    public void setNextSibling(LovyGeneralNode<T> nextSibling) {
        this.nextSibling = nextSibling;
    }






}
