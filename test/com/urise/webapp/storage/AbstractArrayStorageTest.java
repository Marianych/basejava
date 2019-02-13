package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage testStorage) {
        super(testStorage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 1; i <= 10000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException se) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

}