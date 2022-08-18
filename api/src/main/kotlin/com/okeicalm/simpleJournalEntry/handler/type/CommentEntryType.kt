package com.okeicalm.simpleJournalEntry.handler.type

import com.expediagroup.graphql.generator.annotations.GraphQLIngnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expeidagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

const val commentEntryTypeGraphQLName = "CommentEntry"

@GraphQLName(commentEntryTypeGraphQLName)
data class CommentEntryType(
    val id: ID,
    val text: String,
    val value: Int,
    @GraphQLIgnore val commentId: ID,
) {
    constructor(commentEntry: CommentEntry): this(
        ID(commentEntry.id.toString()),
        commentEntry.side.toInt(), // Checking is required
        commentEntry.text, // Checking is required
        ID(commentEntry.commentId.toString())
    )

    fun account(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<AccountType> {
        return dataFetchingEnvironment.getValueFromDataLoader("CommentDataLoader", commentId)
    }
}
