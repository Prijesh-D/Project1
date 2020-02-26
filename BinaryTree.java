/*
    Prijeshkumar Dholaria
    CS435  Section 006
    Recursive Functions 1. c
*/
class Node { 
    int data; 
    Node left, right;
  
    public Node(final int value) {
        data = item;
        left = right = null;
    }
}
public class BinaryTree {
    Node root;
    public static void inOrder(Node node){
        //prints out inorder traversal
        if(node == null)
            return;
        inOrder(node.left);
        System.out.println(node.data + " ");
        inOrder(node.right);
    }
    public static Node insertRec(Node node, int val){
        //base case, sets new node 
        if(node == null){
            return new Node(val);
        }
        else{
            //if val is greater than current node data then recursively go to right tree
            if(val>node.data){
                node.right = insertRec(node.right, val);
            }
            //else go to left tree
            else{
                node.left = insertRec(node.left, val);
            }
        }
        return node;
    }
    public static Node deleteRec(Node node, int val){
        if (node == null)
			return null;
		if (node.data > val) {
			node.left = deleteRec(node.left, val);
		} else if (node.data < val) {
			node.right = deleteRec(node.right, val);
		} else {
			// selected node for deletion have both children
			if (node.left != null && node.right != null) {
				Node temp = node;
				Node nextBiggestValue = findMin(temp.right);
				node.data = nextBiggestValue.data;
				node.right = deleteRec(node.left, nextBiggestValue.data);
 
			}
			//selected node for deletion do not have child (Leaf node)
			else if(node.right == null && node.left == null)
                node = null;
            // selected node for deletion has only left child or only right child
			else{
                Node newChild = null;
                if(node.left != null)
                    newChild = node.left;
                else
                    newChild = node.right;
                node = newChild;
			}
		}
		return node;
    }
    public static Node findMin(Node node){ // will result in going down the left subtree
        while(node.left!=null)
            node = node.left;
        return node;
    }
    public static Node findNextRec(Node node, Node next, int val){
        //base case
        if(node == null){
            return null;
        }
        //if val == data and if right != null go down the left subtree
        if(node.data == val){
            if(node.right!=null)
                next = findMin(node.right);
        }
        //if val is < or > the current nodes data then you travel left or right tree recursively
        else if(node.data > val){
            next = node;
            return findNextRec(node.left, next, val);
        }
        else{
            return findNextRec(node.right, next, val);
        }
        return next;
    }
    public static Node findMax(Node node){ // will result in going down the right subtree
        while(node.right!=null)
            node = node.right;
        return node;
    }
    public static Node findPrevRec(Node node, Node next, int val){
        //base case
        if(node == null){
            return null;
        }
        //doing completely the opposite of what findMaxRec function is doing
        //if node == val and if left is not null, find max in left subtree
        if(node.data == val){
            if(node.left!=null)
                next = findMax(node.left);
        }
        else if(node.data < val){
            next = node;
            return findPrevRec(node.right, next, val);
        }
        else{
            return findPrevRec(node.left, next, val);
        }
        return next;
    }
    public static Node findMinRec(Node node){
        //first time using ternary operator, looks kinda cool
        //if node.left is null then return node else return recursive function
        return ((node.left == null) ? node : findMinRec(node.left));
    }
    public static Node findMaxRec(Node node){
        //if node.right is null then return node else return recursive function
        return ((node.right == null) ? node : findMaxRec(node.right));
    }
    public static void main(final String args[]) {
        Node root = null;
        int[] keys  = {15,10,20,8,12,16,25};
        for(int key:keys){
            root = insertRec(root, key);
        }
        inOrder(root);
        Node succ = findNextRec(root, null, 15);
        Node prev = findPrevRec(root, null, 15);
        Node printmin = findMinRec(root);
        Node printmax = findMaxRec(root);
        System.out.println("15 successor is "+ succ.data);
        System.out.println("15 presuccessor is "+ prev.data);
        System.out.println(printmin.data);
        System.out.println(printmax.data);
        deleteRec(root, 12);
        inOrder(root);
        deleteRec(root, 16);
        inOrder(root);
        
    } 
} 