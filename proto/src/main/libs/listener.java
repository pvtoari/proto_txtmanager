package main.libs;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class listener implements NativeKeyListener {

	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			try {
				GlobalScreen.unregisterNativeHook();

				main.libs.sanCLI.climain.editing = false;
				main.libs.sanCLI.climain.main(null);
			} catch (NativeHookException nativeHookException) {
				nativeHookException.printStackTrace();
			}
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {}

	public void nativeKeyTyped(NativeKeyEvent e) {}

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			//System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new listener());
	}
}