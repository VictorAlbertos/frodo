package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <br>Annotated classes which are of type io.reactivex.Observer will print the following information on
 * android logcat when receiving items from an io.reactivex.Observable.<br>
 *
 * <br>OUTPUT EXAMPLE:<br>
 *
 * <br>Frodo => [@Observer :: MyObserver -> onStart()]
 * <br>Frodo => [@Observer :: MyObserver -> @Requested -> 10 elements]
 * <br>Frodo => [@Observer :: MyObserver -> onNext() -> 1 :: @ObserveOn -> main]
 * <br>Frodo => [@Observer :: MyObserver -> onNext() -> 2 :: @ObserveOn -> main]
 * <br>Frodo => [@Observer :: MyObserver -> onNext() -> 3 :: @ObserveOn -> main]
 * <br>Frodo => [@Observer :: MyObserver -> onCompleted()]
 * <br>Frodo => [@Observer :: MyObserver -> @Received -> 16 elements :: @Time -> 6 ms]
 * <br>Frodo => [@Observer :: MyObserver -> unSubscribe()]<br>
 *
 * @see <a href="https://github.com/android10/frodo/wiki">Frodo Documentation</a>
 */
@Retention(RUNTIME)
@Target({ TYPE })
public @interface RxLogObserver {
}
