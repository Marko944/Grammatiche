package grammatica.struttura;

public abstract class Simbolo {
	
	protected String nome;
	
	public Simbolo(String nome) {
		this.nome = nome;
	}
	
	/** Ritorna true se il simbolo è terminale, false altrimenti */
	public abstract boolean isTerminale();
	
	/**Ritorna il nome identificativo del simbolo */
	public String getNome() {
		return nome;
	}
	
	/** Ritorna true se precedo in ordine lessicografico s */
	public boolean precede(Simbolo s) {
		int lunghezzaMia = nome.length();
		int lunghezzaSua = s.getNome().length();
		boolean sonoPiuCorto = (lunghezzaMia<=lunghezzaSua);
		char a=0, b=0;
		for(int i=0; i<(sonoPiuCorto ? lunghezzaMia : lunghezzaSua); ++i) {
			a = nome.charAt(i);
			b = s.getNome().charAt(i);
			if(a>b)
				return false;
		}
		return sonoPiuCorto;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!((o instanceof SimboloTerminale) || o instanceof SimboloVariabile || o instanceof Epsilon)) {
            return false;
        }
		Simbolo s = (Simbolo) o;
		if(nome.equals(s.getNome()))
			return true;
		return false;
	}
	
}
