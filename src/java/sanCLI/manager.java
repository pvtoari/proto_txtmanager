// codigo original:
//	https://github.com/SanFdezz/PROGRAMACION-1-DAW

package src.java.sanCLI;
import java.io.*;
import java.util.*;
public class manager {
	
	// esto indica el directorio en el que estas trabajando.
	static String directorio = System.getProperty("user.dir");
	// lo creamos como archivo
	static File archivoActual = new File(directorio); 
	
	public static String getPWD() { 
		// devolvemos el directorio en el que nos encontramos
		return archivoActual.getAbsolutePath();
	}
	
	public static boolean changeDir(String dir) {
		// creamos una nueva direccion (la que nos dice el usuario)
		File nuevoDestino = new File(dir);
		// creamos las variables
		String padreDir; 
		boolean changes;
		if (dir.equals("..")) { // si el directorio que nosha introducido es igual a ..
			padreDir = archivoActual.getParent(); // pillamos los padres del archivo actual
			if(padreDir != null) { // por si acaso sale mal pasamos por un filtro.
				File padre = new File(padreDir); // creamos esa ruta
				archivoActual= padre; // y cambiamos la ruta actual a la anterior suya, es decir, una carpeta más arriba
				changes=true; // y modificamos su cambio a verdadero
			}else {
				changes= false; // si no, pues su cambio ha sido falso, no realizado.
			}
		} else { // si el directorio indicado era diferente de ..
			try {
				archivoActual=nuevoDestino; // intentamos reemplazar el archivo anterior por el indicado.
				changes = true; // y lo validamos
			}catch(Exception e) {
				System.out.println("Error al cambiar de directorio."); // si no, nos salta este error.
				changes = false; // y no lo validamos
			}
		}
		// devolvemos los resultados
		return changes;
	}
	
	public static void printList() { // para mostrar los nombres de los archivos
		if(archivoActual.isDirectory()) { // miramos si es directorio
			File[] fileList = archivoActual.listFiles(); // listamos sus archivos
			Arrays.sort(fileList); // los ordenamos por ordena alfabético
			for(File e : fileList) { // recorremos el array
				if(e.isDirectory()) { // mostramos los directorios
					System.out.println("[DIR]"+e.getName());
				}
			}
			for(File e : fileList) { // despues hacemos lo mismo para los archivos
				if(e.isFile()) {
					System.out.println("[FILE]"+e.getName());
				}
			}
		} else { // y si no, decimos que efectivamente, no hay nada que recorrer o q simplemente es un file.
			System.out.println("No es un directorio valído para ello.");
		}
	}
	
	public static void printListLL() {
		if(archivoActual.isDirectory()) { // miramos si es directorio
			File[] fileList = archivoActual.listFiles(); // listamos sus archivos
			Arrays.sort(fileList);// los ordenamos por ordena alfabético
			
			for(File e : fileList) {// recorremos el array
				if(e.isDirectory()) { // mostramos los directorios y su fecha de modificacion
					System.out.println("[DIR]"+e.getName() +". Last modified: "+ new Date(e.lastModified()));
				}
			}
			for(File e : fileList) { // despues hacemos lo mismo para los archivos junto con su peso.
				if(e.isFile()) {
					System.out.println("[FILE]"+e.getName()+". Length: "+ e.length() + " bytes. "+"Last modified:"+ new Date(e.lastModified()));
				}
			}
		} else {  // y si no, decimos que efectivamente, no hay nada que recorrer o q simplemente es un file.
			System.out.println("No es un directorio valido para ello.");
		}
	}
}
