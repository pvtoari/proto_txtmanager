package main.libs;

import java.util.*;
import java.io.*;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import main.libs.sanCLI.climain;

public class proto implements NativeKeyListener{
    public static void main(String[] args) {
        switch (args[0]) {
            case "view":
                viewCall(args[1], true);
            break;
            case "edit":
                editInitCall(args[1]);
                //libs.sanCLI.climain.editing = true;
        }
    }

    static void editInitCall(String ruta) {
        viewCall(ruta, false);
        
        try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new proto());
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
    
    public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("pulsao: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            		try {
                		GlobalScreen.unregisterNativeHook();
                		main.libs.sanCLI.climain.editing = false;
                		//libs.sanCLI.climain.main(null);
                		main.run_me.main(null);
            		} catch (NativeHookException nativeHookException) {
                		nativeHookException.printStackTrace();
            		}
        	}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {}

	public void nativeKeyTyped(NativeKeyEvent e) {}
}