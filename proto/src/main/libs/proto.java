package main.libs;

import java.util.*;
import java.io.*;
import main.libs.listener;
import main.libs.sanCLI.climain;

public class proto {
    public static void main(String[] args) {
        System.out.println("Running proto...");
        switch (args[0]) {
            case "view":
                viewCall(args[1], true);
            break;
            case "edit":
                editInitCall(args[1]);
            break;
        }
    }

    static void editInitCall(String ruta) {
        viewCall(ruta, false);
            listener.main(null);
    }

    static void viewCall(String ruta, boolean headers) {
        String txt = "", hds = "\n-----------------------------------------\n";
        try {
            File f = new File(ruta);
            Scanner fr = new Scanner(f);

            while(fr.hasNextLine()) {
                txt += fr.nextLine() + "\n";
            }

            fr.close();

        } catch (Exception e) {
            System.out.println("La ruta es errónea." + "\n" + e.getMessage());
        }

        if(headers) {
            txt=hds+txt+hds;
        }

        System.out.println(txt);
    }
}