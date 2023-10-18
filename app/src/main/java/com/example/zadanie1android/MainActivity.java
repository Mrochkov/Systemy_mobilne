package com.example.zadanie1android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int Wynik = 0;

    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            Wynik++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private Question[] questions = new Question[]
            {
                    new Question(R.string.q_wieloryb, true),
                    new Question(R.string.q_cialo, false),
                    new Question(R.string.q_kosmos, false),
                    new Question(R.string.q_owoc, false),
                    new Question(R.string.q_kangur, true)
            };

    private int currentIndex = 0;

    private void setNextQuestion()
    {
        nextButton.setEnabled(false);
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextButton.setEnabled(true);
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextButton.setEnabled(true);
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                checkAnswerCorrectness(false);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == questions.length - 1)
                {
                    trueButton.setVisibility(View.GONE);
                    falseButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    questionTextView.setText("Twój wynik to: " + Integer.toString(Wynik) + "/5");

                } else {
                    currentIndex = (currentIndex + 1)%questions.length;
                    setNextQuestion();
                }
            }
        });
        setNextQuestion();

    }

}




