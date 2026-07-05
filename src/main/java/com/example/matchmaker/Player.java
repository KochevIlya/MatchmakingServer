package com.example.matchmaker;

public class Player {

    public String Nickname;
    private int _rating;
    private String _id;

    public int getRating(){
        return _rating;
    }

    public void setRating(int rating){
        _rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s (Rating: %d)", Nickname, _rating);
    }
    public String getId()
    {
        return _id;
    }
    public void setId(String id)
    {
        _id = id;
    }

    public Player(String id, String nickname, int rating)
    {
        _id = id;
        Nickname = nickname;
        _rating = rating;



    }
}
