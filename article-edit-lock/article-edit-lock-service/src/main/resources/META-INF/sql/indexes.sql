create index IX_DF4C8557 on ArticleLock_ArticleEditLock (groupId);
create unique index IX_1DF185D0 on ArticleLock_ArticleEditLock (locked, articleId[$COLUMN_LENGTH:75$]);
create index IX_4210BFD on ArticleLock_ArticleEditLock (locked, lockTime);
create index IX_804F8EB on ArticleLock_ArticleEditLock (locked, userId);