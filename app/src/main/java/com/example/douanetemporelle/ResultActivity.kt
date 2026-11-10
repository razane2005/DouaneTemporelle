package com.example.douanetemporelle

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultactivity)

        // njib el 3anaser ml layout
        val racine = findViewById<View>(R.id.main)
        val tvMessage = findViewById<TextView>(R.id.tv_message_nj)
        val btnNouveau = findViewById<Button>(R.id.btn_nouveau_nj)

        ViewCompat.setOnApplyWindowInsetsListener(racine) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // nijb el 2sem w el critere mn l'activity bdalele2 mta3 cles
        val nom = intent.getStringExtra(Cles.cle(Cles.MATRICULE, Cles.NOM)) ?: ""
        val approuve = intent.getBooleanExtra(Cles.cle(Cles.MATRICULE, Cles.VERDICT), false)

        // l'ecran ytbdel 7aseb el resultat
        if (approuve) {
            racine.setBackgroundColor(Color.parseColor("#4CAF50")) // أخضر
            tvMessage.text = "Bon voyage $nom"
        } else {
            racine.setBackgroundColor(Color.parseColor("#F44336")) // أحمر
            tvMessage.text = "$nom est en état d'arrestation temporelle"
        }

        // ki l'utlisateur yclique ala bouton retour à MainActivity et fermeture de l'écran précédent
        btnNouveau.setOnClickListener {
            val retour = Intent(this, MainActivity::class.java)
            retour.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(retour)
            finish()
        }
    }
}