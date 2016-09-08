package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogObserver;
import io.reactivex.observers.ResourceObserver;

@RxLogObserver
public class MyObserver extends ResourceObserver<String> {

  @Override public void onNext(String value) {
    //empty
  }

  @Override public void onError(Throwable e) {
    //empty
  }

  @Override public void onComplete() {
    //empty
  }
}
