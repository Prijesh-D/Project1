/* Prijeshkumar Dholaria
   CS435     Section:006
   Problem 4c */


import java.io.*; 
import java.util.*; 

class Node{
    int balFactor;
    int value;
    Node parent;
    Node left;
    Node right;
    public Node(int key){
        this.value = key;
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
}
    
public class AVLTree{
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
    //search function to find value that is asked
    public Node search(int key) {
        Node curr = root;
        while (curr != null) {
            if (curr.value == key) {
                return curr;
            } else if (curr.value < key){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return null;
    }
    public void insertIter(Node node){
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
            }
            //This is where the rebalancing phase comes in
            //set current node to node that was inserted and set boolean to false to signify go until the balancing is complete
            curr = node;
            boolean balanced = false;
            while(curr!=null && curr.parent !=null && !balanced){
                if(curr == curr.parent.left){
                    //means that the current node is the parent's left child
                    int parentBalFactor = curr.parent.balFactor;
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
    
    public void deleteIter(Node node){
        //set newNode to the node that is being looked at
        Node newNode;
        if(node.right !=null){
            newNode = node.right;
            while(newNode.left != null){
                newNode = newNode.left;
            }
        }
        else{
            newNode = node;
        }
        //sets current node to new node for reference and sets boolean balanced to false
        Node curr = newNode;
        boolean balanced = false;
        //while parent of current node isnt null and it is not balanced
        while(curr.parent != null && !balanced){
            //increase/decrease parent BalFactor 
            if(curr.parent.left == curr){
                curr.parent.balFactor++;
            }
            else{
                curr.parent.balFactor--;
            }
            //if balance factor pf Parent is 1 or -1 it is balanced
            if(curr.parent.balFactor == 1 || curr.parent.balFactor == -1){
                //initial case
                balanced = true;
            }
            else if(curr.parent.balFactor == 2){
                //if it becomes a right right
                curr = curr.parent.right;
                if(curr.balFactor == 1){
                    curr = leftRotation(curr.parent);
                }
                else{
                //if it becomes  a right left
                    curr = rightRotation(curr);
                    curr = leftRotation(curr.parent);
                }
                //in case it has one node only
                if(curr.balFactor == 1 || curr.balFactor == -1){
                    balanced = true;
                }
            }
            else if(curr.parent.balFactor == -2){
                //if it becomes a left left
                curr = curr.parent.left;
                if(curr.balFactor == -1){
                    curr = rightRotation(curr.parent);
                }
                else{
                //if it becomes a left right
                    curr = leftRotation(curr);
                    curr = rightRotation(curr.parent);
                }
                //in case it has one node only
                if(curr.balFactor == 1 || curr.balFactor == -1){
                    balanced = true;
                }
            }
            else{
                //else keep going
                curr = curr.parent;
            }
        }
        //Now we have to assign parent node to newNode that is now deleted
        if(newNode.parent != null){
            if(newNode == newNode.parent.left){
                newNode.parent.left = null;
            }
            else{
                newNode.parent.right = null;
            }
        }
        if(newNode != node){
            //if newNode is not = to node that is deleted set the deleted nodes parent to current node
            if(node.parent!=null){
                if(node == node.parent.left){
                    node.parent.left = newNode;
                }
                else{
                    node.parent.right = newNode;
                }
            }
            //the deleted node's children become the newNode's children as well as its parent
            newNode.left = node.left;
            newNode.right = node.right;
            if(node.left !=null){
                node.left.parent = newNode;
            }
            if(node.right !=null){
                node.right.parent = newNode;
            }
            newNode.parent = node.parent;
        }
    } // end delete
    
    public static Node findNextIter(Node root, int val){
        Node tempNode = null;
        while(root != null){
            if(root.value > val){
                tempNode = root;
                root = root.left;
            }
            else{
                root = root.right;
            }
         }
         return tempNode;
   }

   public static Node findPrevIter(Node root, int val){
        Node tempNode = null;
        while(root != null){
            if(root.value < val){
                tempNode = root;
                root = root.right;
            }
            else{
                root = root.left;
            }
        }
        return tempNode;
   }

   public static Node findMaxIter(Node root){
        while(root.right != null){
            root = root.right;
        }
         return root;
   }
   public static Node findMinIter(Node root){
        while(root.left!= null){
            root = root.left;
        }
    
         return root;
   }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(new Node(22));
        tree.insert(new Node(3));
        tree.insert(new Node(4));
        tree.insert(new Node(15));
        tree.insert(new Node(25));
        tree.insert(new Node(0));
        tree.insert(new Node(8));
        tree.insert(new Node(29));
        tree.insert(new Node(19));
        tree.insert(new Node(12));
        tree.insert(new Node(7));
        tree.insert(new Node(11));
        tree.insert(new Node(23));
        System.out.print("Before Deleting 23:   ");
        ArrayList<Node> list = inOrderAppend(tree.root);
        list.forEach((Node node) -> System.out.print(node.value + " "));
        System.out.println();
        System.out.println("FindMax: " + findMaxIter(tree.root).value);
        System.out.println("FindMin: " + findMinIter(tree.root).value);
        System.out.println("FindNext of 12: " + findNextIter(tree.root, 12).value);
        System.out.println("FindPrev of 12: " + findPrevIter(tree.root, 12).value);
        tree.delete(tree.search(23));
        System.out.print("After Deleting 23:    ");
        ArrayList<Node> list2 = inOrderAppend(tree.root);
        list2.forEach((Node node2) -> System.out.print(node2.value + " "));
    }
}
        