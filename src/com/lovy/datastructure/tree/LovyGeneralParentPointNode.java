package com.lovy.datastructure.tree;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyGeneralParentPointNode<T> {
    private T val;
    private int parentIndex=-1;

    public LovyGeneralParentPointNode(){}

    public LovyGeneralParentPointNode(T val){
        this.val=val;
    }

    public LovyGeneralParentPointNode(T val,int parentIndex){
        this.val=val;
        this.parentIndex=parentIndex;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }
}
