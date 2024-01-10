package com.example.uas_mobile.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
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
import com.example.uas_mobile.Peminjaman.SendDataPeminjaman
import com.example.uas_mobile.Peminjaman.ViewDataPeminjaman
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Peminjaman
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class AdapterPeminjaman (var context: Context, private var pinjamlist: List<Peminjaman>) : RecyclerView.Adapter<AdapterPeminjaman.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_data_peminjaman, null)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val peminjaman = pinjamlist[position]
        holder.kodePinjam.text = pinjamlist[position].kodePinjam
        holder.idMember.text = pinjamlist[position].idMember
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menu_peminjaman)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.edit_menu -> {
                        val bundle = Bundle()
                        bundle.putString("kodePinjam", peminjaman.kodePinjam)
                        bundle.putString("tanggalPinjam", peminjaman.tanggalPinjam)
                        bundle.putString("periodePinjam", peminjaman.periodePinjam)
                        bundle.putString("kodeBuku", peminjaman.kodeBuku)
                        bundle.putString("idMember", peminjaman.idMember)
                        val intent = Intent(context, SendDataPeminjaman::class.java)
                        intent.putExtra("dataPeminjaman", bundle)
                        context.startActivity(intent)
                    }

                    R.id.delete_menu -> {
                        MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                            .setPositiveButton("Delete"){_,_->
                                val url: String = AppConfig().IP_SERVER + "/PHP/deletePeminjaman.php"
                                val strReq = object : StringRequest(
                                    Method.POST,url, Response.Listener { response ->
                                    try {
                                        val jsonObj = JSONObject(response)
                                        Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, ViewDataPeminjaman::class.java)
                                        context.startActivity(intent)
                                    }
                                    catch (e: JSONException) { e.printStackTrace() } },
                                    Response.ErrorListener {}) {
                                    override fun getParams(): HashMap<String,String>{
                                        val params = HashMap<String,String>()
                                        params["kodePinjam"] = peminjaman.kodePinjam
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
        return pinjamlist.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var kodePinjam   : TextView
        var idMember     : TextView

        var flowmenu     : ImageButton

        init {
            kodePinjam  = itemView.findViewById(R.id.kodePinjam)
            idMember    = itemView.findViewById(R.id.idMember)
            flowmenu    = itemView.findViewById(R.id.flowmenu)
        }
    }
}