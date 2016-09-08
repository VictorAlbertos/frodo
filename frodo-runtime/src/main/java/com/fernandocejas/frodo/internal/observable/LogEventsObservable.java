package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings("unchecked") class LogEventsObservable extends LoggableObservable {
  LogEventsObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) throws Exception {
            messageManager.printObservableOnSubscribe(observableInfo);
          }
        })
        .doOnNext(new Consumer<T>() {
          @Override public void accept(T type) throws Exception {
            messageManager.printObservableOnNext(observableInfo);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            messageManager.printObservableOnError(observableInfo, throwable);
          }
        })
        .doOnComplete(new Action() {
          @Override public void run() throws Exception {
            messageManager.printObservableOnCompleted(observableInfo);
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() throws Exception {
            messageManager.printObservableOnTerminate(observableInfo);
          }
        })
        .doOnDispose(new Action() {
          @Override public void run() throws Exception {
            messageManager.printObservableOnUnsubscribe(observableInfo);
          }
        });
  }
}
