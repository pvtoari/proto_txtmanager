package main;

import main.libs.sanCLI.climain;
import main.libs.sanCLI.CLIinstance;
import main.libs.listener;

public class run_me {

    public static CLIinstance instance = new CLIinstance();
    public static void main(String[] args) {
        System.out.println("Running run_me...");
        if (!listener.isListenerRunnable()) {
            System.out.println("\nError loading JNativeHook library, keyboard-reading functions may not work\n");
            System.out.println("\nContinuing without JNativeHook...");
        }
            
        instance.run();
        }
    }