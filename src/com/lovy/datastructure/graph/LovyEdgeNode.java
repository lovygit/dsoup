package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyEdgeNode<T extends Comparable<? super T>> {
    private int dest;
    private T cost;
    private LovyEdgeNode<T> link;

    public LovyEdgeNode(){}
    public LovyEdgeNode(int dest,T cost,LovyEdgeNode<T> link){
        this.dest=dest;
        this.cost=cost;
        this.link=link;
    }

    public LovyEdgeNode(int dest){
        this.dest=dest;
    }

    public LovyEdgeNode(int dest,T cost){
        this(dest,cost,null);
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public T getCost() {
        return cost;
    }

    public void setCost(T cost) {
        this.cost = cost;
    }

    public LovyEdgeNode<T> getLink() {
        return link;
    }

    public void setLink(LovyEdgeNode<T> link) {
        this.link = link;
    }
}
