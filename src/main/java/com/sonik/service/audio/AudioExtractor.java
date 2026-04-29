package com.sonik.service.audio;

import java.io.IOException;
import java.util.List;

public interface AudioExtractor {
    String execute(List<String> args) throws IOException, InterruptedException;
}