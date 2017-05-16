package grammatica.struttura;

public class Produzione {
	private Simbolo varSX;
	private Stringa varDX;
	
	/** Crea una produzione a partire dal simboloVariabile a sinistra e da una Stringa a destra */
	public Produzione(Simbolo varSX, Stringa varDX) throws Exception {
		if(varSX.isTerminale())
			throw new Exception();
		this.varSX = varSX;
		this.varDX = varDX;
	}
	
	/** Ritorna vero se la stringa a destra è vuota*/
	public boolean isStringaVuota() {
		return varDX.isEmpty();
	}
	
	/** Ritorna il simboloVariabile a sinistra della produzione */
	public Simbolo getSX() {
		return varSX;
	}
	
	/** Ritorna la Stringa a destra della produzione */
	public Stringa getDX() {
		return varDX;
	}
	
	/** return true se precedo in ordine lessicografico p */
	public boolean precede(Produzione p) {
		boolean precedoIo = varSX.precede(p.varSX);
		if(!(varSX.equals(p.getSX()))) {
			return varDX.precede(p.getDX());
		}
		return precedoIo;
	}
	
	@Override
	public String toString() {
		return varSX+" --> "+varDX;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof Produzione)) {
            return false;
        }
		Produzione p = (Produzione) o;
		if(!varSX.equals(p.getSX()))
			return false;
		if(!varDX.equals(p.getDX()))
			return false;
		return true;
	}
	
	@Override
	public Produzione clone() {
		Produzione temp = null;
		try {
			temp = new Produzione(varSX, varDX.clone());
		} catch (Exception e) {
			// NON DOVRESTI MAI ARRIVARE QUA
			e.printStackTrace();
		}
		return temp;
	}
}
