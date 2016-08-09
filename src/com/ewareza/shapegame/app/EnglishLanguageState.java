package com.ewareza.shapegame.app;

import java.util.Locale;

public class EnglishLanguageState implements LanguageState {
    private static LanguageState instance = new EnglishLanguageState();
    private Locale locale = new Locale("en");

    @Override
    public void change(ShapeGameApplication application) {
        application.setLanguageState(PolishLanguageState.newInstance());
    }

    public static LanguageState newInstance() {
        return instance;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
