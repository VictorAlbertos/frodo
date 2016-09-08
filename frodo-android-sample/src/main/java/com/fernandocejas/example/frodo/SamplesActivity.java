package com.fernandocejas.example.frodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo.sample.MyObserver;
import com.fernandocejas.example.frodo.sample.MySubscriber;
import com.fernandocejas.example.frodo.sample.MyObserverIgnore;
import com.fernandocejas.example.frodo.sample.ObservableSample;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SamplesActivity extends Activity {

  private Button btnRxLogObservable;
  private Button btnRxLogObserver;

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      ObservableSample observableSample = new ObservableSample();

      observableSample.stringItemWithDefer()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.numbers()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
              toastMessage("onNext() Integer--> " + String.valueOf(integer));
            }
          });

      observableSample.moreNumbers().toList().blockingSingle();

      observableSample.names()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<String>() {
            @Override public void accept(String string) throws Exception {
              toastMessage("onNext() String--> " + string);
            }
          });

      observableSample.error()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new ResourceObserver<String>() {
            @Override
            public void onComplete() {
              //nothing here
            }

            @Override
            public void onError(Throwable e) {
              toastMessage("onError() --> " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
              //nothing here
            }
          });

      observableSample.list()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<List<ObservableSample.MyDummyClass>>() {
            @Override public void accept(List<ObservableSample.MyDummyClass> myDummyClasses)
                throws Exception {
              toastMessage("onNext() List--> " + myDummyClasses.toString());
            }
            });

      observableSample.doNothing()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.doSomething(v)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.sendEmptyString()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();
    }
  };

  private View.OnClickListener rxLogObserverListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final ObservableSample observableSample = new ObservableSample();
      toastMessage("Subscribing to observables...Check logcat output...");

      observableSample.strings()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver());

      observableSample.stringsWithError()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver());

      observableSample.numbersBackpressure()
          .onBackpressureDrop()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriber());

      observableSample.doNothing()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserverIgnore());

      observableSample.doSomething(v)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserverIgnore());

      observableSample.sendEmptyString()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver());
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_samples);
    this.mapGUI();
  }

  private void mapGUI() {
    this.btnRxLogObservable = (Button) findViewById(R.id.btnRxLogObservable);
    this.btnRxLogObserver = (Button) findViewById(R.id.btnRxLogObserver);

    this.btnRxLogObservable.setOnClickListener(rxLogObservableListener);
    this.btnRxLogObserver.setOnClickListener(rxLogObserverListener);
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
