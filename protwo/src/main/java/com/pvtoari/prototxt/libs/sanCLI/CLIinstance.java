// codigo original:
//	https://github.com/SanFdezz/PROGRAMACION-1-DAW

package com.pvtoari.prototxt.libs.sanCLI;
import java.util.*;
//import java.io.*;

import com.pvtoari.prototxt.libs.Ansi;
import com.pvtoari.prototxt.libs.Proto;

public class CLIinstance {
	private Scanner scanner;
	private String command, content, path;
	private boolean killed;
	boolean changes;
	private String[] tempArgs;
	private final Ansi ANSI_HIGHLIGHT = new Ansi(Ansi.REVERSE_VIDEO);

	public CLIinstance() {};

	public void run() {
		
		this.setDefaultValues();
		System.gc();

		scanner = new Scanner(System.in);
		//System.out.println("Debug info\n"+ ansi.colorize(scanner.toString())); //debug stuff
		do {
			System.out.print("proto >" + " " + ANSI_HIGHLIGHT.colorize(Manager.getPWD()) + " > ");
			if (!scanner.hasNextLine()) {
				scanner = new Scanner(System.in);
				System.out.println("el fokin error esta sucediendo");
			}
			content=scanner.nextLine();
			try {
				// para saber que comando ha introducido, leemos hasta el primer espacio.
				command=catchCommand(content);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			runCommand(command, content);
		}while(!command.equals("exit")&&!killed);
	}

	public void kill() {
		this.killed=true;
	}

	public void setDefaultValues() {
		this.command="";
		this.content="";
		this.path="";
		this.killed=false;
		this.changes=false;
		this.tempArgs=null;
	}

	public void runCommand(String command, String args) {
		switch(command) {
			case "pwd":
				System.out.println(Manager.getPWD());
			break;
			case "cd":
				path=catchContent(content);
				changes=Manager.changeDir(path);
				if(!changes) {
					System.out.println("Command could not be run, if problem persists, open a issue on Github.");
				}
			break;
			case "ls":
				Manager.printList();
			break;
			case "ll":
				Manager.printListLL();
			break;
			case "help":
				showHelp();
			break;
			case "view":
				path = catchContent(content);
				tempArgs = new String[2];
				tempArgs[0]=command;
				tempArgs[1]=path;
				Proto.main(tempArgs);
			break;
			case "edit":
				path = catchContent(content);
				tempArgs = new String[2];
				tempArgs[0]=command;
				tempArgs[1]=path;
				
				command = "exit";
				Proto.main(tempArgs);
			break;
			case "clear":
				System.out.print("\033\143");
			break;
		}
	}

	public static void showHelp() {
		// mostramos los comandos y sus funciones
		System.out.println("List of commands:");
		System.out.println("pwd: Shows the current directory.");
		System.out.println("cd <DIR>: Changes the current directory to 'DIR'. Use .. to go to the parent directory.");
		System.out.println("ls: Shows the list of directories and files in the current directory.");
		System.out.println("ll: Same as ls, but also displays the size and last modification date.");
		System.out.println("help: Displays a brief help with the available commands.");
		System.out.println("view <DIR>: Loads any text-interpretable file.");
		System.out.println("edit <DIR>: Loads any text-interpretable file and displays an interface for editing it.");
		System.out.println("clear: Clears the console.");
		System.out.println("exit: Terminates the program.");

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
