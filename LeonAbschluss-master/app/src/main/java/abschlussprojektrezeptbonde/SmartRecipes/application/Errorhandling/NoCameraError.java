package abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling;

import android.content.Intent;
import android.os.Bundle;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NoCameraError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_camera_found);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_close);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(NoCameraError.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
