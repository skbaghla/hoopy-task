package the26.blinders.hoopytaskbysanjeev.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the26.blinders.hoopytaskbysanjeev.R;
import the26.blinders.hoopytaskbysanjeev.activities.MainActivity;
import the26.blinders.hoopytaskbysanjeev.databinding.FragmentFragmentLoginBinding;
import the26.blinders.hoopytaskbysanjeev.helper.DialogTransparent;
import the26.blinders.hoopytaskbysanjeev.models.ResponseFetch;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.APIMethods;
import the26.blinders.hoopytaskbysanjeev.retrofit_2_0.RetrofitInstance;

public class FragmentLogin extends Fragment implements View.OnClickListener {

    private DialogTransparent dialogTransparent;

    FragmentFragmentLoginBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_login, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        dialogTransparent = new DialogTransparent(getContext());

        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLogin) {
            if (validations()) {
                doLogin();
            }
        }
    }

    private boolean validations() {
        if (binding.edtUserNameLogin.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.validationUsername, Toast.LENGTH_SHORT).show();
            binding.edtUserNameLogin.setError("!");
            binding.edtUserNameLogin.requestFocus();
            return false;
        } else if (binding.edtContactLogin.getText().toString().isEmpty() || binding.edtContactLogin.getText().toString().length() != 10) {
            Toast.makeText(getContext(), R.string.enter_valid_no, Toast.LENGTH_SHORT).show();
            binding.edtContactLogin.setError("!");
            binding.edtContactLogin.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.clickFlag="Crud";
    }

    private void doLogin() {
        APIMethods apiMethods = RetrofitInstance.getRetrofitInstance().create(APIMethods.class);
        dialogTransparent.showDialog();
        Call<ResponseFetch> responseFetchCall = apiMethods.fetchData(binding.edtUserNameLogin.getText().toString(), binding.edtContactLogin.getText().toString());
        responseFetchCall.enqueue(new Callback<ResponseFetch>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFetch> call, @NonNull Response<ResponseFetch> response) {
                dialogTransparent.hideDialog();
                try {
                    if (response.isSuccessful())
                    {
                        ResponseFetch body = response.body();
                        if (body.getMetadata().getResponseCode()==200)
                        {
                            Integer id = body.getData().get(0).getId();
                            BigInteger contact =  body.getData().get(0).getContact();
                            String email = body.getData().get(0).getEmail();
                            String imageUrl = body.getData().get(0).getImageUrl();
                            String name = body.getData().get(0).getName();
                            String username = body.getData().get(0).getUsername();

                            FragmentUpdate fragmentUpdate = new FragmentUpdate();


                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Bundle args = new Bundle();
                            args.putInt("id", id);
                            args.putString("contact", String.valueOf(contact));
                            args.putString("email", email);
                            args.putString("imageUrl", imageUrl);
                            args.putString("name", name);
                            args.putString("username", username);
                            fragmentUpdate.setArguments(args);
                            transaction.replace(R.id.frame, fragmentUpdate);
                            transaction.addToBackStack(null);

                            transaction.commit();
                        }
                        else
                        {
                            String responseText = response.body().getMetadata().getResponseText();
                            Toast.makeText(getContext(), responseText, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFetch> call, Throwable throwable) {
                dialogTransparent.hideDialog();
                Toast.makeText(getContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });


    }
}
