package be.lsinf1225.julien.ezmeal.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import be.lsinf1225.julien.ezmeal.model.Evaluation;
import be.lsinf1225.julien.ezmeal.model.Ingredients;
import be.lsinf1225.julien.ezmeal.model.Necessite;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.model.User;

/**
 * Created by julien on 29-03-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Version de la base de donnée
    private static final int DATABASE_VERSION = 10;

    //Nom de la base de donnée
    private static final String DATABASE_NAME = "UserManager.db";


    //Nom de la table User
    private static final String TABLE_USER = "Utilisateur";

    //Noms des colonnes de la table User
    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_USER_NOM = "nom";
    private static final String COLUMN_USER_PRENOM = "prenom";
    private static final String COLUMN_USER_MAIL = "mail";
    private static final String COLUMN_USER_MDP = "mdp";
    private static final String COLUMN_USER_NAISSANCE = "naissance";
    private static final String COLUMN_USER_SEXE = "sexe";
    private static final String COLUMN_USER_LANGUE = "Langue";
    private static final String COLUMN_USER_PHOTO = "photo";

    //Creation de la table User
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_LOGIN + " VARCHAR NOT NULL UNIQUE PRIMARY KEY,"
            + COLUMN_USER_NOM + " VARCHAR NOT NULL," + COLUMN_USER_PRENOM + " VARCHAR NOT NULL,"
            + COLUMN_USER_MAIL + " VARCHAR NOT NULL," + COLUMN_USER_MDP + " VARCHAR NOT NULL,"
            + COLUMN_USER_NAISSANCE + " BIGINT," + COLUMN_USER_SEXE + " VARCHAR CHECK(\"sexe\" IN (\"M\", \"F\")),"
            + COLUMN_USER_PHOTO + " BLOB,"
            + COLUMN_USER_LANGUE + " VARCHAR NOT NULL CHECK(\"Langue\" IN (\"FR\", \"EN\"))" + ")";

    //Suppression de la table
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //Nom de la table recette
    private static final String TABLE_RECETTE = "Recette";

    //Noms des colonnes de la table recette
    private static final String COLUMN_RECETTE_NOM = "nom";
    private static final String COLUMN_RECETTE_DESCRIPTION = "description";
    private static final String COLUMN_RECETTE_INSTRUCTION = "instruction";
    private static final String COLUMN_RECETTE_TEMPSPREPARATION = "tempsPreparation";
    private static final String COLUMN_RECETTE_TEMPSCUISSON = "tempsCuisson";
    private static final String COLUMN_RECETTE_TYPE = "type";
    private static final String COLUMN_RECETTE_DIFFICULTE = "difficulte";
    private static final String COLUMN_RECETTE_DATEAJOUT = "dateAjout";
    private static final String COLUMN_RECETTE_CREATEUR = "createur";
    private static final String COLUMN_RECETTE_SOUSTYPE = "sous_type";
    private static final String COLUMN_RECETTE_PHOTO = "photo";

    //creation de la table recette
    private String CREATE_RECETTE_TABLE = "CREATE TABLE " + TABLE_RECETTE + "("
            + COLUMN_RECETTE_NOM + " VARCHAR PRIMARY KEY NOT NULL," + COLUMN_RECETTE_DESCRIPTION + " VARCHAR NOT NULL,"
            + COLUMN_RECETTE_INSTRUCTION + " TEXT NOT NULL," + COLUMN_RECETTE_TEMPSPREPARATION + " INTEGER NOT NULL,"
            + COLUMN_RECETTE_TEMPSCUISSON + " INTEGER NOT NULL,"
            + COLUMN_RECETTE_TYPE + " VARCHAR NOT NULL," + COLUMN_RECETTE_DIFFICULTE + " INTERGER NOT NULL,"
            + COLUMN_RECETTE_SOUSTYPE + " VARCHAR," + COLUMN_RECETTE_DATEAJOUT + " BIGINT NOT NULL,"
            + COLUMN_RECETTE_CREATEUR + " VARCHAR," + COLUMN_RECETTE_PHOTO + " BLOB" +")";

    //Suppression de la table recette
    private static final String DROP_RECETTE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECETTE;

    //Nom de la table ingrédient
    private static final String TABLE_INGREDIENTS = "Ingredients";

    //noms des colonnes de la table ingrédient
    private static final String COLUMN_INGREDIENTS_LIBELLE = "libelle";
    private static final String COLUMN_INGREDIENTS_PRIX_KG = "prix_kg";

    //creationd de la table ingrédient
    private String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
            + COLUMN_INGREDIENTS_LIBELLE + " VARCHAR PRIMARY KEY NOT NULL," + COLUMN_INGREDIENTS_PRIX_KG + " DOUBLE NOT NULL" + ")";

    //Suppression de la table ingrédient
    private static final String DROP_INGREDIENTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_INGREDIENTS;

    //Nom de la table Evaluation
    private static final String TABLE_EVALUATION = "Evaluation";

    //Noms des colonnes de la table évaluation
    private static final String COLUMN_EVALUATION_LOGIN_USER = "login";
    private static final String COLUMN_EVALUATION_NOM_RECETTE = "nom";
    private static final String COLUMN_EVALUATION_NOTE = "note";
    private static final String COLUMN_EVALUATION_COMMENT = "comment";
    private static final String COLUMN_EVALUATION_IMAGE = "image";
    private static final String COLUMN_EVALUATION_DATE = "date";

    //Creation de la table ingrédient
    private String CREATE_EVALUATION_TABLE = "CREATE TABLE " + TABLE_EVALUATION + "("
            + COLUMN_EVALUATION_LOGIN_USER + " VARCHAR NOT NULL," + COLUMN_EVALUATION_NOM_RECETTE + " VARCHAR NOT NULL,"
            + COLUMN_EVALUATION_NOTE + " INTEGER NOT NULL CHECK(\"note\" IN (\"1\", \"2\", \"3\", \"4\", \"5\"))," + COLUMN_EVALUATION_DATE + " BIGINT NOT NULL,"
            + COLUMN_EVALUATION_COMMENT + " TEXT," + COLUMN_EVALUATION_IMAGE + " BLOB," + " PRIMARY KEY (\"login\", \"nom\"),"
            + " FOREIGN KEY ("+ COLUMN_RECETTE_NOM + ") REFERENCES Recette(" + COLUMN_RECETTE_NOM + "), FOREIGN KEY (" + COLUMN_USER_LOGIN +") REFERENCES Utilisateur(" + COLUMN_USER_LOGIN + ") )";

    //Suppresion de la table ingrédient
    private static final String DROP_EVALUATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVALUATION;

    //Nom de la table necessite
    private static final String TABLE_NECESSITE = "Necessite";

    //Noms des colonnes de la table necessite
    private static final String COLUMN_NECESSITE_NOM_RECETTE = "nom";
    private static final String COLUMN_NECESSITE_LIBELLEING = "libelleIng";
    private static final String COLUMN_NECESSITE_QUANTITE = "quantite";
    private static final String COLUMN_NECESSITE_UNITE = "unite";

    //creation de la table necessite
    private String CREATE_NECESSITE_TABLE = "CREATE TABLE " + TABLE_NECESSITE + "("
            + COLUMN_NECESSITE_NOM_RECETTE + " VARCHAR NOT NULL," + COLUMN_NECESSITE_LIBELLEING + " VARCHAR NOT NULL,"
            + COLUMN_NECESSITE_QUANTITE + " DOUBLE NOT NULL," + COLUMN_NECESSITE_UNITE + " VARCHAR NOT NULL,"
            + " PRIMARY KEY (\"nom\", \"libelleIng\"), FOREIGN KEY (nom) REFERENCES Recette(nom)," +
            " FOREIGN KEY (libelleIng) REFERENCES Ingredients(libelle))";

    //Suppression de la table necessite
    private static final String DROP_NECESSITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NECESSITE;

    private String insertRecette = "INSERT INTO " + TABLE_RECETTE + "(" + COLUMN_RECETTE_NOM + ", " + COLUMN_RECETTE_DESCRIPTION + ", "
            + COLUMN_RECETTE_INSTRUCTION + ", " + COLUMN_RECETTE_TEMPSPREPARATION + ", " + COLUMN_RECETTE_TEMPSCUISSON + ", "
            + COLUMN_RECETTE_TYPE + ", " + COLUMN_RECETTE_DIFFICULTE + ", " + COLUMN_RECETTE_DATEAJOUT + ", " + COLUMN_RECETTE_PHOTO + ", "
            + COLUMN_RECETTE_CREATEUR + ", " + COLUMN_RECETTE_SOUSTYPE + ")";
    //Insertion de recette
    private Recette r1 = new Recette("Les Timbales de Jeanne", "saumon à la mousse de courgettes au micro-ondes",
            "Rincer, essorer et ciseler l'aneth et la menthe. Peler et émincer la gousse d'ail. \n" +
                    "\n" +
                    "Râper les courgettes.\n" +
                    "\n" +
                    "Les faire revenir dans 1 cuillère à soupe d'huile d'olive avec l'ail émincé et les herbes ciselées.\n" +
                    "Assaisonner (mais pas trop, attention au saumon fumé). Réserver et laisser un peu refroidir. \n" +
                    "\n" +
                    "Battre les oeufs et la crème en omelette.\n" +
                    "\n" +
                    "Mélanger cette préparation avec les courgettes râpées et éventuellement, donner un petit coup de mixeur pour obtenir l'aspect \"mousse\". \n" +
                    "\n" +
                    "Tapisser 4 ramequins avec les tranches de saumon fumé.\n" +
                    "\n" +
                    "Verser le mélange omelette-courgettes dans les ramequins tapissés de saumon. \n" +
                    "\n" +
                    "Faire cuire au micro-ondes pendant 2 à 3 minutes, selon la puissance (vérifier la cuisson vous-même, il n'y a que ça de vrai !). Déguster !",
            10, 3, "entrées", 1, "12/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/e5/e5fb476f-f806-4fb0-bde1-0b3e69d3e1bc_normal.jpg",
            "bastinjul", "chaude");
    private Recette r2 = new Recette("Cake salé au jambon et aux olive", "Entrée - Facile - Moyen",
            "Préchauffer le four à 180°C (thermostat 6).\n" +
                    "\n" +
                    "Couper les olives en rondelles et le jambon en morceaux.\n" +
                    "\n" +
                    "Verser les oeufs dans la farine et mélanger. \n" +
                    "\n" +
                    "Ajouter le lait et l'huile puis mélanger. \n" +
                    "\n" +
                    "Ajouter le jambon, les olives et le gruyère puis mélanger. \n" +
                    "\n" +
                    "Ajouter la levure et, pour ne pas changer, mélanger.\n" +
                    "\n" +
                    "Mettre le tout dans un plat à cake au préalable beurré et placer au four 45 min.",
            20, 45, "entrées", 3, "13/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/c1/c18b0d21-f902-4135-957b-33d750379e09_normal.jpg",
            "bastinjul", "chaude");
    private Recette r3 = new Recette("Tarte thon et tomate", "Entrée - Très facile - Bon marché",
            "Préchauffer le four à 210°C (thermostat 7).\n" +
                    "\n" +
                    "Etaler la pâte brisée et la piquer avec une fourchette. \n" +
                    "\n" +
                    "Emietter le thon sur la pâte.\n" +
                    "\n" +
                    "Mélanger dans un gros bol : la crème, le concentré de tomate, l'oeuf et le sel; verser la préparation sur le thon.\n" +
                    "\n" +
                    "Saupoudrer d'herbes de Provence, recouvrir de fromage râpé, puis faire cuire 25 min (selon puissance du four).",
            10, 25, "entrées", 1, "15/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/fd/fd24e90e-81a0-46ef-b496-fb1b688b5ee7_normal.jpg",
            "bastinjul", "chaude");
    private Recette r4 = new Recette("Blinis faciles maison", "Entrée - Très facile - Bon marché - Végétarien",
            "Mélanger tous les ingrédients ensemble, puis laisser reposer la pâte pendant 1 heure au réfrigérateur. \n" +
                    "\n" +
                    "Cuire dans un poêle à blinis ou sur une poêle antiadhésive. \n" +
                    "\n" +
                    "Lorsqu'ils font des trous, les retourner.\n" +
                    "\n" +
                    "Servir de suite ou réchauffer avant de passer à table.",
            5, 20, "entrées", 1, "15/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/2a/2a1c984c-7ed5-4478-80c5-3a55a80e6553_normal.jpg",
            "bastinjul", "chaude");
    private Recette r5 = new Recette("Chèvre en feuilleté", "Entrée - Très facile - Bon marché - Végétarien",
            "Préchauffer le four à 210°C (thermostat 7).\n" +
                    "\n" +
                    "Couper la bûche en 8 rondelles égales.\n" +
                    "\n" +
                    "Avec un verre, couper 16 ronds dans la pâte feuilletée.\n" +
                    "\n" +
                    "Poser chaque rondelle de fromage sur un rond de pâte feuilletée, parsemer de basilic.\n" +
                    "\n" +
                    "Recouvrir avec la pâte feuilletée restante.\n" +
                    "\n" +
                    "Fermer tout autour les petits pâtés ainsi réalisés. Dorer au jaune d'oeuf avant de passer au four 15 min.\n" +
                    "Se marie très bien avec une salade verte.",
            15, 20, "entrées", 1, "20/04/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/de/de6943f1-e249-4a70-89e6-54371cb19cde_normal.jpg",
            "bastinjul", "chaude");
    private Recette r6 = new Recette("Blanquette de veau facile", "Plat principal - Très facile - Moyen",
            "Faire revenir la viande dans un peu de beurre doux jusqu'à ce que les morceaux soient un peu dorés.\n" +
                    "\n" +
                    "Saupoudrer de 2 cuillères de farine. Bien remuer.\n" +
                    "Ajouter 2 ou 3 verres d'eau, les cubes de bouillon, le vin et remuer. Ajouter de l'eau si nécessaire pour couvrir.\n" +
                    "\n" +
                    "Couper les carottes en rondelles et émincer les oignons puis les incorporer à la viande, ainsi que les champignons. \n" +
                    "\n" +
                    "Laisser mijoter à feu très doux environ 1h30 à 2h00 en remuant. \n" +
                    "Si nécessaire, ajouter de l'eau de temps en temps. \n" +
                    "\n" +
                    "Dans un bol, bien mélanger la crème fraîche, le jaune d’oeuf et le jus de citron. Ajouter ce mélange au dernier moment, bien remuer et servir tout de suite.",
            15, 120, "viandes", 2, "11/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/7e/7e38b1da-c290-4e97-8890-a0d51c85dc46_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r7 = new Recette("Filet mignon en croûte", "Plat principal - Très facile - Bon marché",
            "Peler et émincer les oignons.\n" +
                    "\n" +
                    "Les faire revenir dans une sauteuse avec 20 g de beurre pendant 3 minutes environ. Les retirer de la sauteuse et les réserver. \n" +
                    "\n" +
                    "Dans la même sauteuse, faire revenir les filets mignons de chaque côté.\n" +
                    "Laisser cuire 10 minutes à feu doux.\n" +
                    "\n" +
                    "Réincorporer les oignons. Poursuivre la cuisson pendant 5 minutes. Saler, poivrer. Réserver.\n" +
                    "\n" +
                    "Dérouler les pâtes feuilletées. \n" +
                    "\n" +
                    "Déposer sur chaque pâte deux tranches de jambon et 100 g de gruyère. Saler et poivrer.\n" +
                    "\n" +
                    "Y déposer un filet sur chaque pâte garnie et napper de sauce aux oignons.\n" +
                    "\n" +
                    "Replier la pâte autour de la viande et souder les bords à l'aide du jaune d'oeuf préalablement battu et d'un pinceau alimentaire.\n" +
                    "\n" +
                    "Enfourner pour 45 minutes de cuisson à 200°C (thermostat 6-7).",
            15, 45, "viandes", 2, "14/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/06/06696c38-d8c2-4d39-94b2-a88e7a93a4f1_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r8 = new Recette("Lasagnes à la bolognaise", "Plat principal - Moyennement difficile - Moyen",
            "Emincer les oignons. Ecraser les gousses d'ail. Hacher finement carotte et céleri. \n" +
                    "\n" +
                    "Faire revenir gousses d'ail et oignons dans un peu d'huile d'olive.\n" +
                    "\n" +
                    "Ajouter la carotte et la branche de céleri hachée puis la viande et faire revenir le tout. \n" +
                    "\n" +
                    "Au bout de quelques minutes, ajouter le vin rouge. Laisser cuire jusqu'à évaporation.\n" +
                    "\n" +
                    "Ajouter la purée de tomates, l'eau et les herbes. Saler, poivrer, puis laisser mijoter à feu doux 45 minutes.\n" +
                    "\n" +
                    "Préparer la béchamel : faire fondre le beurre, puis, hors du feu, ajouter la farine d'un coup. Remettre sur le feu et remuer avec un fouet jusqu'à l'obtention d'un mélange bien lisse. \n" +
                    "\n" +
                    "Ajouter le lait peu à peu. Remuer sans cesse, jusqu'à ce que le mélange s'épaississe. Ensuite, parfumer avec la muscade, saler, poivrer.\n" +
                    "\n" +
                    "Laisser cuire environ 5 minutes, à feu très doux, en remuant. Réserver.\n" +
                    "\n" +
                    "Préchauffer le four à 200°C (thermostat 6-7).\n" +
                    "\n" +
                    "Huiler le plat à lasagnes. Poser une fine couche de béchamel puis : des feuilles de lasagnes, de la bolognaise, de la béchamel et du parmesan, et cela 3 fois de suite. \n" +
                    "\n" +
                    "Sur la dernière couche de lasagnes, ne mettre que de la béchamel et recouvrir de fromage râpé. Parsemer quelques noisettes de beurre.\n" +
                    "\n" +
                    "Enfourner pour environ 25 minutes de cuisson.",
            45, 90, "pâtes", 3, "10/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/72/72d94850-5ffa-4b21-8e54-6d7b7aac0a5a_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r9 = new Recette("Amour de saumon en papillote", "Plat principal - Très facile - Moyen",
            "Préchauffer le four à 180°C (thermostat 6).\n" +
                    "\n" +
                    "Laver, essorer et ciseler l'aneth. Peler et émincer la gousse d'ail finement. Réserver. \n" +
                    "\n" +
                    "Couper les tomates cerise en deux.\n" +
                    "\n" +
                    "Emincer les champignons après les avoir nettoyés. \n" +
                    "\n" +
                    "Déposer au centre de chaque feuille de papier cuisson un pavé de saumon, ajouter les tomates et les champignons tout autour. \n" +
                    "\n" +
                    "Parsemer les pavés de saumon d'aneth et d'ail et les arroser d'un filet de jus de citron. Poivrer, saler et terminer par un filet d'huile d'olive. \n" +
                    "\n" +
                    "Fermer les papillotes et les mettre au four pendant 25 à 30 minutes.",
            10, 25, "poissons et fruits de mer", 1, "05/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/d6/d6990ff2-c67d-4d8c-8c46-f65ac0853284_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r10 = new Recette("Poulet à la moutarde, à l'estragon et aux champignons", "Plat principal - Très facile - Bon marché",
            "Faire revenir les échalotes dans l'huile d'olive 3 min sans faire roussir.\n" +
                    "\n" +
                    "Ajouter les champignons et laisser cuire 2 min. \n" +
                    "\n" +
                    "Ajouter le bouillon de volaille. Cuire 10 min. \n" +
                    "\n" +
                    "Faire revenir les blancs de poulet dans une poêle anti-adhésive jusqu'à ce qu'ils soient dorés. \n" +
                    "\n" +
                    "Les ajouter aux champignons et cuire 10 min.\n" +
                    "\n" +
                    "A la fin, enlever le poulet, ajouter la moutarde, la crème fraîche et l'estragon.\n" +
                    "\n" +
                    "Emincer les blancs de poulet en tranches et servir avec les champignons.",
            10, 30, "poulets et volailles", 2, "12/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/2f/2fccb43b-15a6-4041-8030-f6584a4a80c0_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r11 = new Recette("Ramequins fondants au chocolat", "Dessert - Très facile - Bon marché - Végétarien",
            "Préchauffez le four à 210°C (thermostat 7).\n" +
                    "\n" +
                    "Faites fondre dans une casserole le chocolat et le beurre, en remuant régulièrement pour former une pâte homogène et onctueuse.\n" +
                    "\n" +
                    "Dans un saladier, mélangez les oeufs, le sucre et la farine.\n" +
                    "\n" +
                    "Incorporez la préparation chocolatée et mélangez.\n" +
                    "\n" +
                    "Versez 1/3 de la préparation dans des ramequins individuels. \n" +
                    "\n" +
                    "Déposez deux carrés de chocolat dans chacun des 4 ramequins, puis recouvrez-les avec le reste de la préparation chocolatée.\n" +
                    "\n" +
                    "Placez les ramequins au four pendant environ 12 minutes, pas plus!\n" +
                    "\n" +
                    "Dégustez de préférence chauds ou tièdes.\n",
            10, 15, "desserts", 1, "15/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/56/56de703b-adab-49d3-b7e1-c17684364600_normal.jpg",
            "bastinjul", "chaudes");
    private Recette r12 = new Recette("Tiramisu (recette originale)", "Dessert - Très facile - Bon marché - Végétarien - Sans porc",
            "Séparer les blancs des jaunes d'oeufs.\n" +
                    "\n" +
                    "Mélanger les jaunes avec le sucre roux et le sucre vanillé. \n" +
                    "\n" +
                    "Ajouter le mascarpone au fouet.\n" +
                    "\n" +
                    "Monter les blancs en neige et les incorporer délicatement à la spatule au mélange précédent. Réserver.\n" +
                    "\n" +
                    "Mouiller les biscuits dans le café rapidement avant d'en tapisser le fond du plat.\n" +
                    "\n" +
                    "Recouvrir d'une couche de crème au mascarpone puis répéter l'opération en alternant couche de biscuits et couche de crème en terminant par cette dernière.\n" +
                    "\n" +
                    "Saupoudrer de cacao.\n" +
                    "\n" +
                    "Mettre au réfrigérateur 4 heures minimum puis déguster frais.",
            10, 0, "desserts", 2, "15/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/a8/a860ca6e-8fbf-468e-abfa-2c1432a9bac2_normal.jpg",
            "bastinjul", "froide");
    private Recette r13 = new Recette("Salade de l'été", "Entrée - Très facile - Moyen",
            "Mettre la salade dans un saladier, y ajouter les tomates coupées en quartiers fins, les miettes de crabe, l'avocat et la mozzarella coupé en dés. \n" +
                    "Faire une petite sauce blanche pour accompagner.",
            10, 0, "salades", 1, "15/03/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/4a/4abc5464-da60-497d-8a59-b4ed4a63c3dc_normal.jpg",
            "bastinjul", "de saison");
    private Recette r14 = new Recette("Civet de marcassin nature", "Plat principal - Très facile - Moyen",
            "Nettoyer le civet : enlever les parties grasses.\n" +
                    "Fondre la graisse dans une cocotte puis rissoler légèrement les morceaux de civet.\n" +
                    "Parsemer de farine, remuer convenablement. Ajouter 1 dl de fond de gibier, puis incorporer les diverses épices (laurier, poivre, thym...). Couvrir et laisser cuire au moins 2 h.\n" +
                    "En fin de cuisson, ajouter la crème fraîche et -si nécessaire- la maïzena.",
            15, 120, "gibiers", 1, "15/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/df/df3d6b02-a514-4e4e-b52c-ae649f0ba571_normal.jpg",
            "bastinjul", "chaude");
    private Recette r15 = new Recette("Ice Blue", "Boisson - Très facile - Bon marché",
            "Versez tous les ingrédients sauf le Curaçao bleu dans un récipient et mélangez délicatement. \n" +
                    "Rajoutez le Curaçao bleu à votre convenance (suivant la couleur voulue).\n" +
                    "\n" +
                    "Laissez refroidir au réfrigérateur 1 heure minimum. Servez bien frais.",
            10, 0, "cocktails", 1, "14/05/2017", "https://images.marmitoncdn.org/recipephotos/multiphoto/05/0572d640-3383-4436-84aa-dc4174e9625b_normal.jpg",
            "bastinjul", "froide");

    //ajout des ingrédients dans la bdd
    private String ingrdients = "INSERT INTO " + TABLE_INGREDIENTS + "\n" +
            "VALUES " +
            "(\"saumon\", 10.0),\n" +
            "(\"courgette\", 2.0),\n" +
            "(\"oeuf\", 0.2),\n" +
            "(\"crème fraiche\", 2.0),\n" +
            "(\"gousse d'ail\", 0.7),\n" +
            "(\"huile d'olive\", 3.0),\n" +
            "(\"aneth\", 0.2),\n" +
            "(\"menthe\", 0.9),\n" +
            "(\"sel\", 2.5),\n" +
            "(\"poivre\", 2.5),\n" +
            "(\"farine\", 2.0),\n" +
            "(\"jambon\", 3.5),\n" +
            "(\"olive\", 3.0),\n" +
            "(\"gruyère rapé\", 6.0),\n" +
            "(\"lait\", 1.5),\n" +
            "(\"levure\", 2.0),\n" +
            "(\"pâte brisée\", 3.0),\n" +
            "(\"thon\", 4.0),\n" +
            "(\"concentré de tomate\", 0.5),\n" +
            "(\"yaourt\", 2.5),\n" +
            "(\"fromage de chèvre\", 3.0),\n" +
            "(\"basilic\", 2.0),\n" +
            "(\"blanquette de veau\", 5.0),\n" +
            "(\"bouillon de poulet\", 1.0),\n" +
            "(\"bouillon de légume\", 1.0),\n" +
            "(\"carrote\", 3.0),\n" +
            "(\"oignon\", 3.0),\n" +
            "(\"champignon\", 2.0),\n" +
            "(\"jus de citron\", 3.0),\n" +
            "(\"vin blanc\", 5.0),\n" +
            "(\"filets mignon de porc\", 5.0),\n" +
            "(\"pâte à lasagne\", 3.0),\n" +
            "(\"céleri\", 2.0),\n" +
            "(\"viande de boeuf hachée\", 6.0),\n" +
            "(\"purée de tomates\", 2.0),\n" +
            "(\"vin rouge\", 5.0),\n" +
            "(\"eau\", 0.0),\n" +
            "(\"feuille de laurier\", 1.0),\n" +
            "(\"thym\", 1.0),\n" +
            "(\"tomate cerise\", 0.1),\n" +
            "(\"citro jaune\", 0.6),\n" +
            "(\"blanc de poulet\", 1.0),\n" +
            "(\"moutarde\", 0.1),\n" +
            "(\"estragon\", 0.1),\n" +
            "(\"échalotes\", 0.4),\n" +
            "(\"chocolat noir\", 6.0),\n" +
            "(\"sucre\", 3.0),\n" +
            "(\"beurre\", 3.0),\n" +
            "(\"mascarpone\", 2.0),\n" +
            "(\"buiscuits à la cuillère\", 0.1),\n" +
            "(\"café noir\", 3.0),\n" +
            "(\"poudre de cacao\", 5.0),\n" +
            "(\"avocat\", 1.0),\n" +
            "(\"miettes de crabe\", 3.0),\n" +
            "(\"mozzarella\", 5.0),\n" +
            "(\"salade\", 2.0),\n" +
            "(\"tomate\", 2.5),\n" +
            "(\"civet de marcassin\", 7.0),\n" +
            "(\"margarine\", 2.0),\n" +
            "(\"fond de gibier\", 1.0),\n" +
            "(\"grain de genévrier\", 0.2),\n" +
            "(\"pulco citron\", 2.0),\n" +
            "(\"gin\", 10.0),\n" +
            "(\"seven up\", 3.0),\n" +
            "(\"curaçao bleu\", 6.0);";

    //ajout des necessites dans la BDD
    private String necessite = "INSERT INTO " + TABLE_NECESSITE + "\n" +
            "VALUES " +
            "(\"" + r1.getNom() + "\", \"saumon\", 4, \"tranches\"),\n" +
            "(\"" + r1.getNom() + "\", \"courgette\", 2, 0),\n" +
            "(\"" + r1.getNom() + "\", \"oeuf\", 3, 0),\n" +
            "(\"" + r1.getNom() + "\", \"crème fraiche\", 0.01, \"litre\"),\n" +
            "(\"" + r1.getNom() + "\", \"gousse d'ail\", 1, \"unité\"),\n" +
            "(\"" + r1.getNom() + "\", \"huile d'olive\", 1, \"cuillère à soupe\"),\n" +
            "(\"" + r1.getNom() + "\", \"aneth\", 0, 0),\n" +
            "(\"" + r1.getNom() + "\", \"menthe\", 0, 0),\n" +
            "(\"" + r1.getNom() + "\", \"sel\", 0, 0),\n" +
            "(\"" + r1.getNom() + "\", \"poivre\", 0, 0),\n" +
            "(\"" + r2.getNom() + "\", \"farine\", 0.15, \"kg\"),\n" +
            "(\"" + r2.getNom() + "\", \"jambon\", 0.2, \"kg\"),\n" +
            "(\"" + r2.getNom() + "\", \"olive\", 0.15, \"kg\"),\n" +
            "(\"" + r2.getNom() + "\", \"gruyère rapé\", 0.75, \"kg\"),\n" +
            "(\"" + r2.getNom() + "\", \"oeuf\", 4, \"unité\"),\n" +
            "(\"" + r2.getNom() + "\", \"lait\", 0.01, \"litre\"),\n" +
            "(\"" + r2.getNom() + "\", \"levure\", 1, \"sachet\"),\n" +
            "(\"" + r2.getNom() + "\", \"huile d'olive\", 0, \"1 cuillère à soupe\"),\n" +
            "(\"" + r3.getNom() + "\", \"pâte brisée\", 1, \"unité\"),\n" +
            "(\"" + r3.getNom() + "\", \"crème fraiche\", 0.02, \"litre\"),\n" +
            "(\"" + r3.getNom() + "\", \"concentré de tomate\", 1, \"petite boite\"),\n" +
            "(\"" + r3.getNom() + "\", \"thon\", 0.15, \"kg\"),\n" +
            "(\"" + r3.getNom() + "\", \"sel\", 0, 0),\n" +
            "(\"" + r3.getNom() + "\", \"gruyère râpé\", 0.05, \"kg\"),\n" +
            "(\"" + r3.getNom() + "\", \"oeuf\", 1, \"unité\"),\n" +
            "(\"" + r4.getNom() + "\", \"yaourt\", 1, \"unité\"),\n" +
            "(\"" + r4.getNom() + "\", \"oeuf\", 1, \"unité\"),\n" +
            "(\"" + r4.getNom() + "\", \"levure\", 0.5, \"sachet\"),\n" +
            "(\"" + r4.getNom() + "\", \"sel\", 0, 0),\n" +
            "(\"" + r5.getNom() + "\", \"fromage de chèvre\", 1, \"bûche de\"),\n" +
            "(\"" + r5.getNom() + "\", \"pâte brisée\", 1, \"unité\"),\n" +
            "(\"" + r5.getNom() + "\", \"oeuf\", 1, \"unité\"),\n" +
            "(\"" + r5.getNom() + "\", \"basilic\", 0, 0),\n" +
            "(\"" + r6.getNom() + "\", \"blanquette de veau\", 1, \"kg\"),\n" +
            "(\"" + r6.getNom() + "\", \"bouillon de poulet\", 1, \"cube\"),\n" +
            "(\"" + r6.getNom() + "\", \"bouillon de légume\", 1, \"cube\"),\n" +
            "(\"" + r6.getNom() + "\", \"carotte\", 3, \"unité\"),\n" +
            "(\"" + r6.getNom() + "\", \"oignon\", 1, \"unité\"),\n" +
            "(\"" + r6.getNom() + "\", \"champignon\", 0.3, \"kg\"),\n" +
            "(\"" + r6.getNom() + "\", \"crème fraiche\", 0.3, \"litre\"),\n" +
            "(\"" + r6.getNom() + "\", \"jus de citron\", 0, 0),\n" +
            "(\"" + r6.getNom() + "\", \"farine\", 0, 0),\n" +
            "(\"" + r6.getNom() + "\", \"vin blanc\", 0.25, \"litre\"),\n" +
            "(\"" + r6.getNom() + "\", \"sel\", 0, 0),\n" +
            "(\"" + r6.getNom() + "\", \"poivre\", 0, 0),\n" +
            "(\"" + r7.getNom() + "\", \"filets mignon de porc\", 2, \"unité\"),\n" +
            "(\"" + r7.getNom() + "\", \"pâte brisée\", 4, \"unité\"),\n" +
            "(\"" + r7.getNom() + "\", \"jambon\", 0.3, \"kg\"),\n" +
            "(\"" + r7.getNom() + "\", \"oignon\", 2, \"unité\"),\n" +
            "(\"" + r7.getNom() + "\", \"oeuf\", 2, \"unité\"),\n" +
            "(\"" + r8.getNom() + "\", \"pâte à lasagne\", 1, \"paquet\"),\n" +
            "(\"" + r8.getNom() + "\", \"oignon\", 3, \"unité\"),\n" +
            "(\"" + r8.getNom() + "\", \"gousse d'ail\", 2, \"unité\"),\n" +
            "(\"" + r8.getNom() + "\", \"céleri\", 1, \"branche\"),\n" +
            "(\"" + r8.getNom() + "\", \"carotte\", 1, \"unité\"),\n" +
            "(\"" + r8.getNom() + "\", \"viande de boeuf hachée\", 0.6, \"kg\"),\n" +
            "(\"" + r8.getNom() + "\", \"purée de tomate\", 0.8, \"kg\"),\n" +
            "(\"" + r8.getNom() + "\", \"eau\", 0.15, \"litre\"),\n" +
            "(\"" + r8.getNom() + "\", \"vin rouge\", 0.2, \"litre\"),\n" +
            "(\"" + r8.getNom() + "\", \"thym\", 0, 0),\n" +
            "(\"" + r8.getNom() + "\", \"basilic\", 0, 0),\n" +
            "(\"" + r9.getNom() + "\", \"saumon\", 1, \"kg\"),\n" +
            "(\"" + r9.getNom() + "\", \"tomate cerise\", 20, \"unité\"),\n" +
            "(\"" + r9.getNom() + "\", \"champignon\", 0.3, \"kg\"),\n" +
            "(\"" + r9.getNom() + "\", \"aneth\", 4, \"brins\"),\n" +
            "(\"" + r9.getNom() + "\", \"huile d'olive\", 0, 0),\n" +
            "(\"" + r9.getNom() + "\", \"sel\", 0, 0),\n" +
            "(\"" + r9.getNom() + "\", \"poivre\", 0, 0),\n" +
            "(\"" + r10.getNom() + "\", \"blanc de poulet\", 0.3, \"kg\"),\n" +
            "(\"" + r10.getNom() + "\", \"champignon\", 0.2, \"kg\"),\n" +
            "(\"" + r10.getNom() + "\", \"moutarde\", 2, \"cuillères\"),\n" +
            "(\"" + r10.getNom() + "\", \"crème fraiche\", 0.2, \"litre\"),\n" +
            "(\"" + r10.getNom() + "\", \"estragon\", 2, \"cuillères\"),\n" +
            "(\"" + r10.getNom() + "\", \"huile d'olive\", 2, \"cuillères\"),\n" +
            "(\"" + r10.getNom() + "\", \"échalotes\", 2, \"unité\"),\n" +
            "(\"" + r11.getNom() + "\", \"chocolat\", 0.12, \"kg\"),\n" +
            "(\"" + r11.getNom() + "\", \"oeuf\", 3, \"unité\"),\n" +
            "(\"" + r11.getNom() + "\", \"sucre\", 0.08, \"kg\"),\n" +
            "(\"" + r11.getNom() + "\", \"beurre\", 0.035, \"kg\"),\n" +
            "(\"" + r11.getNom() + "\", \"farine\", 0, 0),\n" +
            "(\"" + r12.getNom() + "\", \"oeuf\", 3, \"unité\"),\n" +
            "(\"" + r12.getNom() + "\", \"sucre\", 0.1, \"kg\"),\n" +
            "(\"" + r12.getNom() + "\", \"mascarpone\", 0.25, \"kg\"),\n" +
            "(\"" + r12.getNom() + "\", \"buiscuits à la cuillère\", 24, \"unité\"),\n" +
            "(\"" + r12.getNom() + "\", \"café\", 0.05, \"kg\"),\n" +
            "(\"" + r12.getNom() + "\", \"poudre de cacao\", 0.03, \"kg\"),\n" +
            "(\"" + r13.getNom() + "\", \"avocat\", 1, \"unité\"),\n" +
            "(\"" + r13.getNom() + "\", \"mozzarella\", 0.125, \"kg\"),\n" +
            "(\"" + r13.getNom() + "\", \"miettes de crabe\", 0.18, \"kg\"),\n" +
            "(\"" + r13.getNom() + "\", \"salade\", 1, \"unité\"),\n" +
            "(\"" + r13.getNom() + "\", \"tomate\", 2, \"unité\"),\n" +
            "(\"" + r14.getNom() + "\", \"civet de marcassin\", 0.5, \"kg\"),\n" +
            "(\"" + r14.getNom() + "\", \"farine\", 0, \"1 cuillère\")," +
            "(\"" + r14.getNom() + "\", \"margarine\", 0, 0),\n" +
            "(\"" + r14.getNom() + "\", \"fond de gibier\", 0.1, \"litre\"),\n" +
            "(\"" + r14.getNom() + "\", \"grain de genévrier\", 5, \"unité\"),\n" +
            "(\"" + r14.getNom() + "\", \"poivre\", 0, \"5 grains de\"),\n" +
            "(\"" + r15.getNom() + "\", \"pulco citron\", 0.35, \"litre\"),\n" +
            "(\"" + r15.getNom() + "\", \"gin\", 0.7, \"litre\"),\n" +
            "(\"" + r15.getNom() + "\", \"seven up\", 3, \"litre\"),\n" +
            "(\"" + r15.getNom() + "\", \"curaçao bleu\", 0, 0);";

    /**
     * Constructeur
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RECETTE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_EVALUATION_TABLE);
        db.execSQL(CREATE_NECESSITE_TABLE);
        addRecipe(r1, db);
        addRecipe(r2, db);
        addRecipe(r3, db);
        addRecipe(r4, db);
        addRecipe(r5, db);
        addRecipe(r6, db);
        addRecipe(r7, db);
        addRecipe(r8, db);
        addRecipe(r9, db);
        addRecipe(r10, db);
        addRecipe(r11, db);
        addRecipe(r12, db);
        addRecipe(r13, db);
        addRecipe(r14, db);
        addRecipe(r15, db);
        db.execSQL(ingrdients);
        db.execSQL(necessite);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        //Suppression des tables si elles existes
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_RECETTE_TABLE);
        db.execSQL(DROP_INGREDIENTS_TABLE);
        db.execSQL(DROP_EVALUATION_TABLE);
        db.execSQL(DROP_NECESSITE_TABLE);

        //Recreation des tables
        onCreate(db);
    }

    /**
     * Ajout d'un utilisateur
     *
     * @param user
     */
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NOM, user.getNom());
        values.put(COLUMN_USER_PRENOM, user.getPrenom());
        values.put(COLUMN_USER_LOGIN, user.getLogin());
        values.put(COLUMN_USER_MAIL, user.getMail());
        values.put(COLUMN_USER_MDP, user.getMdp());
        values.put(COLUMN_USER_LANGUE, user.getLangue());
        values.put(COLUMN_USER_SEXE, user.getSexe());
        values.put(COLUMN_USER_NAISSANCE, user.getNaissance());
        values.put(COLUMN_USER_PHOTO, user.getPhoto());

        //Insertion des lignes
        db.insert(TABLE_USER, null, values);
        db.close();

    }

    /**
     * Ajout d'une recette
     * @param recette
     */
    private void addRecipe(Recette recette, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECETTE_NOM, recette.getNom());
        values.put(COLUMN_RECETTE_DESCRIPTION, recette.getDescription());
        values.put(COLUMN_RECETTE_INSTRUCTION, recette.getInstruction());
        values.put(COLUMN_RECETTE_TEMPSPREPARATION, recette.getTempsPreparation());
        values.put(COLUMN_RECETTE_TEMPSCUISSON, recette.getTempsCuisson());
        values.put(COLUMN_RECETTE_TYPE, recette.getType());
        values.put(COLUMN_RECETTE_DIFFICULTE, recette.getDifficulte());
        values.put(COLUMN_RECETTE_DATEAJOUT, recette.getDateAjout());
        values.put(COLUMN_RECETTE_PHOTO, recette.getPhoto());
        values.put(COLUMN_RECETTE_CREATEUR, recette.getCreateur());
        values.put(COLUMN_RECETTE_SOUSTYPE, recette.getSousType());

        db.insert(TABLE_RECETTE, null, values);
    }

    public void addEvaluation(Evaluation evaluation){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVALUATION_COMMENT, evaluation.getComment());
        values.put(COLUMN_EVALUATION_DATE, evaluation.getDate());
        values.put(COLUMN_EVALUATION_IMAGE, evaluation.getImage());
        values.put(COLUMN_EVALUATION_LOGIN_USER, evaluation.getLogin());
        values.put(COLUMN_EVALUATION_NOM_RECETTE, evaluation.getNom());
        values.put(COLUMN_EVALUATION_NOTE, evaluation.getNote());

        db.insert(TABLE_EVALUATION, null, values);
        db.close();
    }

    /**
     * Methode qui retourne tous les utilisateurs
     *
     * @return list
     */
    public List<User> getAllUser(){
        //Les colonnes à retourner
        String[] columns = {
                COLUMN_USER_LOGIN,
                COLUMN_USER_NOM,
                COLUMN_USER_PRENOM,
                COLUMN_USER_MAIL,
                COLUMN_USER_MDP,
                COLUMN_USER_NAISSANCE,
                COLUMN_USER_SEXE,
                COLUMN_USER_LANGUE,
                COLUMN_USER_PHOTO
        };
        //ordre de tri
        String sortOrder = COLUMN_USER_NOM + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        //requête
        Cursor cursor = db.query(TABLE_USER, //Nom de la table
                columns,    //colonnes à retourner
                null,        //colonnes pour le WHERE
                null,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                User user = new User();
                user.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LOGIN)));
                user.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NOM)));
                user.setPrenom(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PRENOM)));
                user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MAIL)));
                user.setMdp(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MDP)));
                user.setLangue(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LANGUE)));
                user.setNaissance(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_NAISSANCE)));
                user.setSexe(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SEXE)));
                user.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_PHOTO)));
                // Ajout de l'utilisateur
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    /**
     *
     * @param email
     * @return L'utilisateur donc l'email est email
     */
    public User getUser(String email){
        String[] columns = {
                COLUMN_USER_LOGIN,
                COLUMN_USER_NOM,
                COLUMN_USER_PRENOM,
                COLUMN_USER_MAIL,
                COLUMN_USER_MDP,
                COLUMN_USER_NAISSANCE,
                COLUMN_USER_SEXE,
                COLUMN_USER_LANGUE,
                COLUMN_USER_PHOTO
        };

        String sortOrder = COLUMN_USER_NOM + " ASC";
        User user = new User();

        SQLiteDatabase db = this.getReadableDatabase();

        //critères de sélection
        String selection = COLUMN_USER_MAIL + " = ?";

        //valeurs des critères de sélection
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        if(cursor.getCount() == 0){
            return null;
        }

        cursor.moveToFirst();

        user.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LOGIN)));
        user.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NOM)));
        user.setPrenom(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PRENOM)));
        user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MAIL)));
        user.setMdp(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MDP)));
        user.setLangue(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LANGUE)));
        user.setNaissance(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_NAISSANCE)));
        user.setSexe(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SEXE)));
        user.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_PHOTO)));

        cursor.close();
        db.close();

        return user;
    }

    /**
     * Suppression d'un utilisateur
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Suppression par login
        db.delete(TABLE_USER, COLUMN_USER_LOGIN + " = ?",
                new String[]{String.valueOf(user.getLogin())});
        db.close();
    }

    /**
     * Méthode qui regarde si un utilisateur existe ou pas
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        String[] columns = {
                COLUMN_USER_LOGIN
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_MAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param evaluation
     * @return true si l'utilisateur a laisser une evaluation sur la recette
     */
    public boolean checkEvaluation(Evaluation evaluation){
        String[] columns = {
                COLUMN_EVALUATION_LOGIN_USER
        };

        SQLiteDatabase db = getReadableDatabase();

        String selection = COLUMN_EVALUATION_LOGIN_USER + " = ?" + " AND " + COLUMN_EVALUATION_NOM_RECETTE + " = ?";

        String[] selectionArgs = {evaluation.getLogin(), evaluation.getNom()};

        Cursor cursor = db.query(TABLE_EVALUATION,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Methode qui regarde su l'utilisateur existe ou pas
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        String[] columns = {
                COLUMN_USER_LOGIN
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_MAIL + " = ?" + " AND " + COLUMN_USER_MDP + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Mise à jour d'une valeur d'un user en fonction de la colonne
     * @param user
     * @param column
     */
    public void updateUser(User user, String column){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues args = new ContentValues();

        String selection = COLUMN_USER_LOGIN + " = ?";

        String[] selectionArgs = {user.getLogin()};

        switch(column){
            case "nom":
                args.put(column, user.getNom());
                break;
            case "prenom":
                args.put(column, user.getPrenom());
                break;
            case "mail":
                args.put(column, user.getMail());
                break;
            case "mdp":
                args.put(column, user.getMdp());
                break;
            case "naissance":
                args.put(column, user.getNaissance());
                break;
            case "sexe":
                args.put(column, user.getSexe());
                break;
            case "Langue":
                args.put(column, user.getLangue());
                break;
            case "photo":
                args.put(COLUMN_USER_PHOTO, user.getPhoto());
                break;
        }

        db.update(TABLE_USER,
                args,
                selection,
                selectionArgs);

        db.close();

    }

    /**
     * Met à jour la photo de la recette
     * @param recette
     */
    public void updateRecettePhoto(Recette recette){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();

        String selection = COLUMN_RECETTE_NOM + " = ?";

        String[] selectionArgs = {recette.getNom()};

        args.put(COLUMN_RECETTE_PHOTO, recette.getPhoto());

        db.update(TABLE_RECETTE,
                args,
                selection,
                selectionArgs);

        db.close();
    }

    /**
     * Renvoie toutes les notes d'une recette sous forme de liste
     * @param recette
     * @return List <int>
     */
    public List <Integer> allNotes (Recette recette) {
        List <Integer> notes = new ArrayList <Integer>();
        String[] columns = {COLUMN_EVALUATION_NOTE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EVALUATION_NOM_RECETTE + " = ?";
        String[] selectionArgs = {recette.getNom()};
        Cursor cursor = db.query(TABLE_EVALUATION,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                notes.add(cursor.getInt(cursor.getColumnIndex(COLUMN_EVALUATION_NOTE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    /**
     *
     * @return liste de toutes les recettes
     */
    public List<Recette> getAllRecettes(){
        String[] columns = {
            COLUMN_RECETTE_NOM,
            COLUMN_RECETTE_DESCRIPTION,
            COLUMN_RECETTE_INSTRUCTION,
            COLUMN_RECETTE_TEMPSPREPARATION,
            COLUMN_RECETTE_TEMPSCUISSON,
            COLUMN_RECETTE_TYPE,
            COLUMN_RECETTE_DIFFICULTE,
            COLUMN_RECETTE_DATEAJOUT,
            COLUMN_RECETTE_CREATEUR,
            COLUMN_RECETTE_SOUSTYPE,
            COLUMN_RECETTE_PHOTO
        };

        //ordre de tri
        String sortOrder = COLUMN_USER_NOM + " ASC";
        List<Recette> recetteList = new ArrayList<Recette>();

        SQLiteDatabase db = this.getReadableDatabase();

        //requête
        Cursor cursor = db.query(TABLE_RECETTE, //Nom de la table
                columns,    //colonnes à retourner
                null,        //colonnes pour le WHERE
                null,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                Recette recette = new Recette();
                recette.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_NOM)));
                recette.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_DESCRIPTION)));
                recette.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_INSTRUCTION)));
                recette.setTempsCuisson(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSCUISSON)));
                recette.setTempsPreparation(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSPREPARATION)));
                recette.setType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_TYPE)));
                recette.setDifficulte(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_DIFFICULTE)));
                recette.setDateAjout(cursor.getLong(cursor.getColumnIndex(COLUMN_RECETTE_DATEAJOUT)));
                recette.setCreateur(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_CREATEUR)));
                recette.setSousType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_SOUSTYPE)));
                recette.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_RECETTE_PHOTO)));
                recetteList.add(recette);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recetteList;

    }


    /**
     *
     * @return la liste des 3 dernières recettes sorties
     */
    public List<Recette> getLastThreeRecipe(){
        int count = 0;
        String[] columns = {
                COLUMN_RECETTE_NOM,
                COLUMN_RECETTE_DESCRIPTION,
                COLUMN_RECETTE_INSTRUCTION,
                COLUMN_RECETTE_TEMPSPREPARATION,
                COLUMN_RECETTE_TEMPSCUISSON,
                COLUMN_RECETTE_TYPE,
                COLUMN_RECETTE_DIFFICULTE,
                COLUMN_RECETTE_SOUSTYPE,
                COLUMN_RECETTE_DATEAJOUT,
                COLUMN_RECETTE_CREATEUR,
                COLUMN_RECETTE_PHOTO
        };

        String sortOrder = COLUMN_RECETTE_DATEAJOUT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        List<Recette> recetteList = new ArrayList<Recette>();

        //requête
        Cursor cursor = db.query(TABLE_RECETTE, //Nom de la table
                columns,    //colonnes à retourner
                null,        //colonnes pour le WHERE
                null,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                Recette recette = new Recette();
                recette.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_NOM)));
                recette.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_DESCRIPTION)));
                recette.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_INSTRUCTION)));
                recette.setTempsCuisson(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSCUISSON)));
                recette.setTempsPreparation(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSPREPARATION)));
                recette.setType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_TYPE)));
                recette.setDifficulte(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_DIFFICULTE)));
                recette.setDateAjout(cursor.getLong(cursor.getColumnIndex(COLUMN_RECETTE_DATEAJOUT)));
                recette.setCreateur(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_CREATEUR)));
                recette.setSousType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_SOUSTYPE)));
                recette.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_RECETTE_PHOTO)));
                recetteList.add(recette);
                count++;
            } while (cursor.moveToNext() && count <= 3);
        }
        cursor.close();
        db.close();

        return recetteList;
    }

    /**
     *
     * @param nomRecette
     * @return la liste des trois denrieres evaluation de la recette
     */
    public List<Evaluation> getLastThreeEvaluation(String nomRecette){
        int count = 0;
        String[] columns = {
                COLUMN_EVALUATION_LOGIN_USER,
                COLUMN_EVALUATION_COMMENT,
                COLUMN_EVALUATION_NOTE,
                COLUMN_EVALUATION_IMAGE
        };

        String sortOrder = COLUMN_EVALUATION_DATE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_EVALUATION_NOM_RECETTE + " = ?";

        String[] selectionArgs = {nomRecette};

        List<Evaluation> evalList = new ArrayList<Evaluation>();

        //requête
        Cursor cursor = db.query(TABLE_EVALUATION, //Nom de la table
                columns,    //colonnes à retourner
                selection,        //colonnes pour le WHERE
                selectionArgs,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                Evaluation evaluation = new Evaluation();
                evaluation.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_EVALUATION_LOGIN_USER)));
                evaluation.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_EVALUATION_COMMENT)));
                evaluation.setNote(cursor.getInt(cursor.getColumnIndex(COLUMN_EVALUATION_NOTE)));
                evaluation.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_EVALUATION_IMAGE)));
                evalList.add(evaluation);
                count++;
            } while (cursor.moveToNext() && count <= 3);
        }
        cursor.close();
        db.close();

        return evalList;
    }

    /**
     *
     * @param nom
     * @return
     */
    public Recette getRecette(String nom){
        String[] columns = {
                COLUMN_RECETTE_NOM,
                COLUMN_RECETTE_DESCRIPTION,
                COLUMN_RECETTE_INSTRUCTION,
                COLUMN_RECETTE_TEMPSPREPARATION,
                COLUMN_RECETTE_TEMPSCUISSON,
                COLUMN_RECETTE_TYPE,
                COLUMN_RECETTE_DIFFICULTE,
                COLUMN_RECETTE_SOUSTYPE,
                COLUMN_RECETTE_DATEAJOUT,
                COLUMN_RECETTE_CREATEUR,
                COLUMN_RECETTE_PHOTO
        };

        String sortOrder = COLUMN_RECETTE_DATEAJOUT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        Recette recette = new Recette();

        //requête
        Cursor cursor = db.query(TABLE_RECETTE, //Nom de la table
                columns,    //colonnes à retourner
                null,        //colonnes pour le WHERE
                null,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        cursor.moveToFirst();

        recette.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_NOM)));
        recette.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_DESCRIPTION)));
        recette.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_INSTRUCTION)));
        recette.setTempsCuisson(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSCUISSON)));
        recette.setTempsPreparation(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSPREPARATION)));
        recette.setType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_TYPE)));
        recette.setDifficulte(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_DIFFICULTE)));
        recette.setDateAjout(cursor.getLong(cursor.getColumnIndex(COLUMN_RECETTE_DATEAJOUT)));
        recette.setCreateur(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_CREATEUR)));
        recette.setSousType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_SOUSTYPE)));
        recette.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_RECETTE_PHOTO)));

        cursor.close();
        db.close();

        return recette;
    }

    /**
     *
     * @param currentTime
     * @return les recettes des 7 derniers jours
     */
    public List<Recette> getLastWeekRecette(long currentTime){
        String[] columns = {
                COLUMN_RECETTE_NOM,
                COLUMN_RECETTE_DESCRIPTION,
                COLUMN_RECETTE_INSTRUCTION,
                COLUMN_RECETTE_TEMPSPREPARATION,
                COLUMN_RECETTE_TEMPSCUISSON,
                COLUMN_RECETTE_TYPE,
                COLUMN_RECETTE_DIFFICULTE,
                COLUMN_RECETTE_SOUSTYPE,
                COLUMN_RECETTE_DATEAJOUT,
                COLUMN_RECETTE_CREATEUR,
                COLUMN_RECETTE_PHOTO
        };

        String sortOrder = COLUMN_RECETTE_DATEAJOUT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        List<Recette> recetteList = new ArrayList<Recette>();

        String selection = COLUMN_RECETTE_DATEAJOUT + "> ?";

        long l = currentTime - 604800000;

        String[] selectionArgs = {String.valueOf(l)};

        //requête
        Cursor cursor = db.query(TABLE_RECETTE, //Nom de la table
                columns,    //colonnes à retourner
                selection,        //colonnes pour le WHERE
                selectionArgs,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                Recette recette = new Recette();
                recette.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_NOM)));
                recette.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_DESCRIPTION)));
                recette.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_INSTRUCTION)));
                recette.setTempsCuisson(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSCUISSON)));
                recette.setTempsPreparation(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSPREPARATION)));
                recette.setType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_TYPE)));
                recette.setDifficulte(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_DIFFICULTE)));
                recette.setDateAjout(cursor.getLong(cursor.getColumnIndex(COLUMN_RECETTE_DATEAJOUT)));
                recette.setCreateur(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_CREATEUR)));
                recette.setSousType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_SOUSTYPE)));
                recette.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_RECETTE_PHOTO)));
                recetteList.add(recette);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recetteList;
    }

    /**
     * recherche par type de recette
     */
    public List<Recette> searchType(String type) {
        String[] columns = {
                COLUMN_RECETTE_NOM,
                COLUMN_RECETTE_DESCRIPTION,
                COLUMN_RECETTE_INSTRUCTION,
                COLUMN_RECETTE_TEMPSPREPARATION,
                COLUMN_RECETTE_TEMPSCUISSON,
                COLUMN_RECETTE_TYPE,
                COLUMN_RECETTE_DIFFICULTE,
                COLUMN_RECETTE_SOUSTYPE,
                COLUMN_RECETTE_DATEAJOUT,
                COLUMN_RECETTE_CREATEUR,
                COLUMN_RECETTE_PHOTO
        };
        String sortOrder = COLUMN_USER_NOM + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        List<Recette> recetteList = new ArrayList<Recette>();

        String selection = COLUMN_RECETTE_TYPE + " = ?";

        String[] selectionArgs = {type};

        Cursor cursor = db.query(TABLE_RECETTE, //Nom de la table
                columns,    //colonnes à retourner
                selection,        //colonnes pour le WHERE
                selectionArgs,        //Valeurs pour le WHERE
                null,
                null,
                sortOrder); //ordre de tri

        if(cursor.moveToFirst()){
            do {
                Recette recette = new Recette();
                recette.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_NOM)));
                recette.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_DESCRIPTION)));
                recette.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_INSTRUCTION)));
                recette.setTempsCuisson(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSCUISSON)));
                recette.setTempsPreparation(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_TEMPSPREPARATION)));
                recette.setType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_TYPE)));
                recette.setDifficulte(cursor.getInt(cursor.getColumnIndex(COLUMN_RECETTE_DIFFICULTE)));
                recette.setDateAjout(cursor.getLong(cursor.getColumnIndex(COLUMN_RECETTE_DATEAJOUT)));
                recette.setCreateur(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_CREATEUR)));
                recette.setSousType(cursor.getString(cursor.getColumnIndex(COLUMN_RECETTE_SOUSTYPE)));
                recette.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_RECETTE_PHOTO)));
                recetteList.add(recette);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recetteList;
    }

    /**
     * Recherche de recette en fonction des paramettres
     * @param keyword
     * @param type
     * @param subtype
     * @return la liste des recettes trouvées
     */
    public List<Recette> search(String keyword, String type, String subtype) {
        List<Recette> firstList = new ArrayList<Recette>();

        firstList = (ArrayList<Recette>)getAllRecettes();

        List<Recette> secondList = new ArrayList<Recette>();

        if (!keyword.isEmpty()) {
            for (Recette recette :
                    firstList) {
                if (recette.getNom().contains(keyword)) {
                    secondList.add(recette);
                }
            }
        } else {
            secondList = firstList;
        }

        List<Recette> thirdList = new ArrayList<Recette>();

        if (!type.isEmpty()) {
            for (Recette recette :
                    secondList) {
                if (recette.getType().contains(type)) {
                    thirdList.add(recette);
                }
            }
        } else {
            thirdList = secondList;
        }

        List<Recette> fourthList = new ArrayList<Recette>();

        if (!subtype.isEmpty()) {
            for (Recette recette :
                    thirdList) {
                if (recette.getSousType().contains(subtype)) {
                    fourthList.add(recette);
                }
            }
        } else {
            fourthList = thirdList;
        }

        return fourthList;
    }


    /**
     *
     * Trouver ingrédients recette
     *
     */
    public List<Necessite> getNecessite(String nom) {

        String[] columns = {
                COLUMN_NECESSITE_NOM_RECETTE,
                COLUMN_NECESSITE_LIBELLEING,
                COLUMN_NECESSITE_QUANTITE,
                COLUMN_NECESSITE_UNITE
        };

        String sortOrder = COLUMN_NECESSITE_LIBELLEING + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Necessite> necessiteList = new ArrayList<Necessite>();

        String selection = COLUMN_NECESSITE_NOM_RECETTE + " = ?";

        String[] selectionArgs = {nom};

        Cursor cursor = db.query(TABLE_NECESSITE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        if(cursor.moveToFirst()){
            do {
                Necessite necessite = new Necessite();
                necessite.setIdRecette(cursor.getString(cursor.getColumnIndex(COLUMN_NECESSITE_NOM_RECETTE)));
                necessite.setLibelleIng(cursor.getString(cursor.getColumnIndex(COLUMN_NECESSITE_LIBELLEING)));
                necessite.setQuantite(cursor.getDouble(cursor.getColumnIndex(COLUMN_NECESSITE_QUANTITE)));
                necessite.setUnite(cursor.getString(cursor.getColumnIndex(COLUMN_NECESSITE_UNITE)));

                necessiteList.add(necessite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return necessiteList;
    }

}

