package com.ro2santi.projectuts

data class PesananModel(
    val id: Int,
    val nomorMeja: String,
    val namaPembeli: String,
    // Daftar item yang dipesan (placeholder)
    val daftarItem: List<MenuModel>,
    val totalHarga: Int,
    val metodePembayaran: String, // Contoh: "Cash", "E-Wallet"
    val statusPesanan: String // Contoh: "Menunggu", "Diproses", "Selesai"
)