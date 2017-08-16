package com.lovy.datastructure.binarytree;

import java.util.*;

import static com.lovy.datastructure.binarytree.LovyRBNode.*;
import static com.lovy.datastructure.binarytree.LovyRBNode.Color.BLACK;
import static com.lovy.datastructure.binarytree.LovyRBNode.Color.RED;

/**
 * Created by asus on 2017/6/9.
 */
public class LovyRBTree<T extends Comparable<? super T>> extends LovyBST<T> {

    private void leftRotate(LovyRBNode<T> node) {
        LovyRBNode<T> right = (LovyRBNode<T>) node.getRight();
        node.right = right.getLeft();
        right.left = node;

        if (node.getRight() != null)
            ((LovyRBNode<T>) node.getRight()).setParent(node);

        right.setParent(node.getParent());

        if (node.getParent() == null)
            root = right;
        else if (node == node.getParent().getLeft())
            right.getParent().setLeft(right);
        else
            right.getParent().setRight(right);

        node.setParent(right);
    }

    private void rightRotate(LovyRBNode<T> node) {
        LovyRBNode<T> left = (LovyRBNode<T>) node.getLeft();
        node.left = left.getRight();
        left.right = node;

        if (node.getLeft() != null)
            ((LovyRBNode<T>) node.getLeft()).setParent(node);

        left.setParent(node.getParent());

        if (node.getParent() == null)
            root = left;
        else if (node == node.getParent().getLeft())
            left.getParent().setLeft(left);
        else
            left.getParent().setRight(left);

        node.setParent(left);
    }

    public boolean insert(T key) {
        if (root == null) {
            root = new LovyRBNode<T>(key);
            ((LovyRBNode<T>) root).setColor(BLACK);
            return true;
        } else {
            LovyRBNode<T> p = (LovyRBNode<T>) root;
            LovyRBNode<T> pre = null;
            while (p != null) {
                pre = p;
                if (key.compareTo(p.getVal()) < 0)
                    p = (LovyRBNode<T>) p.getLeft();
                else if (key.compareTo(p.getVal()) > 0)
                    p = (LovyRBNode<T>) p.getRight();
                else
                    break;
            }
            if (key.compareTo(pre.getVal()) == 0)
                return false;

            LovyRBNode<T> newNode = new LovyRBNode<T>(key);
            if (key.compareTo(pre.getVal()) < 0)
                pre.setLeft(newNode);
            else
                pre.setRight(newNode);

            newNode.setParent(pre);
            insertFixUp(newNode);

            return true;
        }
    }

    private void insertFixUp(LovyRBNode<T> node) {
        LovyRBNode<T> parent = node.getParent();

        while (parent != null && parent.getColor() == RED) {
            LovyRBNode<T> ancestor = parent.getParent();
            LovyRBNode<T> uncle = getUncle(node);
            if (uncle == null) {//need to rotate
                //ancestor is not null due to parent is not null and its color is red
                if (parent == ancestor.getLeft()) {
                    boolean isRight = node == parent.getRight();
                    if (isRight) {
                        leftRotate(parent);
                    }
                    rightRotate(ancestor);

                    if (isRight) {
                        node.setColor(BLACK);
                    } else {
                        parent.setColor(BLACK);
                    }

                    parent = null;//end loop
                    ancestor.setColor(RED);
                } else {//symmetric
                    boolean isLeft = node == parent.getLeft();

                    if (isLeft) {
                        rightRotate(parent);
                    }
                    leftRotate(ancestor);

                    if (isLeft) {
                        node.setColor(BLACK);
                    } else {
                        parent.setColor(BLACK);
                    }
                    parent = null;//end loop
                    ancestor.setColor(RED);
                }
            } else if (uncle.getColor() == RED) {
                parent.setColor(BLACK);
                uncle.setColor(BLACK);
                ancestor.setColor(RED);
                node = ancestor;
                parent = node.getParent();

//                if(((Integer)preVal)==42){
//                    System.out.println("case 1...");
//                }
            } else if (ancestor.getLeft() == parent) {
                if (parent.getRight() == node) {
                    leftRotate(parent);
                    node = parent;
                    parent = node.getParent();
                } else {
                    rightRotate(ancestor);
                    ancestor.setColor(RED);
                    parent.setColor(BLACK);
                }
            } else {
                if (parent.getLeft() == node) {
                    rightRotate(parent);
                    node = parent;
                    parent = node.getParent();
                } else {
                    leftRotate(ancestor);
                    ancestor.setColor(RED);
                    parent.setColor(BLACK);
                }
            }
        }
        ((LovyRBNode<T>) root).setColor(BLACK);
    }


    /**
     * get uncle node
     *
     * @param node
     * @return
     */
    private LovyRBNode<T> getUncle(LovyRBNode<T> node) {
        LovyRBNode<T> parent = node.getParent();
        LovyRBNode<T> ancestor = parent.getParent();
        if (ancestor == null) {
            return null;
        }
        if (parent == ancestor.getLeft()) {
            return (LovyRBNode<T>) ancestor.getRight();
        } else {
            return (LovyRBNode<T>) ancestor.getLeft();
        }
    }

