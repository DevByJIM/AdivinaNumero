package adivina;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Escuchador extends Thread{

	Socket cliente;
	String respuesta;
	
	public Escuchador(Socket cliente) {
		this.cliente = cliente;
	}

	public void run() {
		try {	
			
			do {
				respuesta = new DataInputStream(cliente.getInputStream()).readUTF();
				if(!respuesta.equals("EOF")) 
					System.out.println(respuesta);

			}while(!respuesta.equals("EOF"));
			
			System.out.println("GRACIAS POR JUGAR");
			
			cliente.close();
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
