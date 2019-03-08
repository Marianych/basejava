package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.arraycopy;


public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    private static final int STORAGE_LIMIT = 10000;

    final Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    @Override
    boolean isExist(Integer id) {
        return id >= 0;
    }

    @Override
    public void doSave(Resume r, Integer id) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insert(r, id);
        size++;
    }

    @Override
    public void doUpdate(Resume updateResume, Integer id) {
        storage[id] = updateResume;
    }

    @Override
    public void doDelete(Integer id) {
        int headSize = id + 1;
        arraycopy(storage, headSize, storage, id, size - headSize);
        size--;
    }

    @Override
    Resume doGet(Integer id) {
        return storage[id];
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    abstract void insert(Resume what, int where);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

}