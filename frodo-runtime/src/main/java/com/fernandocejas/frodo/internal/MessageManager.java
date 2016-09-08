package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.internal.observable.ObservableInfo;

public class MessageManager {

  private final MessageBuilder messageBuilder;
  private final DebugLog debugLog;

  public MessageManager() {
    this(new MessageBuilder(), new DebugLog());
  }

  public MessageManager(MessageBuilder messageBuilder, DebugLog debugLog) {
    this.messageBuilder = messageBuilder;
    this.debugLog = debugLog;
  }

  private void printMessage(String tag, String message) {
    debugLog.log(tag, message);
  }

  public void printObservableInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableInfoMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnSubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnSubscribeMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public <T> void printObservableOnNextWithValue(ObservableInfo observableInfo, T value) {
    final String message =
        messageBuilder.buildObservableOnNextWithValueMessage(observableInfo, value);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnNext(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnNextMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnError(ObservableInfo observableInfo,
      Throwable throwable) {
    final String message =
        messageBuilder.buildObservableOnErrorMessage(observableInfo, throwable.getMessage());
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnCompleted(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnCompletedMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnTerminate(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnTerminateMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnUnsubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObserverOnStart(String observerName) {
    final String message = messageBuilder.buildObserverOnStartMessage(observerName);
    this.printMessage(observerName, message);
  }

  public void printObserverOnNext(String observerName, Object value, String threadName) {
    final String message =
        messageBuilder.buildObserverOnNextMessage(observerName, value, threadName);
    this.printMessage(observerName, message);
  }

  public void printObserverOnError(String observerName, String error, long executionTimeMillis,
      int receivedItems) {
    final String itemTimeMessage =
        messageBuilder.buildObserverItemTimeMessage(observerName, executionTimeMillis,
            receivedItems);
    final String onErrorMessage =
        messageBuilder.buildObserverOnErrorMessage(observerName, error);
    this.printMessage(observerName, itemTimeMessage);
    this.printMessage(observerName, onErrorMessage);
  }

  public void printObserverOnCompleted(String observerName, long executionTimeMillis,
      int receivedItems) {
    final String itemTimeMessage =
        messageBuilder.buildObserverItemTimeMessage(observerName, executionTimeMillis,
            receivedItems);
    final String onCompleteMessage =
        messageBuilder.buildObserverOnCompletedMessage(observerName);
    this.printMessage(observerName, itemTimeMessage);
    this.printMessage(observerName, onCompleteMessage);
  }

  public void printObserverRequestedItems(String observerName, long requestedItems) {
    final String message =
        messageBuilder.buildObserverRequestedItemsMessage(observerName, requestedItems);
    this.printMessage(observerName, message);
  }

  public void printObserverUnsubscribe(String observerName) {
    final String message = messageBuilder.buildObserverUnsubscribeMessage(observerName);
    this.printMessage(observerName, message);
  }

  public void printObservableItemTimeInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableItemTimeInfoMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableThreadInfo(ObservableInfo observableInfo) {
    if (observableInfo.getSubscribeOnThread().isPresent() ||
        observableInfo.getObserveOnThread().isPresent()) {
      final String message = messageBuilder.buildObservableThreadInfoMessage(observableInfo);
      this.printMessage(observableInfo.getClassSimpleName(), message);
    }
  }
}
