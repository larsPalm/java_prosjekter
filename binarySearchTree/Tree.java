import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.*;

class Tree implements Oper
{
    Node root;
    int size;
    class Node 
    { 
        int value; 
        Node left, right, prev; 
  
        public Node(int item) 
        { 
            value = item; 
            left = right = prev = null; 
        } 
        /*public Node(int item, Node p) 
        { 
            value = item; 
            left = right = null; 
            prev = p;
        }*/
    } 
    public Tree()
    {
        root = null;
        size = 0;
    }
    public void add(int value)
    {
        if(root == null)
        {
            root = new Node(value);
        }
        else if(existsInTree(value))
        {
            return;
        }
        else if(root != null)
        {
            Node cur = root;
            Node prev = root;
            while(cur != null)
            {
                prev = cur;
                if(cur.value>value){cur = cur.left;}
                else if(cur.value<value){cur = cur.right;}
            }
            Node n = new Node(value);
            if(value< prev.value){prev.left = n;}
            else{prev.right = n;}
        }
        System.out.printf("added %d\n",value);
        size++;
    }
    public boolean remove(int value)
    {
        root = delete(root, value);
        if(existsInTree(value))
        {
            size--;
            return true;
        }
        return false;
    }
    public int size()
    {
        return size;
    }

    public boolean existsInTree(int value)
    {
        if(root.value == value){return true;}
        Node cur = root;
        while(cur != null)
        {
            if(cur.value == value){return true;}
            if(cur.value < value){cur = cur.right;}
            else if(cur.value > value){cur = cur.left;}
        }
        return false;
    }
    //public int findNearestSmallerThan( int value );
    public void addAll(int[] integers)
    {
        for(int i:integers){add(i);}
    }

    public Node delete(Node root, int key) 
    { 
        /* Base Case: If the tree is empty */
        if (root == null)  return root; 
  
        /* Otherwise, recur down the tree */
        if (key < root.value) 
            root.left = delete(root.left, key); 
        else if (key > root.value) 
            root.right = delete(root.right, key); 
  
        // if key is same as root's key, then This is the node 
        // to be deleted 
        else
        { 
            // node with only one child or no child 
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
  
            // node with two children: Get the inorder successor (smallest 
            // in the right subtree)
            //System.out.printf("before %d\n", root.value);
            //System.out.printf("right: %d\n", root.right.value);
            root.value = minValue(root.right);
            //System.out.printf("after %d\n", root.value);
  
            // Delete the inorder successor 
            root.right = delete(root.right, root.value); 
        } 
  
        return root; 
    }

    public int minValue(Node root)
    {
        //System.out.println(root.value);
        int minv = root.value;
        //System.out.println(minv);
        while (root.left != null) 
        { 
            minv = root.left.value;
            //System.out.println(minv);
            root = root.left; 
        } 
        return minv; 
    }
    public int[] sortedArray()
    {
        LinkedList<Integer> order = new LinkedList<>();
        inorder(root,order);
        int[] ret = new int[size];
        int teller = 0;
        for(int i:order)
        {
            ret[teller] = i;
            teller++;
        }
        return ret;
    }
    void inorder(Node cur,LinkedList<Integer> list)
    {
        if(cur != null)
        {
            inorder(cur.left, list);
            list.add(cur.value);
            inorder(cur.right, list);
        }
    }
     // inorder
    public int[] findInRange (int low, int high)
    {
        LinkedList<Integer> order = new LinkedList<>();
        inRange(root,order,low,high);
        int[] ret = new int[order.size()];
        int teller = 0;
        for(int i:order)
        {
            ret[teller] = i;
            teller++;
        }
        return ret;
    }
    void inRange(Node cur,LinkedList<Integer> list,int low, int high)
    {
        if(cur != null)
        {
            if(cur.value >low){inRange(cur.left, list,low,high);}
            if(cur.value>= low && cur.value<= high){list.add(cur.value);}
            if(cur.value <high){inRange(cur.right, list,low,high);}
        }
    }
}