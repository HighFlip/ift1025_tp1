import java.time.LocalTime;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox();
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setSpacing(30);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        Label title = new Label("Gestion d'horaire étudiant");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-font-weight: bold;");
        titleBox.getChildren().add(title);

        HBox listsContainer = new HBox();
        listsContainer.setSpacing(20);
        listsContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(listsContainer, Priority.ALWAYS);

        VBox sectionCours = new VBox();
        sectionCours.setSpacing(20);
        sectionCours.setAlignment(Pos.CENTER);
        VBox.setVgrow(sectionCours, Priority.ALWAYS);

        Button createCoursButton = new Button("Créer nouveau cours");
        TableView<Cours> tableCours = faireTableCours();

        VBox.setVgrow(tableCours, Priority.ALWAYS);
        sectionCours.getChildren().addAll(createCoursButton, tableCours);

        VBox sectionEtudiants = new VBox();
        sectionEtudiants.setSpacing(20);
        sectionEtudiants.setAlignment(Pos.CENTER);
        VBox.setVgrow(sectionEtudiants, Priority.ALWAYS);

        Button createEtudiantButton = new Button("Créer un nouvel étudiant");
        TableView<Etudiant> tableEtudiants = faireTableEtudiant();

        VBox.setVgrow(tableEtudiants, Priority.ALWAYS);
        sectionEtudiants.getChildren().addAll(createEtudiantButton, tableEtudiants);

        HBox.setHgrow(sectionCours, Priority.ALWAYS);
        HBox.setHgrow(sectionEtudiants, Priority.ALWAYS);
        tableCours.setMaxWidth(Double.MAX_VALUE);
        tableEtudiants.setMaxWidth(Double.MAX_VALUE);

        listsContainer.getChildren().addAll(sectionCours, sectionEtudiants);

        mainLayout.getChildren().addAll(titleBox, listsContainer);

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestionnaire d'horaire étudiant");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private TableView<Cours> faireTableCours() {
        TableView<Cours> tableCours = new TableView<>();

        TableColumn<Cours, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cours, String> matiereColumn = new TableColumn<>("Matière");
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        TableColumn<Cours, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        tableCours.getColumns().add(numeroColumn);
        tableCours.getColumns().add(matiereColumn);
        tableCours.getColumns().add(creditsColumn);

        ObservableList<Cours> coursList = FXCollections.observableArrayList();
        Horaire horaire1 = new Horaire();
        Horaire.Seance seance1 = horaire1.new Seance(Horaire.JourDeSemaine.LUNDI, LocalTime.of(9, 0),
                LocalTime.of(10, 30), Horaire.TypeDeSeance.THEORIQUE, true, LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31));
        horaire1.ajouterSeance(seance1);
        coursList.add(new Cours(101, "Mathématiques", horaire1, 3, Etudiant.Niveau.BACCALAUREAT));

        Horaire horaire2 = new Horaire();
        Horaire.Seance seance2 = horaire2.new Seance(Horaire.JourDeSemaine.MARDI, LocalTime.of(11, 0),
                LocalTime.of(12, 30), Horaire.TypeDeSeance.PRATIQUE, true, LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31));
        horaire2.ajouterSeance(seance2);
        coursList.add(new Cours(102, "Physique", horaire2, 4, Etudiant.Niveau.MAITRISE));

        tableCours.setItems(coursList);

        tableCours.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableCours.getSelectionModel().getSelectedItem() != null) {
                Cours selectedCours = tableCours.getSelectionModel().getSelectedItem();
                openCoursDetailWindow(selectedCours);
            }
        });

        return tableCours;
    }

    private TableView<Etudiant> faireTableEtudiant() {
        TableView<Etudiant> tableEtudiants = new TableView<>();

        TableColumn<Etudiant, String> prenomColumn = new TableColumn<>("Prénom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Etudiant, String> nomFamilleColumn = new TableColumn<>("Nom de famille");
        nomFamilleColumn.setCellValueFactory(new PropertyValueFactory<>("nomFamille"));

        TableColumn<Etudiant, Etudiant.Niveau> niveauColumn = new TableColumn<>("Niveau");
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));

        tableEtudiants.getColumns().addAll(prenomColumn, nomFamilleColumn, niveauColumn);

        ObservableList<Etudiant> listEtudiants = FXCollections.observableArrayList();
        listEtudiants.add(new Etudiant("Étu", "Diant", Etudiant.Niveau.BACCALAUREAT));
        listEtudiants.add(new Etudiant("Jean", "Bon", Etudiant.Niveau.BACCALAUREAT));
        listEtudiants.add(new Etudiant("Paul", "Iticien", Etudiant.Niveau.MAITRISE));
        listEtudiants.add(new Etudiant("Aude", "Javel", Etudiant.Niveau.DOCTORAT));

        tableEtudiants.setItems(listEtudiants);
        tableEtudiants.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableEtudiants.getSelectionModel().getSelectedItem() != null) {
                Etudiant selectedEtudiant = tableEtudiants.getSelectionModel().getSelectedItem();
                openEtudiantDetailWindow(selectedEtudiant);
            }
        });

        return tableEtudiants;
    }

    private void openCoursDetailWindow(Cours cours) {
        Stage detailStage = new Stage();
        VBox detailLayout = new VBox();
        detailLayout.setPadding(new Insets(10));
        detailLayout.setSpacing(10);

        Label numeroLabel = new Label("Numero:");
        TextField numeroField = new TextField(String.valueOf(cours.getNumero()));

        Label matiereLabel = new Label("Matière:");
        TextField matiereField = new TextField(cours.getMatiere());

        Label creditsLabel = new Label("Credits:");
        TextField creditsField = new TextField(String.valueOf(cours.getCredits()));

        // Label niveauLabel = new Label("Niveau:");
        // TextField niveauField = new TextField(String.valueOf(cours.getNiveau()));

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            cours.setNumero(Integer.parseInt(numeroField.getText()));
            cours.setMatiere(matiereField.getText());
            cours.setCredits(Integer.parseInt(creditsField.getText()));
            detailStage.close();
        });

        detailLayout.getChildren().addAll(
            numeroLabel, numeroField,
            matiereLabel, matiereField,
            creditsLabel, creditsField,
            saveButton
        );

        Scene detailScene = new Scene(detailLayout, 300, 250);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Détails du Cours");
        detailStage.show();
    }

    private void openEtudiantDetailWindow(Etudiant etudiant) {
        Stage detailStage = new Stage();
        VBox detailLayout = new VBox();
        detailLayout.setPadding(new Insets(10));
        detailLayout.setSpacing(10);

        Label detailLabel = new Label("Prénom: " + etudiant.getPrenom());
        detailLayout.getChildren().add(detailLabel);

        Scene detailScene = new Scene(detailLayout, 300, 200);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Détails de l'Étudiant");
        detailStage.show();
    }
}

