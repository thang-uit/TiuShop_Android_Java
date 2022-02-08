package com.thanguit.tiushop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.databinding.FragmentLoginBinding;
import com.thanguit.tiushop.databinding.FragmentSignupBinding;
import com.thanguit.tiushop.presenter.SignupPresenter;
import com.thanguit.tiushop.presenter.listener.SignupListener;
import com.thanguit.tiushop.util.Common;

public class SignupFragment extends Fragment implements SignupListener.View {
    private static final String TAG = "SignupFragment";
    private FragmentSignupBinding binding;

    private SignupPresenter signupPresenter;

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, null, false);
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
        signupPresenter = new SignupPresenter(this);
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
                    binding.tilUsername.setError(getString(R.string.tvError2));
                } else if (!username.matches(Common.REGEX_USERNAME)) {
                    binding.tilUsername.setError(getString(R.string.tvError1));
                } else {
                    binding.tilUsername.setError(null);
                }
            }
        });

        binding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();

                if (TextUtils.isEmpty(name)) {
                    binding.tilName.setError(getString(R.string.tvError4));
                } else {
                    binding.tilName.setError(null);
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
                } else if (!password.matches(Common.REGEX_USERNAME)) {
                    binding.tilPassword.setError(getString(R.string.tvError6));
                } else {
                    binding.tilPassword.setError(null);
                }
            }
        });

        binding.edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String confirmPassword = editable.toString();

                if (TextUtils.isEmpty(confirmPassword)) {
                    binding.tilConfirmPassword.setError(getString(R.string.tvError5));
                } else if (!confirmPassword.matches(Common.REGEX_USERNAME)) {
                    binding.tilConfirmPassword.setError(getString(R.string.tvError6));
                } else if (!binding.edtPassword.getText().equals(confirmPassword)) {
                    binding.tilConfirmPassword.setError(getString(R.string.tvError7));
                } else {
                    binding.tilConfirmPassword.setError(null);
                }
            }
        });


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void signupSuccess() {

    }

    @Override
    public void signupFail(String error) {

    }
}