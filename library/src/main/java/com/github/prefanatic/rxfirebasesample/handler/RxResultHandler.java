package com.github.prefanatic.rxfirebasesample.handler;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import rx.Subscriber;

public class RxResultHandler implements Firebase.ResultHandler {
    private final Subscriber subscriber;

    public RxResultHandler(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSuccess() {
        subscriber.onCompleted();
    }

    @Override
    public void onError(FirebaseError firebaseError) {
        subscriber.onError(firebaseError.toException());
    }
}
