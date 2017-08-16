package com.lovy.datastructure.binarytree;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * Created by asus on 2017/4/8.
 */
public class LovyTree<T> {

    protected LovyNode<T> root = null;

    public LovyTree() {
    }

    public LovyTree(LovyNode<T> root) {
        this.root = root;
    }

    public LovyNode<T> getRoot() {
        return root;
    }

    public void setRoot(LovyNode<T> root) {
        this.root = root;
    }

    private static <T> void print(LovyNode<T> root, int depth) {
        if (root != null) {
            for (int i = 0; i < depth; i++)
                System.out.print("  ");
            System.out.println(root.getVal());

            if (root.getLeft() != null)
                print(root.getLeft(), depth + 1);
            else if (root.getRight() != null) {
                for (int i = 0; i < depth + 1; i++)
                    System.out.print("  ");
                System.out.println("--");
            }

            if (root.getRight() != null)
                print(root.getRight(), depth + 1);
            else if (root.getLeft() != null) {
                for (int i = 0; i < depth + 1; i++)
                    System.out.print("  ");
                System.out.println("--");
            }
        }
    }

    public void print() {
        print(root, 0);
    }


    public LovyNode<T> getLeftest(LovyNode<T> node) {
        LovyNode<T> q = node;
        if (q == null)
            return null;

        while (q.getLeft() != null)
            q = q.getLeft();
        return q;
    }


    public LovyNode<T> getLeftest() {
        return getLeftest(this.root);
    }


    public static <T> int getLeftestPathLen(LovyNode<T> node){
        if(node==null)
            return 0;
        return 1+getLeftestPathLen(node.left);
    }

    public int getLefetestPathLen(){
        return getLeftestPathLen(this.root);
    }

    public void removeLeftest() {
        LovyNode<T> q = root;
        if (q == null)
            return;
        if (q.left == null) {
            root = root.right;
            return;
        }
        while (q.left.left != null)
            q = q.left;

        q.left = q.left.right;
    }

    public LovyNode<T> removeLestest(LovyNode<T> node) {
        LovyNode<T> q = node;
        if (q == null)
            return null;
        if (q.left == null)
            return q.right;
        while (q.left.left != null)
            q = q.left;
        q.left = q.left.right;
        return node;
    }


    public LovyNode<T> getRightest(LovyNode<T> node) {
        LovyNode<T> q = node;
        if (q == null)
            return null;
        while (q.getRight() != null)
            q = q.getRight();
        return q;
    }


    public LovyNode<T> getRightest() {
        return getRightest(this.root);
    }


    public static <T> int getRightestPathLen(LovyNode<T> node){
        if(node==null)
            return 0;
        return 1+getRightestPathLen(node.right);
    }

    public int getRightestPathLen(){
        return getRightestPathLen(this.root);
    }




    public void removeRightest() {
        LovyNode<T> q = root;
        if (q == null)
            return;

        if (q.right == null) {
            root = root.left;
            return;
        }

        while (q.right.right != null)
            q = q.right;
        q.right = q.right.left;
    }

    public LovyNode<T> removeRightest(LovyNode<T> node) {
        LovyNode<T> q = node;
        if (q == null)
            return q;
        if (q.right == null)
            return q.left;

        while (q.right.right != null)
            q = q.right;

        q.right = q.right.left;
        return node;
    }


    private int getHeight(LovyNode<T> root) {
        if (root == null)
            return 0;
        return 1 + Math.max(getHeight(root.getLeft()), getHeight(root.getRight()));
    }

    public int height() {
        return getHeight(this.root);
    }


    private int size(LovyNode<T> root) {
        if (root == null)
            return 0;
        return 1 + size(root.getLeft()) + size(root.getRight());
    }


    public int size() {
        return size(this.root);
    }


    private String generateStr(LovyNode<T> root) {
        if (root != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(root.getVal());
            if (root.getLeft() != null || root.getRight() != null) {
                builder.append("(");
                if (root.getLeft() != null)
                    builder.append(generateStr(root.getLeft()));
                builder.append(",");

                if (root.getRight() != null)
                    builder.append(generateStr(root.getRight()));
                builder.append(")");
            }
            return builder.toString();
        }
        return "";
    }


    @Override
    public String toString() {
        return generateStr(this.root);
    }


    private void printPreOrder(LovyNode<T> root) {
        if (root != null) {
            System.out.print(root.getVal() + ",");
            printPreOrder(root.getLeft());
            printPreOrder(root.getRight());
        } else {
            System.out.print("null,");
        }
    }

    public void preOrder() {
        printPreOrder(this.root);
        System.out.println();
    }


    private void printPreOrderIter() {
        if (root == null)
            return;

        List<LovyNode<T>> stack = new ArrayList<>();
        stack.add(root);

        LovyNode<T> currentNode = null;
        do {
            while (currentNode != null) {
                System.out.print(currentNode.val + "->");
                stack.add(currentNode);
                currentNode = currentNode.left;
            }
            if (stack.size() == 0)
                return;

            currentNode = stack.remove(stack.size()).right;

        } while (true);
    }


    private void printInOrder(LovyNode<T> root) {
        if (root != null) {
            printInOrder(root.getLeft());
            System.out.print(root.getVal() + ",");
            printInOrder(root.getRight());
        }
//        else {
//            System.out.print("null,");
//        }
    }

    public void printInorderIter(){
        LovyNode<T> curr=root;
        List<LovyNode<T>> stack=new ArrayList<>();
        while(stack.size()>0||curr!=null){
            while(curr!=null){
                stack.add(curr);
                curr=curr.left;
            }
            if(stack.size()>0){
                curr=stack.remove(stack.size()-1);
                System.out.print(curr.val+"->");
                curr=curr.right;
            }
        }
        System.out.println("STOP");
    }

