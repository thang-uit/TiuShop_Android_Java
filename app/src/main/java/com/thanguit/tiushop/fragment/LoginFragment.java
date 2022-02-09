package com.thanguit.tiushop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.activities.MainActivity;
import com.thanguit.tiushop.databinding.FragmentLoginBinding;
import com.thanguit.tiushop.presenter.LoginPresenter;
import com.thanguit.tiushop.presenter.listener.LoginListener;
import com.thanguit.tiushop.util.LoadingDialog;

public class LoginFragment extends Fragment implements LoginListener.View {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    private LoginPresenter loginPresenter;
    private LoadingDialog loadingDialog;

    private boolean flag1 = false;
    private boolean flag2 = false;

    public LoginFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
        listeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initializeViews() {
        loginPresenter = new LoginPresenter(this);
        loadingDialog = LoadingDialog.getInstance();
    }

    private void listeners() {
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = editable.toString();

                if (TextUtils.isEmpty(username)) {
                    binding.tilUsername.setError(getString(R.string.tvError1));
                } else {
                    binding.tilUsername.setError(null);
                }
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = editable.toString();

                if (TextUtils.isEmpty(password)) {
                    binding.tilPassword.setError(getString(R.string.tvError2));
                } else {
                    binding.tilPassword.setError(null);
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.edtUsername.getEditableText().toString();
                String password = binding.edtPassword.getEditableText().toString();

                handleLogin(username, password);
            }
        });
    }

    private void handleLogin(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError(getString(R.string.tvError1));
        } else {
            flag1 = true;
            binding.tilUsername.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError(getString(R.string.tvError2));
        } else {
            flag2 = true;
            binding.tilPassword.setError(null);
        }

        if (flag1 && flag2) {
            loadingDialog.startLoading(getContext(), false);
            loginPresenter.handleLogin(username, password);
//            MyToast.makeText(getContext(), MyToast.TYPE.SUCCESS, "OK em", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loginSuccess() {
        loadingDialog.cancelLoading();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void loginFail(String error) {
        loadingDialog.cancelLoading();

        new AlertDialog.Builder(getContext())
                .setTitle("Notice")
                .setMessage(error)
                .setCancelable(true)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}