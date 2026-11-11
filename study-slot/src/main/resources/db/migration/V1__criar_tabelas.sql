create table users (
    id bigint generated always as identity primary key,
    name varchar(120) not null,
    email varchar(160) not null,
    created_at timestamptz not null default now(),
    constraint users_email_unique unique (email)
);

create table room (
    id bigint generated always as identity primary key,
    name varchar(120) not null,
    capacity int not null,
    active boolean not null default true,
    constraint room_capacity_check
        check (capacity > 0)
);

create table reservation (
    id bigint generated always as identity primary key,
    user_id bigint not null references users (id),
    room_id bigint not null references room (id),
    status varchar(20) not null default 'ACTIVE',
    starts_at timestamptz not null,
    ends_at timestamptz not null,
    created_at timestamptz not null default now(),
    constraint reservation_status_check
        check (status in ('ACTIVE', 'CANCELLED', 'COMPLETED' )),
    constraint reservation_time_check
        check (ends_at > starts_at)
);