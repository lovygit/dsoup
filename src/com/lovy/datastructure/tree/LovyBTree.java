package com.lovy.datastructure.tree;

import java.util.*;

/**
 * Created by asus on 2017/4/26.
 */
public class LovyBTree<K extends Comparable<? super K>> {
    private int order=3;
    private LovyBTreeNode<K> root=new LovyBTreeNode<>();

    public LovyBTree(int order){
        if(order<3)
            order=3;
        this.order=order;
    }

    public int getOrder() {
        return order;
    }

    public LovyBTreeNode<K> getRoot() {
        return root;
    }

    //upper int m/2
    private int upperInt(int m){
        return (m+1)/2;
    }
    
    public boolean exist(K key){
        Triple<K> triple=search(key);
        return triple.tag;
    }

    private Triple<K> search(K key){
        LovyBTreeNode<K> p=this.root;
        while(true){
            int i;
            boolean stop=false;
            for(i=1;i<p.getKeys().size();i++){//there,we start to iterate the keys from index 1
                if(p.getKeys().get(i).compareTo(key)>=0){
                    stop=true;
                    break;
                }
            }
            if(stop){
                if(p.getKeys().get(i).compareTo(key)==0)
                    return new Triple<>(p,i,true);
                else if(p.getChildren().get(i-1)!=null)
                    p=p.getChildren().get(i-1);//index i-1 indicates the next node
                else
                    return new Triple<>(p,i-1,false);
            }
            else if(p.getChildren().size()>0&&p.getChildren().get(p.getKeys().size()-1)!=null)
                p=p.getChildren().get(p.getKeys().size()-1);
            else
                return new Triple<>(p,i,false);
        }
    }
    
    private void insert(LovyBTreeNode<K> node ,K key,LovyBTreeNode<K> smaller,LovyBTreeNode<K> bigger){

        int k=1;
        for(;k<node.getKeys().size();k++)
            if(node.getKeys().get(k).compareTo(key)>0)
                break;
        node.getKeys().add(k,key);

        if(bigger==null){
            node.getChildren().add(null);
            if(node.getKeys().size()==2){//there is only one key in this node
                node.getChildren().add(null);
            }
        }else{
            node.getChildren().set(k-1,smaller);
            node.getChildren().add(k,bigger);

            smaller.setParent(node);
            bigger.setParent(node);
        }

        if(node.getKeys().size()==order+1){//the key's num == order

            LovyBTreeNode<K> smaller_=new LovyBTreeNode<>();
            for(int i=1;i<upperInt(order);i++){//start from 1 [k1 - k[m/2-1]-1] 
                smaller_.getKeys().add(node.getKeys().get(i));
            }
            for(int i=0;i<upperInt(order);i++){//start from 0 [p0 - p[m/2-1]]向上取整
                smaller_.getChildren().add(node.getChildren().get(i));
                if(smaller_.getChildren().get(smaller_.getChildren().size()-1)!=null)
                    smaller_.getChildren().get(smaller_.getChildren().size()-1).setParent(smaller_);
            }
            
            LovyBTreeNode<K> bigger_=new LovyBTreeNode<K>();
            for(int i=1+upperInt(order);i<node.getKeys().size();i++)//start from [order/2]+1
                bigger_.getKeys().add(node.getKeys().get(i));
            for(int i=upperInt(order);i<node.getChildren().size();i++){
                bigger_.getChildren().add(node.getChildren().get(i));
                if(bigger_.getChildren().get(bigger_.getChildren().size()-1)!=null)
                    bigger_.getChildren().get(bigger_.getChildren().size()-1).setParent(bigger_);
            }


            LovyBTreeNode<K> parent=node.getParent();

            if(parent==null){
                parent=new LovyBTreeNode<K>();
                parent.getKeys().add(node.getKeys().get(upperInt(order)));

                smaller_.setParent(parent);
                bigger_.setParent(parent);

                parent.getChildren().add(smaller_);
                parent.getChildren().add(bigger_);

                root=parent;
            }
            else{
                K mid=node.getKeys().get(upperInt(order));
                insert(parent,mid,smaller_,bigger_);
            }
        }
    }

    public boolean insert(K key){
        Triple<K> triple=search(key);
        if(triple.tag)
            return false;
        insert(triple.getNode(),key,null,null);
        return true;
    }

