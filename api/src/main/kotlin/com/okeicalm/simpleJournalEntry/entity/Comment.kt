package com.okeicalm.simpleJournalEntry.entity

import java.time.LocalDate

data class Comment (
  val id: Long,
  val incurredOn: LocalDate,
  val commentEntries: List<CommentEntry>
) {
  companion object {
    fun create(
      incurredOn: LocalDate,
      commentEntries: List<CommentEntry>
    ): Comment {
      return Comment(
        id = 0,
        incurredOn = incurredOn,
        commentEntries = commentEntries,
      )
    }
  }
}
