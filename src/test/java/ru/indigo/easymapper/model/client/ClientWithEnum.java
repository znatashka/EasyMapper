package ru.indigo.easymapper.model.client;

import lombok.Data;
import lombok.Getter;

@Data
public class ClientWithEnum {

    private ClientEnum enumValue;
    private String clientNotEnum;

    public enum ClientEnum {
        VALUE(1);

        @Getter
        private int i;

        ClientEnum(int i) {
            this.i = i;
        }
    }
}
