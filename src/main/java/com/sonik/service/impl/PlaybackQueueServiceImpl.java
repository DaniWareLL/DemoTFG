package com.sonik.service.impl;

import com.sonik.domain.model.Song;
import com.sonik.service.PlaybackQueueService;

import java.util.ArrayList;
import java.util.List;

public class PlaybackQueueServiceImpl implements PlaybackQueueService {

    private List<Song> queue = new ArrayList<>();
    private int index = -1;

    @Override
    public void setQueue(List<Song> songs, int startIndex) {
        this.queue = new ArrayList<>(songs);
        this.index = startIndex;
    }

    @Override
    public Song getCurrent() {
        if (index < 0 || index >= queue.size()) return null;
        return queue.get(index);
    }

    @Override
    public Song next() {
        if (!hasNext()) return null;
        index++;
        return queue.get(index);
    }

    @Override
    public Song previous() {
        if (!hasPrevious()) return null;
        index--;
        return queue.get(index);
    }

    @Override
    public boolean hasNext() {
        return index < queue.size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }
}
