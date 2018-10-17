package no.kristiania.pgr200.jlw.server;

import java.io.*;
import java.nio.file.Files;

public class HttpServerRequestHandlerURL implements HttpServerRequestHandler{

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        String resourceName;

        if(request.getPath().contains("echo")){
            return false;
        }
        if(request.getPath().isEmpty()){
            resourceName = HttpServerConfig.DEFAULT_FILE;
        } else{
            resourceName = request.getPath();
        }

        if(!resourceName.contentEquals("")) {
            File file = new File(resourceName);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                StringBuilder fileContents = new StringBuilder();
                String fileInput = br.readLine();
                while (fileInput != null) {
                    fileContents.append(fileInput);
                    fileInput = br.readLine();
                }
                br.close();
                response.setBody(fileContents.toString());
                response.setContentType(getFileContentType(resourceName));
                response.setStatusCode(200);

            } catch (FileNotFoundException e) {
                response.setStatusCode(404);
                response.setBody("");
            } catch (IOException e) {
                response.setStatusCode(500);
                response.setBody("");
            }
        } else{
            response.setStatusCode(200);
            response.setBody("");
        }
        return true;
    }

    public String getFileContentType(String fileName) {
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try
        {
            fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException ioException)
        {
            System.out.println(
                    "ERROR: Unable to determine file type for " + fileName
                            + " due to exception " + ioException);
        }
        return fileType;
    }


}
