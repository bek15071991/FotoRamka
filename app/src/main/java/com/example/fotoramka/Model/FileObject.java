package com.example.fotoramka.Model;

import java.io.File;

public class FileObject extends FileFolder {
    private String _extension;

    FileObject(String name, String path) {
        super(name, path);

    }

    @Override
    public String GetName() {
        return  _name;
    }

    public String GetExtenstion()
    {
        return _extension =  new File(_name).getName().split("\\.")[1];
    }

    public FileObject(String name, String path, String creationDate, String lastModified) {
        super(name, path, creationDate, lastModified);

    }

    public String GetFullName()
    {
        return _path+"/"+_name;
    }
}
