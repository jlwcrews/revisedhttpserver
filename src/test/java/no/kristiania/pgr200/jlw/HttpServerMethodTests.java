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

    private InputStream createInputStream(String testString) {
        return new ByteArrayInputStream(testString.getBytes());
    }



}
