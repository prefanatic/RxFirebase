package com.github.prefanatic.rxfirebasesample.action;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import rx.functions.Action0;

public class RxRemoveEventListener implements Action0 {
    private final Firebase firebase;
    private final Object listener;

    // FIXME: 10/10/2015 This is a whole bunch of shit.
    public RxRemoveEventListener(Firebase firebase, ValueEventListener listener) {
        this.firebase = firebase;
        this.listener = listener;
    }

    public RxRemoveEventListener(Firebase firebase, ChildEventListener listener) {
        this.firebase = firebase;
        this.listener = listener;
    }

    @Override
    public void call() {
        if (listener instanceof ValueEventListener)
            firebase.removeEventListener((ValueEventListener) listener);
        else
            firebase.removeEventListener((ChildEventListener) listener);
    }
}
