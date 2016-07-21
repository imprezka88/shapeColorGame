package com.ewareza.shapegame.player;

public enum PlayerType {
    SOUND("sound"),
    SPEECH("speech");

    private String prefix;

    PlayerType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
