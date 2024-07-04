package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.NetworkError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.ServiceNotRespondingError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.UnknownError;
import abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints.IngredientResponse;
import abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints.IngredientsClient;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PictureSendIn extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_send_in);
        button = findViewById(R.id.createOverviewButton);
        button.setOnClickListener(v -> {
            button.setEnabled(false);
            new Thread(() -> {
                try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                    if (isNetworkError()) {
                        Intent intent = new Intent(PictureSendIn.this, NetworkError.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Bitmap bitmap = getIntent().getParcelableExtra("imageBitmap");
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();
                        String picture = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        IngredientResponse ingredientResponse = IngredientsClient.getIngredients(picture);
                        Intent intent = new Intent(PictureSendIn.this, IngredientsOverview.class);
                        intent.putExtra("ingredients", ingredientResponse.getIngredients());
                        startActivity(intent);
                    }
                } catch (SocketTimeoutException | TimeoutException e) {
                    Log.d("PictureSendIn exception", ": ", e);
                    Intent intent = new Intent(PictureSendIn.this, ServiceNotRespondingError.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("PictureSendIn exception", ": ", e);
                    Intent intent = new Intent(PictureSendIn.this, UnknownError.class);
                    startActivity(intent);
                }
            }).start();
        });
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private boolean isNetworkError() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork == null || !activeNetwork.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        button.setEnabled(true);
    }
}