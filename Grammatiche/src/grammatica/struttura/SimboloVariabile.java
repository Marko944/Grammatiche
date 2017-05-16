package grammatica.struttura;

public class SimboloVariabile extends Simbolo {
	
	public SimboloVariabile(String nome) {
		super(nome);
	}

	@Override
	public boolean isTerminale() {
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof SimboloVariabile)) {
            return false;
        }
		SimboloVariabile s = (SimboloVariabile) o;
		if(nome.equals(s.getNome()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
