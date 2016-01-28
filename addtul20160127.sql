CREATE DATABASE  IF NOT EXISTS `addtul` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `addtul`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: addtul
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal` (
  `id_animal` char(36) NOT NULL,
  `id_proveedor` char(36) DEFAULT NULL,
  `fecha_compra` datetime DEFAULT NULL,
  `compra` char(255) DEFAULT NULL,
  `numero_lote` char(255) DEFAULT NULL,
  `peso_compra` decimal(20,4) DEFAULT NULL,
  `id_sexo` char(36) DEFAULT NULL,
  `fecha_ingreso` datetime DEFAULT NULL,
  `arete_visual` varchar(45) DEFAULT NULL,
  `arete_electronico` char(255) DEFAULT NULL,
  `arete_siniiga` char(255) DEFAULT NULL,
  `arete_campaña` char(255) DEFAULT NULL,
  `peso_actual` decimal(12,4) DEFAULT NULL,
  `temperatura` decimal(20,4) DEFAULT NULL,
  `es_semental` varchar(1) DEFAULT NULL,
  `id_semental` char(36) DEFAULT NULL,
  `id_raza` char(36) DEFAULT NULL,
  `es_vientre` char(1) DEFAULT NULL,
  `fecha_recepcion` datetime DEFAULT NULL,
  `peso_recepcion` decimal(20,4) DEFAULT NULL,
  `porcentaje_merma` decimal(20,4) DEFAULT NULL,
  `costo_flete` decimal(20,4) DEFAULT NULL,
  `total_alimento` decimal(20,4) DEFAULT NULL,
  `costo_alimento` decimal(20,4) DEFAULT NULL,
  `promedio_alimento` decimal(20,4) DEFAULT NULL,
  `promedio_costo_alimento` decimal(20,4) DEFAULT NULL,
  `fecha_ultima_comida` datetime DEFAULT NULL,
  `ganancia_promedio` decimal(20,4) DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `animal` WRITE;
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `animal_BEFORE_INSERT` 
BEFORE INSERT ON `animal` 
FOR EACH ROW
begin
DECLARE varConteo INT(10);
    DECLARE	msg	CHAR(255);
	
	SELECT COUNT(*)
	INTO varConteo 
    FROM animal
	WHERE arete_visual = NEW.arete_visual 
    AND status = 'A';
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El IDV "', NEW.arete_visual, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
	
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `animal_AINS` AFTER INSERT ON `animal`
FOR EACH ROW
BEGIN

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);   

	SELECT	coalesce(id_corral, ''),	coalesce(id_rancho, '')    
	INTO	varIdCorral,				varIdRancho
	FROM 	corral_animal
	WHERE	id_animal	=	NEW.id_animal;

	if  varIdCorral <> '' then
    
		CALL animalesPorCorral(varIdCorral);
	end if;
    
    if varIdRancho <> '' then
    
	-- Envio a FTP
		DELETE FROM repl_animal
		WHERE	id_rancho	=	varIdRancho
		AND		id_animal	=	NEW.id_animal;

		INSERT INTO repl_animal
		SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
	-- Envio a FTP
	end if;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `animal_BUPD`
BEFORE UPDATE ON `animal`
FOR EACH ROW
BEGIN	

	declare varFechaRecepcion datetime;
    declare	varGananciaPromedio decimal(20,4);
	
	select	fecha_recepcion
    into	varFechaRecepcion
    from	recepcion r
    where   r.numero_lote	=	new.numero_lote AND r.animales_pendientes > 0;

	set 	new.promedio_alimento		=	new.total_alimento	/	datediff(new.fecha_ultima_comida, varFechaRecepcion),
			new.promedio_costo_alimento	=	new.costo_alimento	/	datediff(new.fecha_ultima_comida, varFechaRecepcion);

-- Ganancia promedio

	SELECT	ROUND((NEW.peso_actual - new.peso_compra) / DATEDIFF(MAX(fecha), DATE_SUB(new.fecha_compra, INTERVAL '1' DAY)),2)
    into	varGananciaPromedio
	FROM	movimiento m, detalle_movimiento d, rancho r
    WHERE	m.id_rancho	=   r.id_rancho
    AND		m.id_concepto	=   r.con_pesaje
    AND		(		m.id_rancho     =   d.id_rancho
             AND	m.id_concepto   =   d.id_concepto
             AND	m.id_movimiento =   d.id_movimiento
			 AND	d.id_animal     =   new.id_animal );

	set		new.ganancia_promedio	=	varGananciaPromedio;    

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `animal_AUPD`
AFTER UPDATE ON `animal`
FOR EACH ROW
BEGIN	

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);
    
	SELECT	id_corral, 		id_rancho
	INTO	varIdCorral,	varIdRancho
	FROM	corral_animal
	WHERE	id_animal	=	NEW.id_animal;
	
	if  varIdCorral <> '' then
		CALL animalesPorCorral(varIdCorral);
	end if;

 if varIdRancho <> '' then
	-- Envio a FTP
		DELETE FROM repl_animal
		WHERE	id_rancho	=	varIdRancho
		AND		id_animal	=	NEW.id_animal;

		INSERT INTO repl_animal
		SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
	-- Envio a FTP
 end if;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `animal_ADEL`
AFTER DELETE ON `animal`
FOR EACH ROW
BEGIN	

	DECLARE varIdCorral CHAR(36);

	SELECT	id_corral
	INTO	varIdCorral
	FROM	corral_animal
	WHERE	id_animal	=	OLD.id_animal;

	CALL animalesPorCorral(varIdCorral);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `animal_grupo`
--

DROP TABLE IF EXISTS `animal_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal_grupo` (
  `id_rancho` char(36) NOT NULL,
  `id_usuario` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_usuario`,`id_animal`,`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabla para capturas masivas\ntipo = medicina, traspaso, salid';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal_grupo`
--

LOCK TABLES `animal_grupo` WRITE;
/*!40000 ALTER TABLE `animal_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal_grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ciudad` (
  `id_estado` char(36) NOT NULL,
  `id_ciudad` char(36) NOT NULL,
  `descripcion_ciudad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_estado`,`id_ciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
INSERT INTO `ciudad` VALUES ('1','1','Aguascalientes'),('1','2','Calvillo'),('1','3','San Francisco de los Romo'),('1','4','Jesús María'),('1','5','Pabellón de Arteaga'),('1','6','Rincón de Romos'),('1','7','Asientos'),('1','8','Tepezalá'),('1','9','Cosío'),('10','1','Victoria de Durango'),('10','10','Vicente Guerrero'),('10','11','El Salto'),('10','12','Santa María del Oro'),('10','2','Gómez Palacio'),('10','3','Ciudad Lerdo'),('10','4','Santiago Papasquiaro'),('10','5','Canatlán'),('10','6','San Juan del Río del Centauro del Norte'),('10','7','Peñón Blanco'),('10','8','Francisco I. Madero'),('10','9','Nombre de Dios'),('11','1','Acámbaro'),('11','10','San Francisco del Rincón'),('11','11','Silao'),('11','12','Jerécuaro'),('11','13','Santiago Maravatío'),('11','14','Romita'),('11','15','Tarandacuao'),('11','16','Huanímaro'),('11','17','Comonfort'),('11','18','Uriangato'),('11','19','Pénjamo'),('11','2','San miguel de Allende'),('11','20','Cuerámaro'),('11','21','Empalme Escobedo (Escobedo)'),('11','22','San Luis de la Paz'),('11','23','Valle de Santiago'),('11','24','Abasolo'),('11','25','Rincón de Tamayo'),('11','26','Villagrán'),('11','27','Yuriria'),('11','28','Apaseo el Grande'),('11','29','Purísima de Bustos'),('11','3','Celaya'),('11','30','Salvatierra'),('11','31','Marfil'),('11','32','San José Iturbide'),('11','33','Apaseo el Alto'),('11','34','Ciudad Manuel Doblado'),('11','35','Jaral del Progreso'),('11','36','San Diego de la Unión'),('11','37','Santa Cruz Juventino Rosas'),('11','38','Doctor Mora'),('11','39','Dolores Hidalgo Cuna de la Independencia Nacional'),('11','4','Cortazar'),('11','5','Guanajuato'),('11','6','Irapuato'),('11','7','León de los Aldama'),('11','8','Moroleón'),('11','9','Salamanca'),('12','1','Acapulco de Juárez'),('12','10','Ciudad Apaxtla de Castrejón'),('12','11','Ciudad Altamirano'),('12','12','Buenavista de Cuellar'),('12','13','Cutzamala de Pinzón'),('12','14','Coyuca de Catalán'),('12','15','Tierra Colorada'),('12','16','Coyuca de Benítez'),('12','17','Olinalá'),('12','18','Marquelia'),('12','19','Zumpango del Río'),('12','2','Chilpancingo de los Bravo'),('12','20','San Luis de la Loma'),('12','21','Petatlán'),('12','22','La Unión'),('12','23','San Luis San Pedro'),('12','24','Teloloapan'),('12','25','Técpan de Galeana'),('12','26','Huitzuco'),('12','27','Tixtla de Guerrero'),('12','28','Tepecoacuilco de Trujano'),('12','29','San Marcos'),('12','3','Iguala de la Independencia'),('12','30','Azoyú'),('12','31','Tlapehuala'),('12','32','San Luis Acatlán'),('12','33','Chilapa de Álvarez'),('12','34','Tlapa de Comonfort'),('12','35','Tlalixtaquilla'),('12','36','Cuajinicuilapa'),('12','37','Huamuxtitlan'),('12','38','Cruz Grande'),('12','39','Ocotito'),('12','4','Taxco de Alarcón'),('12','40','Copala'),('12','41','Zihuatanejo'),('12','5','Arcelia'),('12','6','Ayutla de los Libres'),('12','7','Atoyac de Álvarez'),('12','9','San Jerónimo de Juárez'),('13','1','Actopan'),('13','10','Tizayuca'),('13','11','Santiago Tulantepec'),('13','12','Ixmiquilpan'),('13','13','Tepeji del Rio'),('13','14','Cruz Azul'),('13','15','Tepeapulco'),('13','2','Apan'),('13','3','Pachuca de Soto'),('13','4','Ciudad de Fray Bernardino de Sahagún'),('13','5','Tula de Allende'),('13','6','Tulancingo'),('13','7','Zimapan'),('13','8','Huejutla de Reyes'),('13','9','Tlaxcoapan'),('14','1','Ameca'),('14','10','Zapopan'),('14','13','Tuxpan'),('14','14','Tototlán'),('14','15','San Diego de Alejandría'),('14','16','La Resolana'),('14','17','Atotonilco el Alto'),('14','18','Jalostotitlán'),('14','19','Poncitlán'),('14','2','Ciudad Guzmán'),('14','20','Arandas'),('14','21','Talpa de Allende'),('14','22','Etzatlán'),('14','23','Sayula'),('14','24','Ahualulco de Mercado'),('14','25','Autlán de Navarro'),('14','26','Magdalena'),('14','27','San Julián'),('14','28','Cocula'),('14','29','El Grullo'),('14','30','San Miguel el Alto'),('14','31','Tala'),('14','32','La Barca'),('14','33','Jamay'),('14','34','Yahualica de González Gallo'),('14','36','Cihuatlán'),('14','37','Zapotiltic'),('14','38','Villa Corona'),('14','39','Teocaltiche'),('14','4','Lagos de Moreno'),('14','41','Tequila'),('14','42','El Quince (San José el Quince)'),('14','43','San José el Verde (El Verde)'),('14','44','Jocotepec'),('14','45','Tecalitlán'),('14','46','Chapala'),('14','47','Ajijic'),('14','48','San Ignacio Cerro Gordo'),('14','5','Ocotlán'),('14','50','Huejuquilla el Alto'),('14','51','Villa Hidalgo'),('14','52','Unión de San Antonio'),('14','53','Las Pintitas'),('14','54','Tamazula de Gordiano'),('14','55','Acatlán de Juárez'),('14','56','Valle de Guadalupe'),('14','7','San Juan de los Lagos'),('14','8','Tepatitlán de Morelos'),('14','9','Tlaquepaque'),('15','1','Ciudad Adolfo López Mateos'),('15','10','Ciudad Nezahualcoyotl'),('15','11','Villa Nicolás Romero'),('15','12','Tecamac de Felipe Villanueva'),('15','13','Tepotzotlán'),('15','14','Tlalnepantla de Baz'),('15','15','Santa Maria Tultepec'),('15','16','Tultitlán de Mariano Escobedo'),('15','17','Cuautitlán'),('15','18','Ixtapaluca'),('15','19','Texcoco de Mora'),('15','2','Chimalhuacán'),('15','20','Toluca de Lerdo'),('15','21','Valle de Chalco Solidaridad'),('15','22','Tejupilco de Hidalgo'),('15','23','Chalco de Díaz Covarrubias'),('15','24','Amatepec'),('15','26','Melchor Ocampo'),('15','27','San Vicente Chicoloapan de Juárez'),('15','28','Capulhuac'),('15','29','Juchitepec de Mariano Riva Palacio'),('15','3','Coacalco de Berriozabal'),('15','30','Tequixquiac'),('15','31','Xonacatlán'),('15','32','San Mateo Atenco'),('15','36','Chiconcuac'),('15','39','Almoloya de Juárez'),('15','4','Cuautitlán Izcalli'),('15','40','Ocoyoacac'),('15','41','Zumpango'),('15','5','Ecatepec de Morelos'),('15','6','Huixquilucan de Degollado'),('15','7','Los Reyes Acaquilpan (La Paz)'),('15','8','Metepec'),('15','9','Naucalpan de Juárez'),('16','1','Apatzingán de la Constitución'),('16','10','Zacapu'),('16','11','Zamora de Hidalgo'),('16','12','Heroica Zitácuaro'),('16','13','Paracho de Verduzco'),('16','14','Tangancícuaro de Arista'),('16','15','Maravatío de Ocampo'),('16','16','Zinapécuaro de Figueroa'),('16','17','Puruándiro'),('16','18','Yurécuaro'),('16','19','Huetamo de Núñez'),('16','2','Los Reyes de Salgado'),('16','20','Tacámbaro de Codallos'),('16','21','Ciudad Lázaro Cárdenas'),('16','22','Las Guacamayas'),('16','23','Jiquilpan de Juárez'),('16','24','Tuxpan'),('16','25','Cotija de la Paz'),('16','26','Nueva Italia de Ruiz'),('16','27','Cuitzeo del Porvenir'),('16','3','Ciudad Hidalgo'),('16','4','Jacona de Plancarte'),('16','5','La piedad de Cabadas'),('16','6','Morelia'),('16','7','Pátzcuaro'),('16','8','Sahuayo de Morelos'),('16','9','Uruapan'),('17','1','Cuautla (Cuautla de Morelos)'),('17','2','Cuernavaca'),('17','3','Galeana'),('17','4','Jojutla'),('17','5','Puente de Ixtla'),('17','6','Santa Rosa Treinta'),('17','7','Zacatepec de Hidalgo'),('17','8','Tlaquiltenango'),('18','1','Santiago Ixcuintla'),('18','10','San Blas'),('18','11','Ixtlán del Río'),('18','12','Bucerías'),('18','13','Las Varas'),('18','14','Xalisco'),('18','15','San pedro Lagunillas'),('18','16','La peñita de Jaltemba'),('18','17','Jala'),('18','18','Ahuacatlán'),('18','2','Tepic'),('18','3','Tuxpan'),('18','4','Acaponeta'),('18','5','Tecuala'),('18','6','Compostela'),('18','7','Francisco I. Madero (Puga)'),('18','8','Villa Hidalgo (El Nuevo)'),('18','9','Ruiz'),('19','1','Ciudad Apodaca'),('19','10','Ciudad Santa Catarina'),('19','11','Doctor Arroyo'),('19','12','Ciénega de Flores'),('19','13','Hualahuises'),('19','15','Cadereyta Jiménez'),('19','17','Santiago'),('19','18','El cercado'),('19','2','San Pedro Garza García'),('19','20','Anáhuac'),('19','21','García'),('19','22','Ciudad Benito Juárez'),('19','3','Ciudad General Escobedo'),('19','4','Guadalupe'),('19','5','Linares'),('19','6','Montemorelos'),('19','7','Monterrey'),('19','8','Ciudad Sabinas Hidalgo'),('19','9','San Nicolás de los Garza'),('2','1','Ensenada'),('2','2','Mexicali'),('2','3','Tecate'),('2','4','Tijuana'),('2','5','Playas de Rosarito'),('2','6','Rodolfo Sánchez T. (Maneadero)'),('2','7','San Felipe'),('20','1','Juchitán (Juchitán de Zaragoza)'),('20','11','Santiago Suchilquitongo'),('20','12','San Felipe Jalapa de Díaz'),('20','13','Bahias de Huatulco'),('20','14','Putla Villa de Guerrero'),('20','15','Cosolapa'),('20','16','Tlacolula de Matamoros'),('20','17','San Pablo Villa de Mitla'),('20','18','Natividad'),('20','19','Teotitlán de Flores Magón'),('20','2','Oaxaca de Juárez'),('20','20','Santa María Huatulco'),('20','21','San Juan Bautista Cuicatlán'),('20','22','Villa Sola de Vega'),('20','23','Ocotlán de Morelos'),('20','24','Villa de Zaachila'),('20','25','Miahuatlán de Porfirio Díaz'),('20','26','Unión Hidalgo'),('20','27','El Camarón'),('20','28','San Pedro Mixtepec -Dto. 22-'),('20','29','Santa Cruz Itundujia'),('20','3','Salina Cruz'),('20','30','Chahuites'),('20','31','Heroica Ciudad de Ejutla de Crespo'),('20','32','San Pedro Tapanatepec'),('20','33','Vicente Camalote'),('20','34','Villa de Tamazulápam del Progreso'),('20','35','San Juan Bautista lo de Soto'),('20','36','San Juan Cacahuatepec'),('20','37','San Pedro Totolapa'),('20','38','San Miguel el Grande'),('20','39','Zimatlán de Álvarez'),('20','4','San Juan Bautista Tuxtepec'),('20','40','San Pablo Huitzo'),('20','41','San Francisco Telixtlahuaca'),('20','42','Mariscala de Juárez'),('20','43','Santiago Pinotepa Nacional'),('20','44','Santiago Jamiltepec'),('20','45','San Pedro Pochutla'),('20','46','Heroica Ciudad de Tlaxiaco'),('20','47','San Juan Bautista Valle Nacional'),('20','48','Lagunas'),('20','49','Ciudad Ixtepec'),('20','5','Matías Romero Avendaño'),('20','50','Santiago Juxtlahuaca'),('20','51','San Sebastián Tecomaxtlahuaca'),('20','52','Asunción Nochixtlán'),('20','53','San Francisco Ixhuatán'),('20','54','San Blas Atempa'),('20','55','Santo Domingo Tehuantepec'),('20','56','Cuilápam de Guerrero'),('20','57','El Rosario'),('20','58','Santa Lucia del Camino'),('20','59','San Antonio de la Cal'),('20','6','Heroica Ciudad de Huajuapan de León'),('20','7','Loma Bonita'),('20','8','Puerto Escondido'),('20','9','Río Grande o Piedra Parada'),('21','1','Atlixco'),('21','10','Acatlán de Osorio'),('21','11','Cuautlancingo'),('21','12','Tepeaca'),('21','13','Tecamachalco'),('21','14','Zacatlán'),('21','15','Xicotepec'),('21','16','Ciudad Serdán'),('21','17','Amozoc'),('21','2','Izúcar de Matamoros'),('21','3','Puebla (Heroica Puebla)'),('21','4','San Martín Texmelucan de Labastida'),('21','5','Tehuacan'),('21','6','Teziutlan'),('21','7','San Andrés Cholula'),('21','8','San Pedro Cholula'),('21','9','Huauchinango'),('22','1','Querétaro'),('22','2','San Juan del Rio'),('22','4','El Pueblito'),('23','1','Cancún'),('23','2','Cozumel'),('23','3','Felipe Carrillo Puerto'),('23','4','Chetumal'),('23','5','Playa del Carmen'),('23','6','Kantunilkín'),('23','7','Isla Mujeres'),('23','8','Bacalar'),('24','1','Ciudad Valles'),('24','10','Cerritos'),('24','11','Tamuin'),('24','12','Tamasopo'),('24','13','Ciudad del Maíz'),('24','14','Cedral'),('24','15','Tierra Nueva'),('24','16','Villa de Reyes'),('24','17','Fracción el Refugio'),('24','18','Tamazunchale'),('24','19','Santa María del Río'),('24','2','Ébano'),('24','20','El Naranjo'),('24','3','Matehuala'),('24','4','Rioverde'),('24','5','San Luis Potosí'),('24','6','Soledad de Graciano Sánchez'),('24','7','Charcas'),('24','8','Salinas de Hidalgo'),('24','9','Cárdenas'),('25','1','Los Mochis'),('25','10','Higuera de Zaragoza'),('25','11','Choix'),('25','12','Villa Unión'),('25','13','Sinaloa de Leyva'),('25','14','Mocorito'),('25','15','Angostura'),('25','16','San Blas'),('25','17','La Cruz'),('25','18','El rosario'),('25','19','Estación Naranjo'),('25','2','Culiacán Rosales'),('25','20','Aguaruto'),('25','21','Cosalá'),('25','22','San Ignacio'),('25','23','Topolobampo'),('25','24','Lic. Benito Juárez (Campo Gobierno)'),('25','3','Escuinapa de Hidalgo'),('25','4','Guasave'),('25','5','Mazatlán'),('25','6','Guamúchil'),('25','7','Navolato'),('25','8','Quila'),('25','9','Ahome'),('26','1','San Luis Río Colorado'),('26','10','Heroica Nogales'),('26','11','Puerto Peñasco'),('26','12','Heroica Ciudad de Cananea'),('26','13','Sonoita'),('26','14','Magdalena de Kino'),('26','2','Agua Prieta'),('26','3','Heroica Caborca'),('26','4','Ciudad Obregón'),('26','5','Empalme'),('26','6','Heroica Guaymas'),('26','7','Hermosillo'),('26','8','Huatabampo'),('26','9','Navojoa'),('27','1','Cárdenas'),('27','10','Paraíso'),('27','11','Frontera'),('27','12','Cunduacán'),('27','13','Huimanguillo'),('27','2','Villahermosa'),('27','3','Comalcalco'),('27','4','Emiliano Zapata'),('27','5','Jalpa de Méndez'),('27','7','Macuspana'),('27','8','Tenosique de Pino Suárez'),('27','9','Teapa'),('28','1','Altamira'),('28','10','Tampico'),('28','11','Ciudad Victoria'),('28','12','González'),('28','13','Jaumave'),('28','14','Ciudad Gustavo Díaz Ordaz'),('28','15','Estación Manuel (Úrsulo Galván)'),('28','16','Xicoténcatl'),('28','17','Ciudad Miguel Alemán'),('28','18','Soto la Marina'),('28','19','Ciudad Tula'),('28','2','Ciudad Camargo'),('28','20','Nueva Ciudad Guerrero'),('28','21','Valle Hermoso'),('28','3','Ciudad Madero'),('28','4','Ciudad Mante'),('28','5','Heroica Matamoros'),('28','6','Nuevo Laredo'),('28','7','Reynosa'),('28','8','Ciudad Río Bravo'),('28','9','San Fernando'),('29','1','Apizaco'),('29','2','Villa Vicente Guerrero'),('29','3','Tlaxcala (Tlaxcala de Xicotencatl)'),('29','4','Huamantla'),('29','5','Calpulalpan'),('29','6','Chiautempan'),('3','1','Ciudad Constitución'),('3','10','San Ignacio'),('3','11','Guerrero Negro'),('3','2','La Paz'),('3','3','Cabo San Lucas'),('3','4','San José del Cabo'),('3','5','Loreto'),('3','6','Puerto Adolfo López Mateos'),('3','7','Todos Santos'),('3','8','Heroica Mulegé'),('3','9','Villa Alberto Andrés Alvarado Arámburo'),('30','1','Acayucan'),('30','10','Xalapa-Enríquez'),('30','11','Minatitlán'),('30','12','Orizaba'),('30','13','Papantla de Olarte'),('30','14','Poza Rica de Hidalgo'),('30','15','San Andrés Tuxtla'),('30','16','Túxpam de Rodríguez Cano'),('30','17','Veracruz'),('30','18','Tierra Blanca'),('30','19','Cosamaloapan'),('30','2','Naranjos'),('30','20','Carlos A. Carrillo'),('30','21','Pánuco'),('30','22','Tampico Alto'),('30','23','Tempoal de Sánchez'),('30','24','Tantoyuca'),('30','25','Gutiérrez Zamora'),('30','26','Platón Sánchez'),('30','27','Juan Rodríguez Clara'),('30','28','Huatusco de Chicuellar'),('30','29','Ixtaczoquitlán'),('30','3','Boca del RÍo'),('30','30','Río Blanco'),('30','31','Isla'),('30','32','Cuitláhuac'),('30','33','Fortín de las Flores'),('30','34','Alvarado'),('30','35','José Cardel'),('30','36','Banderilla'),('30','37','Paraje Nuevo'),('30','38','Playa Vicente'),('30','39','Altotonga'),('30','4','Coatepec'),('30','40','Juan Díaz Covarrubias'),('30','41','Cuichapa'),('30','42','Santiago Tuxtla'),('30','43','Huayacocotla'),('30','44','Paso de Ovejas'),('30','45','Catemaco'),('30','46','Nogales'),('30','47','Las Choapas'),('30','48','General Miguel Alemán (Potrero Nuevo)'),('30','49','Coatzintla'),('30','5','Agua dulce'),('30','50','Ángel R. Cabada'),('30','51','San Rafael'),('30','52','Tlacojalpan'),('30','53','Cosoleacaque'),('30','54','Lerdo de Tejada'),('30','55','Tihuatlán'),('30','56','Atoyac'),('30','57','Huiloapan de Cuauhtémoc'),('30','58','Cazones de Herrera'),('30','59','Yecuatla'),('30','6','Coatzacoalcos'),('30','60','Soledad de Doblado'),('30','61','Cerro Azul'),('30','62','Tezonapa'),('30','66','Sihuapan'),('30','67','El Higo'),('30','68','Paso del Macho'),('30','69','Tlapacoyan'),('30','7','Córdoba'),('30','8','Tres Valles'),('30','9','Jáltipan de Morelos'),('31','1','Mérida'),('31','2','Tizimín'),('31','3','Ticul'),('31','4','Motul de Carrillo Puerto'),('31','5','Valladolid'),('31','6','Kanasín'),('32','1','Fresnillo'),('32','10','Villa de Cos'),('32','11','Nochistlán de Mejía'),('32','12','Víctor Rosales'),('32','13','Valparaíso'),('32','14','Luis Moya'),('32','15','Moyahua de Estrada'),('32','16','Sombrerete'),('32','17','Jalpa'),('32','18','Loreto'),('32','19','Juan Aldama'),('32','2','Jerez de García Salinas'),('32','3','Zacatecas'),('32','4','Guadalupe'),('32','5','Río Grande'),('32','6','Ciudad Cuauhtémoc'),('32','7','Ojocaliente'),('32','8','Villa Hidalgo'),('32','9','Villanueva'),('4','1','San Francisco de Campeche'),('4','11','Pomuch'),('4','2','Ciudad del Carmen'),('4','3','Calkini'),('4','4','Candelaria'),('4','5','Escárcega'),('4','6','Sabancuy'),('4','7','Hopelchén'),('4','8','Champotón'),('4','9','Hecelchakán'),('5','1','Ciudad Acuña'),('5','10','San Pedro'),('5','11','Torreón'),('5','12','Castaños'),('5','13','Francisco I. Madero (Chávez)'),('5','14','Cuatro Ciénegas de Carranza'),('5','15','Nadadores'),('5','16','Ramos Arizpe'),('5','17','Nava'),('5','18','Zaragoza'),('5','19','San Buenaventura'),('5','2','Frontera'),('5','20','Ciudad Melchor Múzquiz'),('5','21','Viesca'),('5','22','Morelos'),('5','23','Arteaga'),('5','24','Allende'),('5','3','Matamoros'),('5','4','Monclova'),('5','5','Parras de la Fuente'),('5','6','Piedras Negras'),('5','7','Sabinas'),('5','8','Saltillo'),('5','9','Nueva Rosita'),('6','1','Colima'),('6','2','Manzanillo'),('6','3','Tecoman'),('6','4','Ciudad de Villa de Álvarez'),('6','5','Ciudad de Armería'),('7','1','Comitán de Domínguez'),('7','10','Palenque'),('7','11','Ocosingo'),('7','12','Tonalá'),('7','13','Mapastepec'),('7','14','Las Rosas'),('7','15','Chiapa de Corzo'),('7','16','Cacahoatán'),('7','17','Ocozocoautla de Espinosa'),('7','18','Cintalapa de Figueroa'),('7','19','Pichucalco'),('7','2','San Cristóbal de las Casas'),('7','20','Puerto Madero (San Benito)'),('7','21','Pijijiapan'),('7','22','Reforma'),('7','23','Huixtla'),('7','24','Motozintla de Mendoza'),('7','25','Acala'),('7','3','Tapachula de Córdova y Ordóñez'),('7','4','Tuxtla Gutiérrez'),('7','5','Venustiano Carranza'),('7','6','Jiquipilas'),('7','7','Villaflores'),('7','8','Las Margaritas'),('7','9','Arriaga'),('8','1','Santa Rosalía de Camargo'),('8','10','Colonia Anáhuac'),('8','11','Juan Aldama'),('8','12','José Mariano Jiménez'),('8','13','Manuel Ojinaga'),('8','14','Bachíniva'),('8','15','Saucillo'),('8','2','Chihuahua'),('8','3','Cuauhtémoc'),('8','4','Delicias'),('8','5','Hidalgo del Parral'),('8','6','Juárez'),('8','7','Nuevo Casas Grandes'),('8','9','Madera'),('9','1','Ciudad de México'),('9','10','Ciudad de México'),('9','11','Ciudad de México'),('9','12','Ciudad de México'),('9','13','Ciudad de México'),('9','14','Ciudad de México'),('9','15','Ciudad de México'),('9','16','Ciudad de México'),('9','2','Ciudad de México'),('9','3','Ciudad de México'),('9','4','Ciudad de México'),('9','5','Ciudad de México'),('9','6','Ciudad de México'),('9','7','Ciudad de México'),('9','8','Ciudad de México'),('9','9','Ciudad de México');
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clase_movimiento`
--

DROP TABLE IF EXISTS `clase_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clase_movimiento` (
  `id_clase_movimiento` int(11) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  PRIMARY KEY (`id_clase_movimiento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clase_movimiento`
--

LOCK TABLES `clase_movimiento` WRITE;
/*!40000 ALTER TABLE `clase_movimiento` DISABLE KEYS */;
INSERT INTO `clase_movimiento` VALUES (1,'VP - Venta en pie'),(2,'VR - Traspaso al rastro para su venta'),(3,'OT - Otro');
/*!40000 ALTER TABLE `clase_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id_cliente` char(36) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  `id_pais` char(36) DEFAULT NULL,
  `id_estado` char(36) DEFAULT NULL,
  `id_ciudad` char(36) DEFAULT NULL,
  `direccion` char(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `telefono` char(20) DEFAULT NULL,
  `p_fisica_moral` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cliente_AINS`
AFTER INSERT ON `cliente`
FOR EACH ROW
BEGIN
	
 -- Envio a FTP
	DELETE FROM repl_cliente
	WHERE	id_cliente	=	NEW.id_cliente;

    INSERT INTO repl_cliente
	SELECT NEW.id_cliente, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cliente_AUPD`
AFTER UPDATE ON `cliente`
FOR EACH ROW
BEGIN	

	-- Envio a FTP
	DELETE FROM repl_cliente
	WHERE	id_cliente	=	NEW.id_cliente;

    INSERT INTO repl_cliente
	SELECT NEW.id_cliente, NOW(), 'PE';
    -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `color_arete`
--

DROP TABLE IF EXISTS `color_arete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `color_arete` (
  `id_color` int(11) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  PRIMARY KEY (`id_color`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color_arete`
--

LOCK TABLES `color_arete` WRITE;
/*!40000 ALTER TABLE `color_arete` DISABLE KEYS */;
INSERT INTO `color_arete` VALUES (1,'Macho'),(2,'Hembra');
/*!40000 ALTER TABLE `color_arete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra` (
  `id_rancho` char(36) NOT NULL,
  `id_compra` char(36) NOT NULL,
  `id_proveedor` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `factura` varchar(45) DEFAULT NULL,
  `orden` varchar(45) DEFAULT NULL,
  `subtotal` decimal(20,4) DEFAULT NULL,
  `iva` decimal(20,4) DEFAULT NULL,
  `total` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_compra`,`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`compra_AFTER_INSERT` 
AFTER INSERT ON `compra` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_compra
    WHERE id_rancho = NEW.id_rancho 
    AND id_compra = NEW.id_compra 
    AND id_proveedor = NEW.id_proveedor;
    
    INSERT INTO repl_compra 
    SELECT NEW.id_rancho, NEW.id_compra, NEW.id_proveedor, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`compra_AFTER_UPDATE` 
AFTER UPDATE ON `compra` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_compra
    WHERE id_rancho = NEW.id_rancho 
    AND id_compra = NEW.id_compra 
    AND id_proveedor = NEW.id_proveedor;
    
    INSERT INTO repl_compra 
    SELECT NEW.id_rancho, NEW.id_compra, NEW.id_proveedor, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `concepto_movimiento`
--

DROP TABLE IF EXISTS `concepto_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `concepto_movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `descripcion` char(100) DEFAULT NULL,
  `des_corta` char(5) DEFAULT NULL,
  `tipo` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_concepto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concepto_movimiento`
--

LOCK TABLES `concepto_movimiento` WRITE;
/*!40000 ALTER TABLE `concepto_movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `concepto_movimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `concepto_movimiento_AINS` AFTER INSERT ON `concepto_movimiento` FOR EACH ROW BEGIN
	
 -- Envio a FTP
	DELETE FROM repl_concepto_movimiento
	WHERE id_rancho = NEW.id_rancho AND id_concepto = NEW.id_concepto;

	INSERT INTO repl_concepto_movimiento
	SELECT NEW.id_rancho, NEW.id_concepto, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `concepto_movimiento_AUPD` AFTER UPDATE ON `concepto_movimiento` FOR EACH ROW BEGIN	

 -- Envio a FTP
	DELETE FROM repl_concepto_movimiento
	WHERE id_rancho = NEW.id_rancho AND id_concepto = NEW.id_concepto;

	INSERT INTO repl_concepto_movimiento
	SELECT NEW.id_rancho, NEW.id_concepto, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracion` (
  `id_configuracion` char(36) NOT NULL,
  `puerto_baston` char(255) DEFAULT NULL,
  `puerto_bascula` char(255) DEFAULT NULL,
  `envio_com` varchar(45) DEFAULT NULL,
  `rec_com_bascula` varchar(45) DEFAULT NULL,
  `rec_com_baston` varchar(45) DEFAULT NULL,
  `tiempo_espera_com` int(1) DEFAULT NULL,
  `precio_carne` decimal(20,4) DEFAULT NULL,
  `costo_alimento` decimal(20,4) DEFAULT NULL,
  `fecha_ultima_replicacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_configuracion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tipo_aplicacion: 1 = oficina, 2 = rancho(sucursal)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES ('1f12304d-bea4-11e4-a9a2-002258cc1d65','COM2','COM7','{ZN}','EziWeigh7','XRS',5,82.0000,12.6500,'1900-01-01 00:00:00');
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `control_gestacion`
--

DROP TABLE IF EXISTS `control_gestacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `control_gestacion` (
  `id_control_gestacion` char(36) NOT NULL,
  `id_registro_empadre` char(36) DEFAULT NULL,
  `status` char(2) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `tipo_parto` char(36) DEFAULT NULL,
  PRIMARY KEY (`id_control_gestacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `control_gestacion`
--

LOCK TABLES `control_gestacion` WRITE;
/*!40000 ALTER TABLE `control_gestacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `control_gestacion` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER control_gestacion_AFTER_UPDATE 
AFTER UPDATE ON control_gestacion 
FOR EACH ROW
Begin
   /*Replicar */
	DELETE FROM repl_control_gestacion
	WHERE  id_control_gestacion = NEW.id_control_gestacion 
	AND    id_registro_empadre = NEW.id_registro_empadre;

	INSERT INTO repl_control_gestacion 
	SELECT NEW.id_control_gestacion,
           NEW.id_registro_empadre, NOW(), 'PE';
