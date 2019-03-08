package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
    }

    abstract void doWrite(Resume r, OutputStream os) throws IOException;

    abstract Resume doRead(InputStream is) throws IOException;

    @Override
    Path getSearchKey(String uuid) {
        return Paths.get(uuid);
    }

    @Override
    boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    void doSave(Resume r, Path path) {

        doUpdate(r, path);
    }

    @Override
    void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.toString(), e);
        }
    }

    @Override
    void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString(), e);
        }
    }

    @Override
    Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path get error", path.toString(), e);
        }
    }

    @Override
    List<Resume> doGetAll() {
        List<Resume> all = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> all.add(doGet(directory)));
        } catch (IOException e) {
            throw new StorageException("Path write error", null, e);
        }
        return all;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        List pathList;
        try {
            pathList = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path list size error", null);
        }
        return pathList.size();
    }
}
