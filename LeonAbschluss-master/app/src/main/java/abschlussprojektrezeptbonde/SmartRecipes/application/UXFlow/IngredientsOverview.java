package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.NoIngredientsFoundError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.NoRecipesFoundError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.ServiceNotRespondingError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.UnknownError;
import abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints.RecipesClient;
import abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints.RecipesResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class IngredientsOverview extends AppCompatActivity {
    private List<String> primaryIngredientsList;
    private List<String> optionalIngredientsList;
    private Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_overview);
        nextButton = findViewById(R.id.nextButton);
        LinearLayout ingredientsContainer = findViewById(R.id.ingredients_container);
        primaryIngredientsList = new ArrayList<>();
        optionalIngredientsList = new ArrayList<>();
        String[] ingredients = getIntent().getStringArrayExtra("ingredients");
        try {
            if (ingredients.length == 0) {
                navigateToNoIngredientsFoundError();
            } else {
                for (String ingredient : ingredients) {
                    LinearLayout ingredientLayout = new LinearLayout(this);
                    ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
                    ingredientLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ingredientLayout.setPadding(8, 8, 8, 8);
                    TextView ingredientView = new TextView(this);
                    ingredientView.setText(ingredient);
                    ingredientView.setTextColor(getResources().getColor(android.R.color.black));
                    ingredientView.setTextSize(24);
                    ingredientView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    int buttonSize = (int) (ingredientView.getTextSize() * getResources().getDisplayMetrics().density * 0.8);

                    ImageButton optionalButton = new ImageButton(this);
                    optionalButton.setTag(false);
                    optionalButton.setImageResource(R.drawable.eye_crosed);
                    setButtonSize(optionalButton, buttonSize);
                    optionalButton.setOnClickListener(v -> {
                        boolean currentState = (boolean) optionalButton.getTag();
                        if (currentState) {
                            optionalButton.setImageResource(R.drawable.eye_crosed);
                            optionalIngredientsList.remove(ingredient);
                        } else {
                            optionalButton.setImageResource(R.drawable.eye_open);
                            optionalIngredientsList.add(ingredient);
                        }
                        optionalButton.setTag(!currentState);
                    });

                    ImageButton primaryButton = new ImageButton(this);
                    primaryButton.setTag(false);
                    primaryButton.setImageResource(R.drawable.checkbox_empty);
                    setButtonSize(primaryButton, buttonSize);
                    primaryButton.setOnClickListener(v -> {
                        boolean currentState = (boolean) primaryButton.getTag();
                        if (currentState) {
                            primaryButton.setImageResource(R.drawable.checkbox_empty);
                            primaryIngredientsList.remove(ingredient);
                        } else {
                            primaryButton.setImageResource(R.drawable.checkbox_checked);
                            primaryIngredientsList.add(ingredient);
                        }
                        primaryButton.setTag(!currentState);
                    });

                    ingredientLayout.addView(ingredientView);
                    ingredientLayout.addView(optionalButton);
                    ingredientLayout.addView(primaryButton);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) optionalButton.getLayoutParams();
                    params.setMargins(0, 0, 30, 0);
                    optionalButton.setLayoutParams(params);
                    ingredientsContainer.addView(ingredientLayout);
                }
            }

            nextButton.setOnClickListener(v -> {
                nextButton.setEnabled(false);
                new Thread(() -> {
                    String[] primaryIngredients = primaryIngredientsList.toArray(new String[0]);
                    String[] optionalIngredients = optionalIngredientsList.toArray(new String[0]);
                    try {
                        RecipesResponse recipesResponse = RecipesClient.getRecipes(primaryIngredients, optionalIngredients);
                        if (primaryIngredients.length == 0 && optionalIngredients.length == 0) {
                            navigateToNoRecipesFoundErrorError();
                            return;
                        }
                        Intent intent = new Intent(IngredientsOverview.this, RecipeOverview.class);
                        intent.putExtra("recipes", recipesResponse);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.d("TimeoutException", "IngridentsOverview " + e);
                        Intent intent = new Intent (IngredientsOverview.this, ServiceNotRespondingError.class);
                        startActivity(intent);
                        finish();
                    }
                }).start();
            });
            initToolbar();
        } catch (Exception e) {
            Log.d("IngredientsOverview exception", ": " + e);
            Intent intent = new Intent (IngredientsOverview.this, UnknownError.class);
            startActivity(intent);
        }
    }

    private void setButtonSize(ImageButton button, int size) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        button.setLayoutParams(params);
        button.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void navigateToNoIngredientsFoundError() {
        Intent intent = new Intent(IngredientsOverview.this, NoIngredientsFoundError.class);
        startActivity(intent);
        finish();
    }

    private void navigateToNoRecipesFoundErrorError() {
        Intent intent = new Intent(IngredientsOverview.this, NoRecipesFoundError.class);
        startActivity(intent);
        finish();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        nextButton.setEnabled(true);
    }
}