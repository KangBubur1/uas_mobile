package com.example.uas_mobile.DataBuku

data class DataKatalogBuku(
    var kodeBuku : String,
    var judulBuku: String,
    var pengarang: String,
    var kategori: String,
    var gambarBuku: String,
    var tanggalPinjam: String,
    var tanggalPengembalian: String
)
