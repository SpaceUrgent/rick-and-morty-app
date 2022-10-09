--liquibase formatted sql
--changeset <mysql>:<create-episodes-table>

CREATE TABLE IF NOT EXISTS `episodes` (
    `id` bigint NOT NULL,
    `name` varchar(256) DEFAULT NULL,
    `air_date` date DEFAULT NULL,
    `episode` char(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--rollback DROP TABLE rick_and_morty.episodes;