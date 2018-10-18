package no.kristiania.pgr200.jlw;

import no.kristiania.pgr200.jlw.client.HttpClientGETRequest;
import no.kristiania.pgr200.jlw.client.HttpClientPOSTRequest;
import no.kristiania.pgr200.jlw.client.HttpClientResponse;
import no.kristiania.pgr200.jlw.server.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.Arrays;

public class HttpServerTest {

    private static HttpServerListener server;
    int port = 0;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Test
    public void shouldReturnResource() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(), "/");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldWriteStatusCode() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(), "/echo?status=200");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(), "/echo?status=404");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
     public void shouldReadResponseBody() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo?body=Hello+world!");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }

    @Test
    public void shouldEchoResponseBody() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }

    @Test
    public void shouldHandleEchoEmptyParam() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo?");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldHandleEchoNoParams() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldWriteStatusCodePOST() throws IOException {
        HttpClientPOSTRequest request = new HttpClientPOSTRequest("localhost", server.getPort(), "/echo",
                "status=200&body=hello+idiot");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().contains("hello idiot"));
    }

    @Test
    public void shouldHandleNoBodyPOST() throws IOException{
        HttpClientPOSTRequest request = new HttpClientPOSTRequest("localhost", server.getPort(), "/echo",
                "");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isEmpty());
    }

    @Test
    public void shouldRejectGETRequestWithInvalidPath() throws IOException{
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/bogus");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectPOSTRequestWithGarbageBody() throws IOException{
        HttpClientPOSTRequest request = new HttpClientPOSTRequest("localhost", server.getPort(),
                "/bogus", "I+AM+A+BOGUS+POST+REQUEST");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectInvalidMethodwithValidPath() throws IOException{
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/echo?body=hello+idiot", "PUT");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(405);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectDeleteMethod() throws IOException{
        HttpClientGETRequest request = new HttpClientGETRequest("localhost", server.getPort(),
                "/", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(405);
        assertThat(response.getBody()).isEqualTo("");
    }

}