package com.okeicalm.simpleJournalEntry.usecase.comment

import com.okeicalm.simpleJournalEntry.entity.Comment
import com.okeicalm.simpleJournalEntry.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class CommentDeleteUseCaseInput(val id: Long)

data class CommentDeleteUseCaseOutput(val comment: Comment?)

interface CommentDeleteUseCase{
    fun call(input: CommentDeleteUseCaseInput): CommentDeleteUseCaseOutput
}

@Service
class CommentDeleteUseCaseImpl(private val commentRepository: CommentRepository): CommentDeleteUseCase {
    @Transactional
    override fun call(input: CommentDeleteUseCaseInput): CommentDeleteUseCaseOutput {
        val deletedComment = commentRepository.findById(input.id)?: return CommentDeleteUseCaseOutput(null)
        commentRepository.delete(input.id)
        return CommentDeleteUseCaseOutput(deletedComment)
    }
}