package com.example.fotoramka.Model;


public    class FileFolder {
    protected String _name;
    protected String _path;
    protected String _creationDate;
    protected String _lastModified;


    public FileFolder(String name, String path)
    {
        _name =name;
        _path = path;
        _creationDate =java.util.Calendar.getInstance().getTime().toString();
        _lastModified = _creationDate;
    }

   public FileFolder(String name, String path, String creationDate, String lastModified)
    {
        _name =name;
        _path = path;
        _creationDate = creationDate;
        _lastModified = lastModified;
    }


    public String GetName()
    {
        return  _name;
    }

    public String GetPath() {
        return _path;//  _path!=null?_path.substring(0, _path.length()-1):"";
    }

    public  String CreationDate()
    {
        return  _creationDate;
    }

    public  String GetLastModified()
    {
        return  _lastModified;
    }



}
