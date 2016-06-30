package ru.indigo.easymapper.model.server;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ServerWithCollection {

    private Set<Server> servers = new HashSet<>();

    public static ServerWithCollection create() {
        ServerWithCollection serverWithCollection = new ServerWithCollection();
        Server server = new Server();
        server.setNumber(1);
        serverWithCollection.getServers().add(server);

        server = new Server();
        server.setNumber(2);
        serverWithCollection.getServers().add(server);

        server = new Server();
        server.setNumber(3);
        serverWithCollection.getServers().add(server);
        return serverWithCollection;
    }
}
