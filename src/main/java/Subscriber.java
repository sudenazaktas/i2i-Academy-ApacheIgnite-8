public class Subscriber {
    //customerId is the primary key that uniquely identifies each subscriber
    private String customerId;

    // dataUsage represents the amount of mobile data used (in MB)
    private double dataUsage;

    // smsUsage represents the number of SMS messages sent
    private int smsUsage;

    // callUsage represents the number of call minutes used
    private int callUsage;

    // Empty constructor is required so Ignite can map table rows to this class
    public Subscriber(){
    }

    public Subscriber(String customerId, double dataUsage, int smsUsage, int callUsage){
        this.customerId=customerId;
        this.dataUsage=dataUsage;
        this.smsUsage=smsUsage;
        this.callUsage=callUsage;
    }

    // Getters and setters are required for Ignite to read/write the fields
    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId){
        this.customerId=customerId;
    }

    public double getDataUsage(){
        return dataUsage;
    }

    public void setDataUsage(double dataUsage){
        this.dataUsage=dataUsage;
    }

    public int getSmsUsage(){
        return smsUsage;
    }

    public void setSmsUsage(int smsUsage){
        this.smsUsage=smsUsage;
    }

    public int getCallUsage(){
        return callUsage;
    }

    public void setCallUsage(int callUsage){
        this.callUsage=callUsage;
    }

    // Used later to print the subscriber's final state to the console
    @Override
    public String toString(){
        return "Subscriber{" +
                "customerId='" + customerId + '\'' +
                ", dataUsage=" + dataUsage +
                ", smsUsage=" + smsUsage +
                ", callUsage=" + callUsage +
                '}';
    }
}

