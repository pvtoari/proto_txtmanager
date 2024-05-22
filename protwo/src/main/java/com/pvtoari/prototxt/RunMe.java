package com.pvtoari.prototxt;

import com.pvtoari.prototxt.libs.sanCLI.*;

public class RunMe {

    public static CLIinstance instance = null;
    public static void main(String[] args) {
        instance = new CLIinstance();
        instance.run();
    }
}