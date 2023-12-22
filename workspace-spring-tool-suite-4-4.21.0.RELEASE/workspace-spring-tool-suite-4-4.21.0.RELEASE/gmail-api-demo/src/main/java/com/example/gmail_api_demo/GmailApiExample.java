package com.example.gmail_api_demo;




import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;


public class GmailApiExample {
    private static final String APPLICATION_NAME = "GmailTask";
   // private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\user\\Documents\\workspace-spring-tool-suite-4-4.21.0.RELEASE\\gmail-api-demo\\client_secret.json";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ListMessagesResponse response = service.users().messages().list("me").execute();
        List<Message> messages = response.getMessages();

        if (messages != null) {
            for (Message message : messages) {
                Message fullMessage = service.users().messages().get("me", message.getId()).execute();
                String sender = getSender(fullMessage);
                String subject = getSubject(fullMessage);
                System.out.println("Sender: " + sender + ", Subject: " + subject);
            }
        } else {
            System.out.println("No messages found.");
        }
    }

    private static String getSender(Message message) {
        // Implement logic to extract sender information from the message
    	 List<MessagePartHeader> headers = message.getPayload().getHeaders();
         for (MessagePartHeader header : headers) {
             if ("From".equals(header.getName())) {
                 return header.getValue();
             }
         }
        return "Unknown Sender";
    }

    private static String getSubject(Message message) {
        // Implement logic to extract subject information from the message
    	List<MessagePartHeader> headers = message.getPayload().getHeaders();
        for (MessagePartHeader header : headers) {
            if ("Subject".equals(header.getName())) {
                return header.getValue();
            }
        }
        return "Unknown Subject";
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        List<String> scopes = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new com.google.api.client.util.store.FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        VerificationCodeReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}

