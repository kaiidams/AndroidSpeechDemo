package com.gmail.katsuya_iida.speechdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class SpeechRecognizerFragment extends Fragment {

    private static final int REQUEST_RECORD_AUDIO = 1;

    private SpeechRecognizer speechRecognizer;
    private EditText textView;
    private View indicatorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speechrecognizer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton speakButton = view.findViewById(R.id.button_speak);
        textView = view.findViewById(R.id.text);
        indicatorView = view.findViewById(R.id.indicator);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE, "ja-JP");
                intent.putExtra(
                        RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                        true);
                speechRecognizer.startListening(intent);
            }
        });

        requestRecordAudioPermission();
    }

    private void requestRecordAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
            if (!SpeechRecognizer.isRecognitionAvailable(getContext())) {
                Toast.makeText(getContext(),"Speech Recognition is not available",Toast.LENGTH_LONG).show();
            }
            speechRecognizer.setRecognitionListener(recognitionListener);
        }
    }

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            indicatorView.setBackgroundColor(Color.GREEN);
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            //Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onEndOfSpeech() {
            indicatorView.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public void onError(int error) {
            indicatorView.setBackgroundColor(Color.RED);
            String msg = String.format("Error %d", error);
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResults(Bundle results) {
            updateResult(results);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            updateResult(partialResults);
        }

        private void updateResult(Bundle results) {
            List<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] value =
                    results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            StringBuilder sb = new StringBuilder();
            if (value != null) { // CONFIDENCE_SCORES wasn't added until API level 14
                for (int i = 0; i < matches.size(); i++) {
                    sb.append(String.format("%s\nScore: %g\n", matches.get(i), value[i]));
                }
            } else {
                for (int i = 0; i < matches.size(); i++) {
                    sb.append(String.format("%s\n", matches.get(i)));
                }
            }
            textView.setText(sb);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }
    };
}
