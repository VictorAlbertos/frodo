package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import io.reactivex.observers.TestObserver;
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LogObserverTest {

  private LogObserver logObserver;

  @Mock private Counter counter;
  @Mock private StopWatch stopWatch;
  @Mock private MessageManager messageManager;

  private TestObserver observer;
  private TestJoinPoint joinPoint;

  @Before
  public void setUp() {
    logObserver = new LogObserver(counter, stopWatch, messageManager);
    observer = new TestObserver();
    joinPoint = new TestJoinPoint.Builder(observer.getClass())
        .withParamTypes(String.class)
        .withParamNames("param")
        .withParamValues("value")
        .build();
  }

  @Test
  public void annotatedClassMustCheckTargetType() {
    final JoinPoint joinPoint = mock(JoinPoint.class);
    given(joinPoint.getTarget()).willReturn(observer);

    assertThat(LogObserver.classAnnotatedWithRxLogObserver(joinPoint)).isTrue();
    verify(joinPoint).getTarget();
    verifyNoMoreInteractions(joinPoint);
  }

  @Test
  public void shouldWeaveClassOfTypeObserver() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(observer.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObserver.classAnnotatedWithRxLogObserver(proceedingJoinPoint)).isTrue();
  }

  @Test
  public void shouldNotWeaveClassOfOtherTypeThanObserver() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObserver.classAnnotatedWithRxLogObserver(proceedingJoinPoint)).isFalse();
  }

  @Test
  public void printOnStartMessageBeforeObserverOnStartExecution() {
    logObserver.beforeOnStartExecution(joinPoint);

    verify(messageManager).printObserverOnStart(observer.getClass().getSimpleName());
  }

  @Test
  public void printOnNextMessageBeforeObserverOnNextExecution() {
    logObserver.beforeOnNextExecution(joinPoint);

    verify(counter).increment();
    verify(stopWatch).start();
    verify(messageManager).printObserverOnNext(eq(observer.getClass().getSimpleName()),
        eq("value"), anyString());
  }

  @Test public void printOnNextMessageBeforeObserverOnNextExecutionWithEmptyValues() {
    final TestJoinPoint joinPointTest =
        new TestJoinPoint.Builder(observer.getClass()).withParamTypes(String.class)
            .withParamNames("param")
            .withParamValues()
            .build();
    logObserver.beforeOnNextExecution(joinPointTest);

    verify(counter).increment();
    verify(stopWatch).start();
    verify(messageManager).printObserverOnNext(eq(observer.getClass().getSimpleName()),
        anyObject(), anyString());
  }

  @Test
  public void printOnErrorMessageAfterObserverOnErrorExecution() {
    logObserver.afterOnErrorExecution(joinPoint, new IllegalStateException());

    verify(stopWatch).stop();
    verify(counter).tally();
    verify(messageManager).printObserverOnError(eq(observer.getClass().getSimpleName()),
        anyString(), anyLong(), anyInt());
    verify(counter).clear();
    verify(stopWatch).reset();
  }

  @Test
  public void printOnCompleteMessageBeforeObserverOnCompleteExecution() {
    logObserver.beforeOnCompletedExecution(joinPoint);

    verify(stopWatch).stop();
    verify(messageManager).printObserverOnCompleted(eq(observer.getClass().getSimpleName()),
        anyLong(), anyInt());
    verify(counter).tally();
    verify(counter).clear();
    verify(stopWatch).getTotalTimeMillis();
    verify(stopWatch).reset();
  }

  @Test
  public void printUnsubscribeMessageAfterObserverUnsubscribeMethodCall() {
    logObserver.afterUnsubscribeMethodCall(joinPoint);

    verify(messageManager).printObserverUnsubscribe(observer.getClass().getSimpleName());
  }

  @Test
  public void printRequestedItemsAfterObserverRequestMethodCall() {
    logObserver.afterRequestMethodCall(joinPoint, 10);

    verify(messageManager).printObserverRequestedItems(observer.getClass().getSimpleName(), 10);
  }
}