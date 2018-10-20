CREATE TABLE `annonce` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(20) DEFAULT NULL,
  `description` VARCHAR (200)DEFAULT NULL,
  `etat` int(5) DEFAULT NULL,
   `prix` int DEFAULT NULL,
   `auteur` VARCHAR(15) DEFAULT NULL,
   `instrumentId` VARCHAR(20) DEFAULT NULL,
   `picture` VARCHAR(100) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `auteur` (
  `pseudo` VARCHAR (15) NOT NULL,
  `mdp` varchar(500) DEFAULT NULL,
  `email` VARCHAR(30) DEFAULT NULL,
  `numerotel` INT DEFAULT NULL,

  PRIMARY KEY (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




INSERT INTO `annonce` (`titre`,`description`,`etat`,`prix`,`auteur`,`instrumentId`) VALUES('Batterie','Je vend une batterie',3,290,'keke',1);
INSERT INTO `annonce` (`titre`,`description`,`etat`,`prix`,`auteur`,`instrumentId`) VALUES('Batterie','Je vend une batterie',3,290,'keke',1);
INSERT INTO `annonce` (`titre`,`description`,`etat`,`prix`,`auteur`,`instrumentId`) VALUES('Batterie','Je vend une batterie',3,290,'keke',1);
INSERT INTO `annonce` (`titre`,`description`,`etat`,`prix`,`auteur`,`instrumentId`) VALUES('Batterie','Je vend une batterie',3,290,'keke',1);