package com.example.douanetemporelle;

public class Cles {

    // Akher 4 ar9am men carte étudiant (baddelhom b matricule mte3ek)
    public static final String MATRICULE = "5678";

    // Les asmé el asasiya mta3 les clés (bla préfixe)
    public static final String NOM = "NOM_VOYAGEUR";
    public static final String NAISSANCE = "ANNEE_NAISSANCE";
    public static final String DESTINATION = "ANNEE_DESTINATION";
    public static final String MOTIF = "MOTIF_VOYAGE";
    public static final String VERDICT = "VERDICT_FINAL";

    // Fonction statique tebni el clé b matricule
        // Exemple: 1234_NOM_VOYAGEUR
    public static String cle(String matricule, String nom) {
        return matricule + "_" + nom;
    }
}