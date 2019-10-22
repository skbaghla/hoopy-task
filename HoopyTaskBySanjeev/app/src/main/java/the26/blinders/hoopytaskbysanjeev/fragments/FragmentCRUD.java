package the26.blinders.hoopytaskbysanjeev.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import the26.blinders.hoopytaskbysanjeev.R;
import the26.blinders.hoopytaskbysanjeev.activities.MainActivity;
import the26.blinders.hoopytaskbysanjeev.databinding.FragmentFragmentCrudBinding;

public class FragmentCRUD extends Fragment implements View.OnClickListener {
    private FragmentFragmentCrudBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_crud, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.btnInsert.setOnClickListener(this);
        binding.btnFetch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnInsert:
                openInsertData();
                break;
            case R.id.btnFetch:
                openFetchData();
                break;
        }
    }

    private void openFetchData() {
        FragmentLogin fragmentUpdate = new FragmentLogin();


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragmentUpdate);
        transaction.addToBackStack(null);
        MainActivity.clickFlag="Login";
        transaction.commit();

    }

    private void openInsertData() {
        FragmentInput fragmentUpdate = new FragmentInput();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragmentUpdate);
        transaction.addToBackStack(null);
        MainActivity.clickFlag="Insert";
        transaction.commit();
    }
}
