package no.uio.ifi.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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


        // 1. read the file
        try (Scanner MyScanner = new Scanner(file) ) {

            // 2. as long as there is lines, read the nextline.
            while (MyScanner.hasNextLine()) {

                String line = MyScanner.nextLine();

                System.out.println("SCANNER READ: [" + line + "]");

                // 3. add the line to the thread
                inputExc.submit(() -> {
                    // 4. Add the word / line to the stack.
                    inputStack.push(line);
                    System.out.println("EXECUTOR: [" + line + "]");
                });

            }
            // close the thread
            inputExc.shutdown();
            inputExc.awaitTermination(5, TimeUnit.SECONDS);


        } catch (Exception e) {
            e.printStackTrace();
        }


        // Takes the String and puts in to the correct stack for odd and even length strings.
        Mapper<String, Boolean> mapper1 = new Mapper<String, Boolean>(layer) {
            @Override
            void transform(String input) {

                // 1. count the length.
                // 2. check length % / modulus 2
                // 3. if reminder = 0, its even, if not it's odd.
                // 4. add the input string to right stack.

                int len = input.length();
                if(len % 2 == 0) {
                    evenStack.push(input);

                } else {
                    oddStack.push(input);
                }
            }
        };

        // Takes the String and puts in to the correct stack for odd and even length strings.
        Mapper<String, Boolean> mapper2 = new Mapper<String, Boolean>(layer) {
            @Override
            void transform(String input) {

                // 1. count the length.
                // 2. check length % / modulus 2
                // 3. if reminder = 0, its even, if not it's odd.
                // 4. add the input string to right stack.

                int len = input.length();
                if(len % 2 == 0) {
                    evenStack.push(input);

                } else {
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
                /**
                 * each merger is assigned one stack (either odd or even) and
                 * computes the count of all elements into a hash map. The merger for the even
                 * stack computes the counts of all even words, and the merger for the odd stack
                 * computes the counts of all odd words.
                 */


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