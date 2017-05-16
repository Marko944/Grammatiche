package grammatica.struttura;

import java.util.ArrayList;

import grammatica.master.GrammaticheMaster;
import grammatica.utility.Tripla;

public class Grammatica {
	private String nome;
	private InsiemeVar variabili;
	private InsiemeProd produzioni;
	private Simbolo simboloIniziale;
	
	public Grammatica(String nome, InsiemeVar variabili, InsiemeProd produzioni, Simbolo simboloIniziale) throws Exception {
		this.nome = nome;
		this.variabili = variabili;
		this.produzioni = produzioni;
		if(!variabili.hasVar(simboloIniziale))
			throw new Exception("Il simbolo iniziale della grammatica non è contenuto nell'insieme dei simboli.");
		this.simboloIniziale = simboloIniziale;
		try {
			checkConsistenza();
			} catch(Exception e) {
			throw new Exception(e.getMessage());
		}	
	}
	
	/**
	 * Ritorna una grammatica senza epsilon produzioni (Elimina pure S --> epsilon)
	 * @return Grammatica
	 * @throws Exception
	 */
	public Grammatica eliminaEpsilon() throws Exception {
		InsiemeVar annullabili = trovaAnnullabili(new InsiemeVar());
		if(GrammaticheMaster.debug>0)
			System.out.println("Annullabili: "+annullabili+"\n");
		String nome = this.nome+" - No Epsilon";
		InsiemeVar insiemeVar = variabili;
		insiemeVar.remVar(new Epsilon());
		Simbolo simboloIniziale = this.simboloIniziale;
		InsiemeProd insiemeProd = new InsiemeProd();
		boolean flag = false;
		Simbolo daAnnullare = null;
		for(Tripla t : produzioni.getTriplaProduzioni()) {
			for(Produzione p : t.getProd()) {
				for(int i=0; i<p.getDX().getSize(); ++i) {
					flag = false;
					for(Simbolo s : annullabili.getSimboli()) {
						//Nota importante: rimuove al più una var annullabile da una produzione per ora
						if(p.getDX().hasSimbolo(s)) {
							flag = true;
							daAnnullare = s;
						}
					}
					if(flag) {
						//Ora aggiungo la produzione senza il termine annullabile
						Stringa temp = p.getDX().clone();
						temp.popSimbolo(daAnnullare);
						if(!temp.isEmpty())
							try{
								insiemeProd.addProd(p.getSX(), temp);
							} catch(Exception e) {/*Si lamenta delle variabili doppie, ignoro la lamentela*/}
					}
					//In ogni caso aggiungo la produzione come prima se non è epsilon
					if(!p.getDX().hasSimbolo(new Epsilon()))
						try {
							insiemeProd.addProd(p.getSX(), p.getDX());
						} catch(Exception e) {/*Si lamenta delle variabili doppie, ignoro la lamentela*/}
				}
			}
		}
		return new Grammatica(nome, insiemeVar, insiemeProd, simboloIniziale);
	}
	
