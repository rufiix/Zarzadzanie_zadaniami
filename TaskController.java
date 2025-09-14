import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class TaskController {
    private TaskManager manager = new TaskManager();
    private TableView<Task> table;
    private ObservableList<Task> data;

    public Parent getView() {
        BorderPane root = new BorderPane();

        // Tabela
        table = new TableView<>();
        data = FXCollections.observableArrayList(manager.listAll());
        table.setItems(data);

        TableColumn<Task, String> colTitle = new TableColumn<>("Tytuł");
        colTitle.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));

        TableColumn<Task, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus().toString()));

        TableColumn<Task, String> colPriority = new TableColumn<>("Priorytet");
        colPriority.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPriority().toString()));

        TableColumn<Task, String> colDue = new TableColumn<>("Termin");
        colDue.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getDueDate() == null ? "-" : c.getValue().getDueDate().toString()));

        table.getColumns().addAll(colTitle, colStatus, colPriority, colDue);

        root.setCenter(table);

        // Przyciski
        Button btnAdd = new Button("Dodaj");
        btnAdd.setOnAction(e -> addTask());

        Button btnEdit = new Button("Edytuj");
        btnEdit.setOnAction(e -> editTask());

        Button btnRemove = new Button("Usuń");
        btnRemove.setOnAction(e -> removeTask());

        Button btnDone = new Button("Oznacz jako DONE");
        btnDone.setOnAction(e -> markDone());

        HBox buttons = new HBox(10, btnAdd, btnEdit, btnRemove, btnDone);
        buttons.setPadding(new Insets(10));
        root.setBottom(buttons);

        return root;
    }

    private void addTask() {
        Dialog<Task> dialog = createTaskDialog(null);
        dialog.showAndWait().ifPresent(task -> {
            manager.addTask(task);
            data.setAll(manager.listAll());
        });
    }

    private void editTask() {
        Task selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Dialog<Task> dialog = createTaskDialog(selected);
        dialog.showAndWait().ifPresent(task -> {
            data.setAll(manager.listAll());
        });
    }

    private void removeTask() {
        Task selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        manager.removeTask(selected.getId());
        data.setAll(manager.listAll());
    }

    private void markDone() {
        Task selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        manager.markDone(selected.getId());
        data.setAll(manager.listAll());
    }

    // Dialog do dodawania/edycji
    private Dialog<Task> createTaskDialog(Task toEdit) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle(toEdit == null ? "Dodaj zadanie" : "Edytuj zadanie");

        Label lTitle = new Label("Tytuł:");
        TextField fTitle = new TextField(toEdit == null ? "" : toEdit.getTitle());

        Label lDesc = new Label("Opis:");
        TextField fDesc = new TextField(toEdit == null ? "" : toEdit.getDescription());

        Label lPriority = new Label("Priorytet:");
        ComboBox<Task.Priority> cbPriority = new ComboBox<>();
        cbPriority.getItems().addAll(Task.Priority.values());
        cbPriority.setValue(toEdit == null ? Task.Priority.MEDIUM : toEdit.getPriority());

        Label lDate = new Label("Termin:");
        DatePicker dpDate = new DatePicker(toEdit == null ? null : toEdit.getDueDate());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(lTitle, 0, 0); grid.add(fTitle, 1, 0);
        grid.add(lDesc, 0, 1); grid.add(fDesc, 1, 1);
        grid.add(lPriority, 0, 2); grid.add(cbPriority, 1, 2);
        grid.add(lDate, 0, 3); grid.add(dpDate, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                if (toEdit == null) {
                    return new Task(fTitle.getText(), fDesc.getText(), cbPriority.getValue(), dpDate.getValue());
                } else {
                    toEdit.setTitle(fTitle.getText());
                    toEdit.setDescription(fDesc.getText());
                    toEdit.setPriority(cbPriority.getValue());
                    toEdit.setDueDate(dpDate.getValue());
                    return toEdit;
                }
            }
            return null;
        });

        return dialog;
    }
}
