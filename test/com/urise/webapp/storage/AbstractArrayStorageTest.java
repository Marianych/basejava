package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    AbstractArrayStorageTest(Storage testStorage) {
        this.storage = testStorage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));

    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        String uuid = "";
        storage.clear();
        try {
            for (int i = 1; i <= 10000; i++) {
                uuid = "resume " + i;
                storage.save(new Resume(uuid));
            }
        } catch (StorageException se) {
            Assert.fail();
        }
        storage.save(new Resume(UUID_2));
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
    }

    @Test
    public void getAll() {
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}