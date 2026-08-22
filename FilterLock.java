public class FilterLock implements Lock 
{

    private final int n;
    private final VolatileInt[] level;
    private final VolatileInt[] victim;

    public FilterLock(int n) 
    {
        this.n = n;
        level = new VolatileInt[n];
        victim = new VolatileInt[n];

        //all threads start outside waiting room (level 0)
        for (int i=0; i<n; i++){
            level[i] = new VolatileInt(0);
        }

        //no victims in the waiting room
        for (int Level=1; Level<n; Level++){
            victim[Level] = new VolatileInt(-1);
        }
    }

    @Override
    public void lock(int threadId) 
    {
        int i = threadId;

        for(int Level=1; Level<n; Level++){
            level[i].value = Level; //current thread enters waiting room

            victim[Level].value = i; //declares itself as victim

            //algorithm waits until theres another thread at same level or higher
            //And current thread is still victim

            //work out while condition

        }
    }

    @Override
    public void unlock(int threadId) 
    {
        level[threadId].value = 0; // exit waiting room
    }
}