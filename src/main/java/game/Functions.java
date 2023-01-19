package game;

import lombok.Getter;

public enum Functions {
    LETTER("LETTER"), KEY("KEY"), SPIN("SPIN");

    @Getter
    private final String function;

    Functions(String function){
        this.function = function;
    }
}
