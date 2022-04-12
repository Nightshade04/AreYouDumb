package com.nightshade.areudumb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button yesButton;
    private Button noButton;
    private Button resetButton;
    private TextView textView;

    private final Random randomGenerator = new Random();
    private int counter = 0;
    private int yesCounter = 0;
    private float initialX;
    private float initialY;
    private String userName = "";
    private final String[] yesMessages = {"Knew it... LOL.  :3",
            "See, that wasn't so hard to accept... :3",
                                    "If you say so! XD"};
    private final String[] noMessages = {"What are you doing? You know you are dumb... Accept it.",
                                    "Damn dude, you persistent! Just accept that you are dumb!",
            "There ain't no use fighting it. You. Are. Dumb. Accept it.",
                                    "Are you dumb? If not, try closing me!!!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUserNameOfDevice();
        initializeWidgets();
    }

    // Removing this function as it requires a permission.
//    @SuppressLint("Range")
//    private void setUserNameOfDevice() {
//        ContentResolver cr=getContentResolver();
//        Cursor cursor = cr.query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
//        if(cursor.getCount()>0){
//            cursor.moveToFirst();
//            userName = cursor.getString(cursor.getColumnIndex(
//                    ContactsContract.Profile.DISPLAY_NAME));
//        }
//        cursor.close();
//    }

    private void setUserNameOfDevice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Give me your Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            userName = input.getText().toString();
            initializeWidgets();
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
           Toast.makeText(this, "So you won't give me your name huh? I will call you 'Silly Goose' instead.", Toast.LENGTH_LONG).show();
           userName = "Silly Goose";
            initializeWidgets();
        });
        builder.show();
    }

    private void initializeWidgets() {
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        resetButton = findViewById(R.id.resetButton);
        textView = findViewById(R.id.textView);
        textView.setText(textView.getText() + " " + userName);
        setColorOfButtons("#198754", "#DC3545");
    }

    private void setColorOfButtons(String yesColor, String noColor) {
        yesButton.setBackgroundColor(Color.parseColor(yesColor));
        noButton.setBackgroundColor(Color.parseColor(noColor));
    }

    public void yesButtonClick(View view) {
        textView.setText(getDifferentMessage(yesMessages));
        yesCounter += 1;
        noButton.setVisibility(View.INVISIBLE);
        if(counter == 12) {
            counter += 1;
            Toast.makeText(this, "Fas Gaye!!! LOL", Toast.LENGTH_LONG).show();
        }
    }

    public void noButtonClick(View view) {
        setInitialCoordinatesOfNoButton();
        Display display = getWindowManager().getDefaultDisplay();
        noButton.setX(randomGenerator.nextInt(display.getWidth() - 400));
        noButton.setY(randomGenerator.nextInt(display.getHeight() - 600));
        counter += 1;

        if(counter % 3 == 0) {
            textView.setText(getDifferentMessage(noMessages));
        }

        if(counter >= 12) {
            setColorOfButtons("#DC3545", "#198754");
        }

    }

    private void setInitialCoordinatesOfNoButton() {
        if(counter == 0) {
            initialX = noButton.getX();
            initialY = noButton.getY();
        }
    }

    public void resetButtonClick(View view) {
        setInitialCoordinatesOfNoButton();
        setColorOfButtons("#198754", "#DC3545");
        textView.setText(noMessages[3]);
        noButton.setX(initialX);
        noButton.setY(initialY);
        noButton.setVisibility(View.VISIBLE);
        yesButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, "So you wanna try again " + userName + "? Well, knock yourself out!", Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        if(yesCounter == 0) {
            Toast.makeText(this, "You cannot exit until you accept that you are DUMB!!!! " + userName, Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }

    private String getDifferentMessage(String[] messageRepo) {
        String message;
        do {
            message = messageRepo[randomGenerator.nextInt(messageRepo.length)];
        } while (textView.getText().equals(message));
        return message;
    }
}