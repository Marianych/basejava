package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " can not be read/write");
        }
        this.directory = directory;
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    boolean isExist(File file) {
        return file.exists();
    }

    @Override
    void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error on Get", file.getName(), e);
        }
    }

    abstract Resume doRead(File file) throws IOException;

    @Override
    void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error on delete", file.getName());
        }
    }

    @Override
    void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error on update", file.getName(), e);
        }
    }

    @Override
    List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> all = new ArrayList<>(files.length);
        for (File f : files) {
            all.add(doGet(f));
        }
        return all;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO eroor on clear", null);
        }
        for (File f : files) {
            doDelete(f);
        }
    }

    @Override
    public int size() {
        String[] files = directory.list();
        if (files == null) {
            throw new StorageException("IO error on size", null);
        }
        return files.length;
    }
}
