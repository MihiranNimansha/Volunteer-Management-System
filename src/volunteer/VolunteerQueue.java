package volunteer;

// Custom Queue - manages volunteers waiting to be assigned in registration order
public class VolunteerQueue {

    // Inner Node class for the queue
    private class Node {
        Volunteer data;
        Node next;

        Node(Volunteer data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // points to the front of the queue
    private Node rear;  // points to the rear of the queue
    private int size;

    // Constructor
    public VolunteerQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Enqueue - add a volunteer to the rear of the queue
    public void enqueue(Volunteer volunteer) {
        Node newNode = new Node(volunteer);
        if (rear == null) {
            // Queue is empty - front and rear both point to new node
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode; // attach to end
            rear = newNode;      // update rear pointer
        }
        size++;
    }

    // Dequeue - remove and return the volunteer at the front
    // Returns null if queue is empty
    public Volunteer dequeue() {
        if (isEmpty()) {
            System.out.println("  Queue is empty. No volunteers waiting.");
            return null;
        }
        Volunteer volunteer = front.data;
        front = front.next; // move front forward
        if (front == null) {
            rear = null; // queue is now empty
        }
        size--;
        return volunteer;
    }

    // Peek at the front without removing
    public Volunteer peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    // Display all volunteers currently in the queue
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("  Queue is empty.");
            return;
        }
        Node current = front;
        System.out.println("  Volunteers waiting in queue (registration order):");
        int position = 1;
        while (current != null) {
            System.out.print("  [" + position + "] ");
            current.data.display();
            current = current.next;
            position++;
        }
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Get size
    public int getSize() {
        return size;
    }
}
