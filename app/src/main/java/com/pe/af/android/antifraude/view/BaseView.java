package com.pe.af.android.antifraude.view;

import android.content.Context;
import android.content.Intent;

public interface BaseView {
    Context getContext();
    Intent getIntent();
    void showloading(String message);
    void hideloading();
    void showError(String message);
    void showCorrect(String message);
    void showInfo(String message);
    void finalizar();

}