End */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `corral`
--

DROP TABLE IF EXISTS `corral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corral` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `nombre` char(255) DEFAULT NULL,
  `localizacion` char(255) DEFAULT NULL,
  `num_animales` int(11) DEFAULT NULL,
  `dias_corral` int(11) DEFAULT NULL,
  `id_raza` char(36) DEFAULT NULL,
  `total_kilos` decimal(20,4) DEFAULT NULL,
  `peso_minimo` decimal(20,4) DEFAULT NULL,
  `peso_maximo` decimal(20,4) DEFAULT NULL,
  `peso_promedio` decimal(20,4) DEFAULT NULL,
  `alimento_ingresado` decimal(20,4) DEFAULT NULL,
  `peso_ganancia` decimal(20,4) DEFAULT NULL,
  `id_sexo` char(36) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `total_kilos_iniciales` decimal(20,4) DEFAULT NULL,
  `total_costo_medicina` decimal(20,4) DEFAULT NULL,
  `total_costo_flete` decimal(20,4) DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  `ganancia_promedio` decimal(20,4) DEFAULT NULL,
  `promedio_alimento` decimal(20,4) DEFAULT NULL,
  `medicina_promedio` decimal(20,4) DEFAULT NULL,
  `conversion_alimenticia` decimal(20,4) DEFAULT NULL,
  `merma` decimal(20,4) DEFAULT NULL,
  `status` char(1) DEFAULT 'S',
  PRIMARY KEY (`id_rancho`,`id_corral`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corral`
--

LOCK TABLES `corral` WRITE;
/*!40000 ALTER TABLE `corral` DISABLE KEYS */;
/*!40000 ALTER TABLE `corral` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_AINS` AFTER INSERT ON `corral` FOR EACH ROW BEGIN

 -- Envio a FTP
DELETE FROM repl_corral 
WHERE
    id_rancho = NEW.id_rancho
    AND id_corral = NEW.id_corral;

INSERT INTO repl_corral
SELECT NEW.id_rancho, NEW.id_corral, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_AUPD` AFTER UPDATE ON `corral` FOR EACH ROW BEGIN
/*
	DECLARE varIdCorral CHAR(36);

	SELECT NEW.id_corral
    INTO	varIdCorral;

	CALL animalesPorCorral(varIdCorral);
*/
 -- Envio a FTP
DELETE FROM repl_corral 
WHERE
    id_rancho = NEW.id_rancho
    AND id_corral = NEW.id_corral;

INSERT INTO repl_corral
SELECT NEW.id_rancho, NEW.id_corral, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `corral_animal`
--

DROP TABLE IF EXISTS `corral_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corral_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_corral`,`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corral_animal`
--

LOCK TABLES `corral_animal` WRITE;
/*!40000 ALTER TABLE `corral_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `corral_animal` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_animal_AINS` AFTER INSERT ON `corral_animal` FOR EACH ROW BEGIN	

	CALL animalesPorCorral(NEW.id_corral);
	
 -- Envio a FTP
	DELETE FROM repl_corral_animal
	WHERE id_rancho = NEW.id_rancho
	AND id_corral = NEW.id_corral
	AND id_animal = NEW.id_animal;

	INSERT INTO repl_corral_animal
	SELECT NEW.id_rancho, NEW.id_corral, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_animal_AUPD` AFTER UPDATE ON `corral_animal` FOR EACH ROW BEGIN	

	CALL animalesPorCorral(NEW.id_corral);

 -- Envio a FTP
	DELETE FROM repl_corral_animal
	WHERE id_rancho = NEW.id_rancho
	AND id_corral = NEW.id_corral
	AND id_animal = NEW.id_animal;

	INSERT INTO repl_corral_animal
	SELECT NEW.id_rancho, NEW.id_corral, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_animal_ADEL` AFTER DELETE ON `corral_animal` FOR EACH ROW BEGIN	

	CALL animalesPorCorral(OLD.id_corral);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `corral_datos`
--

DROP TABLE IF EXISTS `corral_datos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corral_datos` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `categoria` char(255) DEFAULT NULL,
  `ganado_amedias` char(255) DEFAULT NULL,
  `color_arete` int(11) DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `numero_lote` char(255) DEFAULT NULL,
  `compra` char(255) DEFAULT NULL,
  `porcentaje` decimal(5,2) DEFAULT NULL,
  `id_proveedor` char(36) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_corral`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corral_datos`
--

LOCK TABLES `corral_datos` WRITE;
/*!40000 ALTER TABLE `corral_datos` DISABLE KEYS */;
/*!40000 ALTER TABLE `corral_datos` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_datos_AINS` AFTER INSERT ON `corral_datos` FOR EACH ROW BEGIN

 -- Envio a FTP
	DELETE FROM repl_corral_datos
	WHERE	id_rancho	=	NEW.id_rancho
	AND		id_corral	=	NEW.id_corral;

	INSERT	INTO	repl_corral_datos
	SELECT	NEW.id_rancho,	
			NEW.id_corral,
			NOW(),
			'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `corral_datos_AUPD` AFTER UPDATE ON `corral_datos` FOR EACH ROW BEGIN	

 -- Envio a FTP
	DELETE FROM repl_corral_datos
	WHERE	id_rancho	=	NEW.id_rancho
	AND		id_corral	=	NEW.id_corral;

	INSERT	INTO	repl_corral_datos
	SELECT	NEW.id_rancho,	
			NEW.id_corral,
			NOW(),
			'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `cria`
--

DROP TABLE IF EXISTS `cria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cria` (
  `id_rancho` char(36) NOT NULL,
  `id_madre` char(36) NOT NULL,
  `id_cria` char(36) NOT NULL,
  `arete` varchar(45) DEFAULT NULL,
  `id_sexo` char(36) DEFAULT NULL,
  `id_raza` char(36) DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `peso` decimal(20,4) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `id_tipo_parto` char(36) DEFAULT NULL,
  `id_animal` char(36) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_madre`,`id_cria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cria`
--

LOCK TABLES `cria` WRITE;
/*!40000 ALTER TABLE `cria` DISABLE KEYS */;
/*!40000 ALTER TABLE `cria` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cria_AINS` AFTER INSERT ON `cria` FOR EACH ROW -- Edit trigger body code below this line. Do not edit lines above this one
BEGIN

	-- FTP
	DELETE FROM repl_cria
	WHERE id_rancho = NEW.id_rancho
	AND id_madre = NEW.id_madre
	AND id_cria = NEW.id_cria;

	INSERT INTO repl_cria
	SELECT NEW.id_rancho, NEW.id_madre, NEW.id_cria, NOW(), 'PE';
	-- FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cria_AUPD`
AFTER UPDATE ON `cria`
FOR EACH ROW
BEGIN	

	-- se se esta actualizando el id_animal crear genealogia
	-- if new.id_animal <> old.id_animal then

		-- buscar el id del padre en semental de la madre
		insert into genealogia( 
				id_animal, 		id_madre, 		id_padre)
		select	cria.id_animal,	cria.id_madre,	id_semental
		from 	cria, animal
		where	cria.id_cria		=	NEW.id_cria
		and		animal.id_animal	=	cria.id_madre;
	-- end if;

	-- FTP
	DELETE FROM repl_cria
	WHERE id_rancho = NEW.id_rancho
	AND id_madre = NEW.id_madre
	AND id_cria = NEW.id_cria;

	INSERT INTO repl_cria
	SELECT NEW.id_rancho, NEW.id_madre, NEW.id_cria, NOW(), 'PE';
	-- FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `destino`
--

DROP TABLE IF EXISTS `destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destino` (
  `id_destino` int(11) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  PRIMARY KEY (`id_destino`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destino`
--

LOCK TABLES `destino` WRITE;
/*!40000 ALTER TABLE `destino` DISABLE KEYS */;
INSERT INTO `destino` VALUES (1,'900 - Embarque'),(2,'901 - Rastro Vargas'),(3,'902 - Rastro Tierra Blanca'),(4,'903 - Rastro Tihuatlan'),(5,'904 - Rastro Cuitláhuac');
/*!40000 ALTER TABLE `destino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compra`
--

DROP TABLE IF EXISTS `detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_compra` (
  `id_rancho` char(36) NOT NULL,
  `id_compra` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL,
  `id_detalle` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `presentacion` decimal(20,4) DEFAULT NULL,
  `precio_unitario` decimal(20,4) DEFAULT NULL,
  `importe` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id_detalle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `detalle_compra_AFTER_INSERT`
AFTER INSERT ON `detalle_compra`
FOR EACH ROW
BEGIN
	
	DECLARE varConteo 				INT(11);
	DECLARE varUltimaCompra 		DATETIME;
	DECLARE	varCantidadComprar		INT(11);
	DECLARE	varPrecioUnitario		DECIMAL(20,4);
	DECLARE	varExistenciaActual		INT(11);
	DECLARE	varCostoPromedioActual	DECIMAL(20,4);
	DECLARE	varCostoPromedio		DECIMAL(20,4);
/*
	SELECT	COUNT(*) INTO varConteo
	FROM	rancho_medicina
	WHERE	id_rancho	=	NEW.id_rancho
	AND		id_medicina	=	NEW.id_medicina;
*/
 	IF NOT EXISTS (	SELECT	* 
					FROM	rancho_medicina
					WHERE	id_rancho	=	NEW.id_rancho
					AND		id_medicina	=	NEW.id_medicina) THEN 

		INSERT INTO rancho_medicina(
			id_rancho,		id_medicina, 		existencia,	existencia_inicial, costo_promedio) 
		SELECT	
			NEW.id_rancho, 	NEW.id_medicina,	0,			0,					0.0 ;
 	END IF;

	SET		varCantidadComprar	=	NEW.cantidad 		*	NEW.presentacion,
			varPrecioUnitario	=	NEW.precio_unitario /	NEW.presentacion;	
	
	SELECT	existencia,				costo_promedio
	INTO	varExistenciaActual,	varCostoPromedioActual
	FROM	rancho_medicina
	WHERE	id_medicina 	=	NEW.id_medicina
	AND		id_rancho		=	NEW.id_rancho;

	SELECT 	fecha
	INTO	varUltimaCompra
	FROM	compra
	WHERE	id_rancho	=	NEW.id_rancho
	AND		id_compra	=	NEW.id_compra;

	SET	varCostoPromedio	=	
		( ( varExistenciaActual * varCostoPromedioActual ) + ( varCantidadComprar * varPrecioUnitario ) )
														   / 
									 ( varExistenciaActual + varCantidadComprar );

	UPDATE	rancho_medicina
	SET		ultima_compra	=	varUltimaCompra,			
			ultimo_costo	=	varPrecioUnitario,
			-- costo_promedio	=	varCostoPromedio,
			existencia		=	existencia	+	varCantidadComprar
	WHERE	id_medicina 	=	NEW.id_medicina
	AND		id_rancho		=	NEW.id_rancho;
    
    -- Solo Actualizar costo_promedio cuando sea mayor
    UPDATE	rancho_medicina
	SET		costo_promedio	=	varCostoPromedio,
			existencia		=	existencia	+	varCantidadComprar
	WHERE	id_medicina 	=	NEW.id_medicina
	AND		id_rancho		=	NEW.id_rancho
    AND		costo_promedio	<   varCostoPromedio;
    
    /*Replicar */
    DELETE FROM repl_detalle_compra
	WHERE id_rancho = NEW.id_rancho
	AND id_compra = NEW.id_compra
	AND id_medicina = NEW.id_medicina
	AND id_detalle = NEW.id_detalle;

	INSERT INTO repl_detalle_compra
	SELECT NEW.id_rancho, NEW.id_compra, NEW.id_medicina, NEW.id_detalle, NOW(), 'PE';
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`detalle_compra_AFTER_UPDATE` 
AFTER UPDATE ON `detalle_compra` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_detalle_compra
	WHERE id_rancho = NEW.id_rancho
	AND id_compra = NEW.id_compra
	AND id_medicina = NEW.id_medicina
	AND id_detalle = NEW.id_detalle;

	INSERT INTO repl_detalle_compra
	SELECT NEW.id_rancho, NEW.id_compra, NEW.id_medicina, NEW.id_detalle, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `detalle_movimiento`
--

DROP TABLE IF EXISTS `detalle_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `id_detalle` int(11) NOT NULL,
  `id_animal` char(36) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_concepto`,`id_detalle`,`id_animal`),
  KEY `fk_det_mov_mov_idx` (`id_rancho`,`id_movimiento`,`id_concepto`),
  KEY `fk_det_mov_animal_idx` (`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_movimiento`
--

LOCK TABLES `detalle_movimiento` WRITE;
/*!40000 ALTER TABLE `detalle_movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_movimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `detalle_movimiento_AINS` AFTER INSERT ON `detalle_movimiento` FOR EACH ROW BEGIN

/*SACA DEL CORRAL ORIGEN DEL MOVIMIENTO DE SALIDA*/
	DELETE	corral
	FROM	corral_animal corral, concepto_movimiento, movimiento
	WHERE	(	corral.id_animal	=	NEW.id_animal
			AND	corral.id_rancho	=	NEW.id_rancho )
	AND		(	concepto_movimiento.id_concepto	=	NEW.id_concepto			
			AND	concepto_movimiento.id_rancho	=	NEW.id_rancho			)
	AND		(	movimiento.id_rancho		=	NEW.id_rancho
			AND	movimiento.id_movimiento	=	NEW.id_movimiento
			AND	movimiento.id_concepto		=	NEW.id_concepto				)
	AND		corral.id_corral				=	movimiento.id_corral_origen
	AND		concepto_movimiento.tipo		=	'S';

/*METE AL CORRAL DESTINO DEL MOVIMIENTO DE ENTRADA*/
	INSERT INTO corral_animal(	
			id_rancho,		 				id_corral,						id_animal)
	SELECT	movimiento.id_rancho_destino,	movimiento.id_corral_destino,	NEW.id_animal
	FROM	movimiento, concepto_movimiento
	WHERE	(	concepto_movimiento.id_concepto	=	NEW.id_concepto			
			AND	concepto_movimiento.id_rancho	=	NEW.id_rancho			)	
	AND		(	movimiento.id_rancho		=	NEW.id_rancho
			AND	movimiento.id_movimiento	=	NEW.id_movimiento
			AND	movimiento.id_concepto		=	NEW.id_concepto				)
	AND		concepto_movimiento.tipo		=	'E';

DELETE FROM movimiento_animal 
WHERE
    id_animal = NEW.id_animal;

   INSERT INTO movimiento_animal
   SELECT NEW.id_rancho,
          NEW.id_movimiento,
          NEW.id_concepto,
		  NEW.id_animal;

 -- Envio a FTP
	DELETE FROM repl_detalle_movimiento 
	WHERE    id_rancho = NEW.id_rancho
    AND id_movimiento = NEW.id_movimiento
    AND id_concepto = NEW.id_concepto
    AND id_detalle = NEW.id_detalle
    AND id_animal = NEW.id_animal;

	INSERT INTO repl_detalle_movimiento
	SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NEW.id_detalle, NEW.id_animal, NOW(), 'PE';

 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `detalle_movimiento_AUPD` AFTER UPDATE ON `detalle_movimiento` FOR EACH ROW BEGIN	

 -- Envio a FTP
	DELETE FROM repl_detalle_movimiento
	WHERE id_rancho = NEW.id_rancho
	AND id_movimiento = NEW.id_movimiento
	AND id_concepto = NEW.id_concepto
	AND id_detalle = NEW.id_detalle
	AND id_animal = NEW.id_animal;

	INSERT INTO repl_detalle_movimiento
	SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NEW.id_detalle, NEW.id_animal, NOW(), 'PE';

 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `id_estado` char(36) NOT NULL,
  `id_pais` char(36) NOT NULL,
  `descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`id_estado`,`id_pais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES ('1','1','Aguascalientes'),('10','1','Durango'),('11','1','Guanajuato'),('12','1','Guerrero'),('13','1','Hidalgo'),('14','1','Jalisco'),('15','1','México'),('16','1','Michoacán de Ocampo'),('17','1','Morelos'),('18','1','Nayarit'),('19','1','Nuevo León'),('2','1','Baja California'),('20','1','Oaxaca'),('21','1','Puebla'),('22','1','Querétaro'),('23','1','Quintana Roo'),('24','1','San Luis Potosí'),('25','1','Sinaloa'),('26','1','Sonora'),('27','1','Tabasco'),('28','1','Tamaulipas'),('29','1','Tlaxcala'),('3','1','Baja California Sur'),('30','1','Veracruz de Ignacio de la llave'),('31','1','Yucatán'),('32','1','Zacatecas'),('4','1','Campeche'),('5','1','Coahuila de Zaragoza'),('6','1','Colima'),('7','1','Chiapas'),('8','1','Chihuahua'),('9','1','Distrito Federal');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genealogia`
--

DROP TABLE IF EXISTS `genealogia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genealogia` (
  `id_animal` char(36) NOT NULL,
  `id_madre` char(36) NOT NULL,
  `id_padre` char(36) NOT NULL,
  PRIMARY KEY (`id_animal`,`id_madre`,`id_padre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genealogia`
--

LOCK TABLES `genealogia` WRITE;
/*!40000 ALTER TABLE `genealogia` DISABLE KEYS */;
/*!40000 ALTER TABLE `genealogia` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`genealogia_AFTER_INSERT` 
AFTER INSERT ON `genealogia` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_genealogia
	WHERE id_animal = NEW.id_animal
	AND id_madre = NEW.id_madre
	AND id_padre = NEW.id_padre;

	INSERT INTO repl_genealogia
	SELECT NEW.id_animal, NEW.id_madre, NEW.id_padre, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`genealogia_AFTER_UPDATE` 
AFTER UPDATE ON `genealogia` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_genealogia
	WHERE id_animal = NEW.id_animal
	AND id_madre = NEW.id_madre
	AND id_padre = NEW.id_padre;

	INSERT INTO repl_genealogia
	SELECT NEW.id_animal, NEW.id_madre, NEW.id_padre, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ingreso_alimento`
--

DROP TABLE IF EXISTS `ingreso_alimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingreso_alimento` (
  `id_ingreso_alimento` char(36) NOT NULL,
  `numero_lote` varchar(45) DEFAULT NULL,
  `id_corral` char(36) DEFAULT NULL,
  `total_alimento` decimal(20,4) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `costo_unitario` decimal(20,4) DEFAULT NULL,
  `costo_total` decimal(20,4) DEFAULT NULL,
  `carro` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_ingreso_alimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingreso_alimento`
--

LOCK TABLES `ingreso_alimento` WRITE;
/*!40000 ALTER TABLE `ingreso_alimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingreso_alimento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`ingreso_alimento_AFTER_INSERT` AFTER INSERT ON `ingreso_alimento` FOR EACH ROW
begin
    declare varAlimentoAnimal decimal(20,4);
          
     
    if new.id_corral = "" then 
		
        select 	new.total_alimento / animales_pendientes
        into	varAlimentoAnimal 
        from   	recepcion
        where  	numero_lote = new.numero_lote;
     
UPDATE animal 
SET 
    total_alimento = COALESCE(total_alimento, 0.00) + varAlimentoAnimal,
    costo_alimento = COALESCE(costo_alimento, 0.00) + (varAlimentoAnimal * new.costo_unitario),
    fecha_ultima_comida = new.fecha
WHERE
    numero_lote = new.numero_lote;

		UPDATE recepcion 
SET 
    total_alimento = COALESCE(total_alimento, 0.00) + new.total_alimento
WHERE
    new.numero_lote = recepcion.numero_lote;


    end if;    
    
    if new.numero_lote  = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal, corral_animal
        where  	corral_animal.id_corral		=	new.id_corral
        and     corral_animal.id_animal		=	animal.id_animal;

UPDATE animal 
SET 
    total_alimento = COALESCE(total_alimento, 0.00) + varAlimentoAnimal,
    costo_alimento = COALESCE(costo_alimento, 0.00) + (varAlimentoAnimal * new.costo_unitario),
    fecha_ultima_comida = new.fecha
WHERE
    id_animal IN (SELECT 
            id_animal
        FROM
            corral_animal
        WHERE
            corral_animal.id_corral = new.id_corral);

		call animalesPorCorral(new.id_corral);
    end if;
        /*Replicar */
    DELETE FROM repl_ingreso_alimento
	WHERE id_ingreso_alimento = NEW.id_ingreso_alimento;

	INSERT INTO repl_ingreso_alimento
	SELECT NEW.id_ingreso_alimento, NOW(), 'PE'; 
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`ingreso_alimento_AFTER_UPDATE` 
AFTER UPDATE ON `ingreso_alimento` 
FOR EACH ROW
Begin
    /*Replicar */
    DELETE FROM repl_ingreso_alimento
	WHERE id_ingreso_alimento = NEW.id_ingreso_alimento;

	INSERT INTO repl_ingreso_alimento
	SELECT NEW.id_ingreso_alimento, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `medicina`
--

DROP TABLE IF EXISTS `medicina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicina` (
  `id_medicina` char(36) NOT NULL,
  `codigo` int(11) NOT NULL,
  `nombre` char(255) NOT NULL,
  `costo` decimal(20,4) DEFAULT NULL,
  `id_unidad` char(36) DEFAULT NULL,
  `presentacion` decimal(20,4) DEFAULT NULL,
  `costo_unitario` decimal(20,4) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_medicina`,`codigo`,`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicina`
--

LOCK TABLES `medicina` WRITE;
/*!40000 ALTER TABLE `medicina` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicina` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_BINS`
BEFORE INSERT ON `medicina`
FOR EACH ROW
BEGIN	

DECLARE varConteo INT(10);
    DECLARE	msg	CHAR(255);
	
	SELECT	COUNT(*)
    INTO	varConteo
    FROM	medicina
    WHERE	codigo	=	NEW.codigo
    AND     status = 'S';
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El codigo "', NEW.codigo, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
    
    SELECT	COUNT(*)
    INTO	varConteo
    FROM	medicina
    WHERE	nombre	=	NEW.nombre
    AND     status  = 'S';
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El nombre "', NEW.nombre, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
 
-- Envio a FTP
	DELETE FROM repl_medicina
	WHERE	id_medicina	=	NEW.id_medicina
	AND		codigo		=	NEW.codigo;

    INSERT INTO repl_medicina
	SELECT NEW.id_medicina, NEW.codigo, NOW(), 'PE';
 -- Envio a FTP
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_AINS` AFTER INSERT ON `medicina` FOR EACH ROW BEGIN
	

 -- Envio a FTP
DELETE FROM repl_medicina
WHERE id_medicina = NEW.id_medicina
AND codigo = NEW.codigo;

INSERT INTO repl_medicina
SELECT NEW.id_medicina, NEW.codigo, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_AUPD` AFTER UPDATE ON `medicina` FOR EACH ROW BEGIN	
	
-- Envio a FTP
DELETE FROM repl_medicina
WHERE id_medicina = NEW.id_medicina
AND codigo = NEW.codigo;

INSERT INTO repl_medicina
SELECT NEW.id_medicina, NEW.codigo, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `medicina_animal`
--

DROP TABLE IF EXISTS `medicina_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicina_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_medicina_animal` char(36) NOT NULL,
  `id_medicina` char(36) DEFAULT NULL,
  `id_animal` char(36) DEFAULT NULL,
  `dosis` decimal(20,4) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `costo` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id_medicina_animal`,`id_rancho`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicina_animal`
--

LOCK TABLES `medicina_animal` WRITE;
/*!40000 ALTER TABLE `medicina_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicina_animal` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_animal_AINS`
AFTER INSERT ON `medicina_animal`
FOR EACH ROW
BEGIN
	DECLARE varIdCorral CHAR(36);
    
    SELECT ca.id_corral INTO varIdCorral
	FROM animal AS a, medicina_animal AS ma, corral_animal AS ca
	WHERE ma.id_animal = a.id_animal
	AND ca.id_animal = a.id_animal
	AND ma.id_animal = NEW.id_animal
    LIMIT 1;
	
	CALL animalesPorCorral(varIdCorral);

 -- Envio a FTP
DELETE FROM repl_medicina_animal
WHERE id_rancho = NEW.id_rancho
AND id_medicina_animal = NEW.id_medicina_animal;

INSERT INTO repl_medicina_animal
SELECT NEW.id_rancho, NEW.id_medicina_animal, NOW(), 'PE';
 -- Envio a FTP
/*
	SET	NEW.costo = ( SELECT rancho_medicina.costo_promedio
					 FROM   rancho_medicina
	                 WHERE  rancho_medicina.id_rancho	=	NEW.id_rancho
	                 AND    rancho_medicina.id_medicina	=	NEW.id_medicina);
*/
	UPDATE rancho_medicina
	SET	   existencia	=	existencia - NEW.dosis
	WHERE  rancho_medicina.id_rancho	=	NEW.id_rancho
	AND	   rancho_medicina.id_medicina	=	NEW.id_medicina;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_animal_AUPD`
AFTER UPDATE ON `medicina_animal`
FOR EACH ROW
BEGIN	
 -- Envio a FTP
DELETE FROM repl_medicina_animal
WHERE id_rancho = NEW.id_rancho
AND id_medicina_animal = NEW.id_medicina_animal;

INSERT INTO repl_medicina_animal
SELECT NEW.id_rancho, NEW.id_medicina_animal, NOW(), 'PE';
 -- Envio a FTP
	IF @DISABLE_TRIGER = 0 THEN
    
		UPDATE rancho_medicina
		SET	   existencia	=	existencia + OLD.dosis
		WHERE  rancho_medicina.id_rancho	=	OLD.id_rancho
		AND	   rancho_medicina.id_medicina =	OLD.id_medicina;

		UPDATE rancho_medicina
		SET	   existencia	=	existencia - NEW.dosis
		WHERE  rancho_medicina.id_rancho	=	NEW.id_rancho
		AND	   rancho_medicina.id_medicina	=	NEW.id_medicina;
        
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_animal_ADEL`
AFTER DELETE ON `medicina_animal`
FOR EACH ROW
BEGIN	
 -- Envio a FTP
	DELETE FROM repl_medicina_animal
	WHERE	id_rancho			=	OLD.id_rancho
	AND		id_medicina_animal	=	OLD.id_medicina_animal;		
 -- Envio a FTP
	
	UPDATE rancho_medicina
	SET	   existencia	=	existencia + OLD.dosis
	WHERE  rancho_medicina.id_rancho	=	OLD.id_rancho
	AND	   rancho_medicina.id_medicina	=	OLD.id_medicina;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `medicina_tratamiento`
--

DROP TABLE IF EXISTS `medicina_tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicina_tratamiento` (
  `id_tratamiento` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL,
  `dosis` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id_tratamiento`,`id_medicina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicina_tratamiento`
--

LOCK TABLES `medicina_tratamiento` WRITE;
/*!40000 ALTER TABLE `medicina_tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicina_tratamiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_tratamiento_AINS`
AFTER INSERT ON `medicina_tratamiento`
FOR EACH ROW
BEGIN	
 -- Envio a FTP
DELETE FROM repl_medicina_tratamiento
WHERE id_tratamiento = NEW.id_tratamiento
AND id_medicina = NEW.id_medicina;

INSERT INTO repl_medicina_tratamiento
SELECT NEW.id_tratamiento, NEW.id_medicina, NOW(), 'PE';
 -- Envio a FTP
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `medicina_tratamiento_AUPD`
AFTER UPDATE ON `medicina_tratamiento`
FOR EACH ROW
BEGIN	
 -- Envio a FTP
DELETE FROM repl_medicina_tratamiento
WHERE id_tratamiento = NEW.id_tratamiento
AND id_medicina = NEW.id_medicina;

INSERT INTO repl_medicina_tratamiento
SELECT NEW.id_tratamiento, NEW.id_medicina, NOW(), 'PE';
 -- Envio a FTP
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `id_rancho_origen` char(36) DEFAULT NULL,
  `id_corral_origen` char(36) DEFAULT NULL,
  `id_rancho_destino` char(36) DEFAULT NULL,
  `id_corral_destino` char(36) DEFAULT NULL,
  `id_clase_movimiento` int(11) DEFAULT NULL,
  `numero_pedido` char(255) DEFAULT NULL,
  `id_cliente` char(36) DEFAULT NULL,
  `necropcia` char(255) DEFAULT NULL,
  `dx_muerte` char(255) DEFAULT NULL,
  `etapa_reproductiva` char(255) DEFAULT NULL,
  `causa_entrada` char(255) DEFAULT NULL,
  `observacion` char(255) DEFAULT NULL,
  `peso` decimal(12,4) DEFAULT NULL,
  `fecha_reg` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_concepto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `movimiento_AINS` AFTER INSERT ON `movimiento` FOR EACH ROW BEGIN
-- Envio a FTP
DELETE FROM repl_movimiento
WHERE id_rancho = NEW.id_rancho
AND id_movimiento = NEW.id_movimiento
AND id_concepto = NEW.id_concepto;

INSERT INTO repl_movimiento
SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NOW(), 'PE';
-- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `movimiento_AUPD` AFTER UPDATE ON `movimiento` FOR EACH ROW BEGIN	
-- Envio a FTP
DELETE FROM repl_movimiento
WHERE id_rancho = NEW.id_rancho
AND id_movimiento = NEW.id_movimiento
AND id_concepto = NEW.id_concepto;

INSERT INTO repl_movimiento
SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NOW(), 'PE';
-- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `movimiento_animal`
--

DROP TABLE IF EXISTS `movimiento_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimiento_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_concepto`,`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_animal`
--

LOCK TABLES `movimiento_animal` WRITE;
/*!40000 ALTER TABLE `movimiento_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimiento_animal` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`movimiento_animal_AFTER_INSERT` 
AFTER INSERT ON `movimiento_animal` 
FOR EACH ROW
begin
DELETE FROM repl_movimiento_animal
WHERE id_rancho = NEW.id_rancho
AND id_movimiento = NEW.id_movimiento
AND id_concepto = NEW.id_concepto
AND id_animal = NEW.id_animal;

INSERT INTO repl_movimiento_animal
SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NEW.id_animal, NOW(), 'PE';
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`movimiento_animal_AFTER_UPDATE` 
AFTER UPDATE ON `movimiento_animal` 
FOR EACH ROW
begin
/* replication */
DELETE FROM repl_movimiento_animal
WHERE id_rancho = NEW.id_rancho
AND id_movimiento = NEW.id_movimiento
AND id_concepto = NEW.id_concepto
AND id_animal = NEW.id_animal;

INSERT INTO repl_movimiento_animal
SELECT NEW.id_rancho, NEW.id_movimiento, NEW.id_concepto, NEW.id_animal, NOW(), 'PE';
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `municipio`
--

DROP TABLE IF EXISTS `municipio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `municipio` (
  `id_estado` char(36) NOT NULL,
  `id_ciudad` char(36) NOT NULL,
  `id_municipio` char(36) NOT NULL,
  `descripcion_municipio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_estado`,`id_ciudad`,`id_municipio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipio`
--

LOCK TABLES `municipio` WRITE;
/*!40000 ALTER TABLE `municipio` DISABLE KEYS */;
INSERT INTO `municipio` VALUES ('1','1','1','Aguascalientes'),('1','2','3','Calvillo'),('1','3','11','San Francisco de los Romo'),('1','4','5','Jesús María'),('1','5','6','Pabellón de Arteaga'),('1','6','7','Rincón de Romos'),('1','7','2','Asientos'),('1','8','9','Tepezalá'),('1','9','4','Cosío'),('10','1','5','Durango'),('10','10','38','Vicente Guerrero'),('10','11','23','Pueblo Nuevo'),('10','12','18','El Oro'),('10','2','7','Gómez Palacio'),('10','3','12','Lerdo'),('10','4','32','Santiago Papasquiaro'),('10','5','1','Canatlán'),('10','6','28','San Juan del Río'),('10','7','21','Peñón Blanco'),('10','8','20','Pánuco de Coronado'),('10','9','16','Nombre de Dios'),('11','1','2','Acámbaro'),('11','10','31','San Francisco del Rincón'),('11','11','37','Silao de la Victoria'),('11','12','19','Jerécuaro'),('11','13','36','Santiago Maravatío'),('11','14','26','Romita'),('11','15','38','Tarandacuao'),('11','16','16','Huanímaro'),('11','17','9','Comonfort'),('11','18','41','Uriangato'),('11','19','23','Pénjamo'),('11','2','3','San Miguel de Allende'),('11','20','12','Cuerámaro'),('11','21','9','Comonfort'),('11','22','33','San Luis de la Paz'),('11','23','42','Valle de Santiago'),('11','24','1','Abasolo'),('11','25','7','Celaya'),('11','26','44','Villagrán'),('11','27','46','Yuriria'),('11','28','5','Apaseo el Grande'),('11','29','25','Purísima del Rincón'),('11','3','7','Celaya'),('11','30','28','Salvatierra'),('11','31','15','Guanajuato'),('11','32','32','San José Iturbide'),('11','33','4','Apaseo el Alto'),('11','34','8','Manuel Doblado'),('11','35','18','Jaral del Progreso'),('11','36','29','San Diego de la Unión'),('11','37','35','Santa Cruz de Juventino Rosas'),('11','38','13','Doctor Mora'),('11','39','14','Dolores Hidalgo Cuna de la Independencia Nacional'),('11','4','11','Cortazar'),('11','5','15','Guanajuato'),('11','6','17','Irapuato'),('11','7','20','León'),('11','8','21','Moroleón'),('11','9','27','Salamanca'),('12','1','1','Acapulco de Juárez'),('12','10','6','Apaxtla'),('12','11','50','Pungarabato'),('12','12','15','Buenavista de Cuéllar'),('12','13','27','Cutzamala de Pinzón'),('12','14','22','Coyuca de Catalán'),('12','15','39','Juan R. Escudero'),('12','16','21','Coyuca de Benítez'),('12','17','45','Olinalá'),('12','18','77','Marquelia'),('12','19','75','Eduardo Neri'),('12','2','29','Chilpancingo de los Bravo'),('12','20','57','Técpan de Galeana'),('12','21','48','Petatlán'),('12','22','68','La Unión de Isidoro Montes de Oca'),('12','23','57','Técpan de Galeana'),('12','24','58','Teloloapan'),('12','25','57','Técpan de Galeana'),('12','26','34','Huitzuco de los Figueroa'),('12','27','61','Tixtla de Guerrero'),('12','28','59','Tepecoacuilco de Trujano'),('12','29','53','San Marcos'),('12','3','35','Iguala de la Independencia'),('12','30','13','Azoyú'),('12','31','67','Tlapehuala'),('12','32','52','San Luis Acatlán'),('12','33','28','Chilapa de Álvarez'),('12','34','66','Tlapa de Comonfort'),('12','35','65','Tlalixtaquilla de Maldonado'),('12','36','23','Cuajinicuilapa'),('12','37','33','Huamuxtitlán'),('12','38','30','Florencio Villarreal'),('12','39','29','Chilpancingo de los Bravo'),('12','4','55','Taxco de Alarcón'),('12','40','18','Copala'),('12','41','38','Zihuatanejo de Azueta'),('12','5','7','Arcelia'),('12','6','12','Ayutla de los Libres'),('12','7','11','Atoyac de Álvarez'),('12','9','14','Benito Juárez'),('13','1','3','Actopan'),('13','10','69','Tizayuca'),('13','11','56','Santiago Tulantepec de Lugo Guerrero'),('13','12','30','Ixmiquilpan'),('13','13','63','Tepeji del Río de Ocampo'),('13','14','76','Tula de Allende'),('13','15','61','Tepeapulco'),('13','2','8','Apan'),('13','3','48','Pachuca de Soto'),('13','4','61','Tepeapulco'),('13','5','76','Tula de Allende'),('13','6','77','Tulancingo de Bravo'),('13','7','84','Zimapán'),('13','8','28','Huejutla de Reyes'),('13','9','74','Tlaxcoapan'),('14','1','6','Ameca'),('14','10','120','Zapopan'),('14','13','108','Tuxpan'),('14','14','105','Tototlán'),('14','15','72','San Diego de Alejandría'),('14','16','21','Casimiro Castillo'),('14','17','13','Atotonilco el Alto'),('14','18','46','Jalostotitlán'),('14','19','66','Poncitlán'),('14','2','23','Zapotlán el Grande'),('14','20','8','Arandas'),('14','21','84','Talpa de Allende'),('14','22','36','Etzatlán'),('14','23','82','Sayula'),('14','24','3','Ahualulco de Mercado'),('14','25','15','Autlán de Navarro'),('14','26','55','Magdalena'),('14','27','74','San Julián'),('14','28','24','Cocula'),('14','29','37','El Grullo'),('14','30','78','San Miguel el Alto'),('14','31','83','Tala'),('14','32','18','La Barca'),('14','33','47','Jamay'),('14','34','118','Yahualica de González Gallo'),('14','35','25','Colotlán'),('14','36','22','Cihuatlán'),('14','37','121','Zapotiltic'),('14','38','114','Villa Corona'),('14','39','91','Teocaltiche'),('14','4','53','Lagos de Moreno'),('14','41','94','Tequila'),('14','42','70','El Salto'),('14','43','70','El Salto'),('14','44','50','Jocotepec'),('14','45','87','Tecalitlán'),('14','46','30','Chapala'),('14','47','30','Chapala'),('14','48','125','San Ignacio Cerro Gordo'),('14','5','63','Ocotlán'),('14','50','42','Huejuquilla el Alto'),('14','51','116','Villa Hidalgo'),('14','52','109','Unión de San Antonio'),('14','53','70','El Salto'),('14','54','85','Tamazula de Gordiano'),('14','55','2','Acatlán de Juárez'),('14','56','111','Valle de Guadalupe'),('14','7','73','San Juan de los Lagos'),('14','8','93','Tepatitlán de Morelos'),('14','9','98','San Pedro Tlaquepaque'),('15','1','13','Atizapán de Zaragoza'),('15','10','58','Nezahualcóyotl'),('15','11','60','Nicolás Romero'),('15','12','81','Tecámac'),('15','13','95','Tepotzotlán'),('15','14','104','Tlalnepantla de Baz'),('15','15','108','Tultepec'),('15','16','109','Tultitlán'),('15','17','24','Cuautitlán'),('15','18','39','Ixtapaluca'),('15','19','99','Texcoco'),('15','2','31','Chimalhuacán'),('15','20','106','Toluca'),('15','21','122','Valle de Chalco Solidaridad'),('15','22','82','Tejupilco'),('15','23','25','Chalco'),('15','24','8','Amatepec'),('15','26','53','Melchor Ocampo'),('15','27','29','Chicoloapan'),('15','28','19','Capulhuac'),('15','29','50','Juchitepec'),('15','3','20','Coacalco de Berriozábal'),('15','30','96','Tequixquiac'),('15','31','115','Xonacatlán'),('15','32','76','San Mateo Atenco'),('15','36','30','Chiconcuac'),('15','39','5','Almoloya de Juárez'),('15','4','121','Cuautitlán Izcalli'),('15','40','62','Ocoyoacac'),('15','41','120','Zumpango'),('15','5','33','Ecatepec de Morelos'),('15','6','37','Huixquilucan'),('15','7','70','La Paz'),('15','8','54','Metepec'),('15','9','57','Naucalpan de Juárez'),('16','1','6','Apatzingán'),('16','10','107','Zacapu'),('16','11','108','Zamora'),('16','12','112','Zitácuaro'),('16','13','65','Paracho'),('16','14','85','Tangancícuaro'),('16','15','50','Maravatío'),('16','16','110','Zinapécuaro'),('16','17','71','Puruándiro'),('16','18','106','Yurécuaro'),('16','19','38','Huetamo'),('16','2','75','Los Reyes'),('16','20','82','Tacámbaro'),('16','21','52','Lázaro Cárdenas'),('16','22','52','Lázaro Cárdenas'),('16','23','45','Jiquilpan'),('16','24','98','Tuxpan'),('16','25','19','Cotija'),('16','26','55','Múgica'),('16','27','20','Cuitzeo'),('16','3','34','Hidalgo'),('16','4','43','Jacona'),('16','5','69','La Piedad'),('16','6','53','Morelia'),('16','7','66','Pátzcuaro'),('16','8','76','Sahuayo'),('16','9','102','Uruapan'),('17','1','6','Cuautla'),('17','2','7','Cuernavaca'),('17','3','31','Zacatepec'),('17','4','12','Jojutla'),('17','5','17','Puente de Ixtla'),('17','6','24','Tlaltizapán de Zapata'),('17','7','31','Zacatepec'),('17','8','25','Tlaquiltenango'),('18','1','15','Santiago Ixcuintla'),('18','10','12','San Blas'),('18','11','6','Ixtlán del Río'),('18','12','20','Bahía de Banderas'),('18','13','4','Compostela'),('18','14','8','Xalisco'),('18','15','13','San Pedro Lagunillas'),('18','16','4','Compostela'),('18','17','7','Jala'),('18','18','2','Ahuacatlán'),('18','2','17','Tepic'),('18','3','18','Tuxpan'),('18','4','1','Acaponeta'),('18','5','16','Tecuala'),('18','6','4','Compostela'),('18','7','17','Tepic'),('18','8','15','Santiago Ixcuintla'),('18','9','11','Ruíz'),('19','1','6','Apodaca'),('19','10','48','Santa Catarina'),('19','11','14','Doctor Arroyo'),('19','12','12','Ciénega de Flores'),('19','13','29','Hualahuises'),('19','15','9','Cadereyta Jiménez'),('19','17','49','Santiago'),('19','18','49','Santiago'),('19','2','19','San Pedro Garza García'),('19','20','5','Anáhuac'),('19','21','18','García'),('19','22','31','Juárez'),('19','3','21','General Escobedo'),('19','4','26','Guadalupe'),('19','5','33','Linares'),('19','6','38','Montemorelos'),('19','7','39','Monterrey'),('19','8','44','Sabinas Hidalgo'),('19','9','46','San Nicolás de los Garza'),('2','1','1','Ensenada'),('2','2','2','Mexicali'),('2','3','3','Tecate'),('2','4','4','Tijuana'),('2','5','5','Playas de Rosarito'),('2','6','1','Ensenada'),('2','7','2','Mexicali'),('20','1','43','Heroica Ciudad de Juchitán de Zaragoza'),('20','11','483','Santiago Suchilquitongo'),('20','12','134','San Felipe Jalapa de Díaz'),('20','13','413','Santa María Huatulco'),('20','14','73','Putla Villa de Guerrero'),('20','15','21','Cosolapa'),('20','16','551','Tlacolula de Matamoros'),('20','17','298','San Pablo Villa de Mitla'),('20','18','62','Natividad'),('20','19','545','Teotitlán de Flores Magón'),('20','2','67','Oaxaca de Juárez'),('20','20','413','Santa María Huatulco'),('20','21','177','San Juan Bautista Cuicatlán'),('20','22','277','Villa Sola de Vega'),('20','23','68','Ocotlán de Morelos'),('20','24','565','Villa de Zaachila'),('20','25','59','Miahuatlán de Porfirio Díaz'),('20','26','557','Unión Hidalgo'),('20','27','64','Nejapa de Madero'),('20','28','318','San Pedro Mixtepec -Dto. 22 -'),('20','29','377','Santa Cruz Itundujia'),('20','3','79','Salina Cruz'),('20','30','25','Chahuites'),('20','31','28','Heroica Ciudad de Ejutla de Crespo'),('20','32','327','San Pedro Tapanatepec'),('20','33','2','Acatlán de Pérez Figueroa'),('20','34','540','Villa de Tamazulápam del Progreso'),('20','35','180','San Juan Bautista Lo de Soto'),('20','36','185','San Juan Cacahuatepec'),('20','37','333','San Pedro Totolápam'),('20','38','269','San Miguel el Grande'),('20','39','570','Zimatlán de Álvarez'),('20','4','184','San Juan Bautista Tuxtepec'),('20','40','294','San Pablo Huitzo'),('20','41','150','San Francisco Telixtlahuaca'),('20','42','55','Mariscala de Juárez'),('20','43','482','Santiago Pinotepa Nacional'),('20','44','467','Santiago Jamiltepec'),('20','45','324','San Pedro Pochutla'),('20','46','397','Heroica Ciudad de Tlaxiaco'),('20','47','559','San Juan Bautista Valle Nacional'),('20','48','10','El Barrio de la Soledad'),('20','49','14','Ciudad Ixtepec'),('20','5','57','Matías Romero Avendaño'),('20','50','469','Santiago Juxtlahuaca'),('20','51','348','San Sebastián Tecomaxtlahuaca'),('20','52','6','Asunción Nochixtlán'),('20','53','143','San Francisco Ixhuatán'),('20','54','124','San Blas Atempa'),('20','55','515','Santo Domingo Tehuantepec'),('20','56','23','Cuilápam de Guerrero'),('20','57','350','San Sebastián Tutla'),('20','58','390','Santa Lucía del Camino'),('20','59','107','San Antonio de la Cal'),('20','6','39','Heroica Ciudad de Huajuapan de León'),('20','7','44','Loma Bonita'),('20','8','318','San Pedro Mixtepec -Dto. 22 -'),('20','9','334','Villa de Tututepec de Melchor Ocampo'),('21','1','19','Atlixco'),('21','10','3','Acatlán'),('21','11','41','Cuautlancingo'),('21','12','164','Tepeaca'),('21','13','154','Tecamachalco'),('21','14','208','Zacatlán'),('21','15','197','Xicotepec'),('21','16','45','Chalchicomula de Sesma'),('21','17','15','Amozoc'),('21','2','85','Izúcar de Matamoros'),('21','3','114','Puebla'),('21','4','132','San Martín Texmelucan'),('21','5','156','Tehuacán'),('21','6','174','Teziutlán'),('21','7','119','San Andrés Cholula'),('21','8','140','San Pedro Cholula'),('21','9','71','Huauchinango'),('22','1','14','Querétaro'),('22','2','16','San Juan del Río'),('22','4','6','Corregidora'),('23','1','5','Benito Juárez'),('23','2','1','Cozumel'),('23','3','2','Felipe Carrillo Puerto'),('23','4','4','Othón P. Blanco'),('23','5','8','Solidaridad'),('23','6','7','Lázaro Cárdenas'),('23','7','3','Isla Mujeres'),('23','8','10','Bacalar'),('24','1','13','Ciudad Valles'),('24','10','8','Cerritos'),('24','11','40','Tamuín'),('24','12','36','Tamasopo'),('24','13','10','Ciudad del Maíz'),('24','14','7','Cedral'),('24','15','43','Tierra Nueva'),('24','16','50','Villa de Reyes'),('24','17','11','Ciudad Fernández'),('24','18','37','Tamazunchale'),('24','19','32','Santa María del Río'),('24','2','16','Ebano'),('24','20','58','El Naranjo'),('24','3','20','Matehuala'),('24','4','24','Rioverde'),('24','5','28','San Luis Potosí'),('24','6','35','Soledad de Graciano Sánchez'),('24','7','15','Charcas'),('24','8','25','Salinas'),('24','9','5','Cárdenas'),('25','1','1','Ahome'),('25','10','1','Ahome'),('25','11','7','Choix'),('25','12','12','Mazatlán'),('25','13','17','Sinaloa'),('25','14','13','Mocorito'),('25','15','2','Angostura'),('25','16','10','El Fuerte'),('25','17','8','Elota'),('25','18','14','Rosario'),('25','19','17','Sinaloa'),('25','2','6','Culiacán'),('25','20','6','Culiacán'),('25','21','5','Cosalá'),('25','22','16','San Ignacio'),('25','23','1','Ahome'),('25','24','18','Navolato'),('25','3','9','Escuinapa'),('25','4','11','Guasave'),('25','5','12','Mazatlán'),('25','6','15','Salvador Alvarado'),('25','7','18','Navolato'),('25','8','6','Culiacán'),('25','9','1','Ahome'),('26','1','55','San Luis Río Colorado'),('26','10','43','Nogales'),('26','11','48','Puerto Peñasco'),('26','12','19','Cananea'),('26','13','70','General Plutarco Elías Calles'),('26','14','36','Magdalena'),('26','2','2','Agua Prieta'),('26','3','17','Caborca'),('26','4','18','Cajeme'),('26','5','25','Empalme'),('26','6','29','Guaymas'),('26','7','30','Hermosillo'),('26','8','33','Huatabampo'),('26','9','42','Navojoa'),('27','1','2','Cárdenas'),('27','10','14','Paraíso'),('27','11','3','Centla'),('27','12','6','Cunduacán'),('27','13','8','Huimanguillo'),('27','2','4','Centro'),('27','3','5','Comalcalco'),('27','4','7','Emiliano Zapata'),('27','5','10','Jalpa de Méndez'),('27','7','12','Macuspana'),('27','8','17','Tenosique'),('27','9','16','Teapa'),('28','1','3','Altamira'),('28','10','38','Tampico'),('28','11','41','Victoria'),('28','12','12','González'),('28','13','17','Jaumave'),('28','14','15','Gustavo Díaz Ordaz'),('28','15','12','González'),('28','16','43','Xicoténcatl'),('28','17','25','Miguel Alemán'),('28','18','37','Soto la Marina'),('28','19','39','Tula'),('28','2','7','Camargo'),('28','20','14','Guerrero'),('28','21','40','Valle Hermoso'),('28','3','9','Ciudad Madero'),('28','4','21','El Mante'),('28','5','22','Matamoros'),('28','6','27','Nuevo Laredo'),('28','7','32','Reynosa'),('28','8','33','Río Bravo'),('28','9','35','San Fernando'),('29','1','5','Apizaco'),('29','2','25','San Pablo del Monte'),('29','3','33','Tlaxcala'),('29','4','13','Huamantla'),('29','5','6','Calpulalpan'),('29','6','10','Chiautempan'),('3','1','1','Comondú'),('3','10','2','Mulegé'),('3','11','2','Mulegé'),('3','2','3','La Paz'),('3','3','8','Los Cabos'),('3','4','8','Los Cabos'),('3','5','9','Loreto'),('3','6','1','Comondú'),('3','7','3','La Paz'),('3','8','2','Mulegé'),('3','9','2','Mulegé'),('30','1','3','Acayucan'),('30','10','87','Xalapa'),('30','11','108','Minatitlán'),('30','12','118','Orizaba'),('30','13','124','Papantla'),('30','14','131','Poza Rica de Hidalgo'),('30','15','141','San Andrés Tuxtla'),('30','16','189','Tuxpan'),('30','17','193','Veracruz'),('30','18','174','Tierra Blanca'),('30','19','45','Cosamaloapan de Carpio'),('30','2','13','Naranjos Amatlán'),('30','20','208','Carlos A. Carrillo'),('30','21','123','Pánuco'),('30','22','152','Tampico Alto'),('30','23','161','Tempoal'),('30','24','155','Tantoyuca'),('30','25','69','Gutiérrez Zamora'),('30','26','129','Platón Sánchez'),('30','27','94','Juan Rodríguez Clara'),('30','28','71','Huatusco'),('30','29','85','Ixtaczoquitlán'),('30','3','28','Boca del Río'),('30','30','138','Río Blanco'),('30','31','77','Isla'),('30','32','53','Cuitláhuac'),('30','33','68','Fortín'),('30','34','11','Alvarado'),('30','35','16','La Antigua'),('30','36','26','Banderilla'),('30','37','14','Amatlán de los Reyes'),('30','38','130','Playa Vicente'),('30','39','10','Altotonga'),('30','4','38','Coatepec'),('30','40','73','Hueyapan de Ocampo'),('30','41','111','Moloacán'),('30','42','143','Santiago Tuxtla'),('30','43','72','Huayacocotla'),('30','44','126','Paso de Ovejas'),('30','45','32','Catemaco'),('30','46','115','Nogales'),('30','47','61','Las Choapas'),('30','48','21','Atoyac'),('30','49','40','Coatzintla'),('30','5','204','Agua Dulce'),('30','50','15','Angel R. Cabada'),('30','51','211','San Rafael'),('30','52','176','Tlacojalpan'),('30','53','48','Cosoleacaque'),('30','54','97','Lerdo de Tejada'),('30','55','175','Tihuatlán'),('30','56','21','Atoyac'),('30','57','74','Huiloapan de Cuauhtémoc'),('30','58','33','Cazones de Herrera'),('30','59','197','Yecuatla'),('30','6','39','Coatzacoalcos'),('30','60','148','Soledad de Doblado'),('30','61','34','Cerro Azul'),('30','62','173','Tezonapa'),('30','66','141','San Andrés Tuxtla'),('30','67','205','El Higo'),('30','68','125','Paso del Macho'),('30','69','183','Tlapacoyan'),('30','7','44','Córdoba'),('30','8','207','Tres Valles'),('30','9','89','Jáltipan'),('31','1','50','Mérida'),('31','2','96','Tizimín'),('31','3','89','Ticul'),('31','4','52','Motul'),('31','5','102','Valladolid'),('31','6','41','Kanasín'),('32','1','10','Fresnillo'),('32','10','51','Villa de Cos'),('32','11','34','Nochistlán de Mejía'),('32','12','5','Calera'),('32','13','49','Valparaíso'),('32','14','25','Luis Moya'),('32','15','33','Moyahua de Estrada'),('32','16','42','Sombrerete'),('32','17','19','Jalpa'),('32','18','24','Loreto'),('32','19','22','Juan Aldama'),('32','2','20','Jerez'),('32','3','56','Zacatecas'),('32','4','17','Guadalupe'),('32','5','39','Río Grande'),('32','6','8','Cuauhtémoc'),('32','7','36','Ojocaliente'),('32','8','54','Villa Hidalgo'),('32','9','55','Villanueva'),('4','1','2','Campeche'),('4','11','5','Hecelchakán'),('4','2','3','Carmen'),('4','3','1','Calkiní'),('4','4','11','Candelaria'),('4','5','9','Escárcega'),('4','6','3','Carmen'),('4','7','6','Hopelchén'),('4','8','4','Champotón'),('4','9','5','Hecelchakán'),('5','1','2','Acuña'),('5','10','33','San Pedro'),('5','11','35','Torreón'),('5','12','6','Castaños'),('5','13','9','Francisco I. Madero'),('5','14','7','Cuatro Ciénegas'),('5','15','21','Nadadores'),('5','16','27','Ramos Arizpe'),('5','17','22','Nava'),('5','18','38','Zaragoza'),('5','19','31','San Buenaventura'),('5','2','10','Frontera'),('5','20','20','Múzquiz'),('5','21','36','Viesca'),('5','22','19','Morelos'),('5','23','4','Arteaga'),('5','24','3','Allende'),('5','3','17','Matamoros'),('5','4','18','Monclova'),('5','5','24','Parras'),('5','6','25','Piedras Negras'),('5','7','28','Sabinas'),('5','8','30','Saltillo'),('5','9','32','San Juan de Sabinas'),('6','1','2','Colima'),('6','2','7','Manzanillo'),('6','3','9','Tecomán'),('6','4','10','Villa de Álvarez'),('6','5','1','Armería'),('7','1','19','Comitán de Domínguez'),('7','10','65','Palenque'),('7','11','59','Ocosingo'),('7','12','97','Tonalá'),('7','13','51','Mapastepec'),('7','14','75','Las Rosas'),('7','15','27','Chiapa de Corzo'),('7','16','15','Cacahoatán'),('7','17','61','Ocozocoautla de Espinosa'),('7','18','17','Cintalapa'),('7','19','68','Pichucalco'),('7','2','78','San Cristóbal de las Casas'),('7','20','89','Tapachula'),('7','21','69','Pijijiapan'),('7','22','74','Reforma'),('7','23','40','Huixtla'),('7','24','57','Motozintla'),('7','25','2','Acala'),('7','3','89','Tapachula'),('7','4','101','Tuxtla Gutiérrez'),('7','5','106','Venustiano Carranza'),('7','6','46','Jiquipilas'),('7','7','108','Villaflores'),('7','8','52','Las Margaritas'),('7','9','9','Arriaga'),('8','1','11','Camargo'),('8','10','17','Cuauhtémoc'),('8','11','2','Aldama'),('8','12','36','Jiménez'),('8','13','52','Ojinaga'),('8','14','6','Bachíniva'),('8','15','62','Saucillo'),('8','2','19','Chihuahua'),('8','3','17','Cuauhtémoc'),('8','4','21','Delicias'),('8','5','32','Hidalgo del Parral'),('8','6','37','Juárez'),('8','7','50','Nuevo Casas Grandes'),('8','9','40','Madera'),('9','1','10','Álvaro Obregón'),('9','10','8','La Magdalena Contreras'),('9','11','16','Miguel Hidalgo'),('9','12','9','Milpa Alta'),('9','13','11','Tláhuac'),('9','14','12','Tlalpan'),('9','15','17','Venustiano Carranza'),('9','16','13','Xochimilco'),('9','2','2','Azcapotzalco'),('9','3','14','Benito Juárez'),('9','4','3','Coyoacán'),('9','5','4','Cuajimalpa de Morelos'),('9','6','15','Cuauhtémoc'),('9','7','5','Gustavo A. Madero'),('9','8','6','Iztacalco'),('9','9','7','Iztapalapa');
/*!40000 ALTER TABLE `municipio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pais` (
  `id_pais` char(36) NOT NULL,
  `descripcion` char(100) DEFAULT NULL,
  PRIMARY KEY (`id_pais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES ('bc300667-bead-11e4-a9a2-002258cc1d65','Mexico');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedor` (
  `id_proveedor` char(36) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  `id_pais` char(36) DEFAULT NULL,
  `id_estado` char(36) DEFAULT NULL,
  `id_ciudad` char(36) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` char(45) DEFAULT NULL,
  `telefono` char(20) DEFAULT NULL,
  `p_fisica_moral` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `proveedor_AINS`
AFTER INSERT ON `proveedor`
FOR EACH ROW
BEGIN
	
 -- Envio a FTP
DELETE FROM repl_proveedor
WHERE id_proveedor = NEW.id_proveedor;

INSERT INTO repl_proveedor
SELECT NEW.id_proveedor, NOW(), 'PE';
-- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `proveedor_AUPD`
AFTER UPDATE ON `proveedor`
FOR EACH ROW
BEGIN	

-- Envio a FTP
DELETE FROM repl_proveedor
WHERE id_proveedor = NEW.id_proveedor;

INSERT INTO repl_proveedor
SELECT NEW.id_proveedor, NOW(), 'PE';
-- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rancho`
--

DROP TABLE IF EXISTS `rancho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rancho` (
  `id_rancho` char(36) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  `con_traspaso_entrada` char(36) DEFAULT NULL,
  `con_traspaso_salida` char(36) DEFAULT NULL,
  `con_salida` char(36) DEFAULT NULL,
  `con_muerte` char(36) DEFAULT NULL,
  `con_pesaje` char(36) DEFAULT NULL,
  `id_corral_hospital` char(36) DEFAULT NULL,
  `actividad` char(255) DEFAULT NULL,
  `id_estado` char(36) DEFAULT NULL,
  `id_ciudad` char(36) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rancho`
--

LOCK TABLES `rancho` WRITE;
/*!40000 ALTER TABLE `rancho` DISABLE KEYS */;
/*!40000 ALTER TABLE `rancho` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `rancho_AINS` AFTER INSERT ON `rancho` FOR EACH ROW BEGIN

 -- Envio a FTP
DELETE FROM repl_rancho
WHERE id_rancho = NEW.id_rancho;

INSERT INTO repl_rancho
SELECT NEW.id_rancho, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `rancho_AUPD` AFTER UPDATE ON `rancho` FOR EACH ROW BEGIN	

-- Envio a FTP
DELETE FROM repl_rancho
WHERE id_rancho = NEW.id_rancho;

INSERT INTO repl_rancho
SELECT NEW.id_rancho, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rancho_medicina`
--

DROP TABLE IF EXISTS `rancho_medicina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rancho_medicina` (
  `id_rancho` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL,
  `existencia_inicial` int(11) DEFAULT NULL,
  `existencia` int(11) DEFAULT NULL,
  `costo_promedio` decimal(20,4) DEFAULT NULL,
  `ultimo_costo` decimal(20,4) DEFAULT NULL,
  `ultima_compra` datetime DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_medicina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rancho_medicina`
--

LOCK TABLES `rancho_medicina` WRITE;
/*!40000 ALTER TABLE `rancho_medicina` DISABLE KEYS */;
/*!40000 ALTER TABLE `rancho_medicina` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`rancho_medicina_AFTER_INSERT` 
AFTER INSERT ON `rancho_medicina` 
FOR EACH ROW
BEGIN
DELETE FROM repl_rancho_medicina
WHERE id_rancho = NEW.id_rancho
AND id_medicina = NEW.id_medicina;

INSERT INTO repl_rancho_medicina
SELECT NEW.id_rancho, NEW.id_medicina, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`rancho_medicina_AFTER_UPDATE` 
AFTER UPDATE ON `rancho_medicina` 
FOR EACH ROW
BEGIN
DELETE FROM repl_rancho_medicina
WHERE id_rancho = NEW.id_rancho
AND id_medicina = NEW.id_medicina;

INSERT INTO repl_rancho_medicina
SELECT NEW.id_rancho, NEW.id_medicina, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `raza`
--

DROP TABLE IF EXISTS `raza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `raza` (
  `id_raza` char(36) NOT NULL,
  `descripcion` char(255) NOT NULL,
  `seleccionar` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_raza`,`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raza`
--

LOCK TABLES `raza` WRITE;
/*!40000 ALTER TABLE `raza` DISABLE KEYS */;
INSERT INTO `raza` VALUES ('2ac668fc-1cd9-11e5-a670-0023187ffb93','Red Sindi','S','A'),('2acf7fb7-1cd9-11e5-a670-0023187ffb93','Herdford','S','A'),('2ad9f2d0-1cd9-11e5-a670-0023187ffb93','Holstein','S','A'),('2ae28c0b-1cd9-11e5-a670-0023187ffb93','Pardo Suizo','S','A'),('2aeac128-1cd9-11e5-a670-0023187ffb93','Jersey','S','A'),('2aef8a58-1cd9-11e5-a670-0023187ffb93','Normando','S','A'),('2afa44b8-1cd9-11e5-a670-0023187ffb93','Angus','S','A'),('2b0b792e-1cd9-11e5-a670-0023187ffb93','Charolais','S','A'),('2b111069-1cd9-11e5-a670-0023187ffb93','Simmental','S','A'),('2b1a9327-1cd9-11e5-a670-0023187ffb93','Limousin','S','A'),('31e175e3-cb9b-11e4-bb5f-3860779bbc63','Mixto','N','A'),('3e26de81-beae-11e4-a9a2-002258cc1d65','Brahaman','S','A'),('3e26e1ad-beae-11e4-a9a2-002258cc1d65','Cebu','S','A'),('3e26e25a-beae-11e4-a9a2-002258cc1d65','Guzerat','S','A'),('3e26e2fc-beae-11e4-a9a2-002258cc1d65','Gyr','S','A'),('3e26e3a1-beae-11e4-a9a2-002258cc1d65','Indubrasil','S','A'),('3e26e44f-beae-11e4-a9a2-002258cc1d65','Nelore','S','A'),('a1c84978-1aab-11e5-881a-0023187ffb93','Sardo','S','A'),('bc85dcee-1cd8-11e5-a670-0023187ffb93','Brahaman Rojo','S','A'),('d30ad493-6f88-11e5-b2d5-005056c00001','F1','S','E');
/*!40000 ALTER TABLE `raza` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `raza_AINS`
AFTER INSERT ON `raza`
FOR EACH ROW
BEGIN
	
 -- Envio a FTP
DELETE FROM repl_raza
WHERE id_raza = NEW.id_raza;

INSERT INTO repl_raza
SELECT NEW.id_raza, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `raza_AUPD`
AFTER UPDATE ON `raza`
FOR EACH ROW
BEGIN	

	-- Envio a FTP
DELETE FROM repl_raza
WHERE id_raza = NEW.id_raza;

INSERT INTO repl_raza
SELECT NEW.id_raza, NOW(), 'PE';
 -- Envio a FTP

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `recepcion`
--

DROP TABLE IF EXISTS `recepcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recepcion` (
  `id_recepcion` char(36) NOT NULL,
  `id_proveedor` char(36) DEFAULT NULL,
  `id_origen` char(36) DEFAULT NULL,
  `folio` varchar(45) DEFAULT NULL,
  `fecha_compra` datetime DEFAULT NULL,
  `fecha_recepcion` datetime DEFAULT NULL,
  `animales` int(10) DEFAULT NULL,
  `animales_pendientes` int(10) DEFAULT NULL,
  `peso_origen` decimal(20,4) DEFAULT NULL,
  `limite_merma` decimal(20,4) DEFAULT NULL,
  `merma` decimal(20,4) DEFAULT NULL,
  `porcentaje_merma` decimal(20,4) DEFAULT NULL,
  `peso_recepcion` decimal(20,4) DEFAULT NULL,
  `numero_lote` char(255) DEFAULT NULL,
  `costo_flete` decimal(20,4) DEFAULT NULL,
  `devoluciones` int(10) DEFAULT NULL,
  `causa_devolucion` varchar(45) DEFAULT NULL,
  `total_alimento` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id_recepcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recepcion`
--

LOCK TABLES `recepcion` WRITE;
/*!40000 ALTER TABLE `recepcion` DISABLE KEYS */;
/*!40000 ALTER TABLE `recepcion` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `recepcion_BEFORE_INSERT` 
BEFORE INSERT ON `recepcion` 
FOR EACH ROW
begin
DECLARE varConteo INT(10);
    DECLARE	msg	CHAR(255);
	
	SELECT 
    COUNT(*)
INTO varConteo FROM
    recepcion
WHERE
    folio = NEW.folio
        AND animales_pendientes > 0;
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El folio "', NEW.folio, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
    
    
SELECT 
    COUNT(*)
INTO varConteo FROM
    recepcion
WHERE
    numero_lote = NEW.numero_lote
        AND animales_pendientes > 0;
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El numero_lote "', NEW.numero_lote, '" todavía tiene animales.');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
 end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`recepcion_AFTER_INSERT` 
AFTER INSERT ON `recepcion` 
FOR EACH ROW
BEGIN 
DELETE FROM repl_recepcion
WHERE id_recepcion = NEW.id_recepcion
AND id_proveedor = NEW.id_proveedor
AND id_origen = NEW.id_origen;

INSERT INTO repl_recepcion
SELECT NEW.id_recepcion, NEW.id_proveedor, NEW.id_origen, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`recepcion_AFTER_UPDATE` 
AFTER UPDATE ON `recepcion` 
FOR EACH ROW
BEGIN
DELETE FROM repl_recepcion
WHERE id_recepcion = NEW.id_recepcion
AND id_proveedor = NEW.id_proveedor
AND id_origen = NEW.id_origen;

INSERT INTO repl_recepcion
SELECT NEW.id_recepcion, NEW.id_proveedor, NEW.id_origen, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `registro_empadre`
--

DROP TABLE IF EXISTS `registro_empadre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registro_empadre` (
  `id_registro_empadre` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `id_hembra` char(36) NOT NULL,
  `id_semental` char(36) NOT NULL,
  `status_gestacional` char(2) NOT NULL,
  `aborto` char(2) NOT NULL,
  `id_tipo_parto` char(36) DEFAULT NULL,
  `activo` char(2) DEFAULT NULL,
  PRIMARY KEY (`id_registro_empadre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_empadre`
--

LOCK TABLES `registro_empadre` WRITE;
/*!40000 ALTER TABLE `registro_empadre` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_empadre` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `registro_empadre_AUPD`
AFTER INSERT ON `registro_empadre`
FOR EACH ROW
BEGIN	
	
	-- si se marca como desactivo el registro de empadre
	-- se elimina el emparejamiento
	IF	NEW.activo	=	'N'	THEN
		
		UPDATE	animal
		SET		id_semental	=	'0'
		WHERE	id_animal	=	NEW.id_hembra;
	END IF;
	
	-- FTP
    DELETE FROM repl_registro_empadre
    WHERE id_registro_empadre = NEW.id_registro_empadre
    AND id_hembra = NEW.id_hembra
    AND id_semental = NEW.id_semental;
    
    INSERT INTO repl_registro_empadre
    SELECT NEW.id_registro_empadre, NEW.id_hembra, NEW.id_semental, NOW(), 'PE';

	-- FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`registro_empadre_AFTER_UPDATE` 
AFTER UPDATE ON `registro_empadre` 
FOR EACH ROW
BEGIN 
    DELETE FROM repl_registro_empadre
    WHERE id_registro_empadre = NEW.id_registro_empadre
    AND id_hembra = NEW.id_hembra
    AND id_semental = NEW.id_semental;
    
    INSERT INTO repl_registro_empadre
    SELECT NEW.id_registro_empadre, NEW.id_hembra, NEW.id_semental, NOW(), 'PE';
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `repl_animal`
--

DROP TABLE IF EXISTS `repl_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_animal`,`id_rancho`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_animal`
--

LOCK TABLES `repl_animal` WRITE;
/*!40000 ALTER TABLE `repl_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_cliente`
--

DROP TABLE IF EXISTS `repl_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_cliente` (
  `id_cliente` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` char(2) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_cliente`
--

LOCK TABLES `repl_cliente` WRITE;
/*!40000 ALTER TABLE `repl_cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_compra`
--

DROP TABLE IF EXISTS `repl_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_compra` (
  `id_rancho` char(36) NOT NULL,
  `id_compra` char(36) NOT NULL,
  `id_proveedor` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_compra`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_compra`
--

LOCK TABLES `repl_compra` WRITE;
/*!40000 ALTER TABLE `repl_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_concepto_movimiento`
--

DROP TABLE IF EXISTS `repl_concepto_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_concepto_movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_concepto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_concepto_movimiento`
--

LOCK TABLES `repl_concepto_movimiento` WRITE;
/*!40000 ALTER TABLE `repl_concepto_movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_concepto_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_control_gestacion`
--

DROP TABLE IF EXISTS `repl_control_gestacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_control_gestacion` (
  `id_control_gestacion` char(36) NOT NULL,
  `id_registro_empadre` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_control_gestacion`,`id_registro_empadre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_control_gestacion`
--

LOCK TABLES `repl_control_gestacion` WRITE;
/*!40000 ALTER TABLE `repl_control_gestacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_control_gestacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_corral`
--

DROP TABLE IF EXISTS `repl_corral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_corral` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_corral`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_corral`
--

LOCK TABLES `repl_corral` WRITE;
/*!40000 ALTER TABLE `repl_corral` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_corral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_corral_animal`
--

DROP TABLE IF EXISTS `repl_corral_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_corral_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_corral`,`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_corral_animal`
--

LOCK TABLES `repl_corral_animal` WRITE;
/*!40000 ALTER TABLE `repl_corral_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_corral_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_corral_datos`
--

DROP TABLE IF EXISTS `repl_corral_datos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_corral_datos` (
  `id_rancho` char(36) NOT NULL,
  `id_corral` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_corral`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_corral_datos`
--

LOCK TABLES `repl_corral_datos` WRITE;
/*!40000 ALTER TABLE `repl_corral_datos` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_corral_datos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_cria`
--

DROP TABLE IF EXISTS `repl_cria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_cria` (
  `id_rancho` char(36) NOT NULL,
  `id_madre` char(36) NOT NULL,
  `id_cria` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_madre`,`id_cria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_cria`
--

LOCK TABLES `repl_cria` WRITE;
/*!40000 ALTER TABLE `repl_cria` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_cria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_detalle_compra`
--

DROP TABLE IF EXISTS `repl_detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_detalle_compra` (
  `id_rancho` char(36) NOT NULL,
  `id_compra` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL,
  `id_detalle` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_detalle`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_detalle_compra`
--

LOCK TABLES `repl_detalle_compra` WRITE;
/*!40000 ALTER TABLE `repl_detalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_detalle_movimiento`
--

DROP TABLE IF EXISTS `repl_detalle_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_detalle_movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `id_detalle` int(11) NOT NULL,
  `id_animal` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_detalle`,`id_concepto`,`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_detalle_movimiento`
--

LOCK TABLES `repl_detalle_movimiento` WRITE;
/*!40000 ALTER TABLE `repl_detalle_movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_detalle_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_genealogia`
--

DROP TABLE IF EXISTS `repl_genealogia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_genealogia` (
  `id_animal` char(36) NOT NULL,
  `id_madre` char(36) NOT NULL,
  `id_padre` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_animal`,`id_madre`,`id_padre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_genealogia`
--

LOCK TABLES `repl_genealogia` WRITE;
/*!40000 ALTER TABLE `repl_genealogia` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_genealogia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_ingreso_alimento`
--

DROP TABLE IF EXISTS `repl_ingreso_alimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_ingreso_alimento` (
  `id_ingreso_alimento` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_ingreso_alimento`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_ingreso_alimento`
--

LOCK TABLES `repl_ingreso_alimento` WRITE;
/*!40000 ALTER TABLE `repl_ingreso_alimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_ingreso_alimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_medicina`
--

DROP TABLE IF EXISTS `repl_medicina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_medicina` (
  `id_medicina` char(36) NOT NULL,
  `codigo` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_medicina`,`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_medicina`
--

LOCK TABLES `repl_medicina` WRITE;
/*!40000 ALTER TABLE `repl_medicina` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_medicina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_medicina_animal`
--

DROP TABLE IF EXISTS `repl_medicina_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_medicina_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_medicina_animal` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_medicina_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_medicina_animal`
--

LOCK TABLES `repl_medicina_animal` WRITE;
/*!40000 ALTER TABLE `repl_medicina_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_medicina_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_medicina_tratamiento`
--

DROP TABLE IF EXISTS `repl_medicina_tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_medicina_tratamiento` (
  `id_tratamiento` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL DEFAULT '',
  `fecha` datetime DEFAULT NULL,
  `status` char(2) DEFAULT NULL,
  PRIMARY KEY (`id_tratamiento`,`id_medicina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_medicina_tratamiento`
--

LOCK TABLES `repl_medicina_tratamiento` WRITE;
/*!40000 ALTER TABLE `repl_medicina_tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_medicina_tratamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_movimiento`
--

DROP TABLE IF EXISTS `repl_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_movimiento` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_concepto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_movimiento`
--

LOCK TABLES `repl_movimiento` WRITE;
/*!40000 ALTER TABLE `repl_movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_movimiento_animal`
--

DROP TABLE IF EXISTS `repl_movimiento_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_movimiento_animal` (
  `id_rancho` char(36) NOT NULL,
  `id_movimiento` char(36) NOT NULL,
  `id_concepto` char(36) NOT NULL,
  `id_animal` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`,`id_movimiento`,`id_concepto`,`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_movimiento_animal`
--

LOCK TABLES `repl_movimiento_animal` WRITE;
/*!40000 ALTER TABLE `repl_movimiento_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_movimiento_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_proveedor`
--

DROP TABLE IF EXISTS `repl_proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_proveedor` (
  `id_proveedor` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_proveedor`
--

LOCK TABLES `repl_proveedor` WRITE;
/*!40000 ALTER TABLE `repl_proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_rancho`
--

DROP TABLE IF EXISTS `repl_rancho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_rancho` (
  `id_rancho` char(36) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_rancho`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_rancho`
--

LOCK TABLES `repl_rancho` WRITE;
/*!40000 ALTER TABLE `repl_rancho` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_rancho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_rancho_medicina`
--

DROP TABLE IF EXISTS `repl_rancho_medicina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_rancho_medicina` (
  `id_rancho` char(36) NOT NULL,
  `id_medicina` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_rancho`,`id_medicina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_rancho_medicina`
--

LOCK TABLES `repl_rancho_medicina` WRITE;
/*!40000 ALTER TABLE `repl_rancho_medicina` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_rancho_medicina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_raza`
--

DROP TABLE IF EXISTS `repl_raza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_raza` (
  `id_raza` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` char(2) NOT NULL,
  PRIMARY KEY (`id_raza`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_raza`
--

LOCK TABLES `repl_raza` WRITE;
/*!40000 ALTER TABLE `repl_raza` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_raza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_recepcion`
--

DROP TABLE IF EXISTS `repl_recepcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_recepcion` (
  `id_recepcion` char(36) NOT NULL,
  `id_proveedor` char(36) NOT NULL,
  `id_origen` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_recepcion`,`id_proveedor`,`id_origen`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_recepcion`
--

LOCK TABLES `repl_recepcion` WRITE;
/*!40000 ALTER TABLE `repl_recepcion` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_recepcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_registro_empadre`
--

DROP TABLE IF EXISTS `repl_registro_empadre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_registro_empadre` (
  `id_registro_empadre` char(36) NOT NULL,
  `id_hembra` char(36) DEFAULT NULL,
  `id_semental` char(36) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `status` char(2) DEFAULT NULL,
  PRIMARY KEY (`id_registro_empadre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_registro_empadre`
--

LOCK TABLES `repl_registro_empadre` WRITE;
/*!40000 ALTER TABLE `repl_registro_empadre` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_registro_empadre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_status_gestacion`
--

DROP TABLE IF EXISTS `repl_status_gestacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_status_gestacion` (
  `id_estatus_gestacion` char(36) NOT NULL,
  `id_registro_empadre` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_estatus_gestacion`,`id_registro_empadre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_status_gestacion`
--

LOCK TABLES `repl_status_gestacion` WRITE;
/*!40000 ALTER TABLE `repl_status_gestacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_status_gestacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_tratamiento`
--

DROP TABLE IF EXISTS `repl_tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_tratamiento` (
  `id_tratamiento` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` char(2) NOT NULL,
  PRIMARY KEY (`id_tratamiento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_tratamiento`
--

LOCK TABLES `repl_tratamiento` WRITE;
/*!40000 ALTER TABLE `repl_tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `repl_tratamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repl_usuario`
--

DROP TABLE IF EXISTS `repl_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repl_usuario` (
  `id_usuario` char(36) NOT NULL,
  `log` char(255) NOT NULL,
  `fecha` datetime NOT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`id_usuario`,`log`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repl_usuario`
--

LOCK TABLES `repl_usuario` WRITE;
/*!40000 ALTER TABLE `repl_usuario` DISABLE KEYS */;
INSERT INTO `repl_usuario` VALUES ('1','admin','2016-01-27 22:02:30','PE');
/*!40000 ALTER TABLE `repl_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sexo`
--

DROP TABLE IF EXISTS `sexo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sexo` (
  `id_sexo` char(36) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  `seleccionar` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_sexo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sexo`
--

LOCK TABLES `sexo` WRITE;
/*!40000 ALTER TABLE `sexo` DISABLE KEYS */;
INSERT INTO `sexo` VALUES ('43ae095a-cad9-11e4-af6c-3860779bbc63','Macho','S'),('49388b3d-cad9-11e4-af6c-3860779bbc63','Hembra','S'),('4e73bea4-cad9-11e4-af6c-3860779bbc63','Mixto','N');
/*!40000 ALTER TABLE `sexo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_gestacion`
--

DROP TABLE IF EXISTS `status_gestacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_gestacion` (
  `id_estatus_gestacion` char(36) NOT NULL,
  `id_registro_empadre` char(36) NOT NULL,
  `status` char(2) DEFAULT NULL,
  `fecha_chequeo` datetime DEFAULT NULL,
  `id_tipo_parto` char(36) DEFAULT NULL,
  PRIMARY KEY (`id_estatus_gestacion`,`id_registro_empadre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_gestacion`
--

LOCK TABLES `status_gestacion` WRITE;
/*!40000 ALTER TABLE `status_gestacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `status_gestacion` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `status_gestacion_AINS`
AFTER INSERT ON `status_gestacion`
FOR EACH ROW
BEGIN	

	DECLARE varActivo CHAR(2);

	SELECT 
    activo
INTO varActivo FROM
    registro_empadre
WHERE
    id_registro_empadre = NEW.id_registro_empadre;

	UPDATE registro_empadre 
SET 
    id_tipo_parto = NEW.id_tipo_parto
WHERE
    id_registro_empadre = NEW.id_registro_empadre;

	-- status_gestacional.status
	-- P = positivo
	-- N = negativo

	IF 		NEW.status	=	'P'	AND	varActivo	=	'N'	THEN
		
		UPDATE	registro_empadre
		SET		status_gestacional	=	'S'
		WHERE	id_registro_empadre	=	NEW.id_registro_empadre;
		
	ELSEIF	NEW.status	=	'N'	AND	varActivo	=	'N'	THEN

			-- desemparejar no hay producto
		UPDATE	registro_empadre
		SET		activo				=	'N'
		WHERE	id_registro_empadre	=	NEW.id_registro_empadre;			
/*
	ELSEIF NEW.status	=	'N'	AND	varActivo	=	'S'	THEN
	-- posible caso Aborto
		-- probablemente este caso no se de porque si ya hubo producto
		-- el estatus de gestacion no dara como negativo
		este caso se compensra con el registro de Aborto
		
		-- END IF;
*/
	END IF;
	
	-- FTP
DELETE FROM repl_status_gestacion 
WHERE
    id_estatus_gestacion = NEW.id_estatus_gestacion
    AND id_registro_empadre = NEW.id_registro_empadre;

INSERT INTO repl_status_gestacion
SELECT NEW.id_estatus_gestacion, NEW.id_registro_empadre, NOW(), 'PE';
	-- FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`status_gestacion_AFTER_UPDATE` 
AFTER UPDATE ON `status_gestacion` 
FOR EACH ROW
begin
/* replicacion */
    DELETE FROM repl_status_gestacion
WHERE id_estatus_gestacion = NEW.id_estatus_gestacion
AND id_registro_empadre = NEW.id_registro_empadre;

INSERT INTO repl_status_gestacion
SELECT NEW.id_estatus_gestacion, NEW.id_registro_empadre, NOW(), 'PE';
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tipo_aborto`
--

DROP TABLE IF EXISTS `tipo_aborto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_aborto` (
  `id_tipo_aborto` char(36) NOT NULL,
  `descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_aborto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_aborto`
--

LOCK TABLES `tipo_aborto` WRITE;
/*!40000 ALTER TABLE `tipo_aborto` DISABLE KEYS */;
INSERT INTO `tipo_aborto` VALUES ('adeedc1f-5b4e-11e5-9bc9-3860779bbc63','Aborto'),('b3faa273-5b4e-11e5-9bc9-3860779bbc63','Reabsorcion');
/*!40000 ALTER TABLE `tipo_aborto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_ganado`
--

DROP TABLE IF EXISTS `tipo_ganado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_ganado` (
  `id_tipo_ganado` char(36) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_ganado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_ganado`
--

LOCK TABLES `tipo_ganado` WRITE;
/*!40000 ALTER TABLE `tipo_ganado` DISABLE KEYS */;
INSERT INTO `tipo_ganado` VALUES ('1','Europeo (85%-100% raza pura)'),('2','Porte Grueso (65%-85% europeo)'),('3','Porte Mediano (45%-65% europeo)'),('4','Porte Liviano (menos del 45% europeo)');
/*!40000 ALTER TABLE `tipo_ganado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_medicamento`
--

DROP TABLE IF EXISTS `tipo_medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_medicamento` (
  `id_tipo_medicamento` char(36) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_medicamento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_medicamento`
--

LOCK TABLES `tipo_medicamento` WRITE;
/*!40000 ALTER TABLE `tipo_medicamento` DISABLE KEYS */;
INSERT INTO `tipo_medicamento` VALUES ('1','Engorda-invierno'),('2','Engorda-verano'),('3','Potrero-invierno'),('4','Potrero-verano');
/*!40000 ALTER TABLE `tipo_medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_parto`
--

DROP TABLE IF EXISTS `tipo_parto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_parto` (
  `id_tipo_parto` char(36) NOT NULL,
  `descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_parto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_parto`
--

LOCK TABLES `tipo_parto` WRITE;
/*!40000 ALTER TABLE `tipo_parto` DISABLE KEYS */;
INSERT INTO `tipo_parto` VALUES ('bb80faf6-575b-11e5-95fc-3860779bbc63','SIMPLE'),('c4a0fe69-575b-11e5-95fc-3860779bbc63','DOBLE'),('c831c214-575b-11e5-95fc-3860779bbc63','TRIPLE'),('cd8af6d4-575b-11e5-95fc-3860779bbc63','CUADRUPLE'),('d46eb96a-575b-11e5-95fc-3860779bbc63','QUINTUPLE');
/*!40000 ALTER TABLE `tipo_parto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratamiento`
--

DROP TABLE IF EXISTS `tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tratamiento` (
  `id_tratamiento` char(36) NOT NULL,
  `codigo` char(45) NOT NULL,
  `nombre` char(255) NOT NULL,
  `status` char(1) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`id_tratamiento`,`codigo`,`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratamiento`
--

LOCK TABLES `tratamiento` WRITE;
/*!40000 ALTER TABLE `tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tratamiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `tratamiento_BINS`
BEFORE INSERT ON `tratamiento`
FOR EACH ROW
BEGIN	

DECLARE varConteo INT(10);
    DECLARE	msg	CHAR(255);
	
	SELECT	COUNT(*)
    INTO	varConteo
    FROM	tratamiento
    WHERE	codigo	=	NEW.codigo
    AND		status  =	'A';
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El codigo "', NEW.codigo, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
    
    SELECT	COUNT(*)
    INTO	varConteo
    FROM	tratamiento
    WHERE	nombre	=	NEW.nombre
    AND		status  =	'A';
	
    IF varConteo > 0 THEN    
    
		set msg = concat('El nombre "', NEW.nombre, '" ya esta capturado');
        signal sqlstate '45000' set message_text = msg;         
    END IF;
 
 -- Envio a FTP
	DELETE FROM repl_tratamiento
	WHERE	id_tratamiento	=	NEW.id_tratamiento;
	
    INSERT INTO repl_tratamiento
	SELECT NEW.id_tratamiento, NOW(), 'PE';
 -- Envio a FTP
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `tratamiento_AINS`
AFTER INSERT ON `tratamiento`
FOR EACH ROW
BEGIN	

 -- Envio a FTP
DELETE FROM repl_tratamiento
WHERE id_tratamiento = NEW.id_tratamiento;

INSERT INTO repl_tratamiento
SELECT NEW.id_tratamiento, NOW(), 'PE';
 -- Envio a FTP
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `tratamiento_AUPD`
AFTER UPDATE ON `tratamiento`
FOR EACH ROW
BEGIN	

 -- Envio a FTP
DELETE FROM repl_tratamiento
WHERE id_tratamiento = NEW.id_tratamiento;

INSERT INTO repl_tratamiento
SELECT NEW.id_tratamiento, NOW(), 'PE';
 -- Envio a FTP
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `unidades_de_medida`
--

DROP TABLE IF EXISTS `unidades_de_medida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidades_de_medida` (
  `id_unidad` char(36) NOT NULL,
  `descripcion` char(255) DEFAULT NULL,
  PRIMARY KEY (`id_unidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidades_de_medida`
--

LOCK TABLES `unidades_de_medida` WRITE;
/*!40000 ALTER TABLE `unidades_de_medida` DISABLE KEYS */;
INSERT INTO `unidades_de_medida` VALUES ('1','CJ'),('2','PZA'),('3','ML');
/*!40000 ALTER TABLE `unidades_de_medida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` char(36) NOT NULL,
  `log` char(255) NOT NULL,
  `password` char(255) DEFAULT NULL,
  `nombre` char(255) DEFAULT NULL,
  `apellido` char(255) DEFAULT NULL,
  `id_estado` char(255) DEFAULT NULL,
  `id_ciudad` char(255) DEFAULT NULL,
  `correo` varchar(36) DEFAULT NULL,
  `fecha_nacimiento` varchar(36) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`,`log`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('1','admin','admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`usuario_AFTER_INSERT` 
AFTER INSERT ON `usuario` 
FOR EACH ROW
begin
/* replica */
DELETE FROM repl_usuario
WHERE id_usuario = NEW.id_usuario
AND log = NEW.log;

INSERT INTO repl_usuario
SELECT NEW.id_usuario, NEW.log, NOW(), 'PE';
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addtul`.`usuario_AFTER_UPDATE` 
AFTER UPDATE ON `usuario` 
FOR EACH ROW
begin
DELETE FROM repl_usuario
WHERE id_usuario = NEW.id_usuario
AND log = NEW.log;

INSERT INTO repl_usuario
SELECT NEW.id_usuario, NEW.log, NOW(), 'PE';
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'addtul'
--

--
-- Dumping routines for database 'addtul'
--
/*!50003 DROP FUNCTION IF EXISTS `fn_fechaSalidaHospital` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_fechaSalidaHospital`(
	varIdAnimal CHAR(36), varFecha Date) RETURNS date
BEGIN

DECLARE varFechaSig Date;

SELECT fecha 
INTO   varFechaSig
FROM ( SELECT min(m1.id_movimiento), m1.fecha
       FROM   movimiento m1, detalle_movimiento d1, rancho r1
       WHERE (     m1.id_rancho        = r1.id_rancho
	          AND  m1.id_concepto      = r1.con_traspaso_salida  
              AND  m1.id_corral_origen = r1.id_corral_hospital )
       AND   (     m1.id_rancho     = d1.id_rancho
              AND  m1.id_concepto   = d1.id_concepto
              AND  m1.id_movimiento = d1.id_movimiento )
       AND    d1.id_animal = varIdAnimal
       AND    m1.fecha > varFecha) A1;

return varFechaSig;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizaDatosCorrales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizaDatosCorrales`()
BEGIN

	DECLARE varIdCorral CHAR(36);
    
    DECLARE vb_termina BOOL DEFAULT FALSE;
	
	DECLARE cur_corrales CURSOR
	FOR	SELECT	id_corral                                
		FROM	corral;

	DECLARE CONTINUE HANDLER 
	FOR SQLSTATE '02000'
	SET vb_termina = TRUE;	

	OPEN cur_corrales;

	Recorre_Cursor: LOOP
    
		FETCH cur_corrales INTO varIdCorral;

		IF vb_termina THEN
        
            LEAVE Recorre_Cursor;
        END IF;
        
        CALL animalesPorCorral(varIdCorral);
        
    END LOOP;
  	CLOSE cur_corrales;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarAnimal`(
    varIdAnimal		CHAR(36),		varIdProveedor		CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),
	varNumeroLote	CHAR(255),		varPesoCompra		DECIMAL(20,4),	
	varIdSexo		CHAR(36),		varFechaIngreso		DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),
	varAreteSiniiga	CHAR(255),		varAreteCampaña		CHAR(255),
	varPesoActual	DECIMAL(20,4),	varTemperatura		DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),
	varIdRaza		CHAR(36),		varStatus			CHAR(1),
	varEsVientre	CHAR(1))
BEGIN
	
	UPDATE animal set 
		id_proveedor		=	varIdProveedor,			fecha_compra	=	varFechaCompra,
		compra				=	varCompra,				numero_lote		=	varNumeroLote,
		peso_compra			=	varPesoCompra,			id_sexo			=	varIdSexo,
		fecha_ingreso		=	varFechaIngreso,		arete_visual	=	varAreteVisual,
		arete_electronico	=	varAreteElectronico,	arete_siniiga	=	varAreteSiniiga,
		arete_campaña		=	varAreteCampaña,		peso_actual		=	varPesoActual,
		temperatura			=	varTemperatura,			es_semental		=	varEsSemental,
		id_semental			=	varIdSemental,			id_raza			=	varIdRaza,
		status				=	varStatus,				es_vientre		=	varEsVientre		
	WHERE	id_animal	=	varIdAnimal;	

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarAnimalRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarAnimalRepl`(
	varIdAnimal		    CHAR(36),	   varIdProveedor	       CHAR(36),       varFechaCompra	    DATETIME,
    varCompra	        CHAR(255),     varNumeroLote	       CHAR(255),      varPesoCompra	    DECIMAL(20,4),
	varIdSexo    	    CHAR(36),      varFechaIngreso          DATETIME,	   varAreteVisual	    CHAR(255),
    varAreteElectronico CHAR(255),	   varAreteSiniiga          CHAR(255),	   varAreteCampaña	    CHAR(255),
	varPesoActual	    DECIMAL(20,4), varTemperatura           DECIMAL(20,4), varEsSemental	    CHAR(1),
    varIdSemental	    CHAR(36),      varIdRaza		        CHAR(36),      varEsVientre		    CHAR(1),
    varFechaRecepcion   DATETIME,	   varPesoRecepcion         DECIMAL(20,4), varPorcentajeMerma   DECIMAL(20,4),
    varCostoFlete       DECIMAL(20,4), varTotalAlimento         DECIMAL(20,4), varCostoAlimento     DECIMAL(20,4),
    varPromedioAlimento DECIMAL(20,4), varPromedioCostoAlimento DECIMAL(20,4), varFechaUltimaComida DATETIME, 
    varGananciaPromedio DECIMAL(20,4), varStatus			    CHAR(1))
BEGIN
	
	IF EXISTS(SELECT * FROM animal WHERE id_animal = varIdAnimal ) THEN
		BEGIN

			UPDATE animal set 
				id_proveedor            = varIdProveedor,           fecha_compra        = varFechaCompra,
                compra		            = varCompra,                numero_lote         = varNumeroLote,
                peso_compra             = varPesoCompra,            id_sexo             = varIdSexo,
                fecha_ingreso	        = varFechaIngreso,	        arete_visual        = varAreteVisual,
                arete_electronico       = varAreteElectronico,      arete_siniiga       = varAreteSiniiga,
                arete_campaña	        = varAreteCampaña,          peso_actual         = varPesoActual,
                temperatura		        = varTemperatura,           es_semental         = varEsSemental,
                id_semental		        = varIdSemental,            id_raza	            = varIdRaza,
                es_vientre              = varEsVientre,             fecha_recepcion     = varFechaRecepcion,
                peso_recepcion          = varPesoRecepcion,         porcentaje_merma    = varPorcentajeMerma,
                costo_flete             = varCostoFlete,            total_alimento      = varTotalAlimento,
                costo_alimento          = varCostoAlimento,         promedio_alimento   = varPromedioAlimento,
                promedio_costo_alimento = varPromedioCostoAlimento, fecha_ultima_comida = varFechaUltimaComida,
                ganancia_promedio       = varGananciaPromedio,      status			    = varStatus
			WHERE id_animal = varIdAnimal;
		END;
	ELSE
		BEGIN

			INSERT animal
			(	id_animal,    	   id_proveedor, 	        fecha_compra,        compra, 
				numero_lote,	   peso_compra,			    id_sexo,			 fecha_ingreso,    
				arete_visual,	   arete_electronico,		arete_siniiga,		 arete_campaña,    
				peso_actual,	   temperatura,			    es_semental,		 id_semental,  
				id_raza,		   es_vientre,              fecha_recepcion,     peso_recepcion,
                porcentaje_merma,  costo_flete,             total_alimento,      costo_alimento,
                promedio_alimento, promedio_costo_alimento, fecha_ultima_comida, ganancia_promedio, 
                status)
			SELECT
				varIdAnimal,	     varIdProveedor,		   varFechaCompra,		 varCompra,
				varNumeroLote,	     varPesoCompra,			   varIdSexo,			 varFechaIngreso,
				varAreteVisual,	     varAreteElectronico,	   varAreteSiniiga,	     varAreteCampaña,
				varPesoActual,	     varTemperatura,		   varEsSemental,		 varIdSemental,
				varIdRaza,		     varEsVientre,			   varFechaRecepcion,	 varPesoRecepcion,
                varPorcentajeMerma,  varCostoFlete,            varTotalAlimento,     varCostoAlimento,
                varPromedioAlimento, varPromedioCostoAlimento, varFechaUltimaComida, varGananciaPromedio,
                varStatus;		
		END;
	END IF;
	
	UPDATE repl_animal set status = 'SC'
	WHERE id_animal	=	varIdAnimal;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCliente`(	
	varIdCliente	CHAR(36),	varCliente		CHAR(255), 	
    varIdPais		CHAR(36),	varIdEstado 	CHAR(36),
	varIdCiudad		CHAR(36),	varDireccion	CHAR(255),
	varEmail		CHAR(45),	varTelefono		CHAR(20),
    varPFisicaMoral	CHAR(1))
BEGIN

	UPDATE	cliente 
    SET		descripcion = 	varCliente,
			id_pais		=	varIdPais,            
			id_estado	=	varIdEstado,
            id_ciudad	=	varIdCiudad,
			direccion	=	varDireccion,
            email		=	varEmail,
            telefono	=	varTelefono,
			p_fisica_moral	=	varPFisicaMoral
    WHERE	id_cliente	=	varIdCliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarClienteRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarClienteRepl`(
	varIdCliente	CHAR(36),	varDescripcion			CHAR(255))
BEGIN
	
	IF NOT EXISTS(	SELECT	* 
				FROM	cliente
				WHERE	id_cliente	=	varIdCliente	) THEN
		BEGIN
			INSERT	cliente(	
					id_cliente, 		descripcion)
			SELECT	varIdCliente,		varDescripcion;		
		END;
	ELSE
		BEGIN			
            UPDATE	cliente
            SET		descripcion =	varDescripcion 
			WHERE	id_cliente	=	varIdCliente;
		END;
	END IF;

	UPDATE	repl_cliente set status = 'SC'
	WHERE	id_cliente	=	varIdCliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCompraRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCompraRepl`(
	varIdCompra CHAR(36),	varIdRancho	CHAR(36),		varIdProveedor	CHAR(36),		varFecha	DATETIME,
    varFactura	VARCHAR(45), 	varOrden		VARCHAR(45),	varSubtotal	DECIMAL(20,4),
    varIva		DECIMAL(20,4),	varTotal		DECIMAL(20,4) )
BEGIN
	IF EXISTS(SELECT * FROM compra WHERE id_compra = varIdCompra AND id_rancho = varIdRancho AND id_proveedor = varIdProveedor) THEN
		BEGIN

			UPDATE compra set 
				fecha			    = varFecha,
                factura 		= 		varFactura, 	orden 		= 		varOrden, 	
                subtotal 		= 		varSubtotal,	iva			=			varIva,
                total		=		varTotal
			WHERE id_compra = varIdCompra AND id_rancho = varIdRancho AND id_proveedor = varIdProveedor;
		END;
	ELSE
		BEGIN

			INSERT compra
			(	id_compra, 		id_rancho, 		id_proveedor, 		fecha, 		factura, 		orden, 
            subtotal, 		iva, 		total)
			SELECT
				varIdCompra, 		varIdRancho, 		varIdProveedor, 		varFecha, 
                varFactura, 		varOrden, 		varSubtotal, 		varIva, 		varTotal;		
		END;
	END IF;
	
	UPDATE repl_compra set status = 'SC'
	WHERE id_compra	=	varIdCompra AND id_rancho = varIdRancho AND id_proveedor = varIdProveedor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarConceptoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarConceptoRepl`(
	varIdRancho		CHAR(36),	varIdConcepto	CHAR(36),	varDescripcion	CHAR(100),
	varDesCorta		CHAR(5),	varTipo			CHAR(1))
BEGIN
	
	IF EXISTS(	SELECT	* 
				FROM	concepto_movimiento
				WHERE	id_rancho	=	varIdRancho
                AND		id_concepto	=	varIdConcepto) THEN
		BEGIN

			UPDATE	concepto_movimiento SET
					descripcion	=	varDescripcion,	des_corta	=	varDesCorta,
					tipo		=	varTipo
			WHERE	id_rancho	=	varIdRancho
			AND		id_concepto	=	varIdConcepto;
		END;
	ELSE
		BEGIN

			INSERT	concepto_movimiento
			SELECT	varIdRancho,	varIdConcepto,	varDescripcion,	varDesCorta,	varTipo;		
		END;
	END IF;

	UPDATE	repl_concepto_movimiento set status = 'SC'
	WHERE	id_rancho	=	varIdRancho
    AND		id_concepto	=	varIdConcepto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarConfiguracion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarConfiguracion`(
	varIdConfiguracion	CHAR(36),	varPuertoBaston	CHAR(255),	
	varPuertoBascula	CHAR(255),	varEnvioCom		CHAR(45),
	varRecComBascula	CHAR(45),	varRecComBaston	CHAR(45),
    varTiempoEsperaCom	INT,		varPrecioCarne	DECIMAL(20,4),
	varCostoAlimento	DECIMAL(20,4))
BEGIN
	
    UPDATE	configuracion
    SET		puerto_baston		=	varPuertoBaston,
			puerto_bascula		=	varPuertoBascula,
            envio_com			=	varEnvioCom,
            rec_com_bascula		=	varRecComBascula,
            rec_com_baston		=	varRecComBaston,
            tiempo_espera_com	=	varTiempoEsperaCom,
			precio_carne		=	varPrecioCarne,
            costo_alimento		=	varCostoAlimento
    WHERE	id_configuracion	=	varIdConfiguracion;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarControlGestacionRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarControlGestacionRepl`(
varIdControlGestacion CHAR(36), varIdRegistroEmpadre CHAR(36), 
varStatus CHAR(2), varFecha DATETIME, varTipoParto CHAR(36)
)
BEGIN
	IF EXISTS(SELECT * FROM control_gestacion WHERE id_control_gestacion = varIdControlGestacion AND id_registro_empadre = varIdRegistroEmpadre) THEN
		BEGIN

			UPDATE control_gestacion set 
            status 		= 		varStatus, 		fecha 		= 		varFecha, 
            tipo_parto 	= 		varTipoParto
			WHERE id_control_gestacion = varIdControlGestacion AND id_registro_empadre = varIdRegistroEmpadre;
		END;
	ELSE
		BEGIN

			INSERT control_gestacion
			(	id_control_gestacion, 		id_registro_empadre, 		status, 		fecha, 		tipo_parto)
			SELECT
				varIdControlGestacion, 		varIdRegistroEmpadre, 		varStatus, 		varFecha, varTipoParto;		
		END;
	END IF;
	
	UPDATE repl_control_gestacion set status = 'SC'
	WHERE id_control_gestacion	=	varIdControlGestacion AND id_registro_empadre = varIdRegistroEmpare;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCorral`(
	varIdRancho				CHAR(36),		varIdCorral			CHAR(36),	
	varNombre				CHAR(255),		varLocalizacion		CHAR(255), 	
	varAlimentoIngresado	DECIMAL(20,4),	varObservaciones	CHAR(255))
BEGIN
	UPDATE	corral
	SET		nombre				=	varNombre,				localizacion	=	varLocalizacion,        
			alimento_ingresado	=	varAlimentoIngresado,	observaciones	=	varObservaciones
	WHERE  id_rancho   = varIdRancho
	AND    id_corral   = varIdCorral;

	CALL	animalesPorCorral(	varIdCorral	);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCorralAnimalRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCorralAnimalRepl`(
	varIdRancho		CHAR(36),	varIdCorral	CHAR(36),	varIdAnimal CHAR(36))
BEGIN
	
	DELETE	FROM corral_animal
	WHERE 	id_animal = varIdAnimal;

	INSERT INTO corral_animal
	SELECT 	varIdRancho, varIdCorral, varIdAnimal;
	
	UPDATE	repl_corral_animal set status = 'SC'
	WHERE	id_rancho	=	varIdRancho
	AND		id_corral	=	varIdCorral
	AND		id_animal	=	varIdAnimal;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCorralDatos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCorralDatos`(
    varIdRancho    CHAR(36),		varIdCorral         CHAR(255),            
    varCategoria   CHAR(255),   	varGanado_amedias   CHAR(255),        
    varColor_arete INT,         	varFecha_nacimiento DATETIME,
    varNumero_lote CHAR(255),   	varCompra           CHAR(255),
    varPorcentaje  DECIMAL(20,4),	varIdProveedor		CHAR(36))
BEGIN
    
  UPDATE	corral_datos
  SET		categoria	= varCategoria,		ganado_amedias		=	varGanado_amedias,        
			color_arete	= varColor_arete,	fecha_nacimiento	=	varFecha_nacimiento,    
			numero_lote	= varNumero_lote,	compra				=	varCompra,                
			porcentaje	= varPorcentaje,	id_proveedor		=	varIdProveedor
  WHERE  	id_rancho   = varIdRancho
  AND    	id_corral   = varIdCorral;            
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCorralDatosRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCorralDatosRepl`(
	varIdRancho			CHAR(36),	varIdCorral		CHAR(36),	varCategoria		CHAR(255),
	varGanadoAmedias	CHAR(255),	varColorArete	CHAR(36),	varFechaNacimiento	DATETIME,
	varNumeroLote		CHAR(255),	varCompra		CHAR(255),	varPorcentaje		DECIMAL(5,2),
	varIdProveedor		CHAR(36))
BEGIN
	
	IF EXISTS(	SELECT	* 
				FROM	corral_datos
				WHERE	id_rancho	=	varIdRancho
                AND		id_corral	=	varIdCorral) THEN
		BEGIN

			UPDATE	corral_datos SET
					categoria	=	varCategoria,	ganado_amedias		=	varGanadoAmedias,
					color_arete	=	varColorArete,	fecha_nacimiento	=	varFechaNacimiento,
					numero_lote	=	varNumeroLote,	compra				=	varCompra,
					porcentaje	=	varPorcentaje,	id_proveedor		=	varIdProveedor
			WHERE	id_rancho	=	varIdRancho
			AND		id_corral	=	varIdCorral;
		END;
	ELSE
		BEGIN
        
			INSERT	corral(	
					id_rancho, 		id_corral, 			categoria, 		ganado_amedias, 
					color_arete, 	fecha_nacimiento, 	numero_lote,	compra,
					porcentaje,		id_proveedor)
			SELECT	varIdRancho,	varIdCorral,		varCategoria,	varGanadoAmedias,	
					varColorArete,	varFechaNacimiento,	varNumeroLote,	varCompra,	
					varPorcentaje,	varIdProveedor;		
		END;
	END IF;
	UPDATE	repl_corral_datos set status = 'SC'
	WHERE	id_rancho	=	varIdRancho
	AND		id_corral	=	varIdCorral;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCorralRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCorralRepl`(
	varIdRancho		         CHAR(36),	    varIdCorral	        CHAR(36),	
    varNombre		         CHAR(255),	    varLocalizacion		CHAR(255),	
    varDiasCorral            INT(11),	    varObservaciones	CHAR(255),
    varTotalCostoFlete       DECIMAL(20,4), varFechaInicio      DATETIME,  
    varFechaCierre           DATETIME,      varMedicinaPromedio DECIMAL(20,4),
    varConversionAlimenticia DECIMAL(20,4), varMerma            DECIMAL(20,4),
    varStatus	             CHAR(1))
BEGIN
	
	IF EXISTS(	SELECT	* 
				FROM	corral
				WHERE	id_rancho	=	varIdRancho
                AND		id_corral	=	varIdCorral) THEN
		BEGIN

			UPDATE	corral SET
					nombre			       = varNombre,			       localizacion		 =	varLocalizacion,
					dias_corral            = varDiasCorral,            observaciones	 =	varObservaciones,
                    total_costo_flete      = varTotalCostoFlete,       fecha_inicio      = varFechaInicio,
                    fecha_cierre           = varFechaCierre,           medicina_promedio = varMedicinaPromedio,
                    conversion_alimenticia = varConversionAlimenticia, merma             = varMerma,
					status			       = varStatus					
			WHERE	id_rancho	=	varIdRancho
			AND		id_corral	=	varIdCorral;
		END;
	ELSE
		BEGIN
        
			INSERT	corral(	
					id_rancho, 	    id_corral, 	         nombre,                   localizacion, 
					dias_corral,    observaciones,       total_costo_flete,        fecha_inicio,
                    fecha_cierre,   medicina_promedio,   conversion_alimenticia,   merma,
					status			       )
			SELECT	varIdRancho,    varIdCorral,		 varNombre,	               varLocalizacion,
                    varDiasCorral,  varObservaciones,    varTotalCostoFlete,       varFechaInicio,
                    varFechaCierre, varMedicinaPromedio, varConversionAlimenticia, varMerma,            
					varStatus;		
		END;
	END IF;
	UPDATE	repl_corral set status = 'SC'
	WHERE	id_rancho	=	varIdRancho
	AND		id_corral	=	varIdCorral;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCria`(
    varIdRancho			CHAR(36),	varIdMadre	CHAR(36),            
    varIdCria			CHAR(36),	varArete	CHAR(255),	
	varFechaNacimiento	DATETIME,	varIdSexo	CHAR(36),
	varIdRaza			CHAR(36),	varPeso		DECIMAL(20,4))
BEGIN
    
    UPDATE cria
	SET arete				=	varArete,
		fecha_nacimiento	=	varFechaNacimiento,
		id_sexo				=	varIdSexo,
		id_raza				=	varIdRaza,
		peso				=	varPeso
	WHERE	id_rancho	=	varIdRancho
	AND		id_madre	=	varIdMadre
	AND		id_cria		=	varIdCria;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarCriaRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCriaRepl`(
	varIdRancho	CHAR(36),	varIdMadre	CHAR(36),	varIdCria			CHAR(255),
	varArete	CHAR(45),	varIdSexo	CHAR(36),	varFechaNacimiento	DATETIME,
	varIdRaza	CHAR(36),	varStatus	CHAR(1))
BEGIN
	
	IF EXISTS(	SELECT	* 
				FROM	cria
				WHERE	id_rancho	=	varIdRancho
                AND		id_madre	=	varIdMadre
				AND		id_cria		=	varIdCria	) THEN
		BEGIN

			UPDATE	cria SET
					arete				=	varArete,			id_sexo	=	varIdSexo,
					fecha_nacimiento	=	varFechaNacimiento,	id_raza	=	varIdRaza,	
					status				=	varStatus
			WHERE	id_rancho	=	varIdRancho
			AND		id_madre	=	varIdMadre
			AND		id_cria		=	varIdCria;
		END;
	ELSE
		BEGIN
        
			INSERT	cria(	
					id_rancho, 		id_madre, 			id_cria,	arete, 
					id_sexo, 		fecha_nacimiento, 	id_raza,	status)
			SELECT	varIdRancho,	varIdMadre,			varIdCria,	varArete,	
					varIdSexo,		varFechaNacimiento,	varIdRaza,	varStatus;		
		END;
	END IF;
	UPDATE	repl_cria set status = 'SC'
	WHERE	id_rancho	=	varIdRancho
	AND		id_madre	=	varIdMadre
	AND		id_cria		=	varIdCria;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarDetalleCompraRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarDetalleCompraRepl`(
varIdRancho CHAR(36), 	varIdCompra CHAR(36), 	varIdMedicina CHAR(36), 
varIdDetalle INT, 		varCantidad INT, 			varPresentacion DECIMAL(20,4),
varPrecioUnitario DECIMAL(20,4), 		varImporte DECIMAL(20,4)
)
BEGIN
	
	IF NOT EXISTS(	SELECT	* 
				FROM	detalle_compra
				WHERE	id_rancho	=	varIdRancho 	AND 	id_compra 	= 	varIdCompra
                AND 	id_medicina = 	varIdMedicina 	AND		id_detalle 	=	varIdDetalle) THEN
		BEGIN
			INSERT	detalle_compra(	
					id_rancho, 		id_compra, 	id_medicina,		id_detalle,		cantidad,
                    presentacion,	precio_unitario,		importe)
			SELECT	varIdRancho,	varIdCompra,	varIdMedicina,	varIdDetalle,	
            varCantidad,	varPresentacion,	varPrecioUnitario,	varImporte;		
		END;
	ELSE
		BEGIN			
            UPDATE	detalle_compra
            SET		cantidad 	= 	varCantidad,	presentacion 	= 	varPresentacion,	
            precio_unitario 	= 	varPrecioUnitario,		importe 	= 	varImporte 
			WHERE	id_rancho	=	varIdRancho 	AND 	id_compra 	= 	varIdCompra
                AND 	id_medicina = 	varIdMedicina 	AND		id_detalle 	=	varIdDetalle;
		END;
	END IF;

	UPDATE repl_detalle_compra 
SET 
    status = 'SC'
WHERE
    id_rancho	=	varIdRancho 	AND 	id_compra 	= 	varIdCompra 	
    AND 	id_medicina = 	varIdMedicina 	AND		id_detalle 	=	varIdDetalle;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarDetalleMovimientoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarDetalleMovimientoRepl`(
	varIdRancho		CHAR(36),	varIdMovimiento	CHAR(36),	varIdConcepto				CHAR(255),
	varIdDetalle	INT(11),	varIdAnimal		CHAR(36) )
BEGIN
	
	IF NOT EXISTS(	
				SELECT	* 
				FROM	detalle_movimiento
				WHERE	id_rancho		=	varIdRancho
                AND		id_movimiento	=	varIdMovimiento 
                AND		id_concepto		=	varIdConcepto
                AND		id_detalle		=	varIdDetalle
                AND		id_animal		=	varIdAnimal ) THEN
		BEGIN
			        
			INSERT	detalle_movimiento
			SELECT	varIdRancho,	varIdMovimiento,	varIdConcepto,
					varIdDetalle,	varIdAnimal;
		END;
	END IF;

	UPDATE	repl_detalle_movimiento set status = 'SC'
	WHERE	id_rancho		=	varIdRancho
	AND		id_movimiento	=	varIdMovimiento 
    AND		id_concepto		=	varIdConcepto
    AND		id_detalle		=	varIdDetalle
    AND		id_animal		=	varIdAnimal;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizaRegistroEmpadreRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizaRegistroEmpadreRepl`(
varIdRegistroEmpadre	CHAR(36),	varFecha	DATETIME,	varIdHembra		CHAR(36),
varIdSemental	CHAR(36),	varStatusGestacional	CHAR(2),	varAborto	CHAR(2),	
varIdTipoParto	CHAR(36),	varActivo	CHAR(2))
BEGIN
	IF EXISTS(SELECT * FROM registro_empadre 
	WHERE id_registro_empadre = varIdRegistroEmpadre	AND
		id_hembra = varIdHembra		AND id_semental = varIdSemental) THEN
		BEGIN
			UPDATE registro_empadre SET
			fecha	= varFecha,		status_gestacional = varStatusGestacional,
			aborto = varAborto,		id_tipo_parto = varIdTipoParto,
			activo = varActivo
			WHERE id_registro_empadre = varIdRegistroEmpadre	
				AND		id_hembra = varIdHembra		AND id_semental = varIdSemental;
		END;
	ELSE
		BEGIN
			INSERT registro_empadre (
			id_registro_empadre,	fecha,	id_hembra,	id_semental,	status_gestacional,
			aborto,	id_tipo_parto,	activo)
			SELECT 
			varIdRegistroEmpadre,	varFecha,	varIdHembra,	varIdSemental,	varStatusGestacional,
			varAborto,	varIdTipoParto,		varActivo;
			END;
		END IF;
	UPDATE 	repl_registro_empadre
	SET 	status	=	'SC'
	WHERE	id_registro_empadre = varIdRegistroEmpadre	AND		id_hembra	=	varIdHembra		
		AND		id_semental		AND		varIdSemental;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarGenealogiaRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarGenealogiaRepl`(
varIdAnimal 	CHAR(36),	varIdMadre	CHAR(36),	varIdPadre CHAR(36))
BEGIN
	IF NOT EXISTS(	SELECT	* 
				FROM	genealogia
				WHERE	id_animal	=	varIdAnimal 	AND 	id_madre 	= 	varIdMadre
                AND 	id_padre = 	varIdPadre) THEN
		BEGIN
			INSERT	genealogia(	
					id_animal, 		id_madre, 	id_padre)
			SELECT	varIdAnimal,	varIdMadre,	varIdPadre;		
		END;
END IF;
UPDATE repl_genealogia
SET 
    status = 'SC'
WHERE	id_animal	=	varIdAnimal 	AND 	id_madre 	= 	varIdMadre
AND 	id_padre = 	varIdPadre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarIngresoAlimentoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarIngresoAlimentoRepl`(
varIdIngresoAlimento CHAR(36), varNumeroLote VARCHAR(45), varIdCorral CHAR(36),
varTotalAlimento DECIMAL(20,4), varFecha DATETIME, varCostoUnitario DECIMAL(20,4),
varCostoTotal DECIMAL(20,4), varCarro VARCHAR(45))
BEGIN
	IF NOT EXISTS(	SELECT	* 
				FROM	ingreso_alimento
				WHERE	id_ingreso_alimento	=	varIdIngresoAlimento	) THEN
		BEGIN
			INSERT	ingreso_alimento(	
					id_ingreso_alimento, 		numero_lote, 	id_corral,
                    total_alimento,		fecha,	costo_unitario,	costo_total,
                    carro)
			SELECT	varIdIngresoAlimento,		varNumeroLote,	varIdCorral,
					varTotalAlimento, 	varFecha,	varCostoUnitario,	varCostoTotal,
                    varCarro;		
		END;
	ELSE
		BEGIN			
            UPDATE	ingreso_alimento
            SET		numero_lote 	= 	varNumeroLote, 	id_corral 	= 	varIdCorral, 
					total_alimento 	= 	varTotalAlimento, fecha	= varFecha,	costo_unitario	=	varCostoUnitario,
                    costo_total 	=	varCostoTotal,	carro	=	varCarro
			WHERE	id_ingreso_alimento	=	varIdIngresoAlimento;
		END;
	END IF;

	UPDATE	repl_ingreso_alimento set status = 'SC'
	WHERE	id_ingreso_alimento	=	varIdIngresoAlimento;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarMedicina` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarMedicina`(
	varId_medicina	CHAR(36),	varCodigo	INT,
    varNombre		CHAR(255),	varCosto	DECIMAL(20,4),
    varId_unidad	CHAR(36),	varPresentacion	DECIMAL(20,4))
BEGIN

	UPDATE	medicina
	SET		codigo			=	varCodigo,
			nombre			=	varNombre,
			costo			=	varCosto,
			id_unidad		=	varId_unidad,
			presentacion	=	varPresentacion,
			costo_unitario	=	costo / IFNULL(varPresentacion, 1)
	WHERE	id_medicina = varId_medicina;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarMedicinaAnimalRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarMedicinaAnimalRepl`(
	varIdRancho		CHAR(36),		varIdMedicinaAnimal	CHAR(36),
	varIdMedicina	CHAR(36),		varIdAnimal			CHAR(36),
	varDosis		DECIMAL(20,4),	varFecha			DATETIME,
	varCosto		DECIMAL(20,4)	)
BEGIN
	
	IF NOT EXISTS(	SELECT	* 
					FROM	medicina_animal
					WHERE	id_rancho			=	varIdRancho
					AND		id_medicina_animal	=	varIdMedicinaAnimal) THEN 
		BEGIN
			INSERT INTO medicina_animal 
			SELECT	varIdRancho,	varIdMedicinaAnimal,	varIdMedicina,
					varIdAnimal,	varDosis,				varFecha,
                    varCosto;			
		END;	
	END IF;
	
	UPDATE	repl_medicina_animal set status = 'SC'
	WHERE	id_rancho			=	varIdRancho
	AND		id_medicina_animal	=	varIdMedicinaAnimal;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarMedicinaRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarMedicinaRepl`(
	varIdMedicina		CHAR(36),		varCodigo	INT(11),	varNombre			CHAR(255),
	varCosto			DECIMAL(20,4),	varIdUnidad	CHAR(36),	varPresentacion		DECIMAL(20,4),
	varCostoUnitario	DECIMAL(20,4),	varStatus	CHAR(1)	)
BEGIN
	
	IF EXISTS(	SELECT	* 
				FROM	medicina
				WHERE	id_medicina	=	varIdMedicina
                AND		codigo		=	varCodigo) THEN 
		BEGIN

			UPDATE	medicina SET
					nombre			=	varNombre,			costo			=	varCosto,
					id_unidad		=	varIdUnidad,		presentacion	=	varPresentacion,
					costo_unitario	=	varCostoUnitario,	status			=	varStatus                    
			WHERE	id_medicina	=	varIdMedicina
			AND		codigo		=	varCodigo;
		END;
	ELSE
		BEGIN
        
			INSERT	medicina
			SELECT	varIdMedicina,	varCodigo,			varNombre,			varCosto,		
					varIdUnidad,	varPresentacion,	varCostoUnitario,	varStatus;	
		END;
	END IF;

	UPDATE repl_medicina set status = 'SC'
	WHERE id_medicina	=	varIdMedicina
	AND		codigo		=	varCodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarMedicinaTratamientoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarMedicinaTratamientoRepl`(
varIdTratamiento CHAR(36), varIdMedicina CHAR(36), varDosis DECIMAL(20,4))
BEGIN
	IF NOT EXISTS(	SELECT	* 
					FROM	medicina_tratamiento
					WHERE	id_medicina		=	varIdMedicina 
					AND		id_tratamiento	=	varIdTratamiento) THEN
			BEGIN
				INSERT	medicina_tratamiento(	
					id_tratamiento, 		id_medicina,	dosis)
				SELECT	varIdTratamiento,	varIdMedicina,	varDosis;		
		END;
	ELSE
		BEGIN			
            UPDATE	medicina_tratamiento
            SET		dosis = varDosis 
			WHERE	id_medicina = varIdMedicina
			AND id_tratamiento = varIdTratamiento;		
		END;
	END IF;

	UPDATE repl_medicina_tratamiento 
SET 
    status = 'SC'
WHERE
    id_medicina = varIdMedicina
        AND id_tratamiento = varIdTratamiento;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarMovimientoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarMovimientoRepl`(
	varIdRancho				CHAR(36),		varIdMovimiento 		CHAR(36),		
	varIdConcepto			CHAR(36),		varFecha				DATETIME,
	varIdRanchoOrigen		CHAR(36),		varIdCorralOrigen		CHAR(36),
	varIdRanchoDestino		CHAR(36),		varIdCorralDestino		CHAR(36),
	varIdClaseMovimiento	CHAR(36),		varNumeroPedido			CHAR(255),	
	varIdCliente			CHAR(36),		varNecropcia			CHAR(255),
	varDxMuerte				CHAR(255),		varEtapaReproductiva	CHAR(255),
	varCausaEntrada			CHAR(255),		varObservacion			CHAR(255),	
	varPeso					DECIMAL(12,4),	varFechaReg				DATETIME)
BEGIN

	IF NOT EXISTS(	
				SELECT	* 
				FROM	movimiento
				WHERE	id_rancho		=	varIdRancho
                AND		id_movimiento	=	varIdMovimiento 
                AND		id_concepto		=	varIdConcepto     ) THEN
		BEGIN

			INSERT INTO movimiento(	
				id_rancho,			id_movimiento,		id_concepto,			fecha,					id_rancho_origen,
				id_corral_origen,	id_rancho_destino,	id_corral_destino,		id_clase_movimiento,	numero_pedido,
				id_cliente,			necropcia,			dx_muerte,				etapa_reproductiva,		causa_entrada,
				observacion,		peso,				fecha_reg)
			VALUES(	
				varIdRancho,		varIdMovimiento,	varIdConcepto,			varFecha,				varIdRanchoOrigen,
				varIdCorralOrigen,	varIdRanchoDestino,	varIdCorralDestino,		varIdClaseMovimiento,	varNumeroPedido,
				varIdCliente,		varNecropcia,		varDxMuerte,			varEtapaReproductiva,	varCausaEntrada,
				varObservacion,		varPeso,			varFechaReg);                        
		END;
	END IF;

	UPDATE repl_movimiento set status = 'SC'
	WHERE	id_rancho		=	varIdRancho
	AND		id_movimiento	=	varIdMovimiento 
    AND		id_concepto		=	varIdConcepto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarProveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarProveedor`(	
	varIdProveedor	CHAR(36),	varProveedor	CHAR(255), 	
    varIdPais		CHAR(36),	varIdEstado 	CHAR(36),
	varIdCiudad		CHAR(36),	varDireccion	CHAR(255),
	varEmail		CHAR(45),	varTelefono		CHAR(20),
    varPFisicaMoral	CHAR(1))
BEGIN

	UPDATE	proveedor 
    SET		descripcion		=	varProveedor,
			id_pais			=	varIdPais,            
			id_estado		=	varIdEstado,
            id_ciudad		=	varIdCiudad,
			direccion		=	varDireccion,
            email			=	varEmail,
            telefono		=	varTelefono,
			p_fisica_moral	=	varPFisicaMoral
    WHERE	id_proveedor	=	varIdProveedor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarProveedorRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarProveedorRepl`(
	varIdProveedor	CHAR(36),	varDescripcion			CHAR(255))
BEGIN
	
	IF NOT EXISTS(	SELECT	* 
				FROM	proveedor
				WHERE	id_proveedor	=	varIdProveedor	) THEN
		BEGIN
			INSERT	proveedor(	
					id_proveedor, 		descripcion)
			SELECT	varIdProveedor,		varDescripcion;
		END;
	ELSE
		BEGIN
            UPDATE	proveedor
            SET		descripcion		=	varDescripcion 
			WHERE	id_proveedor	=	varIdProveedor;
		END;
	END IF;

	UPDATE	repl_proveedor set status = 'SC'
	WHERE	id_proveedor	=	varIdProveedor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRancho` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRancho`(
	varIdRancho	CHAR(36),	varDescripcion	CHAR(255),	varActividad	CHAR(255),
	varEstado	CHAR(36),	varCiudad		CHAR(36))
BEGIN

	UPDATE	rancho
	SET		descripcion	=	varDescripcion,
			actividad	=	varActividad,
			id_estado	=	varEstado,
			id_ciudad	=	varCiudad
	WHERE	id_rancho	=	varIdRancho;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRanchoMedicina` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRanchoMedicina`()
BEGIN

	-- Generar Registros
    START TRANSACTION;
		INSERT INTO	rancho_medicina
		SELECT		rancho.id_rancho, 			medicina.id_medicina, 0, 0, 
					medicina.costo_unitario, 	medicina.costo_unitario, 
					'1900-01-01 00:00'
		FROM		rancho, medicina
		WHERE NOT EXISTS(	SELECT	* 
							FROM	rancho_medicina 	
							WHERE	rancho_medicina.id_rancho = rancho.id_rancho
							AND		rancho_medicina.id_medicina = medicina.id_medicina);
	COMMIT;
    
	-- Poner costo en las aplicaciones de medicina que no tengan
    SET @DISABLE_TRIGER = 1;
    
    START TRANSACTION;
		UPDATE	medicina_animal
		SET		costo	=	(	SELECT	costo_promedio
								FROM	rancho_medicina
								WHERE	rancho_medicina.id_rancho 	=	medicina_animal.id_rancho
								AND		rancho_medicina.id_medicina	=	medicina_animal.id_medicina)
		WHERE	COALESCE(costo,0.0)	=	0.0;
    COMMIT;
    
    SET @DISABLE_TRIGER = 0;
    
    -- Actualizar existencias iniciales las que se han aplicado
    UPDATE 	rancho_medicina
    SET		existencia_inicial = (	SELECT SUM(dosis)
									FROM	medicina_animal
									WHERE	medicina_animal.id_rancho	=	rancho_medicina.id_rancho
                                    AND		medicina_animal.id_medicina	=	rancho_medicina.id_medicina);
	
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRanchoMedicinaRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRanchoMedicinaRepl`(
varIdRancho CHAR(36),		varIdMedicina CHAR(36),		varExistenciaInicial INT(11),	
varExistencia INT(11),		varCostoPromedio DECIMAL(20,4),		
varUltimoCosto DECIMAL(20,4),		varUltimaCompra DATETIME)
BEGIN
	IF EXISTS(SELECT * FROM rancho_medicina WHERE id_rancho = varIdRancho AND id_medicina = varIdMedicina) THEN
		BEGIN
			UPDATE rancho_medicina set 
            existencia_inicial = varExistenciaInicial, existencia = varExistencia, costo_promedio = varCostoPromedio, 
            ultimo_costo =  varUltimoCosto, ultima_compra = varUltimaCompra
            WHERE id_rancho = varIdRancho AND id_medicina = varIdMedicina;
		END;
	ELSE
		BEGIN

			INSERT rancho_medicina 
			(	id_rancho,	id_medicina, 	existencia_inicial, 	existencia, 	costo_promedio, 	
            ultimo_costo, 	ultima_compra)
			SELECT
				varIdRancho,		varIdMedicina,	varExistenciaInicial,	varExistencia,		varCostoPromedio,		
				varUltimoCosto,		varUltimaCompra;		
		END;
	END IF;
	
	UPDATE repl_rancho_medicina 
SET 
    status = 'SC'
WHERE
    id_rancho = varIdRancho
        AND id_medicina = varIdMedicina;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRanchoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRanchoRepl`(
	varIdRancho				CHAR(36),	varDescripcion		CHAR(255),	varConTraspasoEntrada	CHAR(36),
    varConTraspasoSalida	CHAR(36),	varConSalida		CHAR(36),	varConMuerte			CHAR(36),
    varConPesaje			CHAR(36),	varIdCorralHospital	CHAR(36),	varActividad			CHAR(255),
	varIdEstado				CHAR(36),	varIdCiudad			CHAR(36))
BEGIN

	IF NOT EXISTS(	
				SELECT	* 
				FROM	rancho
				WHERE	id_rancho		=	varIdRancho ) THEN
		BEGIN
			        
			INSERT	rancho
			SELECT	varIdRancho,	varDescripcion,	varConTraspasoEntrada,	varConTraspasoSalida,
					varConSalida,	varConMuerte,	varConPesaje,			varIdCorralHospital,
                    varActividad,	varIdEstado,	varIdCiudad, 'A';
		END;
        ELSE
        BEGIN
			UPDATE	rancho
			SET		descripcion	=	varDescripcion,
					actividad	=	varActividad,
					id_estado	=	varIdEstado,
					id_ciudad	=	varIdCiudad
			WHERE	id_rancho	=	varIdRancho;
		END;
	END IF;

	UPDATE repl_rancho set status = 'SC'
	WHERE id_rancho	=	varIdRancho;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRaza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRaza`(	
	varIdRaza CHAR(36),	varRaza	CHAR(255)	)
BEGIN
	UPDATE raza set descripcion = varRaza
    WHERE	id_raza = varIdRaza;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRazaRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRazaRepl`(
	varIdRaza	CHAR(36),	varDescripcion			CHAR(255), varSeleccionar	CHAR(1))
BEGIN
	
	IF NOT EXISTS(	SELECT	* 
				FROM	raza
				WHERE	id_raza	=	varIdRaza	) THEN
		BEGIN
			       
			INSERT	raza(	
					id_raza, 		descripcion,	seleccionar)
			SELECT	varIdRaza,		varDescripcion,	varSeleccionar;		
		END;
	END IF;

	UPDATE	repl_raza set status = 'SC'
	WHERE	id_raza	=	varIdRaza;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarRecepcionRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarRecepcionRepl`(
varIdRecepcion CHAR(36), varIdProveedor CHAR(36), varIdOrigen CHAR(36), 
varFolio VARCHAR(45), varFechaCompra DATETIME, varFechaRecepcion DATETIME, 
varAnimales INT(10), varAnimalesPendientes INT(10), varPesoOrigen DECIMAL(20,4), 
varLimiteMerma DECIMAL(20,4), varMerma DECIMAL(20,4), varPorcentajeMerma DECIMAL(20,4), 
varPesoRecepcion DECIMAL(20,4), varNumeroLote CHAR(255), varCostoFlete DECIMAL(20,4), 
varDevoluciones INT(10), varCausaDevolucion VARCHAR(45), varTotalAlimento DECIMAL(20,4))
BEGIN
	IF EXISTS(SELECT * FROM recepcion WHERE id_recepcion = varIdRecepcion AND id_proveedor = varIdProveedor 
    AND id_origen = varIdOrigen) THEN
		BEGIN
			UPDATE recepcion set 
            folio = varFolio, fecha_compra = varFechaCompra, fecha_recepcion = varFechaRecepcion, animales = varAnimales,
            animales_pendientes = varAnimalesPendientes, peso_origen = varPesoOrigen, limite_merma = varLimiteMerma,
            merma = varMerma, porcentaje_merma = varPorcentajeMerma, peso_recepcion = varPesoRecepcion,
            numero_lote = varNumeroLote, costo_flete = varCostoFlete, devoluciones = varDevoluciones,
            causa_devolucion = varCausaDevolucion, total_alimento = varTotalAlimento
            WHERE id_recepcion = varIdRecepcion AND id_proveedor = varIdProveedor AND id_origen = varIdOrigen;
		END;
	ELSE
		BEGIN
			INSERT recepcion 
			(	id_recepcion, id_proveedor, id_origen, folio, fecha_compra, fecha_recepcion, animales, 
            animales_pendientes, peso_origen, limite_merma, merma, porcentaje_merma, peso_recepcion, 
            numero_lote, costo_flete, devoluciones, causa_devolucion, total_alimento)
			SELECT
			varIdRecepcion, varIdProveedor, varIdOrigen,varFolio, varFechaCompra, varFechaRecepcion, 
			varAnimales, varAnimalesPendientes, varPesoOrigen, varLimiteMerma, varMerma, varPorcentajeMerma, 
			varPesoRecepcion, varNumeroLote, varCostoFlete, varDevoluciones, varCausaDevolucion, varTotalAlimento;
		END;
	END IF;

	UPDATE repl_recepcion 
SET 
    status = 'SC'
WHERE
    id_recepcion = varIdRecepcion
        AND id_proveedor = varIdProveedor
        AND id_origen = varIdOrigen;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarStatusGestacionRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarStatusGestacionRepl`(
varIdEstatusGestacion CHAR(36),		varIdRegistroEmpadre CHAR(36),		
varStatus CHAR(2),	varFechaChequeo DATETIME,	varIdTipoParto CHAR(36))
BEGIN
	IF EXISTS(SELECT * FROM status_gestacion WHERE id_estatus_gestacion = varIdestatus_gestacion 
    AND id_registro_empadre = varIdRegistroEmpadre) THEN
		BEGIN
			UPDATE status_gestacion set 
            id_estatus_gestacion = varIdEstatusGestacion, id_registro_empadre = varIdRegistroEmpadre,
            status = varStatus, fecha_chequeo = varFechaChequeo, id_tipo_parto = varIdTipoParto
            WHERE id_estatus_gestacion = varIdestatus_gestacion 
            AND id_registro_empadre = varIdRegistroEmpadre;
		END;
	ELSE
		BEGIN
			INSERT status_gestacion 
			(id_estatus_gestacion, id_registro_empadre, status, fecha_chequeo, id_tipo_parto)
			SELECT
				varIdEstatusGestacion,		varIdRegistroEmpadre,		varStatus,	varFechaChequeo,	varIdTipoParto; 
                END;
	END IF;

	UPDATE repl_status_gestacion 
SET 
    status = 'SC'
WHERE
    id_estatus_gestacion = varIdestatus_gestacion
        AND id_registro_empadre = varIdRegistroEmpadre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarTratamiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarTratamiento`(
	varIdTratamiento	CHAR(36),	varCodigo	CHAR(45),	varNombre	CHAR(255))
BEGIN

	UPDATE	tratamiento
	SET		codigo			=	varCodigo,
			nombre			=	varNombre	
	WHERE	id_tratamiento	=	varIdTratamiento;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarTratamientoRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarTratamientoRepl`(
varIdTratamiento CHAR(36),	varCodigo CHAR(36),	varNombre	CHAR(255),	
varStatus	CHAR(1),	varFecha	DATETIME)
BEGIN
	IF NOT EXISTS(	SELECT	* 
				FROM	tratamiento
				WHERE	id_tratamiento	=	varIdTratamiento	) THEN
		BEGIN
			INSERT	tratamiento(	
					id_tratamiento, 		codigo,		
                    nombre,		status,		fecha)
			SELECT	varIdTratamiento,		varCodigo,	
					varNombre,		varStatus,		varFecha;
		END;
	ELSE
		BEGIN
            UPDATE	tratamiento
            SET		codigo = varCodigo, nombre = varNombre, status = varStatus, fecha = varFecha
			WHERE	id_tratamiento	=	varIdTratamiento;
		END;
	END IF;

	UPDATE	repl_tratamiento set status = 'SC'
	WHERE	id_tratamiento	=	varIdTratamiento;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizarUsuarioRepl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarUsuarioRepl`(
varIdUsuario CHAR(36), varLog CHAR(255), varPassword CHAR(255), varNombre CHAR(255), varApellido CHAR(255), 
varIdEstado CHAR(255), varIdCiudad CHAR(255), varCorreo VARCHAR(36), varFechaNacimiento VARCHAR(36), varTelefono VARCHAR(10))
BEGIN
	IF NOT EXISTS(	SELECT	* 
				FROM	usuario
				WHERE	id_usuario	=	varIdUsuario	) THEN
		BEGIN
			INSERT	usuario(	
					id_usuario, 		log, password,		nombre,		apellido,	
                    id_estado,	id_ciudad,	correo,	fecha_nacimiento,	telefono)
			SELECT	varIdUsuario, varLog, varPassword, varNombre, varApellido, 
					varIdEstado, varIdCiudad, varCorreo, varFechaNacimiento, varTelefono ;
		END;
	ELSE
		BEGIN
            UPDATE	usuario
            SET		password = varPassword, nombre = varNombre, apellido = varApellido, 
					id_estado = varIdEstado, id_ciudad = varIdCiudad, correo = varCorreo, 
                    fecha_nacimiento = varFechaNacimiento, telefono = varTelefono
			WHERE	id_usuario	=	varIdUsuario;
		END;
	END IF;

	UPDATE repl_usuario 
SET    status = 'SC'
WHERE    id_usuario = varIdUsuario;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarAborto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarAborto`(
    varIdHembra	CHAR(36),	varFecha	DATETIME	)
BEGIN
	
	-- Busca registro de empadre RE
	-- Actualiza RE.aborto = 'S'
	-- Actualizar RE.Activo = 'N'
	UPDATE	registro_empadre	
	SET		aborto	=	'S',
			activo	=	'N'
	WHERE	id_hembra	=	varIdHembra
	AND		status_gestacional	=	'S'
	AND		activo		=	'S';
	
	-- Actualizar Animal.semental = 0
	UPDATE	animal
	SET		id_semental  = 0
	WHERE	id_animal = varIdHembra;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarAnimal`(
    varIdRancho		CHAR(36),		varIdCorral     	CHAR(36),		varIdProveedor	CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),		varNumeroLote	CHAR(255),
	varPesoCompra	DECIMAL(20,4),	varIdSexo			CHAR(36),		varFechaIngreso	DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),		varAreteSiniiga	CHAR(255),
	varAreteCampaña	CHAR(255),		varPesoActual		DECIMAL(20,4),	varTemperatura	DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),		varIdRaza		CHAR(36),
	varStatus		CHAR(1),		varIdCria			CHAR(36),		varEsVientre	CHAR(1))
