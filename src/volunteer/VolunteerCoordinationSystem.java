package volunteer;

// VolunteerCoordinationSystem - the main system class
// Brings together LinkedList, Queue, and Stack to manage volunteers
public class VolunteerCoordinationSystem {

    private VolunteerLinkedList volunteerList; // stores all registered volunteers
    private VolunteerQueue registrationQueue;  // manages volunteers waiting to be assigned
    private VolunteerStack undoStack;          // tracks operations for undo
    private int nextId;                        // auto-increments volunteer IDs

    // Constructor
    public VolunteerCoordinationSystem() {
        this.volunteerList = new VolunteerLinkedList();
        this.registrationQueue = new VolunteerQueue();
        this.undoStack = new VolunteerStack();
        this.nextId = 1;
    }

    // ---- VOLUNTEER MANAGEMENT ALGORITHM -----

    // Add a volunteer to the system
    public void registerVolunteer(String name, String taskAssigned, String priorityLevel) {
        registerVolunteer(name, taskAssigned, priorityLevel, false);
    }

    // Overloaded method to add a volunteer to the system with an option to suppress output
    public void registerVolunteer(String name, String taskAssigned, String priorityLevel, boolean silent) {
        Volunteer volunteer = new Volunteer(nextId, name, taskAssigned, priorityLevel);
        nextId++;

        volunteerList.add(volunteer);         // add to the main list
        registrationQueue.enqueue(volunteer); // add to queue (registration order)

        // Push ADD operation to undo stack
        undoStack.push(new OperationRecord(OperationRecord.ADD, volunteer));

        if (!silent) {
            System.out.println("  Volunteer registered: " + volunteer.getName()
                    + " (ID: " + volunteer.getVolunteerId() + ")");
        }
    }

    // Remove a volunteer from the system by ID
    // O(n) - search through linked list
    public void removeVolunteer(int volunteerId) {
        Volunteer removed = volunteerList.removeById(volunteerId);
        if (removed != null) {
            // Push REMOVE operation to undo stack so it can be reversed
            undoStack.push(new OperationRecord(OperationRecord.REMOVE, removed));
            System.out.println("  Volunteer removed: " + removed.getName()
                    + " (ID: " + removed.getVolunteerId() + ")");
        }
    }

    // Search for a volunteer by ID
    public void searchById(int volunteerId) {
        Volunteer found = volunteerList.searchById(volunteerId);
        if (found != null) {
            System.out.println("  Found:");
            found.display();
        } else {
            System.out.println("  No volunteer found with ID: " + volunteerId);
        }
    }

    // Search for a volunteer by name
    public void searchByName(String name) {
        Volunteer found = volunteerList.searchByName(name);
        if (found != null) {
            System.out.println("  Found:");
            found.display();
        } else {
            System.out.println("  No volunteer found with name: " + name);
        }
    }

    // ---- PRIORITY-BASED ASSIGNMENT ALGORITHM ----

    // Assign tasks to volunteers from the queue based on priority
    public void assignByPriority() {
        System.out.println("  Assigning volunteers by priority (High -> Medium -> Low)...");

        String[] priorities = {"high", "medium", "low"};

        for (String priority : priorities) {
            System.out.println("  --- Priority: " + priority.toUpperCase() + " ---");
            // Use a temporary queue to requeue non-matching volunteers
            VolunteerQueue tempQueue = new VolunteerQueue();
            boolean anyAssigned = false;

            // Process entire queue
            while (!registrationQueue.isEmpty()) {
                Volunteer v = registrationQueue.dequeue();
                if (v.getPriorityLevel().equalsIgnoreCase(priority) && !v.isAssigned()) {
                    // Assign this volunteer
                    v.setAssigned(true);
                    undoStack.push(new OperationRecord(OperationRecord.ASSIGN, v, v.getTaskAssigned()));
                    System.out.println("  Assigned: " + v.getName()
                            + " -> Task: " + v.getTaskAssigned());
                    anyAssigned = true;
                } else {
                    tempQueue.enqueue(v); // put back unmatched ones
                }
            }

            // Restore remaining volunteers back to the main queue
            while (!tempQueue.isEmpty()) {
                registrationQueue.enqueue(tempQueue.dequeue());
            }

            if (!anyAssigned) {
                System.out.println("  No unassigned volunteers at this priority.");
            }
        }
    }

