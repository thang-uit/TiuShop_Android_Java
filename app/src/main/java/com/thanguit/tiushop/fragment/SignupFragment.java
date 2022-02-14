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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.activities.LoginActivity;
import com.thanguit.tiushop.activities.MainActivity;
import com.thanguit.tiushop.base.MyToast;
import com.thanguit.tiushop.databinding.FragmentSignupBinding;
import com.thanguit.tiushop.presenter.SignupPresenter;
import com.thanguit.tiushop.presenter.listener.SignupListener;
import com.thanguit.tiushop.util.Common;
import com.thanguit.tiushop.util.LoadingDialog;

public class SignupFragment extends Fragment implements SignupListener.View {
    private static final String TAG = "SignupFragment";
    private FragmentSignupBinding binding;

    private SignupPresenter signupPresenter;
    private LoadingDialog loadingDialog;

    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    private boolean flag4 = false;

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
                } else if (!username.matches(Common.REGEX_USERNAME)) {
                    binding.tilUsername.setError(getString(R.string.tvError5));
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
                    binding.tilName.setError(getString(R.string.tvError3));
                } else if (name.length() < 6) {
                    binding.tilName.setError(getString(R.string.tvError6));
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
                    binding.tilPassword.setError(getString(R.string.tvError2));
                } else if (!password.matches(Common.REGEX_PASSWORD)) {
                    binding.tilPassword.setError(getString(R.string.tvError7));
                } else if (password.equals(binding.edtConfirmPassword.getEditableText().toString())) {
                    binding.tilConfirmPassword.setError(null);
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
                    binding.tilConfirmPassword.setError(getString(R.string.tvError4));
                } else if (!binding.edtPassword.getEditableText().toString().equals(confirmPassword)) {
                    binding.tilConfirmPassword.setError(getString(R.string.tvError8));
                } else {
                    binding.tilConfirmPassword.setError(null);
                }
            }
        });


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup();
            }
        });
    }

    private void handleSignup() {
        String username = binding.edtUsername.getEditableText().toString();
        String displayName = binding.edtName.getEditableText().toString();
        String password = binding.edtPassword.getEditableText().toString();
        String confirmPassword = binding.edtConfirmPassword.getEditableText().toString();

        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError(getString(R.string.tvError1));
        } else if (!username.matches(Common.REGEX_USERNAME)) {
            binding.tilUsername.setError(getString(R.string.tvError5));
        } else {
            flag1 = true;
            binding.tilUsername.setError(null);
        }

        if (TextUtils.isEmpty(displayName)) {
            binding.tilName.setError(getString(R.string.tvError3));
        } else if (displayName.length() < 6) {
            binding.tilName.setError(getString(R.string.tvError6));
        } else {
            flag2 = true;
            binding.tilName.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError(getString(R.string.tvError2));
        } else if (!password.matches(Common.REGEX_PASSWORD)) {
            binding.tilPassword.setError(getString(R.string.tvError7));
        } else {
            flag3 = true;
            binding.tilPassword.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.tilConfirmPassword.setError(getString(R.string.tvError4));
        } else if (!binding.edtPassword.getEditableText().toString().equals(confirmPassword)) {
            binding.tilConfirmPassword.setError(getString(R.string.tvError8));
        } else {
            flag4 = true;
            binding.tilConfirmPassword.setError(null);
        }

        if (flag1 && flag2 && flag3 && flag4) {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.tvAlertTitle))
                    .setMessage(getString(R.string.tvAlertMessage))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.tvAlertButton1), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadingDialog.startLoading(getContext(), false);
                            signupPresenter.handleSignup(username, displayName, password);
                        }
                    })
                    .setNegativeButton(getString(R.string.tvAlertButton2), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    private void clearForm() {
        binding.edtUsername.setText("");
        binding.edtName.setText("");
        binding.edtPassword.setText("");
        binding.edtConfirmPassword.setText("");
    }

    @Override
    public void signupSuccess() {
        loadingDialog.cancelLoading();
        clearForm();
        MyToast.makeText(getContext(), MyToast.TYPE.SUCCESS, getString(R.string.toast1), Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void signupFail(String error) {
        loadingDialog.cancelLoading();

        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.tvAlertTitle))
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