package com.example.zadanie1android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER = "com.example.zadanie1android.correctAnswer";

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private Button promptButton;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int Wynik = 0;
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean answerWasShown;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_CODE_PROMPT)
        {
            if(data == null)
            { return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda: OnSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }


    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                Wynik++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
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
    protected void onStart() {
        Log.d("QUIZ_TAG", "Aplikacja wlaczona");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("QUIZ_TAG", "Aplikacja zatrzymana");
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        Log.d("QUIZ_TAG", "Aplikacja odpauzowana");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("QUIZ_TAG", "Aplikacja Zamknieta");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("QUIZ_TAG", "Aplikacja zapauzowana");
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QUIZ_TAG", "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
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

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent,REQUEST_CODE_PROMPT);

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
                    promptButton.setVisibility(View.GONE);
                    questionTextView.setText("Twój wynik to: " + Integer.toString(Wynik) + "/5");

                } else {
                    currentIndex = (currentIndex + 1)%questions.length;
                    answerWasShown = false;
                    setNextQuestion();
                }
            }
        });
        setNextQuestion();

    }

}




