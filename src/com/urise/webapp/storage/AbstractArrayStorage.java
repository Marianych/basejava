package com.urise.webapp.storage;/*
  Array based storage for Resumes
 */


import com.urise.webapp.model.Resume;


public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public Resume get(String uuid) {
        int id = indexOf(uuid);
        if (isInArray(id))
            return storage[id];
        return null;
    }

    protected abstract boolean isInArray(int id);


    protected abstract int indexOf(String uuid);

    public int size() {
        return size;
    }


}