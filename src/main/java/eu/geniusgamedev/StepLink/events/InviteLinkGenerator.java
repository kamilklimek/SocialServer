package eu.geniusgamedev.StepLink.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

@Component
public class InviteLinkGenerator {
    private final String UUID_SEPARATOR = "-";
    private String host;
    private final int port;

    public InviteLinkGenerator(@Value("${server.port}") int port) {
        this.port = port;
        initHost();
    }

    private void initHost() {
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not a get local host address due to: " + e.getMessage());
        }
    }

    public String generateUniqueHash() {
        return generateWithUUID();
    }

    public String appendHostToLink(String hash) {
        return getHost() + hash;
    }

    private String getHost() {
        return host + ":" + port + "/invitation/";
    }

    private String generateWithUUID() {
        String uuidBase = UUID.randomUUID().toString();

        String base = cutPart(uuidBase);
        int hash = randomHash();

        return hashBase(base, hash);
    }

    private int randomHash() {
        Random random = new Random();

        return random.nextInt(231) + 7;
    }

    private String hashBase(String base, int hash) {
        StringBuilder builder = new StringBuilder();

        for (Character chr : base.toCharArray()) {
            String hashed = String.valueOf(chr % hash);
            builder.append(hashed);
        }

        return builder.toString();
    }

    private String cutPart(String uuidBase) {
        String [] parts = uuidBase.split(UUID_SEPARATOR);

        return parts[2] + parts[0];
    }
}
