import java.io.*;
import java.util.*;

public class Lab6
{

    /**
     *  Problem 1: Find the largest integer that appears at least m times.
     */
    private static int problem1(int[] arr, int m)
    {
       // Implement me!


        // Hash map where the key will be the unique value in the array and the value is the count of it
        HashMap<Integer, Integer> hm = new HashMap<>();


        // The max is set to the min integer value which is -2,147,483,648.
        int max = Integer.MIN_VALUE;

        for (int num: arr) {

            /*
             We do getOrDefault in case it is the first time we have seen the number.
             We cannot increment a value that does not exist so the default is just 0
             */

            hm.put(num, hm.getOrDefault(num, 0) + 1);
        }

      // Loop through the array again to find the max that appears at least m times
        for (int num: arr) {
            if ((hm.get(num) >= m) && (num > max)) {
                max = num;
            }
        }

       return max;
    }

    /**
     *  Problem 2: Find two distinct indices i and j such that arr[i] = arr[j] and |i - j| <= m.
     */
    private static int[] problem2(int[] arr, int m)
    {

        // We will define a hashmap with an array number as a key and it's index as it's value
        HashMap<Integer, Integer> hm = new HashMap<>();

        // Define the answer array. This will always have two elements
        int[] answer = new int[2];


        for (int i = 0; i < arr.length; i++) {

            // If the hash map has the key, that means we potentially have a pair.
            if (hm.containsKey(arr[i])) {

                // We check to see if the difference in indices is less than or equal to m
                if ((i - hm.get(arr[i])) <= m) {

                    // If they are, we simply put first index seen and last index seen in answer and break
                    answer = new int[]{hm.get(arr[i]), i};
                    break;
                }
            }


            /*
            We do this when we don't have the number in the hashmap, or when the difference in indices is greater than m.
            This line can both add new items, or update existing items
             */
            hm.put(arr[i], i);



        }



        return answer;


    }
    // ---------------------------------------------------------------------
    // Do not change any of the code below!

    private static final int LabNo = 6;
    private static final Random rng = new Random(123456);

    private static boolean testProblem1(int[][] testCase)
    {
        int[] arr = testCase[0];
        int m = testCase[1][0];

        int answer = problem1(arr.clone(), m);       
        
        Arrays.sort(arr);
        
        for (int i = arr.length-1, j = arr.length-1; i >= 0; i = j)
        {
            for (; j >=0 && arr[i] == arr[j]; j--) { }

             if (i - j >= m){
                return answer == arr[i];
             }
        }
        
        

        return false; // Will never happen.
    }

    private static boolean testProblem2(int[][] testCase)
    {
        int[] arr = testCase[0];
        int m = testCase[1][0];

        int[] answer = problem2(arr.clone(), m);

        if (answer == null || answer.length != 2)
        {
            return false;
        }

        Arrays.sort(answer);

        // Check answer
        int i = answer[0];
        int j = answer[1];
        return i != j
            && j - i <= m
            && i >= 0
            && j < arr.length
            && arr[i] == arr[j];
    }

    public static void main(String args[])
    {
        System.out.println("CS 302 -- Lab " + LabNo);
        testProblems(1);
        testProblems(2);
    }

    private static void testProblems(int prob)
    {
        int noOfLines = prob == 1 ? 100000 : 500000;

        System.out.println("-- -- -- -- --");
        System.out.println(noOfLines + " test cases for problem " + prob + ".");

        boolean passedAll = true;

        for (int i = 1; i <= noOfLines; i++)
        {

            int[][] testCase = null;

            boolean passed = false;
            boolean exce = false;

            try
            {
                switch (prob)
                {
                    case 1:
                        testCase = createProblem1(i);
                        passed = testProblem1(testCase);
                        break;

                    case 2:
                        testCase = createProblem2(i);
                        passed = testProblem2(testCase);
                        break;
                }
            }
            catch (Exception ex)
            {
                passed = false;
                exce = true;
            }

            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                passedAll = false;
                break;
            }
        }

        if (passedAll)
        {
            System.out.println("All test passed.");
        }

    }

    private static int[][] createProblem1(int testNo)
    {
        int size = rng.nextInt(Math.min(1000, testNo)) + 5;

        int[] numbers = getRandomNumbers(size, size);
        Arrays.sort(numbers);

        int maxM = 0;

        for (int i = 0, j = 0; i < size; i = j)
        {
            for (; j < size && numbers[i] == numbers[j]; j++) { }
            maxM = Math.max(maxM, j - i);
        }

        int m = rng.nextInt(maxM) + 1;

        shuffle(numbers);

        return new int[][] { numbers, new int[] { m } };
    }

    private static int[][] createProblem2(int testNo)
    {
        int size = rng.nextInt(Math.min(1000, testNo)) + 5;

        int[] numbers = getRandomNumbers(size, size);

        int i = rng.nextInt(size);
        int j = rng.nextInt(size - 1);
        if (i <= j) j++;

        numbers[i] = numbers[j];

        return new int[][] { numbers, new int[] { Math.abs(i - j) } };
    }

    private static void shuffle(int[] arr)
    {
        for (int i = 0; i < arr.length - 1; i++)
        {
            int rndInd = rng.nextInt(arr.length - i) + i;
            int tmp = arr[i];
            arr[i] = arr[rndInd];
            arr[rndInd] = tmp;
        }
    }

    private static int[] getRandomNumbers(int range, int size)
    {
        int numbers[] = new int[size];

        for (int i = 0; i < size; i++)
        {
            numbers[i] = rng.nextInt(2 * range) - range;
        }

        return numbers;
    }
}
