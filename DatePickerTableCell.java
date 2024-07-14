import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ContentDisplay;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.application.Platform;
import java.time.LocalDate;

public class DatePickerTableCell<S> extends TableCell<S, LocalDate> {
    private final DatePicker datePicker;

    public DatePickerTableCell() {
        this.datePicker = new DatePicker();
        this.datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.toString() : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string) : null;
            }
        });
        this.datePicker.setOnAction(e -> commitEdit(datePicker.getValue()));
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            datePicker.setValue(item);
            setGraphic(datePicker);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEmpty()) {
            datePicker.setValue(getItem());
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    public void commitEdit(LocalDate newValue) {
        LocalDate oldValue = getItem();
        super.commitEdit(newValue);
        Platform.runLater(() -> {
            Horaire.Seance seance = (Horaire.Seance) getTableRow().getItem();
            if (this.getTableColumn().getText().equals("Date Début")) {
                if (seance.getDateFin() != null && newValue.isAfter(seance.getDateFin())) {
                    showAlert("La date de début doit être antérieure à la date de fin.");
                    datePicker.setValue(oldValue); // Reset to old value
                    cancelEdit();
                } else {
                    seance.setDateDebut(newValue);
                }
            } else if (this.getTableColumn().getText().equals("Date Fin")) {
                if (seance.getDateDebut() != null && newValue.isBefore(seance.getDateDebut())) {
                    showAlert("La date de fin doit être postérieure à la date de début.");
                    datePicker.setValue(oldValue); // Reset to old value
                    cancelEdit();
                } else {
                    seance.setDateFin(newValue);
                }
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <S> Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> forTableColumn() {
        return param -> new DatePickerTableCell<>();
    }
}
