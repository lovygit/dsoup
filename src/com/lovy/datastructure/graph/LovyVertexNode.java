package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyVertexNode<T extends Comparable<? super T>,V> {

    private V data;
    private LovyEdgeNode<T> first;

    public LovyVertexNode(){}

    public LovyVertexNode(V data){
        this.data=data;
    }

    public LovyVertexNode(V data,LovyEdgeNode<T> first){
        this.data=data;
        this.first=first;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public LovyEdgeNode<T> getFirst() {
        return first;
    }

    public void setFirst(LovyEdgeNode<T> first) {
        this.first = first;
    }
}
