-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 01, 2015 at 07:35 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `eimenik`
--

-- --------------------------------------------------------

--
-- Table structure for table `komentar`
--

CREATE TABLE IF NOT EXISTS `komentar` (
  `Id_komentara` int(11) NOT NULL AUTO_INCREMENT,
  `Id_nastavnika` int(11) NOT NULL,
  `Id_ucenika` int(11) NOT NULL,
  `Redni_br_upisa` int(11) NOT NULL,
  `Datum` datetime DEFAULT NULL,
  `Komentar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_komentara`),
  KEY `Id_ucenika` (`Id_ucenika`),
  KEY `Upisani_predmetiKomentar` (`Redni_br_upisa`),
  KEY `Id_nastavnika` (`Id_nastavnika`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `komentar`
--

INSERT INTO `komentar` (`Id_komentara`, `Id_nastavnika`, `Id_ucenika`, `Redni_br_upisa`, `Datum`, `Komentar`) VALUES
(1, 1, 7, 3, '2014-09-16 12:17:43', 'Učenik nemiran na satu.'),
(2, 1, 7, 2, '2015-01-21 00:00:00', 'Učenik se zabavlja na tabletu.');

-- --------------------------------------------------------

--
-- Table structure for table `nastavnici`
--

CREATE TABLE IF NOT EXISTS `nastavnici` (
  `Id_nastavnika` int(11) NOT NULL AUTO_INCREMENT,
  `Oib_nastavnika` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `Ime` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `Prezime` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_nastavnika`),
  UNIQUE KEY `Oib_nastavnika` (`Oib_nastavnika`),
  KEY `Id_nastavnika` (`Id_nastavnika`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `nastavnici`
--

INSERT INTO `nastavnici` (`Id_nastavnika`, `Oib_nastavnika`, `Ime`, `Prezime`) VALUES
(1, '20635678364', 'Ivana', 'Olić'),
(2, '56125678364', 'Kristina', 'Šuker');

-- --------------------------------------------------------

--
-- Table structure for table `ocjene`
--

CREATE TABLE IF NOT EXISTS `ocjene` (
  `Redni_broj_ocjene` int(11) NOT NULL AUTO_INCREMENT,
  `Id_ucenika` int(11) DEFAULT NULL,
  `Id_rubrike` int(11) DEFAULT NULL,
  `Redni_br_upisa` int(11) DEFAULT NULL,
  `Ocjena` smallint(6) DEFAULT NULL,
  `Datum_ocjene` datetime DEFAULT NULL,
  `Komentar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Redni_broj_ocjene`),
  KEY `Id_rubrike` (`Id_rubrike`),
  KEY `Id_ucenik` (`Id_ucenika`),
  KEY `Redni_br_upisa` (`Redni_br_upisa`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Dumping data for table `ocjene`
--

INSERT INTO `ocjene` (`Redni_broj_ocjene`, `Id_ucenika`, `Id_rubrike`, `Redni_br_upisa`, `Ocjena`, `Datum_ocjene`, `Komentar`) VALUES
(1, 7, 1, 1, 4, '2014-10-07 10:33:40', 'Učenik vlada naučenim znanjem'),
(2, 7, 1, 1, 1, '2014-11-10 08:38:18', 'Učenik nije spreman.'),
(4, 7, 3, 1, 3, '2015-03-04 09:40:33', 'Učenik razumije osnovne elemente lektire.'),
(5, 7, 21, 2, 4, '2015-01-05 12:20:22', 'Riješeno 4 od 5 zadataka.'),
(6, 7, 25, 6, 5, '2015-02-10 10:17:38', 'Kolut naprijed, nazad.'),
(7, 7, 29, 7, 4, '2014-11-11 10:40:04', 'Učenik vrlo dobro čita.'),
(8, 7, 5, 4, 2, '2015-07-22 00:00:00', 'sada'),
(9, 7, 7, 5, 5, '2015-07-29 05:41:00', 'bbbbbbbb');

-- --------------------------------------------------------

--
-- Table structure for table `predmeti`
--

CREATE TABLE IF NOT EXISTS `predmeti` (
  `Id_predmeta` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv_predmeta` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_predmeta`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `predmeti`
--

INSERT INTO `predmeti` (`Id_predmeta`, `Naziv_predmeta`) VALUES
(1, 'Hrvatski jezik'),
(2, 'Matematika'),
(3, 'Priroda i društvo'),
(4, 'Likovna kultura'),
(5, 'Glazbena kultura'),
(6, 'Tjelesna i zdravstvena kultura'),
(7, 'Engleski jezik');

-- --------------------------------------------------------

--
-- Table structure for table `razredi`
--

CREATE TABLE IF NOT EXISTS `razredi` (
  `Id_razreda` int(11) NOT NULL AUTO_INCREMENT,
  `Godina` smallint(6) DEFAULT NULL,
  `Razred` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `Godina_upisa` year(4) DEFAULT NULL,
  PRIMARY KEY (`Id_razreda`),
  KEY `Id_razred` (`Razred`),
  KEY `Id_razred1` (`Id_razreda`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `razredi`
--

INSERT INTO `razredi` (`Id_razreda`, `Godina`, `Razred`, `Godina_upisa`) VALUES
(1, 1, 'A', 2014),
(2, 1, 'B', 2014);

-- --------------------------------------------------------

--
-- Table structure for table `raz_pred_nast`
--

CREATE TABLE IF NOT EXISTS `raz_pred_nast` (
  `Id_razreda` int(11) NOT NULL,
  `Id_nastavnika` int(11) NOT NULL,
  `Redni_br_upisa` int(11) DEFAULT NULL,
  `Datum_od` date DEFAULT NULL,
  `Datum_do` date DEFAULT NULL,
  KEY `Redni_br_upisa` (`Redni_br_upisa`),
  KEY `Id_razreda` (`Id_razreda`),
  KEY `Id_nastavnika` (`Id_nastavnika`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `raz_pred_nast`
--

INSERT INTO `raz_pred_nast` (`Id_razreda`, `Id_nastavnika`, `Redni_br_upisa`, `Datum_od`, `Datum_do`) VALUES
(1, 1, 1, '2014-09-01', '2015-06-17'),
(1, 1, 2, '2014-09-01', '2015-06-17'),
(1, 1, 3, '2014-09-01', '2015-06-17'),
(1, 1, 4, '2014-09-01', '2015-06-17'),
(1, 1, 5, '2014-09-01', '2015-06-17'),
(1, 1, 6, '2014-09-01', '2015-06-17');

-- --------------------------------------------------------

--
-- Table structure for table `rubrike`
--

CREATE TABLE IF NOT EXISTS `rubrike` (
  `Id_rubrike` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv_rubrike` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_rubrike`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=32 ;

--
-- Dumping data for table `rubrike`
--

INSERT INTO `rubrike` (`Id_rubrike`, `Naziv_rubrike`) VALUES
(1, 'Hrvatski jezik'),
(2, 'Književnost'),
(3, 'Lektira'),
(4, 'Jezično izražavanje i stvaranje - usmeno'),
(5, 'Jezično izražavanje i stvaranje - pisano'),
(6, 'Medijska kultura'),
(7, 'Domaći uradak'),
(8, 'Zalaganje'),
(9, 'Crtanje'),
(10, 'Slikanje'),
(11, 'Oblikovanje'),
(12, 'Pjevanje'),
(13, 'Sviranje'),
(14, 'Slušanje'),
(15, 'Razumijevanje - slušanje'),
(16, 'Razumijevanje - čitanje'),
(17, 'Istraživanje i stvaranje - usmeno'),
(18, 'Istraživanje i stvaranje - pismeno'),
(21, 'Usvojenost, razumijevanje i primjena programskih sadržaja - usmeno'),
(22, 'Usvojenost, razumijevanje i primjena programskih sadržaja - pismeno'),
(23, 'Usvojenost, razumijevanje i primjena programskih sadržaja - domaći uradak'),
(24, 'Praktičan rad'),
(25, 'Motorička znanja'),
(26, 'Motorička dostignuća'),
(27, 'Funkcionalne sposobnosti'),
(28, 'Razumijevanje - slušanje'),
(29, 'Razumijevanje - čitanje'),
(30, 'Istraživanje i stvaranje - usmeno'),
(31, 'Istraživanje i stvaranje - pisano');

-- --------------------------------------------------------

--
-- Table structure for table `ucenici`
--

CREATE TABLE IF NOT EXISTS `ucenici` (
  `Id_ucenika` int(11) NOT NULL AUTO_INCREMENT,
  `Oib_ucenika` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `Ime` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `Prezime` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `Ime_oca` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `Datum_rodjenja` date DEFAULT NULL,
  `Adresa_stanovanja` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Grad_stanovanja` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Skola` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Adresa_skole` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Grad_skole` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Korisnicko_ime` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `Lozinka` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `Salt` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_ucenika`),
  UNIQUE KEY `Oib_ucenika` (`Oib_ucenika`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Dumping data for table `ucenici`
--

INSERT INTO `ucenici` (`Id_ucenika`, `Oib_ucenika`, `Ime`, `Prezime`, `Ime_oca`, `Datum_rodjenja`, `Adresa_stanovanja`, `Grad_stanovanja`, `Skola`, `Adresa_skole`, `Grad_skole`, `Korisnicko_ime`, `Lozinka`, `Salt`) VALUES
(7, '19734319463', 'Luka', 'Perišić', 'Ante', '2007-03-21', 'Idrijska 25', 'Zagreb', 'Dr. Ante Starčevića', 'Ulica Svetog Leopolda Mandića 55', 'Zagreb', 'lperisic', '45ly127JTgbSFpw+/P5gLPw9mh02MGY1ZDg2ZDM0', '60f5d86d34'),
(8, '2373431947', 'Mario', 'Srna', 'Ivan', '2007-09-01', 'Gojlanska 20', 'Zagreb', 'Dr. Ante Starčevića ', 'Ulica Svetog Leopolda Mandića 55', 'Zagreb', 'msrna', 'SlkbS0GYEalBPThVSM8u88HvTSU3ZjViNmZlY2Vk', '7f5b6feced');

-- --------------------------------------------------------

--
-- Table structure for table `upis`
--

CREATE TABLE IF NOT EXISTS `upis` (
  `Id_upisa` int(11) NOT NULL AUTO_INCREMENT,
  `Id_ucenika` int(11) DEFAULT NULL,
  `Id_razreda` int(11) DEFAULT NULL,
  `Datum_upisa` date DEFAULT NULL,
  PRIMARY KEY (`Id_upisa`),
  KEY `Id_razred` (`Id_razreda`),
  KEY `Id_ucenik` (`Id_ucenika`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `upis`
--

INSERT INTO `upis` (`Id_upisa`, `Id_ucenika`, `Id_razreda`, `Datum_upisa`) VALUES
(1, 7, 1, '2014-09-01'),
(2, 8, 2, '2014-09-01');

-- --------------------------------------------------------

--
-- Table structure for table `upisani_predmeti`
--

CREATE TABLE IF NOT EXISTS `upisani_predmeti` (
  `Redni_br_upisa` int(11) NOT NULL AUTO_INCREMENT,
  `Id_upisa` int(11) DEFAULT NULL,
  `Id_predmeta` int(11) DEFAULT NULL,
  `Datum_upisa` date DEFAULT NULL,
  `Zavrsna_ocjena_predmeta` smallint(6) DEFAULT NULL,
  `Datum_zavrsne_ocjene` datetime DEFAULT NULL,
  PRIMARY KEY (`Redni_br_upisa`),
  KEY `Id_predmeta` (`Id_predmeta`),
  KEY `Id_upis` (`Id_upisa`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `upisani_predmeti`
--

INSERT INTO `upisani_predmeti` (`Redni_br_upisa`, `Id_upisa`, `Id_predmeta`, `Datum_upisa`, `Zavrsna_ocjena_predmeta`, `Datum_zavrsne_ocjene`) VALUES
(1, 1, 1, '2014-09-01', 4, '2015-06-17 10:00:00'),
(2, 1, 2, '2014-09-01', NULL, NULL),
(3, 1, 3, '2014-09-01', NULL, NULL),
(4, 1, 4, '2014-09-01', NULL, NULL),
(5, 1, 5, '2014-09-01', NULL, NULL),
(6, 1, 6, '2014-09-01', NULL, NULL),
(7, 1, 7, '2014-09-01', NULL, NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `komentar`
--
ALTER TABLE `komentar`
  ADD CONSTRAINT `NastavniciKomentar` FOREIGN KEY (`Id_nastavnika`) REFERENCES `nastavnici` (`Id_nastavnika`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `UceniciKomentar` FOREIGN KEY (`Id_ucenika`) REFERENCES `ucenici` (`Id_ucenika`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Upisani_predmetiKomentar` FOREIGN KEY (`Redni_br_upisa`) REFERENCES `upisani_predmeti` (`Redni_br_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ocjene`
--
ALTER TABLE `ocjene`
  ADD CONSTRAINT `RubrikeOcjene` FOREIGN KEY (`Id_rubrike`) REFERENCES `rubrike` (`Id_rubrike`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `UceniciOcjene` FOREIGN KEY (`Id_ucenika`) REFERENCES `ucenici` (`Id_ucenika`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Upisani_predmetiOcjene` FOREIGN KEY (`Redni_br_upisa`) REFERENCES `upisani_predmeti` (`Redni_br_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `raz_pred_nast`
--
ALTER TABLE `raz_pred_nast`
  ADD CONSTRAINT `NastavniciRaz_pred_nast` FOREIGN KEY (`Id_nastavnika`) REFERENCES `nastavnici` (`Id_nastavnika`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `RazrediRaz_pred_nast` FOREIGN KEY (`Id_razreda`) REFERENCES `razredi` (`Id_razreda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Upisani_predmetiRaz_pred_nast` FOREIGN KEY (`Redni_br_upisa`) REFERENCES `upisani_predmeti` (`Redni_br_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `upis`
--
ALTER TABLE `upis`
  ADD CONSTRAINT `RazrediUpis` FOREIGN KEY (`Id_razreda`) REFERENCES `razredi` (`Id_razreda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `UceniciUpis` FOREIGN KEY (`Id_ucenika`) REFERENCES `ucenici` (`Id_ucenika`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `upisani_predmeti`
--
ALTER TABLE `upisani_predmeti`
  ADD CONSTRAINT `PredmetiUpisani_predmeti` FOREIGN KEY (`Id_predmeta`) REFERENCES `predmeti` (`Id_predmeta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `UpisUpisani_predmeti` FOREIGN KEY (`Id_upisa`) REFERENCES `upis` (`Id_upisa`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
