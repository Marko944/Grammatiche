package grammatica.struttura;

public class SimboloTerminale extends Simbolo {
	
	public SimboloTerminale(String nome) {
		super(nome);
	}

	@Override
	public boolean isTerminale() {
		return true;
	} 
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof SimboloTerminale)) {
            return false;
        }
		SimboloTerminale s = (SimboloTerminale) o;
		if(nome.equals(s.getNome()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
