package com.thanguit.tiushop.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thanguit.tiushop.R;

public class MyToast extends Toast {
    public MyToast(Context context) {
        super(context);
    }

    public enum TYPE {
        SUCCESS,
        ERROR,
        WARNING,
        INFORMATION
    }

    public static Toast makeText(Context context, TYPE type, String content, int duration) {
        Toast toast = new Toast(context);

        // https://viblo.asia/p/tim-hieu-ve-layoutinflater-trong-android-07LKXzL2lV4
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false);
        ImageView ivIconToast = layout.findViewById(R.id.ivIconToast);
        TextView tvTextToast = layout.findViewById(R.id.tvTextToast);

        if (TextUtils.isEmpty(content)) {
            tvTextToast.setText("");
        } else {
            tvTextToast.setText(content.trim());
        }

        switch (type) {
            case SUCCESS: {
                ivIconToast.setImageResource(R.drawable.ic_success_1);
                break;
            }

            case ERROR: {
                ivIconToast.setImageResource(R.drawable.ic_error);
                break;
            }

            case WARNING: {
                ivIconToast.setImageResource(R.drawable.ic_warning);
                break;
            }

            case INFORMATION: {
                ivIconToast.setImageResource(R.drawable.ic_info);
                break;
            }
        }

        toast.setDuration(duration);
        toast.setView(layout);
        return toast;
    }
}
