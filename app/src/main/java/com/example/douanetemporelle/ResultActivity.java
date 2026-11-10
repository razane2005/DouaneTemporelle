package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultactivity);

        // njib el 3anaser ml layout
        final View racine = findViewById(R.id.main);
        TextView tvMessage = findViewById(R.id.tv_message_nj);
        Button btnNouveau = findViewById(R.id.btn_nouveau_nj);

        ViewCompat.setOnApplyWindowInsetsListener(racine, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // nijb el 2sem w el critere mn l'activity bdalele2 mta3 cles
        String nom = getIntent().getStringExtra(Cles.cle(Cles.MATRICULE, Cles.NOM));
        if (nom == null) {
            nom = "";
        }
        boolean approuve = getIntent().getBooleanExtra(Cles.cle(Cles.MATRICULE, Cles.VERDICT), false);

        // l'ecran ytbdel 7aseb el resultat
        if (approuve) {
            racine.setBackgroundColor(Color.parseColor("#4CAF50")); // vert
            tvMessage.setText("Bon voyage " + nom);
        } else {
            racine.setBackgroundColor(Color.parseColor("#F44336")); // rouge
            tvMessage.setText(nom + " est en état d'arrestation temporelle");
        }

        // ki l'utlisateur yclique ala bouton retour à MainActivity et fermeture de l'écran précédent
        btnNouveau.setOnClickListener(view -> {
            Intent retour = new Intent(ResultActivity.this, MainActivity.class);
            retour.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(retour);
            finish();
        });
    }
}