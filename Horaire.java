import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un horaire.
 */
public class Horaire {
    /**
     * Enumération représentant les jours de la semaine.
     */
    public enum JourDeSemaine {
        DIMANCHE,
        LUNDI,
        MARDI,
        MERCREDI,
        JEUDI,
        VENDREDI,
        SAMEDI;
    }

    /**
     * Enumération représentant les types de séance.
     */
    public enum TypeDeSeance {
        THEORIQUE,
        PRATIQUE,
        EVALUATION;
    }

    /**
     * Classe représentant une séance.
     */
    public class Seance {
        private JourDeSemaine jour;
        private LocalTime tempsDebut;
        private LocalTime tempsFin;
        private TypeDeSeance typeDeSeance;
        private boolean repete;
        private LocalDate dateDebut;
        private LocalDate dateFin;

        /**
         * Constructeur de la classe Seance.
         *
         * @param jour         Le jour de la semaine.
         * @param tempsDebut   L'heure de début.
         * @param tempsFin     L'heure de fin.
         * @param typeDeSeance Le type de séance.
         * @param repete       Indique si la séance se répète.
         * @param dateDebut    La date de début.
         * @param dateFin      La date de fin.
         */
        public Seance(JourDeSemaine jour, LocalTime tempsDebut, LocalTime tempsFin, TypeDeSeance typeDeSeance,
                boolean repete, LocalDate dateDebut, LocalDate dateFin) {
            this.jour = jour;
            this.tempsDebut = tempsDebut;
            this.tempsFin = tempsFin;
            this.typeDeSeance = typeDeSeance;
            this.repete = repete;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
        }

        /**
         * Obtient le jour de la séance.
         *
         * @return Le jour de la séance.
         */
        public JourDeSemaine getJour() {
            return jour;
        }

        /**
         * Définit le jour de la séance.
         *
         * @param jour Le nouveau jour de la séance.
         */
        public void setJour(JourDeSemaine jour) {
            this.jour = jour;
        }

        /**
         * Obtient l'heure de début de la séance.
         *
         * @return L'heure de début de la séance.
         */
        public LocalTime getTempsDebut() {
            return tempsDebut;
        }

        /**
         * Définit l'heure de début de la séance.
         *
         * @param tempsDebut La nouvelle heure de début de la séance.
         */
        public void setTempsDebut(LocalTime tempsDebut) {
            this.tempsDebut = tempsDebut;
        }

        /**
         * Obtient l'heure de fin de la séance.
         *
         * @return L'heure de fin de la séance.
         */
        public LocalTime getTempsFin() {
            return tempsFin;
        }

        /**
         * Définit l'heure de fin de la séance.
         *
         * @param tempsFin La nouvelle heure de fin de la séance.
         */
        public void setTempsFin(LocalTime tempsFin) {
            this.tempsFin = tempsFin;
        }

        /**
         * Obtient le type de séance.
         *
         * @return Le type de séance.
         */
        public TypeDeSeance getTypeDeSeance() {
            return typeDeSeance;
        }

        /**
         * Définit le type de séance.
         *
         * @param typeDeSeance Le nouveau type de séance.
         */
        public void setTypeDeSeance(TypeDeSeance typeDeSeance) {
            this.typeDeSeance = typeDeSeance;
        }

        /**
         * Indique si la séance se répète.
         *
         * @return true si la séance se répète, sinon false.
         */
        public boolean isRepete() {
            return repete;
        }

        /**
         * Définit si la séance se répète.
         *
         * @param repete La nouvelle valeur pour la répétition de la séance.
         */
        public void setRepete(boolean repete) {
            this.repete = repete;
        }

        /**
         * Obtient la date de début de la séance.
         *
         * @return La date de début de la séance.
         */
        public LocalDate getDateDebut() {
            return dateDebut;
        }

        /**
         * Définit la date de début de la séance.
         *
         * @param dateDebut La nouvelle date de début de la séance.
         */
        public void setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
        }

        /**
         * Obtient la date de fin de la séance.
         *
         * @return La date de fin de la séance.
         */
        public LocalDate getDateFin() {
            return dateFin;
        }

        /**
         * Définit la date de fin de la séance.
         *
         * @param dateFin La nouvelle date de fin de la séance.
         */
        public void setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
        }
    }

    private List<Seance> seances;

    /**
     * Constructeur de la classe Horaire.
     */
    public Horaire() {
        this.seances = new ArrayList<>();
    }

    /**
     * Ajoute une séance à l'horaire.
     *
     * @param seance La séance à ajouter.
     */
    public void ajouterSeance(Seance seance) {
        seances.add(seance);
    }

    /**
     * Obtient la liste des séances de l'horaire.
     *
     * @return La liste des séances.
     */
    public List<Seance> getSeances() {
        return seances;
    }

    /**
     * Définit la liste des séances de l'horaire.
     *
     * @param seances La nouvelle liste des séances.
     */
    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }
}
