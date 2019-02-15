package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean isExist(Object searchKey) {
        String key = (String) searchKey;
        return map.containsKey(key);
    }

    @Override
    void doSave(Resume r, Object id) {
        map.put((String) id, r);
    }

    @Override
    Resume doGet(Object id) {
        String key = (String) id;
        return map.get(key);
    }

    @Override
    void doDelete(Object id) {
        String key = (String) id;
        map.remove(key);
    }

    @Override
    void doUpdate(Resume r, Object id) {
        map.replace((String) id, r);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
