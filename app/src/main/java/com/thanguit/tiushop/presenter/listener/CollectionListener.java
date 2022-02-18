package com.thanguit.tiushop.presenter.listener;

import com.thanguit.tiushop.model.repository.Collection;

import java.util.List;

public interface CollectionListener {
    interface View {
        void collectionSuccess(List<Collection> collectionList);

        void collectionFail(String error);
    }

    interface Presenter {
        void handleCollection();
    }
}
