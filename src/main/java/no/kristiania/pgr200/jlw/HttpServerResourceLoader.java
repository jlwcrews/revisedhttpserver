package no.kristiania.pgr200.jlw;

import java.io.*;

public class HttpServerResourceLoader {

    private HttpServerRequest request;
    public String resourceBody;
    public String resourceName;

    public HttpServerResourceLoader(HttpServerRequest request){
        this.request = request;
        //if status code isn't 200, it's an error and let the builder deal with it
        //if path contains "echo", then it's an echo request and let the builder deal with it
        if(request.getStatusCode()== 200 && !request.getPath().contains("echo")) {
            setResource();
            loadResource();
        }
    }

    public void setResource(){
        if(request.getPath().isEmpty()){
            resourceName = HttpServerConfig.DEFAULT_FILE;
        } else{
            resourceName = request.getPath();
        }
    }

    public void loadResource(){
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
            resourceBody = fileContents.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find resource.");
            request.setStatusCode(404);
        } catch (IOException e) {
            System.out.println("Error loading resource.");
            request.setStatusCode(500);
        }
    }
}
