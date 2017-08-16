package com.lovy.datastructure.binarytree;

import org.omg.CORBA.Any;

/**
 * Created by asus on 2017/4/10.
 */
public class LovyThreadTree<T> extends LovyTree<T> {

    public LovyThreadTree(){}
    public LovyThreadTree(LovyThreadNode<T> root){
        this.root=root;
    }

    /**
     * in the inorder thread tree,this method aims to find the first node we will visit
     * @param node
     * @param <AnyType>
     * @return
     */
    public static <AnyType> LovyThreadNode<AnyType> findInorderFirst(LovyThreadNode<AnyType> node){
        while(node!=null&&node.ltag==0&&node.left!=null)
            node= (LovyThreadNode<AnyType>) node.left;
        return node;
    }

    /**
     * this method aims to find the next node(after the current node) we will access.
     * @param node
     * @param <AnyType>
     * @return
     */
    public static <AnyType> LovyThreadNode<AnyType> findInorderNext(LovyThreadNode<AnyType> node){
        if(node.rtag==1)
            return (LovyThreadNode<AnyType>) node.getRight();
        else
            return findInorderFirst((LovyThreadNode<AnyType>) node.getRight());
    }

    /**
     * we can travel all tree with the inorder thread tree
     * The key is: in inorder thread tree,the end start's (only this one node) right child is null
     */

    public void inorderTravelThreadTree(){
        LovyThreadNode<T> p= findInorderFirst((LovyThreadNode)root);
        for(;p!=null;p=findInorderNext(p)){
            System.out.print(p.val+"->");
        }
        System.out.println("STOP");
    }


    private static <T> LovyThreadNode<T> createLovyThreadNodeFromLovyNodeWithoutThread(LovyNode<T> root){
        if(root==null)
            return null;

        LovyThreadNode<T> newNode=new LovyThreadNode<T>(root.val);
        newNode.left=createLovyThreadNodeFromLovyNodeWithoutThread(root.left);
        newNode.right=createLovyThreadNodeFromLovyNodeWithoutThread(root.right);
        return newNode;
    }


    public static <T> LovyThreadTree<T> createLovyThreadTreeFromLovyTreeWithoutThread(LovyTree<T> tree){
        return new LovyThreadTree<T>(createLovyThreadNodeFromLovyNodeWithoutThread(tree.root));
    }


    /**
     * construct an inorder thread tree
     * @param node
     * @param preNode
     * @param <T>
     */
    private static <T> void createInorderThreadForLovyThreadNode(LovyThreadNode<T> node,LovyThreadNode<T>[] preNode){
        if(node==null)
            return ;

        createInorderThreadForLovyThreadNode((LovyThreadNode<T>) node.left,preNode);
        if(node.left==null&&preNode[0]!=node){
            node.ltag=1;
            node.left=preNode[0];
        }
        if(preNode[0]!=node&&preNode[0].right==null){
            preNode[0].rtag=1;
            preNode[0].right=node;
        }
        preNode[0]=node;
        if(node.rtag==0)
            createInorderThreadForLovyThreadNode((LovyThreadNode<T>) node.right,preNode);//there we refresh the preNode
    }


    public static <AnyType> LovyThreadTree<AnyType> createInorderLovyThreadTreeFromLovyTree(LovyTree<AnyType> lovyTree){
        LovyThreadTree lovyThreadTree=createLovyThreadTreeFromLovyTreeWithoutThread(lovyTree);
        if(lovyThreadTree.root==null)
            return lovyThreadTree;

        LovyThreadNode[] preNode=new LovyThreadNode[]{(LovyThreadNode) lovyThreadTree.root};

        createInorderThreadForLovyThreadNode((LovyThreadNode<AnyType>)lovyThreadTree.root,preNode);
        preNode[0].right=null;
        preNode[0].rtag=1;

        return lovyThreadTree;
    }




    //In this part ,we will construct preorder thread tree...

    /**
     * find the succeed node of preNode
     * @param root - the node of current tree's root
     * @param node - the current node we operate
     * @param <AnyType>
     * @return
     */
    public static <AnyType> LovyThreadNode<AnyType> findPreorderNext(LovyThreadNode<AnyType> root,LovyThreadNode<AnyType> node){
        if(node==null)
            return null;

        if(node.ltag==0)
            return (LovyThreadNode<AnyType>) node.left;
        else
            return (LovyThreadNode<AnyType>) node.right;

    }


    /**
     * find the node child's parent node
     * @param node
     * @param child
     * @param <AnyType>
     * @return
     */
    public static<AnyType> LovyThreadNode<AnyType> findParent(LovyThreadNode<AnyType> node,LovyThreadNode<AnyType> child){
        if(node==null)
            return null;

        if(node.left==child||node.right==child)
            return node;
        LovyThreadNode<AnyType> leftResult=findParent((LovyThreadNode<AnyType>) node.left,child);
        if(leftResult!=null)
            return leftResult;
        return findParent((LovyThreadNode<AnyType>)node.right,child);
    }


