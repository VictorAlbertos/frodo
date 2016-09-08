package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogObserver;
import io.reactivex.observers.ResourceObserver;

@RxLogObserver
public class MyObserverIgnore extends ResourceObserver<Integer> {
  @Override public void onNext(Integer ignore) {
    //empty
  }

  @Override public void onError(Throwable e) {
    //empty
  }

  @Override public void onComplete() {
    //empty
  }
}
