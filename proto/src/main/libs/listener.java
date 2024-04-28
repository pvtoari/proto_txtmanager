package main.libs;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import main.run_me;
import main.libs.sanCLI.climain;

public class listener {

    private static boolean finished;
    public static void main(String[] args) {
        System.out.println("Running listener...");
        try {
            
            jarloader jarLoader = new jarloader("proto\\jarLibs\\jnativehook-2.2.2.jar");
            
            Class<?> globalScreenClass = jarLoader.loadClass("com.github.kwhat.jnativehook.GlobalScreen");
            Class<?> nativeKeyEventClass = jarLoader.loadClass("com.github.kwhat.jnativehook.keyboard.NativeKeyEvent");
            Class<?> nativeKeyListenerClass = jarLoader.loadClass("com.github.kwhat.jnativehook.keyboard.NativeKeyListener");

            jarLoader.close();

            Object listenerInstance = Proxy.newProxyInstance(
                nativeKeyListenerClass.getClassLoader(),
                new Class<?>[] { nativeKeyListenerClass },
                (proxy, method, methodArgs) -> {
                    if (method.getName().equals("nativeKeyPressed")) {
                        
						Method getKeyCodeMethod = nativeKeyEventClass.getMethod("getKeyCode");
						int keyCode = (int) getKeyCodeMethod.invoke(methodArgs[0]);

						System.out.println("Key Pressed: " + nativeKeyEventClass.getMethod("getKeyText", int.class).invoke(null, keyCode));

						if (keyCode == (int) nativeKeyEventClass.getField("VC_ESCAPE").get(null)) {
							Method unregisterNativeHookMethod = globalScreenClass.getMethod("unregisterNativeHook");
							unregisterNativeHookMethod.invoke(null);

                            System.out.println("ESC key pressed, exiting editing mode.");
						}
                        
                    } else if (method.getName().equals("nativeKeyReleased")) {
                        // nativeKeyReleased event handling
                    } else if (method.getName().equals("nativeKeyTyped")) {
                        // nativeKeyTyped event handling
                    }
                    return null;
                }
                );

                Method registerNativeHookMethod = globalScreenClass.getMethod("registerNativeHook");
                Method addNativeKeyListenerMethod = globalScreenClass.getMethod("addNativeKeyListener", nativeKeyListenerClass);
                
                registerNativeHookMethod.invoke(null);
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