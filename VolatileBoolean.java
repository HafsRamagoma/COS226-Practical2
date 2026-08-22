public class VolatileBoolean 
{

    public volatile boolean value;

    public VolatileBoolean(boolean value) 
    {
        this.value = value;
    }
    
}

/*
    voltile on an array only makes the reference volatile , not the elements
    so other threads may not see when you update an element
    therefore we set it i the wrapper class
*/