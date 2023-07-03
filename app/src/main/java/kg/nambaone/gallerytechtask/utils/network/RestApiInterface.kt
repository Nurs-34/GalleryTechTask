package kg.nambaone.gallerytechtask.utils.network

import kg.nambaone.gallerytechtask.utils.network.response.PhotoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiInterface {

    @GET("/v1/curated")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<PhotoResponse>

    companion object {
        private const val BASE_URL = "https://api.pexels.com"
        private const val API_KEY = "3I0snobuoINf2J7AMsTT6gYKuaKHG2xP0keC8kUvxTxK1CLb04c0PDBV"

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .build()

                val request = original.newBuilder()
                    .addHeader("Authorization", API_KEY)
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .build()

        operator fun invoke(): RestApiInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApiInterface::class.java)
        }
    }
}
