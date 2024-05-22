package com.pvtoari.prototxt.libs;

import java.util.*;
import java.io.*;

import com.pvtoari.prototxt.RunMe;
import com.pvtoari.prototxt.libs.sanCLI.Manager;

public class Proto {
    public static Listener listener = new Listener();

    public static void main(String[] args) {
        System.out.println("Running proto core...");
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
        RunMe.instance.kill();
        listener.start();
    }

    static void viewCall(String ruta, boolean headers) {
        String txt = "", hds = "\n-----------------------------------------\n";

        if(!ruta.contains("..") && ruta.indexOf("\\")==-1 || ruta.indexOf("/") == -1) {
            ruta = Manager.getPWD() + "\\" + ruta;
        }

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