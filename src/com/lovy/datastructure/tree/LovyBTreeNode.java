package com.lovy.datastructure.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2017/4/26.
 */
public class LovyBTreeNode<K extends Comparable<? super K>> {
    private LovyBTreeNode<K> parent;
    private List<K> keys=new LinkedList<>();
    private List<LovyBTreeNode<K>> children=new LinkedList<>();

    public LovyBTreeNode(){
        keys.add(null);//we don't use the first pos
    }

    public List<K> getKeys() {
        return keys;
    }

    public void setKeys(List<K> keys) {
        this.keys = keys;
    }

    public void setParent(LovyBTreeNode<K> parent) {
        this.parent = parent;
    }

    public LovyBTreeNode<K> getParent() {
        return parent;
    }

    public List<LovyBTreeNode<K>> getChildren() {
        return children;
    }

    public void setChildren(List<LovyBTreeNode<K>> children) {
        this.children = children;
    }
}
