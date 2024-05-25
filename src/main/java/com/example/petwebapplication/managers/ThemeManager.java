package com.example.petwebapplication.managers;

import com.example.petwebapplication.interfaces.Theme;
import jakarta.inject.Inject;

public class ThemeManager {

    @Inject
    private Theme theme;

    public void applyTheme() {
        String cssPath = theme.getStyleSheet();
    }
}