BEGIN
    DECLARE varIdAnimal,	varIdRecepcion CHAR(36);
	declare varPorcentajeMerma,		varCostoFlete,				varTotalAlimento,		varCostoAlimento,
			varPromedioAlimento,	varPromedioCostoAlimento, varGananciaPromedio,		varPesoCompra,
			varPesoRecepcion	DECIMAL(20,4);		

	declare	varAnimalesPendientes int(10);

	declare varFechaUltimaComida,varFechaRecepcion datetime;

	SELECT UUID()
	INTO varIdAnimal;

	INSERT corral_animal
    (   id_rancho,    id_corral,    id_animal)
    SELECT
        varIdRancho, varIdCorral, varIdAnimal;

    INSERT animal
    (	id_animal,		id_proveedor,		fecha_compra,	compra,
		numero_lote,	peso_compra,		id_sexo,		fecha_ingreso,
		arete_visual,	arete_electronico,	arete_siniiga,	arete_campaña,
		peso_actual,	temperatura,		es_semental,	id_semental,
		id_raza,		status,				es_vientre)
    SELECT
		varIdAnimal,	varIdProveedor,			varFechaCompra,		varCompra,
		varNumeroLote,	varPesoCompra,			varIdSexo,			varFechaIngreso,
		varAreteVisual,	varAreteElectronico,	varAreteSiniiga,	varAreteCampaña,
		varPesoActual,	varTemperatura,			varEsSemental,		varIdSemental,		
		varIdRaza,		varStatus,				varEsVientre;



	update	cria 
	set		id_animal	=	varIdAnimal
	where	id_cria		=	varIdCria;

	-- obtener datos del animal base
	select	peso_compra,				fecha_recepcion,			peso_recepcion,			porcentaje_merma,
			costo_flete,				total_alimento,				costo_alimento,			promedio_alimento,
			promedio_costo_alimento,	fecha_ultima_comida,		ganancia_promedio
	into	varPesoCompra,				varFechaRecepcion,			varPesoRecepcion,		varPorcentajeMerma,		
			varCostoFlete,				varTotalAlimento,			varCostoAlimento,
			varPromedioAlimento,		varPromedioCostoAlimento, 	varFechaUltimaComida,	varGananciaPromedio
	from	animal
	where 	numero_lote = varNumeroLote
	and		not exists (	select	* 
							from	corral_animal 
							where	corral_animal.id_animal = animal.id_animal	);

	update animal set	peso_compra				=	varPesoCompra,
						fecha_recepcion			=	varFechaRecepcion,
						peso_recepcion			=	varPesoRecepcion,
						porcentaje_merma		=	varPorcentajeMerma,
						costo_flete				=	varCostoFlete,				
						total_alimento			=	varTotalAlimento,			
						costo_alimento			=	varCostoAlimento,
						promedio_alimento		=	varPromedioAlimento,	
						promedio_costo_alimento	=	varPromedioCostoAlimento,	
						fecha_ultima_comida		=	varFechaUltimaComida,
						ganancia_promedio		=	varGananciaPromedio
	where	id_animal	=	varidAnimal;

	
	-- agregar peso de compra y peso de recepcion como registros de pesos
	call movimientoPeso( varIdRancho, varIdAnimal, varFechaCompra, varPesoCompra);
	call movimientoPeso( varIdRancho, varIdAnimal, varFechaRecepcion, varPesoRecepcion);
	call movimientoPeso( varIdRancho, varIdAnimal, varFechaIngreso, varPesoActual);
	-- disminuir el numero de animales en recepcion de animales
	select	id_recepcion,	animales_pendientes
	into	varIdRecepcion,	varAnimalesPendientes
	from	recepcion
	where	animales_pendientes > 0
	and 	numero_lote = varNumeroLote;
	
	update	recepcion
	set		animales_pendientes = animales_pendientes - 1
	where	id_recepcion = varIdRecepcion;

	-- si quedaba un solo animal pendiente, se elimina el animal base
	if varAnimalesPendientes = 1 then

		delete from animal
		where numero_lote = varNumeroLote
		and		not exists (	select	* 
							from	corral_animal 
							where	corral_animal.id_animal = animal.id_animal	);

	end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarAnimalGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarAnimalGrupo`(
	varIdRancho CHAR(36),	varIdUsuario	CHAR(36),
	varIdAnimal	CHAR(36),	vartipo			CHAR(255))
BEGIN

	IF NOT EXISTS	(	SELECT *				
						FROM	animal_grupo
						WHERE	id_rancho 	=	varIdRancho
						AND		id_usuario	=	varIdUsuario
						AND		id_animal	=	varIdAnimal
						AND		tipo		=	vartipo			)	THEN
		BEGIN

			INSERT animal_grupo
				(    id_rancho,		id_usuario,		id_animal,		tipo)
			SELECT
				varIdRancho, 	varIdUsuario,	varIdAnimal,	vartipo;
		END ;
	END if;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCliente`(	
								varCliente		CHAR(255), 	
	varIdPais		CHAR(36),	varIdEstado 	CHAR(36),
	varIdCiudad		CHAR(36),	varDireccion	CHAR(255),	
    varEmail		CHAR(45),	varTelefono		CHAR(20),
    varPFisicaMoral	CHAR(1))
