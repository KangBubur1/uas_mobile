package com.example.uas_mobile

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private var context: Context, arraylist_data: ArrayList<Product>) : BaseAdapter() {
    private var arraylist_data: ArrayList<Product> = arraylist_data
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arraylist_data.size
    }

    override fun getItem(position: Int): Any {
        return arraylist_data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vi: View? = convertView
        if (convertView == null)
            vi = inflater.inflate(R.layout.single_data, null)

        val txt_caption     : TextView  = vi!!.findViewById(R.id.txt_caption)
        val imageProduct          : ImageView = vi.findViewById(R.id.imageProduct)
        val txt_kodebuku     : TextView    = vi.findViewById(R.id.txt_kodebuku)
        val txt_kategori    : TextView  = vi.findViewById(R.id.txt_kategori)
        val txt_pengarang   : TextView  = vi.findViewById(R.id.txt_pengarang )
        val txt_penerbit   : TextView  = vi.findViewById(R.id.txt_penerbit )
        val txt_tempatterbit   : TextView  = vi.findViewById(R.id.txt_tempatterbit )
        val txt_jumlahsalinan   : TextView  = vi.findViewById(R.id.txt_jumlahsalinan )
//        val button_check_out          : Button    =  vi.findViewById(R.id.btn_booking)
        val product = arraylist_data[position]

        txt_caption.text  = product.judulBuku
        txt_kodebuku.text = product.kodeBuku
        txt_kategori.text = product.kodeKategori
        txt_pengarang.text = product.pengarang
        txt_penerbit.text = product.penerbit
        txt_tempatterbit.text = product.tempatTerbit
        txt_jumlahsalinan.text = product.jumlahSalinan

        Glide.with(vi.context)
            .load(product.gambarBuku)
            .into(imageProduct)
//
//        button_check_out.setOnClickListener(){
//            val intent = Intent(context, Check_out_activity::class.java)
//            intent.putExtra("nama_pemesan",booking_model.nama_pemesan)
//            intent.putExtra("kode_booking", booking_model.kode_booking)
//            intent.putExtra("kode_kamar", booking_model.kode_kamar)
//            intent.putExtra("waktu_check_in", booking_model.waktu_check_in)
//            intent.putExtra("waktu_check_out", booking_model.waktu_check_out)
//            context.startActivity(intent)
//        }
        return vi
    }
}
