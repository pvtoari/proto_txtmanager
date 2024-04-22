// codigo original:
//	https://github.com/SanFdezz/PROGRAMACION-1-DAW

package main.libs.sanCLI;
import java.util.*;
//import java.io.*;

public class climain {
	public static boolean editing = false;
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String comando="", contenido, rutas;
		boolean cambios;
		String[] tempArgs= null;
		do {
			System.out.print("proto > ");
			contenido=sc.nextLine();
			try {
				// para saber que comando ha introducido, leemos hasta el primer espacio.
				comando=captarComando(contenido);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			switch(comando) {
				case "pwd":
					System.out.println(manager.getPWD());
				break;
				case "cd":
					rutas=captarContenido(contenido);
					cambios=manager.changeDir(rutas);
					if(cambios) {
						System.out.println("cambios realizados.");
					}else {
						System.out.println("No se han podido realizar los cambios.");
					}
					break;
				case "ls":
					manager.printList();
				break;
				case "ll":
					manager.printListLL();
				break;
				case "help":
					mostrarAyuda();
				break;
				case "view":
					rutas = captarContenido(contenido);
					tempArgs = new String[2];
					tempArgs[0]=comando;
					tempArgs[1]=rutas;
					main.libs.proto.main(tempArgs);
				break;
				case "edit":
					rutas = captarContenido(contenido);
					tempArgs = new String[2];
					tempArgs[0]=comando;
					tempArgs[1]=rutas;
					editing = true;
					main.libs.proto.main(tempArgs);
				break;
				case "clear":
					System.out.print("\033\143");
				break;
			}
				
		}while(!comando.equals("exit") && !editing);
		
		sc.close();
	}

	public static void mostrarAyuda() {
		// mostramos los comandos y sus funciones
		System.out.println("Lista de comandos:");
		System.out.println("pwd: Muestra cual es la carpeta actual.");
		System.out.println("cd <DIR>: Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.");
		System.out.println("ls: Muestra la lista de directorios y archivos de la carpeta actual");
		System.out.println("ll: Como ls pero muestra también el tamaño y la fecha de última modificación.");
		System.out.println("help: Muestra una breve ayuda con los comandos disponibles.");
		System.out.println("view: Carga cualquier archivo interpretable como texto.");
		System.out.println("edit: Carga cualquier archivo interpretable como texto y muestra una interfaz para su edicion.");
		System.out.println("clear: Limpia la consola.");
		System.out.println("exit: Termina el programa.");

	}
	
	public static String captarComando(String cont) throws Exception{ 
		// creamos variables
		String comand="";
		
		// recortamos hasta el primer espacio
		int posicion = cont.indexOf(" ");
		if(posicion>-1) {
			comand = cont.substring(0,posicion);
		} else {
			comand = cont;
		}
		// vemos que sea valido
		if(!comand.equals("pwd") && !comand.equals("cd") && !comand.equals("ls") && !comand.equals("ll") &&
			 !comand.equals("help") && !comand.equals("exit") && !comand.equals("view") && 
			 !comand.equals("clear") && !comand.equals("edit")) {
			throw new Exception("Comando no valido");
		}
		// devolvemos el comando
		return comand;
	}
	
	public static String captarContenido(String cont) {
		// creamos las variables y obtenemos cual es el contenido mediante el substring
		String content="";
			int pos = cont.indexOf(" ");
			content = cont.substring(pos+1);
		return content;
	}
	
}
