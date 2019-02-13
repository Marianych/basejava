package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {

    Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    AbstractStorageTest(Storage testStorage) {
        this.storage = testStorage;
    }

    @Before
    public void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        Assert.assertEquals(3,storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExists() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get("dummy");
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
        Assert.assertEquals(UUID_2, storage.get(UUID_2).getUuid());
        Assert.assertEquals(UUID_3, storage.get(UUID_3).getUuid());

    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotFound() {
        storage.delete(UUID_2);
        storage.delete(UUID_2);
    }

    @Test
    public void update() {

        Resume updateResume = new Resume(UUID_2);
        storage.update(updateResume);
        Assert.assertSame(storage.get(UUID_2), updateResume);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(UUID_1, storage.getAll()[0].getUuid());
        Assert.assertEquals(UUID_2, storage.getAll()[1].getUuid());
        Assert.assertEquals(UUID_3, storage.getAll()[2].getUuid());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}
