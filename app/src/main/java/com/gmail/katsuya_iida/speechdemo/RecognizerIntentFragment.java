package com.gmail.katsuya_iida.speechdemo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RecognizerIntentFragment extends Fragment {
    private static final int RESULT_SPEECH = 1;

    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recognizerintent, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnSpeak = view.findViewById(R.id.button_speak);
        textView = view.findViewById(R.id.text);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    textView.setText("");
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(
                            getContext(),
                            "Speech recognition is not supported.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        List<String> texts = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < texts.size(); i++) {
                            sb.append(String.format("Result %d: %s\n", i, texts.get(0)));
                        }
                        textView.setText(sb);
                    } else {
                        textView.setText("No results");
                    }
                } else {
                    String text = String.format("Error: %d", resultCode);
                    textView.setText(text);
                }
                break;
        }
    }
}
