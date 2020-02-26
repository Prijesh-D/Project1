/*
    Prijeshkumar Dholaria
    CS435  Section 006
    Question 2. c 
*/

import java.io.*; 
import java.util.*; 
class Node{
    int data;
    Node left, right;
    public Node(final int n){
        data = n;
        left = right = null;
    }
}
public class Sort{
    public static ArrayList<Node> sort(Node node){
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
    public static Node insert(Node node, int val){
        //base case, sets new node 
        if(node == null){
            return new Node(val);
        }
        else{
            //if val is greater than current node data then recursively go to right tree
            if(val>node.data){
                node.right = insert(node.right, val);
            }
            //else go to left tree
            else{
                node.left = insert(node.left, val);
            }
        }
        return node;
    }

    public static void main(String[] args){
        Node root = null;
        int keys[] = {15,10,20,8,12,16,25};
        //insert into BST
        for(int key : keys){
            root = insert(root, key);
        }
        ArrayList<Node> list = sort(root);
        list.forEach((Node value) -> System.out.print(value.data + " "));

    }
}