# Tweets schema

# --- !Ups

CREATE SEQUENCE tweet_id_seq;
CREATE TABLE tweet (
            id integer NOT NULL DEFAULT nextval('tweet_id_seq'),
            body varchar(140) NOT NULL
        );

# --- !Downs

DROP TABLE tweet;
DROP SEQUENCE tweet_id_seq;
