package ru.indigo.easymapper.model.client;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClientFull {

    private int number;
    private Long longNumber;
    private String string;
    private Client object;
    private List<Client> servers = new ArrayList<>();
    private Client[] serversArray;
    private ClientWithEnum.ClientEnum enumValue;
    private String clientNotEnum;
    private Map<Integer, List<Client>> map = new HashMap<>();
}
