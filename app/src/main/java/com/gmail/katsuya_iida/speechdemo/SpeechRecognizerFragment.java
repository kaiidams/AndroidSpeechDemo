package com.gmail.katsuya_iida.speechdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    private SpeechRecognizer sr;
    private EditText textView;

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

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
                // Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
                intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        "en-US" //RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                );
                //intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.tkjelectronics.voice");
                // intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                //intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.tkjelectronics.voice");
                // intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                sr.startListening(intent);
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
            Toast.makeText(getContext(), "Granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(sr == null) {
            sr = SpeechRecognizer.createSpeechRecognizer(getContext());
            if (!SpeechRecognizer.isRecognitionAvailable(getContext())) {
                Toast.makeText(getContext(),"Speech Recognition is not available",Toast.LENGTH_LONG).show();
            }
            sr.setRecognitionListener(recognitionListener);
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
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
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
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(int error) {
            String msg = String.format("Error %d", error);
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResults(Bundle results) {
            List<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] value =
                    results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            StringBuilder sb = new StringBuilder();
            sb.append("Results: " + matches.size() + "\n");
            if (value != null) { // CONFIDENCE_SCORES wasn't added until API level 14
                for (int i = 0; i < matches.size(); i++)  // The size of the data and value is the same
                sb.append(matches.get(0) + "\nScore: " + value[i] + "\n");
            }
            textView.setText(sb);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
        }
    };
}
