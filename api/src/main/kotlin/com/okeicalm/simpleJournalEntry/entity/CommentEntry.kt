package com.okeicalm.simpleJournalEntry.entity

data class CommentEntry(
    val id: Int,
    val commentId: Long,
    val text: String,
    val accountId: Long,
    val journalId: Long,
) {
    companion object {
        fun create(
          accountId: Long,
          journalId: Long,
          text: String,
        ): CommentEntry {
            return CommentEntry(
                id = 0,
                commentId = 0,
                text = text,
                accountId = accountId,
                journalId = journalId,
            )
        }
    }
}

