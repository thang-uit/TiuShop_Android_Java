package com.thanguit.tiushop.presenter.listener;

import com.thanguit.tiushop.model.repository.Slider;

import java.util.List;

public interface SliderListener {
    interface View {
        void sliderSuccess(List<Slider> sliderList);

        void sliderFail(String error);
    }

    interface Presenter {
        void optionProduct(int amount, String option);
    }
}
