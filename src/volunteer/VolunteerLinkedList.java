package volunteer;

// Custom Singly LinkedList to store and manage Volunteer records
public class VolunteerLinkedList {

    // Inner Node class - holds a Volunteer and a pointer to next node
    private class Node {
        Volunteer data;
        Node next;

        Node(Volunteer data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // points to first node
    private int size;  // tracks number of volunteers

    // Constructor
    public VolunteerLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add a volunteer at the end of the list
    public void add(Volunteer volunteer) {
        Node newNode = new Node(volunteer);
        if (head == null) {
            head = newNode; // list was empty
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next; // traverse to last node
            }
            current.next = newNode; // attach at end
        }
        size++;
    }

    // Remove a volunteer by their ID
    // Returns the removed Volunteer, or null if not found
    public Volunteer removeById(int volunteerId) {
        if (head == null) {
            System.out.println("List is empty.");
            return null;
        }
        // Check if head node is the one to remove
        if (head.data.getVolunteerId() == volunteerId) {
            Volunteer removed = head.data;
            head = head.next;
            size--;
            return removed;
        }
        // Traverse list to find the node before the target
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getVolunteerId() == volunteerId) {
                Volunteer removed = current.next.data;
                current.next = current.next.next; // skip over removed node
                size--;
                return removed;
            }
            current = current.next;
        }
        System.out.println("Volunteer with ID " + volunteerId + " not found.");
        return null;
    }

    // Search volunteer by ID
    public Volunteer searchById(int volunteerId) {
        Node current = head;
        while (current != null) {
            if (current.data.getVolunteerId() == volunteerId) {
                return current.data;
            }
            current = current.next;
        }
        return null; // not found
    }

    // Search volunteer by name
    public Volunteer searchByName(String name) {
        Node current = head;
        while (current != null) {
            if (current.data.getName().equalsIgnoreCase(name)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // not found
    }

    // Display all volunteers in the list
    public void displayAll() {
        if (head == null) {
            System.out.println("  No volunteers registered.");
            return;
        }
        Node current = head;
        while (current != null) {
            current.data.display();
            current = current.next;
        }
    }

    // Display volunteers by a priority level
    public void displayByPriority(String priority) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.data.getPriorityLevel().equalsIgnoreCase(priority)) {
                current.data.display();
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("  No volunteers with priority: " + priority);
        }
    }

    // Get size of the list
    public int getSize() {
        return size;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return head == null;
    }
}
