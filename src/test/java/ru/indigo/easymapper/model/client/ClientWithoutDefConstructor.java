package ru.indigo.easymapper.model.client;

import lombok.Data;

@Data
public class ClientWithoutDefConstructor {

    private Integer number;

    private ClientWithoutDefConstructor() {
    }
}
