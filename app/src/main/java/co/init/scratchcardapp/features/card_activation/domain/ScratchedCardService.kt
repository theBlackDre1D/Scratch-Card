package co.init.scratchcardapp.features.card_activation.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScratchedCardService {

    @GET("version")
    suspend fun activateCard(@Query("code") code: String): Response<CardActivationResponse>
}