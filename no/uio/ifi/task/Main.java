package no.uio.ifi.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* You are allowed to 1. add modifiers to fields and method signatures of subclasses, and 2. add code at the marked places, including removing the following return */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        LinkedStack<String> inputStack = new LinkedStack<>();
        LinkedStack<String> evenStack = new LinkedStack<>();
        LinkedStack<String> oddStack = new LinkedStack<>();

        HashMap<Boolean,LinkedStack<String>> layer = new HashMap<>();
        layer.put(true, evenStack);
        layer.put(false, oddStack);

        // Count the number of even and odd strings in the file
        // We need this to know how many threads to start for the mappers and mergers
        int evenCount = 0;
        int oddCount = 0;
        HashMap<String, Integer> total = new HashMap<>();

        ExecutorService inputExc = Executors.newCachedThreadPool();
        File file = new File("words.txt");
        /* TODO: Read the file and for each line add the string to the inputQueue. Each line contains a single string */

        Mapper<String, Boolean> mapper1 = new Mapper<String, Boolean>(layer) {
            @Override
            void transform(String input) {
                /* TODO: take string and put it into the right stack */

                // 1. count the length.
                // 2. check length % / modolus 2
                // 3. if reminder = 0, its even, if not its odd.
                // 4. add the input string to right stack.

                int len = input.length();
                if(len % 2 == 0) {
                    // add to even
                    evenStack.push(input);

                } else {
                    // push to odd stack
                    oddStack.push(input);
                }


            }
        };
        Mapper<String, Boolean> mapper2 = new Mapper<String, Boolean>(layer) {
            @Override
            void transform(String input) {
                /* TODO: take string and put it into the right stack */
                // 1. count the length.
                // 2. check length % / modolus 2
                // 3. if reminder = 0, its even, if not its odd.
                // 4. add the input string to right stack.

                int len = input.length();
                if(len % 2 == 0) {
                    // add to even
                    evenStack.push(input);

                } else {
                    // push to odd stack
                    oddStack.push(input);
                }
            }
        };

        ExecutorService distribute = Executors.newCachedThreadPool();
        /* TODO: start n threads, each taking a single string from inputStack to either mapper1 or mapper2
        *        each mapper might have different number of strings to process,
        *        so the threads should keep taking strings until all strings
        *        have been processed
        *        the mapper must add its string to the correct queue*/

        Merger<String> merger1 = new Merger<String>() {
            @Override
            protected void merge(String input) {
                /* put the number of occurrences of the string in the map */

            }
        };
        Merger<String> merger2 = new Merger<String>() {

            @Override
            protected void merge(String input) {
                /* put the number of occurrences of the string in the map */
            }
        };


        ExecutorService merge = Executors.newCachedThreadPool();
        /* TODO: start n threads, each taking one string from either stack and giving it to a merger.
        *        Merger 1 will only add even strings, merger 2 will only add odd strings */

        // Close the executors
        inputExc.shutdown();
        distribute.shutdown();
        merge.shutdown();

        Thread.sleep(3000);
        System.out.println("Total: " + total);
    }
}