package com.example.petwebapplication.controllers;

import com.example.petwebapplication.interfaces.Theme;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ThemeController {

    @Inject
    private Theme theme;

    public String getActiveStyleSheet() {
        return theme.getStyleSheet();
    }
}
