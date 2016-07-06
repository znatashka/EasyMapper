package ru.indigo.easymapper.model.client;

import lombok.Data;

import java.util.*;

@Data
public class ClientFull {

    private int number;
    private Long longNumber;
    private String string;
    private Client object;
    private Boolean isTrue;
    private List<Client> servers = new ArrayList<>();
    private Client[] serversArray;
    private ClientWithEnum.ClientEnum enumValue;
    private String clientNotEnum;
    private Map<Integer, List<Client>> map = new HashMap<>();
    private Client clientMapToField;

    public static ClientFull create() {
        ClientFull full = new ClientFull();
        full.setNumber(1);
        full.setLongNumber(Long.MAX_VALUE);
        full.setString("text");
        full.setObject(new Client(100));
//        full.setIsTrue(true);
        full.setServersArray(new Client[]{new Client(2)});
        full.setServers(Arrays.asList(new Client(3), new Client(4)));
        full.setEnumValue(ClientWithEnum.ClientEnum.VALUE);
        full.setClientNotEnum("VALUE");
        full.getMap().put(Integer.MAX_VALUE, Arrays.asList(new Client(10_000), new Client(20_000)));
        return full;
    }

    public Client findServerByNumber(Integer number) {
        return this.servers.stream().filter(server -> server.getNumber() == number).findFirst().orElse(null);
    }
}
