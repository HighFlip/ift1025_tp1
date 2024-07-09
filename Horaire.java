import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Horaire {
    public enum JourDeSemaine {
        DIMANCHE,
        LUNDI,
        MARDI,
        MERCREDI,
        JEUDI,
        VENDREDI,
        SAMEDI;
    }

    public enum TypeDeSeance {
        THEORIQUE,
        PRATIQUE,
        EVALUATION;
    }

    public class Seance {
        private JourDeSemaine jour;
        private LocalTime tempsDebut;
        private LocalTime tempsFin;
        private TypeDeSeance typeDeSeance;
        private boolean repete;
        private LocalDate dateDebut;
        private LocalDate dateFin;

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

        public JourDeSemaine getJour() {
            return jour;
        }

        public void setJour(JourDeSemaine jour) {
            this.jour = jour;
        }

        public LocalTime getTempsDebut() {
            return tempsDebut;
        }

        public void setTempsDebut(LocalTime tempsDebut) {
            this.tempsDebut = tempsDebut;
        }

        public LocalTime getTempsFin() {
            return tempsFin;
        }

        public void setTempsFin(LocalTime tempsFin) {
            this.tempsFin = tempsFin;
        }

        public TypeDeSeance getTypeDeSeance() {
            return typeDeSeance;
        }

        public void setTypeDeSeance(TypeDeSeance typeDeSeance) {
            this.typeDeSeance = typeDeSeance;
        }

        public boolean getRepete() {
            return repete;
        }

        public void setRepete(boolean repete) {
            this.repete = repete;
        }

        public LocalDate getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
        }

        public LocalDate getDateFin() {
            return dateFin;
        }

        public void setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
        }

    }

    private List<Seance> seances;

    public Horaire() {
        this.seances = new ArrayList<>();
    }

    public void ajouterSeance(Seance seance) {
        seances.add(seance);
    }

    public List<Seance> getSeances() {
        return seances;
    }
}
