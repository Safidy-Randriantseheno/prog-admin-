create table if not exists "subscribe"(
    id                varchar
            constraint subscribe_pk primary key default uuid_generate_v4(),
    subscriber_name VARCHAR(255)
);
create index if not exists subscriber_name_index on "book" (subscriber_name);