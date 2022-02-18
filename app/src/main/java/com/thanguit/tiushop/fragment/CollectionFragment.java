package com.thanguit.tiushop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thanguit.tiushop.adapter.CollectionAdapter;
import com.thanguit.tiushop.databinding.FragmentCollectionBinding;
import com.thanguit.tiushop.model.repository.Collection;
import com.thanguit.tiushop.presenter.CollectionPresenter;
import com.thanguit.tiushop.presenter.listener.CollectionListener;

import java.util.List;

public class CollectionFragment extends Fragment implements CollectionListener.View {
    private FragmentCollectionBinding binding;

    private CollectionPresenter collectionPresenter;

    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        collectionPresenter = new CollectionPresenter(this);

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        collectionPresenter.handleCollection();
    }

    private void listeners() {

    }

    @Override
    public void collectionSuccess(List<Collection> collectionList) {
        if (collectionList != null) {
            binding.rvCollection.setHasFixedSize(true);
            binding.rvCollection.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvCollection.setAdapter(new CollectionAdapter(getContext(), collectionList));
        }
    }

    @Override
    public void collectionFail(String error) {

    }
}