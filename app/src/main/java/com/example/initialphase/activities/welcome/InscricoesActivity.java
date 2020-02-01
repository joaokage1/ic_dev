package com.example.initialphase.activities.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.initialphase.R;

public class InscricoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricoes);

        WebView webView = findViewById(R.id.webViewInscricoes);
        webView.loadUrl("https://iftm.edu.br/internacional/editais/");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
