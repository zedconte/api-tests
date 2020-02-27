package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rortega
 */
public class User
{
    @JsonProperty("name")
    String name;

    @JsonProperty("job")
    String job;

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @JsonProperty("name")
    public String getName ()
    {
        return name;
    }

    @JsonProperty("name")
    public void setName (String name)
    {
        this.name = name;
    }

    @JsonProperty("job")
    public String getJob ()
    {
        return job;
    }

    @JsonProperty("job")
    public void setJob (String job)
    {
        this.job = job;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", job = "+job+"]";
    }
}