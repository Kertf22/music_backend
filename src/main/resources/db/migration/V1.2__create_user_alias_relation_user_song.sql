alter table songs
   add column artist_id text not null;
alter table songs
   add column created_at TIMESTAMP not null;
alter table songs
   add column updated_at TIMESTAMP not null;
alter table songs
   add column deleted_at TIMESTAMP;
alter table songs
    add column banner_image text not null;
alter table songs
    RENAME COLUMN filename TO audio_file;

ALTER TABLE "songs" ADD FOREIGN KEY ("artist_id") REFERENCES "users" ("id");