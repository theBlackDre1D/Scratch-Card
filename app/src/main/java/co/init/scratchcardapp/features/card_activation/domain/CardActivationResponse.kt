package co.init.scratchcardapp.features.card_activation.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardActivationResponse(
    @SerializedName("android") val android: String
) : Serializable