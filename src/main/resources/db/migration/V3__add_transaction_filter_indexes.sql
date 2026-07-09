create index idx_transactions_timestamp
on transactions (timestamp);

create index idx_transactions_category
on transactions (category);

create index idx_transactions_type
on transactions (type);