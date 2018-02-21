package com.e.amichai.myapplication;

import java.util.ArrayList;

public class GameTheme {
    public static final int NUMBER_OF_LEVELS = 10;

    public static Theme theme;

    public static final String DEFAULT_THEME= "classic";

    public static ArrayList<GameLevel> gameLevels;

    public static GameLevel classic;
    public static GameLevel trump;
    public static GameLevel vitas;
    public static GameLevel quagmire;
    public static GameLevel borat;
    public static GameLevel obama;
    public static GameLevel dalaiLama;
    public static GameLevel oprah;
    public static GameLevel timberlake;
    public static GameLevel einstein;
    public static GameLevel currentGameLevel;

    public GameTheme(){
        theme = new Theme();

        classic = new GameLevel("classic");
        trump = new GameLevel("trump");
        vitas = new GameLevel("vitas");
        quagmire = new GameLevel("quagmire");
        borat = new GameLevel("borat");
        obama = new GameLevel("obama");
        dalaiLama = new GameLevel("dalai lama");
        oprah = new GameLevel("oprah");
        timberlake = new GameLevel("timberlake");
        einstein = new GameLevel("einstein");

        gameLevels = new ArrayList<GameLevel>();

        gameLevels.add(0,classic);
        gameLevels.add(1,trump);
        gameLevels.add(2,quagmire);
        gameLevels.add(3,vitas);
        gameLevels.add(4,borat);
        gameLevels.add(5,obama);
        gameLevels.add(6,dalaiLama);
        gameLevels.add(7,oprah);
        gameLevels.add(8,timberlake);
        gameLevels.add(9,einstein);

        currentGameLevel = gameLevels.get(0);
        trump.setLockedStatus(false);
        classic.setLockedStatus(false);
        einstein.setLockedStatus(false);
        setLevelBoardSizes();
        setLevelNumberOfBombs();
    }

    private void setLevelBoardSizes() {
        for (int i=1; i<NUMBER_OF_LEVELS; i++){
            gameLevels.get(i).setBoardSizeEasyMode(GameLevel.EASY_BOARD_SIZE+i);
            gameLevels.get(i).setBoardSizeIntermediateMode(GameLevel.INTERMEDIATE_BOARD_SIZE+i);
            if (i>6){
                gameLevels.get(i).setBoardSizeProMode(GameLevel.PRO_BOARD_SIZE+6);
            } else {
                gameLevels.get(i).setBoardSizeProMode(GameLevel.PRO_BOARD_SIZE+i);
            }
        }
    }


    private void setLevelNumberOfBombs(){
        for (int i=1; i<NUMBER_OF_LEVELS; i++){
            gameLevels.get(i).setNumberOfBombsEasyMode((gameLevels.get(i).getBoardSizeEasyMode()*gameLevels.get(i).getBoardSizeEasyMode())/6);
            gameLevels.get(i).setNumberOfBombsIntermediateMode((gameLevels.get(i).getBoardSizeIntermediateMode()*gameLevels.get(i).getBoardSizeIntermediateMode())/6+i);
            if (i>6){
                gameLevels.get(i).setNumberOfBombsHardMode(GameLevel.PRO_NUMBER_OF_MINES+(6*6)+6*2 );
            } else {
                gameLevels.get(i).setNumberOfBombsHardMode(GameLevel.PRO_NUMBER_OF_MINES+(i*i)+i*2 );
            }
        }
    }
    public boolean allModesWon(GameLevel g) {
        if (g.getBestTimeProMode() == Integer.MAX_VALUE || g.getBestIntermediateMode() == Integer.MAX_VALUE || g.getBestTimeEasyMode() == Integer.MAX_VALUE){
            return false;
        }

        return true;
    }
    public void setCurrentGameLevel(String gameLevel){
        switch (gameLevel) {
            case "vitas":
                currentGameLevel = vitas;
                break;
            case "classic":
                currentGameLevel = classic;
                break;
            case "trump":
                currentGameLevel = trump;
                break;
            case "quagmire":
                currentGameLevel = quagmire;
                break;
            case "borat":
                currentGameLevel = borat;
                break;
            case "obama":
                currentGameLevel = obama;
                break;
            case "dalai lama":
                currentGameLevel = dalaiLama;
                break;
            case "oprah":
                currentGameLevel = oprah;
                break;
            case "timberlake":
                currentGameLevel = timberlake;
                break;
            case "einstein":
                currentGameLevel = einstein;
                break;
        }
    }
}
