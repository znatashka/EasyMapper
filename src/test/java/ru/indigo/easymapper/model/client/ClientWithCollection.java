package ru.indigo.easymapper.model.client;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientWithCollection {

    private List<Client> servers = new ArrayList<>();
    private Client[] serversArray;

    public static ClientWithCollection create() {
        ClientWithCollection clientWithCollection = new ClientWithCollection();
        for (int i = 0; i < 3; i++) {
            Client server = new Client();
            server.setNumber(i);
            clientWithCollection.getServers().add(server);
        }
        return clientWithCollection;
    }
}
