package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activity 2: Scanner mta3 el passport temporel
 */
public class VerificationActivity extends AppCompatActivity {

    // Ta3rif les éléments mta3 el l'interface m3aa les variables (champs mta3 el classe)
    private TextView tvNomRs;
    private TextView tvAgeRs;
    private TextView tvMotifRs;
    private Button btnApprouverRs;
    private Button btnRefuserRs;

    // Les données elli jeyin mel activité 1
    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nareebtou kol variable bel élément mte3haa fil activity_verification.xml bil id
        tvNomRs = findViewById(R.id.tv_nom_rs);
        tvAgeRs = findViewById(R.id.tv_age_rs);
        tvMotifRs = findViewById(R.id.tv_motif_rs);
        btnApprouverRs = findViewById(R.id.btn_approuver_rs);
        btnRefuserRs = findViewById(R.id.btn_refuser_rs);

        // Nesta9belou el Intent elli 7al el activité hedhi
        Intent intent = getIntent();

        // Neekhdhou el numéro mta3 el carte (ee5er 4 chiffres) mel classe Cles
        String matricule = Cles.MATRICULE;

        // Na9rou les données bnafs el clés elli esta3malnehom fil activité 1
        nom = intent.getStringExtra(Cles.cle(matricule, Cles.NOM));
        int anneeNaissance = intent.getIntExtra(Cles.cle(matricule, Cles.NAISSANCE), 0);
        int anneeDestination = intent.getIntExtra(Cles.cle(matricule, Cles.DESTINATION), 0);
        String motif = intent.getStringExtra(Cles.cle(matricule, Cles.MOTIF));

        // N7asbou el âge temporel elli howa el l'année de destination - l'année de naissance
        int ageTemporel = anneeDestination - anneeNaissance;

        // Na3eemlou affichage lel résumé mta3 les données fil passeport
        tvNomRs.setText("Nom : " + nom);
        tvAgeRs.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotifRs.setText("Motif : " + motif);

        // Condition: idhee ken el motif 5tarou el user "Modification du passé" w el far9 un nombre premier
        //    na3emlou désactivation le bouton "Approuver" mel loul ki tet7aal l'activité
        if ("Modification du passé".equals(motif) && estPremier(ageTemporel)) {
            btnApprouverRs.setEnabled(false);
        }

        // Bouton "Approuver": el décision = true
        btnApprouverRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(true);
            }
        });

        // Bouton "Refuser": el décision = false
        btnRefuserRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(false);
            }
        });
    }

    /**
     * Fonction tet2akkeed ken el nombre premier walla lee
     */
    private boolean estPremier(int n) {
        // El nombre el as8ar mel 2 mouch premier
        if (n < 2) {
            return false;
        }
        // Nejarbou el diviseurs mel 2 lin el racine carré mta3 el nombre
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false; // L9ina diviseur donc el nombre mouch premier
            }
        }
        // Ma l9iina 7atta diviseur => el nombre premier
        return true;
    }

    /**
     *Fonction t7el el activité 3 w tab3ath el décision finale (boolean) w esm el voyageur
     */
    private void lancerVerdict(boolean approuve) {
        // Intent explicite lel ResultActivity
        Intent intent = new Intent(VerificationActivity.this, ResultActivity.class);

        // Nab3thou el décision (boolean) w esm el voyageur b clés ma3mouliin bel fonction statique
        intent.putExtra(Cles.cle(Cles.MATRICULE, Cles.VERDICT), approuve);
        intent.putExtra(Cles.cle(Cles.MATRICULE, Cles.NOM), nom);

        // Nlanciw el activité 3
        startActivity(intent);
    }
}