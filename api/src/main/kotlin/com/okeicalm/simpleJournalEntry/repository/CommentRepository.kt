package com.okeicalm.simpleJournalEntry.repository

import com.okeicalm.simpleJournalEntry.entity.Comment
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import com.okeicalm.simpleJournalEntry.infra.db.tables.Comments
import com.okeicalm.simpleJournalEntry.infra.db.tables.references.COMMENTS
import com.okeicalm.simpleJournalEntry.infra.db.tables.references.COMMENT_ENTRIES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface CommentRepository {
    fun findAll(): List<Comment>
    fun findById(id: Long): Comment?
    fun create(comment: Comment): Comment
}
data class UpdateCommentUseCaseInput(
    val id: Long,
    val incurredOn: LocalDate,
    val commentEntries: List<CommentEntry>,
)

data class UpdateCommentUseCaseOutput(val comment: Comment)

@Repository
class CommentRepositoryImpl(private val dslContext: DSLContext):CommentRepository {
    override fun findAll(): List<Comment> {
        val records = dslContext
            .select(
                COMMENTS.ID,
                COMMENTS.INCURRED_ON,
                COMMENT_ENTRIES.ID,
                COMMENT_ENTRIES.TEXT,
                COMMENT_ENTRIES.ACCOUNT_ID,
                COMMENT_ENTRIES.JOURNAL_ID,
                COMMENT_ENTRIES.COMMENT_ID
            )
            .from(COMMENTS)
            .join(COMMENT_ENTRIES)
            .on(COMMENTS.ID.eq(COMMENT_ENTRIES.COMMENT_ID))
            .fetch()

        val commentMap = records.groupBy {it[COMMENTS.ID]}

        return commentMap.map { c ->
            val commentEntries = c.value.map {  ce ->
                CommentEntry(
                    id = ce.getValue(COMMENT_ENTRIES.ID)!!.toInt(),
                    text = ce.getValue(COMMENT_ENTRIES.TEXT)!!,
                    accountId = ce.getValue(COMMENT_ENTRIES.ACCOUNT_ID)!!,
                    journalId = ce.getValue(COMMENT_ENTRIES.JOURNAL_ID)!!,
                    commentId = ce.getValue(COMMENT_ENTRIES.COMMENT_ID)!!
                )
            }
            Comment(
                id = c.key!!,
                incurredOn = c.value.first().getValue(COMMENTS.INCURRED_ON)!!,
                commentEntries = commentEntries
            )
        }
    }

    override fun findById(id: Long): Comment? {
        return dslContext
            .fetchOne(COMMENTS, COMMENTS.ID.eq(id))
            ?.into(Comment::class.java)
    }
    override fun create(comment: Comment): Comment {
        // For Comment
        val record = dslContext
            .newRecord(COMMENTS)
            .apply {
                incurredOn = comment.incurredOn
            }
        record.store()

        val commentId = record.id!!

        val commentEntryWithCommentId = comment.commentEntries.map {
            it.copy(commentId = commentId)
        }

        // For CommentEntry
        bulkInsertCommentEntry(commentEntryWithCommentId)

        val createdCommentEntries = dslContext
            .select()
            .from(COMMENT_ENTRIES)
            .where(COMMENT_ENTRIES.COMMENT_ID.eq(commentId))
            .fetch {
                CommentEntry(
                    id = it.getValue(COMMENT_ENTRIES.ID)!!.toInt(),
                    text = it.getValue(COMMENT_ENTRIES.TEXT)!!,
                    accountId = it.getValue(COMMENT_ENTRIES.ACCOUNT_ID)!!,
                    journalId = it.getValue(COMMENT_ENTRIES.JOURNAL_ID)!!,
                    commentId = it.getValue(COMMENT_ENTRIES.COMMENT_ID)!!
                )
            }
        return comment.copy(id = commentId, commentEntries = createdCommentEntries)
    }

     fun update(input: UpdateCommentUseCaseInput):UpdateCommentUseCaseOutput {
        val comment = this.findById(input.id)

        val updateComment = comment?.copy(
            id = this.id,
            incurredOn = this.incurredOn,
            commentEntries = this.listOf(CommentEntry)
        )

//        val commentErrors = validate(updateComment)
//        return if (commentErrors.isEmpty()) {
//
//        }

    }

    private fun bulkInsertCommentEntry(commentEntries: List<CommentEntry>) {
        val queries = commentEntries.map {
            dslContext.insertInto(
                COMMENT_ENTRIES,
                COMMENT_ENTRIES.COMMENT_ID,
                COMMENT_ENTRIES.TEXT,
                COMMENT_ENTRIES.ACCOUNT_ID,
                COMMENT_ENTRIES.JOURNAL_ID,
            )
                .values(it.commentId, it.text, it.accountId, it.journalId)
        }
        dslContext.batch(queries).execute()
    }
}

