package main;

import main.libs.jarloader;
import main.libs.listener;

public class run_me {
    public static void main(String[] args) {
        if (!listener.isListenerRunnable()) {
            System.out.println("\nError loading JNativeHook library, keyboard-reading functions may not work\n");
            System.out.println("\nContinuing without JNativeHook...");
        }
            
        main.libs.sanCLI.climain.main(null);
        
        }
    }