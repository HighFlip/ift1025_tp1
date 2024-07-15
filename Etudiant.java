import java.util.List;

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
    private List<Cours> cours;

    public Etudiant(String prenom, String nomFamille, Niveau niveau, Horaire horaire, List<Cours> cours) {
        this.prenom = prenom;
        this.nomFamille = nomFamille;
        this.niveau = niveau;
        this.horaire = horaire;
        this.cours = cours;
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

    public List<Cours> getCours() {
        return this.cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }
}
