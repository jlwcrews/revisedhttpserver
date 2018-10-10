package no.kristiania.pgr200.jlw;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;

public class HttpServerRequestHandlerURL implements HttpServerRequestHandler{

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        String resourceName;
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
                StringBuffer fileContents = new StringBuffer();
                String fileInput = br.readLine();
                while (fileInput != null) {
                    fileContents.append(fileInput);
                    fileInput = br.readLine();
                }
                br.close();
                response.setBody(fileContents.toString());
                response.setContentType(getContentType(file));

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

    private String getContentType(File file) {
        String mimeType = MimetypesFileTypeMap
                .getDefaultFileTypeMap()
                .getContentType(file);
        return mimeType;
    }


}
