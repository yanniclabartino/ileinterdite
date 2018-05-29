package ileinterdite;
public interface Observe {

	Observateur getObservateur();

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