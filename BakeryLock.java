public class BakeryLock implements Lock 
{

    private final int n;
    private final VolatileBoolean[] flag; 
    private final VolatileInt[] label;

    /*
        -volatile used to ensure changes made by one thread are visible
        to other threads
        -values are flushed and read in main memory only not cache
    */

    public BakeryLock(int n) 
    {
        this.n = n; //indicate the number of threads

        //create array of wrapper objects
        flag = new VolatileBoolean[n]; //all threads [false, false, false,...]
        label = new VolatileInt[n]; //out of queue [0,0,0,...]

        for (int i=0; i<n; i++){
            flag[i] = new VolatileBoolean(false); //[false, false, false,...]
            label[i] = new VolatileInt(0); //[0,0,0,...]
        }
    }

    @Override
    public void lock(int threadId) 
    {
        int i = threadId;

        //interest
        flag[i].value = true; //current thread wants lock

        int max = 0; //get max label of all threads

        for (int j=0; j<n; j++){
            if (label[j].value > max){
                max = label[j].value;
            }
        }

        label[i].value = max + 1; //current thread updates its ticket number

        //wait for current thread turn
        for(int j=0; j<n; j++){
            //other thread is interested so now we compare
            //with curr thread in lexicographic order
            while(j!=i && flag[j].value &&
                    (label[j].value < label[i].value ||
                        (label[j] == label[i] &&
                            j<i))){
                //wait
            }
        }

        //use threadID to compare in case two threads have the same label
    }

    @Override
    public void unlock(int threadId) 
    {
        flag[threadId].value = false; //current thread is done and leaves CS
    }
}