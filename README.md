# Volunteer Management System

A console application, written in Java, for coordinating event volunteers. Volunteers can be registered, searched, assigned to tasks by priority, queued for processing, and every change can be undone.

The three core data structures (a **linked list**, a **queue** and a **stack**) are written from scratch. The project uses no `java.util` collections.

> Coursework project for **CM1602 Data Structures and Algorithms for Artificial Intelligence** (BSc (Hons) Artificial Intelligence and Data Science, IIT / Robert Gordon University).

## Features

- **Register and remove volunteers**, each with an ID, name, task and priority level (high, medium or low).
- **Search** by volunteer ID or by name.
- **Assign by priority**: high-priority volunteers are assigned first. View volunteers filtered by priority.
- **Reassign** a volunteer to a different task. The tasks are Registration Desk, Crowd Control, Logistics, First Aid, Stage Management and Parking.
- **Waiting queue**: volunteers can be processed one at a time or all at once, in first-in-first-out order.
- **Undo**: every operation is recorded on a stack, and the last operation can be reversed.
- **Built-in test run** from the menu, and sample data to try the system quickly.

## How it works

| Class | Role |
| --- | --- |
| `Volunteer` | The volunteer model: ID, name, task, priority and assigned status |
| `VolunteerLinkedList` | Singly linked list that stores all registered volunteers |
| `VolunteerQueue` | FIFO queue of volunteers waiting to be processed |
| `VolunteerStack` | LIFO stack of `OperationRecord`s, used for undo |
| `OperationRecord` | One recorded operation (type, volunteer, previous task) |
| `VolunteerCoordinationSystem` | Ties the structures together and holds the business logic |
| `Main` | Menu-driven console interface |
| `TestVolunteerSystem` | Test cases that can be run from the menu |

## Project structure

```
.
├── src/volunteer/   # All source files (package `volunteer`)
├── .gitignore
└── README.md
```

## Running it

You need a JDK installed (Java 8 or newer).

```bash
# compile into the out/ folder
javac -d out src/volunteer/*.java

# run
java -cp out volunteer.Main
```

## About the commit history

This project was finished and submitted before it was put under version control. This repository was created on **5 November 2026** from the submitted version of the code.

- Each feature commit is dated with the **last-modified time of its file** in the submitted version (14 to 29 March 2026), so the dates show when each file was last changed.
- Only the final content of each file exists, so each feature is a single commit. Earlier edits are not part of this history.
- The initial commit, this README and the merge commits are dated 5 November 2026, when they were created.
