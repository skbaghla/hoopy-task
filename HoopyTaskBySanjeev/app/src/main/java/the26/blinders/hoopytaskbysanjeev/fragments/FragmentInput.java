package the26.blinders.hoopytaskbysanjeev.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.BuildConfig;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import the26.blinders.hoopytaskbysanjeev.R;
import the26.blinders.hoopytaskbysanjeev.activities.MainActivity;
import the26.blinders.hoopytaskbysanjeev.databinding.FragmentFragmentInputBinding;
import the26.blinders.hoopytaskbysanjeev.helper.DialogTransparent;
import the26.blinders.hoopytaskbysanjeev.models.ResponseInsert;
import the26.blinders.hoopytaskbysanjeev.models.ResponseUploadImage;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.APIMethods;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.RetrofitInstance;

import static android.app.Activity.RESULT_OK;

public class FragmentInput extends Fragment implements View.OnClickListener {
    private int GALLERY = 1, CAMERA = 2;
    private Uri captureUri;

    private DialogTransparent dialogTransparent;
    private String imageUrlfromAPI = "";
    private FragmentFragmentInputBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_input, container, false);
        init();
        return binding.getRoot();
    }

    /*Click Listeners*/
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgEditProfile:
                getImage();
                break;

            case R.id.btnSubmit:
                InsertData();
                break;

        }
    }

    private void InsertData() {
        if (validations()) {
            String stName = binding.edtName.getText().toString();
            String stContact = binding.edtContact.getText().toString();
            String stEmail = binding.edtEmail.getText().toString();
            String stUsername = binding.edtUsername.getText().toString();

            hitInsertDataApi(stName,stContact,stEmail,stUsername);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.clickFlag="Crud";
    }

    private void hitInsertDataApi(String stName, String stContact, String stEmail, String stUsername) {
        APIMethods apiMethods = RetrofitInstance.getRetrofitInstance().create(APIMethods.class);
        Call<ResponseInsert> responseInsertCall = apiMethods.insertData(stName, stEmail, stUsername, stContact, imageUrlfromAPI);
        dialogTransparent.showDialog();
        responseInsertCall.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                dialogTransparent.hideDialog();
                if (response.isSuccessful())
                {
                    ResponseInsert body = response.body();
                    String responseText = body.getMetadata().getResponseText();
                    Toast.makeText(getContext(), responseText, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(getContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                dialogTransparent.hideDialog();
            }
        });

    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean validations() {
        if (binding.edtName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.please_enter_your_name, Toast.LENGTH_SHORT).show();
            binding.edtName.setError("!");
            binding.edtName.requestFocus();
            return false;
        } else if (imageUrlfromAPI.isEmpty()) {
            Toast.makeText(getContext(), R.string.select_image, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.please_enter_your_email, Toast.LENGTH_SHORT).show();
            binding.edtEmail.setError("!");
            binding.edtEmail.requestFocus();
            return false;
        } else if (!isValidEmailId(binding.edtEmail.getText().toString())) {
            Toast.makeText(getContext(), R.string.please_enter_valid_email, Toast.LENGTH_SHORT).show();
            binding.edtEmail.setError("!");
            binding.edtEmail.requestFocus();
            return false;
        } else if (binding.edtUsername.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.please_enter_username, Toast.LENGTH_SHORT).show();
            binding.edtUsername.setError("!");
            binding.edtUsername.requestFocus();
            return false;
        } else if (binding.edtContact.getText().toString().isEmpty()|| binding.edtContact.getText().toString().length() != 10) {
            Toast.makeText(getContext(), R.string.enter_valid_no, Toast.LENGTH_SHORT).show();
            binding.edtContact.setError("!");
            binding.edtContact.requestFocus();
            return false;
        }

        return true;
    }

    /*Upload Image Api*/
    private void init() {

        binding.imgEditProfile.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        dialogTransparent = new DialogTransparent(getContext());

    }

    private void uploadImage(Uri imageUri) {
        APIMethods apiMethods = RetrofitInstance.getRetrofitInstance().create(APIMethods.class);

        dialogTransparent.showDialog();
        File file = new File(imageUri.getPath());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("Image", file.getName(), fbody);
        Call<ResponseUploadImage> responseUploadImageCall = apiMethods.uploadImage(body);

        responseUploadImageCall.enqueue(new Callback<ResponseUploadImage>() {
            @Override
            public void onResponse(Call<ResponseUploadImage> call, Response<ResponseUploadImage> response) {
                dialogTransparent.hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMetadata().getResponseCode()==200)
                    {
                        ResponseUploadImage body1 = response.body();
                        imageUrlfromAPI = body1.getUrls().get(0);
                        System.out.println("Response>>" + body1.getUrls().get(0));
                    }
                    else
                    {
                        Toast.makeText(getContext(), response.body().getMetadata().getResponseText(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.cant_load_image, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUploadImage> call, Throwable throwable) {
                dialogTransparent.hideDialog();

                throwable.printStackTrace();
            }
        });

    }

    private void getImage() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            showPictureDialog();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.need_permissions));
        builder.setMessage(getString(R.string.this_app_need_permission));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle(getString(R.string.select_action));
        String[] pictureDialogItems = {
                getString(R.string.gallery),
                getString(R.string.camera)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                galleryIntent();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    private void galleryIntent() {

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.complete_action_using)), GALLERY);
    }

    private void takePhotoFromCamera() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        captureUri = FileProvider.getUriForFile(
                getContext(),
                "the26.blinders.hoopytaskbysanjeev.provider",
                new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg")
        );


        try {
            Log.e("uri is", captureUri.toString());

            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, captureUri);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, CAMERA);

        } catch (Exception ae) {

            ae.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e("profile img", "HERE" + requestCode);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), resultUri);

                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(bitmap,
                            200, 200);
                    //     base64_small_image = encodeTobase64(ThumbImage);
/*
                    base64stringImage= encodeTobase64(bitmap);
                    Log.e("my profile img", base64stringImage.trim());

*/
                    binding.imgProfilePic.setImageBitmap(getRoundedShape(ThumbImage));

                    uploadImage(resultUri);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                System.out.println(error + "");
            }
        }

        if (requestCode == CAMERA) {
            try {

                CropImage.activity(captureUri).start(getContext(), this);

                Log.e("uri in result", captureUri.toString());
            } catch (Exception ae) {
                ae.printStackTrace();
            }
            Log.e("CropImage", "" + CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        if (requestCode == GALLERY) {
            try {
                Log.e("my uri ", String.valueOf(captureUri));
                captureUri = data.getData();
                CropImage.activity(captureUri)
                        .start(getContext(), this);
            } catch (Exception ae) {
                System.out.println(ae + "GalleryrError");
            }

        }

    }


    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 250;
        int targetHeight = 250;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
                targetHeight), null);
        return targetBitmap;
    }

}
