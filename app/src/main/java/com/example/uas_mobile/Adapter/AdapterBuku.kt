package com.example.uas_mobile.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.Buku.SendDataBuku
import com.example.uas_mobile.Buku.ViewBuku
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class AdapterBuku(var context: Context, private var productList: List<Product>) : RecyclerView.Adapter<AdapterBuku.ImageViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_data_buku, null)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val product = productList[position]
        Picasso.get().load(product.gambarBuku).into(holder.gambarBuku)
        holder.kodeBuku.text =  "Kode Buku: ${productList[position].kodeBuku}"
        holder.judulBuku.text = productList[position].judulBuku
        holder.pengarang.text = "Pengarang: ${productList[position].pengarang}"
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menu_buku)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.edit_menu -> {
                        val bundle = Bundle()
                        bundle.putString("kodeBuku", product.kodeBuku)
                        bundle.putString("judulBuku", product.judulBuku)
                        bundle.putString("pengarang", product.pengarang )
                        bundle.putString("penerbit", product.penerbit)
                        bundle.putString("tempatTerbit", product.tempatTerbit)
                        bundle.putString("jumlahSalinan", product.jumlahSalinan )
                        bundle.putString("gambarBuku", product.gambarBuku)
                        bundle.putString("kodeKategori", product.kodeKategori)

                        val intent = Intent(context, SendDataBuku::class.java)
                        intent.putExtra("databuku", bundle)
                        context.startActivity(intent)
                    }

                    R.id.delete_menu -> {
                        MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                            .setPositiveButton("Delete"){_,_->
                                val url: String = AppConfig().IP_SERVER + "/PHP/deleteBuku.php"
                                val strReq = object : StringRequest(Method.POST,url, Response.Listener { response ->
                                    try {
                                        val jsonObj = JSONObject(response)
                                        Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, ViewBuku::class.java)
                                        context.startActivity(intent)
                                    }
                                    catch (e: JSONException) { e.printStackTrace() } },
                                    Response.ErrorListener {}) {
                                    override fun getParams(): HashMap<String,String>{
                                        val params = HashMap<String,String>()
                                        params["kodeBuku"] = product.kodeBuku
                                        return params
                                    }
                                }
                                Volley.newRequestQueue(context).add(strReq)
                            }
                            .setNegativeButton("Cancel"){_,_->}.show()
                    }

                    else -> return@setOnMenuItemClickListener false
                }
                false
            }
            //display menu
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var kodeBuku     : TextView
        var judulBuku     : TextView
        var pengarang     : TextView
        var gambarBuku   : ImageView
        var flowmenu    : ImageButton
        init {
            kodeBuku     = itemView.findViewById(R.id.txt_kodeBuku)
            judulBuku     = itemView.findViewById(R.id.txt_judulBuku)
            pengarang     = itemView.findViewById(R.id.txt_pengarang)
            gambarBuku   = itemView.findViewById(R.id.imageProduct)
            flowmenu    = itemView.findViewById(R.id.flowmenu)
        }
    }
}
