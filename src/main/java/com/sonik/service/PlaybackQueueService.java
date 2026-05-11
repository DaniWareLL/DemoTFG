package com.sonik.service;

import com.sonik.domain.model.Song;
import java.util.List;

public interface PlaybackQueueService {
    void setQueue(List<Song> songs, int startIndex);
    Song getCurrent();
    Song next();
    Song previous();
    boolean hasNext();
    boolean hasPrevious();
}
