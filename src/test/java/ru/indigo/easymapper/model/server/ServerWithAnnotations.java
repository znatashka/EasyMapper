package ru.indigo.easymapper.model.server;

import lombok.Data;
import ru.indigo.easymapper.annotations.MappingToField;

@Data
public class ServerWithAnnotations {

    @MappingToField("clientString")
    private String serverString;
}
