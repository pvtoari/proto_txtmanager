// codigo original:
//	https://github.com/SanFdezz/PROGRAMACION-1-DAW

package main.libs.sanCLI;
import java.util.*;
//import java.io.*;

import main.libs.proto;

public class CLIinstance {
	private Scanner scanner;
	private String command="", content="", path="";
	boolean changes;
	private String[] tempArgs= null;

	public CLIinstance() {};

	public void run() {
		scanner = new Scanner(System.in);
		do {
			System.out.print("proto > ");
			content=scanner.nextLine();
			try {
				// para saber que comando ha introducido, leemos hasta el primer espacio.
				command=catchCommand(content);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			runCommand(command, content);
		}while(!command.equals("exit"));
		scanner.close();
	}

	public void runCommand(String command, String args) {
		switch(command) {
			case "pwd":
				System.out.println(manager.getPWD());
			break;
			case "cd":
				path=catchContent(content);
				changes=manager.changeDir(path);
				if(changes) {
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
				showHelp();
			break;
			case "view":
				path = catchContent(content);
				tempArgs = new String[2];
				tempArgs[0]=command;
				tempArgs[1]=path;
				proto.main(tempArgs);
			break;
			case "edit":
				path = catchContent(content);
				tempArgs = new String[2];
				tempArgs[0]=command;
				tempArgs[1]=path;
				
				command = "exit";
				proto.main(tempArgs);
			break;
			case "clear":
				System.out.print("\033\143");
			break;
		}
	}
	/*
	public static void main(String[] args) {
		System.out.println("Running main CLI...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		String comando="", contenido, rutas;
		boolean cambios;
		String[] tempArgs= null;
		System.out.println("paso por aqui");
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
					proto.main(tempArgs);
				break;
				case "edit":
					rutas = captarContenido(contenido);
					tempArgs = new String[2];
					tempArgs[0]=comando;
					tempArgs[1]=rutas;
					
					comando = "exit";
					proto.main(tempArgs);
				break;
				case "clear":
					System.out.print("\033\143");
				break;
			}
				
		}while(!comando.equals("exit"));
		
		sc.close();
	}
 */
	public static void showHelp() {
		// mostramos los comandos y sus funciones
		System.out.println("Lista de comandos:");
		System.out.println("pwd: Muestra cual es la carpeta actual.");
		System.out.println("cd <DIR>: Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.");
		System.out.println("ls: Muestra la lista de directorios y archivos de la carpeta actual");
		System.out.println("ll: Como ls pero muestra también el tamaño y la fecha de última modificación.");
		System.out.println("help: Muestra una breve ayuda con los comandos disponibles.");
		System.out.println("view <DIR>: Carga cualquier archivo interpretable como texto.");
		System.out.println("edit <DIR>: Carga cualquier archivo interpretable como texto y muestra una interfaz para su edicion.");
		System.out.println("clear: Limpia la consola.");
		System.out.println("exit: Termina el programa.");

	}
	
	public static String catchCommand(String cont) throws Exception{ 
		// creamos variables
		String command="";
		
		// recortamos hasta el primer espacio
		int pos = cont.indexOf(" ");
		if(pos>-1) {
			command = cont.substring(0,pos);
		} else {
			command = cont;
		}
		// vemos que sea valido
		if(!command.equals("pwd") && !command.equals("cd") && !command.equals("ls") && !command.equals("ll") &&
			 !command.equals("help") && !command.equals("exit") && !command.equals("view") && 
			 !command.equals("clear") && !command.equals("edit")) {
			throw new Exception("Comando no valido");
		}
		// devolvemos el comando
		return command;
	}
	
	public static String catchContent(String cont) {
		// creamos las variables y obtenemos cual es el contenido mediante el substring
		String content="";
			int pos = cont.indexOf(" ");
			content = cont.substring(pos+1);
		return content;
	}
	
}
