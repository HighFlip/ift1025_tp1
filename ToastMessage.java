import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ToastMessage {
    public static void show(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
