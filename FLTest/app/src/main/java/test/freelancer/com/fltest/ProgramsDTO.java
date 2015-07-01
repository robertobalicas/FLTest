package test.freelancer.com.fltest;

/**
 * Created by Android 18 on 7/1/2015.
 */
public class ProgramsDTO {

    int _id;
    String _name;
    String _start_time;
    String _end_time;
    String _channel;
    String _rating;


    public ProgramsDTO(){

    }

    public ProgramsDTO(int id,String pname, String start_time, String end_time, String channel, String rating){
        _id = id;
         _name=pname;
         _start_time=start_time;
         _end_time=end_time;
         _channel=channel;
         _rating=rating;
    }

    public ProgramsDTO(String pname, String start_time, String end_time, String channel, String rating){
        _name=pname;
        _start_time=start_time;
        _end_time=end_time;
        _channel=channel;
        _rating=rating;
    }

    // getting ID
    public int getID(){
        return _id;
    }

    // setting id
    public void setID(int id){
        _id = id;
    }

    // getting name
    public String getName(){
        return _name;
    }

    // setting name
    public void setName(String name){
        _name = name;
    }

    // getting start time
    public String getStart_time(){
        return _start_time;
    }

    // setting start time
    public void setStart_time(String start_time){
        _start_time = start_time;
    }

    // getting end time
    public String getEnd_time(){
        return _end_time;
    }

    // setting end  time
    public void setEnd_time(String end_time){
        _end_time = end_time;
    }

    // getting channel
    public String getChannel(){
        return _channel;
    }

    // setting channel
    public void setChannel(String channel){
        _channel = channel;
    }

    // getting rating
    public String getRating(){
        return _rating;
    }

    // setting rating
    public void setRating(String rating){
        _rating = rating;
    }
}