    public void print(){
        List<LovyBTreeNode<K>> queue=new LinkedList<>();
        queue.add(root);
        LovyBTreeNode<K> curr;
        int level=0;
        int count=0;
        while(queue.size()>0){
            int currSize=queue.size();
            for(int i=0;i<level;i++)
                System.out.print("\t");
            level++;

            while(currSize>0){
                curr=queue.remove(0);
                if(curr.getKeys().size()!=curr.getChildren().size()){
                    System.out.println("Warning...");
                }
                count++;
                for(int i=1;i<curr.getKeys().size();i++)
                    System.out.print(curr.getKeys().get(i)+" ");
                if(currSize>1)
                    System.out.print(",");
                for(int i=0;i<curr.getChildren().size();i++)
                    if(curr.getChildren().get(i)!=null)
                        queue.add(curr.getChildren().get(i));

                currSize--;
            }
            System.out.println();
        }
        System.out.println("-------------"+count+"-------------");
    }


    public boolean remove(K key){
        Triple<K> triple=search(key);
        if(!triple.tag)
            return false;
        int pos=triple.index;
        LovyBTreeNode<K> node=triple.getNode();

        if(node.getChildren().size()>0&&node.getChildren().get(pos)!=null){//找到key的后继
            LovyBTreeNode<K> p=node.getChildren().get(pos);
            while(p.getChildren().size()>0&&p.getChildren().get(0)!=null)
                p=p.getChildren().get(0);

//            System.out.println("p "+p.getKeys());
            node.getKeys().set(pos,p.getKeys().get(1));

            node=p;
            pos=1;
        }

        node.getKeys().remove(pos);
        node.getChildren().remove(pos);

        solveUnderFlow(node);
        return true;
    }


    private void solveUnderFlow(LovyBTreeNode<K> node){
        if(node.getChildren().size()>=upperInt(order))
            return;

        LovyBTreeNode<K> parent=node.getParent();

        if(parent==null){
            if(node.getKeys().size()==1&&node.getChildren().size()>0&&node.getChildren().get(0)!=null){
//                System.out.println("Renew root...");
                this.root=node.getChildren().remove(0);
                root.setParent(null);
            }
            return;
        }

        int pos=0;
        while(parent.getChildren().get(pos)!=node)
            pos++;

        //有左兄弟，并且左兄弟足够胖
        //把父节点的位于pos的key放置在node的keys首位置，然后用左兄弟最右边的键填充parent.keys[pos]
        //ls.child[ls.child.size-1]-> node.child[0]
        //if node.child[0]-> node.child[0].setParent
        if(pos>0){
            LovyBTreeNode<K> leftSibling=parent.getChildren().get(pos-1);

            if(leftSibling.getChildren().size()>upperInt(order)){
//                System.out.println("condition 1...");

                node.getKeys().add(1,parent.getKeys().get(pos));
                parent.getKeys().set(pos,leftSibling.getKeys().remove(leftSibling.getKeys().size()-1));
                node.getChildren().add(0,leftSibling.getChildren().remove(leftSibling.getChildren().size()-1));
                if(node.getChildren().get(0)!=null)
                    node.getChildren().get(0).setParent(node);
                return;
            }
        }
        //有右兄弟，并且右兄弟足够胖
        else{
            LovyBTreeNode<K> rightSibling=parent.getChildren().get(pos+1);

            if(rightSibling.getChildren().size()>upperInt(order)){
//                System.out.println("Condition 2...");

                node.getKeys().add(parent.getKeys().get(pos+1));
                parent.getKeys().set(pos+1,rightSibling.getKeys().remove(1));
                node.getChildren().add(rightSibling.getChildren().remove(0));

                if(node.getChildren().get(node.getChildren().size()-1)!=null)
                    node.getChildren().get(node.getChildren().size()-1).setParent(node);
                return;
            }
        }

        //合并node与左节点
        //rs.getKeys().add(1,parent.getKeys().remove(pos))
        //rs.getChildren().addAll(0,node.getChildren()) if necessary update the parent of these children
        //rs.getKeys().addAll(0,node.getKeys() except 0)
        if(pos>0){
//            System.out.println("Condition 3...");

            LovyBTreeNode<K> leftSibling=parent.getChildren().get(pos-1);
            leftSibling.getKeys().add(parent.getKeys().remove(pos));
            parent.getChildren().remove(pos);//父节点中删除node

            leftSibling.getChildren().add(node.getChildren().remove(0));
            if(leftSibling.getChildren().get(leftSibling.getChildren().size()-1)!=null)
                leftSibling.getChildren().get(leftSibling.getChildren().size()-1).setParent(leftSibling);

            while(node.getKeys().size()>1){
                leftSibling.getKeys().add(node.getKeys().remove(1));
                leftSibling.getChildren().add(node.getChildren().remove(0));

                if(leftSibling.getChildren().get(leftSibling.getChildren().size()-1)!=null)
                    leftSibling.getChildren().get(leftSibling.getChildren().size()-1).setParent(leftSibling);
            }
        }else{//合并node与右节点
//            System.out.println("Condition 4...");

            LovyBTreeNode<K> rightSibling=parent.getChildren().get(pos+1);
            rightSibling.getKeys().add(1,parent.getKeys().remove(pos+1));
            parent.getChildren().remove(pos);

            rightSibling.getChildren().add(0,node.getChildren().remove(node.getChildren().size()-1));
            if(rightSibling.getChildren().get(0)!=null)
                rightSibling.getChildren().get(0).setParent(rightSibling);

            while(node.getKeys().size()>1){
                rightSibling.getKeys().add(1,node.getKeys().remove(node.getKeys().size()-1));
                rightSibling.getChildren().add(0,node.getChildren().remove(node.getChildren().size()-1));

                if(rightSibling.getChildren().get(0)!=null)
                    rightSibling.getChildren().get(0).setParent(rightSibling);
            }
        }

        solveUnderFlow(parent);
    }


