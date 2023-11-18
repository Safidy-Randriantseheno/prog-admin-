do
$$
    begin
        if not exists(select from pg_type where typname = 'topic') then
            create type user_status as enum ('ROMANCE','COMEDY' , 'OTHER' );
        end if;
        if not exists(select from pg_type where typname = 'availability') then
                    create type sex as enum ('Disponible', 'Borowed');
                end if;
    end
$$;
create table if not exists "book"
(
    id                varchar
        constraint book_pk primary key default uuid_generate_v4(),
    book_name        varchar                  not null,
    page_number        varchar                  not null,
    release_date        date                     not null,
    topic               topic                   not null,
    availability        availability            not null,
    author_id         VARCHAR NOT NULL REFERENCES "author"("id")
);
create index if not exists book_name_index on "book" (book_name);
