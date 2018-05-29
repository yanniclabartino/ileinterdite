package ileinterdite;
public interface Observe {
    
	/**
	 * 
	 * @param o
	 */
	void addObservateur(Observateur o);

	/**
	 * 
	 * @param m
	 */
	void notifierObservateur(Message m);

}