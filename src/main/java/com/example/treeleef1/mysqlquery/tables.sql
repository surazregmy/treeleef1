CREATE TABLE `locations`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `cameras`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `brand`       varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `location_id` int(11)                                 DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKmxxi0r9od2aakv7lh5rbfbvr8` (`location_id`),
    CONSTRAINT `FKmxxi0r9od2aakv7lh5rbfbvr8` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `vehicles`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT,
    `number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `vehicles_locations`
(
    `vehicles_id`  int(11) NOT NULL,
    `locations_id` int(11) NOT NULL,
    KEY `FK4h2hywoiqaje4ywncje75spdu` (`locations_id`),
    KEY `FKibu51axdxannf3t169wfnxukx` (`vehicles_id`),
    CONSTRAINT `FK4h2hywoiqaje4ywncje75spdu` FOREIGN KEY (`locations_id`) REFERENCES `locations` (`id`),
    CONSTRAINT `FKibu51axdxannf3t169wfnxukx` FOREIGN KEY (`vehicles_id`) REFERENCES `vehicles` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;