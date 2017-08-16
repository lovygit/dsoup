package com.lovy.datastructure.binarytree;

/**
 * Created by asus on 2017/6/9.
 */
public class LovyRBNode<T extends Comparable<? super T> > extends LovyNode<T> {
    public enum Color{
        RED,BLACK
    }

    private Color color=Color.RED;
    private LovyRBNode<T> parent=null;

    public LovyRBNode(){}
    public LovyRBNode(T key){
        this.val=key;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public LovyRBNode<T> getParent() {
        return parent;
    }

    public void setParent(LovyRBNode<T> parent) {
        this.parent = parent;
    }

    public static <T extends Comparable<? super T>> Color colorOf(LovyRBNode<T> node){
        return node==null? Color.BLACK:node.color;
    }

    public static <T extends Comparable<? super T>> void setColor(LovyRBNode<T> node,Color color){
        if(node!=null)
            node.color=color;
    }

    public static <T extends Comparable<? super T>> LovyRBNode<T> parentOf(LovyRBNode<T> node){
        return node==null? null:node.parent;
    }

    public static <T extends Comparable<? super T>> LovyRBNode<T> leftOf(LovyRBNode<T> node){
        return node==null? null: (LovyRBNode<T>) node.left;
    }

    public static <T extends Comparable<? super T>> LovyRBNode<T> rightOf(LovyRBNode<T> node){
        return node==null? null: (LovyRBNode<T>) node.right;
    }
}
