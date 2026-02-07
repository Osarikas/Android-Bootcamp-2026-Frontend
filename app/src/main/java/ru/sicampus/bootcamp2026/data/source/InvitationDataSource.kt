package ru.sicampus.bootcamp2026.data.source

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.Network
import ru.sicampus.bootcamp2026.data.dto.AnswerInvitationRequestDTO
import ru.sicampus.bootcamp2026.data.dto.InvitationDTO
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2026.data.dto.CreateInvitationRequestDTO

class InvitationDataSource {
    suspend fun getInvitations(): Result<List<InvitationDTO>> = withContext(Dispatchers.IO){
        runCatching {
            val result = Network.client.get("api/invitation/active")
            if (result.status != HttpStatusCode.OK){
                error("Status: ${result.status}")
            }
            result.body()
        }
    }
    suspend fun answerInvitation(answerInvitationRequestDTO: AnswerInvitationRequestDTO): Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            Log.d("KTOR", "Answering invitation with data: ${Json.encodeToString(answerInvitationRequestDTO)}")
            val result = Network.client.patch("api/invitation"){
                setBody(answerInvitationRequestDTO)

            }

            if (result.status != HttpStatusCode.OK){
                error("Status: ${result.status}")
            }
        }
    }
    suspend fun createInvitation(createInvitationRequestDTO: CreateInvitationRequestDTO): Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            val result = Network.client.post("api/invitation"){
                setBody(createInvitationRequestDTO)
            }
            if (result.status != HttpStatusCode.OK){
                error("Status: ${result.status}")
            }
        }
    }
}