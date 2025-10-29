package com.ro2santi.projectuts

data class MenuModel(
    val id: Int,
    val nama: String,
    val harga: Int,
    val urlGambar: String,
    // Atribut untuk Aktivasi Menu Hari Ini oleh Manajer
    val isMenuAktif: Boolean = true
)