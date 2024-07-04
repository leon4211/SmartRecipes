package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.abschlussprojektrezeptbonde.R;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");
    }
}