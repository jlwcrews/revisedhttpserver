package no.kristiania.pgr200.jlw;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

public class HttpServerTest {

    private static HttpServerListener server;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new HttpServerListener(0);
        server.start();
    }

    @Test
    public void shouldWriteStatusCode() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo?status=200");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo?status=404");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
    public void shouldReadResponseBody() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?body=Hello+world!");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }

    @Test
    public void shouldEchoResponseBody() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }

    @Test
    public void shouldHandleEmptyParam() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldHandleNoParams() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    public static void main(String[] args) throws IOException {

    }


}