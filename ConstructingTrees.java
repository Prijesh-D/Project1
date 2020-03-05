
/* Prijeshkumar Dholaria
   CS435   Section:006
    Problems 5 and 6*/
import java.io.*; 
import java.util.*; 
class Node { 
    int value; 
    Node left, right, parent;
    int balFactor;
    public Node( int value) {
        this.value = value;
        this.balFactor = 0;
    }
    public void leftRotateBalFactorUpdate(){
        //stores balance factors of all nodes involved
            int balFac = this.balFactor;
            int rightbalFac = this.right.balFactor;
            //updates balance factor
            if(this.right.balFactor <=0){
                this.balFactor--; //subtract since right side balance factor will be negative
            }
            else{
                // if greater than 0, update balFactor of current by subtracting right bal factor -1
                this.balFactor = this.balFactor - this.right.balFactor-1; 
            }
            //gets the lowest value and sets the right node's balance factor to that value
            this.right.balFactor = Math.min(balFac-2, rightbalFac-1);
    }
    public void leftRotate(){
        //avoiding confusion
        //"this" == current "node" that is being used
        //checks if the right node of curr is null
        if(this.right!=null){
            //left helper for updating balance factors
            this.leftRotateBalFactorUpdate();
            //assignment begins now by creating new node and assigning to curret node's right
            //the current node's right becomes the new node's left
            Node rightnode = this.right;
            this.right = rightnode.left;
            //if new node's left isn't null, the parent of it becomes the current node
            if(rightnode.left!=null){
                rightnode.left.parent = this;
            }
            //if it is null then the left node of the new node becomes the current node
            rightnode.left = this;
            //check for parent and assign parent to the correct node
            //check if current node's parent isn't null
            if(this.parent!=null){
                //assign left or right of parent depending on the current node's position to the parent (left child or right child)
                if(this.parent.left == this){
                    this.parent.left = rightnode;
                }
                else{
                    this.parent.right = rightnode;
                }
            }
            //the new node's parent becomes the current node's parent and the new nodebecomes parent of the current node
            rightnode.parent = this.parent;
            this.parent = rightnode;
        }
    }
    public void rightRotateBalFactorUpdate(){
        int balFac = this.balFactor;
        int leftbalFac = this.left.balFactor;
        //updates balance factor
        if(leftbalFac>=0){
            this.balFactor++; // add since left side balance factor will be positive
        }
        else{
            //if less than 0, update balFactor of current by subtracting left balance factor + 1
            this.balFactor = this.balFactor - this.left.balFactor+1;
        }
        //get the highest value and sets the left node's balance factor to that value
        this.left.balFactor = Math.max(balFac+2, leftbalFac+1);
    }
    public void rightRotate(){
        //avoiding confusion
        //"this" == current "node" that is being used
        //checks if the right node of curr is null
        if(this.left!=null){
            //helper for rightRotate //updates balance factors
            this.rightRotateBalFactorUpdate();
            //assignment begins now by creating new node and assigning to current node's left
            //the current node's left becomes the new node's right
            Node leftnode = this.left;
            this.left = leftnode.right;
            //if new node's right isn't null, the parent of it becomes the current node
            if(leftnode.right!=null){
                leftnode.right.parent = this;
            }
            //check for parent and assign parent to the correct node
            //check if current node's parent isn't null
            if(this.parent != null){
                //assign left or right of the parent depending on the current node's position to the parent (left child or right child)
                if(this.parent.left == this){
                    this.parent.left = leftnode;
                }
                else{
                    this.parent.right = leftnode;
                }
            }
            //new node's right becomes the current node
            //the new node's parent becomes the current node's parent and the new node becomes parent of the current node
            leftnode.right = this;
            leftnode.parent = this.parent;
            this.parent = leftnode;
            
        }
    }
} //class Node done

class AVLTree{
    Node root;
    int counter;
        //right rotation function
    public Node rightRotation(Node node){
        if(node == root){
            root = node.left;
        }
        node.rightRotate();
        return node.parent;
    }
    //left rotation function
    public Node leftRotation(Node node){
        if(node == root){
            root = node.right;
        }
        node.leftRotate();
        return node.parent;
    }
    //insert
    public void insert(Node node){
        //check if root is null, if it is, then set root to node and set balance factor to 0
        if(root == null){
            root = node;
            node.balFactor = 0;
        }
        else{
            //store current node and use loop until the value is inserted into the tree
            Node curr = root;
            boolean isInserted = false;
            while(!isInserted){
                if(node.value > curr.value){ //compare values of node being inserted and current value on the tree
                    if(curr.right == null){ //if right is null set current node's right to node being inserted and parent becomes the current, else continue going into tree
                        curr.right = node;
                        node.parent = curr; // assignment is complete and while loop ends
                        isInserted = true;
                    }
                    else{
                        curr = curr.right;
                    }
                }
                else{
                    if(curr.left == null){ // if left is null set current node's left to node being inserted and parent becomes the current, else continue going into tree
                        curr.left = node;
                        node.parent = curr; // assignment is complete and while loop ends
                        isInserted = true;
                    }
                    else{
                        curr = curr.left;
                    }
                }
                counter++;
            }
            //This is where the rebalancing phase comes in
            //set current node to node that was inserted and set boolean to false to signify go until the balancing is complete
            curr = node;
            boolean balanced = false;
            while(curr!=null && curr.parent !=null && !balanced){
                if(curr == curr.parent.left){
                    //means that the current node is the parent's left child
                    curr.parent.balFactor--;
                    //subtract balance factor because rotation will be necessary depending on the answer
                    if(curr.parent.balFactor == 0){
                        balanced = true; // parent bal factor was 1, therefore was already balanced
                    }
                    else if(curr.parent.balFactor == -2){
                        if(curr.balFactor == -1){
                            //Left left
                            curr = rightRotation(curr.parent);
                        }
                        else{
                            //left right
                            curr = leftRotation(curr);
                            curr = rightRotation(curr.parent);
                        }
                        balanced = true;
                    }
                }
                else{
                    //means that the current node is the parent's right child
                    int parentBalFactor = curr.parent.balFactor;
                    curr.parent.balFactor++;
                    if(curr.parent.balFactor== 0){
                        balanced = true;
                    }
                    else if(curr.parent.balFactor == 2){
                        //right right
                        if(curr.balFactor ==1){
                            curr = leftRotation(curr.parent);
                        }
                        else{
                        //right left
                            curr = rightRotation(curr);
                            curr = leftRotation(curr.parent);
                        }
                        balanced = true;
                    }
                }
                //set current node to parent node to keep loop going if nothing worked
                    curr = curr.parent;
            }
        }
    }
}//avlTree Done

