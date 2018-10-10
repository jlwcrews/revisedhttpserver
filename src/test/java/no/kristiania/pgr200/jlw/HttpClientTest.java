package no.kristiania.pgr200.jlw;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import no.kristiania.pgr200.jlw.client.HttpClientGETRequest;
import no.kristiania.pgr200.jlw.client.HttpClientResponse;
import org.junit.Test;

public class HttpClientTest {

    @Test
    public void shouldReadStatusCode() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("urlecho.appspot.com", 80, "/echo?status=200");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("urlecho.appspot.com", 80, "/echo?status=404");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("urlecho.appspot.com",
                80, "/echo?status=307&Location=http%3A%2F%2Fwww.google.com");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
    }

    @Test
    public void shouldReadResponseBody() throws IOException {
        HttpClientGETRequest request = new HttpClientGETRequest("urlecho.appspot.com",
                80, "/echo?body=Hello+world!");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }


    public static void main(String[] args) throws IOException {

    }


}
