package com.okeicalm.simpleJournalEntry.repository

import com.okeicalm.simpleJournalEntry.entity.Comment
import com.okeicalm.simpleJournalEntry.entity.CommentEntry
import com.okeicalm.simpleJournalEntry.infra.db.tables.CommentEntries
import com.okeicalm.simpleJournalEntry.infra.db.tables.Comments
import com.okeicalm.simpleJournalEntry.infra.db.tables.references.COMMENTS
import com.okeicalm.simpleJournalEntry.infra.db.tables.references.COMMENT_ENTRIES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

interface CommentRepository {
    fun findAll(): List<Comment>
    fun findById(id: Long): Comment?
    fun create(comment: Comment): Comment
    fun update(comment: Comment): Comment
}
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

    override fun update(comment: Comment): Comment {
        dslContext
            .update(Comments.COMMENTS)
            .set(Comments.COMMENTS.ID, comment.id)
            .set(Comments.COMMENTS.INCURRED_ON, comment.incurredOn)
            .where(Comments.COMMENTS.ID.eq(comment.id))
            .execute()

          for (ce in comment.commentEntries) {
              dslContext
                  .update(CommentEntries.COMMENT_ENTRIES)
                  .set(CommentEntries.COMMENT_ENTRIES.ID, ce.id)
                  .set(CommentEntries.COMMENT_ENTRIES.COMMENT_ID, ce.commentId)
                  .set(CommentEntries.COMMENT_ENTRIES.TEXT, ce.text)
                  .set(CommentEntries.COMMENT_ENTRIES.ACCOUNT_ID, ce.accountId)
                  .set(CommentEntries.COMMENT_ENTRIES.JOURNAL_ID, ce.journalId)
                  .where(COMMENT_ENTRIES.ID.eq(ce.id))
                  .execute()
          }

        return comment
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

