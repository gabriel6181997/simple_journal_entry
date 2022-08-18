package com.okeicalm.simpleJournalEntry.handler.query

import com.expediagroup.graphql.spring.operations.Query
import com.okeicalm.simpleJournalEntry.handler.type.CommentType
import com.okeicalm.simpleJournalEntry.repository.CommentRepository
import org.springframework.stereotype.Component

@Component
class CommenetQuery(private val repository: CommentRepository) : Query {
    fun allComments(): List<CommentType> {
        val comments = repository.findAll()
        return comments.map { CommentType(it) } 
    }
}
