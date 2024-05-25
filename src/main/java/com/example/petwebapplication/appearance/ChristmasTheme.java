package com.example.petwebapplication.appearance;

import com.example.petwebapplication.interfaces.Theme;
import jakarta.enterprise.inject.Alternative;

@Alternative
public class ChristmasTheme implements Theme {
    @Override
    public String getStyleSheet() {
        return "christmas.css";
    }
}