BEGIN

	DECLARE	varIdCliente CHAR(36);

	SELECT	UUID()
	INTO	varIdCliente;

	INSERT	cliente
		(	id_cliente,		descripcion,	id_pais,	id_estado,		
			id_ciudad,		direccion,		email,		telefono,
            p_fisica_moral,		status)
	SELECT	varIdCliente,	varCliente,		varIdPais,	varIdEstado,	
			varIdCiudad,	varDireccion,	varEmail,	varTelefono,
            varPFisicaMoral,	'A';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarCompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCompra`(	
	varIdRancho	CHAR(36),		varIdProveedor	CHAR(36),		varFecha	DATETIME,
    varFactura	VARCHAR(45), 	varOrden		VARCHAR(45),	varSubtotal	DECIMAL(20,4),
    varIva		DECIMAL(20,4),	varTotal		DECIMAL(20,4)	)
BEGIN

	DECLARE	varIdCompra CHAR(36);

	SELECT	UUID()
	INTO	varIdCompra;

	INSERT	compra
		(	id_compra,		id_rancho,		id_proveedor,	fecha,		
			factura,		orden, 			subtotal, 		iva, 
            total)
	SELECT	varidCompra,	varIdRancho,	varIdProveedor,	varFecha,	
			varFactura,		varOrden,		varSubtotal,	varIva,
            varTotal;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCorral`(
	varId_Rancho		CHAR(36),	varNombre				CHAR(255),	
	varLocalizacion		CHAR(255),	varAlimentoIngresado	DECIMAL(20,4),
	varObservaciones	CHAR(255))
