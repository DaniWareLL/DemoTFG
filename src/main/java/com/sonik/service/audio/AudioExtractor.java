package com.sonik.service.audio;

import java.util.List;

public interface AudioExtractor {
    String execute(List<String> args) throws Exception;
}