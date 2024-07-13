public class Cours {
    private int numero;
    private String matiere;
    private Horaire horaire;
    private int credits;
    private Etudiant.Niveau niveau;

    public Cours(int numero, String matiere, Horaire horaire, int credits, Etudiant.Niveau niveau) {
        this.numero = numero;
        this.matiere = matiere;
        this.horaire = horaire;
        this.credits = credits;
        this.niveau = niveau;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Etudiant.Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Etudiant.Niveau niveau) {
        this.niveau = niveau;
    }
}
