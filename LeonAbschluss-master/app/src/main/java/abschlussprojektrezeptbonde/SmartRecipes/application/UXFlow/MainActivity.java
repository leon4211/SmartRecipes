package abschlussprojektrezeptbonde.SmartRecipes.application.UXFlow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abschlussprojektrezeptbonde.R;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.NoCameraError;
import abschlussprojektrezeptbonde.SmartRecipes.application.Errorhandling.UnknownError;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logo = findViewById(R.id.icon);
        logo.setImageResource(R.drawable.app_icon);
        Button openCamera = findViewById(R.id.openCamera);
        try {
            openCamera.setOnClickListener(v -> {
                if (checkAndRequestCameraPermission()) {
                    if (isCameraAvailable()) {
                        dispatchPictureIntent();
                    } else {
                        navigateToNoCameraError();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("MainActivity exception", ": ", e);
            Intent intent = new Intent (MainActivity.this, UnknownError.class);
            startActivity(intent);
        }
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private boolean checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean isCameraAvailable() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void navigateToNoCameraError() {
        Intent intent = new Intent(MainActivity.this, NoCameraError.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isCameraAvailable()) {
                    dispatchPictureIntent();
                } else {
                    navigateToNoCameraError();
                }
            } else {
                Toast.makeText(this, "Kamera-Berechtigung erforderlich", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Keine Kamera-App gefunden", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    Intent intent = new Intent(MainActivity.this, PictureSendIn.class);
                    intent.putExtra("imageBitmap", imageBitmap);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.datenschutzbestimmungen) {
            startActivity(new Intent(this, WebViewActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}