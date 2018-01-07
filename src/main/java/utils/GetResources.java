package utils;

import java.io.File;

public final class GetResources {

    public static final String fileSearchPrepend = "file:///";

    private GetResources() {
        throw new UnsupportedOperationException("GetResources is a utility class, do not create objects of it!");
    }

    public static File getFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = GetResources.class.getClassLoader();

        return new File(classLoader.getResource(fileName).getFile());
    }

    public static String getFilePath(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = GetResources.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        return file.getAbsolutePath();
    }

    public static String getURLFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = GetResources.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        return fileSearchPrepend + file.getAbsolutePath();
    }
}
