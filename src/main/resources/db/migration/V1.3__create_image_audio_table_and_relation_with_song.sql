
create table image (
    id text not null,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    size INT NOT NULL,
    width INT not NULL,
    height INT not NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    primary key (id)
);

create table audio (
    id text not null,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    size INT NOT NULL,
    duration INT not NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    primary key (id)
);

alter table songs
    DROP COLUMN banner_image;
alter table songs
    DROP COLUMN audio_file;

alter table songs
    add column image_id text not null;
alter table songs
    add column audio_id text not null;

ALTER TABLE "songs" ADD FOREIGN KEY ("image_id") REFERENCES "image" ("id");
ALTER TABLE "songs" ADD FOREIGN KEY ("audio_id") REFERENCES "audio" ("id");