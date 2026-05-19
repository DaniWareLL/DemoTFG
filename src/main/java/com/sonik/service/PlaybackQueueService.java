package com.sonik.service;

import com.sonik.domain.model.Song;
import java.util.List;

public interface PlaybackQueueService {

    /**
     * Sets the playback queue and the starting position.
     * @param songs      The list of Songs to queue
     * @param startIndex The index of the first song to play
     */
    void setQueue(List<Song> songs, int startIndex);

    /**
     * Returns the song at the current index.
     * @return The current Song, or null if the index is out of bounds
     */
    Song getCurrent();

    /**
     * Advances to the next song and returns it.
     * @return The next Song, or null if there is none
     */
    Song next();

    /**
     * Goes back to the previous song and returns it.
     * @return The previous Song, or null if there is none
     */
    Song previous();

    /**
     * @return true if there is a next song in the queue, false otherwise
     */
    boolean hasNext();

    /**
     * @return true if there is a previous song in the queue, false otherwise
     */
    boolean hasPrevious();
}
