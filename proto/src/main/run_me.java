package main;

import java.io.IOException;

public class run_me {
    public static void main(String[] args) {
        try {
            main.libs.libraryloader.loadLibrary("JNativeHook");
            // El resto de tu código va aquí...
        } catch (Exception e) {
            System.out.println("\nError loading JNativeHook library, keyboard-reading functions may not work\n");
            e.printStackTrace();
            System.out.println("\nContinuing without JNativeHook...");
        }

        main.libs.sanCLI.climain.main(null);
    }
}