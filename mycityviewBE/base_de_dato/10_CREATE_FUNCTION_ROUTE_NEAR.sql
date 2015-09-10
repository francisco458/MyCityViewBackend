USE `mycityviewdb`;
DROP function IF EXISTS `get_route_near`;

DELIMITER $$
USE `mycityviewdb`$$
CREATE DEFINER=`app_user`@`localhost` FUNCTION `get_route_near`(latitud_1 FLOAT, longitud_1 FLOAT, latitud_2 FLOAT, longitud_2 FLOAT, idruta LONG) RETURNS mediumtext CHARSET utf8
BEGIN
	DECLARE dist DOUBLE;
	
	SET dist = ACOS(SIN(PI() * latitud_1/180.0) * SIN(PI() * latitud_2/180.0) + COS(PI() * latitud_1/180.0) * COS(PI() * latitud_2/180.0)* COS(PI() * longitud_2/180.0 - PI() * longitud_1/180.0)) * 6371 * 1000;
  	IF(dist > 55) THEN
		RETURN null;
	ELSE 
		RETURN idruta;
    END IF;    
END$$

DELIMITER ;

