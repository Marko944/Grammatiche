package grammatica.utility;

import java.util.ArrayList;

import grammatica.struttura.Produzione;
import grammatica.struttura.Simbolo;

public class Tripla {
	private Simbolo sx;
	private ArrayList<Produzione> prod;
	private int counter;
	
	/**
	 * Costruttore di Tripla con un ArrayList di produzioni, con il check ai duplicati sull'input.
	 * @param variabileSinistra
	 * @param insiemeProduzioni
	 * @throws Exception Se variabileSinistra non è un SimboloVariabile oppure se ci sono duplicati.
	 */
	public Tripla(Simbolo sx, ArrayList<Produzione> prod) throws Exception {
		if(sx.isTerminale())
			throw new Exception("Non puoi avere un terminale a sinistra di una produzione");
		if(hasDuplicati(prod))
			throw new Exception("Non puoi creare un insieme di produzioni con duplicati.");
		this.sx = sx;
		this.prod = prod;
		this.counter = prod.size();
	}
	
	/**
	 * Costruttore con una sola produzione.
	 * @param variabileSinistra
	 * @param produzione
	 * @throws Exception Se variabileSinistranon è un SimboloVariabile.
	 */
	public Tripla(Simbolo sx, Produzione produzione) throws Exception {
		if(sx.isTerminale())
			throw new Exception("Non puoi avere un terminale a sinistra di una produzione");
		this.sx = sx;
		this.prod = new ArrayList<Produzione>();
		this.prod.add(produzione);
		this.counter = 1;
	}

	
	/**
	 * Aggiunge alla tripla una produzione che ha uguale VarSX, controlla i duplicati.
	 * @param produzione
	 * @throws Exception Se p.getSX() è diverso dalla variabileSX oppure se è un duplicato.
	 */
	public void addProd(Produzione produzione) throws Exception {
		if(hasDuplicati(produzione))
			throw new Exception("Non puoi aggiungere una produzione già esistente.");
		if(!produzione.getSX().equals(sx))
			throw new Exception("Non puoi aggiungere una produzione con un elemento sinistro differente a questa tripla.");
		prod.add(produzione);
		counter++;
	}
	
	
	/**
	 * Rimuove una produzione dalla tripla.
	 * @param produzione
	 * @return Produzione rimossa
	 * @throws Exception Se il parametro non esiste nella tripla.
	 */
	public Produzione remProd(Produzione produzione) throws Exception {
		if(!prod.contains(produzione))
			throw new Exception("Non puoi rimuovere una produzione che non appartiene all'insieme.");
		counter--;
		return prod.remove(prod.indexOf(produzione));
	}
	
	
	/**
	 * Ritorna vero se la produzione è contenuta nella tripla.
	 * @param produzione
	 * @return true se la produzione è contenuta nella tripla, false altrimenti
	 */
	public boolean hasProd(Produzione produzione) {
		if(prod.contains(produzione))
			return true;
		return false;
	}
	
	/**
	 * @return Simbolo che corrisponde alla variabileSX
	 */
	public Simbolo getSx() {
		return sx;
	}

	/**
	 * @return ArrayList che contiene le produzioni
	 */
	public ArrayList<Produzione> getProd() {
		return prod;
	}

	/**
	 * @return int contatore di quante produzioni sono contenute nella tripla
	 */
	public int getCounter() {
		return counter;
	}
	
	@Override
	public String toString() {
		String ritorno = "";
		//ritorno += "la variabile "+sx.getNome()+" ha "+counter+" produzioni"+"\n";
		ritorno += sx.getNome()+" --> ";
		int contatore = 1;
		for(Produzione p : prod) {
			if(contatore!=counter)
				ritorno += p.getDX().toString()+" | ";
			if(contatore==counter)
				ritorno += p.getDX().toString()+"\n";
			contatore++;
		}

		return ritorno;
	}
	
	@Override
	public Tripla clone() {
		Simbolo tempS;
		ArrayList<Produzione> tempP = new ArrayList<Produzione>();
		tempS = sx;
		for(Produzione p : prod) {
			tempP.add(p.clone());
		}
		
		Tripla ret = null;
		try {
			ret = new Tripla(tempS, tempP);
		} catch (Exception e) {
			//NON DOVRESTI ESSERCI QUA
			e.printStackTrace();
		}
		return ret;
	}

	private boolean hasDuplicati(Produzione p) {
		for(Produzione x : prod)
			if(x.getSX().equals(p.getSX()) && x.getDX().equals(p.getDX()))
				return true;
		return false;
	}
	
	private boolean hasDuplicati(ArrayList<Produzione> x) {
		for (Produzione y : x) {
			for(Produzione z : x) {
				if(y.getSX().equals(z.getSX()) && y.getDX().equals(z.getDX()))
					return true;
			}
		}
		return false;
	}
}
