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
 * النشاط 1: استمارة تسجيل المسافر عبر الزمن
 */
public class MainActivity extends AppCompatActivity {

    // 1) تعريف عناصر الواجهة كمتغيرات (حقول الصنف)
    private EditText etNomRs;
    private EditText etNaissanceRs;
    private EditText etDestinationRs;
    private Spinner spMotifRs;
    private Button btnInspecterRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2) تفعيل العرض بملء الشاشة (تحت أشرطة النظام)
        WindowCompat.enableEdgeToEdge(getWindow());

        // 3) ربط النشاط بملف التصميم activity_main.xml
        setContentView(R.layout.activity_main);

        // 4) إضافة padding بحجم أشرطة النظام حتى لا يختفي المحتوى تحتها
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 5) ربط كل متغير بالعنصر الموافق له في الـ XML عن طريق الـ id
        etNomRs = findViewById(R.id.nom_rs);
        etNaissanceRs = findViewById(R.id.naissance_rs);
        etDestinationRs = findViewById(R.id.destination_rs);
        spMotifRs = findViewById(R.id.motif_rs);
        btnInspecterRs = findViewById(R.id.btn_inspecter_rs);

        // 6) عند الضغط على الزر نستدعي دالة المعالجة
        btnInspecterRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inspecterProfil();
            }
        });
    }

    /**
     * دالة تقرأ البيانات، تتحقق منها، ثم تفتح النشاط 2
     */
    private void inspecterProfil() {

        // 1) قراءة النصوص من الحقول (trim لحذف الفراغات الزائدة)
        String nom = etNomRs.getText().toString().trim();
        String naissanceStr = etNaissanceRs.getText().toString().trim();
        String destinationStr = etDestinationRs.getText().toString().trim();

        // 2) قراءة العنصر المختار في القائمة المنسدلة (سبب السفر)
        String motif = spMotifRs.getSelectedItem().toString();

        // 3) التحقق: الاسم لا يجب أن يكون فارغا
        if (nom.isEmpty()) {
            etNomRs.setError("Veuillez saisir le nom");
            return; // 3.1) نخرج من الدالة ولا نفتح النشاط 2
        }

        // 4) التحقق: سنة الولادة لا يجب أن تكون فارغة
        if (naissanceStr.isEmpty()) {
            etNaissanceRs.setError("Veuillez saisir l'année de naissance");
            return;
        }

        // 5) التحقق: سنة الوجهة لا يجب أن تكون فارغة
        if (destinationStr.isEmpty()) {
            etDestinationRs.setError("Veuillez saisir l'année de destination");
            return;
        }

        // 6) تحويل النصوص إلى أعداد صحيحة (int)
        int anneeNaissance = Integer.parseInt(naissanceStr);
        int anneeDestination = Integer.parseInt(destinationStr);

        // 7) تحقق منطقي: لا يمكن أن يسافر الشخص إلى سنة قبل ولادته
        if (anneeDestination < anneeNaissance) {
            Toast.makeText(this,
                    "L'année de destination doit être supérieure à l'année de naissance",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // 8) إنشاء Intent صريح (Explicit Intent):
        //    المعامل الأول = النشاط الحالي (MainActivity.this)
        //    المعامل الثاني = النشاط الهدف (VerificationActivity.class)
        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

        // 9) نأخذ رقم البطاقة (آخر 4 أرقام) من الصنف Cles
        String matricule = Cles.MATRICULE;

        // 10) إضافة البيانات بواسطة putExtra
        //     المفاتيح تُبنى بالدالة الثابتة Cles.cle(matricule, nom) وليست مكتوبة يدويا
        intent.putExtra(Cles.cle(matricule, Cles.NOM), nom);                      // 10.1) نص
        intent.putExtra(Cles.cle(matricule, Cles.NAISSANCE), anneeNaissance);     // 10.2) عدد صحيح
        intent.putExtra(Cles.cle(matricule, Cles.DESTINATION), anneeDestination); // 10.3) عدد صحيح
        intent.putExtra(Cles.cle(matricule, Cles.MOTIF), motif);                  // 10.4) نص

        // 11) إطلاق النشاط 2
        startActivity(intent);
    }
}