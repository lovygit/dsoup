package com.lovy.datastructure.graph.minimumtree;

/**
 * minimum-cost spanning tree
 * Created by asus on 2017/4/13.
 */
public class MSTEdgeNode<T extends Comparable<? super T>> implements Comparable<MSTEdgeNode<T>>{
    private int origin;
    private int end;
    private T cost;

    public MSTEdgeNode(){}
    public MSTEdgeNode(int origin, int end, T cost){
        this.origin=origin;
        this.end=end;
        this.cost=cost;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public T getCost() {
        return cost;
    }

    public void setCost(T cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null)
            return false;
        if(!(obj instanceof MSTEdgeNode))
            return false;

        MSTEdgeNode<T> another=(MSTEdgeNode<T>) obj;

        return origin==another.origin&&end==another.end;
    }


    @Override
    public int compareTo(MSTEdgeNode<T> o) {
        return this.cost.compareTo(o.cost);
    }

    @Override
    public String toString(){
        return "<"+origin+","+end+">("+cost+")";
    }
}
