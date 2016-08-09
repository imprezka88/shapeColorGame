package com.ewareza.shapegame.app;

import java.util.Locale;

public class LanguageStateFactory {
    public static LanguageState getState(Locale locale) {
        if(locale.getLanguage().equals("pl")) {
            return PolishLanguageState.newInstance();
        }

        return EnglishLanguageState.newInstance();
    }
}
