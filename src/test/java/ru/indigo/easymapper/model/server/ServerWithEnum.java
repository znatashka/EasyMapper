package ru.indigo.easymapper.model.server;

import lombok.Data;

@Data
public class ServerWithEnum {

    private ServerEnum enumValue;
    private ServerEnum clientNotEnum;

    public enum ServerEnum {
        VALUE
    }
}
