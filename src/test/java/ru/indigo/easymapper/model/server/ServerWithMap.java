package ru.indigo.easymapper.model.server;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class ServerWithMap {

    private Map<Integer, Set<String>> map = new HashMap<>();

    public static ServerWithMap create() {
        ServerWithMap server = new ServerWithMap();
        for (int i = 0; i < 3; i++) {
            Set<String> list = new HashSet<>();
            for (int j = 0; j < 2; j++) {
                list.add("value_i_" + i + "_j_" + j);
            }
            server.getMap().put(i, list);
        }
        return server;
    }
}
