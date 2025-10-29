package com.ro2santi.projectuts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso // Pastikan library Picasso telah ditambahkan di build.gradle

class MenuAdapter(
    private val menuList: List<MenuModel>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(menu: MenuModel)
    }

    class MenuViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.menu_nama)
        val harga: TextView = itemView.findViewById(R.id.menu_harga)
        val gambar: ImageView = itemView.findViewById(R.id.menu_gambar)

        fun bind(menu: MenuModel) {
            nama.text = menu.nama
            harga.text = "Rp ${menu.harga.toRupiahFormat()}"

            // Gunakan Picasso untuk loading gambar (jika URL valid)
            Picasso.get()
                .load(menu.urlGambar)
                .centerCrop()
                .fit()
                // Gunakan placeholder jika Anda tidak menggunakan URL gambar nyata
                // .placeholder(android.R.drawable.btn_star_big_on)
                .into(gambar)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_menu,
                parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder, position: Int) {
        val item = menuList[position]

        holder.bind(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun getItemCount() = menuList.size
}