BEGIN

DECLARE	varId_corral CHAR(36);

	SELECT	UUID()
	INTO	varId_corral;

	INSERT	corral
		(	id_rancho,				id_corral,		nombre,
			localizacion,			num_animales,	alimento_ingresado,
			observaciones)
	SELECT	varId_Rancho,			varId_corral,	varNombre,
			varLocalizacion,		0,				varAlimentoIngresado,	
			varObservaciones;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarCorralDatos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCorralDatos`(
   varIdRancho		CHAR(36),		varIdCorral         CHAR(255),            
   varCategoria		CHAR(255),		varGanado_amedias   CHAR(255),
   varColor_arete	INT,			varFecha_nacimiento DATETIME,    
   varNumero_lote	CHAR(255),		varCompra           CHAR(255),        
   varPorcentaje	DECIMAL(20,4),	varIdProveedor		CHAR(36))
BEGIN
    
    INSERT corral_datos
    (	id_rancho,		id_corral,			categoria,		ganado_amedias,    
		color_arete,	fecha_nacimiento,	numero_lote,	compra,
		porcentaje,		id_proveedor)
    SELECT
		varIdRancho,    varIdCorral,         varCategoria,   varGanado_amedias,    
		varColor_arete, varFecha_nacimiento, varNumero_lote, varCompra,
		varPorcentaje,	varIdProveedor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarCria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCria`(
    varIdRancho	CHAR(36),		varIdMadre			CHAR(36),            
    varArete	CHAR(255),		varFechaNacimiento	DATETIME,    
    varIdSexo	CHAR(36),		varIdRaza			CHAR(36),	
	varPeso		DECIMAL(20,4),	varIdTipoParto		CHAR(36))
