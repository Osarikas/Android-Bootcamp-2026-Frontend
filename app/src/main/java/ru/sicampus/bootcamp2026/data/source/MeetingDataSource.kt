package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.Network
import ru.sicampus.bootcamp2026.data.dto.CreateMeetingRequestDTO

class MeetingDataSource {
    suspend fun createMeeting(createMeetingRequestDTO: CreateMeetingRequestDTO): Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            val result = Network.client.post("api/meeting"){
                setBody(createMeetingRequestDTO)
            }
            if (result.status != HttpStatusCode.OK){
                error("Status: ${result.status}")
            }
        }
    }
}