    private void print(LovyRBNode<T> node, int depth) {
        if (node != null) {
            for (int i = 0; i < depth; i++)
                System.out.print("  ");
            System.out.println(node.getVal() + "(" + (node.getColor() == BLACK ? "B" : "R") + ")");

            if (node.getLeft() != null)
                print((LovyRBNode<T>) node.getLeft(), depth + 1);
            else if (node.getRight() != null) {
                for (int i = 0; i < depth + 1; i++)
                    System.out.print("  ");
                System.out.println("--");
            }

            if (node.getRight() != null)
                print((LovyRBNode<T>) node.getRight(), depth + 1);
            else if (node.getLeft() != null) {
                for (int i = 0; i < depth + 1; i++)
                    System.out.print("  ");
                System.out.println("--");
            }
        }
    }

    public void print() {
        print((LovyRBNode<T>) root, 0);
    }

    private void countBlack(LovyRBNode<T> node, int pre, List<Integer> counter) {
        if (node == null) {
            counter.add(pre + 1);
            return;
        }

        countBlack((LovyRBNode<T>) node.getLeft(), node.getColor() == BLACK ? pre + 1 : pre, counter);
        countBlack((LovyRBNode<T>) node.getRight(), node.getColor() == BLACK ? pre + 1 : pre, counter);
    }


    public List<Integer> countBlack() {
        List<Integer> counter = new ArrayList<>();
        countBlack((LovyRBNode<T>) root, 0, counter);
        return counter;
    }

    private LovyRBNode<T> inorderSuccessor(LovyRBNode<T> node) {
        if(node==null)
            return null;

        LovyRBNode<T> p= (LovyRBNode<T>) node.getRight();
        if(p!=null){
            while(p.getLeft()!=null)
                p= (LovyRBNode<T>) p.getLeft();
            return p;
        }else{
            LovyRBNode<T> t=null;
            p=node;

            while(p!=null&&p.getRight()==t){
                t=p;
                p=p.getParent();
            }
            return p;
        }
    }

    @Override
    public void remove(T key) {
        LovyRBNode<T> y = (LovyRBNode<T>) root;
        while (y != null) {
            if (key.compareTo(y.getVal()) < 0)
                y = (LovyRBNode<T>) y.getLeft();
            else if (key.compareTo(y.getVal()) > 0)
                y = (LovyRBNode<T>) y.getRight();
            else
                break;
        }

        if (y == null)
            return;

        if (y.getLeft() != null && y.getRight() != null) {
            LovyRBNode<T> successor = inorderSuccessor(y);
            y.setVal(successor.getVal());
            y = successor;
        }

        LovyRBNode<T> x;
        if (y.getLeft() != null)
            x = (LovyRBNode<T>) y.getLeft();
        else
            x = (LovyRBNode<T>) y.getRight();

        if(x!=null){
            x.setParent(y.getParent());
            if(y.getParent()==null)
                root=x;
            else if(y.getParent().getLeft()==y)
                y.getParent().setLeft(x);
            else
                y.getParent().setRight(x);

//            y.setLeft(null);
//            y.setRight(null);
//            y.setParent(null);

            if(y.getColor()==BLACK)
                removeFixUp(x);
        }else if(y.getParent()==null)
            root=null;
        else{
            if(y.getColor()==BLACK)
                removeFixUp(y);

            if(y.getParent()!=null){
                if(y==y.getParent().getLeft())
                    y.getParent().setLeft(null);
                else
                    y.getParent().setRight(null);

//                y.setParent(null);
            }
        }
    }

