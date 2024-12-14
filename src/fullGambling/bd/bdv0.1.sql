
CREATE TABLE `user` ( 
  `id` INT AUTO_INCREMENT NOT NULL,
  `username` VARCHAR(250) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp() ,
  CONSTRAINT `PRIMARY` PRIMARY KEY (`id`),
  CONSTRAINT `UQ_user_username` UNIQUE (`username`)
);


CREATE TABLE `viaje` ( 
  `id` INT AUTO_INCREMENT NOT NULL,
  `usuario` VARCHAR(250) NOT NULL,
  `fecha_hora` DATETIME NOT NULL,
  `origen` VARCHAR(250) NOT NULL,
  `destino` VARCHAR(250) NOT NULL,
  `plazas` INT NOT NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (`id`)
);

CREATE TABLE `pasajero` ( 
  `viaje` INT NOT NULL,
  `usuario` VARCHAR(250) NOT NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (`viaje`, `usuario`)
);

INSERT INTO `user` (`id`, `username`, `password`, `created_at`) VALUES (1, 'aguado', '$2y$13$DE231eGe9Z2hiNgpdIsVE.CJP65clkYvRMGjFdlpug9Odw3OSZhTS', '2024-10-30 17:39:00');
ALTER TABLE `pasajero` ADD CONSTRAINT `FK_pasajero_viaje` FOREIGN KEY (`viaje`) REFERENCES `viaje` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
