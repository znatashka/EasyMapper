package ru.indigo.easymapper.model.client;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientWithCollection {

    private List<Client> servers = new ArrayList<>();

    public static ClientWithCollection create() {
        ClientWithCollection clientWithCollection = new ClientWithCollection();
        Client server = new Client();
        server.setNumber(1);
        clientWithCollection.getServers().add(server);

        server = new Client();
        server.setNumber(2);
        clientWithCollection.getServers().add(server);

        server = new Client();
        server.setNumber(3);
        clientWithCollection.getServers().add(server);
        return clientWithCollection;
    }
}
