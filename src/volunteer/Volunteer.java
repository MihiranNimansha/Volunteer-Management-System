package volunteer;

// Volunteer class - represents a single volunteer record
// Used as the data stored inside LinkedList nodes
public class Volunteer {

    // Attributes for each volunteer
    int volunteerId;
    String name;
    String taskAssigned;
    String priorityLevel; // "high", "medium", "low"
    boolean assigned;

    // Constructor
    public Volunteer(int volunteerId, String name, String taskAssigned, String priorityLevel) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.taskAssigned = taskAssigned;
        this.priorityLevel = priorityLevel;
        this.assigned = false; // not assigned by default
    }

    // Helper method to get priority as a number for comparison
    // high = 3, medium = 2, low = 1
    public int getPriorityValue() {
        if (priorityLevel.equalsIgnoreCase("high")) {
            return 3;
        } else if (priorityLevel.equalsIgnoreCase("medium")) {
            return 2;
        } else {
            return 1;
        }
    }

    // Display volunteer details
    public void display() {
        System.out.println("  ID: " + volunteerId
                + " | Name: " + name
                + " | Task: " + taskAssigned
                + " | Priority: " + priorityLevel
                + " | Assigned: " + assigned);
    }

    // Getters and Setters
    public int getVolunteerId() { return volunteerId; }
    public String getName() { return name; }
    public String getTaskAssigned() { return taskAssigned; }
    public void setTaskAssigned(String taskAssigned) { this.taskAssigned = taskAssigned; }
    public String getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
    public boolean isAssigned() { return assigned; }
    public void setAssigned(boolean assigned) { this.assigned = assigned; }
}
