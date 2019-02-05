package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.System.arraycopy;


public abstract class AbstractArrayStorage implements Storage {

    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (size <= STORAGE_LIMIT) {
            int id = indexOf(r.getUuid());
            if (!isInArray(id)) {
                insert(r, id);
                size++;
            }
        } else System.out.println("Storage overflow");
    }

    @Override
    public Resume get(String uuid) {
        int id = indexOf(uuid);
        if (isInArray(id))
            return storage[id];
        return null;
    }

    @Override
    public void delete(String uuid) {
        int id = indexOf(uuid);
        int headSize = id + 1;
        if (isInArray(id)) {
            arraycopy(storage, headSize, storage, id, size - headSize);
            size--;
        } else {
            System.out.println("Resume " + uuid + " not found");
        }
    }

    @Override
    public void update(Resume updateResume) {
        String uuid = updateResume.getUuid();
        int id = indexOf(uuid);
        if (isInArray(id)) {
            storage[id].setUuid(uuid);
        } else System.out.println("Resume " + uuid + " not found");
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    private boolean isInArray(int id) {
        return id >= 0;
    }

    abstract int indexOf(String uuid);

    abstract void  insert(Resume what, int where);

}