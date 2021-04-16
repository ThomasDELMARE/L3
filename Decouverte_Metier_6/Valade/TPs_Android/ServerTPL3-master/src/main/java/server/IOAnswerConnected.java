package server;

import java.util.Date;

public class IOAnswerConnected {
	
		String [] connected ;

		public IOAnswerConnected() {
			
		}
		
		public IOAnswerConnected(String [] connected) {
			this.connected = connected;
		}
		
		public String[] getConnected( ){
			return this.connected ;
		}
		
		public void setConnected(String[] connected) {
			this.connected = connected;
		}



		/**
		// pour illuster que le serveur peut envoyer des objets complexes
	Object [] nimporte = { new Integer(1) , "fsdfsdf" , new Date(), new Object() };

	int [] tableauDeInt = { 1, 2, 3, 4, 5, 6, 7, 8};

	public int[] getTableauDeInt() {
		return tableauDeInt;
	}

	public void setTableauDeInt(int[] tableauDeInt) {
		this.tableauDeInt = tableauDeInt;
	}

	public Object[] getNimporte() {
		return nimporte;
	}

	public void setNimporte(Object[] nimporte) {
		this.nimporte = nimporte;
	}
		 */

}
