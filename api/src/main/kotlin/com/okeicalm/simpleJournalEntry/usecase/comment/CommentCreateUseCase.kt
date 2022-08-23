package com.okeicalm.simpleJournalEntry.usecase.comment;

import com.okeicalm.simpleJournalEntry.entity.Comment
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import com.okeicalm.simpleJournalEntry.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

data class CommentEntryInputData(val text: String, val accountID: Long, val journalID:Long )
data class CommentCreateUseCaseInput(val incurredOn: LocalDate, val commentEntryInputDatum: List<CommentEntryInputData>)
data class CommentCreateUseCaseOutput(val comment: Comment)

interface CommentCreateUseCase {
    fun call(input: CommentCreateUseCaseInput): CommentCreateUseCaseOutput
}

@Service
class CommentCreateUseCaseImpl(
  private val commentRepository: CommentRepository,
) : CommentCreateUseCase {
    @Transactional
    override fun call(input: CommentCreateUseCaseInput): CommentCreateUseCaseOutput {
        val commentEntries = input.commentEntryInputDatum.map {
            CommentEntry.create(
                text = it.text,
                accountId = it.accountID,
                journalId = it.journalID
            )
        }
        val comment = Comment.create(
            incurredOn = input.incurredOn,
            commentEntries = commentEntries,
        )

        val createdComment = commentRepository.create(comment)

        return CommentCreateUseCaseOutput(createdComment)
    }
}

