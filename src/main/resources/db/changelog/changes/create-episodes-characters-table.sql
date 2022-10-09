--liquibase formatted sql
--changeset <mysql>:<create-episodes-characters-table>

CREATE TABLE IF NOT EXISTS `episodes_characters` (
    `episode_id` bigint NOT NULL,
    `character_id` bigint NOT NULL,
    PRIMARY KEY (`episode_id`,`character_id`),
    KEY `fk_character_id` (`character_id`),
    CONSTRAINT `fk_character_id` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_episode_id` FOREIGN KEY (`episode_id`) REFERENCES `episodes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE rick_and_morty.episodes_characters;