import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskApp extends Application {
    @Override
    public void start(Stage stage) {
        TaskController controller = new TaskController();
        Scene scene = new Scene(controller.getView(), 800, 600);

        stage.setTitle("Menedżer zadań");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
