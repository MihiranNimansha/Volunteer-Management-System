package volunteer;

public class TestVolunteerSystem {

    public static void runAllTests(VolunteerCoordinationSystem testSystem) {
        // TEST 1: Register Volunteers
        printTestCase(1, "Register 6 Volunteers");
        testSystem.registerVolunteer("Mihiran Samaraweera",  "Registration Desk", "high");
        testSystem.registerVolunteer("Thasith Gajanayake",      "Crowd Control",     "medium");
        testSystem.registerVolunteer("Yonath Lakvindu",    "Logistics",         "low");
        testSystem.registerVolunteer("Gayanuka Liyanage",    "First Aid",         "low");
        testSystem.registerVolunteer("Charuka Jayasena",      "Logistics",     "medium");
        testSystem.registerVolunteer("Methasha Gunathilake",   "Crowd Control",         "high");

        // TEST 2: Display all
        printTestCase(2, "Display All Volunteers");
        testSystem.displayAllVolunteers();

        // TEST 3: Search by ID
        printTestCase(3, "Search by ID = 3");
        testSystem.searchById(3);

        // TEST 4: Search by Name
        printTestCase(4, "Search by Name = 'Mihiran Samaraweera' ");
        testSystem.searchByName("Mihiran Samaraweera");

        // TEST 5: Search non-existent
        printTestCase(5, "Search Non-Existent Name = 'Ghost User'");
        testSystem.searchByName("Ghost User");

        // TEST 6: View by priority
        printTestCase(6, "View High Priority Volunteers");
        testSystem.viewByPriority("high");

        // TEST 7: Display queue
        printTestCase(7, "Registration Queue");
        testSystem.displayQueue();

        // TEST 8: Priority-based assignment
        printTestCase(8, "Priority-Based Assignment");
        testSystem.assignByPriority();

        // TEST 9: Reassign
        printTestCase(9, "Reassign Volunteer ID=2 to 'Stage Management'");
        testSystem.reassignVolunteer(2, "Stage Management");
        testSystem.searchById(2);

        // TEST 10: Remove volunteer
        printTestCase(10, "Remove Volunteer ID=4 (Gayanuka Liyanage)");
        testSystem.removeVolunteer(4);
        testSystem.displayAllVolunteers();

        // TEST 11: Undo operations
        printTestCase(11, "Undo Last 3 Operations (Stack)");
        testSystem.displayUndoStackInfo();
        System.out.println("  >> Undo #1 - Reverse the Remove of Gayanuka Liyanage:");
        testSystem.undoLastOperation();
        System.out.println("  >> Undo #2 - Reverse the Reassign of Thasith Gajanayake:");
        testSystem.undoLastOperation();
        System.out.println("  >> Undo #3 - Reverse an Assignment:");
        testSystem.undoLastOperation();
        System.out.println("  State after 3 undos:");
        testSystem.displayAllVolunteers();

        // TEST 12: Undo on empty stack
        printTestCase(12, "Undo When Stack is Empty");
        while (testSystem.undoLastOperation_safe()) {
            // drain remaining stack
        }
        System.out.println("  Attempting undo on empty stack:");
        testSystem.undoLastOperation();

        printLine();
        printSuccess("All 12 test cases completed successfully!");
        printLine();
    }

    private static void printTestCase(int num, String desc) {
        System.out.println("\n  [TEST " + num + "] " + desc);
        System.out.println("  " + "-".repeat(44));
    }

    private static void printLine() {
        System.out.println("  ──────────────────────────────────────────────");
    }

    private static void printSuccess(String msg) {
        System.out.println("  [OK] " + msg);
    }
}
