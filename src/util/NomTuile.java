package util;
public enum NomTuile {
	LE_PONT_DES_ABIMES(1,"le pont des abimes"),
	LA_PORTE_DE_BRONZE(2,"la porte de bronze"),
	LA_CAVERNE_DES_OMBRES(3,"la caverne des ombres"),
	LA_PORTE_DE_FER(4,"la porte de fer"),
	LA_PORTE_D_OR(5,"la porte d or"),
	LES_FALAISES_DE_L_OUBLI(6,"les falaises de l oubli"),
	LE_PALAIS_DE_CORAIL(7,"le palais de corail"),
	LA_PORTE_D_ARGENT(8,"la porte d argent"),
	LES_DUNES_DE_L_ILLUSION(9,"les dunes de l illusion"),
	HELIPORT(10,"heliport"),
	LA_PORTE_DE_CUIVRE(11,"la porte de cuivre"),
	LE_JARDIN_DES_HURLEMENTS(12,"le jardin des hurlements"),
	LA_FORET_POURPRE(13,"la foret pourpre"),
	LE_LAGON_PERDU(14,"le lagon perdu"),
	LE_MARAIS_BRUMEUX(15,"les marais brumeux"),
	OBSERVATOIRE(16,"observatoire"),
	LE_ROCHER_FANTOME(17,"le rocher fantome"),
	LA_CAVERNE_DU_BRASIER(18,"la caverne du brasier"),
	LE_TEMPLE_DU_SOLEIL(19,"le temple du soleil"),
	LE_TEMPLE_DE_LA_LUNE(20,"le temple de la lune"),
	LE_PALAIS_DES_MAREES(21,"le palais des marées"),
	LE_VAL_DU_CREPUSCULE(22,"le val du crepuscule"),
	LA_TOUR_DU_GUET(23,"la tour du guet"),
	LE_JARDIN_DES_MURMURES(24,"le jardin des murmures");
        
        private int nbTuile;
        private String nomTuile;
        
        NomTuile(int nb, String nom){
            this.nbTuile = nb;
            this.nomTuile = nom;
        }
        
        @Override
        public String toString() {
            return this.nomTuile;
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