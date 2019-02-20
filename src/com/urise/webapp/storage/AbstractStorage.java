package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    abstract SK getSearchKey(String uuid);

    abstract boolean isExist(SK id);

    abstract void doSave(Resume r, SK id);

    abstract Resume doGet(SK id);

    abstract void doDelete(SK id);

    abstract void doUpdate(Resume r, SK id);

    abstract List<Resume> doGetAll();


    @Override
    public List<Resume> getAllSorted() {
        List<Resume> all = new ArrayList<>(doGetAll());
        all.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return all;
    }


    @Override
    public void save(Resume r) {
        SK id = getExistSearchKey(r.getUuid());
        doSave(r, id);
    }

    @Override
    public Resume get(String uuid) {
        SK id = getNotExistSearchKey(uuid);
        return doGet(id);
    }

    @Override
    public void delete(String uuid) {
        SK id = getNotExistSearchKey(uuid);
        doDelete(id);
    }

    @Override
    public void update(Resume r) {
        SK id = getNotExistSearchKey(r.getUuid());
        doUpdate(r, id);
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

}
