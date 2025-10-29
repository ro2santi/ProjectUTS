package com.ro2santi.projectuts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ManajerMenuAdapter(
    private val menuList: List<MenuModel>,
    // Listener untuk menangani perubahan Switch (isMenuAktif)
    private val switchListener: OnSwitchChangeListener
) : RecyclerView.Adapter<ManajerMenuAdapter.MenuManajerViewHolder>() {

    interface OnSwitchChangeListener {
        fun onSwitchChange(menu: MenuModel, isActive: Boolean)
    }

    class MenuManajerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.manajer_menu_nama)
        val idText: TextView = itemView.findViewById(R.id.manajer_menu_id)
        val gambar: ImageView = itemView.findViewById(R.id.manajer_menu_gambar)
        val switchAktif: Switch = itemView.findViewById(R.id.switch_menu_aktif)

        fun bind(menu: MenuModel) {
            nama.text = "${menu.nama} (Rp ${menu.harga.toRupiahFormat()})"
            idText.text = "ID: ${menu.id}"
            switchAktif.isChecked = menu.isMenuAktif

            // Gambar (menggunakan placeholder karena fokus pada tampilan)
            Picasso.get()
                .load(menu.urlGambar)
                .centerCrop()
                .fit()
                // .placeholder(android.R.drawable.btn_star_big_on) // Uncomment jika butuh placeholder
                .into(gambar)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): MenuManajerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_menu_manajer,
                parent, false)
        return MenuManajerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MenuManajerViewHolder, position: Int) {
        val item = menuList[position]

        holder.bind(item)

        // Penting: Reset listener sebelum mengatur status.
        // Ini menghindari pemanggilan onSwitchChange saat RecyclerView mendaur ulang view.
        holder.switchAktif.setOnCheckedChangeListener(null)
        holder.switchAktif.isChecked = item.isMenuAktif

        // Set listener baru
        holder.switchAktif.setOnCheckedChangeListener { _, isChecked ->
            // Panggil listener untuk simulasi update data
            switchListener.onSwitchChange(item, isChecked)
        }
    }

    override fun getItemCount() = menuList.size
}