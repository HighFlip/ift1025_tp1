import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale pour la gestion d'horaire étudiant.
 */
public class Main extends Application {
    private TableView<Cours> tableCours;
    private TableView<Etudiant> tableEtudiants;

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
        createCoursButton.setOnAction(e -> openCreateCoursWindow());
        tableCours = faireTableCours();

        VBox.setVgrow(tableCours, Priority.ALWAYS);
        sectionCours.getChildren().addAll(createCoursButton, tableCours);

        VBox sectionEtudiants = new VBox();
        sectionEtudiants.setSpacing(20);
        sectionEtudiants.setAlignment(Pos.CENTER);
        VBox.setVgrow(sectionEtudiants, Priority.ALWAYS);

        Button createEtudiantButton = new Button("Créer un nouvel étudiant");
        createEtudiantButton.setOnAction(e -> openCreateEtudiantWindow());
        tableEtudiants = faireTableEtudiant();

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

    /**
     * Crée la table des cours.
     *
     * @return TableView des cours.
     */
    private TableView<Cours> faireTableCours() {
        TableView<Cours> tableCours = new TableView<>();

        TableColumn<Cours, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cours, String> matiereColumn = new TableColumn<>("Matière");
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        TableColumn<Cours, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        tableCours.getColumns().addAll(numeroColumn, matiereColumn, creditsColumn);

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

    /**
     * Crée la table des étudiants.
     *
     * @return TableView des étudiants.
     */
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
        listEtudiants.add(new Etudiant("Étu", "Diant", Etudiant.Niveau.BACCALAUREAT, new Horaire(), new ArrayList<>()));
        listEtudiants.add(new Etudiant("Jean", "Bon", Etudiant.Niveau.BACCALAUREAT, new Horaire(), new ArrayList<>()));
        listEtudiants.add(new Etudiant("Paul", "Iticien", Etudiant.Niveau.MAITRISE, new Horaire(), new ArrayList<>()));
        listEtudiants.add(new Etudiant("Aude", "Javel", Etudiant.Niveau.DOCTORAT, new Horaire(), new ArrayList<>()));

        tableEtudiants.setItems(listEtudiants);
        tableEtudiants.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableEtudiants.getSelectionModel().getSelectedItem() != null) {
                Etudiant selectedEtudiant = tableEtudiants.getSelectionModel().getSelectedItem();
                openEtudiantDetailWindow(selectedEtudiant);
            }
        });

        return tableEtudiants;
    }

    /**
     * Ouvre la fenêtre de détails d'un cours.
     *
     * @param cours Le cours à afficher.
     */
    private void openCoursDetailWindow(Cours cours) {
        Stage detailStage = new Stage();
        VBox detailLayout = new VBox();
        detailLayout.setPadding(new Insets(10));
        detailLayout.setSpacing(10);

        HBox row1 = new HBox();
        row1.setSpacing(10);
        Label numeroLabel = new Label("Numero:");
        TextField numeroField = new TextField(String.valueOf(cours.getNumero()));
        Label matiereLabel = new Label("Matière:");
        TextField matiereField = new TextField(cours.getMatiere());
        row1.getChildren().addAll(numeroLabel, numeroField, matiereLabel, matiereField);

        HBox row2 = new HBox();
        row2.setSpacing(10);
        Label creditsLabel = new Label("Credits:");
        TextField creditsField = new TextField(String.valueOf(cours.getCredits()));
        Label niveauLabel = new Label("Niveau:");
        ComboBox<Etudiant.Niveau> niveauComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Etudiant.Niveau.values()));
        niveauComboBox.setValue(cours.getNiveau());
        row2.getChildren().addAll(creditsLabel, creditsField, niveauLabel, niveauComboBox);

        Label seancesTitle = new Label("Séances:");
        seancesTitle.setFont(new Font("Arial", 16));
        seancesTitle.setStyle("-fx-font-weight: bold;");

        TableView<Horaire.Seance> tableSeances = new TableView<>();
        tableSeances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableSeances.setEditable(true);

        ObservableList<Horaire.Seance> seanceList = FXCollections.observableArrayList(cours.getHoraire().getSeances());
        tableSeances.setItems(seanceList);

        TableColumn<Horaire.Seance, Horaire.JourDeSemaine> jourColumn = new TableColumn<>("Jour");
        jourColumn.setCellValueFactory(new PropertyValueFactory<>("jour"));
        jourColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.JourDeSemaine.values())));
        jourColumn.setOnEditCommit(event -> event.getRowValue().setJour(event.getNewValue()));

        TableColumn<Horaire.Seance, LocalTime> heureDebutColumn = new TableColumn<>("Heure Début");
        heureDebutColumn.setCellValueFactory(new PropertyValueFactory<>("tempsDebut"));
        heureDebutColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        heureDebutColumn.setOnEditCommit(event -> event.getRowValue().setTempsDebut(event.getNewValue()));

        TableColumn<Horaire.Seance, LocalTime> heureFinColumn = new TableColumn<>("Heure Fin");
        heureFinColumn.setCellValueFactory(new PropertyValueFactory<>("tempsFin"));
        heureFinColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        heureFinColumn.setOnEditCommit(event -> event.getRowValue().setTempsFin(event.getNewValue()));

        TableColumn<Horaire.Seance, Horaire.TypeDeSeance> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeDeSeance"));
        typeColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.TypeDeSeance.values())));
        typeColumn.setOnEditCommit(event -> event.getRowValue().setTypeDeSeance(event.getNewValue()));

        TableColumn<Horaire.Seance, Boolean> repeteColumn = new TableColumn<>("Répète");
        repeteColumn.setCellValueFactory(new PropertyValueFactory<>("repete"));
        repeteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(repeteColumn));
        repeteColumn.setOnEditCommit(event -> event.getRowValue().setRepete(event.getNewValue()));

        TableColumn<Horaire.Seance, LocalDate> dateDebutColumn = new TableColumn<>("Date Début");
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateDebutColumn.setCellFactory(DatePickerTableCell.forTableColumn());
        dateDebutColumn.setOnEditCommit(event -> {
            Horaire.Seance seance = event.getRowValue();
            if (seance.getDateFin() != null && event.getNewValue().isAfter(seance.getDateFin())) {
                showAlert("La date de début doit être antérieure à la date de fin.");
                tableSeances.refresh();
            } else {
                seance.setDateDebut(event.getNewValue());
            }
        });

        TableColumn<Horaire.Seance, LocalDate> dateFinColumn = new TableColumn<>("Date Fin");
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        dateFinColumn.setCellFactory(DatePickerTableCell.forTableColumn());
        dateFinColumn.setOnEditCommit(event -> {
            Horaire.Seance seance = event.getRowValue();
            if (seance.getDateDebut() != null && event.getNewValue().isBefore(seance.getDateDebut())) {
                showAlert("La date de fin doit être postérieure à la date de début.");
                tableSeances.refresh();
            } else {
                seance.setDateFin(event.getNewValue());
            }
        });

        tableSeances.getColumns().addAll(jourColumn, heureDebutColumn, heureFinColumn, typeColumn, repeteColumn,
                dateDebutColumn, dateFinColumn);

        Button addSeanceButton = new Button("Ajouter une séance");
        addSeanceButton.setOnAction(e -> openAddSeanceDialog(seanceList));

        VBox seancesBox = new VBox();
        seancesBox.setSpacing(10);
        seancesBox.getChildren().addAll(seancesTitle, tableSeances, addSeanceButton);

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            cours.setNumero(Integer.parseInt(numeroField.getText()));
            cours.setMatiere(matiereField.getText());
            cours.setCredits(Integer.parseInt(creditsField.getText()));
            cours.setNiveau(niveauComboBox.getValue());
            cours.getHoraire().setSeances(new ArrayList<>(seanceList)); // Save seances
            tableCours.refresh();
            detailStage.close();
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> {
            tableCours.getItems().remove(cours);
            detailStage.close();
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> detailStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(saveButton, deleteButton, cancelButton);

        detailLayout.getChildren().addAll(row1, row2, seancesBox, buttonBox);

        Scene detailScene = new Scene(detailLayout, 800, 600);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Détails du Cours");
        detailStage.show();
    }

    /**
     * Ouvre la fenêtre pour créer un nouveau cours.
     */
    private void openCreateCoursWindow() {
        Stage createStage = new Stage();
        VBox createLayout = new VBox();
        createLayout.setPadding(new Insets(10));
        createLayout.setSpacing(10);

        Label numeroLabel = new Label("Numero:");
        TextField numeroField = new TextField();

        Label matiereLabel = new Label("Matière:");
        TextField matiereField = new TextField();

        Label creditsLabel = new Label("Credits:");
        TextField creditsField = new TextField();

        Label niveauLabel = new Label("Niveau:");
        ComboBox<Etudiant.Niveau> niveauComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Etudiant.Niveau.values()));

        Label seancesTitle = new Label("Séances:");
        seancesTitle.setFont(new Font("Arial", 16));
        seancesTitle.setStyle("-fx-font-weight: bold;");

        TableView<Horaire.Seance> tableSeances = new TableView<>();
        tableSeances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableSeances.setEditable(true);

        TableColumn<Horaire.Seance, Horaire.JourDeSemaine> jourColumn = new TableColumn<>("Jour");
        jourColumn.setCellValueFactory(new PropertyValueFactory<>("jour"));
        jourColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.JourDeSemaine.values())));

        TableColumn<Horaire.Seance, LocalTime> heureDebutColumn = new TableColumn<>("Heure Début");
        heureDebutColumn.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        heureDebutColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        TableColumn<Horaire.Seance, LocalTime> heureFinColumn = new TableColumn<>("Heure Fin");
        heureFinColumn.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        heureFinColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        TableColumn<Horaire.Seance, Horaire.TypeDeSeance> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.TypeDeSeance.values())));

        TableColumn<Horaire.Seance, Boolean> repeteColumn = new TableColumn<>("Répète");
        repeteColumn.setCellValueFactory(new PropertyValueFactory<>("repete"));
        repeteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(repeteColumn));

        TableColumn<Horaire.Seance, LocalDate> dateDebutColumn = new TableColumn<>("Date Début");
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateDebutColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        TableColumn<Horaire.Seance, LocalDate> dateFinColumn = new TableColumn<>("Date Fin");
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        dateFinColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        tableSeances.getColumns().addAll(jourColumn, heureDebutColumn, heureFinColumn, typeColumn, repeteColumn,
                dateDebutColumn, dateFinColumn);

        ObservableList<Horaire.Seance> seanceList = FXCollections.observableArrayList();
        tableSeances.setItems(seanceList);

        VBox seancesBox = new VBox();
        seancesBox.setSpacing(10);
        seancesBox.getChildren().addAll(seancesTitle, tableSeances);

        Button addSeanceButton = new Button("Ajouter une séance");
        addSeanceButton.setOnAction(e -> openAddSeanceDialog(seanceList));

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(numeroField.getText());
                String matiere = matiereField.getText();
                int credits = Integer.parseInt(creditsField.getText());
                Etudiant.Niveau niveau = niveauComboBox.getValue();

                if (matiere.isEmpty() || niveau == null) {
                    showAlert("Tous les champs doivent être remplis.");
                    return;
                }

                Horaire horaire = new Horaire();
                horaire.setSeances(new ArrayList<>(seanceList));

                Cours newCours = new Cours(numero, matiere, horaire, credits, niveau);
                tableCours.getItems().add(newCours);
                tableCours.refresh();
                createStage.close();
            } catch (NumberFormatException ex) {
                showAlert("Les champs 'Numero' et 'Credits' doivent être des nombres.");
            }
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> createStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        createLayout.getChildren().addAll(
                numeroLabel, numeroField,
                matiereLabel, matiereField,
                creditsLabel, creditsField,
                niveauLabel, niveauComboBox,
                seancesBox,
                addSeanceButton,
                buttonBox);

        Scene createScene = new Scene(createLayout, 600, 600);
        createStage.setScene(createScene);
        createStage.setTitle("Créer un Nouveau Cours");
        createStage.show();
    }

    /**
     * Ouvre la fenêtre pour créer un nouvel étudiant.
     */
    private void openCreateEtudiantWindow() {
        Stage createStage = new Stage();
        VBox createLayout = new VBox();
        createLayout.setPadding(new Insets(10));
        createLayout.setSpacing(10);

        Label prenomLabel = new Label("Prénom:");
        TextField prenomField = new TextField();

        Label nomFamilleLabel = new Label("Nom de Famille:");
        TextField nomFamilleField = new TextField();

        Label niveauLabel = new Label("Niveau:");
        ComboBox<Etudiant.Niveau> niveauComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Etudiant.Niveau.values()));

        Horaire defaultHoraire = new Horaire();

        List<Cours> defaultCoursList = new ArrayList<>();

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            String prenom = prenomField.getText();
            String nomFamille = nomFamilleField.getText();
            Etudiant.Niveau niveau = niveauComboBox.getValue();

            if (prenom.isEmpty() || nomFamille.isEmpty() || niveau == null) {
                showAlert("Tous les champs doivent être remplis.");
                return;
            }

            Etudiant newEtudiant = new Etudiant(prenom, nomFamille, niveau, defaultHoraire, defaultCoursList);
            tableEtudiants.getItems().add(newEtudiant);
            tableEtudiants.refresh();
            createStage.close();
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> createStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        createLayout.getChildren().addAll(
                prenomLabel, prenomField,
                nomFamilleLabel, nomFamilleField,
                niveauLabel, niveauComboBox,
                buttonBox);

        Scene createScene = new Scene(createLayout, 400, 300);
        createStage.setScene(createScene);
        createStage.setTitle("Créer un Nouvel Étudiant");
        createStage.show();
    }

    /**
     * Ouvre la fenêtre de détails d'un étudiant.
     *
     * @param etudiant L'étudiant à afficher.
     */
    private void openEtudiantDetailWindow(Etudiant etudiant) {
        Stage detailStage = new Stage();
        VBox detailLayout = new VBox();
        detailLayout.setPadding(new Insets(10));
        detailLayout.setSpacing(10);

        Label prenomLabel = new Label("Prénom:");
        TextField prenomField = new TextField(etudiant.getPrenom());

        Label nomFamilleLabel = new Label("Nom de Famille:");
        TextField nomFamilleField = new TextField(etudiant.getNomFamille());

        Label niveauLabel = new Label("Niveau:");
        ComboBox<Etudiant.Niveau> niveauComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Etudiant.Niveau.values()));
        niveauComboBox.setValue(etudiant.getNiveau());

        Label coursTitle = new Label("Cours:");
        coursTitle.setFont(new Font("Arial", 16));
        coursTitle.setStyle("-fx-font-weight: bold;");

        TableView<Cours> tableCoursEtudiant = new TableView<>();
        tableCoursEtudiant.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableCoursEtudiant.setEditable(true);

        TableColumn<Cours, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cours, String> matiereColumn = new TableColumn<>("Matière");
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        TableColumn<Cours, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        tableCoursEtudiant.getColumns().addAll(numeroColumn, matiereColumn, creditsColumn);
        tableCoursEtudiant.setItems(FXCollections.observableArrayList(etudiant.getCours()));

        Label seancesTitle = new Label("Séances:");
        seancesTitle.setFont(new Font("Arial", 16));
        seancesTitle.setStyle("-fx-font-weight: bold;");

        TableView<Horaire.Seance> tableSeances = new TableView<>();
        tableSeances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableSeances.setEditable(true);

        TableColumn<Horaire.Seance, Horaire.JourDeSemaine> jourColumn = new TableColumn<>("Jour");
        jourColumn.setCellValueFactory(new PropertyValueFactory<>("jour"));
        jourColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.JourDeSemaine.values())));

        TableColumn<Horaire.Seance, LocalTime> heureDebutColumn = new TableColumn<>("Heure Début");
        heureDebutColumn.setCellValueFactory(new PropertyValueFactory<>("tempsDebut"));
        heureDebutColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        TableColumn<Horaire.Seance, LocalTime> heureFinColumn = new TableColumn<>("Heure Fin");
        heureFinColumn.setCellValueFactory(new PropertyValueFactory<>("tempsFin"));
        heureFinColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        TableColumn<Horaire.Seance, Horaire.TypeDeSeance> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeDeSeance"));
        typeColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Horaire.TypeDeSeance.values())));

        TableColumn<Horaire.Seance, Boolean> repeteColumn = new TableColumn<>("Répète");
        repeteColumn.setCellValueFactory(new PropertyValueFactory<>("repete"));
        repeteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(repeteColumn));

        TableColumn<Horaire.Seance, LocalDate> dateDebutColumn = new TableColumn<>("Date Début");
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateDebutColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        TableColumn<Horaire.Seance, LocalDate> dateFinColumn = new TableColumn<>("Date Fin");
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        dateFinColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        tableSeances.getColumns().addAll(jourColumn, heureDebutColumn, heureFinColumn, typeColumn, repeteColumn,
                dateDebutColumn, dateFinColumn);
        ObservableList<Horaire.Seance> seancesList = FXCollections.observableArrayList();
        etudiant.getCours().forEach(cours -> seancesList.addAll(cours.getHoraire().getSeances()));
        tableSeances.setItems(seancesList);

        Button addCoursButton = new Button("Ajouter un cours");
        addCoursButton.setOnAction(e -> {
            openAddCoursDialog(etudiant);
            tableCoursEtudiant.setItems(FXCollections.observableArrayList(etudiant.getCours()));
            seancesList.setAll();
            etudiant.getCours().forEach(cours -> seancesList.addAll(cours.getHoraire().getSeances()));
        });

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            etudiant.setPrenom(prenomField.getText());
            etudiant.setNomFamille(nomFamilleField.getText());
            etudiant.setNiveau(niveauComboBox.getValue());
            tableEtudiants.refresh();
            detailStage.close();
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> detailStage.close());

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> {
            tableEtudiants.getItems().remove(etudiant);
            detailStage.close();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(saveButton, deleteButton, cancelButton);

        detailLayout.getChildren().addAll(
                prenomLabel, prenomField,
                nomFamilleLabel, nomFamilleField,
                niveauLabel, niveauComboBox,
                coursTitle, tableCoursEtudiant,
                seancesTitle, tableSeances,
                addCoursButton,
                buttonBox);

        Scene detailScene = new Scene(detailLayout, 600, 600);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Détails de l'Étudiant");
        detailStage.show();
    }

    /**
     * Ouvre la fenêtre de dialogue pour ajouter un cours à un étudiant.
     *
     * @param etudiant L'étudiant à qui ajouter le cours.
     */
    private void openAddCoursDialog(Etudiant etudiant) {
        Dialog<Cours> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un Cours");

        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        VBox dialogLayout = new VBox();
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setSpacing(10);

        Label coursLabel = new Label("Sélectionnez un cours:");

        TableView<Cours> coursTableView = new TableView<>();
        coursTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        coursTableView
                .setItems(FXCollections.observableArrayList(tableCours.getItems().filtered(cours -> cours != null)));

        TableColumn<Cours, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cours, String> matiereColumn = new TableColumn<>("Matière");
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        TableColumn<Cours, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        TableColumn<Cours, Etudiant.Niveau> niveauColumn = new TableColumn<>("Niveau");
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));

        coursTableView.getColumns().addAll(numeroColumn, matiereColumn, creditsColumn, niveauColumn);

        Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            Cours selectedCours = coursTableView.getSelectionModel().getSelectedItem();
            if (selectedCours == null) {
                showAlert("Vous devez sélectionner un cours.");
                event.consume();
                return;
            }
            if (!selectedCours.getNiveau().equals(etudiant.getNiveau())) {
                showAlert("Le niveau du cours doit correspondre au niveau de l'étudiant.");
                event.consume();
                return;
            }
            for (Horaire.Seance newSeance : selectedCours.getHoraire().getSeances()) {
                for (Cours existingCours : etudiant.getCours()) {
                    for (Horaire.Seance existingSeance : existingCours.getHoraire().getSeances()) {
                        if (seancesOverlap(newSeance, existingSeance)) {
                            showAlert("Les séances du cours ne doivent pas chevaucher les séances existantes.");
                            event.consume();
                            return;
                        }
                    }
                }
            }
            etudiant.getCours().add(selectedCours);
            tableEtudiants.refresh();
        });

        dialogLayout.getChildren().addAll(coursLabel, coursTableView);
        dialog.getDialogPane().setContent(dialogLayout);
        dialog.showAndWait();
    }

    /**
     * Vérifie si deux séances se chevauchent.
     *
     * @param seance1 La première séance.
     * @param seance2 La deuxième séance.
     * @return true si les séances se chevauchent, false sinon.
     */
    private boolean seancesOverlap(Horaire.Seance seance1, Horaire.Seance seance2) {
        if (seance1.getJour() != seance2.getJour()) {
            return false;
        }
        if (seance1.getTempsDebut().isBefore(seance2.getTempsFin())
                && seance1.getTempsFin().isAfter(seance2.getTempsDebut())) {
            return true;
        }
        return false;
    }

    /**
     * Ouvre la fenêtre de dialogue pour ajouter une séance à un cours.
     *
     * @param seanceList La liste des séances du cours.
     */
    private void openAddSeanceDialog(ObservableList<Horaire.Seance> seanceList) {
        Dialog<Horaire.Seance> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une Séance");

        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        VBox dialogLayout = new VBox();
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setSpacing(10);

        ComboBox<Horaire.JourDeSemaine> jourComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Horaire.JourDeSemaine.values()));
        DatePicker dateDebutPicker = new DatePicker();
        DatePicker dateFinPicker = new DatePicker();
        TextField heureDebutField = new TextField();
        TextField heureFinField = new TextField();
        ComboBox<Horaire.TypeDeSeance> typeComboBox = new ComboBox<>(
                FXCollections.observableArrayList(Horaire.TypeDeSeance.values()));
        CheckBox repeteCheckBox = new CheckBox("Répète");

        dialogLayout.getChildren().addAll(
                new Label("Jour:"), jourComboBox,
                new Label("Date Début:"), dateDebutPicker,
                new Label("Date Fin:"), dateFinPicker,
                new Label("Heure Début:"), heureDebutField,
                new Label("Heure Fin:"), heureFinField,
                new Label("Type de Séance:"), typeComboBox,
                repeteCheckBox);

        dialog.getDialogPane().setContent(dialogLayout);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            // Validate input fields
            if (jourComboBox.getValue() == null || dateDebutPicker.getValue() == null
                    || dateFinPicker.getValue() == null ||
                    heureDebutField.getText().isEmpty() || heureFinField.getText().isEmpty()
                    || typeComboBox.getValue() == null) {
                showAlert("Tous les champs doivent être remplis.");
                event.consume(); // Prevent dialog from closing
                return;
            }
            LocalTime heureDebut;
            LocalTime heureFin;
            try {
                heureDebut = LocalTime.parse(heureDebutField.getText());
                heureFin = LocalTime.parse(heureFinField.getText());
            } catch (Exception e) {
                showAlert("Les heures doivent être au format HH:MM.");
                event.consume(); // Prevent dialog from closing
                return;
            }
            if (dateDebutPicker.getValue().isAfter(dateFinPicker.getValue())) {
                showAlert("La date de début doit être antérieure à la date de fin.");
                event.consume(); // Prevent dialog from closing
                return;
            }
            Horaire.Seance newSeance = new Horaire().new Seance(
                    jourComboBox.getValue(),
                    heureDebut,
                    heureFin,
                    typeComboBox.getValue(),
                    repeteCheckBox.isSelected(),
                    dateDebutPicker.getValue(),
                    dateFinPicker.getValue());
            seanceList.add(newSeance);
        });

        dialog.showAndWait();
    }

    /**
     * Affiche une alerte avec le message spécifié.
     *
     * @param message Le message à afficher dans l'alerte.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class LocalTimeStringConverter extends StringConverter<LocalTime> {
        @Override
        public String toString(LocalTime time) {
            return time != null ? time.toString() : "";
        }

        @Override
        public LocalTime fromString(String string) {
            return string != null && !string.isEmpty() ? LocalTime.parse(string) : null;
        }
    }

    public class LocalDateStringConverter extends StringConverter<LocalDate> {
        @Override
        public String toString(LocalDate date) {
            return date != null ? date.toString() : "";
        }

        @Override
        public LocalDate fromString(String string) {
            return string != null && !string.isEmpty() ? LocalDate.parse(string) : null;
        }
    }

}

    
