package com.thanguit.tiushop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.base.MyToast;
import com.thanguit.tiushop.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

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

    }

    private void listeners() {
        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.tilEmail.setError(getString(R.string.tvError1));
                } else {
                    binding.tilEmail.setError(null);
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
                    binding.tilPassword.setError(getString(R.string.tvError3));
                } else {
                    binding.tilPassword.setError(null);
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPassword.getText().toString();

                handleLogin(email, password);
            }
        });
    }

    private void handleLogin(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError(getString(R.string.tvError2));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError(getString(R.string.tvError1));
        } else {
            flag1 = true;
            binding.tilEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError(getString(R.string.tvError3));
        } else {
            flag2 = true;
            binding.tilPassword.setError(null);
        }

        if (flag1 && flag2) {
            MyToast.makeText(getContext(), MyToast.TYPE.SUCCESS, "OK em", Toast.LENGTH_LONG).show();
        }
    }
}