package edu.augustana;

import java.util.InputMismatchException;

public interface CallbackRelease extends Callback {
    void onComplete();
    void onTimerComplete(String letter);
    void onTimerCatch(InputMismatchException e);
}