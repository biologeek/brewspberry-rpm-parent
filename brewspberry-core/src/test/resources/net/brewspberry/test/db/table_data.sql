

INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (1, 'Stock dispo à la fabrication', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (2, 'Stock DLC dépassée', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (3, 'Stock réservé fabrication', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (4, 'Stock dispo vente', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (5, 'Stock réservé CC', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (6, 'Démarque casse', NOW());
INSERT INTO `CompteurType` (cty_id, cty_libelle, cty_date_cre) VALUES (7, 'Démarque qualité', NOW());

INSERT INTO `SimpleMalt` (stb_id, ing_desc, ing_fournisseur, ing_unitary_price, ing_unitary_price_unit, ing_unitary_weight, ing_unitary_weight_unit, smal_cereale, smal_type, smal_couleur)
VALUES (1, 'test', 'Weyermann', 5, 4, 1, 4, 'Orge', 'Blond', 3);

INSERT INTO `SimpleHoublon` (stb_id, ing_desc, ing_fournisseur, ing_unitary_price, ing_unitary_price_unit, ing_unitary_weight, ing_unitary_weight_unit, shbl_variete, shbl_acide_alpha, shbl_aromes, shbl_type)
VALUES (2, 'test2', 'Saveur Biere', 20, 4, 0.1, 4, 'East Test Goldings', 4.4, 'Vanilla, weed', 1);

INSERT INTO `RawMaterialCounter` (cpt_id, cpt_counter_type, cpt_product, cpt_value, cpt_unit, cpt_date_cre, cpt_date_maj) VALUES (1, 1, 1, 20, 4, NOW(), NOW());
INSERT INTO `RawMaterialCounter` (cpt_id, cpt_counter_type, cpt_product, cpt_value, cpt_unit, cpt_date_cre, cpt_date_maj) VALUES (2, 1, 2, 0.1, 4, NOW(), NOW());
INSERT INTO `RawMaterialCounter` (cpt_id, cpt_counter_type, cpt_product, cpt_value, cpt_unit, cpt_date_cre, cpt_date_maj) VALUES (3, 3, 1, 5, 4, NOW(), NOW());
INSERT INTO `RawMaterialCounter` (cpt_id, cpt_counter_type, cpt_product, cpt_value, cpt_unit, cpt_date_cre, cpt_date_maj) VALUES (4, 2, 2, 0.05, 4, NOW(), NOW());
