package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(new File(FILE_DIR), new ObjectStreamSerializer()));
    }
}