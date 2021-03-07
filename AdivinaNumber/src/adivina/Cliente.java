package adivina;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		String host = "localhost";
		int port = 3600;
		try {

			Socket cliente = new Socket(host,port);
			DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
			
			System.out.println("PON NOMBRE PARA JUGAR...");
			salida.writeUTF(teclado.nextLine());
			
			Escuchador listen = new Escuchador(cliente);
			listen.start();

			System.out.println("ESCRIBE UN NUMERO PARA PROBAR... -1 para salir");

			do {
				try {
					salida.writeInt(teclado.nextInt());
				}catch(InputMismatchException ex) {
					teclado.nextLine();
					System.err.println("LA ENTRADA NO ES VALIDA");
				}

			}while(true);


		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		teclado.close();
		
	}

}
