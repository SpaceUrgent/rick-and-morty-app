--liquibase formatted sql
--changeset <mysql>:<create-locations-table>

CREATE TABLE IF NOT EXISTS `locations` (
    `id` bigint NOT NULL,
    `name` varchar(256) NOT NULL,
    `type` varchar(256) DEFAULT NULL,
    `dimension` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE rick_and_morty.locations;
