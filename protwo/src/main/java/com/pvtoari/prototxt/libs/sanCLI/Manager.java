package com.pvtoari.prototxt.libs.sanCLI;

import java.io.*;
import java.util.*;
import com.pvtoari.prototxt.libs.Ansi;

// original code:
// https://github.com/SanFdezz/PROGRAMACION-1-DAW

public class Manager {
	
	// this indicates the directory you are working in
	static String dir = System.getProperty("user.dir");
	// create it as a file
	static File currentFile = new File(dir); 
	
	public static String getPWD() { 
		// return the current directory
		return currentFile.getAbsolutePath();
	}
	
	public static boolean changeDir(String[] args) {
		String tempDir = args[1]; // get the directory specified by the user
		File newDestination = new File(tempDir);
		String parentDir; 
		boolean changes = true;

		// first we look for args, speciffically argument "-near"
		if(args.length>2 && !args[2].isEmpty() && args[2].equals("-near")) {
			File[] tempFiles = currentFile.listFiles();
			ArrayList<Pair> tempStrings = new ArrayList<Pair>();
			// simple stuff just looking at files wit that prefix and adding them to the list
			// using pairs for Comparator.comparing method, might remove it if not needed
			for(int i = 0; i < tempFiles.length; i++) {
				if(tempFiles[i].getName().startsWith(tempDir)) {
					tempStrings.add(new Pair(tempFiles[i], tempFiles[i].getName()));
				}
			}

			/*  apparently this is not needed as the files are already sorted (java literally says the opposite, but it works somehow)
			System.out.println(tempStrings);
			tempStrings.sort(Comparator.comparing(p -> p.x));
			System.out.println(tempStrings);
			*/

			// if no prefix found then this runs
			if(tempStrings.size() == 0) {
				System.out.println("No files found with that prefix");
				return false;
			} else {
				// if there's a prefix found, we change the directory to the first one found
				currentFile = tempStrings.get(0).x;
				return true;
			}
		}

		// if no args, then we enter no args context
		if (tempDir.equals("..")) { // if the directory entered is equal to ..
			parentDir = currentFile.getParent(); // get the parent of the current file
			if(parentDir != null) { // apply a filter in case something goes wrong
				File parent = new File(parentDir); // create that path
				currentFile = parent; // change the current path to its parent, i.e., one folder up
				changes = true; // and mark the change as true
			} else {
				changes = false; // if not, the change has been false, not performed
			}
		} else { // if user not using ".." functionaly then
			File temp = newDestination; //used just for comparing at the end

			for(File d : currentFile.listFiles()) {
				if(d.isDirectory() && d.getName().equals(tempDir)) { // if the file is a directory and its name is equal to the one specified
					newDestination = d; // change the directory to the one specified
					break;
				}
			}

			if(newDestination == temp) {
				System.out.println("Directory not valid"); // if not, display this error
				changes = false; // and do not validate it
			} else {
				currentFile = newDestination; // try to replace the previous file with the specified one
				changes = true; // and validate it
			}
		}
		// return the results
		return changes;
	}
	
	public static void printList() { // to display the file names
		if(currentFile.isDirectory()) { // check if it is a directory
			File[] fileList = currentFile.listFiles(); // list its files
			Arrays.sort(fileList); // sort them alphabetically
			for(File e : fileList) { // iterate through the array
				if(e.isDirectory()) { // display the directories
					System.out.print(Ansi.Yellow.colorize("[DIR] "));
					System.out.print(Ansi.Bold.colorize(e.getName()) + "\n");
				}
			}
			for(File e : fileList) { // do the same for the files
				if(e.isFile()) {
					System.out.print(Ansi.Black.colorize("[FILE] "));
					System.out.print(Ansi.Bold.colorize(e.getName()) + "\n");
				}
			}
		} else { // if not, there is nothing to iterate or it is simply a file
			System.out.println("Not a valid directory for this command");
		}
	}
	
	public static void printListLL() {
		if(currentFile.isDirectory()) { // check if it is a directory
			File[] fileList = currentFile.listFiles(); // list its files
			Arrays.sort(fileList); // sort them alphabetically
			
			for(File e : fileList) { // iterate through the array
				if(e.isDirectory()) { // display the directories and their modification date
					System.out.print(Ansi.Yellow.colorize("[DIR] "));
					System.out.print(Ansi.Bold.colorize(e.getName()) + " | Last modified: " + new Date(e.lastModified()) + "\n");
				}
			}
			for(File e : fileList) { // do the same for the files along with their size
				if(e.isFile()) {
					System.out.print(Ansi.Black.colorize("[FILE] "));
					System.out.print(Ansi.Bold.colorize(e.getName()) + " | Length: " + e.length() + " bytes | " + "Last modified: " + new Date(e.lastModified()) + "\n");
				}
			}
		} else { // if not, there is nothing to iterate or it is simply a file
			System.out.println("Not a valid directory for this command");
		}
	}

	public static String[] getSortedFilesNameStrings(File[] files) {
		String[] res = new String[files.length];

		for(int i = 0; i < files.length; i++) {
			res[i] = files[i].getName();
		}

		Arrays.sort(res);

		return res;
	}

	static class Pair {
		File x;
		String y;

		Pair(File x, String y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "("+x.getName() + " " + y + ")";
		}
	}
}
