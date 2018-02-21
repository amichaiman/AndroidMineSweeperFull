package com.e.amichai.myapplication;

import android.widget.Button;

public class Theme {
    private String themeName;

    public static final String DEFAULT_THEME = "classic";

    public Theme(String themeName){
        this.themeName = themeName;
    }

    public Theme() {
        themeName = new String(DEFAULT_THEME);
    }

    public String getThemeName(){
        return themeName;
    }

    public void setThemeName(String themeName){
        this.themeName = themeName;
    }
    public void smileyGameImage(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.game_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.game); break;
            case "trump":
                b.setBackgroundResource(R.drawable.game_trump); break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.game_quagmire); break;
            case "borat":
                b.setBackgroundResource(R.drawable.game_borat); break;
            case "obama":
                b.setBackgroundResource(R.drawable.game_obama); break;
            case "dalai lama":
                b.setBackgroundResource(R.drawable.game_dalai_lama); break;
            case "oprah":
                b.setBackgroundResource(R.drawable.oprah_game); break;
            case "timberlake":
                b.setBackgroundResource(R.drawable.timberlake_game); break;
            case "einstein":
                b.setBackgroundResource(R.drawable.game_einstein); break;
        }
    }

    public void smileyPressedImage(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.pressed_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.pressed); break;
            case "trump":
                b.setBackgroundResource(R.drawable.pressed_trump); break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.pressed_quagmire); break;
            case "borat":
                b.setBackgroundResource(R.drawable.pressed_borat); break;
            case "obama":
                b.setBackgroundResource(R.drawable.pressed_obama); break;
            case "dalai lama":
                b.setBackgroundResource(R.drawable.pressed_dalai_lama); break;
            case "oprah":
                b.setBackgroundResource(R.drawable.pressed_oprah); break;
            case "timberlake":
                b.setBackgroundResource(R.drawable.timberlake_pressed); break;
            case "einstein":
                b.setBackgroundResource(R.drawable.pressed_einstein); break;
        }
    }


    public void smileyWon(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.win_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.win); break;
            case "trump":
                b.setBackgroundResource(R.drawable.win_trump); break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.win_quagmire); break;
            case "borat":
                b.setBackgroundResource(R.drawable.win_borat); break;
            case "obama":
                b.setBackgroundResource(R.drawable.win_obama); break;
            case "dalai lama":
                b.setBackgroundResource(R.drawable.win_dalai_lama); break;
            case "oprah":
                b.setBackgroundResource(R.drawable.win_oprah); break;
            case "timberlake":
                b.setBackgroundResource(R.drawable.timberlake_win); break;
            case "einstein":
                b.setBackgroundResource(R.drawable.win_einstein); break;
        }
    }


    public void smileyLost(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.lose_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.lose); break;
            case "trump":
                b.setBackgroundResource(R.drawable.lose_trump); break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.lose_quagmire); break;
            case "borat":
                b.setBackgroundResource(R.drawable.lose_borat); break;
            case "obama":
                b.setBackgroundResource(R.drawable.lose_obama); break;
            case "dalai lama":
                b.setBackgroundResource(R.drawable.lose_dalai_lama); break;
            case "oprah":
                b.setBackgroundResource(R.drawable.lose_oprah); break;
            case "timberlake":
                b.setBackgroundResource(R.drawable.timberlake_lose); break;
            case "einstein":
                b.setBackgroundResource(R.drawable.lose_einstein); break;
        }
    }

    public void setThemeButton(Button b) {
        switch (GameTheme.currentGameLevel.getThemeName()) {
            case "vitas":
                b.setBackgroundResource(R.drawable.vitas);
                break;
            case "classic":
                b.setBackgroundResource(R.drawable.smiley);
                break;
            case "trump":
                b.setBackgroundResource(R.drawable.trump);
                break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.quagmire);
                break;
            case "borat":
                b.setBackgroundResource(R.drawable.borat);
                break;
            case "obama":
                b.setBackgroundResource(R.drawable.obama);
                break;
            case "dalai lama":
                b.setBackgroundResource(R.drawable.dalai_lama); break;
            case "oprah":
                b.setBackgroundResource(R.drawable.oprah_winfrey); break;
            case "timberlake":
                b.setBackgroundResource(R.drawable.timberlake); break;
            case "einstein":
                b.setBackgroundResource(R.drawable.einstein); break;
        }
    }

    public void themeButtonPressed(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.vitas_sunglasses);
                break;
            case "trump":
                b.setBackgroundResource(R.drawable.trump_sunglasses);
                break;
            case "quagmire":
                b.setBackgroundResource(R.drawable.quagmire_sunglasses);
                break;
            case "borat":
                b.setBackgroundResource(R.drawable.borat_sunglasses);
                break;
            case "obama":
                b.setBackgroundResource(R.drawable.obama_sunglasses);
                break;
        }
    }
}
