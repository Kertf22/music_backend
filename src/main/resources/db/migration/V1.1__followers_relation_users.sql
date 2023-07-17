

alter table users
    add column artist_name varchar(255) not null;
alter table users
    add column name varchar(255) not null;

create table follows (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    following_user_id text not null,
    followed_user_id text not null,
    created_at TIMESTAMP,
    deleted_at TIMESTAMP
);

ALTER TABLE "follows" ADD FOREIGN KEY ("following_user_id") REFERENCES "users" ("id");

ALTER TABLE "follows" ADD FOREIGN KEY ("followed_user_id") REFERENCES "users" ("id");