    /**
     *this method aims to find the precursor node of the current node
     * @param root
     * @param node
     * @param <AnyType>
     * @return
     */
    public static <AnyType> LovyThreadNode<AnyType> prePrior(LovyThreadNode<AnyType> root,LovyThreadNode<AnyType> node){
        if(node==null)
            return null;

        if(node.ltag!=0)//means the left child is the precursor
            return (LovyThreadNode<AnyType>) node.left;
        else{
            LovyThreadNode<AnyType> prePriorNode=findParent(root,node);
            if(prePriorNode!=null){
                if(prePriorNode.left==node)
                    return prePriorNode;
                else
                    return (LovyThreadNode<AnyType>) prePriorNode.left;
            }
        }
        return null;
    }


    public static <AnyType> void createPreorderThreadForLovyThreadNode(LovyThreadNode<AnyType> node,LovyThreadNode<AnyType>[] preNode){
        if(node==null)
            return;

        if(node.left==null){
            node.left=preNode[0];
            node.ltag=1;
        }

        if(preNode[0]!=null&&preNode[0].right==null){
            preNode[0].right=node;
            preNode[0].rtag=1;
        }
        preNode[0]=node;

        if(node.ltag==0)
            createPreorderThreadForLovyThreadNode((LovyThreadNode<AnyType>) node.left,preNode);

        if(node.rtag==0)
            createPreorderThreadForLovyThreadNode((LovyThreadNode<AnyType>) node.right,preNode);
    }


    public static <AnyType> LovyThreadTree<AnyType> createPreorderLovyThreadTreeFromLovyTree(LovyTree<AnyType> lovyTree){
        LovyThreadTree<AnyType> lovyThreadTree=createLovyThreadTreeFromLovyTreeWithoutThread(lovyTree);


        LovyThreadNode<AnyType>[] preNode=new LovyThreadNode[]{null};

        createPreorderThreadForLovyThreadNode((LovyThreadNode<AnyType>) lovyThreadTree.root,preNode);

        return lovyThreadTree;
    }


    public void preorderTravelThreadTree(){
        LovyThreadNode<T> p= (LovyThreadNode<T>) this.root;

        while(p!=null){
            System.out.print(p.val+"->");
            p= findPreorderNext((LovyThreadNode<T>)this.root,p);
        }
        System.out.println("STOP");
    }

    //at this part ,we will create the postorder thread tree

    public static <AnyType> LovyThreadNode<AnyType> findPostOrderNext(LovyThreadNode<AnyType> root,LovyThreadNode<AnyType> node){
        if(node==null)
            return null;
        if(node.rtag==1)
            return (LovyThreadNode<AnyType>) node.right;
        else{
            LovyThreadNode<AnyType> parent=findParent(root,node);
            if(parent.left==node)
                return (LovyThreadNode<AnyType>) parent.right;
            else
                return parent;
        }
    }


    public static <AnyType> void createPostorderThreadForLovyThreadNode(LovyThreadNode<AnyType> node,LovyThreadNode<AnyType>[] preNode){
        if(node==null)
            return;

        createPostorderThreadForLovyThreadNode((LovyThreadNode<AnyType>) node.left,preNode);
        createPostorderThreadForLovyThreadNode((LovyThreadNode<AnyType>) node.right,preNode);

        if(node.left==null){
            node.left=preNode[0];
            node.ltag=1;
        }

        //no matter a node has a right node, after it's left subtree and right subtree construct the thread,it's time to modify it's right child
        //to point the succeed node...
        if(preNode[0]!=null){
            preNode[0].right=node;
            preNode[0].rtag=1;
        }
        preNode[0]=node;
    }


    public static <AnyType> LovyThreadTree<AnyType> createPostorderLovyThreadTreeFromLovyTree(LovyTree<AnyType> lovyTree){
        LovyThreadTree<AnyType> lovyThreadTree=createLovyThreadTreeFromLovyTreeWithoutThread(lovyTree);

        LovyThreadNode<AnyType>[] preNode=new LovyThreadNode[]{null};
        createPostorderThreadForLovyThreadNode((LovyThreadNode<AnyType>)lovyThreadTree.root,preNode);

        preNode[0].rtag=1;
        preNode[0].right=null;

        return lovyThreadTree;
    }


    public void postorderTravelThreadTree(){
        LovyThreadNode<T> p=(LovyThreadNode<T>) getLeftest(this.root);
        while(p!=null){
            System.out.print(p.val+"->");
            p=findPostOrderNext((LovyThreadNode<T>)this.root,p);
        }
        System.out.println("STOP");
    }



    public static void test(){
        LovyTree lovyTree=createLovyTreeInLevel(1,2,3,4,5,6,7,8);

        //preorder testcase
        System.out.println("This is a testcase of preorder thread tree...");
        LovyThreadTree lovyThreadTreePreorder=createPreorderLovyThreadTreeFromLovyTree(lovyTree);

        lovyThreadTreePreorder.preorderTravelThreadTree();

        lovyTree.preOrder();

        //inorder testcase
        System.out.println("\n\nThis is a testcase of inorder thread tree...");
        LovyThreadTree lovyThreadTreeInorder=createInorderLovyThreadTreeFromLovyTree(lovyTree);

        lovyThreadTreeInorder.inorderTravelThreadTree();

        lovyTree.inOrder();

        //postorder testcase
        System.out.println("\n\nthis is a testcase of postorder thread tree...");
        LovyThreadTree lovyThreadTreePostorder=createPostorderLovyThreadTreeFromLovyTree(lovyTree);

        lovyThreadTreePostorder.postorderTravelThreadTree();

        lovyTree.postOrder();

    }
}
