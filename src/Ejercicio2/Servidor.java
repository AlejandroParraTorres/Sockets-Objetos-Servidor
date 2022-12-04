package Ejercicio2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws Exception {

		int numeroPuerto = 6000;// Puerto
		ServerSocket servidor = new ServerSocket(numeroPuerto);
		System.out.println("Esperando al cliente.....");
		Socket cliente = servidor.accept();
		int numero;
		int contadorCliente = 0;
		int contadorServidor = 0;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		do{

			ois = new ObjectInputStream(cliente.getInputStream());
			Juego jCliente = (Juego) ois.readObject();
			System.out.println("El cliente juega a " + jCliente.getEleccion());

			numero = (int) (Math.random() * (100 - 1 + 1) + 1);
			System.out.println("Generamos un numero aleatorio para jugar --> " + numero);

			// Se prepara un flujo de salida para objetos
			oos = new ObjectOutputStream(cliente.getOutputStream());
			Juego jServidor;
			// Se prepara un objeto y se env�a
			if (jCliente.getEleccion().equalsIgnoreCase("PARES")) {
				jServidor = new Juego("NONES", numero);
			} else {
				jServidor = new Juego("PARES", numero);
			}

			oos.writeObject(jServidor); // enviando objeto

			if ((numero + jCliente.getNumero()) % 2 == 0) {
				if (jServidor.getEleccion().equalsIgnoreCase("PARES")) {
					contadorServidor++;
					System.out.println(
							"He ganado :) . Vamos Cliente: " + contadorCliente + " Servidor: " + contadorServidor);
				} else {
					contadorCliente++;
					System.out.println(
							"He perdido :( . Vamos Cliente: " + contadorCliente + " Servidor: " + contadorServidor);
				}
			} else if ((numero + jCliente.getNumero()) % 2 != 0) {
				if (jServidor.getEleccion().equalsIgnoreCase("NONES")) {
					contadorServidor++;
					System.out.println(
							"He ganado :) . Vamos Cliente: " + contadorCliente + " Servidor: " + contadorServidor);
				} else {
					contadorCliente++;
					System.out.println(
							"He perdido :( . Vamos Cliente: " + contadorCliente + " Servidor: " + contadorServidor);
				}
			}

		}while(contadorCliente!=3 && contadorServidor!=3);
		
		if(contadorCliente==3) {
			System.out.println("***** HA GANADO EL CLIENTE :-( *****");
		}else {
			System.out.println("***** HE GANADOOOO :-) *****");
		}

		// CERRAR STREAMS Y SOCKETS
		oos.close();
		ois.close();
		cliente.close();
		servidor.close();
	}

}
