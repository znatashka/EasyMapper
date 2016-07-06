package ru.indigo.easymapper.model.server;

import lombok.Data;
import ru.indigo.easymapper.annotations.MappingToField;

import java.util.*;

@Data
public class ServerFull {

    private Integer number;
    private Long longNumber;
    private String string;
    private Server object;
    private boolean isTrue;
    private Set<Server> servers = new HashSet<>();
    private Server[] serversArray;
    private ServerWithEnum.ServerEnum enumValue;
    private ServerWithEnum.ServerEnum clientNotEnum;
    private Map<Integer, List<Server>> map = new HashMap<>();
    @MappingToField("clientMapToField")
    private Server serverMapToField;

    public static ServerFull create() {
        ServerFull full = new ServerFull();
        full.setNumber(1);
        full.setLongNumber(Long.MAX_VALUE);
        full.setString("text");
        full.setObject(new Server(100));
        full.setTrue(true);
        full.setServerMapToField(new Server(60));
        full.setServersArray(new Server[]{new Server(2)});
        full.setServers(new HashSet<>(Arrays.asList(new Server(3), new Server(4))));
        full.setEnumValue(ServerWithEnum.ServerEnum.VALUE);
        full.setClientNotEnum(ServerWithEnum.ServerEnum.VALUE);
        full.getMap().put(Integer.MAX_VALUE, Arrays.asList(new Server(10_000), new Server(20_000)));
        return full;
    }

    public Server findServerByNumber(int number) {
        return this.servers.stream().filter(server -> server.getNumber() == number).findFirst().orElse(null);
    }
}
