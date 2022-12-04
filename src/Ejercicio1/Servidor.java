package Ejercicio1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {

	public static void main(String[] args) throws IOException {
		byte[] bufer = new byte[1024];// bufer para recibir el datagrama
		// ASOCIO EL SOCKET AL PUERTO 12345
		DatagramSocket socket = new DatagramSocket(12345);

		// ESPERANDO DATAGRAMA
		System.out.println("SERVIDOR esperando nombre del fichero y ruta .......... ");
		String nombreFich;
		String rutaFich;

		DatagramPacket recibo = new DatagramPacket(bufer, bufer.length);
		socket.receive(recibo);// recibo datagrama
		nombreFich = new String(recibo.getData(), recibo.getOffset(), recibo.getLength());// obtengo String
		System.out.println("SERVIDOR ha recibido el nombre del archivo .......... ");
		System.out.println("El nombre que he recibido ha sido: " + nombreFich);

		DatagramPacket recibo2 = new DatagramPacket(bufer, bufer.length);
		socket.receive(recibo2);// recibo datagrama
		rutaFich = new String(recibo2.getData(), recibo2.getOffset(), recibo2.getLength());// obtengo String
		System.out.println("SERVIDOR ha recibido el nombre del archivo .......... ");
		System.out.println("La ruta que he recibido ha sido: " + rutaFich);

		Fichero f = new Fichero(nombreFich, rutaFich);

		File fichero = new File(f.unirFichero(nombreFich, rutaFich));

		String textoEnviar = "";

		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String cadena = "";
			while ((cadena = br.readLine()) != null) {
				textoEnviar += cadena + "\n";
			}

		} catch (Exception e) {
			System.out.println();
		}
		
		
		System.out.println("SERVIDOR enviando contenido del fichero......");

		DatagramPacket envio = new DatagramPacket(textoEnviar.getBytes(), textoEnviar.length(), recibo.getAddress(),
				recibo.getPort());

		socket.send(envio);

		System.out.println("Cerrando conexion...");
		socket.close(); // cierro el socket

	}

}
