package abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NetworkError extends AppCompatActivity {

    private String callingActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_error);
        callingActivity = getIntent().getStringExtra("callingActivity");
        Button retryButton = findViewById(R.id.tryAgainButton);
        retryButton.setOnClickListener(v -> navigation());
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_close);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(NetworkError.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void navigation() {
        Intent intent;
        if ("PictureSendIn".equals(callingActivity)) {
                intent = new Intent(NetworkError.this, MainActivity.class);
        } else if ("IngredientsOverview".equals(callingActivity)){
            intent = new Intent(NetworkError.this, MainActivity.class);
        } else {
            intent = new Intent(NetworkError.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}