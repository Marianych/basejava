package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    Object getSearchKey(String uuid) {
//        Set<String> setCodes = map.keySet();
////        Iterator<String> iterator = setCodes.iterator();
////
////        while (iterator.hasNext()) {
////            if (map.get(iterator.next()).getUuid().equals(uuid)) {
////                return iterator.next();
////            }
////        }
//        for (String key:setCodes) {
//            if (map.get(key).getUuid().equals(uuid)){
//                return  key;
//            }
//        }
        return uuid;
    }

    @Override
    boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    void doSave(Resume r, Object id) {
        map.put((String) id, r);
    }

    @Override
    Resume doGet(Object id) {
        return map.get(id);
    }

    @Override
    void doDelete(Object id) {
        map.remove(id);
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