    // View volunteers filtered by priority level
    public void viewByPriority(String priority) {
        System.out.println("  Volunteers with priority [" + priority.toUpperCase() + "]:");
        volunteerList.displayByPriority(priority);
    }

    // Reassign a volunteer to a new task
    public void reassignVolunteer(int volunteerId, String newTask) {
        Volunteer v = volunteerList.searchById(volunteerId);
        if (v != null) {
            String oldTask = v.getTaskAssigned();
            undoStack.push(new OperationRecord(OperationRecord.ASSIGN, v, oldTask));
            v.setTaskAssigned(newTask);
            System.out.println("  Volunteer " + v.getName()
                    + " reassigned from [" + oldTask + "] to [" + newTask + "]");
        } else {
            System.out.println("  Volunteer not found.");
        }
    }

    // ---- UNDO ASSIGNMENT ALGORITHM -----

    // Undo the most recent operation using the Stack
    public void undoLastOperation() {
        OperationRecord record = undoStack.pop();
        if (record == null) {
            return; // stack was empty
        }

        String type = record.getOperationType();
        Volunteer v = record.getVolunteer();

        if (type.equals(OperationRecord.ADD)) {
            // Undo an ADD: remove the volunteer from the list
            volunteerList.removeById(v.getVolunteerId());
            System.out.println("  UNDO: Removed volunteer that was added -> " + v.getName());

        } else if (type.equals(OperationRecord.REMOVE)) {
            // Undo a REMOVE: add the volunteer back to the list
            volunteerList.add(v);
            System.out.println("  UNDO: Re-added volunteer that was removed -> " + v.getName());

        } else if (type.equals(OperationRecord.ASSIGN)) {
            // Undo an ASSIGN: restore previous task and unassign
            v.setTaskAssigned(record.getPreviousTask());
            v.setAssigned(false);
            System.out.println("  UNDO: Reverted assignment for -> " + v.getName()
                    + " | Task restored to: " + record.getPreviousTask());
        }
    }

    // ---- DISPLAY METHODS ----

    // Display all registered volunteers
    public void displayAllVolunteers() {
        System.out.println("  All Registered Volunteers:");
        volunteerList.displayAll();
    }

    // Display the registration queue
    public void displayQueue() {
        registrationQueue.displayQueue();
    }

    // Display undo stack size
    public void displayUndoStackInfo() {
        System.out.println("  Undo stack has " + undoStack.getSize() + " operation(s).");
    }

    // Process ALL unassigned volunteers from the queue one by one
    public void processAllFromQueue() {
        if (registrationQueue.isEmpty()) {
            System.out.println("  Queue is empty. No volunteers to process.");
            return;
        }
        int count = 0;
        while (!registrationQueue.isEmpty()) {
            Volunteer v = registrationQueue.dequeue();
            if (!v.isAssigned()) {
                v.setAssigned(true);
                undoStack.push(new OperationRecord(OperationRecord.ASSIGN, v, v.getTaskAssigned()));
                System.out.println("  [OK] " + v.getName()
                        + " -> " + v.getTaskAssigned()
                        + "  [Priority: " + v.getPriorityLevel() + "]");
                count++;
            }
        }
        System.out.println("  Total volunteers processed from queue: " + count);
    }

    // Process the next volunteer from the queue
    // Dequeues front volunteer, marks assigned, pushes to undo stack
    public void processNextFromQueue() {
        if (registrationQueue.isEmpty()) {
            System.out.println("  Queue is empty. No volunteers waiting to be processed.");
            return;
        }
        // Peek so user sees who is next
        Volunteer next = registrationQueue.peek();
        System.out.println("  Next in queue:");
        next.display();

        if (next.isAssigned()) {
            registrationQueue.dequeue(); // already assigned, just remove from queue
            System.out.println("  Note: Already assigned. Removed from queue.");
            return;
        }
        // Dequeue and assign
        Volunteer v = registrationQueue.dequeue();
        v.setAssigned(true);
        undoStack.push(new OperationRecord(OperationRecord.ASSIGN, v, v.getTaskAssigned()));
        System.out.println("  [OK] Assigned via queue: "
                + v.getName() + " -> " + v.getTaskAssigned());
        System.out.println("  Remaining in queue: " + registrationQueue.getSize());
    }

    // Safe undo - returns true if undo was performed, false if stack was empty
    public boolean undoLastOperation_safe() {
        if (undoStack.isEmpty()) {
            return false;
        }
        undoLastOperation();
        return true;
    }
}
