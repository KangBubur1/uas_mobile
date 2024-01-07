import com.example.uas_mobile.DataBuku.DataKatalogBuku
import retrofit2.http.GET

interface ApiService {

    @GET("getCategory.php")
    suspend fun getBooks(): List<DataKatalogBuku>

    // If you still want to use kodeBuku for specific book retrieval
    // @GET("getCategory.php")
    // suspend fun getBook(@Query("kodeBuku") kodeBuku: String): DataKatalogBuku
}