class BSTTree{
    int counter;
    //insert
    public  Node insertRec(Node node, int val){
        //base case, sets new node 
        if(node == null){
            return new Node(val);
        }
        else{
            //if val is greater than current node data then recursively go to right tree
            if(val>node.value){
                node.right = insertRec(node.right, val);
            }
            //else go to left tree
            else{
                node.left = insertRec(node.left, val);
            }
            counter++;
        }
        return node;
    }
     public  Node insertIter(Node node, int val){
        Node prevNode= null;
        if(node == null){
            return new Node(val);
        }
        while(node != null){
            prevNode = node;
            if(val<node.value){
                node = node.left;
            }
            else{
                node = node.right;
            }
            counter++;
        }
        if(prevNode == null)
             prevNode = new Node(val);
        else if(val< prevNode.value){
            prevNode.left= new Node(val);
        }
        else{
            prevNode.right = new Node(val);
        }
        return prevNode;
    }
}

public class ConstructingTrees{
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
    public static ArrayList<Integer> getRandomArray(int n){
        //implement a function where output is an array of size n and contains random UNIQUE numbers
        //use Math.rand()
        // a)
        ArrayList<Integer> nums = new ArrayList<Integer>();
        while(nums.size()!=n){
            int rand = (int)(Math.random() * n + 1);
            if(nums.indexOf(rand)==-1){
                nums.add(rand);
            }
        }
        return nums;
    }
    public static ArrayList<Integer> getSortedArray(int n){
        //implement a function where the output is an array of size n and the first element is n and each consequent is n-1
        // b)
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for(int x = n; x!= 0; x--){
            nums.add(x);
        }
        return nums;
    }
    
    public static void main(String[] args){
        int amount = 10000;
        ArrayList<Integer> arrOfNums = getRandomArray(amount);
        //construct trees from this array ^^^^^
        
        //Iterative AVL and RecursiveBST //Problem 5a - Array is 10000
        BSTTree bstRec = new BSTTree();
        Node rootbstRec = null;
        AVLTree avlIter = new AVLTree();
        
        for(int num : arrOfNums){
            rootbstRec = bstRec.insertRec(rootbstRec, num);
            avlIter.insert(new Node(num));
        }
        //Iterative AVL and Recursive BST // Problem 5b - Array is 10
        int amount2 = 10;
        ArrayList<Integer> arrOfNums1 = getRandomArray(amount2);
        BSTTree bstRec2 = new BSTTree();
        Node rootbstRec2 = null;
        AVLTree avlIter2 = new AVLTree();
        
        for(int num : arrOfNums1){
            rootbstRec2 = bstRec2.insertRec(rootbstRec2, num);
            avlIter2.insert(new Node(num));
        }
        
        //Iterative AVL and Iterative BST //Problem 5c - Array is 10009
        BSTTree bstIter = new BSTTree();
        Node rootbstIter = null;
        AVLTree avlIterr = new AVLTree();
        for(int num : arrOfNums){
            rootbstIter = bstIter.insertIter(rootbstIter, num);
            avlIterr.insert(new Node(num));
        }
        
        //Problem 6a already implemented inside code;
        
        //Iterative Implementations Traversals RANDOM //Problem 6b, bstIter and avlIter gotten from 5c
        System.out.println("Iterative BST Traversals for Random Array of 10000: " + bstIter.counter);
        System.out.println("Iterative VAL Traversals for Random Array of 10000: "+ avlIter.counter);
        
        //Iterative Implementation Traversals SORTED //Problem 6c, bstIter and avlIter
        ArrayList<Integer> sorted = getSortedArray(amount); //amount 10000
        BSTTree bstIterSorted = new BSTTree();
        Node rootbstIterSorted = null;
        AVLTree avlIterSorted = new AVLTree();
        for(int num : sorted){
            rootbstIterSorted = bstIterSorted.insertIter(rootbstIterSorted, num);
            avlIterSorted.insert(new Node(num));
        }
        System.out.println("Iterative BST Traversals for Sorted Array of 10000: " + bstIterSorted.counter);
        System.out.println("Iterative VAL Traversals for Sorted Array of 10000: "+ avlIterSorted.counter);
        
    }
}
