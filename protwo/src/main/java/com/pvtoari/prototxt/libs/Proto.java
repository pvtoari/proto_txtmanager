package com.pvtoari.prototxt.libs;

import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

import com.pvtoari.prototxt.RunMe;
import com.pvtoari.prototxt.libs.sanCLI.Manager;

public class Proto {
    public static Listener listener = new Listener();
    private static ArrayList<String> charGrid = new ArrayList<String>();
    public static HighlightingPointer hlPointer;

    public static void main(String[] args) {
        System.out.println("Running proto core...");
        switch (args[0]) {
            case "view":
                viewCall(args[1], true, false);
            break;
            case "edit":
                editInitCall(args[1]);
            break;
        }
    }

    static void editInitCall(String ruta) {
        
        // view call without headers and outputing to static charGrid
        if(!viewCall(ruta, false, true)) {
            RunMe.instance.kill();
            listener.start();
        }

        // create a new highlighting pointer
        hlPointer = new HighlightingPointer(charGrid);
        hlPointer.printHighlightedArrayList();
        while(listener.getEditing()) {
            if(hlPointer.getPointerUpdated()) {
                hlPointer.printHighlightedArrayList();
                System.out.println("updated"); //debug
            }
        }

        System.out.println("ended editing");
    }

    static boolean viewCall(String ruta, boolean headers, boolean outputing) {
        String txt = "", hds = "\n-----------------------------------------\n";
        boolean error = false;

        //if the path is not absolute, make it absolute so it can be read
        // view file.txt -> C:\Users\name\file.txt
        if(!ruta.contains("..") && ruta.indexOf("\\")==-1 || ruta.indexOf("/") == -1) {
            ruta = Manager.getPWD() + "\\" + ruta;
        }

        //read the file
        try {
            File f = new File(ruta);
            Scanner fr = new Scanner(f);

            while(fr.hasNextLine()) {
                String next = fr.nextLine();
                // reading char by char
                for(int i = 0, j = 0; i < next.length(); i++) {
                    txt += next.charAt(i);
                    j++;

                    //if the line exceeds character per line limit, add a new line
                    if(j > Listener.CHARACTER_PER_LINE_LIMIT) {
                        txt+="\n";
                        j=0;
                    }
                }
                //add a new line after each line
                txt +="\n";
            }

            fr.close();

        } catch (Exception e) {
            //if the file is not found, print the error
            headers = false;
            error = true;
            System.out.println("La ruta es errónea." + "\n" + e.getMessage());
        }

        if(headers) {
            //add headers to the file
            txt=hds+txt+hds;
        }

        
        if(outputing) {
            // if outputing to a list, fill it with the content of txt
            //static charGrid = new ArrayList<String>();
            //fill output with content of txt
            String[] lines = txt.split("\n");
            for (String line : lines) {
                charGrid.add(line);
            }
        } else {
            System.out.println(txt);
        }

        return error;
    }
}