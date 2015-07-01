-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 02, 2015 at 12:10 AM
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
  `Id_nastavnika` int(11) NOT NULL,
  `Id_ucenika` int(11) NOT NULL,
  `Redni_br_upisa` int(11) NOT NULL,
  `Datum` datetime DEFAULT NULL,
  `Komentar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_nastavnika`,`Id_ucenika`,`Redni_br_upisa`),
  KEY `Id_ucenika` (`Id_ucenika`),
  KEY `Upisani_predmetiKomentar` (`Redni_br_upisa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `nastavnici`
--

INSERT INTO `nastavnici` (`Id_nastavnika`, `Oib_nastavnika`, `Ime`, `Prezime`) VALUES
(1, '20635678364', 'Ivo', 'Srna');

-- --------------------------------------------------------

--
-- Table structure for table `ocjene`
--

CREATE TABLE IF NOT EXISTS `ocjene` (
  `Redni_broj_ocjene` int(11) NOT NULL AUTO_INCREMENT,
  `Id_ucenika` int(11) DEFAULT NULL,
  `Id_rubrike` int(11) DEFAULT NULL,
  `Id_predmeta` int(11) DEFAULT NULL,
  `Ocjena` smallint(6) DEFAULT NULL,
  `Datum_ocjene` datetime DEFAULT NULL,
  `Komentar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Redni_broj_ocjene`),
  KEY `Id_predmeta` (`Id_predmeta`),
  KEY `Id_rubrike` (`Id_rubrike`),
  KEY `Id_ucenik` (`Id_ucenika`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `ocjene`
--

INSERT INTO `ocjene` (`Redni_broj_ocjene`, `Id_ucenika`, `Id_rubrike`, `Id_predmeta`, `Ocjena`, `Datum_ocjene`, `Komentar`) VALUES
(1, 7, 1, 1, 4, '2014-10-07 10:33:40', 'Učenik vlada naučenim znanjem'),
(2, 7, 1, 1, 1, '2014-11-10 08:38:18', 'Učenik nije spreman');

-- --------------------------------------------------------

--
-- Table structure for table `predmeti`
--

CREATE TABLE IF NOT EXISTS `predmeti` (
  `Id_predmeta` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv_predmeta` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_predmeta`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `predmeti`
--

INSERT INTO `predmeti` (`Id_predmeta`, `Naziv_predmeta`) VALUES
(1, 'Hrvatski jezik'),
(2, 'Matematika'),
(3, 'Priroda i društvo'),
(4, 'Likovna kultura'),
(5, 'Glazbena kultura'),
(6, 'Tjelesna i zdravstvena kultura');

-- --------------------------------------------------------

--
-- Table structure for table `raz_pred_nast`
--

CREATE TABLE IF NOT EXISTS `raz_pred_nast` (
  `Id_razreda` int(11) NOT NULL,
  `Id_nastavnika` int(11) NOT NULL,
  `Id_predmeta` int(11) DEFAULT NULL,
  `Datum_od` date DEFAULT NULL,
  `Datum_do` date DEFAULT NULL,
  PRIMARY KEY (`Id_razreda`,`Id_nastavnika`),
  KEY `Id_nastava` (`Id_nastavnika`),
  KEY `Id_predmet` (`Id_predmeta`),
  KEY `Raz_pred_nastId_razred` (`Id_razreda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `raz_pred_nast`
--

INSERT INTO `raz_pred_nast` (`Id_razreda`, `Id_nastavnika`, `Id_predmeta`, `Datum_od`, `Datum_do`) VALUES
(1, 1, 1, '2014-09-01', '2015-06-17');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `razredi`
--

INSERT INTO `razredi` (`Id_razreda`, `Godina`, `Razred`, `Godina_upisa`) VALUES
(1, 1, 'A', 2014);

-- --------------------------------------------------------

--
-- Table structure for table `rubrike`
--

CREATE TABLE IF NOT EXISTS `rubrike` (
  `Id_rubrike` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv_rubrike` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id_rubrike`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `rubrike`
--

INSERT INTO `rubrike` (`Id_rubrike`, `Naziv_rubrike`) VALUES
(1, 'hrvatski jezik');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=11 ;

--
-- Dumping data for table `ucenici`
--

INSERT INTO `ucenici` (`Id_ucenika`, `Oib_ucenika`, `Ime`, `Prezime`, `Ime_oca`, `Datum_rodjenja`, `Adresa_stanovanja`, `Grad_stanovanja`, `Skola`, `Adresa_skole`, `Grad_skole`, `Korisnicko_ime`, `Lozinka`, `Salt`) VALUES
(7, '19734319463', 'Luka', 'Perišić', 'Ante', '2007-03-21', 'Idrijska 25', 'Zagreb', 'Dr. Ante Starčevića', 'Ulica Svetog Leopolda Mandića 55', 'Zagreb', 'lperisic', 'vgoDUA7pVR3uM0fwwvv3K3Q3NJY0YTJlNmIwNTI2', '4a2e6b0526'),
(8, NULL, 'Mario', 'Srna', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'msrna', 'SlkbS0GYEalBPThVSM8u88HvTSU3ZjViNmZlY2Vk', '7f5b6feced'),
(9, NULL, 'Proba', 'Šššššs', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'proba', 'lNiLOIvbnM8KE3uZE9KcwK2UuzA4MWQwODRlMjNh', '81d084e23a'),
(10, NULL, 'Probni', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'probni', 'qlC9mrIA+MhBeOqJ0Wmj5pFEKLoyYjAwZjMwMjNj', '2b00f3023c');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `upis`
--

INSERT INTO `upis` (`Id_upisa`, `Id_ucenika`, `Id_razreda`, `Datum_upisa`) VALUES
(1, 7, 1, '2014-06-11');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `upisani_predmeti`
--

INSERT INTO `upisani_predmeti` (`Redni_br_upisa`, `Id_upisa`, `Id_predmeta`, `Datum_upisa`, `Zavrsna_ocjena_predmeta`, `Datum_zavrsne_ocjene`) VALUES
(1, 1, 1, '2014-06-11', NULL, NULL);

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
  ADD CONSTRAINT `Upisani_predmetiOcjene` FOREIGN KEY (`Id_predmeta`) REFERENCES `upisani_predmeti` (`Redni_br_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `raz_pred_nast`
--
ALTER TABLE `raz_pred_nast`
  ADD CONSTRAINT `NastavniciRaz_pred_nast` FOREIGN KEY (`Id_nastavnika`) REFERENCES `nastavnici` (`Id_nastavnika`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `RazrediRaz_pred_nast` FOREIGN KEY (`Id_razreda`) REFERENCES `razredi` (`Id_razreda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Upisani_predmetiRaz_pred_nast` FOREIGN KEY (`Id_predmeta`) REFERENCES `upisani_predmeti` (`Redni_br_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
  ADD CONSTRAINT `UpisUpisani_predmeti` FOREIGN KEY (`Id_upisa`) REFERENCES `upis` (`Id_upisa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
