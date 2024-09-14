package co.init.scratchcardapp.features.card_activation.domain

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ScratchedCardRemoteDataSource @Inject constructor(
    private val service: ScratchedCardService
) {

    suspend fun activateCard(cardNumber: String?) = flow<Result<CardActivationResponse>> {
        val response = service.activateCard(cardNumber)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                emit(Result.success(body))
            }
        } else {
            emit(Result.failure(Throwable(response.message())))
        }
    }.catch {
        emit(Result.failure(it))
    }
}