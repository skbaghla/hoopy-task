package the26.blinders.hoopytaskbysanjeev.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the26.blinders.hoopytaskbysanjeev.R;
import the26.blinders.hoopytaskbysanjeev.databinding.FragmentFragmentUpdateBinding;
import the26.blinders.hoopytaskbysanjeev.helper.DialogTransparent;
import the26.blinders.hoopytaskbysanjeev.models.ResponseInsert;
import the26.blinders.hoopytaskbysanjeev.models.ResponseUpdate;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.APIMethods;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.RetrofitInstance;

public class FragmentUpdate extends Fragment implements View.OnClickListener {
    private DialogTransparent dialogTransparent;
    private int stUserID;
    private FragmentFragmentUpdateBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_update, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        dialogTransparent = new DialogTransparent(getContext());

        assert getArguments() != null;
        String email = getArguments().getString("email");
        String imageUrl = getArguments().getString("imageUrl");
        String name = getArguments().getString("name");
        String username = getArguments().getString("username");
        stUserID = getArguments().getInt("id");
        String contact = getArguments().getString("contact");

        binding.edtUsername.setText(username);
        binding.edtName.setText(name);
        binding.edtEmail.setText(email);
        binding.edtContact.setText(contact);

        Glide.with(getContext()).load(imageUrl).placeholder(R.drawable.ic_profile_placeholder).into(binding.imgProfilePic);


        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSubmit) {
            if (validations()) {
                String stName = binding.edtName.getText().toString();
                String stContact = binding.edtContact.getText().toString();
                String stEmail = binding.edtEmail.getText().toString();
                String stUsername = binding.edtUsername.getText().toString();
                hitInsertDataApi(stName, stContact, stEmail, stUsername);
            }
        }

    }

    private void hitInsertDataApi(String stName, String stContact, String stEmail, String stUsername) {
        APIMethods apiMethods = RetrofitInstance.getRetrofitInstance().create(APIMethods.class);
        Call<ResponseUpdate> responseUpdateCall = apiMethods.updateData(stName, stEmail, stUsername, stContact, String.valueOf(stUserID));
        dialogTransparent.showDialog();
        responseUpdateCall.enqueue(new Callback<ResponseUpdate>() {
            @Override
            public void onResponse(Call<ResponseUpdate> call, Response<ResponseUpdate> response) {
                dialogTransparent.hideDialog();
                try {
                    if (response.isSuccessful()) {
                        ResponseUpdate body = response.body();
                        String responseText = body.getMetadata().getResponseText();
                        Toast.makeText(getContext(), responseText, Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdate> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
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
        } else if (binding.edtContact.getText().toString().isEmpty() || binding.edtContact.getText().toString().length() != 10) {
            Toast.makeText(getContext(), R.string.enter_valid_no, Toast.LENGTH_SHORT).show();
            binding.edtContact.setError("!");
            binding.edtContact.requestFocus();
            return false;
        }

        return true;
    }

}
