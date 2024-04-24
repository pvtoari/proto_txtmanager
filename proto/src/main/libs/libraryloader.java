package main.libs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class libraryloader {
    public static void loadLibrary(String name) throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        String path = "/native/" + osName + "/" + osArch + "/" + System.mapLibraryName(name);

        InputStream jarStream = libraryloader.class.getResourceAsStream("proto/lib/jnativehook-2.2.2.jar");
        File tempJar = File.createTempFile("jnativehook", ".jar");
        FileOutputStream fos = new FileOutputStream(tempJar);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = jarStream.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.close();

        JarFile jarFile = new JarFile(tempJar);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().equals(path)) {
                InputStream in = jarFile.getInputStream(entry);
                File tempLib = File.createTempFile(name, ".lib");
                fos = new FileOutputStream(tempLib);

                while ((read = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }

                fos.close();
                System.load(tempLib.getAbsolutePath());
                break;
            }
        }
    }
}