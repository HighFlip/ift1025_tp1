public class Etudiant {
    public enum Niveau {
        BACCALAUREAT,
        MAITRISE,
        DOCTORAT;
    }

    private String prenom;
    private String nomFamille;
    private Niveau niveau;
    private Horaire horaire;

    public Etudiant(String prenom, String nomFamille, Niveau niveau) {
        this.prenom = prenom;
        this.nomFamille = nomFamille;
        this.niveau = niveau;
        this.horaire = new Horaire();
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomFamille() {
        return this.nomFamille;
    }

    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Horaire getHoraire() {
        return this.horaire;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

}
