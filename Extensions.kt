package com.ro2santi.projectuts

import java.text.NumberFormat
import java.util.Locale

// Extension function untuk format Rupiah
fun Int.toRupiahFormat(): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getNumberInstance(localeID)
    return formatRupiah.format(this)
}