    public void inOrder() {
        printInOrder(this.root);
        System.out.println();
    }


    private void printPostOrderIter(){
        LovyNode<T> curr=root;
        LovyNode<T> pre=null;
        List<LovyNode<T>> stack=new ArrayList<>();
        while(stack.size()>0||curr!=null){
            while(curr!=null){
                stack.add(curr);
                curr=curr.left;
            }
            if(stack.size()>0){
                curr=stack.remove(stack.size()-1);
                if(curr.right==null||curr.right==pre){
                    System.out.print(curr.val+"->");
                    pre=curr;
                    curr=null;
                }else{
                    stack.add(curr);
                    curr=curr.right;
                }
            }
        }
        System.out.println("STOP");
    }
    private void printPostOrder(LovyNode<T> root) {
        if (root != null) {
            printPostOrder(root.getLeft());
            printPostOrder(root.getRight());
            System.out.print(root.getVal() + ",");
        }
//        else {
//            System.out.print("null,");
//        }
    }

    public void postOrder() {
        printPostOrder(this.root);
        System.out.println();
    }


    private boolean contains(LovyNode<T> root, LovyNode<T> LovyNode) {
        if (LovyNode == null) {
            if (root == null)
                return true;
            if (contains(root.getLeft(), LovyNode))
                return true;
            if (contains(root.getRight(), LovyNode))
                return true;

            return false;
        } else {
            if (root.equals(LovyNode))
                return true;
            if (root.getLeft() != null && contains(root.getLeft(), LovyNode))
                return true;
            if (root.getRight() != null && contains(root.getRight(), LovyNode))
                return true;
            return false;
        }
    }

    public boolean contains(LovyNode<T> LovyNode) {
        return contains(this.root, LovyNode);
    }

    private boolean contains(LovyNode<T> root, T val) {
        if (root != null) {
            if (val == null) {
                if (root.getVal() == null)
                    return true;
            } else {
                if (root.getVal().equals(val))
                    return true;
            }

            if (contains(root.getLeft(), val) || contains(root.getRight(), val))
                return true;
        }
        return false;
    }

    public boolean contains(T val) {
        return contains(this.root, val);
    }


    public static <T> LovyTree<T> createLovyTreeInLevel(T... values) {
        if (values == null || values.length == 0)
            return null;
        List<LovyNode<T>> LovyNodeQueue = new LinkedList<>();
        LovyNode<T> root = new LovyNode(values[0]);
        LovyNodeQueue.add(root);
        int k = 1;
        LovyNode<T> currentLovyNode;
        while (k < values.length) {
            int currentSize = LovyNodeQueue.size();
            while (currentSize > 0) {
                currentLovyNode = LovyNodeQueue.remove(0);
                T val = values[k++];
                if (val != null) {
                    LovyNode<T> leftLovyNode = new LovyNode(val);
                    currentLovyNode.setLeft(leftLovyNode);
                    LovyNodeQueue.add(leftLovyNode);
                }

                if (k < values.length) {
                    val = values[k++];
                    if (val != null) {
                        LovyNode<T> rightLovyNode = new LovyNode(val);
                        currentLovyNode.setRight(rightLovyNode);
                        LovyNodeQueue.add(rightLovyNode);
                    }
                }
                currentSize--;
                if (k >= values.length)
                    break;
            }
        }
        return new LovyTree<T>(root);
    }


    public static LovyTree<Character> createLovyTreeWithGeneralizedSeq(String seq) {
        List<LovyNode<Character>> stack = new LinkedList<>();
        LovyTree<Character> lovyTree = new LovyTree<>();
        LovyNode<Character> p = null;
        int flag = 0;
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case '(':
                    stack.add(p);
                    flag = 1;
                    break;
                case ')':
                    stack.remove(stack.size() - 1);//means that this node's left subtree and right subtree has been modified completely.
                    break;
                case ',':
                    flag = 2;
                    break;
                default:
                    p = new LovyNode<>(seq.charAt(i));
                    if (lovyTree.getRoot() == null)
                        lovyTree.setRoot(p);
                    else {
                        LovyNode<Character> top = stack.get(stack.size() - 1);
                        if (flag == 1)
                            top.left = p;
                        else
                            top.right = p;
                    }
            }
        }
        return lovyTree;
    }



    private LovyNode<T> copy(LovyNode<T> root){
        if(root==null)
            return null;

        LovyNode<T> newNode=new LovyNode<T>(root.val);
        newNode.left=copy(root.left);
        newNode.right=copy(root.right);
        return newNode;
    }


    public LovyTree<T> copy(){
        return new LovyTree<T>(copy(this.root));
    }


//    public void printGraph(){
//        List<LovyNode<T>> queue=new ArrayList<>();
//        if(this.root==null)
//            return;
//        queue.add(this.root);
//
//
//        LovyNode<T> currentNode;
//        int depth=0;
//        int height=getHeight(this.root);
//        while(queue.size()>0){
//            int currentSize=queue.size();
//            while(currentSize>0){
//                currentNode=queue.remove(0);
//
//                int span=(int)Math.pow(2,height-depth-1);
//                for(int i=0;i<span;i++)
//                    System.out.print(" ");
//                System.out.print(currentNode.val);
//                if(currentSize==1)
//                    System.out.println();
//                else{
//
//                }
//                currentSize--;
//            }
//        }
//    }







//    public int maxPathSum(LovyNode root) {
//
//    }


    public static void test() {
        LovyTree<Integer> tree=LovyTree.createLovyTreeInLevel(1,2,3,4,5,6);
//        tree.printInOrder(tree.root);
        tree.printInorderIter();

//        tree.printPostOrder(tree.root);
        tree.printPostOrderIter();
    }
}
