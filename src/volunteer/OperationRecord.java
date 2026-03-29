package volunteer;

// OperationRecord - stores details of an action so it can be undone
// Pushed onto the Stack every time an add/remove/assign happens
public class OperationRecord {

    // Types of operations that can be undone
    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String ASSIGN = "ASSIGN";

    private String operationType;  // ex: ADD, REMOVE, ASSIGN
    private Volunteer volunteer;   // the volunteer involved
    private String previousTask;   // used when undoing an ASSIGN

    // Constructor for ADD and REMOVE operations
    public OperationRecord(String operationType, Volunteer volunteer) {
        this.operationType = operationType;
        this.volunteer = volunteer;
        this.previousTask = null;
    }

    // Constructor for ASSIGN operation (stores previous task for undo)
    public OperationRecord(String operationType, Volunteer volunteer, String previousTask) {
        this.operationType = operationType;
        this.volunteer = volunteer;
        this.previousTask = previousTask;
    }

    // Getters
    public String getOperationType() { return operationType; }
    public Volunteer getVolunteer() { return volunteer; }
    public String getPreviousTask() { return previousTask; }
}
