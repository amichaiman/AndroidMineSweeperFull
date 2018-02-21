package com.e.amichai.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class SecondActivity extends AppCompatActivity {

    private Board board;
    private Button[][] buttons;
    private int boardSize;
    private int numberOfMines;
    private TableLayout boardTableLayout;
    private TextView numberOfMinesLeftTextView;
    private ImageView timerAnimationImageView;
    private TextView numberOfSecondsTextView;
    private FloatingActionButton flagButton;
    private ImageView flagImageView;

    private int time = 0;

    private MediaPlayer startGameMediaPlayer;
    private MediaPlayer winGameMediaPlayer;

    public static String alertDialogChoice;

    private Button smileButton;

    private Animations animations;

    public static Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        AdView mAdView= new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-9056258295474141/3753052531");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        alertDialogChoice = "back to home";

        Bundle bundle = getIntent().getExtras();
        boardSize = bundle.getInt("boardSize");
        numberOfMines = bundle.getInt("numberOfMines");

        animations = new Animations();

        timerAnimationImageView = (ImageView) findViewById(R.id.timerAnimationImageView);
        numberOfSecondsTextView = (TextView) findViewById(R.id.numberOfSecondsTextView);
        numberOfMinesLeftTextView = (TextView) findViewById(R.id.numberOfMinesLeftTextView);
        flagButton = (FloatingActionButton) findViewById(R.id.flagModeFloatingActionButton);
        flagImageView = (ImageView) findViewById(R.id.flagImageView);

        if (!settingsActivity.flagModeFloatingButton){
            flagButton.setVisibility(View.INVISIBLE);
        }

        setSoundByTheme();


        if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")) {
            startGameMediaPlayer.start();
        }

        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines));
        numberOfSecondsTextView.setText(Integer.toString(time));


        time = 0;
        t = new Thread(){
            @Override
            public void run() {
                try{
                    while (!isInterrupted() && !board.gameIsOver()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                numberOfSecondsTextView.setText(Integer.toString(time));
                                animations.time(timerAnimationImageView);
                                time+=1;
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e){

                }
            }
        };


        board = new Board(boardSize, numberOfMines);
        buttons = new Button[boardSize][boardSize];

        smileButton = (Button) findViewById(R.id.smileButton);
        GameTheme.theme.smileyGameImage(smileButton);

        smileButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    alertDialogChoice = "refresh";
                    onBackPressed();
                    return true;
                }
                GameTheme.theme.smileyGameImage(smileButton);
                return false;
            }
        });

        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (board.getFlagModeStatus()){
                    board.setFlagModeStatus(false);
                    flagImageView.setImageResource(R.drawable.flag);
                    flagButton.setImageResource(R.drawable.flag);
                } else {
                    board.setFlagModeStatus(true);
                    flagImageView.setImageResource(R.drawable.flag_pressed);
                    flagButton.setImageResource(R.drawable.flag_pressed);
                }
            }
        });

        createBoard();
        board.setButtons(buttons);

    }

    public void createBoard() {
        boardTableLayout = (TableLayout) findViewById(R.id.boardTableLayout);

        for (int row = 0; row < boardSize; row++) {
            TableRow curTableRow = new TableRow(this);
            curTableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            boardTableLayout.addView(curTableRow);
            for (int col = 0; col < boardSize; col++) {
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        board.reCloseSpots(view.getId()/boardSize,view.getId()%boardSize);
                        GameTheme.theme.smileyGameImage(smileButton);

                        board.buttonClicked(view.getId() / boardSize, view.getId() % boardSize);

                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));

                        if (board.gameIsOver()) {
                            board.unActivateButtons();
                            if (board.gameWon()) {
                                if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")){
                                    winGameMediaPlayer.start();
                                }

                                GameTheme.theme.smileyWon(smileButton);
                            } else {
                                GameTheme.theme.smileyLost(smileButton);
                                board.revealMines();
                            }
                            alertDialogEndOfGame();
                        }
                    }


                });
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            GameTheme.theme.smileyPressedImage(smileButton);
                            if (board.buttonIsVisible(view.getId()/boardSize,view.getId()%boardSize)) {
                                return false;
                            }
                            return false;
                        }
                        return false;
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        board.reCloseSpots(view.getId()/boardSize,view.getId()%boardSize);
                        GameTheme.theme.smileyGameImage(smileButton);
                        board.buttonLongClicked(view.getId() / boardSize, view.getId() % boardSize);
                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));
                        return true;
                    }
                });

                button.setId(row * boardSize + col);
                button.setBackgroundResource(R.drawable.square);
                curTableRow.addView(button);
                buttons[row][col] = button;

            }
        }
    }

    private void alertDialogEndOfGame() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SecondActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.game_finished_alert_dialog, null);
        TextView gameFinishedStatusTextView = (TextView) mView.findViewById(R.id.gameFinishedStatusTextView);
        TextView gameTimeTextView = (TextView) mView.findViewById(R.id.gameTimeTextView);
        Button previousLevelButton = (Button) mView.findViewById(R.id.previousLevelButton);
        Button refreshGameButton = (Button) mView.findViewById(R.id.refreshGameButton);
        final Button nextLevelButton = (Button) mView.findViewById(R.id.nextLevelButton);
        Button backToHomeButton = (Button) mView.findViewById(R.id.backToHomeButton);

        switch (MainActivity.gameMode){
            case "easy":
                previousLevelButton.setText("Previous level");
                nextLevelButton.setText("intermediate");
                break;
            case "intermediate":
                previousLevelButton.setText("easy");
                nextLevelButton.setText("pro");
                break;
            case "pro":
                previousLevelButton.setText("intermediate");
                nextLevelButton.setText("next level");
                break;
        }


        if (GameTheme.currentGameLevel.getThemeName().equals("classic") && MainActivity.gameMode.equals("easy") || !GameTheme.currentGameLevel.getThemeName().equals("classic") && MainActivity.gameMode.equals("easy") && GameTheme.currentGameLevel.getPreviousLevel().getLockedStatus()) {
            previousLevelButton.setEnabled(false);
            previousLevelButton.setBackgroundColor(0);
        }

        if (MainActivity.gameMode.equals("pro") && GameTheme.currentGameLevel.getThemeName().equals("einstein")){
            nextLevelButton.setEnabled(false);
            nextLevelButton.setBackgroundColor(0);
        }

        previousLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogChoice = "previous level";
                backToMain();
            }
        });

        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.gameMode.equals("pro") && !MainActivity.gameTheme.allModesWon(GameTheme.currentGameLevel) && GameTheme.currentGameLevel.hasNext() && GameTheme.currentGameLevel.getNextLevel().getLockedStatus()) {
                    Toast.makeText(getApplicationContext(), "Next level is locked. You must finish 'easy' 'intermediate' and 'pro' in order to open next level.", Toast.LENGTH_LONG).show();
                } else {
                    alertDialogChoice = "next level";
                    backToMain();
                }
            }
        });

        refreshGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogChoice = "refresh";
                backToMain();
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
        String timeInMinutes;
        gameFinishedStatusTextView.setText(board.gameWon() ? newBestTime() ? "NEW BEST TIME!" : "WE HAVE A WINNER!" : "HAHA YOU LOST!");
        timeInMinutes = Integer.toString(time / 60) + ":" + (time % 60 > 9 ? Integer.toString(time % 60 - 1) : "0" + Integer.toString(time % 60 - 1));
        gameTimeTextView.setText("Time: " + timeInMinutes);

        setAlertDialogBackground(mView);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void setAlertDialogBackground(View mView) {
        switch (GameTheme.currentGameLevel.getThemeName()) {
            case "vitas":
                mView.setBackgroundResource(R.drawable.vitas);
            break;
            case "classic":
                mView.setBackgroundResource(R.drawable.smiley);
            break;
            case "trump":
                mView.setBackgroundResource(R.drawable.trump);
            break;
            case "quagmire":
                mView.setBackgroundResource(R.drawable.quagmire);
            break;
            case "borat":
                mView.setBackgroundResource(R.drawable.borat);
            break;
            case "obama":
                mView.setBackgroundResource(R.drawable.obama);
            break;
            case "dalai lama":
                mView.setBackgroundResource(R.drawable.dalai_lama); break;
            case "oprah":
                mView.setBackgroundResource(R.drawable.oprah_winfrey); break;
            case "timberlake":
                mView.setBackgroundResource(R.drawable.timberlake); break;
            case "einstein":
                mView.setBackgroundResource(R.drawable.einstein); break;
        }
    }

    private boolean newBestTime(){
        if (MainActivity.gameMode == "easy") {
            if ( GameTheme.currentGameLevel.getBestTimeEasyMode() > time) {
                GameTheme.currentGameLevel.setBestTimeEasyMode(time);
                return true;
            }
        } else if (MainActivity.gameMode == "intermediate"){
            if ( GameTheme.currentGameLevel.getBestIntermediateMode() > time){
                GameTheme.currentGameLevel.setBestIntermediateMode(time);
                return true;
            }
        } else if (MainActivity.gameMode == "pro"){
            if ( GameTheme.currentGameLevel.getBestTimeProMode() > time){
                GameTheme.currentGameLevel.setBestTimeProMode(time);
                return true;
            }
        }
        return false;
    }
    private void setSoundByTheme() {
        switch (GameTheme.currentGameLevel.getThemeName()) {
            case "vitas":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_vitas);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_vitas);
                break;
            case "trump":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_trump);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_trump);
                break;
            case "quagmire":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_quagmire);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_quagmire); break;
            case "borat":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.start_game_borat);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_borat); break;
            case "obama":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.start_game_obama);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_obama); break;
            case "dalai lama":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_dalai_lama);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_dame_dalai_lama); break;
            case "oprah":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.oprah_start_game);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.oprah_win_game); break;
            case "timberlake":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.timberlake_start_game);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.justin_win); break;
            case "einstein":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.einstein_start_game);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.einstein_win_game); break;

        }
    }

    @Override
    public void onBackPressed() {
        backToMain();
    }

    private void backToMain() {
        Intent backToMain = new Intent(SecondActivity.this, MainActivity.class);

        if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")) {
            winGameMediaPlayer.stop();
            startGameMediaPlayer.stop();
        }

        backToMain.putExtra("gameLost", board.gameIsLost());
        if (board.gameWon()){
            backToMain.putExtra("gameWon", true);
            backToMain.putExtra("time", time-1);
            setResult(RESULT_OK, backToMain);
        }
        setResult(RESULT_OK, backToMain);
        finish();
        super.onBackPressed();
    }
}
