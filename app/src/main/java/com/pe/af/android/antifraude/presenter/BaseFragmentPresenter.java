package com.pe.af.android.antifraude.presenter;


import com.pe.af.android.antifraude.view.BaseFragmentView;

public class BaseFragmentPresenter {
    BaseFragmentView view;

    public BaseFragmentPresenter(BaseFragmentView view) {
        this.view = view;
    }
}
