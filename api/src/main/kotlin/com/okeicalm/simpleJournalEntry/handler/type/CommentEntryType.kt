package com.okeicalm.simpleJournalEntry.handler.type

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

const val commentEntryTypeGraphQLName = "CommentEntry"

@GraphQLName(commentEntryTypeGraphQLName)
data class CommentEntryType(
    val id: ID,
    val text: String,
    val accountId: ID,
    val journalId: ID
//    @GraphQLIgnore val accountId: ID,
) {
    constructor(commentEntry: CommentEntry): this(
        ID(commentEntry.id.toString()),
        commentEntry.text,
        ID(commentEntry.accountId.toString()),
        ID(commentEntry.journalId.toString())
    )

    fun account(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<AccountType> {
        return dataFetchingEnvironment.getValueFromDataLoader("CommentDataLoader", accountId)
    }
}