BEGIN
    DECLARE varIdCria, 
			varIdCorral CHAR(36);

	SELECT	UUID()
    INTO	varIdCria;    
    
    INSERT cria
    (	id_rancho,		id_madre,			id_cria,
		arete,			fecha_nacimiento,	id_sexo,
		id_raza,		status,				peso,
		id_tipo_parto)
    SELECT
		varIdRancho,	varIdMadre,			varIdCria,        
		varArete,		varFechaNacimiento,	varIdSexo,    
		varIdRaza,		'A',				varPeso,
		varIdTipoParto;

	-- obtener Corral de la madre
	SELECT	id_corral
	INTO	varIdCorral
	FROM	corral_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	varIdMadre;

	-- Agregar kardex de la Cria
	CALL agregarAnimal(
		varIdRancho,	varIdCorral,	NULL,
		NULL,			NULL,			NULL,
		NULL,			varIdSexo,		NOW(),
		varArete,		NULL,			NULL,
		NULL,			varPeso,		NULL,
		NULL,			NULL,			varIdRaza,
		'A',			varIdCria,		'N');

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarDetalleCompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarDetalleCompra`(
	varIdRancho	CHAR(36),		varIdCompra		CHAR(36),	varIdMedicina		CHAR(36), 
	varCantidad	INT(11),		varPresentacion	INT(11),	varPrecioUnitario	DECIMAL(20,4),
	varImporte	DECIMAL(20,4)
)
BEGIN

    DECLARE varIdDetalle INT(11);
 
    SELECT	COALESCE(MAX(id_detalle), 0) + 1
	INTO	varIdDetalle
	FROM	detalle_compra;
        
    INSERT detalle_compra(
		id_rancho,			id_compra,			id_medicina, 	id_detalle,
		cantidad,		presentacion,		precio_unitario,	importe)
    SELECT
		varIdRancho,		varIdCompra,		varIdMedicina,	varIdDetalle, 
		varCantidad,	varPresentacion,	varPrecioUnitario,	varImporte;    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarIngresoAlimento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarIngresoAlimento`(
    varNumeroLote	CHAR(255),		varIdCorral			CHAR(36),		varTotalAlimento	decimal(20,4),
    varFecha		DATETIME,		varCostoUnitario	DECIMAL(20,4),	varCostoTotal		DECIMAL(20,4),	
    varCarro		varchar(45))
