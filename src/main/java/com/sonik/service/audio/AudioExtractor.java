package com.sonik.service.audio;

import java.io.IOException;
import java.util.List;

/**
 * Facade and connector. Wraps OS process execution and exposes a clean Java interface,
 * hiding details like stream handling and process configuration from the caller.
 */
public interface AudioExtractor {

    /**
     * Executes a command and returns its output.
     * Stdout and stderr are read on separate threads to avoid blocking.
     * @param args The command and its arguments to execute
     * @return The stdout output of the process, trimmed
     * @throws IOException          If the process cannot be started
     * @throws InterruptedException If the current thread is interrupted while waiting for the process
     */
    String execute(List<String> args) throws IOException, InterruptedException;
}