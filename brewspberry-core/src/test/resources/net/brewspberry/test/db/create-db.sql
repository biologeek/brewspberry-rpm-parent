-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 29, 2016 at 06:58 PM
-- Server version: 5.7.12-0ubuntu1
-- PHP Version: 7.0.4-7ubuntu2.1

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ;

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;

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
) ;
