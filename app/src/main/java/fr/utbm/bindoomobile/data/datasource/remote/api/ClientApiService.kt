package fr.utbm.bindoomobile.data.datasource.remote.api

import fr.utbm.bindoomobile.data.datasource.remote.dtos.CompteSFDs
import fr.utbm.bindoomobile.data.datasource.remote.dtos.PersonneInfo
import fr.utbm.bindoomobile.data.datasource.remote.dtos.ResponseObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ClientApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("login") login: String, @Field("passe") passe: String,
        @Field("version") version: Int
    ): Response<ResponseObject>

    @FormUrlEncoded
    @POST("logout/{token}")
    suspend fun logout(
        @Path("token") token: String,
        @Field("value") value: String
    ): Response<ResponseObject>

    @FormUrlEncoded
    @POST("grantee/{token}")
    suspend fun grantee(
        @Path("token") token: String,
        @Field("account") account: String = ""
    ): Response<PersonneInfo>

    @FormUrlEncoded
    @POST("accounts/{token}")
    suspend fun accounts(
        @Path("token") token: String, @Field("member") member: String = "",
        @Field("sfd") sfd: String = ""
    ): Response<CompteSFDs>

    @FormUrlEncoded
    @POST("balance/{agency}/{token}")
    suspend fun balance(
        @Path("token") token: String, @Path("agency") agency: String,
        @Field("account") account: String, @Field("member") member: String = ""
    ): Response<ResponseObject>

    @FormUrlEncoded
    @POST("statement/{agency}/{token}")
    suspend fun statement(
        @Path("token") token: String, @Path("agency") agency: String,
        @Field("account") account: String, @Field("amount") amount: Int = 0,
        @Field("member") member: String = ""
    ): Response<ResponseObject>

    @FormUrlEncoded
    @POST("transfer1/{agency}/{token}")
    suspend fun transfer(
        @Path("token") token: String, @Path("agency") agency: String,
        @Field("account") account: String, @Field("daccount") daccount: String,
        @Field("amount") amount: String, @Field("account-type") accountType: String = "C",
        @Field("daccount-type") daccountType: String = "C", @Field("member") member: String = "",
        @Field("reason") reasonCode: String="1", @Field("comment") comment: String
    ): Response<ResponseObject>

    @FormUrlEncoded
    @POST("member/infos/{agency}/{token}")
    suspend fun memberInfos(
        @Path("token") token: String, @Path("agency") agency: String,
        @Field("account") account: String,
        @Field("account-type") accountType: String = "C"
    ): Response<ResponseObject>
}