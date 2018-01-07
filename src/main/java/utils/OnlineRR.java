package utils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class OnlineRR {

    private static final String IP = "34.240.50.232:5000";

    /**
     * Send a POST request to the server to update the file in the web
     * @param fileName of the file to sync, must include the .xml extension by now the only supported by the server
     * @throws IOException when it fails the connection to the server
     */
    public static int HttpPOSTFile(String fileName) throws IOException {
        //TODO: Change URL when deploy to the server
        URL url = new URL("http://" + IP + "/file/" + fileName);

        URLConnection con = url.openConnection();

        HttpURLConnection http = (HttpURLConnection)con;

        http.setRequestMethod("POST"); // PUT is another valid option

        http.setDoOutput(true);
        String boundary = UUID.randomUUID().toString();
        byte[] boundaryBytes = ("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8);
        byte[] finishBoundaryBytes = ("--" + boundary + "--").getBytes(StandardCharsets.UTF_8);
        http.setRequestProperty("Content-Type",
                "multipart/form-data; charset=UTF-8; boundary=" + boundary);

        // Enable streaming mode with default settings
        http.setChunkedStreamingMode(0);

        try(OutputStream out = http.getOutputStream()) {
            // Send HEADER
            out.write(boundaryBytes);

            // Send FILE
            try(InputStream file = new FileInputStream(GetResources.getFilePath("Data") + "/" + fileName)) {
                String o = "Content-Disposition: form-data; name=\"" + URLEncoder.encode(fileName,"UTF-8")
                        + "\"; filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"\r\n\r\n";
                out.write(o.getBytes(StandardCharsets.UTF_8));
                byte[] buffer = new byte[2048];
                for (int n = 0; n >= 0; n = file.read(buffer))
                    out.write(buffer, 0, n);
                out.write("\r\n".getBytes(StandardCharsets.UTF_8));
            }

            // Finish the request
            out.write(finishBoundaryBytes);
            return http.getResponseCode();
        }
    }

    /** Sends a GET requests to the server, wich returns the file with fileName requested
     * @param fileName of the file to get from the server
     * @throws IOException when it fails the connection to the server
     */
    public static int HttpGETFile(String fileName) throws IOException {
        URL url = new URL("http://" + IP + "/file/" + fileName);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setRequestMethod("GET");

        InputStream in = http.getInputStream();

        File targetFile = new File(GetResources.getFilePath("Data") + "/" + fileName);
        Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        in.close();

        return http.getResponseCode();
    }
}
