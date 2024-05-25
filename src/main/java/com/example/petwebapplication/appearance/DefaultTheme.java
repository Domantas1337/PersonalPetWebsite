package com.example.petwebapplication.appearance;

import com.example.petwebapplication.interfaces.Theme;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;

@Default
public class DefaultTheme implements Theme {
    @Override
    public String getStyleSheet() {
        return "default.css";
    }
}

