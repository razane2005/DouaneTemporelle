package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 *Activité 1:El formulaire d'enregistrement mta3 el voyageur
 */
public class MainActivity extends AppCompatActivity {

    // Ta3rif les éléments mta3 el l'interface comme des variables (champs mta3 el classe)
    private EditText etNomRs;
    private EditText etNaissanceRs;
    private EditText etDestinationRs;
    private Spinner spMotifRs;
    private Button btnInspecterRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Na3mlou el affichage 3al écran el kol (ta7t el barres mta3 el système)
        WindowCompat.enableEdgeToEdge(getWindow());

        // Na3mlou lien bin el activité w el layout bel activity_main.xml
        setContentView(R.layout.activity_main);

        // Nzidou padding 9add el barres mta3 el système bch el contenu ma yet3adech ta7thom
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Kol variable nareebtouh bel élément elli yetwafa9 m3ah fil fichier XML bil ID
        etNomRs = findViewById(R.id.nom_rs);
        etNaissanceRs = findViewById(R.id.naissance_rs);
        etDestinationRs = findViewById(R.id.destination_rs);
        spMotifRs = findViewById(R.id.motif_rs);
        btnInspecterRs = findViewById(R.id.btn_inspecter_rs);

        // Waqt elli nenzlou 3al bouton nestad3iiw el fonction mta3 el traitement
        btnInspecterRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inspecterProfil();
            }
        });
    }

    /**
     *Fonction ta9ra les données, tet2akkeed menhom, w ba3d t7el el activité 2
     */
    private void inspecterProfil() {

        // Na9rou les textes mel les champs (trim bech n7ayou el espaces elli zeydin)
        String nom = etNomRs.getText().toString().trim();
        String naissanceStr = etNaissanceRs.getText().toString().trim();
        String destinationStr = etDestinationRs.getText().toString().trim();

        // 9raya mta3 el choix elli ekhtarou el user fil spinner (raison mta3 el voyage)
        String motif = spMotifRs.getSelectedItem().toString();

        // Vérification: el nom ma yelzeemch ykoun vide
        if (nom.isEmpty()) {
            etNomRs.setError("Veuillez saisir le nom");
            return; // Nokhorjou mel fonction (return) w ma n7eelouch el activité 2
        }

        // Vérification: date de naissance maa yelzemch tkoun vide
        if (naissanceStr.isEmpty()) {
            etNaissanceRs.setError("Veuillez saisir l'année de naissance");
            return;
        }

        // Vérification : l'année de destination maa yelzemch tkoun vide
        if (destinationStr.isEmpty()) {
            etDestinationRs.setError("Veuillez saisir l'année de destination");
            return;
        }

        // Conversion mta3 el textes en entiers
        int anneeNaissance = Integer.parseInt(naissanceStr);
        int anneeDestination = Integer.parseInt(destinationStr);

        // Vérification: ma tnajemch tseefer fel l'année elli 9bal el date de naissance (ya3nii el L'année de destination leezem tkoun akbeer men l'année de naissance)
        if (anneeDestination < anneeNaissance) {
            Toast.makeText(this,
                    "L'année de destination doit être supérieure à l'année de naissance",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Création mta3 Intent explicite:
        //  1er parametre = el activité courante elli hia l'activité 1 (MainActivity.this)
        //  2ème parametre = el activité 2 (VerificationActivity.class)
        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

        // Nakhdhou el matricule (a5er 4 chiffres) mel classe Cles
        String matricule = Cles.MATRICULE;

        // Nzidou les données b putExtra
        // El clés yet3amlou bel fonction statique Cles.cle(matricule, nom) w mouch maktoubin bel yedd
        intent.putExtra(Cles.cle(matricule, Cles.NOM), nom);                      // texte (String)
        intent.putExtra(Cles.cle(matricule, Cles.NAISSANCE), anneeNaissance);     // entier (Int)
        intent.putExtra(Cles.cle(matricule, Cles.DESTINATION), anneeDestination); // entier (Int)
        intent.putExtra(Cles.cle(matricule, Cles.MOTIF), motif);                  // texte (String)

        // Lancement mta3 el activité 2
        startActivity(intent);
    }
}