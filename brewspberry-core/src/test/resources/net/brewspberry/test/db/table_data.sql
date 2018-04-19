
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (1 , 1 , 'm'  , 1 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (2 , 1 , 'cm'  , 0.01 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (3 , 3 , 'L'  , 1 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (4 , 5 , 'kg'  , 1000 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (6 , 3 , 'btl 33 cl', 0.33 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (7 , 3 , 'btl 50 cl', 0.75 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (8 , 3 , 'btl 75 cl', 0.5 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (9 , 3 , 'fut 5 L' , 5 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (10 , 3 , 'fut 20 L' , 20 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (11 , 3 , 'fut 30 L' , 30 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (12 , 4 , 'sac 1 kg' , 1 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (13 , 4 , 'sac 5 kg' , 5 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (14 , 4 , 'sac 25 kg', 25 );
--INSERT INTO StockUnit (stu_id, stu_parent, stu_value, stu_multi) VALUES (5 , 5 , 'g'  , 1 );

INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (0, 'Stock disponible a la fabrication', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (1, 'Stock DLC depassee', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (2, 'Stock reserve fabrication', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (3, 'Stock dispo vente', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (4, 'Stock reserve CC', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (5, 'Demarque casse', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (6, 'Demarque qualite', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (7, 'Stock en cours de fabrication', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (8, 'Stock bloque a la vente', NOW());
INSERT INTO CounterType (cty_id, cty_libelle, cty_date_cre) VALUES (99, 'Autre', NOW());

INSERT INTO SimpleMalt (stb_id, ing_desc, ing_fournisseur, ing_unitary_price, ing_unitary_price_unit, ing_unitary_weight, ing_unitary_weight_unit, smal_cereale, smal_type, smal_couleur) VALUES (1, 'test', 'Weyermann', 5, 4, 1, 4, 'Orge', 'Blond', 3);

INSERT INTO SimpleHoublon (stb_id, ing_desc, ing_fournisseur, ing_unitary_price, ing_unitary_price_unit, ing_unitary_weight, ing_unitary_weight_unit, shbl_variete, shbl_acide_alpha, shbl_aromes, shbl_type) VALUES (2, 'test2', 'Saveur Biere', 20, 4, 0.1, 4, 'East Test Goldings', 4.4, 'Vanilla, weed', 1);



INSERT INTO Biere (stb_id, afp_unitary_value, afp_unitary_value_unit, afp_name, beer_style, beer_alcohol, beer_density, beer_color_ebc, beer_aroma, beer_bubbles, beer_notation, beer_comment, beer_quantity, beer_conso_progress, beer_init_bottles, beer_remaining_bottles, beer_first_drink_date) VALUES (3, 4, 4, 'Biere test', 'blonde', 8.5, 1031, 25, 'fruits genre test', 3, 4, 'pas trop mal', 50, 0.2, 70, 30, NOW());



INSERT INTO StockCounter (cpt_discriminator, cpt_id, cpt_cty_id, cpt_stb_id, cpt_value, cpt_stu_id, cpt_date_cre, cpt_date_maj) VALUES ('raw', 1, 0, 1, 200000, 5, NOW(), NOW());
INSERT INTO StockCounter (cpt_discriminator, cpt_id, cpt_cty_id, cpt_stb_id, cpt_value, cpt_stu_id, cpt_date_cre, cpt_date_maj) VALUES ('raw', 2, 0, 2, 0.1, 4, NOW(), NOW());
INSERT INTO StockCounter (cpt_discriminator, cpt_id, cpt_cty_id, cpt_stb_id, cpt_value, cpt_stu_id, cpt_date_cre, cpt_date_maj) VALUES ('raw', 3, 2, 1, 5.0, 4, NOW(), NOW());
INSERT INTO StockCounter (cpt_discriminator, cpt_id, cpt_cty_id, cpt_stb_id, cpt_value, cpt_stu_id, cpt_date_cre, cpt_date_maj) VALUES ('raw', 4, 1, 2, 0.05, 4, NOW(), NOW());


INSERT INTO StockCounter (cpt_discriminator, cpt_id, cpt_cty_id, cpt_stb_id, cpt_value, cpt_stu_id, cpt_date_cre, cpt_date_maj) VALUES ('finished', 5, 2, 3, 0.05, 4, NOW(), NOW());
INSERT INTO Brassin (bra_id, bra_date_maj, bra_debut, bra_fin, bra_nom, bra_quantiteEnLitres, bra_statut, bra_type) VALUES (1, NOW(), NOW(), NOW(), 'testBrew', 50, 1, 'blonde');

--INSERT INTO PalierType (plt_id, plt_libelle, plt_temperature, plt_temperature_max, plt_temperature_min) VALUES (1, 'testPalier', 64, 65, 60);
INSERT INTO Etape (etp_id, etp_nom, etp_numero, etp_remarque, etp_temperature_theorique, etp_bra_id, etp_etape_type, etp_palier_type, etp_active, etp_duration) VALUES (1, 'test1', 0, 'blabla', 64, 1, 'PALIER', 'SACCHARIFICATION', true, 0);