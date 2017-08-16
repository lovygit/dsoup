package com.lovy.datastructure;

/**
 * Created by asus on 2017/3/18.
 */

import java.util.Comparator;
import java.util.Random;

/**
 * this class is a customized list that does not contains the header node
 *
 * @param <T>
 */
public class LovyLinkList<T extends Comparable<T>> {

    private LovyLinkListNode<T> first = null;


    public LovyLinkList() {

    }

    public LovyLinkList(LovyLinkListNode first) {
        this.first = first;
    }

    public void setFirst(LovyLinkListNode<T> first) {
        this.first = first;
    }

    public LovyLinkListNode<T> getFirst() {
        return first;
    }

    public boolean insert(T value, int index) {
        if (index < 0) {
            System.err.println("The i is illegal...");
            return false;
        } else if (index == 0) {
            if (first == null) {
                first = new LovyLinkListNode<T>(value, null);
            } else {
                LovyLinkListNode<T> newNode = new LovyLinkListNode<T>(value, first);
                first = newNode;
            }
        } else {
            int k = 0;
            LovyLinkListNode<T> mover = first;
            while (mover.getLink() != null && k < index - 1) {
                mover = mover.link;
                k++;
            }

            if (mover.getLink() != null || (mover.getLink() == null && k == index - 1)) {
                LovyLinkListNode<T> newNode = new LovyLinkListNode<T>(value, null);
                newNode.link = mover.link;
                mover.link = newNode;
            } else {
                System.err.println("The i is too big...");
                return false;
            }
        }
        return true;
    }

    public static void printLovyLinkList(LovyLinkList lovyLinkList) {
        LovyLinkListNode first = lovyLinkList.first;
        while (first != null) {
            System.out.print(first.value + " ");
            first = first.link;
        }
        System.out.println();
    }

    public static <T extends Comparable<T>> LovyLinkList<T> createLovyLinkList(T... datas) {
        LovyLinkList<T> list = new LovyLinkList<>();
        for (int i = 0; i < datas.length; i++) {
            list.insert(datas[i], i);
        }
        return list;
    }


    public static int getLovyLinkListLength(LovyLinkList lovyLinkList) {
        LovyLinkListNode first = lovyLinkList.first;
        int count = 0;
        while (first != null) {
            count++;
            first = first.link;
        }
        return count;
    }

    public boolean remove(int index) {
        if (index < 0) {
            System.err.println("The index is illegal...");
            return true;
        } else if (index == 0) {
            if (first == null) {
                System.err.println("The list is empty...");
                return false;
            } else {
                first = first.link;
            }
        } else {
            int k = 0;
            LovyLinkListNode mover = first;
            while (mover.link != null && k < index - 1) {
                mover = mover.link;
                k++;
            }

            if (mover.link == null) {
                System.err.println("The index does not exist...");
                return false;
            } else {
                mover.link = mover.link.link;
            }
        }
        return true;
    }

    public void reverse() {
        LovyLinkListNode p = first;
        if (p == null || p.link == null) {
            return;
        } else {
            LovyLinkListNode pNext = p.link;
            first.link = null;//it's important to change...
            LovyLinkListNode nextNext = pNext.link;

            while (pNext != null) {
                nextNext = pNext.link;
                pNext.link = p;
                p = pNext;
                pNext = nextNext;
            }
            first = p;//p points the last node;
        }
    }

    /**
     * this method can only be used when listA is sorted before and listB is sorted Before ,and both lists are
     * [non-decreasing] lists,the result list is [non-increasing] list,
     * and must note : listB.first==null after calling this method
     *
     * @param listA
     * @param listB
     * @param <T>
     */
    public static <T extends Comparable<T>> void unionTwoList(LovyLinkList<T> listA, LovyLinkList<T> listB) {
        LovyLinkListNode firstA = listA.first;
        LovyLinkListNode firstB = listB.first;

        listB.first = null;//listB.first has no significance...
        LovyLinkListNode resultListHeader = null;
        LovyLinkListNode p = null;

        while (firstA != null && firstB != null) {
            if (firstA.value.compareTo(firstB.value) <= 0) {
                p = firstA;
                firstA = firstA.link;
            } else {
                p = firstB;
                firstB = firstB.link;
            }
            p.link = resultListHeader;
            resultListHeader = p;
//            System.out.println("resultListHeader.value is "+resultListHeader.value);
        }

        if (firstB != null) {
            firstA = firstB;
        }
        while (firstA != null) {
            p = firstA;
            firstA = firstA.link;
            p.link = resultListHeader;
            resultListHeader = p;
//            System.out.println("resultListHeader.value is "+resultListHeader.value);
        }

        listA.first = resultListHeader;
    }


    public void removeByValueSpan(T min,T max){
        LovyLinkListNode p=first;
        LovyLinkListNode q=first;//q is a test pointer;
        while(q!=null){
            if(q.value.compareTo(min)>0&&q.value.compareTo(max)<0){
                if(q==first){
                    first=first.link;
                    p=q=first;
                    continue;
                }else{
                    p.link=q.link;//delete q
                }
            }else{
                p=q;//move p
            }
            q=q.link;//move q
        }
    }

    public LovyLinkList[] getEvenAndOddList(){
        if(first.value instanceof Integer){
            LovyLinkListNode<T> p=first;
            LovyLinkList[] lists=new LovyLinkList[]{new LovyLinkList(),new LovyLinkList() };//evenList is lists[0]
            LovyLinkListNode evenMover=null,oddMover=null;
            while(p!=null){
                if(((Integer)p.value)%2==0){
                    if(lists[0].first==null){
                        lists[0].first=new LovyLinkListNode<T>(p.value,null);
                        evenMover=lists[0].first;
                    }else{
                        evenMover.link=new LovyLinkListNode(p.value,null);
                        evenMover=evenMover.link;
                    }
                }else{
                    if(lists[1].first==null){
                        lists[1].first=new LovyLinkListNode(p.value,null);
                        oddMover=lists[1].first;
                    }else{
                        oddMover.link=new LovyLinkListNode(p.value,null);
                        oddMover=oddMover.link;
                    }
                }

                p=p.link;
            }
            return lists;
        }
        return null;
    }

    public static class LovyLinkListNode<T extends Comparable<T>> {
        private T value;
        private LovyLinkListNode link = null;

        public LovyLinkListNode(T value, LovyLinkListNode<T> link) {
            this.value = value;
            this.link = link;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public LovyLinkListNode getLink() {
            return link;
        }

        public void setLink(LovyLinkListNode link) {
            this.link = link;
        }
    }


    public static void test() {
        LovyLinkList list=createLovyLinkList(new Integer[]{3,4,5,6,7,8,9});
    }
    private static void testMethodA() {
        LovyLinkList linkList = createLovyLinkList(new Integer[]{1, 2, 3, 4, 5});
        System.out.println("linkList :");
        printLovyLinkList(linkList);

        linkList.remove(1);
        System.out.println("After removing,linkList is :");
        printLovyLinkList(linkList);


        LovyLinkList linkListB = createLovyLinkList(new Integer[]{3, 4, 6, 7, 8, 9, 10});
        System.out.println("linkListB :");
        printLovyLinkList(linkListB);

        unionTwoList(linkList, linkListB);
        System.out.println("After union...,linkList is :");
        printLovyLinkList(linkList);

        System.out.println("After reversing,linkList is :");
        linkList.reverse();
        printLovyLinkList(linkList);
    }
}
