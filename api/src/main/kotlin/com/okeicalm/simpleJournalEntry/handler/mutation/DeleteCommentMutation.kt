package com.okeicalm.simpleJournalEntry.handler.mutation

import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Mutation
import com.okeicalm.simpleJournalEntry.handler.type.CommentType
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentDeleteUseCase
import com.okeicalm.simpleJournalEntry.usecase.comment.CommentDeleteUseCaseInput
import org.springframework.stereotype.Component


data class DeleteCommentInput(val id: ID)

data class DeleteCommentPayload(val deletedComment: CommentType?)

@Component
class DeleteCommentMutation(private val commentDeleteUseCase: CommentDeleteUseCase): Mutation {
    fun deleteComment(input: DeleteCommentInput): DeleteCommentPayload {
        val output = commentDeleteUseCase.call(CommentDeleteUseCaseInput(id= input.id.toString().toLong()))

        val deleteCommentType = if(output.comment == null) {
            null
        } else {
            CommentType(output.comment)
        }

        return DeleteCommentPayload(deleteCommentType)
    }
}