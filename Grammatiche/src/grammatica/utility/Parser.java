package grammatica.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import grammatica.struttura.Epsilon;
import grammatica.struttura.Grammatica;
import grammatica.struttura.InsiemeProd;
import grammatica.struttura.InsiemeVar;
import grammatica.struttura.Produzione;
import grammatica.struttura.Simbolo;
import grammatica.struttura.SimboloTerminale;
import grammatica.struttura.SimboloVariabile;
import grammatica.struttura.Stringa;

public class Parser {
	private String nomeFile;
	
	public Parser(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	/** Legge la grammatica, con il seguente parsing le variabili e i terminali sono limitati
	 * a singole lettere rispettivamente maiuscole e minuscole */
	public Grammatica parse() throws FileNotFoundException {
		String nome = null;
		InsiemeVar insiemeVar = null;
		InsiemeProd insiemeProd = null;
		Simbolo simboloIniziale = null;
		Grammatica grammatica = null;
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(nomeFile));
		} catch (FileNotFoundException e1) {
			throw new FileNotFoundException("Il file non esiste.");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		int contatore = 0;
		ArrayList<Produzione> produzioni = new ArrayList<Produzione>();
		try {
			while ((line = br.readLine()) != null) {
				//System.out.println("Linea letta: "+line);
				if(contatore==0) //Nome grammatica
					nome = line.replace(":", "");
				if(contatore>0) { 
					String[] tempArray;
					String[] strProd;
					tempArray = line.split("-->");
					//System.out.println("Simbolo SX: "+tempArray[0].trim());
					if(contatore==1) //Simbolo Iniziale
						simboloIniziale = new SimboloVariabile(tempArray[0].trim());
					strProd = tempArray[1].split("\\|");
					for(int i=0; i<strProd.length; ++i) {
						Stringa str = new Stringa();
						String temp = strProd[i].trim();
						//System.out.println("Stringa DX letta: "+temp);
						if(temp.equals("epsilon"))
							str.addTesta(new Epsilon());
						if(temp.length()==1) {
							char pippo = temp.charAt(0);
							if(Character.isUpperCase(pippo))
								str.addTesta(new SimboloVariabile(pippo+""));
							else if(Character.isDigit(pippo) || Character.isLowerCase(pippo))
								str.addTesta(new SimboloTerminale(pippo+""));
						}
						if(temp.length()>1 && !temp.equals("epsilon")) {
							for(int j=0; j<temp.length(); ++j) {
								char pippo = temp.charAt(j);
								if(Character.isUpperCase(pippo))
									str.addCoda(new SimboloVariabile(pippo+""));
								else if(Character.isDigit(pippo) || Character.isLowerCase(pippo))
									str.addCoda(new SimboloTerminale(pippo+""));
							}
						}
						produzioni.add(new Produzione(new SimboloVariabile(tempArray[0].trim()), str));
					}
				}
				++contatore;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		try {br.close();} catch (IOException e) {}
		
		try {
			insiemeProd = new InsiemeProd(eliminaDoppioni(produzioni));
		} catch (Exception e) {}
		insiemeVar = estraiVariabili(insiemeProd);		
		try {
			grammatica = new Grammatica(nome, insiemeVar, insiemeProd, simboloIniziale);
		} catch (Exception e) {}
		
		return grammatica;
	}
	
	/** Ritorna un ArrayList di produzioni senza duplicati */
	private ArrayList<Produzione> eliminaDoppioni(ArrayList<Produzione> pippo) {
		ArrayList<Produzione> ritorno = new ArrayList<Produzione>();
		for(Produzione p : pippo)
			if(!ritorno.contains(p))
				ritorno.add(p);
		return ritorno;
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
