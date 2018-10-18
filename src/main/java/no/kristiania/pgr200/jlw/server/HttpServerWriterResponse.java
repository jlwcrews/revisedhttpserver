package no.kristiania.pgr200.jlw.server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HttpServerWriterResponse implements  HttpServerWriter {

    public void write(OutputStream stream, HttpServerResponse response) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        writeStatus(writer, response);
        writeCommonHeaders(writer, response);
        writeAdditionalHeaders(writer, response);
        writer.write("\r\n");
        writeBody(writer, response);
        writer.flush();
    }

    private void writeBody(OutputStreamWriter writer, HttpServerResponse response) throws IOException {
        /*if (response.getBody() != null) {
            writer.write(response.getBody());
        } else {
            writer.write("");
        }*/
        writer.write(response.getBody().orElse("None"));
    }

    private void writeStatus(OutputStreamWriter writer, HttpServerResponse response) throws IOException {
        writer.write(response.getHttpVersion()+" "+response.getStatusCode()+" "+HttpServerStatusMessages.getStatusMessage(response.getStatusCode())+"\r\n");
    }

    private void writeCommonHeaders(OutputStreamWriter writer,  HttpServerResponse response) throws IOException {
        writeHeader(writer,"X-Server-Name", HttpServerConfig.SERVER_NAME);
        writeHeader(writer, "Date", LocalDate.now());
        writeHeader(writer, "Connection", "Close");
        if (response.getBody().isPresent()) {
            writeHeader(writer,"Content-Type", response.getContentType());
            writeHeader(writer, "Content-Length", response.getBody().get().length());
        }else {
            writeHeader(writer, "Content-Length", 0);
        }
    }

    private void writeAdditionalHeaders(OutputStreamWriter writer, HttpServerResponse response) throws IOException {
        HashMap<String,String> headers = response.getAdditionalHeaders();
        if (headers != null){
            for (Map.Entry<String, String> e : headers.entrySet()) {
                writeHeader(writer, e.getKey(), e.getValue());
            }
        }
    }

    private void writeHeader(OutputStreamWriter writer, String headerName, Object headerValue) throws IOException {
        writer.write(headerName+": "+headerValue+"\r\n");
    }
}
