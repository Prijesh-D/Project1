/*
    Prijeshkumar Dholaria
    CS435  Section 006
    Iterative Functions (- Delete, could not figure it out)
    1.d
*/
import java.io.*; 
import java.util.*; 

class Node { 
    int data; 
    Node left, right;
  
    public Node(final int item) {
        data = item;
        left = right = null;
    }
}

public class BinTree {
    Node root;
    public static ArrayList<Node> inOrderAppend(Node node){
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Stack<Node> s = new Stack<Node>();
        while(s.size()>0 || node != null){
            while(node != null){
                s.push(node);
                node= node.left;
            }
            node = s.pop();
            nodeList.add(node);
            node = node.right;
        }
        return nodeList;
    }
    
    public static Node insertIter(Node node, int val){
        Node prevNode= null;
        if(node == null){
            return new Node(val);
        }
        while(node != null){
            prevNode = node;
            if(val<node.data){
                node = node.left;
            }
            else{
                node = node.right;
            }
        }
        if(prevNode == null)
             prevNode = new Node(val);
        else if(val< prevNode.data){
            prevNode.left= new Node(val);
        }
        else{
            prevNode.right = new Node(val);
        }
        return prevNode;
    }
    
    public static Node findMinIter(Node node){
        //finds min in the node passed
        if(node == null){
            return null;
        }
        while(node.left!=null)
            node = node.left;
        return node;
    }
    
    public static Node findNextIter(Node node, int val){
        Node tempNode = null;
        while(node!=null){
            if(node.data>val){
                tempNode = node;
                node = node.left;
            }
            else
                node = node.right;
        }
        return tempNode;
    }
   
    public static Node findMaxIter(Node node){
        //finds max in the node passed
        if(node == null){
            return null;
        }
        while(node.right!=null)
            node = node.right;
        return node.data;
    }
    
    public static Node findPrevIter(Node node, int val){
        Node tempNode = null;
        while(node!=null){
            if(node.data<val){
                tempNode = node;
                node = node.right;
            }
            else
                node = node.left;
        }
        return tempNode;
    }

    public static void main(String args[])  {  
        Node root = null;  
        root = insertIter(root, 50);  
        insertIter(root, 30);  
        insertIter(root, 20);  
        insertIter(root, 40);  
        insertIter(root, 70);  
        insertIter(root, 60);  
        insertIter(root, 80);  
        Node test = findNextIter(root, 40);
        System.out.println(test.data);
        Node test2 = findPrevIter(root, 40);
        System.out.println(test2.data);
        ArrayList<Node> list = inOrderAppend(root);
        list.forEach((Node value) -> System.out.print(value.data + " "));

    }  
} 
