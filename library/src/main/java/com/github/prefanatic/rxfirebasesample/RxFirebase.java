package com.github.prefanatic.rxfirebasesample;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;
import com.github.prefanatic.rxfirebasesample.handler.RxAuthResultHandler;
import com.github.prefanatic.rxfirebasesample.handler.RxResultHandler;
import com.github.prefanatic.rxfirebasesample.handler.RxValueResultHandler;
import com.github.prefanatic.rxfirebasesample.listener.RxCompletionListener;
import com.github.prefanatic.rxfirebasesample.listener.RxValueEventListener;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class RxFirebase {
    private final Firebase firebase;

    public RxFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public RxFirebase(String url) {
        firebase = new Firebase(url);
    }

    public RxFirebase(Repo repo, Path path) {
        firebase = new Firebase(repo, path);
    }

    public static void setAndroidContext(Context context) {
        Firebase.setAndroidContext(context);
    }

    public RxFirebase child(String url) {
        return new RxFirebase(firebase.child(url));
    }

     /*
    **************************** Saving Data
     */
    public Observable<Firebase> setValue(Object value) {
        return Observable.create(subscriber -> firebase.setValue(value, new RxCompletionListener(subscriber)));
    }

    public Observable<Firebase> setValue(Object value, Object priority) {
        return Observable.create(subscriber -> firebase.setValue(value, priority, new RxCompletionListener(subscriber)));
    }

    public RxFirebase push() {
        return new RxFirebase(firebase.push());
    }

    // TODO: Transactions

    /*
    **************************** Reading Data
     */
    public Observable<DataSnapshot> readOnce(String child) {
        return Observable.create(subscriber ->
                firebase.child(child).addListenerForSingleValueEvent(new RxValueEventListener(subscriber, true)));
    }

    public Observable<DataSnapshot> onDataChange() {
        return Observable.create(subscriber -> firebase.addValueEventListener(new RxValueEventListener(subscriber)));
    }

    /*
    **************************** User Authentication
     */
    public Observable<AuthData> authAnonymously() {
        return Observable.create(subscriber -> firebase.authAnonymously(new RxAuthResultHandler(subscriber)));
    }

    public Observable<Map<String, Object>> createUser(String username, String password) {
        return Observable.create(subscriber -> firebase.createUser(username, password, new RxValueResultHandler<>(subscriber)));
    }

    public Observable<AuthData> authWithPassword(String username, String password) {
        return Observable.create(subscriber -> firebase.authWithPassword(username, password, new RxAuthResultHandler(subscriber)));
    }

    public Observable changeEmail(String fromEmail, String toEmail, String password) {
        return Observable.create(subscriber -> firebase.changeEmail(fromEmail, toEmail, password, new RxResultHandler(subscriber)));
    }

    public Observable changePassword(String email, String fromPassword, String toPassword) {
        return Observable.create(subscriber -> firebase.changePassword(email, fromPassword, toPassword, new RxResultHandler(subscriber)));
    }

    public Observable resetPassword(String email) {
        return Observable.create(subscriber -> firebase.resetPassword(email, new RxResultHandler(subscriber)));
    }

    public Observable removeUser(String email, String password) {
        return Observable.create(subscriber -> firebase.removeUser(email, password, new RxResultHandler(subscriber)));
    }
}
