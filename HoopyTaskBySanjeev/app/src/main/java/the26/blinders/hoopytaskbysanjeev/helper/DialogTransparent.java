package the26.blinders.hoopytaskbysanjeev.helper;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import the26.blinders.hoopytaskbysanjeev.R;


/**
 * Created by Sanjeev on 04/7/19.
 */

public class DialogTransparent {

    Context context;
    Dialog dialogTransparent;
    public DialogTransparent(Context context) {
        this.context = context;
        dialogTransparent = new Dialog(context, android.R.style.Theme_Black);
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_transparent, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        dialogTransparent.setContentView(view);

    }
    public void showDialog()
    {
        dialogTransparent.show();
    }

    public void hideDialog()
    {
        if (dialogTransparent.isShowing())
            dialogTransparent.dismiss();
    }
}
