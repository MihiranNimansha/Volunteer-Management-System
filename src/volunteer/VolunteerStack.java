package volunteer;

// Custom Stack - used to track recent volunteer operations for UNDO
public class VolunteerStack {

    // Inner Node class for the stack
    private class Node {
        OperationRecord data;
        Node next;

        Node(OperationRecord data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; // points to the top of the stack
    private int size;

    // Constructor
    public VolunteerStack() {
        this.top = null;
        this.size = 0;
    }

    // Push an operation record onto the stack
    public void push(OperationRecord record) {
        Node newNode = new Node(record);
        newNode.next = top; // new node points to current top
        top = newNode;      // top is now the new node
        size++;
    }

    // Pop the top operation record from the stack
    // Returns null if stack is empty
    public OperationRecord pop() {
        if (isEmpty()) {
            System.out.println("  Stack is empty. Nothing to undo.");
            return null;
        }
        OperationRecord record = top.data;
        top = top.next; // move top down
        size--;
        return record;
    }

    // Peek at the top without removing
    public OperationRecord peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Get size
    public int getSize() {
        return size;
    }
}
