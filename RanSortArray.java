import java.io.*; 
import java.util.*; 
/*
    Prijeshkumar Dholaria   
    Class 435 - Section 006
    Question 3 - Part a and b
*/



public class RanSortArray{
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
        int randomArrNum = 30;
        int sortedArrNum = 30;
        System.out.println(Arrays.deepToString(getRandomArray(randomArrNum).toArray()));
        System.out.println(Arrays.deepToString(getSortedArray(sortedArrNum).toArray()));
    }


}
