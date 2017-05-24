package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {
	
	public boolean getSimilarWord(String temp, String parola) {
		int errori = 0;
		for(int i = 0; i < parola.length(); i++){
			if(parola.charAt(i) != temp.charAt(i))
				errori++;
		}
		if(errori == 1)
			return true;
		else
			return false;
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un solo carattere
	 */
	public List<String> getAllSimilarWords(String parola, int numeroLettere) {
	
		List<String> paroleSimili = new ArrayList<String>();
		List<String> parole = this.getAllWordsFixedLength(numeroLettere);
		
		for(String temp : parole){
			if(getSimilarWord(temp, parola))
				paroleSimili.add(temp);
		}
		
		return paroleSimili;
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
