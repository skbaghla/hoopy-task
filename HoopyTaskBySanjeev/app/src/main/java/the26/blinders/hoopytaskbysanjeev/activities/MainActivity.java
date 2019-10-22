package the26.blinders.hoopytaskbysanjeev.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.Toast;

import the26.blinders.hoopytaskbysanjeev.R;
import the26.blinders.hoopytaskbysanjeev.fragments.FragmentCRUD;
import the26.blinders.hoopytaskbysanjeev.helper.Constant;

public class MainActivity extends AppCompatActivity {

    FragmentCRUD fragmentCRUD;
    FrameLayout frameLayout;
    public static String clickFlag = "Crud";
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        frameLayout = findViewById(R.id.frame);

        fragmentCRUD = new FragmentCRUD();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame, fragmentCRUD, Constant.FRAGMENT_INPUT);
        transaction.addToBackStack(null);
        clickFlag = "Crud";
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        String s = clickFlag;

        if (s.equalsIgnoreCase(getString(R.string.crud))) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.please_click_back_again), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {

            getSupportFragmentManager().popBackStack();
        }
    }
}
