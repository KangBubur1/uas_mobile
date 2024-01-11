import com.android.volley.Response
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.model.SaveSelectedBookResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("getCategory.php")
    suspend fun getBooks(): List<DataKatalogBuku>


    @FormUrlEncoded
    @POST("saveSelectedBook.php")
    suspend fun saveSelectedBook(@Field("kodeBuku") kodeBuku: String): Response<SaveSelectedBookResponse>
    // If you still want to use kodeBuku for specific book retrieval
    // @GET("getCategory.php")
    // suspend fun getBook(@Query("kodeBuku") kodeBuku: String): DataKatalogBuku
}