BEGIN
    DECLARE varIdIngresoAlimento char(36);
	
	SELECT	UUID()
	INTO	varIdIngresoAlimento;
     	
    INSERT ingreso_alimento
    (	id_ingreso_alimento,	numero_lote,	id_corral,		total_alimento,
		fecha,					costo_unitario,	costo_total, 	carro	)
    SELECT
		varIdIngresoAlimento,	varNumeroLote,		varIdCorral,	varTotalAlimento,
        varFecha,				varCostoUnitario,	varCostoTotal,	varCarro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarMedicina` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarMedicina`(
	varCodigo		INT,			varNombre			CHAR(255),
	varCosto		DECIMAL(20,4),	varId_unidad		INT,
	varPresentacion	DECIMAL(20,4))
BEGIN

	DECLARE	varId_medicina CHAR(36);

	SELECT	UUID()
	INTO	varId_medicina;

	INSERT	medicina
		(	id_medicina,	codigo,				nombre,								costo,
			id_unidad,		presentacion,		costo_unitario,						status			)
	SELECT	varId_medicina,	varCodigo,			varNombre,							varCosto,
			varId_unidad,	varPresentacion,	varCosto / IFNULL(varPresentacion, 1),	"S";
		
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarMedicinaAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarMedicinaAnimal`(
	varIdRancho CHAR(36),		varMedicina	CHAR(36),	varIdAnimal			CHAR(36),	
	varDosis	DECIMAL(20,4),	varFecha	DATETIME)
BEGIN
	
	DECLARE varIdMedicinaAnimal	CHAR(36);
	DECLARE	varCosto	DECIMAL(20,4);
	/*
	UPDATE	rancho
	SET		id_medicina_animal	=	ifNull(id_medicina_animal, 0) + 1
	where	id_rancho			=	varIdRancho;

	select	id_medicina_animal	
	INTO	varIdMedicinaAnimal
	from	rancho
	where	id_rancho	=	varIdRancho;
*/
	SELECT	rancho_medicina.costo_promedio
	INTO	varCosto
	FROM	rancho_medicina
	WHERE	rancho_medicina.id_rancho	=	varIdRancho
	AND		rancho_medicina.id_medicina	=	varMedicina;

	SELECT	UUID()
	INTO	varIdMedicinaAnimal;

	INSERT INTO medicina_animal(
			id_rancho,		id_medicina_animal,		id_medicina,	id_animal,		dosis,		fecha,		costo)
	select	varIdRancho,	varIdMedicinaAnimal,	varMedicina, 	varIdAnimal,	varDosis,	varFecha,	varCosto;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarMedicinaAnimalGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarMedicinaAnimalGrupo`(
	varIdRancho 	CHAR(36),		varMedicina	CHAR(36),	
	varDosis		DECIMAL(20,4),	varFecha	DATETIME, 
	varIdUsuario	CHAR(36))
BEGIN
	DECLARE vb_termina BOOL DEFAULT FALSE;
	DECLARE varIdMedicinaAnimal	CHAR(36);
	DECLARE	varIdAnimal			CHAR(36);
	
	DECLARE cur_animales CURSOR
	FOR	SELECT	id_animal                                
		FROM	animal_grupo
		WHERE	id_rancho 	=	varIdRancho
		AND		id_usuario	=	varIdUsuario		
		AND		tipo		=	'medicina';

	DECLARE CONTINUE HANDLER 
	FOR SQLSTATE '02000'
	SET vb_termina = TRUE;

	OPEN cur_animales;
	Recorre_Cursor: LOOP

		FETCH cur_animales INTO varIdAnimal;
		
		IF vb_termina THEN
            LEAVE Recorre_Cursor;
        END IF;

		SELECT	UUID()
		INTO	varIdMedicinaAnimal;

		INSERT INTO medicina_animal(
				id_rancho,		id_medicina_animal,		id_medicina,	
				id_animal,		dosis,					fecha)
		select	varIdRancho,	varIdMedicinaAnimal,	varMedicina,
				varIdAnimal,	varDosis,				varFecha;	

	END LOOP;
  
	CLOSE cur_animales;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarMedicinas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarMedicinas`(
	varCodigo	INT,			varNombre		CHAR(255),
	varCosto	DECIMAL(20,4),	varId_unidad	CHAR(36))
BEGIN

	DECLARE	varId_medicina CHAR(36);

	SELECT	UUID()
	INTO	varId_medicina;

	INSERT	medicinas
		(	id_medicina,	codigo,	nombre,	costo,
			id_unidad,		status			)
	SELECT	varId_medicina,	varCodigo,	varNombre,	varCosto,
			varId_unidad,	"S";

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarMedicinaTratamiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarMedicinaTratamiento`(
	varIdTratamiento	CHAR(36),	varIdMedicina	CHAR(36),	varDosis	DECIMAL(20,4))
BEGIN

	INSERT INTO medicina_tratamiento
	SELECT	varIdTratamiento,	varIdMedicina,	varDosis;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarProveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarProveedor`(	
								varProveedor	CHAR(255), 	
	varIdPais		CHAR(36),	varIdEstado 	CHAR(36),
	varIdCiudad		CHAR(36),	varDireccion	CHAR(255),	
    varEmail		CHAR(45),	varTelefono		CHAR(20),
    varPFisicaMoral	CHAR(1))
BEGIN

	DECLARE	varIdProveedor CHAR(36);

	SELECT	UUID()
	INTO	varIdProveedor;

	INSERT	proveedor
		(	id_proveedor,		descripcion,	id_pais,	id_estado,		
			id_ciudad,			direccion,		email,		telefono,
            p_fisica_moral,		status)
	SELECT	varIdProveedor,		varProveedor,	varIdPais,	varIdEstado,	
			varIdCiudad,		varDireccion,	varEmail,	varTelefono,
            varPFisicaMoral,	'A';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarRancho` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRancho`(
	varDescripcion	CHAR(255),	varActividad	CHAR(255),
	varEstado		CHAR(36),	varCiudad		CHAR(36))
BEGIN

	DECLARE	varIdRancho	,
			varIdCorral	CHAR(36);

	SELECT	UUID()
	INTO	varIdRancho;

	INSERT	rancho(
			id_rancho,		descripcion,	actividad,		id_estado,	id_ciudad,	status	)
	SELECT	varIdRancho,	varDescripcion,	varActividad,	varEstado,	varCiudad,	"A";

	CALL agregarCorral(varIdRancho, 'Hospital','', 0.00,'');
    
    UPDATE	corral
    SET		status		=	'E'
    WHERE	id_rancho	=	varIdRancho
	AND		nombre		=	'Hospital';
    
    SELECT	id_corral
    INTO 	varIdCorral
    FROM	corral
    WHERE	id_rancho	=	varIdRancho
	AND		nombre		=	'Hospital';

	INSERT INTO concepto_movimiento	SELECT varIdRancho,	UUID(),	'Muerte',			'Mrt',	'S';

	INSERT INTO concepto_movimiento	SELECT varIdRancho,	UUID(),	'Peso',				'Pso',	'N';

	INSERT INTO concepto_movimiento	SELECT varIdRancho,	UUID(),	'Traspaso Salida',	'Tsa',	'S';

	INSERT INTO concepto_movimiento	SELECT varIdRancho,	UUID(),	'Traspaso Entrada',	'Ten',	'E';

	INSERT INTO concepto_movimiento	SELECT varIdRancho,	UUID(),	'Salida',			'Sal',	'S';

    UPDATE rancho SET 
		id_corral_hospital	=	varIdCorral,
		con_muerte				=	(	SELECT	id_concepto	FROM concepto_movimiento	WHERE	descripcion	=	'Muerte' 			AND id_rancho	=	varIdRancho	),
		con_pesaje				=	(	SELECT	id_concepto	FROM concepto_movimiento	WHERE	descripcion	=	'Peso'				AND id_rancho	=	varIdRancho	),
		con_traspaso_salida		=	(	SELECT	id_concepto	FROM concepto_movimiento	WHERE	descripcion	=	'Traspaso Salida'	AND id_rancho	=	varIdRancho	),
		con_traspaso_entrada	=	(	SELECT	id_concepto	FROM concepto_movimiento	WHERE	descripcion	=	'Traspaso Entrada'	AND id_rancho	=	varIdRancho	),
		con_salida				=	(	SELECT	id_concepto	FROM concepto_movimiento	WHERE	descripcion	=	'Salida'			AND id_rancho	=	varIdRancho	)
	WHERE	id_rancho	=	varIdRancho; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarRaza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRaza`(	
	varRaza	CHAR(255)	)
BEGIN

	DECLARE	varIdRaza CHAR(36);

	SELECT	UUID()
	INTO	varIdRaza;

	INSERT	raza
		(	id_raza,	descripcion, seleccionar, status)
	SELECT	varIdRaza,	varRaza, 'S', 'A'						;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarRecepcion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRecepcion`(
    varIdProveedor		CHAR(36),		varIdOrigen			CHAR(36),		varFolio			CHAR(45),
    varFechaCompra		DATETIME,		varFechaRecepcion	DATETIME,		varAnimales			int(10),
	varPesoOrigen		DECIMAL(20,4),	varLimiteMerma		DECIMAL(20,4),	varMerma			decimal(20,5),
	varPorcentajeMerma	decimal(20,4),	varPesoRecepcion	DECIMAL(20,4),	varNumeroLote		char(255),	
	varCostoFlete		DECIMAL(20,4),	varDevoluciones		int(10),		varCausaDevolucion	varchar(45))
BEGIN
    DECLARE varIdRecepcion char(36);
	declare i int;
    DECLARE varIdAnimal CHAR(36);

	SELECT	UUID()
	INTO	varIdRecepcion;
     	
	set varMerma = varPesoOrigen - varPesoRecepcion;
    
    set varPorcentajeMerma = ( varMerma * 100 ) / varPesoOrigen;

    INSERT recepcion
    (	id_recepcion,	id_proveedor,		id_origen,			folio,
		fecha_compra,	fecha_recepcion,	animales,			animales_pendientes,
		peso_origen,	limite_merma,		merma,				porcentaje_merma,	
		peso_recepcion,	numero_lote,		costo_flete,		devoluciones,		
		causa_devolucion	)
    SELECT
		varIdRecepcion,		varIdProveedor,		varIdOrigen,		varFolio,
		varFechaCompra,		varFechaRecepcion,	varAnimales,		varAnimales,
		varPesoOrigen,		varLimiteMerma,		varMerma,			varPorcentajeMerma,
		varPesoRecepcion,	varNumeroLote,		varCostoFlete,		varDevoluciones,	
		varCausaDevolucion;	

	-- set i = 0;
 
	-- while  i < varAnimales do

-- se agrega un animal base, para asignar alimento
		SELECT UUID()
		INTO varIdAnimal;
    
		INSERT animal
		(	id_animal,			id_proveedor,	                fecha_compra,		compra,
			numero_lote,		peso_compra,	                fecha_recepcion,	peso_recepcion,
			porcentaje_merma,   costo_flete,
            status)
		SELECT
			varIdAnimal,		varIdProveedor,	                varFechaCompra,		varfolio,
			varNumeroLote,		varPesoOrigen / varAnimales,	varFechaRecepcion,	varPesoRecepcion / varAnimales,
			varPorcentajeMerma, varCostoFlete / varAnimales,
            'A';
    
	-- set i = i + 1;
    -- end while;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarRegistroEmpadre` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRegistroEmpadre`(
	varIdHembra		CHAR(36),	varIdSemental	CHAR(36))
BEGIN
    DECLARE varIdRegistroEmpadre CHAR(36);

	SELECT UUID()
	INTO varIdRegistroEmpadre;

	INSERT registro_empadre
    (   id_registro_empadre,    fecha,	id_hembra,		id_semental,	
		status_gestacional,		aborto, activo,			id_tipo_parto )
    SELECT
        varIdRegistroEmpadre,	NOW(),	varIdHembra,	varIdSemental,
		'N',					'N',	'S',			NULL;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarStatusGestacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarStatusGestacion`(
    varIdRegistroEmpadre	CHAR(36),	varStatus		CHAR(2),
	varFechaChequeo			DATETIME,	varIdTipoParto	CHAR(36)	)
BEGIN
	
	DECLARE varIdStatusGestacional CHAR(36);

	SELECT	UUID()
	INTO 	varIdStatusGestacional;

	INSERT INTO status_gestacional(
		id_status_gestacional,	id_registro_empadre,	status, 	fecha_chequeo, 		
		id_tipo_parto)
	SELECT
		varIdStatusGestacional,	varIdRegistroEmpadre,	varStatus,	varFechaChequeo,	
		varIdTipoParto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarTratamiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarTratamiento`(
	varCodigo	CHAR(45),	varNombre	CHAR(255))
BEGIN

	DECLARE varIdTratamiento CHAR(36);
	
	SELECT 	UUID()
	INTO	varIdTratamiento;

	INSERT INTO tratamiento	
	(	id_tratamiento,		codigo,		nombre,		status,	fecha	)
	SELECT 
		varIdTratamiento,	varCodigo,	varNombre,	'A',	NOW();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarTratamientoAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarTratamientoAnimal`(
	varIdRancho	CHAR(36),	varIdTratamiento	CHAR(36),	
	varIdAnimal	CHAR(36),	varDosisTratamiento	DECIMAL(20,4),
	varFecha			DATETIME)
BEGIN
	DECLARE	varIdMedicina 		CHAR(36);
	DECLARE	varDosisMedicinas	DECIMAL(20,4);
	DECLARE varDosisTotal		DECIMAL(20,4);
	
	DECLARE vb_termina BOOL DEFAULT FALSE;
	
	DECLARE curMedicina CURSOR
	FOR	SELECT mt.id_medicina,	mt.dosis        
		FROM   medicina_tratamiento mt,	medicina m                 
		WHERE  mt.id_medicina		=	m.id_medicina 
		AND		mt.id_tratamiento	=	varIdTratamiento;
	
	DECLARE CONTINUE HANDLER 
	FOR SQLSTATE '02000'
	SET vb_termina = TRUE;	

	OPEN curMedicina;

	Recorre_Cursor: LOOP
	
		FETCH curMedicina INTO varIdMedicina, varDosisMedicinas;

		IF vb_termina THEN
        
			LEAVE Recorre_Cursor;
		END IF;
		
		SET	varDosisTotal = varDosisMedicinas * varDosisTratamiento;

		CALL agregarMedicinaAnimal(
			varIdRancho, 	varIdMedicina,	varIdAnimal,	
			varDosisTotal,	varFecha);

	END LOOP;
	
	CLOSE curMedicina;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregarUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarUsuario`(
varLog CHAR(255), varPassword CHAR(255), 
varNombre CHAR(255), varApellido CHAR(255), 
varId_estado CHAR(36), varId_ciudad CHAR(36), 
varCorreo VARCHAR(255), varFecha varchar(36), varTelefono VARCHAR(10))
BEGIN
	DECLARE var_id_usuario	CHAR(36); 

/*		SET @QUERY = CONCAT_WS('','GRANT SELECT, INSERT, DELETE, UPDATE, EXECUTE on *.* TO ',CHAR(39) ,varLog,CHAR(39), '@', CHAR(39) , '%' , CHAR(39), ' IDENTIFIED BY', CHAR(39) , varPassword , CHAR(39), 'WITH GRANT OPTION');

		PREPARE stmt1 FROM @QUERY;	
		EXECUTE stmt1;
*/
	SELECT	UUID()
	INTO	var_id_usuario;

	INSERT INTO usuario
		(	id_usuario,
			log,
			password,
            nombre, 
            apellido,
            id_estado, 
            id_ciudad, 
            correo, 
            fecha_nacimiento, 
            telefono
            )
	SELECT	var_id_usuario, 
    varLog, 
    varPassword, 
    varNombre, 
	varApellido, 
    varId_estado, 
    varId_ciudad, 
	varCorreo, 
    varFecha, 
    varTelefono;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `animalesPorCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `animalesPorCorral`(
	varIdCorral CHAR(36)	)
