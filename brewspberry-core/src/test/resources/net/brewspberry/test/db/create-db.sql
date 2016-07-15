
CREATE TABLE IF NOT EXISTS "users" (
  `us_id` int(11) NOT NULL AUTO_INCREMENT,
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
  `us_registration` datetime DEFAULT NULL,
  PRIMARY KEY (`us_id`,`us_age`)
) ;





CREATE TABLE "SimpleMalt" (
  `ing_id` bigint(20) NOT NULL,
  `ing_desc` varchar(255) DEFAULT NULL,
  `ing_fournisseur` varchar(255) DEFAULT NULL,
  `smal_cereale` varchar(255) DEFAULT NULL,
  `smal_couleur` int(11) DEFAULT NULL,
  `smal_type` varchar(255) DEFAULT NULL
);



ALTER TABLE `SimpleMalt`
  ADD PRIMARY KEY (`ing_id`);
