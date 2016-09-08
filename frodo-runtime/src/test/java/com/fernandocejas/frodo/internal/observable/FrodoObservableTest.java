package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import io.reactivex.observers.TestObserver;
import java.lang.annotation.Annotation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class FrodoObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private FrodoObservable frodoObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;
  @Mock private LoggableObservableFactory observableFactory;

  @Before
  public void setUp() {
    frodoObservable =
        new FrodoObservable(observableRule.joinPoint(), messageManager, observableFactory);
    observer = new TestObserver();

    given(observableFactory.create(any(Annotation.class))).willReturn(
        createLogEverythingObservable());
  }

  @Test
  public void shouldPrintObservableInfo() throws Throwable {
    frodoObservable.getObservable();

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldBuildObservable() throws Throwable {
    frodoObservable.getObservable().subscribe(observer);

    // TODO: remove observer.dispose() once new version of RxJava2 has been released.
    observer.dispose();

    assertEquals(observer.values().get(0), observableRule.OBSERVABLE_STREAM_VALUE);
    observer.assertNoErrors();
    observer.assertComplete();
    assertThat(observer.isDisposed()).isTrue();
  }

  @Test
  public void shouldLogObservableInformation() throws Throwable {
    frodoObservable.getObservable().subscribe(observer);

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  private LogEverythingObservable createLogEverythingObservable() {
    return new LogEverythingObservable(observableRule.joinPoint(), messageManager,
        new ObservableInfo(observableRule.joinPoint()));
  }
}