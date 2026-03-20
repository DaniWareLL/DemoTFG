package com.sonik.infrastructure.streaming;


/**
 * Interface to define a structure from where the songs are fetched
 * following the <a href="https://refactoring.guru/design-patterns/chain-of-responsibility">Chain of Responsibility</a> design pattern.
 * <br>Audio sources don't know who called them, but they know who to call next if they can't handle the request
 */
public interface AudioSource {

    /**
     * Points to the next AudioSource
     */
    void setNextSource(AudioSource nextSource);

    /**
     * Attempts to handle the request for a song, if it can't, it passes the request onto the next source
     * @param request The request to be handled
     * @return Whether the request was handled
     */
    boolean handle(String request);

}
