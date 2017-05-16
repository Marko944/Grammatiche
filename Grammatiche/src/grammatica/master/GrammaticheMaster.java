package grammatica.master;

import grammatica.struttura.Grammatica;
import grammatica.utility.Parser;

public class GrammaticheMaster {
	
	/** 
	 * debug = 0 :: OFF
	 * debug = 1 :: POCO
	 * debug = 2 :: MOLTO
	 * */
	public static int debug = 0;

	public static void main(String[] args) throws Exception {
		/*ArrayList<Simbolo> vars = new ArrayList<Simbolo>();
		vars.add(new SimboloTerminale("0"));
		vars.add(new SimboloTerminale("1"));
		vars.add(new Epsilon());
		vars.add(new SimboloVariabile("S"));
		vars.add(new SimboloVariabile("B"));
		vars.add(new SimboloVariabile("D"));
		vars.add(new SimboloVariabile("E"));
		vars.add(new SimboloVariabile("C"));
		vars.add(new SimboloVariabile("A"));
		vars.add(new SimboloVariabile("F"));
		
		ArrayList<Produzione> prod = new ArrayList<Produzione>();
		ArrayList<Simbolo> str0S = new ArrayList<Simbolo>();
		ArrayList<Simbolo> strBD = new ArrayList<Simbolo>();
		ArrayList<Simbolo> strE0 = new ArrayList<Simbolo>();
		ArrayList<Simbolo> strA0 = new ArrayList<Simbolo>();
		ArrayList<Simbolo> strBE = new ArrayList<Simbolo>();
		ArrayList<Simbolo> strSE = new ArrayList<Simbolo>();
		str0S.add(new SimboloTerminale("0"));
		str0S.add(new SimboloVariabile("S"));
		strBD.add(new SimboloVariabile("B"));
		strBD.add(new SimboloVariabile("D"));
		strE0.add(new SimboloVariabile("E"));
		strE0.add(new SimboloTerminale("0"));
		strA0.add(new SimboloVariabile("A"));
		strA0.add(new SimboloTerminale("0"));
		strBE.add(new SimboloVariabile("B"));
		strBE.add(new SimboloVariabile("E"));
		strSE.add(new SimboloVariabile("S"));
		strSE.add(new SimboloVariabile("E"));
		
		Produzione S0S = new Produzione(new SimboloVariabile("S"),new Stringa(str0S));
		Produzione SBD = new Produzione(new SimboloVariabile("S"),new Stringa(strBD));
		Produzione SE0 = new Produzione(new SimboloVariabile("S"),new Stringa(strE0));
		Produzione BC = new Produzione(new SimboloVariabile("B"),new Stringa(new SimboloVariabile("C")));
		Produzione Bepsilon = new Produzione(new SimboloVariabile("B"),new Stringa(new Epsilon()));
		Produzione B1 = new Produzione(new SimboloVariabile("B"),new Stringa(new SimboloTerminale("1")));
		Produzione DA0 = new Produzione(new SimboloVariabile("D"),new Stringa(strA0));
		Produzione DS = new Produzione(new SimboloVariabile("D"),new Stringa(new SimboloVariabile("S")));
		Produzione Depsilon = new Produzione(new SimboloVariabile("D"),new Stringa(new Epsilon()));
		Produzione D0 = new Produzione(new SimboloVariabile("D"),new Stringa(new SimboloTerminale("0")));
		Produzione EBE = new Produzione(new SimboloVariabile("E"),new Stringa(strBE));
		Produzione ESE = new Produzione(new SimboloVariabile("E"),new Stringa(strSE));
		Produzione C0 = new Produzione(new SimboloVariabile("C"), new Stringa(new SimboloTerminale("0")));
		Produzione FC = new Produzione(new SimboloVariabile("F"), new Stringa(new SimboloVariabile("C")));
		
		prod.add(S0S);
		prod.add(SBD);
		prod.add(SE0);
		prod.add(BC);
		prod.add(Bepsilon);
		prod.add(B1);
		prod.add(DA0);
		prod.add(DS);
		prod.add(Depsilon);
		prod.add(D0);
		prod.add(EBE);
		prod.add(ESE);
		prod.add(C0);
		prod.add(FC);*/
		
		/*ArrayList<Simbolo> strSA = new ArrayList<Simbolo>();
		strSA.add(new SimboloVariabile("S"));
		strSA.add(new SimboloVariabile("A"));
		
		Produzione Sa = new Produzione(new SimboloVariabile("S"),new Stringa(new SimboloTerminale("a")));
		Produzione Sb = new Produzione(new SimboloVariabile("S"),new Stringa(new SimboloTerminale("b")));
		Produzione Ab = new Produzione(new SimboloVariabile("A"),new Stringa(new SimboloTerminale("b")));
		Produzione AB = new Produzione(new SimboloVariabile("A"),new Stringa(new SimboloTerminale("B")));
		Produzione Ba = new Produzione(new SimboloVariabile("B"),new Stringa(new SimboloTerminale("a")));
		Produzione SA = new Produzione(new SimboloVariabile("S"),new Stringa(new SimboloTerminale("A")));
		Produzione ASA = new Produzione(new SimboloVariabile("A"),new Stringa(strSA));
		
		prod.add(Sa);
		prod.add(Sb);
		prod.add(Ab);
		prod.add(AB);
		prod.add(Ba);
		prod.add(SA);
		prod.add(ASA);
		
		InsiemeVar variabili = new InsiemeVar(vars);
		InsiemeProd produzioni = new InsiemeProd(prod);
		
		Grammatica grammatica = new Grammatica("Test", variabili, produzioni, new SimboloVariabile("S"));*/
		
		Parser parserTest = new Parser("test.txt");
		Grammatica grammatica = parserTest.parse();
		
		grammatica.print();
		Grammatica noEpsilon = grammatica.eliminaEpsilon();
		noEpsilon.print();
		Grammatica noImpr = noEpsilon.eliminaImproduttive();
		noImpr.print();
		
		if(grammatica.isChomsky())
			System.out.println("CHOMSKY");
	}

}
