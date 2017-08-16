package com.lovy.datastructure.binarytree;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyThreadNode<T> extends LovyNode<T> {

    protected int ltag,rtag;

    public LovyThreadNode(){}

    public LovyThreadNode(T val){
        super(val);
    }

    public LovyThreadNode(T val,int ltag,int rtag,LovyThreadNode<T> leftChild,LovyThreadNode<T> rightChild){
        super(val,leftChild,rightChild);
        this.ltag=ltag;
        this.rtag=rtag;
    }

    public int getLtag() {
        return ltag;
    }

    public void setLtag(int ltag) {
        this.ltag = ltag;
    }

    public int getRtag() {
        return rtag;
    }

    public void setRtag(int rtag) {
        this.rtag = rtag;
    }
}
