ALTER TABLE "tour_company_wallet"
ALTER COLUMN "last_updated" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "user_point"
ALTER COLUMN "last_updated" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "user_wallet"
ALTER COLUMN "last_updated" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "booking"
ALTER COLUMN "last_updated" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "booking"
ALTER COLUMN "booking_date" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "transaction"
ALTER COLUMN "transaction_date" TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE "tour"
ALTER COLUMN "activity_date" TYPE TIMESTAMP WITH TIME ZONE;