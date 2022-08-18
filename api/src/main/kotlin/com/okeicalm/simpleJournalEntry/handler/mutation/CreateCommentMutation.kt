package com.okeicalm.simpleJournalEntry.handler.mutation

import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Mutation
import com.okeicalm.simpleJournalEntry.handler.type.CommentEntryType
import com.okeicalm.simpleJournalEntry.handler.type.CommentType
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentCreateUseCase
import com.okeicalm.simpleJournalEntry.usecase.commenet.CommentCreateUseCaseInput
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentEntryInputData
import org.springframework.sterotype.Component
import java.time.LocalDate

data class CreateCommentInput(val incurredOn: Int, val createCommmentEntryInput: List<CreateCommentEntryInput>)
data class CreateCommentEntryInput(val side: Int, val commentId: ID, val value: Int)

@Component
class CreateCommentMutation(private val commentCreateUseCase: CommentCreateUseCase) : Mutation {
    fun createComment(input: CreateCommentInput): CommentType {
        val commentEntryInputDatum = input.CreateCommentEntryInput.map {
            CommentEntryInputData(
                side = it.side.toByte(),
                accountID = it.accountID.toString().toLong(),
                value = it.value
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

