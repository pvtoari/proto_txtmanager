package main.libs;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class listener {

    public static void main(String[] args) {
        try {

            jarloader jarLoader = new jarloader("proto\\jarLibs\\jnativehook-2.2.2.jar");

            // Load the classes
            Class<?> globalScreenClass = jarLoader.loadClass("com.github.kwhat.jnativehook.GlobalScreen");
            Class<?> nativeHookExceptionClass = jarLoader.loadClass("com.github.kwhat.jnativehook.NativeHookException");
            Class<?> nativeKeyEventClass = jarLoader.loadClass("com.github.kwhat.jnativehook.keyboard.NativeKeyEvent");
            Class<?> nativeKeyListenerClass = jarLoader.loadClass("com.github.kwhat.jnativehook.keyboard.NativeKeyListener");

            Object listenerInstance = Proxy.newProxyInstance(
                nativeKeyListenerClass.getClassLoader(),
                new Class<?>[] { nativeKeyListenerClass },
                (proxy, method, methodArgs) -> {
                    if (method.getName().equals("nativeKeyPressed")) {
                        // Handle nativeKeyPressed event
						Method getKeyCodeMethod = nativeKeyEventClass.getMethod("getKeyCode");
						int keyCode = (int) getKeyCodeMethod.invoke(methodArgs[0]);

						System.out.println("Key Pressed: " + nativeKeyEventClass.getMethod("getKeyText", int.class).invoke(null, keyCode));

						if (keyCode == (int) nativeKeyEventClass.getField("VC_ESCAPE").get(null)) {
							Method unregisterNativeHookMethod = globalScreenClass.getMethod("unregisterNativeHook");
							unregisterNativeHookMethod.invoke(null);

							main.libs.sanCLI.climain.editing = false;
							main.libs.sanCLI.climain.main(null);
						}

                    } else if (method.getName().equals("nativeKeyReleased")) {
                        // Handle nativeKeyReleased event
                    } else if (method.getName().equals("nativeKeyTyped")) {
                        // Handle nativeKeyTyped event
                    }
                    return null;
                }
            );

            // Get the methods
            Method registerNativeHookMethod = globalScreenClass.getMethod("registerNativeHook");
            Method addNativeKeyListenerMethod = globalScreenClass.getMethod("addNativeKeyListener", nativeKeyListenerClass);

            // Register the native hook
            registerNativeHookMethod.invoke(null);

            // Add the native key listener
            addNativeKeyListenerMethod.invoke(null, listenerInstance);
        } catch (Exception e) {
            System.err.println("There was a problem registering the native hook.");
            e.printStackTrace();
        }
    }

	public static boolean isListenerRunnable() {
		try {
            // Load the JAR file
            jarloader jarLoader = new jarloader("proto\\jarLibs\\jnativehook-2.2.2.jar");

            // Load the classes
            Class<?> globalScreenClass = jarLoader.loadClass("com.github.kwhat.jnativehook.GlobalScreen");
            Class<?> nativeKeyListenerClass = jarLoader.loadClass("com.github.kwhat.jnativehook.keyboard.NativeKeyListener");

            // Create a new instance of the listener
            Object listenerInstance = Proxy.newProxyInstance(
                nativeKeyListenerClass.getClassLoader(),
                new Class<?>[] { nativeKeyListenerClass },
                (proxy, method, methodArgs) -> {
					Method unregisterNativeHookMethod = globalScreenClass.getMethod("unregisterNativeHook");
					unregisterNativeHookMethod.invoke(null);
                    return null;
                }
            );

			return true;

        } catch (Exception e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
			return false;
        }
	}
}

/*

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

 */
