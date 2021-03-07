package adivina;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Servidor {



	public static void main(String[] args) {
		ServerSocket server= null;
		ArrayList<Socket> jugadores = new ArrayList<Socket>();
		Socket cliente = null;
		int port = 3600;
		int numero;

		numero = new Random().nextInt(100-1)-1;
		
		try {
			
			server = new ServerSocket(port);
			
			while(true) {
				System.out.println("Servidor en espera de jugadores...");
				cliente = server.accept();
				jugadores.add(cliente);
				HiloServidor hilo = new HiloServidor(jugadores, cliente, numero);
				hilo.start();
				Thread.sleep(100);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
