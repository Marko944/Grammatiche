package grammatica.struttura;

import java.util.ArrayList;

import grammatica.utility.Tripla;

public class InsiemeProd {
	private ArrayList<Tripla> produzioni;
	
	/** Insieme vuoto */
	public InsiemeProd() {
		produzioni = new ArrayList<Tripla>();
	}
	
	/** Insieme con una sola produzione */
	public InsiemeProd(Simbolo sx, Stringa dx) throws Exception {
		produzioni = new ArrayList<Tripla>();
		produzioni.add(new Tripla(sx, new Produzione(sx, dx)));
	}
	
	/** Insieme con più produzioni, senza duplicati */
	public InsiemeProd(ArrayList<Produzione> produzioni) throws Exception {
		this.produzioni = new ArrayList<Tripla>();
		if(produzioni.isEmpty())
			throw new Exception("Non puoi creare un insieme di produzioni vuoto");
		produzioni = eliminaDoppioni(produzioni);
		for(Produzione p : produzioni)
			addProd(p.getSX(), p.getDX());
	}
	
	/** Aggiungi una sola produzione, senza duplicati */
	public void addProd(Simbolo sx, Stringa dx) throws Exception {
		boolean aggiunto = false;
		for(Tripla t : this.produzioni) {
			if(t.getSx().equals(sx)) {
				t.addProd(new Produzione(sx, dx));
				aggiunto = true;
				break;
			}
		}
		if(!aggiunto)
			this.produzioni.add(new Tripla(sx, new Produzione(sx, dx)));
	}
	
	/** Rimuovi una produzione e la ritorna */
	public void remProd(Produzione p) throws Exception {
		for(Tripla t : produzioni) {
			if(t.getSx().getNome().equals(p.getSX().getNome()))
				t.remProd(p);
		}
	}
	
	/** Return true se la produzione esiste nell'insieme */
	public boolean hasProd(Produzione p) {
		for(Tripla t : produzioni) {
			if(t.getSx().getNome().equals(p.getSX().getNome()))
				if(t.hasProd(p))
					return true;
		}
		return false;
	}
	
	/** Ritorna true se l'insieme di produzioni è vuoto */
	public boolean isEmpty() {
		return produzioni.isEmpty();
	}
	
	/**
	 * @return ArrayList delle triple
	 */
	public ArrayList<Tripla> getTriplaProduzioni() {
		return produzioni;
	}
	
	@Override
	public String toString() {
		/* Stampa le produzioni in modo condensato */
		String ritorno = "";
		for(Tripla t : produzioni) {
			ritorno += t.toString();
		}
		return ritorno;
	}
	
	@Override
	public InsiemeProd clone() {
		ArrayList<Tripla> temp = new ArrayList<Tripla>();
		for(Tripla t : produzioni)
			temp.add(t.clone());
		InsiemeProd tempP = new InsiemeProd();
		tempP.produzioni = temp;
		return tempP;
	}
	
	/** Elimina in un ArrayList di produzioni tutti i doppioni */
	private ArrayList<Produzione> eliminaDoppioni(ArrayList<Produzione> pippo) {
		ArrayList<Produzione> ritorno = new ArrayList<Produzione>();
		for(Produzione p : pippo)
			if(!ritorno.contains(p))
				ritorno.add(p);
		return ritorno;
	}
}
