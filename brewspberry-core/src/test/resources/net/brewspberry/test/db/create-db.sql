-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 29, 2016 at 06:58 PM
-- Server version: 5.7.12-0ubuntu1
-- PHP Version: 7.0.4-7ubuntu2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `brewspberry`
--

-- --------------------------------------------------------

--
-- Table structure for table `Actioner`
--

CREATE TABLE `Actioner` (
  `act_id` bigint(20) NOT NULL,
  `act_activated` bit(1) NOT NULL,
  `act_date_debut` datetime DEFAULT NULL,
  `act_date_fin` datetime DEFAULT NULL,
  `act_nom` varchar(255) DEFAULT NULL,
  `act_raspi_pin` varchar(255) DEFAULT NULL,
  `act_status` int(11) NOT NULL,
  `act_type` varchar(255) DEFAULT NULL,
  `act_used` bit(1) NOT NULL,
  `act_uuid` varchar(255) DEFAULT NULL,
  `act_bra_id` bigint(20) DEFAULT NULL,
  `act_etp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Biere`
--

CREATE TABLE `Biere` (
  `beer_id` bigint(20) NOT NULL,
  `beer_alcohol` float NOT NULL,
  `beer_aroma` varchar(255) DEFAULT NULL,
  `beer_bubbles` int(11) NOT NULL,
  `beer_color_ebc` int(11) NOT NULL,
  `beer_comment` varchar(255) DEFAULT NULL,
  `beer_conso_progress` double NOT NULL,
  `beer_density` int(11) NOT NULL,
  `beer_first_drink_date` datetime DEFAULT NULL,
  `beer_init_bottles` int(11) NOT NULL,
  `beer_name` varchar(255) DEFAULT NULL,
  `beer_notation` int(11) NOT NULL,
  `beer_quantity` double NOT NULL,
  `beer_remaining_bottles` int(11) NOT NULL,
  `beer_style` varchar(255) DEFAULT NULL,
  `bra_beer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Brassin`
--

CREATE TABLE `Brassin` (
  `bra_id` bigint(20) NOT NULL,
  `bra_date_maj` datetime DEFAULT NULL,
  `bra_debut` datetime DEFAULT NULL,
  `bra_fin` datetime DEFAULT NULL,
  `bra_nom` varchar(255) DEFAULT NULL,
  `bra_quantiteEnLitres` double DEFAULT NULL,
  `bra_statut` int(11) DEFAULT NULL,
  `bra_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ConcreteTemperatureMeasurement`
--

CREATE TABLE `ConcreteTemperatureMeasurement` (
  `tmes_id` bigint(20) NOT NULL,
  `tmes_date` datetime DEFAULT NULL,
  `tmes_probeUI` varchar(255) DEFAULT NULL,
  `tmes_probe_name` varchar(255) DEFAULT NULL,
  `tmes_value` float DEFAULT NULL,
  `tmes_act_id` bigint(20) DEFAULT NULL,
  `tmes_bra_id` bigint(20) DEFAULT NULL,
  `tmes_etape_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DurationBO`
--

CREATE TABLE `DurationBO` (
  `dur_id` bigint(20) NOT NULL,
  `day` bigint(20) NOT NULL,
  `hour` bigint(20) NOT NULL,
  `milisecond` bigint(20) NOT NULL,
  `minute` bigint(20) NOT NULL,
  `second` bigint(20) NOT NULL,
  `week` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Etape`
--

CREATE TABLE `Etape` (
  `etp_id` bigint(20) NOT NULL,
  `etp_debut` datetime DEFAULT NULL,
  `etp_fin` datetime DEFAULT NULL,
  `etp_nom` varchar(255) DEFAULT NULL,
  `etp_numero` int(11) DEFAULT NULL,
  `etp_remarque` varchar(255) DEFAULT NULL,
  `etp_temperature_theorique` double DEFAULT NULL,
  `etp_bra_id` bigint(20) DEFAULT NULL,
  `etp_plt_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Houblon`
--

CREATE TABLE `Houblon` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `shbl_acide_alpha` double DEFAULT NULL,
  `shbl_aromes` varchar(255) DEFAULT NULL,
  `shbl_type` int(11) DEFAULT NULL,
  `shbl_variete` varchar(255) DEFAULT NULL,
  `ing_prix` float NOT NULL,
  `ing_quantite` float NOT NULL,
  `hbl_bra_id` bigint(20) DEFAULT NULL,
  `hbl_etp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Levure`
--

CREATE TABLE `Levure` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `slev_aromes` varchar(255) DEFAULT NULL,
  `slev_espece` varchar(255) DEFAULT NULL,
  `slev_floculation` varchar(255) DEFAULT NULL,
  `ing_prix` float NOT NULL,
  `ing_quantite` float NOT NULL,
  `lev_bra_id` bigint(20) DEFAULT NULL,
  `lev_etp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Malt`
--

CREATE TABLE `Malt` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `smal_cereale` varchar(255) DEFAULT NULL,
  `smal_couleur` int(11) DEFAULT NULL,
  `smal_type` varchar(255) DEFAULT NULL,
  `ing_prix` float NOT NULL,
  `ing_quantite` float NOT NULL,
  `malt_bra_id` bigint(20) DEFAULT NULL,
  `malt_etp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PalierType`
--

CREATE TABLE `PalierType` (
  `plt_id` int(11) NOT NULL,
  `plt_libelle` varchar(255) DEFAULT NULL,
  `plt_temperature` int(11) NOT NULL,
  `plt_temperature_max` int(11) NOT NULL,
  `plt_temperature_min` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `SimpleHoublon`
--

CREATE TABLE `SimpleHoublon` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `shbl_acide_alpha` double DEFAULT NULL,
  `shbl_aromes` varchar(255) DEFAULT NULL,
  `shbl_type` int(11) DEFAULT NULL,
  `shbl_variete` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `SimpleLevure`
--

CREATE TABLE `SimpleLevure` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `slev_aromes` varchar(255) DEFAULT NULL,
  `slev_espece` varchar(255) DEFAULT NULL,
  `slev_floculation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `SimpleMalt`
--

CREATE TABLE `SimpleMalt` (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `smal_cereale` varchar(255) DEFAULT NULL,
  `smal_couleur` int(11) DEFAULT NULL,
  `smal_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `us_id` int(11) NOT NULL,
  `us_age` int(11) NOT NULL,
  `us_active` bit(1) NOT NULL,
  `us_birthday` datetime DEFAULT NULL,
  `us_date_activation` datetime DEFAULT NULL,
  `us_date_inactivation` datetime DEFAULT NULL,
  `us_first_connection` datetime DEFAULT NULL,
  `us_force_inactivated` bit(1) NOT NULL,
  `us_last_connection` datetime DEFAULT NULL,
  `us_login` varchar(255) DEFAULT NULL,
  `us_nom` varchar(255) DEFAULT NULL,
  `us_password` varchar(255) DEFAULT NULL,
  `us_prenom` varchar(255) DEFAULT NULL,
  `us_profile` int(11) DEFAULT NULL,
  `us_session_token` varchar(255) DEFAULT NULL,
  `us_registration` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Actioner`
--
ALTER TABLE `Actioner`
  ADD PRIMARY KEY (`act_id`),
  ADD KEY `FK_77smbd47arfgjr7uobswigrxw` (`act_bra_id`),
  ADD KEY `FK_4qs5namanm8b2kx5smkqyatw2` (`act_etp_id`);

--
-- Indexes for table `Biere`
--
ALTER TABLE `Biere`
  ADD PRIMARY KEY (`beer_id`),
  ADD KEY `FK_q123a3u611eaqt9m6f2awngex` (`bra_beer_id`);

--
-- Indexes for table `Brassin`
--
ALTER TABLE `Brassin`
  ADD PRIMARY KEY (`bra_id`);

--
-- Indexes for table `ConcreteTemperatureMeasurement`
--
ALTER TABLE `ConcreteTemperatureMeasurement`
  ADD PRIMARY KEY (`tmes_id`),
  ADD KEY `FK_m22b2qtg2of89o7079km8941o` (`tmes_act_id`),
  ADD KEY `FK_lct5lkwpfty0q0giuxpw1opgq` (`tmes_bra_id`),
  ADD KEY `FK_hhltrdi7y32tyc8urtl42ktx3` (`tmes_etape_id`);

--
-- Indexes for table `DurationBO`
--
ALTER TABLE `DurationBO`
  ADD PRIMARY KEY (`dur_id`);

--
-- Indexes for table `Etape`
--
ALTER TABLE `Etape`
  ADD PRIMARY KEY (`etp_id`),
  ADD KEY `FK_es8f101ew1x8noh6inn9qisn3` (`etp_bra_id`),
  ADD KEY `FK_afhugf25w378de6ap990skxaf` (`etp_plt_id`);

--
-- Indexes for table `Houblon`
--
ALTER TABLE `Houblon`
  ADD PRIMARY KEY (`ing_id`),
  ADD KEY `FK_rfmaghhnq5yutuir5o3dpjpl8` (`hbl_bra_id`),
  ADD KEY `FK_kii2v65q2cl96xg3s6ipud0x` (`hbl_etp_id`);

--
-- Indexes for table `Levure`
--
ALTER TABLE `Levure`
  ADD PRIMARY KEY (`ing_id`),
  ADD KEY `FK_d7sna1i34x0374hjlf0pfxswo` (`lev_bra_id`),
  ADD KEY `FK_6udqfhwgmtjitt4g62vnfwjrs` (`lev_etp_id`);

--
-- Indexes for table `Malt`
--
ALTER TABLE `Malt`
  ADD PRIMARY KEY (`ing_id`),
  ADD KEY `FK_my3y7wtygu3e6lmhj705f9w8t` (`malt_bra_id`),
  ADD KEY `FK_dld6f6xpnsqthbweo0i5dsr22` (`malt_etp_id`);

--
-- Indexes for table `PalierType`
--
ALTER TABLE `PalierType`
  ADD PRIMARY KEY (`plt_id`);

--
-- Indexes for table `SimpleHoublon`
--
ALTER TABLE `SimpleHoublon`
  ADD PRIMARY KEY (`ing_id`);

--
-- Indexes for table `SimpleLevure`
--
ALTER TABLE `SimpleLevure`
  ADD PRIMARY KEY (`ing_id`);

--
-- Indexes for table `SimpleMalt`
--
ALTER TABLE `SimpleMalt`
  ADD PRIMARY KEY (`ing_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`us_id`,`us_age`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Actioner`
--
ALTER TABLE `Actioner`
  MODIFY `act_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Biere`
--
ALTER TABLE `Biere`
  MODIFY `beer_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Brassin`
--
ALTER TABLE `Brassin`
  MODIFY `bra_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ConcreteTemperatureMeasurement`
--
ALTER TABLE `ConcreteTemperatureMeasurement`
  MODIFY `tmes_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `DurationBO`
--
ALTER TABLE `DurationBO`
  MODIFY `dur_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Etape`
--
ALTER TABLE `Etape`
  MODIFY `etp_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PalierType`
--
ALTER TABLE `PalierType`
  MODIFY `plt_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `us_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Actioner`
--
ALTER TABLE `Actioner`
  ADD CONSTRAINT `FK_4qs5namanm8b2kx5smkqyatw2` FOREIGN KEY (`act_etp_id`) REFERENCES `Etape` (`etp_id`),
  ADD CONSTRAINT `FK_77smbd47arfgjr7uobswigrxw` FOREIGN KEY (`act_bra_id`) REFERENCES `Brassin` (`bra_id`);

--
-- Constraints for table `Biere`
--
ALTER TABLE `Biere`
  ADD CONSTRAINT `FK_q123a3u611eaqt9m6f2awngex` FOREIGN KEY (`bra_beer_id`) REFERENCES `Brassin` (`bra_id`);

--
-- Constraints for table `ConcreteTemperatureMeasurement`
--
ALTER TABLE `ConcreteTemperatureMeasurement`
  ADD CONSTRAINT `FK_hhltrdi7y32tyc8urtl42ktx3` FOREIGN KEY (`tmes_etape_id`) REFERENCES `Etape` (`etp_id`),
  ADD CONSTRAINT `FK_lct5lkwpfty0q0giuxpw1opgq` FOREIGN KEY (`tmes_bra_id`) REFERENCES `Brassin` (`bra_id`),
  ADD CONSTRAINT `FK_m22b2qtg2of89o7079km8941o` FOREIGN KEY (`tmes_act_id`) REFERENCES `Actioner` (`act_id`);

--
-- Constraints for table `Etape`
--
ALTER TABLE `Etape`
  ADD CONSTRAINT `FK_afhugf25w378de6ap990skxaf` FOREIGN KEY (`etp_plt_id`) REFERENCES `PalierType` (`plt_id`),
  ADD CONSTRAINT `FK_es8f101ew1x8noh6inn9qisn3` FOREIGN KEY (`etp_bra_id`) REFERENCES `Brassin` (`bra_id`);

--
-- Constraints for table `Houblon`
--
ALTER TABLE `Houblon`
  ADD CONSTRAINT `FK_kii2v65q2cl96xg3s6ipud0x` FOREIGN KEY (`hbl_etp_id`) REFERENCES `Etape` (`etp_id`),
  ADD CONSTRAINT `FK_rfmaghhnq5yutuir5o3dpjpl8` FOREIGN KEY (`hbl_bra_id`) REFERENCES `Brassin` (`bra_id`);

--
-- Constraints for table `Levure`
--
ALTER TABLE `Levure`
  ADD CONSTRAINT `FK_6udqfhwgmtjitt4g62vnfwjrs` FOREIGN KEY (`lev_etp_id`) REFERENCES `Etape` (`etp_id`),
  ADD CONSTRAINT `FK_d7sna1i34x0374hjlf0pfxswo` FOREIGN KEY (`lev_bra_id`) REFERENCES `Brassin` (`bra_id`);

--
-- Constraints for table `Malt`
--
ALTER TABLE `Malt`
  ADD CONSTRAINT `FK_dld6f6xpnsqthbweo0i5dsr22` FOREIGN KEY (`malt_etp_id`) REFERENCES `Brassin` (`bra_id`),
  ADD CONSTRAINT `FK_my3y7wtygu3e6lmhj705f9w8t` FOREIGN KEY (`malt_bra_id`) REFERENCES `Brassin` (`bra_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;