package ru.sicampus.bootcamp2026.ui.screen.inbox

import ru.sicampus.bootcamp2026.data.repository.InvitationRepository

class CreateInvitationUseCase(
    private val repo: InvitationRepository
) {
    suspend operator fun invoke(
        meetingId: Int,
        username: String,
        message: String
    ): Result<Unit> {
        return repo.createInvitation(
            meetingId = meetingId,
            message = message,
            username = username
        )
    }
}