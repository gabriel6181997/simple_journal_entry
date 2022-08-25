package com.okeicalm.simpleJournalEntry.usecase.comment

import com.okeicalm.simpleJournalEntry.entity.Comment
import com.okeicalm.simpleJournalEntry.handler.type.CommentEntryType
import com.okeicalm.simpleJournalEntry.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

data class CommentUpdateUseCaseInput(val id: Long, val incurredOn: LocalDate, val commentEntries: List<CommentEntryType>)

data class CommentUpdateUseCaseOutput(val comment: Comment)

interface CommentUpdateUseCase {
    fun call(input: CommentUpdateUseCaseInput): CommentUpdateUseCaseOutput
}

@Service
class CommentUpdateUseCaseImpl(private val commentRepository: CommentRepository): CommentUpdateUseCase {
    @Transactional
    override fun call(input: CommentUpdateUseCaseInput): CommentUpdateUseCaseOutput {
        val comment = Comment(
            id = input.id,
            incurredOn = input.incurredOn,
            commentEntries = input.commentEntries //Check type
        )

        return CommentUpdateUseCaseOutput(commentRepository.update(comment))
    }
}