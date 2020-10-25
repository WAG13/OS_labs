package functions;

import java.io.File;
import java.io.IOException;

public class FuncProcess {
    private static Process process;

    public FuncProcess() {}

    public static void execute(Class cls) throws IOException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = cls.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);
        builder.inheritIO();

        process = builder.start();
    }

    public static void destroy(){
        process.destroy();
    }

}