BEGIN
	
	UPDATE corral
	SET num_animales =	(	SELECT	count(	*	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		total_kilos	=	(	SELECT	SUM(	peso_actual	) 
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_minimo	=	(	SELECT	MIN(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_maximo	=	(	SELECT	MAX(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_promedio	=	(	SELECT	AVG(	peso_actual	)
								FROM	animal, corral_animal
								WHERE	STATUS					=	'A'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		peso_ganancia	=	(	SELECT	SUM(	peso_actual	) - SUM(	peso_compra) 
								FROM	animal, corral_animal
								WHERE	STATUS					=	'A'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		id_raza			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_raza
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_raza,	COUNT(DISTINCT id_raza) AS count, 
											(	SELECT	id_raza	
												FROM	raza
												WHERE	descripcion = 'Mixto' ) mixto
											FROM	animal a, corral_animal ca
											WHERE	a.id_animal	=	ca.id_animal
											AND  	id_corral	=	varIdCorral		)	a1	),                                                                                      
		id_sexo			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_sexo
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_sexo,	COUNT(DISTINCT id_sexo) AS count, 
											(	SELECT	id_sexo	
												FROM	sexo
												WHERE	descripcion = 'Mixto' ) mixto
												FROM	animal a, corral_animal ca
												WHERE	a.id_animal = ca.id_animal
												AND		id_corral 	= varIdCorral	)	a1	),
		
        total_kilos_iniciales	=	(	SELECT	SUM(peso_compra)										
										FROM    corral_animal c, animal a
										WHERE   c.id_animal	=	a.id_animal
										AND     c.id_corral	=	corral.id_corral),
         
		total_costo_medicina	=	(	SELECT  SUM( ma.dosis * rm.costo_promedio)
										FROM 	corral_animal ca, medicina_animal ma, medicina m, rancho_medicina rm
										WHERE 	ma.id_medicina	=	m.id_medicina
                                        AND 	rm.id_medicina 	= 	m.id_medicina
                                        AND		ca.id_animal	=	ma.id_animal
										AND		ma.id_rancho	=	ca.id_rancho
										AND 	rm.id_rancho	= 	corral.id_rancho
                                        AND		ca.id_rancho	=	corral.id_rancho
										AND		ca.id_corral	=	corral.id_corral	),
                                        
		ganancia_promedio	=	(	SELECT	AVG(	animal.ganancia_promedio		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'A'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal),
		
        promedio_alimento	=	(	SELECT	AVG(	animal.promedio_alimento		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'A'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal	),

		alimento_ingresado	=	(	SELECT	sum(	animal.total_alimento		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'A'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal	)
									
	WHERE corral.id_corral	=	varIdCorral;    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `animalesPorCorralCerrado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `animalesPorCorralCerrado`(
	varIdCorral CHAR(36)	)
BEGIN
	
	UPDATE corral
	SET num_animales =	(	SELECT	count(	*	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'V'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
        dias_corral		=	(	SELECT ROUND(AVG(DATEDIFF(m.fecha, a.fecha_ingreso))) 
								FROM movimiento m
									LEFT OUTER JOIN clase_movimiento c ON m.id_clase_movimiento = c.id_clase_movimiento, 
									detalle_movimiento d, rancho r, animal a, corral_animal ca
								WHERE    m.id_rancho = r.id_rancho
									AND m.id_concepto = r.con_salida
									AND m.id_rancho = d.id_rancho
									AND m.id_movimiento = d.id_movimiento
									AND m.id_concepto = d.id_concepto
									AND d.id_animal = a.id_animal
									AND d.id_animal = ca.id_animal
									AND ca.id_corral = corral.id_corral
									AND a.status = 'V'),
		
		total_kilos	=	(	SELECT	SUM(	peso_actual	) 
							FROM	animal, corral_animal
							WHERE	STATUS					=	'V'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_minimo	=	(	SELECT	MIN(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'V'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_maximo	=	(	SELECT	MAX(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'V'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_promedio	=	(	SELECT	AVG(	peso_actual	)
								FROM	animal, corral_animal
								WHERE	STATUS					=	'V'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		peso_ganancia	=	(	SELECT	SUM(	peso_actual	) - SUM(	peso_compra) 
								FROM	animal, corral_animal
								WHERE	STATUS					=	'V'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		id_raza			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_raza
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_raza,	COUNT(DISTINCT id_raza) AS count, 
											(	SELECT	id_raza	
												FROM	raza
												WHERE	descripcion = 'Mixto' ) mixto
											FROM	animal a, corral_animal ca
											WHERE	a.id_animal	=	ca.id_animal
											AND  	id_corral	=	varIdCorral		)	a1	),                                                                                      
		id_sexo			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_sexo
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_sexo,	COUNT(DISTINCT id_sexo) AS count, 
											(	SELECT	id_sexo	
												FROM	sexo
												WHERE	descripcion = 'Mixto' ) mixto
												FROM	animal a, corral_animal ca
												WHERE	a.id_animal = ca.id_animal
												AND		id_corral 	= varIdCorral	)	a1	),
		
        total_kilos_iniciales	=	(	SELECT	SUM(peso_compra)										
										FROM    corral_animal c, animal a
										WHERE   c.id_animal	=	a.id_animal
										AND     c.id_corral	=	corral.id_corral),
         
		total_costo_medicina	=	(	SELECT  SUM( ma.dosis * rm.costo_promedio)
										FROM 	corral_animal ca, medicina_animal ma, medicina m, rancho_medicina rm
										WHERE 	ma.id_medicina	=	m.id_medicina
                                        AND 	rm.id_medicina 	= 	m.id_medicina
                                        AND		ca.id_animal	=	ma.id_animal
										AND		ma.id_rancho	=	ca.id_rancho
										AND 	rm.id_rancho	= 	corral.id_rancho
                                        AND		ca.id_rancho	=	corral.id_rancho
										AND		ca.id_corral	=	corral.id_corral	),
                                        
		total_costo_flete	=		(	SELECT	SUM(a.costo_flete) 
										FROM	animal a,	corral_animal ca
										WHERE	ca.id_animal = a.id_animal 	
                                        AND 	ca.id_corral = corral.id_corral	),
                                        
       fecha_inicio			=		(	SELECT MIN(a.fecha_ingreso) 
										FROM animal a, corral_animal ca
										WHERE a.id_animal = ca.id_animal 
                                        AND corral.id_corral = ca.id_corral 	),                                 
                                        
		ganancia_promedio	=	(	SELECT	AVG(	animal.ganancia_promedio		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'V'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal),
		
        promedio_alimento	=	(	SELECT	AVG(	animal.promedio_alimento		)
									FROM	animal, corral_animal
									WHERE	corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal	),
                                    
		medicina_promedio	=	(	SELECT total_costo_medicina / num_animales
										),

		alimento_ingresado	=	(	SELECT	sum(	animal.total_alimento		)
									FROM	animal, corral_animal
									WHERE	corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal	),
                                    
		conversion_alimenticia	=	(	SELECT corral.ganancia_promedio / corral.promedio_alimento 	),
                                        
		merma				=		(	SELECT     AVG(a.porcentaje_merma)
										FROM    animal a,    corral_animal ca
										WHERE    a.id_animal = ca.id_animal
											AND ca.id_corral = corral.id_corral	)
		
									
	WHERE corral.id_corral	=	varIdCorral;    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calcula_existencias_costos_medicna` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calcula_existencias_costos_medicna`(
	varIdRancho	CHAR(36),	varIdMedicina	CHAR(36)	)
BEGIN
	
    -- Obtiene Existencia Inicial
	-- Suma Cantidades en Compras
    -- Suma Cantidades en Aplicaciones
	-- (	Existencia Inicial + Compras	)	-	Aplicaciones
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cierreCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cierreCorral`(
varIdCorral CHAR(36), varIdRancho CHAR(36))
BEGIN

UPDATE corral c 
SET 
    status = 'C',
    c.fecha_cierre = NOW()
WHERE
    c.id_corral = varIdCorral
        AND c.id_rancho = varIdRancho;
        
CALL animalesPorCorralCerrado(varIdCorral);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `corralPesajesGrafica` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `corralPesajesGrafica`(
	varIdRancho	CHAR(36),	varIdCorral CHAR(36))
BEGIN
	DECLARE	varfechaMin		DATETIME;
	DECLARE varPesoInicial	DECIMAL(20,4);
    DECLARE	varFechaMax		DATETIME;
        
	SELECT	MIN(fecha),		MAX(fecha) -- c.*, r.con_pesaje, fecha
    INTO	varfechaMin,	varFechaMax
	FROM	corral_animal c, rancho r, movimiento m, detalle_movimiento d 
	WHERE 	m.id_rancho 	=	d.id_rancho
	AND		m.id_movimiento	=	d.id_movimiento
	AND		m.id_concepto	=	d.id_concepto
	AND		c.id_animal		=	d.id_animal
	AND		r.con_pesaje	=	m.id_concepto
	AND		r.id_rancho		=	m.id_rancho
	AND		r.id_rancho 	= 	c.id_rancho
	AND		c.id_rancho 	= 	varIdRancho -- 'cb09a99b-0552-11e5-be6c-a4db30742c49'
	AND	  	c.id_corral 	= 	varIdCorral;	-- '7773887c-092a-11e5-8440-a4db30742c49';
/*    
	SELECT	 SUM(peso)
	INTO	varPesoInicial
	FROM	corral_animal c, rancho r, movimiento m, detalle_movimiento d 
	WHERE 	m.id_rancho 	=	d.id_rancho
	AND		m.id_movimiento	=	d.id_movimiento
	AND		m.id_concepto	=	d.id_concepto
	AND		c.id_animal		=	d.id_animal
	AND		r.con_pesaje	=	m.id_concepto
	AND		r.id_rancho		=	m.id_rancho
	AND		r.id_rancho		= c.id_rancho
	AND		c.id_rancho 	=  varIdRancho
	AND	  	c.id_corral 	= varIdCorral
	AND		CONVERT(m.fecha,  DATE) = CONVERT(varfechaMin, DATE);
*/

	IF varFechaMax = null then
		SET varFechaMax	= DATE_ADD(CONVERT(varfechaMin, DATE),INTERVAL 1 SECOND);
    END IF;

	SELECT	COALESCE(varfechaMin,'1900-01-01 00:00:00'), COALESCE(total_kilos_iniciales,0.0)
    FROM 	corral 
	WHERE 	id_rancho = varIdRancho -- 'cb09a99b-0552-11e5-be6c-a4db30742c49'
	AND	  	id_corral = varIdCorral
	UNION
	SELECT	COALESCE(CONVERT(varFechaMax, DATETIME),'1900-01-01 00:00:00'), COALESCE(total_kilos,0.0)
	FROM 	corral 
	WHERE 	id_rancho = varIdRancho -- 'cb09a99b-0552-11e5-be6c-a4db30742c49'
	AND	  	id_corral = varIdCorral;    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `crear_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `crear_usuario`(
	PARlogin	VARCHAR(45),	PARpassword	VARCHAR(45))
BEGIN
	DECLARE var_id_usuario	CHAR(36);

	IF	NOT	EXISTS(	SELECT	*
					FROM	usuario
					WHERE	log	=	PARlogin)
	THEN
		SET @QUERY = CONCAT_WS('','grant SELECT,INSERT,DELETE,UPDATE,EXECUTE on eon.* to `',CHAR(39) ,PARlogin,CHAR(39), '`@`', CHAR(39) , '%' , CHAR(39), '` identified by `', CHAR(39) , PARpassword , CHAR(39));

		PREPARE stmt1 FROM @QUERY;
		EXECUTE stmt1;
	END IF;

	SELECT	UUID()
	INTO	var_id_usuario;

	INSERT INTO usuario
		(	id_usuario,
			log,
			password	)
	SELECT	var_id_usuario,	
			PARlogin,
			PARpassword;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `duplicaMovimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `duplicaMovimiento`(
		varIdRancho			CHAR(36), 	varIdMovimientoOrigen	CHAR(36), 
		varConceptoOrigen	CHAR(36),	varIdRanchoDestino		CHAR(36),
		varConceptoDestino	CHAR(36)	)
BEGIN

	DECLARE	varIdMovimientoDestino CHAR(36);
	
	SELECT	UUID()
	INTO	varIdMovimientoDestino;

	INSERT INTO movimiento(
			id_rancho,				id_movimiento,			id_concepto,		fecha,					
			id_rancho_origen,		id_corral_origen,		id_rancho_destino,	id_corral_destino,
			id_clase_movimiento,	numero_pedido,			id_cliente,			necropcia,	
			dx_muerte,				etapa_reproductiva,		causa_entrada,		observacion,
			peso)
	SELECT	varIdRanchoDestino,		varIdMovimientoDestino,	varConceptoDestino,	fecha,			
			id_rancho,				id_corral_origen,		varIdRanchoDestino,	id_corral_destino,
			id_clase_movimiento,	numero_pedido,			id_cliente,			necropcia,
			dx_muerte,				etapa_reproductiva,		causa_entrada,		observacion,
			peso
	FROM	movimiento m2
	WHERE	m2.id_rancho		=	varIdRancho
	AND		m2.id_concepto		=	varConceptoOrigen
	AND		m2.id_movimiento	=	varIdMovimientoOrigen;

	INSERT	INTO detalle_movimiento(
			id_rancho,			id_movimiento,			id_concepto,		id_detalle,	
			id_animal)
	SELECT	varIdRanchoDestino,	varIdMovimientoDestino,	varConceptoDestino,	id_detalle,	
			id_animal
	FROM	detalle_movimiento d2
	WHERE	d2.id_rancho		=	varIdRancho	
	AND		d2.id_concepto		=	varConceptoOrigen
	AND		d2.id_movimiento	=	varIdMovimientoOrigen;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarAnimal`(	
	varIdAnimal	CHAR(36)	)
BEGIN
	
	UPDATE animal
	SET 	status	=	'E'
	WHERE	id_animal	=	varIdAnimal;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarAnimalGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarAnimalGrupo`(
	varIdRancho CHAR(36),	varIdUsuario	CHAR(36),
	varIdAnimal	CHAR(36),	varTipo			CHAR(255))
BEGIN

	DELETE FROM animal_grupo
	WHERE	id_rancho	=	varIdRancho
	AND		id_usuario	=	varIdUsuario
	AND		id_animal	=	varIdAnimal
	AND		tipo		=	vartipo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarAnimalGrupoUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarAnimalGrupoUsuario`(
	varIdRancho CHAR(36),	varIdUsuario	CHAR(36),
	varTipo		CHAR(255)							)
BEGIN

	DELETE FROM animal_grupo
	WHERE	id_rancho	=	varIdRancho
	AND		id_usuario	=	varIdUsuario
	AND		tipo		=	vartipo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarCliente`(
	varIdCliente	CHAR(36))
BEGIN

	UPDATE	cliente
	SET		status		=	'E'
	WHERE	id_cliente	=	varIdCliente;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarCorral`(
	varIdRancho		CHAR(36),	varIdCorral	CHAR(36))
BEGIN

	UPDATE corral
    SET		status = 'E'
    WHERE   id_rancho 	=	varIdRancho
    AND		id_corral	=	varIdCorral;    	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarCria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarCria`(
	varIdRancho int,			varIdCria int)
BEGIN
	
	UPDATE cria
	set status = 'E'
	where	id_rancho	=	varIdRancho
	and		id_cria		=	varIdCria;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarMedicina` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarMedicina`(
	varId_medicina CHAR(36))
BEGIN

	UPDATE medicina
	set status		=	'N'
	where id_medicina = varId_medicina;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarMedicinaAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarMedicinaAnimal`(
	varIdRancho CHAR(36),			varIdMedicinaAnimal	CHAR(36))
BEGIN
	
	DELETE FROM	medicina_animal
	WHERE	id_rancho			=	varIdRancho
	AND		id_medicina_animal	=	varIdMedicinaAnimal; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarMedicinaTratamiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarMedicinaTratamiento`(
	varIdTratamiento	CHAR(36),	varIdMedicina	CHAR(36))
BEGIN
	DELETE FROM	medicina_tratamiento
	WHERE	id_tratamiento	=	varIdTratamiento
	AND		id_medicina		=	varIdMedicina;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarProveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarProveedor`(
	varIdProveedor	CHAR(36))
BEGIN

	UPDATE	proveedor
	SET		status			=	'E'
	WHERE	id_proveedor	=	varIdProveedor;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarRancho` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarRancho`(
	varIdRancho	CHAR(36))
BEGIN

	UPDATE	rancho
	SET		status	=	'E'
	WHERE	id_rancho	=	varIdRancho;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarRaza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarRaza`(
	varIdRaza	CHAR(36))
BEGIN

	UPDATE	raza
	SET		status	=	'E'
	WHERE	id_raza	=	varIdRaza;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarTratamiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarTratamiento`(
	varIdTratamiento	CHAR(36))
BEGIN
	UPDATE	tratamiento
	SET		status			=	'E'
	WHERE	id_tratamiento	=	varIdTratamiento;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarTraspasoSalida` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generarTraspasoSalida`(
	varIdRancho			CHAR(36),	varConceptoTrasSalida	CHAR(36),	OUT varIdMovimiento		CHAR(36),
	varFecha			DATETIME,	varIdRanchoDestino		CHAR(36),		varIdCorralOrigen	CHAR(36),
	varIdCorralDestino	CHAR(36))
BEGIN
/*
	SELECT	con_traspaso_salida
	INTO	varConceptoTrasSalida
	FROM 	rancho
	WHERE	id_rancho	=	varIdRancho;
*/
	/*	SALIDA DEL CORRAL ORIGEN */
-- CALL nuevoIdMovimiento(	varIdRancho,	varConceptoTrasSalida,	@varIdMovimiento);
/*
	UPDATE	concepto_movimiento
	set		id_consecutivo	=	ifnull(id_consecutivo,0)	+	1
	where	id_rancho		=	varIdRancho
	and 	id_concepto		=	varConceptoTrasSalida;

	select	id_consecutivo
	INTO	varIdMovimiento
	from	concepto_movimiento
	where	id_rancho	=	varIdRancho
	and 	id_concepto	=	varConceptoTrasSalida;	
*/
	SELECT	UUID()
	INTO	varIdMovimiento;
	
	CALL insertarMovimiento(varIdRancho,		varIdMovimiento,	varConceptoTrasSalida,	
							varFecha,			varIdRancho,		varIdCorralOrigen,	
                            varIdRanchoDestino,	varIdCorralDestino,	NULL,
							NULL,				NULL,				NULL,	
                            NULL,				NULL,				NULL,				
                            NULL,				NULL);				
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ingresoHospital` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ingresoHospital`(
	varIdRancho CHAR(36),	varIdAnimal			CHAR(36),		varFecha	DATETIME,
	varCausa	CHAR(255),	varObservaciones	CHAR(255))
BEGIN
	
	DECLARE varConceptoTrasSalida,
			varConceptoTrasEntrada,
			varIdMovimiento,
			varIdDetalle,
			varIdCorralOrigen,
			varIdCorralHospital	CHAR(36);
	
	SELECT	con_traspaso_entrada, 	con_traspaso_salida, 	id_corral_hospital
	INTO	varConceptoTrasEntrada,	varConceptoTrasSalida,	varIdCorralHospital
	FROM 	rancho
	WHERE	id_rancho	=	varIdRancho;

	SELECT	id_corral
	INTO	varIdCorralOrigen
	FROM	corral_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	varIdAnimal;

	/*	SALIDA DEL CORRAL ORIGEN */
	SELECT	UUID()
	INTO	varIdMovimiento;
	
	CALL insertarMovimiento(
		varIdRancho,		varIdMovimiento,		varConceptoTrasSalida,	
		varFecha,			NULL,					varIdCorralOrigen,	
		NULL,				varIdCorralHospital,	NULL,
		NULL,				NULL,					NULL,
		NULL,				NULL,					varCausa,
		varObservaciones,	NULL);
			
	CALL insertarDetalleMovimiento(	
		varIdRancho,	varIdMovimiento,	varConceptoTrasSalida,
		varIdAnimal);	

	CALL duplicaMovimiento( 
		varIdRancho,	varIdMovimiento, 		varConceptoTrasSalida, 
		varIdRancho,	varConceptoTrasEntrada );
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertarDetalleMovimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarDetalleMovimiento`(	
	varIdRancho				CHAR(36),	varIdMovimiento CHAR(36),	
	varConceptoMovimiento 	CHAR(36),	varIdAnimal		CHAR(36)	)
BEGIN

	DECLARE varIdDetalle INT;

	CALL nuevoIdDetalle(varIdRancho,varConceptoMovimiento, varIdMovimiento, @varIdDetalle);
	
	INSERT INTO detalle_movimiento(	
				id_rancho,		id_movimiento,		id_concepto,			id_detalle,		id_animal)
		values(	varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	@varIdDetalle,	varIdAnimal);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertarMovimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarMovimiento`(
	varIdRancho			CHAR(36),	varIdMovimiento 		CHAR(36),		varConceptoMovimiento 	CHAR(36),		
	varFecha			DATETIME,	varIdRanchoOrigen		CHAR(36),		varIdCorralOrigen		CHAR(36),
	varIdRanchoDestino	CHAR(36),	varIdCorralDestino		CHAR(36),		varIdClaseMovimiento	CHAR(36),
	varNumeroPedido		CHAR(255),	varIdCliente			CHAR(36),		varNecropcia			CHAR(255),
	varDxMuerte			CHAR(255),	varEtapaReproductiva	CHAR(255),		varCausaEntrada			CHAR(255),
	varObservacion		CHAR(255),	varPeso					DECIMAL(12,4))
BEGIN

	INSERT INTO movimiento(	
		id_rancho,			id_movimiento,		id_concepto,			fecha,					id_rancho_origen,
		id_corral_origen,	id_rancho_destino,	id_corral_destino,		id_clase_movimiento,	numero_pedido,
		id_cliente,			necropcia,			dx_muerte,				etapa_reproductiva,		causa_entrada,
		observacion,		peso)
	VALUES(	
		varIdRancho,		varIdMovimiento,	varConceptoMovimiento,	varFecha,				varIdRanchoOrigen,
		varIdCorralOrigen,	varIdRanchoDestino,	varIdCorralDestino,		varIdClaseMovimiento,	varNumeroPedido,
		varIdCliente,		varNecropcia,		varDxMuerte,			varEtapaReproductiva,	varCausaEntrada,
		varObservacion,		varPeso);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `movimientoMuerte` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoMuerte`(	
	varIdRancho 	CHAR(36),	varIdAnimal				CHAR(36),		
	varFechaMuerte	DATETIME,	varNecropcia			CHAR(255), 
	varDxMuerte 	CHAR(255), 	varEtapaReproductiva	CHAR(255))
BEGIN
	
	DECLARE varConceptoMovimiento,	
			varIdMovimiento,
			varIdDetalle	CHAR(36);
	
	SELECT	con_muerte
	INTO	varConceptoMovimiento
	FROM 	rancho
	WHERE 	id_rancho	=	varIdRancho;
	
	-- CALL nuevoIdMovimiento(varIdRancho,varConceptoMovimiento, @varIdMovimiento);
	SELECT	UUID()
	INTO	varIdMovimiento;

	CALL insertarMovimiento(varIdRancho,	varIdMovimiento,		varConceptoMovimiento,	
							varFechaMuerte,	NULL,					NULL,
                            NULL,			NULL,					NULL,
							NULL,			NULL,					varNecropcia,
                            varDxMuerte,	varEtapaReproductiva,	NULL,
                            NULL,			NULL);

	-- CALL nuevoIdDetalle(varIdRancho,varConceptoMovimiento, varIdMovimiento, @varIdDetalle);

	CALL insertarDetalleMovimiento(	
		varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	varIdAnimal);

	UPDATE	animal
	SET 	status		=	'M'
	WHERE	id_animal	=	varIdAnimal;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `movimientoPeso` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoPeso`(	
	varIdRancho CHAR(36),	varIdAnimal	CHAR(36),		
	varFecha	DATETIME,	varPeso		DECIMAL(12,4))
BEGIN
	
	DECLARE varConceptoMovimiento,	
			varIdMovimiento,
			varIdDetalle	CHAR(36);
	
	SELECT	con_pesaje
	INTO	varConceptoMovimiento
	FROM 	rancho
	WHERE 	id_rancho	=	varIdRancho;
	
    IF NOT EXISTS(	SELECT	* 
					FROM 	movimiento m, detalle_movimiento d
					WHERE	m.id_rancho		=	d.id_rancho
                    AND		m.id_movimiento	=	d.id_movimiento
					AND		m.id_concepto	=	d.id_concepto
                    AND		m.id_rancho		=	varIdRancho
                    AND		d.id_animal		=	varIdAnimal
                    AND		m.fecha			=	varFecha
					AND		m.peso			=	varPeso) THEN
		BEGIN
    
			-- CALL nuevoIdMovimiento(varIdRancho,varConceptoMovimiento, @varIdMovimiento);
			SELECT	UUID()	
			INTO	varIdMovimiento;
	
			CALL insertarMovimiento(varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	
									varFecha, 		NULL,				NULL,	
									NULL,			NULL,				NULL,	
									NULL,			NULL,				NULL,
									NULL,			NULL,				NULL,
									NULL,			varPeso);
	
			CALL insertarDetalleMovimiento(	
				varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	
				varIdAnimal);	
	
			UPDATE animal SET peso_actual	=	varPeso
			WHERE id_animal	=	varIdAnimal;
		END;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `movimientoSalida` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoSalida`(	
	varIdRancho 		CHAR(36),		varIdAnimal				CHAR(36),		
	varFechaSalida		DATETIME,		varIdClaseMovimiento	CHAR(255), 
    varNumeroPedido		CHAR(255), 		varIdCliente			CHAR(255),
	varPesoActual		DECIMAL(20,4))
BEGIN
	
	DECLARE varConceptoMovimiento,	
			varIdMovimiento,
			varIdDetalle,
			varIdCorralOrigen	CHAR(36);
	
	SELECT 	id_corral
	INTO	varIdCorralOrigen
	FROM 	corral_animal
	WHERE 	id_animal	=	varIdAnimal;

	SELECT	con_salida
	INTO	varConceptoMovimiento
	FROM	rancho
	WHERE 	id_rancho	=	varIdRancho;
	
	-- CALL nuevoIdMovimiento(varIdRancho,varConceptoMovimiento, @varIdMovimiento);

	SELECT UUID()
    INTO	varIdMovimiento;

	CALL insertarMovimiento(
		varIdRancho,		varIdMovimiento,	varConceptoMovimiento,	
		varFechaSalida, 	NULL,				varIdCorralOrigen,	
		NULL,				NULL,				varIdClaseMovimiento,
        varNumeroPedido,	varIdCliente,		NULL,	
        NULL,				NULL,				NULL,	
        NULL,				varPesoActual);

	-- CALL nuevoIdDetalle(varIdRancho,varConceptoMovimiento, varIdMovimiento, @varIdDetalle);

	CALL insertarDetalleMovimiento(
		varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	varIdAnimal);

		-- Se inserta porque el movimiento genera un cambio de corral
	IF NOT EXISTS (	SELECT	* 
					FROM	corral_animal 
					WHERE	id_rancho	=	varIdRancho
					AND		id_corral	=	varIdCorralOrigen
                    AND		id_animal	=	varIdAnimal)
	THEN
     
		INSERT INTO corral_animal
		SELECT varIdRancho, varIdCorralOrigen, varIdAnimal;
	END IF;

	UPDATE animal
	SET status		=	'V',
		peso_actual	=	varPesoActual
	WHERE id_animal	=	varIdAnimal;
/*
    INSERT INTO corral_animal
	SELECT varIdRancho, varIdCorralOrigen, varIdAnimal;
*/
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `movimientoSalidaGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoSalidaGrupo`(	
	varIdRancho 			CHAR(36),	varFechaSalida	DATETIME,
	varIdClaseMovimiento	CHAR(255),	varNumeroPedido	CHAR(255),
	varIdCliente			CHAR(255),		varPesoActual	DECIMAL(20,4),
	varIdUsuario			CHAR(36))
BEGIN
	
	DECLARE varIdAnimal,		varIdCorralOrigen,
			varIdDetalle,		varConceptoMovimiento,	
            varIdMovimiento		CHAR(36);

	DECLARE varPesoProrrateado	DECIMAL(20,4);

	DECLARE vb_termina BOOL DEFAULT FALSE;
	
	DECLARE cur_animales CURSOR
	FOR	SELECT	animal_grupo.id_animal,	id_corral                                
		FROM	animal_grupo, corral_animal
		WHERE	animal_grupo.id_animal	=	corral_animal.id_animal
        AND		animal_grupo.id_rancho	=	varIdRancho
		AND		id_usuario				=	varIdUsuario		
		AND		tipo					=	'salida';

	DECLARE CONTINUE HANDLER 
	FOR SQLSTATE '02000'
	SET vb_termina = TRUE;	

	SELECT (varPesoActual	/	(	SELECT COUNT(*)
									FROM	animal_grupo
									WHERE	id_rancho 	=	varIdRancho
									AND		id_usuario	=	varIdUsuario		
									AND		tipo		=	'salida') )
	INTO	varPesoProrrateado;

	SELECT	con_salida
	INTO	varConceptoMovimiento
	FROM	rancho
	WHERE 	id_rancho	=	varIdRancho;

	OPEN cur_animales;

	Recorre_Cursor: LOOP
		
		FETCH cur_animales INTO varIdAnimal, varIdCorralOrigen;

		IF vb_termina THEN
        
            LEAVE Recorre_Cursor;
        END IF;

		SELECT UUID()
        INTO varIdMovimiento;    

		CALL insertarMovimiento(varIdRancho,		varIdMovimiento,	varConceptoMovimiento,	
								varFechaSalida,		NULL,				varIdCorralOrigen,	
                                NULL,				NULL,				varIdClaseMovimiento,
                                varNumeroPedido,	varIdCliente,		NULL,	
                                NULL,				NULL,				NULL,	
                                NULL,				varPesoProrrateado);

		CALL insertarDetalleMovimiento(	varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	varIdAnimal);

	-- Se inserta porque el movimiento genera un cambio de corral
		IF NOT EXISTS (SELECT * FROM corral_animal WHERE id_rancho = varIdRancho AND id_corral = varIdCorralOrigen AND id_animal = varIdAnimal)
		THEN
       
			INSERT INTO corral_animal
			SELECT varIdRancho, varIdCorralOrigen, varIdAnimal;
		END IF;

		UPDATE animal
		SET		status		=	'V',
				peso_actual	=	varPesoProrrateado
		WHERE 	id_animal	=	varIdAnimal;

	END LOOP;
  	CLOSE cur_animales;

	DELETE	FROM	animal_grupo
			WHERE	id_rancho 	=	varIdRancho
			AND		id_usuario	=	varIdUsuario
			AND		tipo		=	'salida';

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `movimientoTraspasoSalida` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoTraspasoSalida`(
	varIdRancho			CHAR(36),	varConceptoTrasSalida	CHAR(36),	OUT varIdMovimiento		CHAR(36),
	varFecha			DATETIME,	varIdRanchoDestino		CHAR(36),		varIdCorralOrigen	CHAR(36),
	varIdCorralDestino	CHAR(36))
BEGIN
/*
	SELECT	con_traspaso_salida
	INTO	varConceptoTrasSalida
	FROM 	rancho
	WHERE	id_rancho	=	varIdRancho;
*/
	/*	SALIDA DEL CORRAL ORIGEN */
-- CALL nuevoIdMovimiento(	varIdRancho,	varConceptoTrasSalida,	@varIdMovimiento);
/*
	UPDATE	concepto_movimiento
	set		id_consecutivo	=	ifnull(id_consecutivo,0)	+	1
	where	id_rancho		=	varIdRancho
	and 	id_concepto		=	varConceptoTrasSalida;

	select	id_consecutivo
	INTO	varIdMovimiento
	from	concepto_movimiento
	where	id_rancho	=	varIdRancho
	and 	id_concepto	=	varConceptoTrasSalida;	
*/
	SELECT	UUID()
	INTO	varIdMovimiento;
	
	CALL insertarMovimiento(varIdRancho,		varIdMovimiento,	varConceptoTrasSalida,	
							varFecha,			varIdRancho,		varIdCorralOrigen,	
                            varIdRanchoDestino,	varIdCorralDestino,	NULL,
							NULL,				NULL,				NULL,	
                            NULL,				NULL,				NULL,				
                            NULL,				NULL);				
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nuevoIdConcepto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `nuevoIdConcepto`(varIdRancho int, varIdConcepto int, out varIdConsecutivo int )
BEGIN
	
	UPDATE	concepto_movimiento
	set		id_concecutivo	=	ifnull(id_concecutivo,0)	+	1
	where	id_rancho		=	varIdRancho;

	select	id_concecutivo
	INTO	varIdConsecutivo
	from	concepto_movimiento
	where	id_rancho	=	varIdRancho;	

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nuevoIdDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `nuevoIdDetalle`(
	varIdRancho		CHAR(36),		varIdConcepto	CHAR(36),	
	varIdMovimiento	CHAR(36),	OUT	varIdDetalle	INT)
BEGIN

	SELECT	COUNT(*) + 1
	INTO	varIdDetalle
	FROM	detalle_movimiento
	WHERE	id_rancho		=	varIdRancho
	AND		id_concepto		=	varIdConcepto
	AND		id_movimiento	=	varIdMovimiento;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nuevoIdMovimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `nuevoIdMovimiento`(
		varIdRancho		int,	varIdConcepto	int,
	out	varIdMovimiento	int )
BEGIN
	
	UPDATE	concepto_movimiento
	set		id_consecutivo	=	ifnull(id_consecutivo,0)	+	1
	where	id_rancho		=	varIdRancho
	and 	id_concepto		=	varIdConcepto;

	select	id_consecutivo
	INTO	varIdMovimiento
	from	concepto_movimiento
	where	id_rancho	=	varIdRancho
	and 	id_concepto	=	varIdConcepto;	

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reactivarCorral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reactivarCorral`(varIdCorral CHAR(36), varIdRancho CHAR(36))
BEGIN
UPDATE animal a 
SET 
    status = 'A'
WHERE
    (SELECT 
            ca.id_animal
        FROM
            corral_animal ca
        WHERE
            ca.id_corral = varIdCorral
                AND a.id_animal = ca.id_animal
                AND a.status = 'C') = a.id_animal;

UPDATE corral c 
SET 
    status = 'S'
WHERE
    c.id_corral = varIdCorral
        AND c.id_rancho = varIdRancho;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reinicia_bd` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reinicia_bd`()
BEGIN
/*	DECLARE varIdRancho			,
			varIdCorralAnimal	CHAR(36);
	*/
    TRUNCATE TABLE animal;
    TRUNCATE TABLE animal_grupo;
    TRUNCATE TABLE cliente;
    TRUNCATE TABLE compra;
    TRUNCATE TABLE corral;
    TRUNCATE TABLE corral_animal;
    TRUNCATE TABLE corral_datos;
    TRUNCATE TABLE compra;
    TRUNCATE TABLE concepto_movimiento;
    TRUNCATE TABLE cria;
    TRUNCATE TABLE detalle_compra;
    TRUNCATE TABLE detalle_movimiento;
    TRUNCATE TABLE genealogia;
    TRUNCATE TABLE ingreso_alimento;
    TRUNCATE TABLE medicina;
    TRUNCATE TABLE medicina_animal;
    TRUNCATE TABLE medicina_tratamiento;
    TRUNCATE TABLE movimiento;
    TRUNCATE TABLE movimiento_animal; 
    TRUNCATE TABLE proveedor;
    TRUNCATE TABLE recepcion;
    TRUNCATE TABLE rancho;
    TRUNCATE TABLE rancho_medicina;
    TRUNCATE TABLE registro_empadre;
    TRUNCATE TABLE status_gestacion;
    TRUNCATE TABLE tratamiento;
    TRUNCATE TABLE usuario;
                
    TRUNCATE TABLE repl_animal;
    TRUNCATE TABLE repl_cliente;
    TRUNCATE TABLE repl_compra;
    TRUNCATE TABLE repl_concepto_movimiento;
    TRUNCATE TABLE repl_control_gestacion;
    TRUNCATE TABLE repl_corral;    
    TRUNCATE TABLE repl_corral_animal;
    TRUNCATE TABLE repl_corral_datos;
    TRUNCATE TABLE repl_cria;
    TRUNCATE TABLE repl_detalle_compra;
    TRUNCATE TABLE repl_detalle_movimiento;
    TRUNCATE TABLE repl_genealogia;
    TRUNCATE TABLE repl_ingreso_alimento;
    TRUNCATE TABLE repl_medicina;
    TRUNCATE TABLE repl_medicina_animal;
    TRUNCATE TABLE repl_medicina_tratamiento;
    TRUNCATE TABLE repl_movimiento;
    TRUNCATE TABLE repl_movimiento_animal;
    TRUNCATE TABLE repl_proveedor;
    TRUNCATE TABLE repl_rancho;
    TRUNCATE TABLE repl_rancho_medicina;
    TRUNCATE TABLE repl_raza;
    TRUNCATE TABLE repl_recepcion;
    TRUNCATE TABLE repl_registro_empadre;
    TRUNCATE TABLE repl_status_gestacion;
    TRUNCATE TABLE repl_tratamiento;
    TRUNCATE TABLE repl_usuario;
    
    INSERT INTO `usuario` (`id_usuario`, `log`, `password`) VALUES ('1', 'admin', 'admin');

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateStatusReplica` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateStatusReplica`(
	varStatusNuevo char(2),	varStatusAnterior	char(2))
BEGIN
	
	UPDATE	repl_animal
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
	
	UPDATE	repl_concepto_movimiento
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_corral
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_corral_animal
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
	
	UPDATE	repl_corral_datos
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_cria
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
		
	UPDATE	repl_detalle_movimiento
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_medicina
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
	
	UPDATE	repl_medicina_animal
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
	
	UPDATE	repl_movimiento
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;	
	
	UPDATE	repl_proveedor
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_raza
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;
    
    UPDATE	repl_cliente
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

	UPDATE	repl_rancho
	SET		status = varStatusNuevo
	WHERE	status = varStatusAnterior;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-27 22:02:58
