ALTER TABLE `despesas`
	ADD COLUMN `categoria` INT NOT NULL DEFAULT '0' AFTER `data`;