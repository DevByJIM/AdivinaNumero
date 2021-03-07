package adivina;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HiloServidor extends Thread {

	ArrayList<Socket> jugadores = new ArrayList<Socket>();
	DataOutputStream salida;
	DataInputStream entrada;
	Socket cliente;
	String nombre="Anonimous";
	int port = 3600;
	int numero;
	

	public HiloServidor(ArrayList<Socket> jugadores, Socket cliente, int numero) {
		super();
		this.jugadores = jugadores;
		this.cliente = cliente;
		this.numero = numero;
		try {
			salida = new DataOutputStream(this.cliente.getOutputStream());
			entrada = new DataInputStream(this.cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	public void run() {

		try {
		nombre = entrada.readUTF();
		System.out.println("JUGADOR " + nombre + " ESTA LISTO PARA JUGAR ADIVINA DEL 1 AL 100. ");
		int dato=-1;

			do {
				System.out.println("Nº:Jugadores ahora: " + jugadores.size());
				dato = entrada.readInt();
				if(dato != numero) {
					salida.writeUTF("No has acertado. Lo siento??. Prueba de nuevo...");
				}else if(dato == numero) {
					salida.writeUTF("Has acertado!!!!!");
					salida.writeUTF("EOF");
					jugadores.remove(cliente);
					if(jugadores.size()>0) envioATodos();
					dato = -1;
				}

			}while(dato!=-1);
			
			salida.writeUTF("EOF");
			salida.close();
			entrada.close();
			cliente.close();
			System.out.println("Jugador " + nombre + " desconectado.");
		} catch (EOFException ex) {
			System.out.println("Jugador " + nombre + " desconectado.");
		} catch (IOException e) {
			e.printStackTrace();	
		}
	}
	
	private synchronized void envioATodos() {

		for(Socket socket : jugadores) {
				try {
					new DataOutputStream(socket.getOutputStream()).writeUTF("Ha acertado el jugador " + nombre);
					new DataOutputStream(socket.getOutputStream()).writeUTF("EOF");
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
	}
	
}
