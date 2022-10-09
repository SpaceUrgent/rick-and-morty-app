--liquibase formatted sql
--changeset <mysql>:<create-characters-table>

CREATE TABLE IF NOT EXISTS `characters` (
    `id` bigint NOT NULL,
    `name` varchar(256) NOT NULL,
    `status` enum('ALIVE','DEAD','UNKNOWN') DEFAULT NULL,
    `spieces` varchar(256) DEFAULT NULL,
    `type` varchar(256) DEFAULT NULL,
    `gender` enum('FEMALE','MALE','GENDERLESS','UNKNOWN') DEFAULT NULL,
    `origin_id` bigint DEFAULT NULL,
    `location_id` bigint DEFAULT NULL,
    `image` mediumblob,
    PRIMARY KEY (`id`),
    KEY `fk_origin_id` (`origin_id`),
    KEY `fk_location_id` (`location_id`),
    CONSTRAINT `fk_location_id` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
    CONSTRAINT `fk_origin_id` FOREIGN KEY (`origin_id`) REFERENCES `locations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE rick_and_morty.characters;

