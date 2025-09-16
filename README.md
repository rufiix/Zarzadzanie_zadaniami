# JavaFX Task Manager

## Overview

This project is a desktop task management application developed using Java and the JavaFX framework. It provides a clean and intuitive graphical user interface (GUI) for managing daily tasks. The application supports essential features like creating, editing, and deleting tasks, as well as tracking their status and priority. All tasks are saved to a file, ensuring data persistence between sessions.

This project demonstrates a solid understanding of object-oriented programming, GUI development with JavaFX, and the implementation of the Model-View-Controller (MVC) architectural pattern.

---

## Features

* **Create, Read, Update, Delete (CRUD) Operations**:
    * **Add**: Create new tasks with a title, description, priority, and due date.
    * **Edit**: Modify the details of existing tasks.
    * **Remove**: Delete tasks from the list.
    * **View**: Display all tasks in a clear, sortable table.
* **Task Properties**: Each task includes a title, description, status (`TODO`, `IN_PROGRESS`, `DONE`), priority (`LOW`, `MEDIUM`, `HIGH`), and an optional due date.
* **Status Updates**: Easily mark a selected task as `DONE` with a single click.
* **Data Persistence**: The application uses Java serialization to save the entire list of tasks to a file, automatically loading them on the next launch.

---

## Technology Stack

* **Language**: Java
* **Framework**: JavaFX for the graphical user interface.
* **Build Tool**: Can be compiled and run directly or managed with tools like Maven or Gradle.

---

## How to Run

### Prerequisites

* Java Development Kit (JDK) 8 or higher.
* JavaFX SDK (if not included with your JDK distribution).

### Steps to Run

1.  **Compile the code**:
    Navigate to the project's source directory and compile all `.java` files.

    ```bash
    javac *.java
    ```

2.  **Run the application**:
    Execute the main application class. If you are using a separate JavaFX SDK, you may need to specify the module path.

    ```bash
    java TaskApp
    ```
    **With JavaFX modules:**
    ```bash
    java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml TaskApp
    ```

---

## Code Structure

The application follows a Model-View-Controller (MVC) pattern to separate concerns and improve maintainability.

* **`TaskApp.java`**: The main entry point for the JavaFX application. It sets up the primary stage and scene.
* **`TaskController.java`**: The Controller. It manages the user interface, handles user interactions (button clicks), and connects the view to the underlying data model.
* **`TaskManager.java`**: The primary Model component. It contains the business logic for managing the list of tasks, including adding, removing, finding, and updating them. It also handles the serialization (saving/loading) of task data.
* **`Task.java`**: A model class (POJO) that represents a single task, defining its attributes like `id`, `title`, `status`, `priority`, etc.
