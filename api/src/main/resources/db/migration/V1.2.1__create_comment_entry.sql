use simple_journal_entry_db;

create table comment_entries
(
    id          bigint not null primary key auto_increment,
    comment_id  bigint not null,
    text        text binary not null,
    journal_id  bigint not null,
    account_id  bigint not null,
    foreign key fk_journal (journal_id) references journals (id),
    foreign key fk_account (account_id) references accounts (id),
    foreign key fk_comment (comment_id) references comments (id)
);
