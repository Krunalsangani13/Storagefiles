package com.example.storagefiles.Model;

public class FavoriteModel {
    int id;
    String favorite_file,file_path;

    public FavoriteModel(int id, String favorite_file,String file_path) {
        this.id = id;
        this.favorite_file = favorite_file;
        this.file_path = file_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavorite_file() {
        return favorite_file;
    }

    public void setFavorite_file(String favorite_file) {
        this.favorite_file = favorite_file;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
