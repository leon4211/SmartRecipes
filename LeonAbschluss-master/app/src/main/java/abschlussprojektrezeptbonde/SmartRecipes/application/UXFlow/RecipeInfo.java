package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.UnknownError;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecipeInfo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_info);
        LinearLayout recipeName = findViewById(R.id.recipeNameLayout);
        LinearLayout recipeInfo = findViewById(R.id.recipesInfo_container);
        try {
            String recipeInfoExtra = getIntent().getStringExtra("recipeInfo");
            String recipeNameExtra = getIntent().getStringExtra("recipeName");

            TextView recipeNameView = new TextView(this);
            recipeNameView.setText(recipeNameExtra);
            recipeNameView.setGravity(Gravity.CENTER_HORIZONTAL);
            recipeNameView.setTextColor(getResources().getColor(android.R.color.black));
            recipeNameView.setTextSize(36);

            TextView recipeInfoView = new TextView(this);
            recipeInfoView.setText(recipeInfoExtra);
            recipeInfoView.setTextColor(getResources().getColor(android.R.color.black));
            recipeInfoView.setTextSize(22);
            recipeInfoView.setPadding(2, 2, 2, 2);

            recipeName.addView(recipeNameView);
            recipeInfo.addView(recipeInfoView);
            initToolbar();
        } catch (Exception e) {
            Log.e("RecipeInfo exception", ": " + e);
            Intent intent = new Intent (RecipeInfo.this, UnknownError.class);
            startActivity(intent);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}