package rick.and.morty.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static Status getStatusFromCode(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
