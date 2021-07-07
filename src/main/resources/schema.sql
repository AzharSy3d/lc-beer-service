CREATE TABLE "BEER"(
                       "ID" VARCHAR_IGNORECASE NOT NULL,
                       "BEER_NAME" VARCHAR_IGNORECASE(255),
                       "BEER_STYLE" VARCHAR_IGNORECASE(255),
                       "CREATED_DATE" TIMESTAMP,
                       "LAST_MODIFIED_DATE" TIMESTAMP,
                       "MIN_ON_HAND" INTEGER,
                       "PRICE" DECIMAL(19, 2),
                       "QUANTITY_TO_BREW" INTEGER,
                       "UPC" VARCHAR_IGNORECASE(255),
                       "VERSION" BIGINT
);