package com.ewareza.shapegame.app;

import java.util.Locale;

public interface LanguageState {
    void change(ShapeGameApplication application);

    Locale getLocale();
}