	/**
	 * Restituisce una Grammatica a partire dall'attuale senza variabili improduttive dal fondo
	 * @return Grammatica senza produzioni non significative a partire dai terminali
	 * @throws Exception
	 */
	public Grammatica eliminaImproduttive() throws Exception {
		String nome = this.nome+" - No Improduttive";
		Simbolo simboloIniziale = this.simboloIniziale;
		InsiemeProd insiemeProd = null;
		try {
			insiemeProd = new InsiemeProd(soloProduttive(null));
		} catch (Exception e) {/* Per entrare qua dovresti aver finito la memoria */}
		InsiemeVar insiemeVar = estraiVariabili(insiemeProd);
		try {
			return new Grammatica(nome, insiemeVar, insiemeProd, simboloIniziale);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	/**
	 * Restituisce solo le produzioni che, a partire dai terminali, arrivano a qualcosa
	 * @param produzioniProduttive prima chiamata con null
	 * @return ArrayList<<Produzione>Produzione>
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Produzione> soloProduttive(ArrayList<Produzione> produzioniProduttive) {
		ArrayList<Produzione> temp = null;
		ArrayList<Produzione> precedente = null;
		//caso base
		if(produzioniProduttive==null) {
			temp = new ArrayList<Produzione>();
			for(Tripla t : produzioni.getTriplaProduzioni())
				for(Produzione p : t.getProd())
					if(p.getDX().isTerminale())
						try {
							temp.add(new Produzione(p.getSX(), p.getDX()));
						} catch (Exception e) {/*Qua ci arrivo solo a memoria piena, non dovrei mai farlo*/}
		}
		//passo
		if(produzioniProduttive!=null) {
			temp = (ArrayList<Produzione>) produzioniProduttive.clone();
			precedente = (ArrayList<Produzione>) produzioniProduttive.clone();
			
			int flag = 0;
			for(Tripla t : produzioni.getTriplaProduzioni())
				for(Produzione p : t.getProd()) {
					flag = 0;
					for(Produzione produttiva : produzioniProduttive) {
						//Se la stringa p.DX contiene SOLO variabili che stanno a sinistra in 'produttiva' faccio ++
						if(p.getDX().hasSimbolo(produttiva.getSX())) {
							//p.DX contiene un simbolo produttivo
							flag++;
						}
					}
					for(Simbolo s : p.getDX().getSimboli())
						if(s.isTerminale())
							flag++;
					if(flag>=p.getDX().getSize())
						try {
							temp.add(new Produzione(p.getSX(), p.getDX()));
						} catch (Exception e) {/*Qua ci arrivo solo a memoria piena, non dovrei mai farlo*/}
				}
		}
		//test convergenza
		temp = eliminaDoppioni(temp);
		if(GrammaticheMaster.debug>1) //debug
			System.out.println(temp); //debug
		if(arrayProduzioniUguali(precedente,temp))
			return temp;
		//chiamata ricorsiva
		return soloProduttive(temp);
	}
	
	/**
	 * @return Vero se la grammatica è nella forma normale di Chomsky
	 */
	public boolean isChomsky() {
		for(Tripla t : produzioni.getTriplaProduzioni()) {
			for(Produzione p : t.getProd()) {
				Stringa str = p.getDX();
				if(str.getSize()==1 && !str.getSimboloAt(0).isTerminale())
					return false;
				if(str.getSize()==2 && (str.getSimboloAt(0).isTerminale() || str.getSimboloAt(1).isTerminale()))
					return false;
				if(str.getSize()>2)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Stampa la grammatica
	 */
	public void print() {
		System.out.println(nome+":");
		System.out.println(produzioni);
	}
	
	/**
	 * Trova tutte le variabili annullabili (l'insieme di ritorno non contiene epsilon) 
	 * @param insiemeVar Insieme di variabili
	 * @return InsiemeVar Insieme di variabili
	 * @throws Exception
	 */
	private InsiemeVar trovaAnnullabili(InsiemeVar insiemeVar) {
		//caso base
		if(insiemeVar.isEmpty())
			try {
				insiemeVar.addVar(new Epsilon());
			} catch (Exception e1) {/*Se arrivi qua la memoria è piena, non dovresti arrivarci*/}
		//test convergenza pt1
		InsiemeVar precedente = insiemeVar.clone();
		InsiemeVar temp = insiemeVar.clone();
		//alg
		for(Tripla t : produzioni.getTriplaProduzioni()) {
			for(Produzione p : t.getProd()) {
				for(Simbolo s : insiemeVar.getSimboli()) {
					if(p.getDX().hasSimbolo(s))
						try{
							temp.addVar(p.getSX());
						} catch(Exception e) {/*Si lamenta delle variabili doppie, ignoro la lamentela*/}
				}
			}
		}
		//test convergenza pt2
		if(precedente.equals(temp)) {
			try {
				temp.remVar(new Epsilon());
			} catch (Exception e) {/*Per come è fatto il programma non puoi arrivarci qua dentro*/}
			return temp;
		}	
		//chiamata ricorsiva
		return trovaAnnullabili(temp);
	}
		
	/**
	 * @return true se le variabili delle produzioni sono contenute nell'insieme delle variabili
	 */
	private boolean checkConsistenza() throws Exception {
		for(Tripla t : produzioni.getTriplaProduzioni()) {
			for(Produzione p : t.getProd()) {
				if(!variabili.hasVar(p.getSX()))
					throw new Exception("Il simbolo "+p.getSX()+" non è contenuto nell'insieme delle variabili.");
				for(Simbolo s : p.getDX().getSimboli())
					if(!variabili.hasVar(s))
						throw new Exception("Il simbolo "+s+" non è contenuto nell'insieme delle variabili.");
			}		
		}
		return true;
	}
	
	/** Ritorna un ArrayList di produzioni senza duplicati */
	private ArrayList<Produzione> eliminaDoppioni(ArrayList<Produzione> pippo) {
		ArrayList<Produzione> ritorno = new ArrayList<Produzione>();
		for(Produzione p : pippo)
			if(!ritorno.contains(p))
				ritorno.add(p);
		return ritorno;
	}
	
	/** Ritorna vero se i due array di produzioni sono uguali */
	private boolean arrayProduzioniUguali(ArrayList<Produzione> pippo, ArrayList<Produzione> pluto) {
		if(pippo==null || pluto==null)
			return false;
		if(pippo.size()!=pluto.size())
			return false;
		int contatore = 0;
		for(Produzione p : pippo)
			for(Produzione q : pluto)
				if(p.equals(q))
					contatore++;
		return contatore==pippo.size() ? true : false;
	}
	
	/** Ritorna un Insiemevar a partire da delle produzioni */
	private InsiemeVar estraiVariabili(InsiemeProd ip) {
		InsiemeVar ritorno = new InsiemeVar();
		try {
			for (Tripla t : ip.getTriplaProduzioni()) {
				for (Produzione p : t.getProd()) {
					if (!ritorno.hasVar(p.getSX()))
						ritorno.addVar(p.getSX());
					for (Simbolo s : p.getDX().getSimboli())
						if (!ritorno.hasVar(s))
							ritorno.addVar(s);
				}
			}
		} catch (Exception e) {
			/* Tutto a posto */}
		return ritorno;
	}
	
}
