package util;
public enum NomTuile {
	LE_PONT_DES_ABIMES(1),
	LA_PORTE_DE_BRONZE(2),
	LA_CAVERNE_DES_OMBRES(3),
	LA_PORTE_DE_FER(4),
	LA_PORTE_D_OR(5),
	LES_FALAISES_DE_L_OUBLI(6),
	LE_PALAIS_DE_CORAIL(7),
	LA_PORTE_D_ARGENT(8),
	LES_DUNES_DE_L_ILLUSION(9),
	HELIPORT(10),
	LA_PORTE_DE_CUIVRE(11),
	LE_JARDIN_DES_HURLEMENTS(12),
	LA_FORET_POURPRE(13),
	LE_LAGON_PERDU(14),
	LE_MARAIS_BRUMEUX(15),
	OBSERVATOIRE(16),
	LE_ROCHER_FANTOME(17),
	LA_CAVERNE_DU_BRASIER(18),
	LE_TEMPLE_DU_SOLEIL(19),
	LE_TEMPLE_DE_LA_LUNE(20),
	LE_PALAIS_DES_MAREES(21),
	LE_VAL_DU_CREPUSCULE(22),
	LA_TOUR_DU_GUET(23),
	LE_JARDIN_DES_MURMURES(24);
        
        private int nbTuile;
        
        NomTuile(int nb){
            this.nbTuile = nb;
        }
        
        public static NomTuile getFromNb(int nb){
            if (LE_PONT_DES_ABIMES.nbTuile==nb){
                return LE_PONT_DES_ABIMES;
            } else if (LA_PORTE_DE_BRONZE.nbTuile==nb){
                return LA_PORTE_DE_BRONZE;
            } else if (LA_CAVERNE_DES_OMBRES.nbTuile==nb) {
                return LA_CAVERNE_DES_OMBRES;
            } else if (LA_PORTE_DE_FER.nbTuile==nb) {
                return LA_PORTE_DE_FER;
            } else if (LA_PORTE_D_OR.nbTuile==nb) {
                return LA_PORTE_D_OR;
            } else if (LES_FALAISES_DE_L_OUBLI.nbTuile==nb) {
                return LES_FALAISES_DE_L_OUBLI;
            } else if (LE_PALAIS_DE_CORAIL.nbTuile==nb) {
                return LE_PALAIS_DE_CORAIL;
            } else if (LA_PORTE_D_ARGENT.nbTuile==nb) {
                return LA_PORTE_D_ARGENT;
            } else if (LES_DUNES_DE_L_ILLUSION.nbTuile==nb) {
                return LES_DUNES_DE_L_ILLUSION;
            } else if (HELIPORT.nbTuile==nb) {
                return HELIPORT;
            } else if (LA_PORTE_DE_CUIVRE.nbTuile==nb) {
                return LA_PORTE_DE_CUIVRE;
            } else if (LE_JARDIN_DES_HURLEMENTS.nbTuile==nb) {
                return LE_JARDIN_DES_HURLEMENTS;
            } else if (LA_FORET_POURPRE.nbTuile==nb) {
                return LA_FORET_POURPRE;
            } else if (LE_LAGON_PERDU.nbTuile==nb) {
                return LE_LAGON_PERDU;
            } else if (LE_MARAIS_BRUMEUX.nbTuile==nb) {
                return LE_MARAIS_BRUMEUX;
            } else if (OBSERVATOIRE.nbTuile==nb) {
                return OBSERVATOIRE;
            } else if (LE_ROCHER_FANTOME.nbTuile==nb) {
                return LE_ROCHER_FANTOME;
            } else if (LA_CAVERNE_DU_BRASIER.nbTuile==nb) {
                return LA_CAVERNE_DU_BRASIER;
            } else if (LE_TEMPLE_DU_SOLEIL.nbTuile==nb) {
                return LE_TEMPLE_DU_SOLEIL;
            } else if (LE_TEMPLE_DE_LA_LUNE.nbTuile==nb) {
                return LE_TEMPLE_DE_LA_LUNE;
            } else if (LE_PALAIS_DES_MAREES.nbTuile==nb) {
                return LE_PALAIS_DES_MAREES;
            } else if (LE_VAL_DU_CREPUSCULE.nbTuile==nb) {
                return LE_VAL_DU_CREPUSCULE;
            } else if (LA_TOUR_DU_GUET.nbTuile==nb) {
                return LA_TOUR_DU_GUET;
            } else if (LE_JARDIN_DES_MURMURES.nbTuile==nb) {
                return LE_JARDIN_DES_MURMURES;
            } else {
                System.err.println("Aucun enum NomTuile de cet entier.");
                return null;
            }
        }
}