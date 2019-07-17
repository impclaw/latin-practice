package com.example.latinpractice;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PracticeActivity extends AppCompatActivity {
    private ExerciseGenerator generator;
    private Exercise current;

    private TextView questionText, extraText;
    private Button newButton;
    private Button[] answerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        questionText = findViewById(R.id.question);
        extraText = findViewById(R.id.extra);
        newButton = findViewById(R.id.newbutton);
        answerButtons = new Button[] {
                findViewById(R.id.answer1),
                findViewById(R.id.answer2),
                findViewById(R.id.answer3),
                findViewById(R.id.answer4)
        };

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        boolean singular = intent.getBooleanExtra("singular", true);
        boolean plural = intent.getBooleanExtra("plural", true);

        if(type.equals("noun")) {
            ArrayList<Integer> mode = intent.getIntegerArrayListExtra("mode");
            generator = new NounGenerator(mode, singular, plural);
        }

        for(final Button btn : answerButtons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String playerAnswer = btn.getText().toString();
                    if(playerAnswer.equals(current.getAnswer())) {
                        DrawableCompat.setTint(DrawableCompat.wrap(btn.getBackground()), Color.rgb(112, 212, 112));
                    } else {
                        DrawableCompat.setTint(DrawableCompat.wrap(btn.getBackground()), Color.rgb(212, 112, 112));
                        for(final Button b : answerButtons) {
                            if (b.getText().toString().equals(current.getAnswer())) {
                                DrawableCompat.setTint(DrawableCompat.wrap(b.getBackground()), Color.rgb(112, 112, 212));
                            }
                        }
                    }
                    for(final Button b : answerButtons) {
                        b.setEnabled(false);
                    }
                    newButton.setEnabled(true);
                }
            });
        }

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newExercise();
            }
        });

        newExercise();
    }

    private void newExercise() {
        current = generator.GetExercise();
        questionText.setText(current.getQuestion());
        extraText.setText(current.getExtra());
        String[] answers = current.getAlternatives();
        for(int i = 0; i < 4; i++) {
            answerButtons[i].setText(answers[i]);
            answerButtons[i].setEnabled(true);
            answerButtons[i].setPadding(0, 64, 0, 64);
            DrawableCompat.setTint(DrawableCompat.wrap(answerButtons[i].getBackground()), Color.rgb(212, 212, 212));
        }
        newButton.setEnabled(false);
    }
}