    public void removeFixUp(LovyRBNode<T> x){
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                LovyRBNode<T> sib = rightOf(parentOf(x));

                //Case 1)x是"黑+黑"节点，x的兄弟节点是红色:(额外的一个"黑色")
                /*
                (01) 将x的兄弟节点设为“黑色”。
                (02) 将x的父节点设为“红色”。
                (03) 对x的父节点进行左旋。
                (04) 左旋后，重新设置x的兄弟节点。

                 这样做的目的是将“Case 1”转换为“Case 2”、“Case 3”或“Case 4”，从而进行进一步的处理。对x的父节点进行左旋；
                 左旋后，为了保持红黑树特性，就需要在左旋前“将x的兄弟节点设为黑色”，同时“将x的父节点设为红色”；左旋后，由于x的兄弟节点发生了变化，需要更新x的兄弟节点，从而进行后续处理。
                 */
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    leftRotate(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                //(Case 2) x是"黑+黑"节点，x的兄弟节点是黑色，x的兄弟节点的两个孩子都是黑色
                /*
                (01) 将x的兄弟节点设为“红色”。
                (02) 设置“x的父节点”为“新的x节点”

                这个情况的处理思想：是将“x中多余的一个黑色属性上移(往根方向移动)”。
                x是“黑+黑”节点，我们将x由“黑+黑”节点 变成 “黑”节点，多余的一个“黑”属性移到x的父节点中，即x的父节点多出了一个黑属性(若x的父节点原先是“黑”，则此时变成了“黑+黑”；
                若x的父节点原先时“红”，则此时变成了“红+黑”)。 此时，需要注意的是：所有经过x的分支中黑节点个数没变化；但是，所有经过x的兄弟节点的分支中黑色节点的个数增加了1(因为x的父节点多了一个黑色属性)！
                为了解决这个问题，我们需要将“所有经过x的兄弟节点的分支中黑色节点的个数减1”即可，那么就可以通过“将x的兄弟节点由黑色变成红色”来实现。
                经过上面的步骤(将x的兄弟节点设为红色)，多余的一个颜色属性(黑色)已经跑到x的父节点中。我们需要将x的父节点设为“新的x节点”进行处理。
                若“新的x节点”是“黑+红”，直接将“新的x节点”设为黑色，即可完全解决该问题；若“新的x节点”是“黑+黑”，则需要对“新的x节点”进行进一步处理。
                 */
                if (colorOf(leftOf(sib))  == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    //(Case 3)x是“黑+黑”节点，x的兄弟节点是黑色；x的兄弟节点的左孩子是红色，右孩子是黑色的
                    /*
                    (01) 将x兄弟节点的左孩子设为“黑色”。
                    (02) 将x兄弟节点设为“红色”。
                    (03) 对x的兄弟节点进行右旋。
                    (04) 右旋后，重新设置x的兄弟节点。

                    我们处理“Case 3”的目的是为了将“Case 3”进行转换，转换成“Case 4”,从而进行进一步的处理。转换的方式是对x的兄弟节点进行右旋；
                    为了保证右旋后，它仍然是红黑树，就需要在右旋前“将x的兄弟节点的左孩子设为黑色”，同时“将x的兄弟节点设为红色”；右旋后，由于x的兄弟节点发生了变化，需要更新x的兄弟节点，从而进行后续处理。
                     */
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rightRotate(sib);
                        sib = rightOf(parentOf(x));
                    }

                    //(Case 4)x是“黑+黑”节点，x的兄弟节点是黑色；x的兄弟节点的右孩子是红色的，x的兄弟节点的左孩子任意颜色
                    /*
                    (01) 将x父节点颜色 赋值给 x的兄弟节点。
                    (02) 将x父节点设为“黑色”。
                    (03) 将x兄弟节点的右子节设为“黑色”。
                    (04) 对x的父节点进行左旋。
                    (05) 设置“x”为“根节点”。

                    我们处理“Case 4”的目的是：去掉x中额外的黑色，将x变成单独的黑色。处理的方式是“：进行颜色修改，然后对x的父节点进行左旋。下面，我们来分析是如何实现的。
                    为了便于说明，我们设置“当前节点”为S(Original Son)，“兄弟节点”为B(Brother)，“兄弟节点的左孩子”为BLS(Brother's Left Son)，“兄弟节点的右孩子”为BRS(Brother's Right Son)，“父节点”为F(Father)。
                    为了保证满足“特性(5)”，即为了保证左旋之后：
                    第一，“同时经过根节点和S的分支的黑色节点个数不变”。
                    若满足“第一”，只需要S丢弃它多余的颜色即可。因为S的颜色是“黑+黑”，而左旋后“同时经过根节点和S的分支的黑色节点个数”增加了1；现在，只需将S由“黑+黑”变成单独的“黑”节点，即可满足“第一”。
                    第二，“同时经过根节点和BLS的分支的黑色节点数不变”。
                    若满足“第二”，只需要将“F的原始颜色”赋值给B即可。之前，我们已经将“F设置为黑色”(即，将B的颜色"黑色"，赋值给了F)。至此，我们算是调换了F和B的颜色。
                    第三，“同时经过根节点和BRS的分支的黑色节点数不变”。
                    在“第二”已经满足的情况下，若要满足“第三”，只需要将BRS设置为“黑色”即可。
                    经过，上面的处理之后。红黑树的特性全部得到的满足！接着，我们将x设为根节点，就可以跳出while循环(参考伪代码)；即完成了全部处理。
                     */
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);//把x额外的黑色给了父节点，保证经过父节点和x的黑色数目不变
                    setColor(rightOf(sib), BLACK);//在原来的路径上，由于颜色的交换，少了sib的一个黑色，直接修改右孩子颜色即可
                    leftRotate(parentOf(x));//在左旋转之后，在父节点到sib.leftChild路径上的黑色数目不变
                    x = (LovyRBNode<T>) root;
                }
            } else { // symmetric
                LovyRBNode<T> sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rightRotate(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        leftRotate(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rightRotate(parentOf(x));
                    x = (LovyRBNode<T>) root;
                }
            }
        }

        setColor(x, BLACK);
    }

    public static void test() {
        LovyRBTree<Integer> rbTree = new LovyRBTree<>();
        Random random = new Random();
        Set<Integer> rep = new HashSet<>();

        int size=10;
        while(rep.size()<size)
            rep.add(Math.abs(random.nextInt(100)));

        for (int val : rep) {
            rbTree.insert(val);
        }
        rbTree.print();
    }

//     public static void main(String[] args){
//         test();
//     }
}
