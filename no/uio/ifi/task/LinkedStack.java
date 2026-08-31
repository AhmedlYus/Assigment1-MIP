package no.uio.ifi.task;

/* You are allowed to 1. add modifiers to fields and method signatures and 2. add code at the marked places, including removing the following return */
public class LinkedStack<T> {
    private Node<T> top;

    public int find(T t){
        /* implement me */

        // 1. check if the list is empty
        // 2. find the current element at the top.
        // 3. Compare if the curr element is the one we want
        // 4. If not go to the next until we find the element.

        Node<T> curr = top;

        while(top != null){
            if(curr.content.equals(t)){
                return 1;
            }
            curr = curr.next;
        }

        return -1;
    }

    public void push(T t){
        /* implement me */

        // 1. create a new node with the element to add.
        // 2. make the new node point to current top node.
        // 3. make top point to the new node.

        Node<T> newNode = new Node<>(t);
        newNode.next = top;
        top = newNode;

    }

    public T pop(){
        /* implement me */

        // 1. check if stack is emtpy
        // 2. if not store current top as temp
        // 3. move top to next node
        // 4. return temp value removed

        if(top == null) return null;
        Node<T> temp = top;
        top = top.next;
        temp.next = null;

        return temp.content;
    }
}
