import java.time.LocalTime;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        Label title = new Label("Gestion d'horaire étudiant");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setTop(title);

        // Create TableView and Columns
        TableView<Cours> tableView = new TableView<>();

        TableColumn<Cours, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cours, String> matiereColumn = new TableColumn<>("Matière");
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        TableColumn<Cours, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        tableView.getColumns().add(numeroColumn);
        tableView.getColumns().add(matiereColumn);
        tableView.getColumns().add(creditsColumn);

        // Add mock data to the TableView
        ObservableList<Cours> coursList = FXCollections.observableArrayList();
        Horaire horaire1 = new Horaire();
        Horaire.Seance seance1 = horaire1.new Seance(Horaire.JourDeSemaine.LUNDI, LocalTime.of(9, 0),
                LocalTime.of(10, 30), Horaire.TypeDeSeance.THEORIQUE, true, LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31));
        horaire1.ajouterSeance(seance1);
        coursList.add(new Cours(101, "Mathématiques", horaire1, 3));

        Horaire horaire2 = new Horaire();
        Horaire.Seance seance2 = horaire2.new Seance(Horaire.JourDeSemaine.MARDI, LocalTime.of(11, 0),
                LocalTime.of(12, 30), Horaire.TypeDeSeance.PRATIQUE, true, LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31));
        horaire2.ajouterSeance(seance2);
        coursList.add(new Cours(102, "Physique", horaire2, 4));

        tableView.setItems(coursList);

        // Set TableView to center
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestionnaire d'horaire étudiant");
        primaryStage.show();
    }
}
