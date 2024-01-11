package com.example.uas_mobile.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.Member.UpdateDataMember
import com.example.uas_mobile.Member.ViewDataMember
import com.example.uas_mobile.Peminjaman.SendDataPeminjaman
import com.example.uas_mobile.Peminjaman.ViewDataPeminjaman
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Member
import com.example.uas_mobile.model.Peminjaman
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONException
import org.json.JSONObject

class AdapterMember (var context: Context, private var memberList: List<Member>) : RecyclerView.Adapter<AdapterMember.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_data_member, null)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val member= memberList[position]
        holder.idMember.text = "ID Member : ${memberList[position].idMember}"
        holder.name.text = "Nama : ${memberList[position].name}"
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menu_member)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.edit_menu -> {
                        val bundle = Bundle()
                        bundle.putString("idMember", member.idMember)
                        bundle.putString("username", member.username)
                        bundle.putString("name", member.name)
                        bundle.putString("tempatLahir", member.tempatLahir)
                        bundle.putString("tanggalLahir", member.tanggalLahir)
                        bundle.putString("noTelepon", member.noTelepon)
                        val intent = Intent(context, UpdateDataMember::class.java)
                        intent.putExtra("dataMember", bundle)
                        context.startActivity(intent)
                    }

                    R.id.delete_menu -> {
                        MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                            .setPositiveButton("Delete"){_,_->
                                val url: String = AppConfig().IP_SERVER + "/PHP/deleteMember.php"
                                val strReq = object : StringRequest(
                                    Method.POST,url, Response.Listener { response ->
                                        try {
                                            val jsonObj = JSONObject(response)
                                            Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                                            val intent = Intent(context, ViewDataMember::class.java)
                                            context.startActivity(intent)
                                        }
                                        catch (e: JSONException) { e.printStackTrace() } },
                                    Response.ErrorListener {}) {
                                    override fun getParams(): HashMap<String,String>{
                                        val params = HashMap<String,String>()
                                        params["idMember"] = member.idMember
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
        return memberList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idMember        : TextView
        var name            : TextView
        var flowmenu        : ImageButton

        init {
            idMember  = itemView.findViewById(R.id.idMember)
            name   = itemView.findViewById(R.id.namaMember)
            flowmenu    = itemView.findViewById(R.id.flowmenuMember)
        }
    }
}