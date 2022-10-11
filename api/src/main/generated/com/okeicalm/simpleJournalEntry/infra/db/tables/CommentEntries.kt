/*
 * This file is generated by jOOQ.
 */
package com.okeicalm.simpleJournalEntry.infra.db.tables


import com.okeicalm.simpleJournalEntry.infra.db.SimpleJournalEntryDb
import com.okeicalm.simpleJournalEntry.infra.db.indexes.COMMENT_ENTRIES_FK_ACCOUNT
import com.okeicalm.simpleJournalEntry.infra.db.indexes.COMMENT_ENTRIES_FK_COMMENT
import com.okeicalm.simpleJournalEntry.infra.db.indexes.COMMENT_ENTRIES_FK_JOURNAL
import com.okeicalm.simpleJournalEntry.infra.db.keys.COMMENT_ENTRIES_IBFK_1
import com.okeicalm.simpleJournalEntry.infra.db.keys.COMMENT_ENTRIES_IBFK_2
import com.okeicalm.simpleJournalEntry.infra.db.keys.COMMENT_ENTRIES_IBFK_3
import com.okeicalm.simpleJournalEntry.infra.db.keys.KEY_COMMENT_ENTRIES_PRIMARY
import com.okeicalm.simpleJournalEntry.infra.db.tables.records.CommentEntriesRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row5
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class CommentEntries(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, CommentEntriesRecord>?,
    aliased: Table<CommentEntriesRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<CommentEntriesRecord>(
    alias,
    SimpleJournalEntryDb.SIMPLE_JOURNAL_ENTRY_DB,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>simple_journal_entry_db.comment_entries</code>
         */
        val COMMENT_ENTRIES: CommentEntries = CommentEntries()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<CommentEntriesRecord> = CommentEntriesRecord::class.java

    /**
     * The column <code>simple_journal_entry_db.comment_entries.id</code>.
     */
    val ID: TableField<CommentEntriesRecord, Long?> = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "")

    /**
     * The column
     * <code>simple_journal_entry_db.comment_entries.comment_id</code>.
     */
    val COMMENT_ID: TableField<CommentEntriesRecord, Long?> = createField(DSL.name("comment_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>simple_journal_entry_db.comment_entries.text</code>.
     */
    val TEXT: TableField<CommentEntriesRecord, String?> = createField(DSL.name("text"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column
     * <code>simple_journal_entry_db.comment_entries.journal_id</code>.
     */
    val JOURNAL_ID: TableField<CommentEntriesRecord, Long?> = createField(DSL.name("journal_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column
     * <code>simple_journal_entry_db.comment_entries.account_id</code>.
     */
    val ACCOUNT_ID: TableField<CommentEntriesRecord, Long?> = createField(DSL.name("account_id"), SQLDataType.BIGINT.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<CommentEntriesRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<CommentEntriesRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>simple_journal_entry_db.comment_entries</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>simple_journal_entry_db.comment_entries</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>simple_journal_entry_db.comment_entries</code> table
     * reference
     */
    constructor(): this(DSL.name("comment_entries"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, CommentEntriesRecord>): this(Internal.createPathAlias(child, key), child, key, COMMENT_ENTRIES, null)
    override fun getSchema(): Schema? = if (aliased()) null else SimpleJournalEntryDb.SIMPLE_JOURNAL_ENTRY_DB
    override fun getIndexes(): List<Index> = listOf(COMMENT_ENTRIES_FK_ACCOUNT, COMMENT_ENTRIES_FK_COMMENT, COMMENT_ENTRIES_FK_JOURNAL)
    override fun getIdentity(): Identity<CommentEntriesRecord, Long?> = super.getIdentity() as Identity<CommentEntriesRecord, Long?>
    override fun getPrimaryKey(): UniqueKey<CommentEntriesRecord> = KEY_COMMENT_ENTRIES_PRIMARY
    override fun getReferences(): List<ForeignKey<CommentEntriesRecord, *>> = listOf(COMMENT_ENTRIES_IBFK_3, COMMENT_ENTRIES_IBFK_1, COMMENT_ENTRIES_IBFK_2)

    private lateinit var _comments: Comments
    private lateinit var _journals: Journals
    private lateinit var _accounts: Accounts
    fun comments(): Comments {
        if (!this::_comments.isInitialized)
            _comments = Comments(this, COMMENT_ENTRIES_IBFK_3)

        return _comments;
    }
    fun journals(): Journals {
        if (!this::_journals.isInitialized)
            _journals = Journals(this, COMMENT_ENTRIES_IBFK_1)

        return _journals;
    }
    fun accounts(): Accounts {
        if (!this::_accounts.isInitialized)
            _accounts = Accounts(this, COMMENT_ENTRIES_IBFK_2)

        return _accounts;
    }
    override fun `as`(alias: String): CommentEntries = CommentEntries(DSL.name(alias), this)
    override fun `as`(alias: Name): CommentEntries = CommentEntries(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): CommentEntries = CommentEntries(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): CommentEntries = CommentEntries(name, null)

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row5<Long?, Long?, String?, Long?, Long?> = super.fieldsRow() as Row5<Long?, Long?, String?, Long?, Long?>
}
