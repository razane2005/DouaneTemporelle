package com.example.douanetemporelle;

public class Cles {

    // رقم البطاقة: آخر 4 أرقام من بطاقة الطالب (غيّره برقمك)
    public static final String MATRICULE = "1234";

    // الأسماء الأساسية للمفاتيح (بدون البادئة)
    public static final String NOM = "NOM_VOYAGEUR";
    public static final String NAISSANCE = "ANNEE_NAISSANCE";
    public static final String DESTINATION = "ANNEE_DESTINATION";
    public static final String MOTIF = "MOTIF_VOYAGE";
    public static final String VERDICT = "VERDICT_FINAL";

    // دالة ثابتة تبني المفتاح من رقم البطاقة (مثال: 1234_NOM_VOYAGEUR)
    public static String cle(String matricule, String nom) {
        return matricule + "_" + nom;
    }
}