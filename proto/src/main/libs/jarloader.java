package main.libs;

import java.net.URL;
import java.net.URLClassLoader;

public class jarloader extends URLClassLoader {
    public jarloader(String jarPath) throws Exception {
        super(new URL[]{new URL("file:" + jarPath)});
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}