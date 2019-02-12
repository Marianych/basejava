package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    void insert(Resume what, int where) {
        where = -(where + 1);
        arraycopy(storage, where, storage, where + 1, size - where);
        storage[where] = what;
    }

}
