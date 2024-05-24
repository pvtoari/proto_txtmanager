package com.pvtoari.prototxt.libs;

import java.io.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.pvtoari.prototxt.RunMe;
import com.pvtoari.prototxt.libs.sanCLI.CLIinstance;
import javax.swing.SwingUtilities;

public class Listener implements NativeKeyListener {
	public static int CHARACTER_PER_LINE_LIMIT = 80;
	public static final int VC_ARROW_UP = 57416;
	public static final int VC_ARROW_DOWN = 57424;
	public static final int VC_ARROW_LEFT = 57419;
	public static final int VC_ARROW_RIGHT = 57421;
	private int counting = 0; //might remove
	private boolean editing = false;
	private boolean isAlt = false;
	private boolean isControl = false; 
	private boolean isShift = false;
	private static boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK); // true if caps lock is on, false otherwise
	
	public Listener() {}
	
	public void nativeKeyReleased(NativeKeyEvent e) {
		//System.out.println(e.getKeyCode()); used for debugging
		switch(e.getKeyCode()) {
			case NativeKeyEvent.VC_SHIFT: 
				caps=false;
				isShift=false;
				break;
			case NativeKeyEvent.VC_ALT: 
				isAlt = false;
				break;
			case NativeKeyEvent.VC_CONTROL:
				this.isControl = false;
				break;
		}
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		this.counting++;
		String toAppend = NativeKeyEvent.getKeyText(e.getKeyCode());

		if(!isLatinLetter(toAppend) && this.isShift) {
			toAppend = parseCap(toAppend);	
		}

		switch(e.getKeyCode()) {
			case NativeKeyEvent.VC_CAPS_LOCK: 
				caps=!caps; 
				break;
			case NativeKeyEvent.VC_SHIFT: 
				caps=true;
				this.isShift = true;
				break;
			case NativeKeyEvent.VC_SPACE: 
				//handle spacebar events
				break;
			case NativeKeyEvent.VC_ENTER: 
				//handle enter events
				break;
			case NativeKeyEvent.VC_ALT: 
				isAlt = true;
				break;
			case NativeKeyEvent.VC_F10:
			System.out.println("shit i got killed");
				this.kill();
				break;
			case NativeKeyEvent.VC_CONTROL:
				this.isControl = true;
				break;
			case VC_ARROW_UP:
				Proto.hlPointer.forwardRow();
				break;
			case VC_ARROW_DOWN:
				Proto.hlPointer.backwardRow();
				break;
			case VC_ARROW_LEFT:
				Proto.hlPointer.forwardColumn();
				break;
			case VC_ARROW_RIGHT:
				Proto.hlPointer.forwardColumn();
				break;
			case 83:
				System.out.println("I'm tracking :^)");
			default: {
				//handle writing
				if(caps==false) toAppend=toAppend.toLowerCase();
			}
		}

		//useless rn
		if (this.counting == CHARACTER_PER_LINE_LIMIT) {
			//handle character-per-line limit
			this.counting = 0;
		}
	}

	// doesnt work
	private void deleteTraces() {
		File log = new File("writinghere.txt");
		if (log.exists()) {
			log.delete();
		}

		File dll = new File("JNativeHook.x86_64.dll");
		if (dll.exists()) {
			dll.delete();
		}
	}

	public void start() {
		this.editing = true;
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException e) {
			//System.exit(1);
			e.printStackTrace();
		}
		
		GlobalScreen.addNativeKeyListener(Listener.this);
		Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					//handle process killing events
					Listener.this.kill();
				}
		});
	}

	private void kill() {
		// https://github.com/kwhat/jnativehook/issues/109
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					GlobalScreen.unregisterNativeHook();
					GlobalScreen.removeNativeKeyListener(Listener.this);
				} catch (NativeHookException e) {
					e.printStackTrace();
				} finally {
					Listener.this.editing = false;
					deleteTraces();
					RunMe.instance.run();
				}
			}
		});
	}

	// might remove
	public void restart() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
	}

	public boolean getShiftState() {
		return this.isShift;
	}

	public boolean getControlState() {
		return this.isControl;
	}

	public boolean getAltState() {
		return this.isAlt;
	}

	public static boolean isLatinLetter(String s) {
		String aux = s;
		aux=aux.toLowerCase();
		return aux.length() == 1 && aux.charAt(0) >= 'a' && aux.charAt(0) <= 'z';
	}
	
	public String parseCap(String s) {
		String res = "";

		if(!isLatinLetter(s) && isShift) {
			switch(s) {
				// parsing spanish special characters, might do a Locale system in the future
				case "1": res="!"; break;
				case "2": res="\""; break;
				case "3": res="·"; break;
				case "4": res="$"; break;
				case "5": res="%"; break;
				case "6": res="&"; break;
				case "7": res="/"; break;
				case "8": res="("; break;
				case "9": res=")"; break;
				case "0": res="="; break;
			}
		}
		return res;
	}

	public boolean getEditing() {
		return this.editing;
	}
}