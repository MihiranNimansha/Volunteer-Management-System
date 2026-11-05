package volunteer;

import java.util.Scanner;

//------- Smart Event Volunteer Coordination System -------

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static VolunteerCoordinationSystem system = new VolunteerCoordinationSystem();

    public static void main(String[] args) {
        printBanner();
        
        // Pre-populate with sample data for demonstration
        loadSampleData();
        
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt("  Enter your choice: ");

            switch (choice) {
                case 1:
                    menuRegisterVolunteer();
                    break;
                case 2:
                    menuRemoveVolunteer();
                    break;
                case 3:
                    menuSearchVolunteer();
                    break;
                case 4:
                    menuDisplayAll();
                    break;
                case 5:
                    menuPriorityMenu();
                    break;
                case 6:
                    menuQueueMenu();
                    break;
                case 7:
                    menuUndoMenu();
                    break;
                case 8:
                    menuRunTests();
                    break;
                case 0:
                    running = false;
                    printLine();
                    System.out.println("  Thank you for using the Volunteer Coordination System.");
                    printLine();
                    break;
                default:
                    printError("Invalid option. Please choose from the menu.");
            }
        }

        scanner.close();
    }


    // MENUS
    private static void printMainMenu() {
        printLine();
        System.out.println("  MAIN MENU");
        printLine();
        System.out.println("  [1]  Register a Volunteer");
        System.out.println("  [2]  Remove a Volunteer");
        System.out.println("  [3]  Search for a Volunteer");
        System.out.println("  [4]  Display All Volunteers");
        System.out.println("  [5]  Priority Management");
        System.out.println("  [6]  Queue Management");
        System.out.println("  [7]  Undo Last Operation");
        System.out.println("  [8]  Run Test Cases");
        System.out.println("  [0]  Exit");
        printLine();
    }

    // Register Volunteer
    private static void menuRegisterVolunteer() {
        printHeader("REGISTER VOLUNTEER");

        System.out.print("  Enter Name           : ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            printError("Name cannot be empty. Returning to menu.");
            return;
        }

        System.out.println("  Available Tasks:");
        System.out.println("    [1] Registration Desk");
        System.out.println("    [2] Crowd Control");
        System.out.println("    [3] Logistics");
        System.out.println("    [4] First Aid");
        System.out.println("    [5] Stage Management");
        System.out.println("    [6] Other");
        System.out.print("  Choose task number or type custom task name: ");
        String taskInput = scanner.nextLine().trim();
        String task = resolveTask(taskInput);

        System.out.println("  Priority Level:");
        System.out.println("    [1] High");
        System.out.println("    [2] Medium");
        System.out.println("    [3] Low");
        int priChoice = readInt("  Choose priority (1-3): ");
        String priority = resolvePriority(priChoice);

        if (priority == null) {
            printError("Invalid priority. Defaulting to Medium.");
            priority = "medium";
        }

        system.registerVolunteer(name, task, priority);
        printSuccess("Volunteer registered successfully!");
    }

    // Remove Volunteer
    private static void menuRemoveVolunteer() {
        printHeader("REMOVE VOLUNTEER");
        int id = readInt("  Enter Volunteer ID to remove: ");
        system.removeVolunteer(id);
    }

    // Search Volunteer
    private static void menuSearchVolunteer() {
        printHeader("SEARCH VOLUNTEER");
        System.out.println("  Search by:");
        System.out.println("  [1]  Volunteer ID");
        System.out.println("  [2]  Volunteer Name");
        int choice = readInt("  Choose: ");

        if (choice == 1) {
            int id = readInt("  Enter Volunteer ID: ");
            system.searchById(id);
        } else if (choice == 2) {
            System.out.print("  Enter Volunteer Name: ");
            String name = scanner.nextLine().trim();
            system.searchByName(name);
        } else {
            printError("Invalid choice.");
        }
    }

    // Display All
    private static void menuDisplayAll() {
        printHeader("ALL REGISTERED VOLUNTEERS");
        system.displayAllVolunteers();
    }

    // Priority Sub Menu
    private static void menuPriorityMenu() {
        printHeader("PRIORITY MANAGEMENT");
        System.out.println("  [1]  Assign Volunteers by Priority (High > Medium > Low)");
        System.out.println("  [2]  View Volunteers by Priority Level");
        System.out.println("  [3]  Reassign a Volunteer to a New Task");
        System.out.println("  [0]  Back to Main Menu");
        int choice = readInt("  Choose: ");

        switch (choice) {
            case 1:
                printHeader("PRIORITY-BASED ASSIGNMENT");
                system.assignByPriority();
                break;
            case 2:
                System.out.println("  Filter by: [1] High  [2] Medium  [3] Low");
                int priChoice = readInt("  Choose: ");
                String priority = resolvePriority(priChoice);
                if (priority != null) {
                    printHeader("VOLUNTEERS - " + priority.toUpperCase() + " PRIORITY");
                    system.viewByPriority(priority);
                } else {
                    printError("Invalid choice.");
                }
                break;
            case 3:
                int id = readInt("  Enter Volunteer ID to reassign: ");
                System.out.print("  Enter new Task: ");
                String newTask = scanner.nextLine().trim();
                system.reassignVolunteer(id, newTask);
                break;
            case 0:
                break;
            default:
                printError("Invalid option.");
        }
    }

    // Queue Sub Menu
    private static void menuQueueMenu() {
        printHeader("QUEUE MANAGEMENT");
        System.out.println("  The Queue holds volunteers in registration order.");
        System.out.println("  Volunteers are processed from front to back.");
        System.out.println();
        System.out.println("  [1]  View Registration Queue");
        System.out.println("  [2]  Process Next Volunteer from Queue");
        System.out.println("  [3]  Process All Waiting Volunteers");
        System.out.println("  [0]  Back to Main Menu");
        int choice = readInt("  Choose: ");

        switch (choice) {
            case 1:
                printHeader("REGISTRATION QUEUE");
                system.displayQueue();
                break;
            case 2:
                printHeader("PROCESS NEXT (FIFO)");
                system.processNextFromQueue();
                break;
            case 3:
                printHeader("PROCESS ALL WAITING VOLUNTEERS");
                system.processAllFromQueue();
                break;
            case 0:
                break;
            default:
                printError("Invalid option.");
        }
    }

    // Undo Sub Menu
    private static void menuUndoMenu() {
        printHeader("UNDO LAST OPERATION");
        system.displayUndoStackInfo();
        System.out.println("  [1]  Undo Last Operation");
        System.out.println("  [0]  Back to Main Menu");
        int choice = readInt("  Choose: ");

        if (choice == 1) {
            system.undoLastOperation();
        }
    }

    //-------- TEST CASES - runs automatically with no user input needed ---------


    private static void menuRunTests() {
        printHeader("RUNNING ALL TEST CASES");
        System.out.println("  Creating a separate test instance with sample data...\n");

        VolunteerCoordinationSystem testSystem = new VolunteerCoordinationSystem();
        TestVolunteerSystem.runAllTests(testSystem);
    }


    // Load sample data for demonstration
    private static void loadSampleData() {
        system.registerVolunteer("Mihiran Samaraweera", "Registration Desk", "high", true);
        system.registerVolunteer("Thasith Gajanayake", "Crowd Control", "medium", true);
        system.registerVolunteer("Yonath Lakvindu", "Logistics", "low", true);
        system.registerVolunteer("Gayanuka Liyanage", "First Aid", "medium", true);
        system.registerVolunteer("Methasha Gunathilake", "Crowd Control", "high", true);
    }

    // HELPER METHODS
    private static String resolveTask(String input) {
        switch (input) {
            case "1": return "Registration Desk";
            case "2": return "Crowd Control";
            case "3": return "Logistics";
            case "4": return "First Aid";
            case "5": return "Stage Management";
            case "6": return "Parking";
            default:  return input.isEmpty() ? "Unassigned" : input;
        }
    }

    private static String resolvePriority(int choice) {
        if (choice == 1) return "high";
        if (choice == 2) return "medium";
        if (choice == 3) return "low";
        return null;
    }

    // Read an integer safely - handles bad non-numeric input
    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // UI FORMATTING

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║     SMART EVENT VOLUNTEER COORDINATION SYSTEM        ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  [INFO] System ready. Sample data loaded.\n");
    }

    private static void printHeader(String title) {
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────┐");
        System.out.printf( "  │  %-42s│%n", title);
        System.out.println("  └──────────────────────────────────────────┘");
    }

    private static void printLine() {
        System.out.println("  ──────────────────────────────────────────────");
    }

    private static void printTestCase(int num, String desc) {
        System.out.println("\n  [TEST " + num + "] " + desc);
        System.out.println("  " + "-".repeat(44));
    }

    private static void printSuccess(String msg) {
        System.out.println("  [OK] " + msg);
    }

    private static void printError(String msg) {
        System.out.println("  [!]  " + msg);
    }
}
