package grammatica.struttura;

public class Epsilon extends Simbolo {
	
	public Epsilon() {
		super("epsilon");
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
		if (!(o instanceof Epsilon)) {
            return false;
        }
		Epsilon s = (Epsilon) o;
		if(nome.equals(s.getNome()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "epsilon";
	}
}
