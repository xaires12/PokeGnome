package com.example.pokegnomego.network

import com.example.pokegnomego.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("gnomes")
    fun getGnomes(): Call<List<Gnome>>

    @GET("gnomes/{gnome_id}")
    fun getGnome(@Path("gnome_id") gnomeId: Int): Call<Gnome>

    @GET("users")
    fun getUsers(): Call<List<RankingUser>>

    @POST("visit")
    fun addVisit(@Body visit: Visit): Call<ResponseBody>

    @GET("visit/{visit_id}")
    fun getVisit(@Path("visit_id") visitId: Int): Call<Visit>

    @POST("gnomes/{gnome_id}/comment")
    fun addComment(@Path("gnome_id") gnomeId: Int, @Body comment: Comment): Call<ResponseBody>

    @GET("gnomes/{gnome_id}/comments")
    fun getGnomeComments(@Path("gnome_id") gnomeId: Int): Call<List<String>>

    @POST("register")
    fun createUser(@Body user: User): Call<ResponseBody>

    @POST("login")
    fun login(@Body user: User): Call<ResponseBody>

    @GET("users/{user_id}/visits")
    fun getUserVisits(@Path("user_id") userId: Int): Call<UserVisits>

    @GET("achievements")
    fun getAchievements(): Call<List<Achievement>>

    @GET("users/{user_id}/achievements")
    fun getUserAchievements(@Path("user_id") userId: Int): Call<UserAchievements>

    @GET("users/{user_id}/draw_gnome")
    fun drawGnomeForUser(@Path("user_id") userId: Int): Call<Gnome>
}
