package com.okeicalm.simpleJournalEntry.entity

data class CommentEntry(
    val id: Int,
    val text: String,
    val accountId: Long,
    val journalId: Long,
) {
    companion object {
        fun create(
          side: Byte,
          accountId: Long,
          journalId: Long,
        ): CommentEntry {
            return CommentEntry(
                id = 0,
                text = "",
                accountId = accountId,
                journalId = journalId,
            )
        }
    }
}

