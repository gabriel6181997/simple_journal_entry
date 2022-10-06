package com.okeicalm.simpleJournalEntry.handler.mutation

import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Mutation
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import com.okeicalm.simpleJournalEntry.handler.type.CommentType
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentUpdateUseCase
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentUpdateUseCaseInput
import java.time.LocalDate
import org.springframework.stereotype.Component

data class UpdateCommentInput(val id:ID, val incurredOn: LocalDate, val commentEntries: List<CommentEntry>)

@Component
class UpdateCommentMutation(private val commentUpdateUseCase: CommentUpdateUseCase): Mutation {
    fun updateComment(input: UpdateCommentInput):CommentType {
        val output = commentUpdateUseCase.call(
            CommentUpdateUseCaseInput(
                id = input.id.toString().toLong(),
                incurredOn = input.incurredOn,
                commentEntries = input.commentEntries
            )
        )
        return CommentType(output.comment)
    }
}