package ru.indigo.easymapper.model.server;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
public class ServerFull {

    private Integer number;
    private String string;
    private Server object;
    private Set<Server> servers = new HashSet<>();
    private Server[] serversArray;
    private ServerWithEnum.ServerEnum enumValue;
    private ServerWithEnum.ServerEnum clientNotEnum;

    public static ServerFull create() {
        ServerFull full = new ServerFull();
        full.setNumber(1);
        full.setString("text");
        full.setObject(new Server(100));
        full.setServersArray(new Server[]{new Server(2)});
        full.setServers(new HashSet<>(Arrays.asList(new Server(3), new Server(4))));
        full.setEnumValue(ServerWithEnum.ServerEnum.VALUE);
        full.setClientNotEnum(ServerWithEnum.ServerEnum.VALUE);
        return full;
    }

    public Server findServerByNumber(int number) {
        return this.servers.stream().filter(server -> server.getNumber() == number).findFirst().orElse(null);
    }
}
