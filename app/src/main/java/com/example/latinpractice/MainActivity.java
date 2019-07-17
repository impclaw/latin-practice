package com.example.latinpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox nounFirstDecl = findViewById(R.id.noun_1st_decl);
        final CheckBox nounSecondDecl = findViewById(R.id.noun_2nd_decl);
        final CheckBox nounThirdDeclA = findViewById(R.id.noun_3rd_decl_a);
        final CheckBox nounThirdDeclB = findViewById(R.id.noun_3rd_decl_b);
        final CheckBox nounThirdDeclC = findViewById(R.id.noun_3rd_decl_c);
        final CheckBox[] nounCheckBoxes = new CheckBox[] {nounFirstDecl, nounSecondDecl, nounThirdDeclA, nounThirdDeclB, nounThirdDeclC};

        final RadioButton nounSingular = findViewById(R.id.noun_singular);
        final RadioButton nounPlural = findViewById(R.id.noun_plural);
        final RadioButton nounBoth = findViewById(R.id.noun_both);

        final Button nounStart = findViewById(R.id.noun_start);
        nounStart.setEnabled(false);

        nounStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practiceIntent = new Intent(MainActivity.this, PracticeActivity.class);
                practiceIntent.putExtra("type", "noun");

                ArrayList<Integer> declList = new ArrayList<Integer>();
                if (nounFirstDecl.isChecked()) declList.add(1);
                if (nounSecondDecl.isChecked()) declList.add(2);
                if (nounThirdDeclA.isChecked()) declList.add(3);
                if (nounThirdDeclB.isChecked()) declList.add(4);
                if (nounThirdDeclC.isChecked()) declList.add(5);
                practiceIntent.putExtra("mode", declList);

                boolean singular = false, plural = false;
                if(nounSingular.isChecked()) singular = true;
                if(nounPlural.isChecked()) plural = true;
                if(nounBoth.isChecked()) singular = plural = true;
                practiceIntent.putExtra("singular", singular);
                practiceIntent.putExtra("plural", plural);

                MainActivity.this.startActivity(practiceIntent);
            }
        });

        for (final CheckBox box : nounCheckBoxes) {
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (final CheckBox box : nounCheckBoxes) {
                        if(box.isChecked() == true) {
                            nounStart.setEnabled(true);
                            return;
                        }
                    }
                    nounStart.setEnabled(false);
                }
            });
        }
    }
}
