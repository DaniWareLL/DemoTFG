package com.sonik.infrastructure.streaming;

/**
 * Handles the chain of responsibility, its order and its members
 */
public abstract class BaseAudioSource implements AudioSource {

    /**
     * Points to the next AudioSource
     */
    protected AudioSource nextSource;

    public BaseAudioSource(AudioSource nextSource) {
        this.nextSource = nextSource;
    }

    @Override
    public final void setNextSource(AudioSource nextSource) {
        this.nextSource = nextSource;
    }

    // Final class so that classes can only call it and not override it
    @Override
    public final boolean handle(String request) {
        if (canHandle(request)) {
            process(request);
            return true;
        } else if (nextSource != null) {
            return nextSource.handle(request);
        }
        return false;
    }

    /**
     * Determines whether the request can be handled, should be used in {@link #handle(String) handle()} method
     * @return <code>true</code> if the request can be handled, <code>false</code> otherwise
     */
    protected abstract boolean canHandle(String request);

    /**
     * Actually processes the request
     * @param request The request to be processed
     */
    protected abstract void process(String request);
}
