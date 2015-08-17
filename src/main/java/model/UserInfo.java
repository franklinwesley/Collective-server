package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfo{

    private String id;
    private String name;
    private String email;
    private String picture;
    private String latitude;
    private String longitude;

    private ArrayList<String> movies;
    private ArrayList<String> books;
    private ArrayList<String> games;
    private ArrayList<String> music;
    private ArrayList<String> tv;

    public UserInfo(String id, String name, String email, String picture, String latitude, String longitude){
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public UserInfo(){}


    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getURLPicture(){
        return picture;
    }

    public ArrayList<String> getMovies(){
        if (movies == null){
            return new ArrayList<String>();
        }
        return movies;
    }

    public ArrayList<String> getBooks(){
        if (books == null){
            return new ArrayList<String>();
        }
        return books;
    }

    public ArrayList<String> getGames(){
        if (games == null){
            return new ArrayList<String>();
        }
        return games;
    }

    public ArrayList<String> getMusic(){
        if (music == null){
            return new ArrayList<String>();
        }
        return music;
    }

    public ArrayList<String> getTv(){
        if (tv == null){
            return new ArrayList<String>();
        }
        return tv;
    }

    public void setId(String id){ this.id = id; }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public void addUserMovies(ArrayList<String> movies){
        this.movies = new ArrayList<String>(movies);
    }

    public void addUserGames(ArrayList<String> games){
        this.games = new ArrayList<String>(games);
    }

    public void addUserBooks(ArrayList<String> books){
        this.books = new ArrayList<String>(books);
    }

    public void addUserMusic(ArrayList<String> music){
        this.music = new ArrayList<String>(music);
    }

    public void addUserTv(ArrayList<String> tv){
        this.tv = new ArrayList<String>(tv);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}