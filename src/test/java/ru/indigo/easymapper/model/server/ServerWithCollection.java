package ru.indigo.easymapper.model.server;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ServerWithCollection {

    private Set<Server> servers = new HashSet<>();
    private Server[] serversArray;

    public static ServerWithCollection create() {
        ServerWithCollection serverWithCollection = new ServerWithCollection();
        for (int i = 0; i < 3; i++) {
            Server server = new Server();
            server.setNumber(i);
            serverWithCollection.getServers().add(server);
        }
        return serverWithCollection;
    }

    public static ServerWithCollection createWArray() {
        ServerWithCollection serverWithCollection = new ServerWithCollection();
        Server[] servers = new Server[3];
        for (int i = 0; i < servers.length; i++) {
            Server server = new Server();
            server.setNumber(i);
            servers[i] = server;
        }
        serverWithCollection.setServersArray(servers);
        return serverWithCollection;
    }
}
