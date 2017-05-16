package grammatica.struttura;

import java.util.ArrayList;

public class Stringa {
	private ArrayList<Simbolo> simboli;
	
	/** Crea una stringa vuota */
	public Stringa() {
		simboli = new ArrayList<Simbolo>();
	}
	
	/** Crea una stringa con un solo simbolo */
	public Stringa(Simbolo s) {
		simboli = new ArrayList<Simbolo>();
		simboli.add(s);
	}
	
	/** Crea una stringa a partire da un ArrayList di simboli */
	public Stringa(ArrayList<Simbolo> s) {
		simboli = s;
	}
	
	/** Ritorna vero se la stringa è vuota */
	public boolean isEmpty() {
		return simboli.isEmpty();
	}
	
	/** Aggiunge un simbolo alla fine della stringa */
	public void addCoda(Simbolo x) {
		simboli.add(x);
	}
	
	/** Aggiunge un simbolo all'inizio della stringa */
	public void addTesta(Simbolo x) {
		simboli.add(0,x);
	}
	
	/** Rinuove e ritorna la fine della stringa*/
	public Simbolo popCoda() {
		if(!simboli.isEmpty())
			return simboli.remove(simboli.size());
		else return new Epsilon();
	}
	
	/** Rimuove e ritorna l'inizio della stringa */
	public Simbolo popTesta() {
		if(!simboli.isEmpty())
			return simboli.remove(0);
		else return new Epsilon();
	}
	
	/** Rimuove e ritorna il simbolo dato */
	public Simbolo popSimbolo(Simbolo simbolo) {
		return simboli.remove(simboli.indexOf(simbolo));
	}
	
	/** Ritorna il simbolo all'indice dato */
	public Simbolo getSimboloAt(int index) {
		return simboli.get(index);
	}
	
	/** Ritorna l'ArrayList dei simboli */
	public ArrayList<Simbolo> getSimboli() {
		return simboli;
	}
	
	/** Ritorna vero se la stringa contiene il simbolo dato come parametro. */
	public boolean hasSimbolo(Simbolo simbolo) {
		for(Simbolo temp : simboli)
			if(temp.equals(simbolo))
				return true;
		return false;
	}
	
	/** Ritorna vero se la stringa contiene solo terminali. */
	public boolean isTerminale() {
		for(Simbolo temp : simboli)
			if(!temp.isTerminale())
				return false;
		return true;
	}
	
	/** Ritorna quanti simboli contiene la stringa */
	public int getSize() {
		return simboli.size();
	}
	
	/** Ritorna true se l'oggetto precede in ordine lessicografico l'argomento */
	public boolean precede(Stringa s) {
		int lunghezzaMia = simboli.size();
		int lunghezzaSua = s.getSize();
		boolean sonoPiuCorto = (lunghezzaMia<=lunghezzaSua);
		Simbolo a, b;
		for(int i=0; i<(sonoPiuCorto ? lunghezzaMia : lunghezzaSua); ++i) {
			a = simboli.get(i);
			b = s.getSimboloAt(i);
			if(b.precede(a))
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String ritorno = "";
		if(simboli.size()==0)
			return "epsilon";
		for(int i=0; i<simboli.size(); ++i) {
			ritorno += simboli.get(i);
		}
		return ritorno;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof Stringa)) {
            return false;
        }
		Stringa x = (Stringa) o;
		int t = 0;
		if(simboli.size()!=x.getSize())
			return false;
		for(Simbolo y : simboli) {
			if(!y.equals(x.getSimboloAt(t)))
				return false;
			t++;
		}
		return true;
	}
	
	@Override
	public Stringa clone() {
		ArrayList<Simbolo> temp = new ArrayList<Simbolo>();
		for(Simbolo s : simboli)
			temp.add(s);
		return new Stringa(temp);
	}
}
