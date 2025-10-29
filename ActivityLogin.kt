package com.ro2santi.projectuts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etInputUtama = findViewById<EditText>(R.id.et_input_utama)
        val etInputKedua = findViewById<EditText>(R.id.et_input_kedua)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val inputUtama = etInputUtama.text.toString().trim()
            val inputKedua = etInputKedua.text.toString().trim()

            if (inputUtama.isEmpty() || inputKedua.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- SIMULASI NAVIGASI & PERAN (Fokus Tampilan) ---
            if (inputUtama.contains("manajer", true) || inputUtama.contains("kasir", true)) {
                // Manajer/Kasir Login
                val role = if (inputUtama.contains("manajer", true)) "Manajer" else "Kasir"
                Toast.makeText(this, "Login berhasil sebagai $role! (Simulasi)", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ManajerActivity::class.java)
                intent.putExtra("USER_ROLE", role)
                startActivity(intent)
                finish()
            }
            else if (inputUtama.matches(Regex("^\\d+$")) && inputUtama.isNotEmpty() && inputKedua.isNotEmpty()) {
                // Pembeli Login (Nomor Meja)
                Toast.makeText(this, "Login Pembeli Meja $inputUtama berhasil! (Simulasi)", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PembeliActivity::class.java)
                intent.putExtra("NOMOR_MEJA", inputUtama)
                intent.putExtra("NAMA_PEMBELI", inputKedua)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Kredensial atau Nomor Meja tidak valid.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}