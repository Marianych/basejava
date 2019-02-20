package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    void doSave(Resume r, String id) {
        map.put(id, r);
    }

    @Override
    Resume doGet(String id) {
        return map.get(id);
    }

    @Override
    void doDelete(String id) {
        map.remove(id);
    }

    @Override
    void doUpdate(Resume r, String id) {
        map.replace(id, r);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
