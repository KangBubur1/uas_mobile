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
import com.example.uas_mobile.Pengembalian.SendDataPengembalian
import com.example.uas_mobile.Pengembalian.ViewDataPengembalian
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Peminjaman
import com.example.uas_mobile.model.Pengembalian
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class AdapterPengembalian (var context: Context, private var kembalilist: List<Pengembalian>) : RecyclerView.Adapter<AdapterPengembalian.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_data_pengembalian, null)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val pengembalian = kembalilist[position]
        holder.kodeKembali.text = kembalilist[position].kodeKembali
        holder.tanggalKembali.text = kembalilist[position].tanggalKembali
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menu_pengembalian)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.edit_menu -> {
                        val bundle = Bundle()
                        bundle.putString("kodeKembali", pengembalian.kodeKembali)
                        bundle.putString("tanggalKembali", pengembalian.tanggalKembali)
                        bundle.putString("kodePinjam", pengembalian.kodePinjam)
                        val intent = Intent(context, SendDataPengembalian::class.java)
                        intent.putExtra("dataPengembalian", bundle)
                        context.startActivity(intent)
                    }

                    R.id.delete_menu -> {
                        MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                            .setPositiveButton("Delete"){_,_->
                                val url: String = AppConfig().IP_SERVER + "/PHP/deletePengembalian.php"
                                val strReq = object : StringRequest(
                                    Method.POST,url, Response.Listener { response ->
                                        try {
                                            val jsonObj = JSONObject(response)
                                            Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                                            val intent = Intent(context, ViewDataPengembalian::class.java)
                                            context.startActivity(intent)
                                        }
                                        catch (e: JSONException) { e.printStackTrace() } },
                                    Response.ErrorListener {}) {
                                    override fun getParams(): HashMap<String,String>{
                                        val params = HashMap<String,String>()
                                        params["kodeKembali"] = pengembalian.kodeKembali
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
        return kembalilist.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var kodeKembali   : TextView
        var tanggalKembali    : TextView

        var flowmenu     : ImageButton

        init {
            kodeKembali   = itemView.findViewById(R.id.kodeKembali)
            tanggalKembali    = itemView.findViewById(R.id.tglPengembalian)

            flowmenu    = itemView.findViewById(R.id.flowmenu)
        }
    }
}