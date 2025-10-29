package com.ro2santi.projectuts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PembeliActivity : AppCompatActivity(), MenuAdapter.OnItemClickListener {

    private lateinit var rvMenu: RecyclerView
    private lateinit var btnLogout: Button

    private var nomorMeja: String = "XX"
    private var namaPembeli: String = "Anonim"

    // Hardcoded data menu aktif dengan URL placeholder aman
    private val activeMenuList = listOf(
        MenuModel(1, "Espresso Klasik", 20000, "http://placeholder.com/1", isMenuAktif = true),
        MenuModel(2, "Latte Caramel", 35000, "http://placeholder.com/2", isMenuAktif = true),
        // Menu 3 (Teh Lychee) tidak dimasukkan karena isMenuAktif = false
        MenuModel(4, "Croissant Keju", 30000, "http://placeholder.com/4", isMenuAktif = true),
        MenuModel(5, "Frappe Coklat", 40000, "http://placeholder.com/5", isMenuAktif = true)
    )

    private val keranjang: MutableList<MenuModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembeli)

        // Ambil data
        nomorMeja = intent.getStringExtra("NOMOR_MEJA") ?: "XX"
        namaPembeli = intent.getStringExtra("NAMA_PEMBELI") ?: "Anonim"

        findViewById<TextView>(R.id.tv_pembeli_header).text = "Pesan Menu Hari Ini - Meja $nomorMeja"

        rvMenu = findViewById(R.id.rv_pembeli_menu)
        btnLogout = findViewById(R.id.btn_logout_pembeli)

        // Tampilkan menu yang aktif
        rvMenu.adapter = MenuAdapter(activeMenuList, this)

        rvMenu.layoutManager = GridLayoutManager(this, 2)

        findViewById<Button>(R.id.btn_konfirmasi_pesanan).setOnClickListener {
            if (keranjang.isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong! Silahkan pilih menu.", Toast.LENGTH_SHORT).show()
            } else {
                showKonfirmasiPesananDialog()
            }
        }

        // Logika Logout
        btnLogout.setOnClickListener {
            performLogout()
        }
    }

    override fun onItemClick(menu: MenuModel) {
        keranjang.add(menu)
        updateKeranjangRingkasan()
        Toast.makeText(this, "${menu.nama} ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
    }

    private fun updateKeranjangRingkasan() {
        val totalHarga = keranjang.sumOf { it.harga }
        val ringkasan = "Keranjang: ${keranjang.size} item, Total: Rp ${totalHarga.toRupiahFormat()}"
        findViewById<TextView>(R.id.tv_keranjang_ringkasan).text = ringkasan
    }

    private fun showKonfirmasiPesananDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_konfirmasi_pesanan, null)
        val etNamaPembeliDialog = dialogView.findViewById<EditText>(R.id.et_nama_pembeli_konfirmasi)
        val rgMetodePembayaran = dialogView.findViewById<RadioGroup>(R.id.rg_metode_pembayaran)
        val tvDetailPesanan = dialogView.findViewById<TextView>(R.id.tv_detail_pesanan)

        val totalHarga = keranjang.sumOf { it.harga }
        tvDetailPesanan.text = "Total Item: ${keranjang.size} | Total Harga: Rp ${totalHarga.toRupiahFormat()}"
        etNamaPembeliDialog.setText(namaPembeli)

        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pesanan")
            .setView(dialogView)
            .setPositiveButton("Kirim Pesanan") { dialog, _ ->
                val nama = etNamaPembeliDialog.text.toString().trim()
                val selectedId = rgMetodePembayaran.checkedRadioButtonId
                val metode = if (selectedId == R.id.rb_cash) "Cash" else "E-Wallet"

                if (nama.isEmpty()) {
                    Toast.makeText(this, "Nama Pembeli tidak boleh kosong.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                keranjang.clear()
                updateKeranjangRingkasan()

                Toast.makeText(this, "Pesanan dari Meja $nomorMeja ($nama) berhasil dikirim via $metode. Total: Rp ${totalHarga.toRupiahFormat()} (Simulasi)", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun performLogout() {
        keranjang.clear()
        Toast.makeText(this, "Sesi Meja $nomorMeja diakhiri.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ActivityLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}