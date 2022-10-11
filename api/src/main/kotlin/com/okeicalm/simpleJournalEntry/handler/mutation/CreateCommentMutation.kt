package com.okeicalm.simpleJournalEntry.handler.mutation

import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Mutation
import com.okeicalm.simpleJournalEntry.handler.type.CommentEntryType
import com.okeicalm.simpleJournalEntry.handler.type.CommentType
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentCreateUseCase
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentCreateUseCaseInput
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentEntryInputData
import org.springframework.stereotype.Component
import java.time.LocalDate

data class CreateCommentInput(val incurredOn: Int, val createCommentEntryInput: List<CreateCommentEntryInput>)
data class CreateCommentEntryInput(val text: String, val accountID: ID, val journalID: ID)

@Component
class CreateCommentMutation(private val commentCreateUseCase: CommentCreateUseCase) : Mutation {
    fun createComment(input: CreateCommentInput): CommentType {
        val commentEntryInputDatum = input.createCommentEntryInput.map {
            CommentEntryInputData(
                text = it.text,
                accountID = it.accountID.toString().toLong(),
                journalID = it.journalID.toString().toLong()
            )
        }
        val inputData = CommentCreateUseCaseInput(
            incurredOn = LocalDate.now(),
            commentEntryInputDatum = commentEntryInputDatum
        )
        val outputData = commentCreateUseCase.call(inputData)

        return CommentType(
          id = ID(outputData.comment.id.toString()),
          incurredOn = outputData.comment.incurredOn,
          commentEntries = outputData.comment.commentEntries.map { CommentEntryType(it)}
        )
    }
}

