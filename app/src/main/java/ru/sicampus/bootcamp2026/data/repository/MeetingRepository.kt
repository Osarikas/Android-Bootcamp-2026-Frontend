package ru.sicampus.bootcamp2026.data.repository

class MeetingRepository {
    suspend fun createMeeting(): Result<Unit> {
        return Result.success(Unit)
    }
}