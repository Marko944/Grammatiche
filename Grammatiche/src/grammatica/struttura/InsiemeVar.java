package grammatica.struttura;

import java.util.ArrayList;

public class InsiemeVar {
	private ArrayList<Simbolo> variabili;
	
	/** Insieme vuoto */
	public InsiemeVar() {
		variabili = new ArrayList<Simbolo>();
	}
	
	/** Insieme con una sola variabile */
	public InsiemeVar(Simbolo var) {
		variabili.add(var);
	}
	
	/** Insieme con più variabili, senza duplicati */
	public InsiemeVar(ArrayList<Simbolo> variabili) throws Exception {
		if(!hasDuplicati(variabili))
			throw new Exception();
		this.variabili = variabili;
	}
	
	/** Aggiungi una sola variabile, senza duplicati */
	public void addVar(Simbolo s) throws Exception {
		if(hasDuplicati(s))
			throw new Exception();
		variabili.add(s);
	}
	
	/** Aggiungi più variabili, senza duplicati */
	public void addVar(ArrayList<Simbolo> s) throws Exception {
		if(!hasDuplicati(s))
			throw new Exception("L'array che hai passato come argomento contiene duplicati");
		for(Simbolo x : s) {
			if(hasDuplicati(x))
				throw new Exception("L'array che hai passato come argomento ha elementi in comune con l'insieme");
			variabili.add(x);
		}
	}
	
	/** Rimuovi una variabile e la ritorna */
	public Simbolo remVar(Simbolo s) throws Exception {
		int t=0;
		for (Simbolo y : variabili) {
			if(y.equals(s))
				return variabili.remove(t);
			t++;
		}
		throw new Exception("La variabile "+s+" non esiste nell'insieme.");
	}
	
	/** @return Vero se il simbolo esiste nell'insieme */
	public boolean hasVar(Simbolo s) {
		return hasDuplicati(s);
	}
	
	/**
	 * @return ArrayList delle variabili
	 */
	public ArrayList<Simbolo> getSimboli() {
		return variabili;
	}
	
	/**
	 * @return Vero se l'insieme è vuoto
	 */
	public boolean isEmpty() {
		return variabili.isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof InsiemeVar)) {
            return false;
        }
		InsiemeVar insiemeVar = (InsiemeVar) o;
		return (variabili.containsAll(insiemeVar.getSimboli()) && insiemeVar.getSimboli().containsAll(variabili)) ? true : false;
	}
	
	@Override
	public String toString() {
		String ritorno = "";
		ritorno += "{ ";
		int cont=0;
		for(Simbolo s : variabili) {
			if(cont!=variabili.size()-1)
				ritorno += s+", ";
			else
				ritorno += s;
			cont++;
		}
		ritorno += " }";
		return ritorno;
	}
	
	@Override
	public InsiemeVar clone() {
		ArrayList<Simbolo> temp = new ArrayList<Simbolo>();
		for(Simbolo s : variabili) {
			if(s.isTerminale() && !(s instanceof Epsilon))
				temp.add(new SimboloTerminale(s.getNome()));
			else if(s instanceof Epsilon)
				temp.add(new Epsilon());
			else
				temp.add(new SimboloVariabile(s.getNome()));
		}
		InsiemeVar ret = null;
		
		try {
			ret = new InsiemeVar(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private boolean hasDuplicati(Simbolo s) {
		for(Simbolo x : variabili)
			if(x.equals(s))
				return true;
		return false;
	}
	
	private boolean hasDuplicati(ArrayList<Simbolo> x) {
		for (Simbolo y : x) {
			for(Simbolo z : x) {
				if(y.equals(z))
					return true;
			}
		}
		return false;
	}
}
