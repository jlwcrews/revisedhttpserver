package no.kristiania.pgr200.jlw;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    HttpServerListener server = new HttpServerListener();
    try {
      server.start();
    } catch (IOException e) {
      System.out.println("Error starting server.");
      e.printStackTrace();
    }
  }
}
