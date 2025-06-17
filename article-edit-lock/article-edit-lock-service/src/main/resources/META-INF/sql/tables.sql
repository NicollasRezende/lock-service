create table ArticleLock_ArticleEditLock (
	articleEditLockId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	articleId VARCHAR(75) null,
	userId LONG,
	userName VARCHAR(75) null,
	userFullName VARCHAR(75) null,
	locked BOOLEAN,
	lockTime DATE null
);