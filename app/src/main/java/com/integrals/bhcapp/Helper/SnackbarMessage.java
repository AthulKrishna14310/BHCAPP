package com.integrals.bhcapp.Helper;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarMessage {

    public void ShowSnackbarMessage(View RootView, String message,boolean isDurationLong)
    {
        if(isDurationLong)
        {
            Snackbar.make(RootView,message,Snackbar.LENGTH_LONG).show();

        }
        else
        {
            Snackbar.make(RootView,message,Snackbar.LENGTH_SHORT).show();

        }
    }

}
