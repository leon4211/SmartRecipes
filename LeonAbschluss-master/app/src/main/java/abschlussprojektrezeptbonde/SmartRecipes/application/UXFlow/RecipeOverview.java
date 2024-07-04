package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.NoRecipesFoundError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.UnknownError;
import abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints.RecipesResponse;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class RecipeOverview extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_overview);
        LinearLayout recipesContainer = findViewById(R.id.recipes_container);
        RecipesResponse recipes = (RecipesResponse) getIntent().getSerializableExtra("recipes");
        try {
            if (recipes == null) {
                navigateToNoRecipesFoundErrorError();
            } else {
                for (Map.Entry<String, String> entry : recipes.getRecipes().entrySet()) {
                    LinearLayout recipeLayout = new LinearLayout(this);
                    recipeLayout.setOrientation(LinearLayout.HORIZONTAL);
                    recipeLayout.setGravity(Gravity.CENTER_VERTICAL);
                    recipeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    TextView ingredientView = new TextView(this);
                    ingredientView.setText(entry.getKey());
                    ingredientView.setTextColor(getResources().getColor(android.R.color.black));
                    ingredientView.setTextSize(28);
                    ingredientView.setPadding(0, 50, 30, 50);
                    ingredientView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.drawable.recipe_ic);
                    setImageSize(imageView, (int) (ingredientView.getTextSize() * getResources().getDisplayMetrics().density * 0.62));

                    ingredientView.setOnClickListener(v -> {
                        Intent intent = new Intent(RecipeOverview.this, RecipeInfo.class);
                        intent.putExtra("recipeName", entry.getKey());
                        intent.putExtra("recipeInfo", entry.getValue());
                        startActivity(intent);
                    });

                    recipeLayout.addView(ingredientView);
                    recipeLayout.addView(imageView);
                    recipesContainer.addView(recipeLayout);
                }
            }
            initToolbar();
        } catch (Exception e) {
            Log.e("RecipeOverview exception", ": " + e);
            Intent intent = new Intent (RecipeOverview.this, UnknownError.class);
            startActivity(intent);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void navigateToNoRecipesFoundErrorError() {
        Intent intent = new Intent(RecipeOverview.this, NoRecipesFoundError.class);
        startActivity(intent);
    }

    private void setImageSize(ImageView imageView, int size) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
