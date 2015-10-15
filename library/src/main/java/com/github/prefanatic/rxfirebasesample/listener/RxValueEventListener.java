package com.github.prefanatic.rxfirebasesample.listener;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.prefanatic.rxfirebasesample.action.RxRemoveEventListener;

import rx.Subscriber;
import rx.subscriptions.Subscriptions;

public class RxValueEventListener implements ValueEventListener {
    private final Subscriber<? super DataSnapshot> subscriber;
    private final boolean readOnce;

    public RxValueEventListener(Subscriber<? super DataSnapshot> subscriber) {
        this(subscriber, false);
    }

    public RxValueEventListener(Subscriber<? super DataSnapshot> subscriber, boolean readOnce) {
        this.subscriber = subscriber;
        this.readOnce = readOnce;

        subscriber.add(Subscriptions.create(new RxRemoveEventListener()));
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        subscriber.onNext(dataSnapshot);

        if (readOnce)
            subscriber.onCompleted();
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        subscriber.onError(firebaseError.toException());
    }
}
