package ru.indigo.easymapper.model.client;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClientWithMap {

    private Map<Integer, List<String>> map = new HashMap<>();
}
