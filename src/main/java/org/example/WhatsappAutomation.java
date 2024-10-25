package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WhatsappAutomation {

    public static void main(String[] args) {
        try {
            // Mensagem a ser enviada
            String message = "Neguinho sensação na area!";
            // Codificar a mensagem
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            // Números dos contatos
            String[] numeros = {
                    "558293078187", // número 1
                    "558288956028"  // número 2
            };

            // Cria o cliente HTTP
            CloseableHttpClient httpClient = HttpClients.createDefault();

            for (String number : numeros) {
                // Monta a URL
                String url = "http://localhost:3000/send?number=" + number + "&message=" + encodedMessage;
                HttpGet request = new HttpGet(url);
                System.out.println("Requisição preparada para a URL: " + url);

                // Executa a requisição
                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    // Aqui você pode verificar a resposta se necessário
                    System.out.println("Resposta recebida: " + response.getStatusLine());
                } catch (IOException e) {
                    System.err.println("Erro ao enviar mensagem para " + number + ": " + e.getMessage());
                }
            }

            // Fecha o cliente HTTP
            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
