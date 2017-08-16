package com.lovy.datastructure.binarytree;

import org.omg.CORBA.Any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2017/4/8.
 */
public class LovyBST<T extends Comparable<? super T>> extends LovyTree<T> {

    public LovyBST(){}
    public LovyBST(LovyNode<T> root){
        super(root);
    }


    private LovyNode<T> insert(LovyNode<T> root,LovyNode<T> newNode,boolean duplicated){
        if(root==null)
            return newNode;
        if(newNode==null)
            return root;

        int compareResult=root.val.compareTo(newNode.val);
        if(compareResult<0)
            root.right=insert(root.right,newNode,duplicated);
        else if(compareResult>0)
            root.left=insert(root.left,newNode,duplicated);
        else{
            if(duplicated)
                root.right=insert(root.right,newNode,duplicated);
            else
                ;
        }
        return root;
    }

    public void insert(LovyNode<T> newNode,boolean dupplicated){
        this.root=insert(this.root,newNode,dupplicated);
    }

    public void insert(LovyNode<T> newNode){
        this.root=insert(this.root,newNode,false);
    }


    public static <AnyType extends Comparable<? super AnyType>> LovyBST<AnyType> createLovyBST(AnyType... values){
        if(values==null||values.length==0)
            return null;
        LovyNode<AnyType> root=new LovyNode<AnyType>(values[0]);
        LovyBST<AnyType> lovyBST=new LovyBST(root);
        int k=1;
        while(k<values.length){
            lovyBST.insert(new LovyNode<AnyType>(values[k++]));
        }
        return lovyBST;
    }


    public LovyNode<T> remove(LovyNode<T> root,LovyNode<T> target){
        if(root==null||target==null)
            return null;

        int compareResult=root.val.compareTo(target.val);

        if(compareResult<0)
            root.right=remove(root.right,target);
        else if(compareResult>0)
            root.left=remove(root.left,target);
        else{
            if(root.left==null&&root.right==null)
                root=null;
            else if(root.left!=null){
                T leftSubTreeMaxVal=getRightest(root.left).val;
                root.val=leftSubTreeMaxVal;

                root.left=removeRightest(root.left);
            }else{
                T rightSubTreeMinVal=getLeftest(root.right).val;
                root.val=rightSubTreeMinVal;

                root.right=removeLestest(root.right);
            }
        }
        return root;
    }

    public LovyNode<T> remove(LovyNode<T> root,T target){
        return remove(root,new LovyNode<T>(target));
    }

    public void remove(LovyNode<T> target){
        this.root=remove(this.root,target);
    }

    public void remove(T target){
        this.root=remove(this.root,target);
    }

    public static <AnyType extends Comparable<? super AnyType>> LovyBST<AnyType> createBalancedLovyBST(AnyType... vals){
        Arrays.sort(vals);
        List<AnyType> valsList=new LinkedList<AnyType>(Arrays.asList(vals));
        return new LovyBST<AnyType>(createBalancedLovyNode(valsList));
    }

    private static <AnyType> LovyNode<AnyType> createBalancedLovyNode(List<AnyType> vals){
        if(vals.size()==0)
            return null;
        LovyNode<AnyType> root=new LovyNode<AnyType>(vals.get(vals.size()/2));
        List<AnyType> leftVals=new LinkedList<AnyType>();
        int i=0;
        for(;i<vals.size()/2;i++){
           leftVals.add(vals.get(i));
        }

        List<AnyType> rightVals=new LinkedList<AnyType>();
        for(i++;i<vals.size();i++)
            rightVals.add(vals.get(i));

        root.left=createBalancedLovyNode(leftVals);
        root.right=createBalancedLovyNode(rightVals);
        return root;
    }



    public List<LovyNode<Integer>> generateTrees(int n) {

        return genTrees(1,n);
    }

    public List<LovyNode<Integer>> genTrees (int start, int end)
    {

        List<LovyNode<Integer>> list = new ArrayList<>();

        if(start>end)
        {
            list.add(null);// this statement aims that it's parent node can enter the for each block
            return list;
        }

        if(start == end){
            list.add(new LovyNode<>(start));
            return list;
        }

        List<LovyNode<Integer>> left,right;
        for(int i=start;i<=end;i++)
        {

            left = genTrees(start, i-1);
            right = genTrees(i+1,end);

            for(LovyNode<Integer> lnode: left)
            {
                for(LovyNode<Integer> rnode: right)
                {
                    LovyNode<Integer> root = new LovyNode<Integer>(i);
                    root.left = lnode;
                    root.right = rnode;
                    list.add(root);
                }
            }

        }

        return list;
    }
    

    
    public static void test(){
        List<LovyNode<Integer>> treesNode=new LovyBST<Integer>().generateTrees(3);
        for(LovyNode<Integer> root:treesNode){
            new LovyTree<>(root).print();
            System.out.println("-----------------------------");
        }
    }
}
