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
 * النشاط 2: ماسح المفارقة الزمنية (جواز السفر الزمني)
 */
public class VerificationActivity extends AppCompatActivity {

    // 1) تعريف عناصر الواجهة كمتغيرات (حقول الصنف)
    private TextView tvNomRs;
    private TextView tvAgeRs;
    private TextView tvMotifRs;
    private Button btnApprouverRs;
    private Button btnRefuserRs;

    // 2) البيانات المستلمة من النشاط 1
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

        // 3) ربط كل متغير بالعنصر الموافق له في الـ XML عن طريق الـ id
        tvNomRs = findViewById(R.id.tv_nom_rs);
        tvAgeRs = findViewById(R.id.tv_age_rs);
        tvMotifRs = findViewById(R.id.tv_motif_rs);
        btnApprouverRs = findViewById(R.id.btn_approuver_rs);
        btnRefuserRs = findViewById(R.id.btn_refuser_rs);

        // 4) استلام الـ Intent الذي أطلق هذا النشاط
        Intent intent = getIntent();

        // 5) رقم البطاقة (آخر 4 أرقام) من الصنف Cles
        String matricule = Cles.MATRICULE;

        // 6) قراءة البيانات بنفس المفاتيح المستعملة في النشاط 1
        nom = intent.getStringExtra(Cles.cle(matricule, Cles.NOM));
        int anneeNaissance = intent.getIntExtra(Cles.cle(matricule, Cles.NAISSANCE), 0);
        int anneeDestination = intent.getIntExtra(Cles.cle(matricule, Cles.DESTINATION), 0);
        String motif = intent.getStringExtra(Cles.cle(matricule, Cles.MOTIF));

        // 7) حساب العمر الزمني = سنة الوجهة - سنة الولادة
        int ageTemporel = anneeDestination - anneeNaissance;

        // 8) عرض ملخص البيانات في واجهة الجواز
        tvNomRs.setText("Nom : " + nom);
        tvAgeRs.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotifRs.setText("Motif : " + motif);

        // 9) الشرط: إذا كان السبب "Modification du passé" والفرق عدد أولي
        //    فإن زر "Approuver" يُعطَّل (disabled) بمجرد تحميل النشاط
        if ("Modification du passé".equals(motif) && estPremier(ageTemporel)) {
            btnApprouverRs.setEnabled(false);
        }

        // 10) زر الموافقة: القرار = true
        btnApprouverRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(true);
            }
        });

        // 11) زر الرفض: القرار = false
        btnRefuserRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(false);
            }
        });
    }

    /**
     * دالة تتحقق هل العدد أولي أم لا
     */
    private boolean estPremier(int n) {
        // 1) الأعداد الأصغر من 2 ليست أولية
        if (n < 2) {
            return false;
        }
        // 2) نختبر القواسم من 2 إلى الجذر التربيعي للعدد
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false; // 2.1) وجدنا قاسما => ليس أوليا
            }
        }
        // 3) لا يوجد أي قاسم => العدد أولي
        return true;
    }

    /**
     * دالة تفتح النشاط 3 وترسل القرار النهائي (boolean) واسم المسافر
     */
    private void lancerVerdict(boolean approuve) {
        // 1) Intent صريح نحو ResultActivity
        Intent intent = new Intent(VerificationActivity.this, ResultActivity.class);

        // 2) إرسال القرار (boolean) واسم المسافر بمفاتيح مبنية بالدالة الثابتة
        intent.putExtra(Cles.cle(Cles.MATRICULE, Cles.VERDICT), approuve);
        intent.putExtra(Cles.cle(Cles.MATRICULE, Cles.NOM), nom);

        // 3) إطلاق النشاط 3
        startActivity(intent);
    }
}