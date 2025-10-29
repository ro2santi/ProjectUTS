package com.ro2santi.projectuts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManajerActivity : AppCompatActivity(), ManajerMenuAdapter.OnSwitchChangeListener {

    private lateinit var rvMenu: RecyclerView
    private lateinit var btnLogout: Button

    // Hardcoded data
    private var menuList = mutableListOf(
        MenuModel(1, "Espresso Klasik", 20000, "url1", isMenuAktif = true),
        MenuModel(2, "Latte Caramel", 35000, "url2", isMenuAktif = true),
        MenuModel(3, "Teh Lychee Segar", 25000, "url3", isMenuAktif = false),
        MenuModel(4, "Croissant Keju", 30000, "url4", isMenuAktif = true),
        MenuModel(5, "Frappe Coklat", 40000, "url5", isMenuAktif = true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manajer)

        rvMenu = findViewById(R.id.rv_manajer_menu)
        btnLogout = findViewById(R.id.btn_logout_manajer) // Inisialisasi tombol baru
        rvMenu.layoutManager = LinearLayoutManager(this)

        loadAndDisplayMenus()

        // Logika Logout
        btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun loadAndDisplayMenus() {
        rvMenu.adapter = ManajerMenuAdapter(menuList, this)
    }

    override fun onSwitchChange(menu: MenuModel, isActive: Boolean) {
        val newStatusText = if (isActive) "aktif" else "non-aktif"
        val index = menuList.indexOfFirst { it.id == menu.id }
        if (index != -1) {
            menuList[index] = menuList[index].copy(isMenuAktif = isActive)
            rvMenu.adapter?.notifyItemChanged(index)
            Toast.makeText(this, "Simulasi: ${menu.nama} berhasil di-${newStatusText}kan.", Toast.LENGTH_SHORT).show()
        }
    }

    // FUNGSI LOGOUT
    private fun performLogout() {
        // Hapus status sesi (jika ada) dan navigasi ke ActivityLogin
        Toast.makeText(this, "Anda telah Logout.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ActivityLogin::class.java)
        // Tambahkan flag agar Activity sebelumnya (Manajer) dihapus dari stack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}