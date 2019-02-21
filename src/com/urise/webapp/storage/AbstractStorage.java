package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    //    protected final Logger LOG = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    abstract SK getSearchKey(String uuid);

    abstract boolean isExist(SK id);

    abstract void doSave(Resume r, SK id);

    abstract Resume doGet(SK id);

    abstract void doDelete(SK id);

    abstract void doUpdate(Resume r, SK id);

    abstract List<Resume> doGetAll();


    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted ");
        List<Resume> all = new ArrayList<>(doGetAll());
        all.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return all;
    }


    @Override
    public void save(Resume r) {
        LOG.info("save " + r);
        SK id = getNotExistSearchKey(r.getUuid());
        doSave(r, id);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        SK id = getExistSearchKey(uuid);
        return doGet(id);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        SK id = getExistSearchKey(uuid);
        doDelete(id);
    }

    @Override
    public void update(Resume r) {
        LOG.info("update " + r);
        SK id = getExistSearchKey(r.getUuid());
        doUpdate(r, id);
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exists");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

}
