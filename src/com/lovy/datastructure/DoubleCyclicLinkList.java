package com.lovy.datastructure;

import java.util.Random;

/**
 * Created by asus on 2017/3/19.
 */
public class DoubleCyclicLinkList<T extends Comparable<T>> {


    public static class Node<T extends Comparable<T>>{
        private T value=null;
        private int frequency=0;
        private Node<T> leftLink=null;
        private Node<T> rightLink=null;

        public Node(){

        }
        public Node(T value){
            this.value=value;
        }
        public Node(T value,Node<T> leftLink,Node<T> rightLink){
            this.value=value;
            this.leftLink=leftLink;
            this.rightLink=rightLink;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getLeftLink() {
            return leftLink;
        }

        public void setLeftLink(Node<T> leftLink) {
            this.leftLink = leftLink;
        }

        public Node<T> getRightLink() {
            return rightLink;
        }

        public void setRightLink(Node<T> rightLink) {
            this.rightLink = rightLink;
        }
    }

    private Node<T> header=null;

    public DoubleCyclicLinkList(){
        header=new Node(null);
        header.setLeftLink(header);
        header.setRightLink(header);
    }

    public Node<T> locateNode(int i,boolean direction){//i begins at 1
//        System.out.println("locateNode method with args-- i= "+i);
        if(i<1){
//            System.err.println("The i is illegal...");
            return null;
        }

        Node<T> p=(direction)?header.rightLink:header.leftLink;
        int k=1;
        while(k<i){
            if(p==header)
                break;
            p=(direction)?p.rightLink:p.leftLink;
            k++;
        }
        return (i==k)?p:null;
    }


    public boolean insert(T value,int i,boolean direction){
        if(i<1){
            System.err.println("The i is illegal...");
            return false;
        }

        Node<T> located=locateNode(i,direction);
        if(located==null){
            System.err.println("The i is illegal...");
            return false;
        }

        Node<T> newNode=new Node<T>(value);

        if(direction){
            newNode.rightLink=located.rightLink;
            located.rightLink=newNode;
            newNode.rightLink.leftLink=newNode;
            newNode.leftLink=located;
        }else{
            newNode.leftLink=located.leftLink;
            located.leftLink=newNode;

            newNode.leftLink.rightLink=newNode;
            newNode.rightLink=located;
        }
        return true;
    }

    public static <T extends Comparable<T>> DoubleCyclicLinkList<T> createDoubleCyclicLinkList(boolean direction,T... values){
        DoubleCyclicLinkList<T> list=new DoubleCyclicLinkList<T>();
        if(values.length==0){
            return list;
        }else{
                Node<T> newNode=new Node<T>(values[0]);
                list.header.rightLink=list.header.leftLink=newNode;
                newNode.leftLink=newNode.rightLink=list.header;//prepare to insert other values.
        }
        if(direction){
            for(int i=values.length-1;i>=1;i--){
                list.insert(values[i],1,direction);
            }
        }else{
            for(int i=1;i<values.length-1;i++){
                list.insert(values[i],1,direction);
            }
        }
        return list;
    }


    public void print(){
        print(true);
    }

    public void print(boolean direction){
        Node<T> p=(direction)?header.rightLink:header.leftLink;
        while(p!=header){
            System.out.print(p.value+" ");
            p=(direction)?p.rightLink:p.leftLink;
        }
        System.out.println();
    }

    public void printWithFrequency(boolean direction){
        Node<T> p=(direction)?header.rightLink:header.leftLink;
        while(p!=header){
            System.out.print("("+p.value+","+p.frequency+") ");
            p=(direction)?p.rightLink:p.leftLink;
        }
        System.out.println();
    }

    public void printWithFrequency(){
        printWithFrequency(true);
    }

    public boolean remove(int i,boolean direction){
        Node<T> located=locateNode(i,direction);
        if(located==null||located==header){
            System.err.println("The i is illegal...");
            return false;
        }
        Node<T> preNode=locateNode(i-1,direction);
        if(preNode==null){
            preNode=header;
        }
        preNode.rightLink=located.rightLink;
        located.rightLink.leftLink=preNode;
        return true;
    }

    public void reverse() {
        Node<T> mover=header;
        if(mover==null){
            return;
        }
        Node<T> quest;
        do{
            quest=mover.rightLink;
            mover.rightLink=mover.leftLink;
            mover.leftLink=quest;
            mover=quest;
        }while(mover!=header);
    }


    public void modifyOddEven(){
        Node<T> quest=header.rightLink;
        Node<T> preQuest=header;
        if(quest==header){
            return;
        }
        while(quest!=header){
            preQuest=quest;
            System.out.println("quest'val is "+quest.value);
            if(quest.rightLink==header){
                break;
            }
            quest.rightLink=quest.rightLink.rightLink;
            quest=quest.rightLink;
        }


//        System.out.println("quest's val is "+quest.value+", preQuest's val is "+preQuest.value);
        if(quest==header){
            System.out.println("Condition one...");
            quest=header.leftLink;
        }else{
            System.out.println("Condition two...");
            quest=header.leftLink.leftLink;
        }
//        System.out.println("quest'val is "+quest.value);
        while(quest!=header){
            preQuest.rightLink=quest;
            preQuest=quest;
//            if(quest.leftLink==header){ in this precedure, we can sure it's impossible to happen...
//                break;
//            }
            quest=quest.leftLink.leftLink;
        }
        preQuest.rightLink=header;

        preQuest=header;
        do{
            quest=preQuest.rightLink;
            quest.leftLink=preQuest;
            preQuest=quest;
        }while(preQuest!=header);
    }


    public Node<T> locateWithFrequency(int i){

        Node<T> p=locateNode(i,true);

        Node<T> quest=p;
        if(p!=null){
            p.frequency+=1;
//            direction=!direction;
//            if(direction){
//                while(quest!=header&&quest.rightLink!=header&&quest.frequency>quest.rightLink.frequency){
//                    T val=quest.value;
//                    int freq=quest.frequency;
//
//                    quest.value=quest.rightLink.value;
//                    quest.frequency=quest.rightLink.frequency;
//
//                    quest.rightLink.value=val;
//                    quest.rightLink.frequency=freq;
//
//                    quest=quest.rightLink;
//                }
//            }else{
            System.out.println("go left...");
            while(quest!=header&&quest.leftLink!=header&&quest.frequency>quest.leftLink.frequency){
                T val=quest.value;
                int freq=quest.frequency;

                quest.value=quest.leftLink.value;
                quest.frequency=quest.leftLink.frequency;

                quest.leftLink.value=val;
                quest.leftLink.frequency=freq;

                quest=quest.leftLink;
            }
//            }
        }
        return quest;
    }



    public static void test(){

        DoubleCyclicLinkList linkList=createDoubleCyclicLinkList(true,new Integer[]{3,4,5,6,7,8,9,10,11,12});
        linkList.print();

    }
}
