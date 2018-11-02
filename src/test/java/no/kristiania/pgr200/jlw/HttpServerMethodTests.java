package no.kristiania.pgr200.jlw;

import no.kristiania.pgr200.jlw.server.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerMethodTests {

    @Test
    private void ShouldParseRequestLine() throws IOException{
        InputStream input = createInputStream("GET / HTTP/1.1");
        HttpServerParserRequest parser = new HttpServerParserRequest();
        HttpServerRequest request = new HttpServerRequest();


    }

    private InputStream createInputStream(String testString) {
        return new ByteArrayInputStream(testString.getBytes());
    }



}