    public static void test(){
        LovyBTree rbTree=new LovyBTree(10);

        Set<Integer> set=new HashSet<>();
        Random random=new Random();
        int size=1000000;

        while(set.size()<size)
            set.add(Math.abs(random.nextInt()));

        long startTime=System.currentTimeMillis();
        for(int val:set)
            rbTree.insert(val);
        long endTime=System.currentTimeMillis();

        System.out.println("Insertion costed "+(endTime-startTime)+" milliseconds...");

        startTime=endTime;
        for(int val:set){
            rbTree.remove(val);
        }
        endTime=System.currentTimeMillis();
        System.out.println("Removing costed "+(endTime-startTime)+" milliseconds...");
    }


    public static void testInsert(){
        LovyBTree<Integer> bTree=new LovyBTree<>(50);
        Random random=new Random();
        int len=1000000;
        Set<Integer> set=new HashSet<>();
        for(int i=0;i<len;i++)
            set.add(Math.abs(random.nextInt(10000000)));

//        System.out.println(set);
        for(int val:set){
            bTree.insert(val);
        }

//        bTree.print();

        long start=System.currentTimeMillis();
        for(int val:set){
            bTree.exist(val);
        }
        long end=System.currentTimeMillis();
        System.out.println("costed "+(end-start)+" milliseconds...");
        System.out.println("avarage time "+((end-start)*1.0/set.size()));
    }

    public static void testRemove(int order){
        LovyBTree<Integer> bTree=new LovyBTree<>(order);
        bTree.insert(50);
        bTree.insert(30);
        bTree.insert(60);
        bTree.insert(75);
        bTree.insert(10);
        bTree.insert(40);
        bTree.insert(58);
        bTree.insert(70);
        bTree.insert(80);

        bTree.print();
        bTree.remove(50);
        bTree.print();
        bTree.remove(30);
        bTree.print();
        bTree.remove(60);
        bTree.print();
        bTree.remove(75);
        bTree.print();
        bTree.remove(10);
        bTree.print();
        bTree.remove(40);
        bTree.print();
        bTree.remove(58);
        bTree.print();
        bTree.remove(70);
        bTree.print();
    }

    private static class Triple<K extends Comparable<? super K>> {
        private LovyBTreeNode<K> node;
        private int index;
        private boolean tag;

        public Triple(LovyBTreeNode<K> node,int index,boolean tag){
            this.node=node;
            this.index=index;
            this.tag=tag;
        }

        public LovyBTreeNode<K> getNode() {
            return node;
        }

        public void setnode(LovyBTreeNode<K> node) {
            this.node = node;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isTag() {
            return tag;
        }

        public void setTag(boolean tag) {
            this.tag = tag;
        }
    }
}
