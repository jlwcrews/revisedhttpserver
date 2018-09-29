package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class HttpServerParser {

    public InputStream input;
    public HttpServerRequest request;

    public void parseRequestLine() throws IOException{
            String requestLine[] = readNextLine(input).split(" ");
            request.setHttpMethod(requestLine[0]);
            request.setHttpVersion(requestLine[2]);
            request.setURL(requestLine[1]);
            parseParameters(parsePath());
    }

    public String parsePath() {
        String URL[] = request.getURL().split("[?]");
        request.setPath(URL[0]);
        return URL[1];
    }

    public void parseParameters(String paramString) {
        String queryParams[] = paramString.split("&");
        for (String param : queryParams) {
            int delimiterPos = param.indexOf("=");
            try {
                request.setParameter(param.substring(0, delimiterPos), URLDecoder.decode(param.substring(delimiterPos + 1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error parsing parameters");
            }
        }
    }

    abstract void parse() throws IOException;

    abstract void parseHeaderLines(String line) throws IOException;

    public static String readNextLine(InputStream input) throws IOException {
        StringBuilder nextLine = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {
            if (c == '\r') {
                input.mark(1);
                input.read();
                break;
            }
            nextLine.append((char) c);
        }
        return nextLine.toString();
    }
}