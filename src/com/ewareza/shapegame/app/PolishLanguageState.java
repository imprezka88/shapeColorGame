package com.ewareza.shapegame.app;

import java.util.Locale;

public class PolishLanguageState implements LanguageState {
    private static LanguageState instance = new PolishLanguageState();
    private Locale locale = new Locale("pl");

    @Override
    public void change(ShapeGameApplication application) {
        application.setLanguageState(EnglishLanguageState.newInstance());
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public static LanguageState newInstance() {
        return instance;
    }
}
