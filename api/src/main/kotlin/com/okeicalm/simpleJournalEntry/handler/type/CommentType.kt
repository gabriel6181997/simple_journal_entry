package com.okeicalm.simpleJournalEntry.handler.type

import com.expediagroup.graphql.generator.annoations.GraphQLName
import com.expeidagroup.graphql.generator.scalars.ID
import com.okeicalm.simpleJournalEntry.entity.Comment
import java.time.LocalDate

const val commentTypeGraphQLName = "Comment"

@GraphQLName(commentTypeGraphQLName)
data class CommentType(
    val id: ID,
    val incurredOn: LocalDate,
    val commentEntries: List<CommentEntryType>,
) {
    constructor(comment: Comment) : this(
        id = ID(comment.id),
        incurredOn = comment.incurredOn,
        comment.commentEntries.map { CommentEntryType(it) }
    )
}
