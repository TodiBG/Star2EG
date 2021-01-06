package fr.istic.mob.star2eg.modeles;


/**
 * Created by Bonaventure Gbehe && Rebecca Ehua on 31/12/2020.
 */

public class StopTime {

    private String tripId;
    private String arrivalTime;
    private String departureTme;
    private String stopId;
    private String StopSequence;

    public StopTime(String tripId, String arrivalTime, String departureTme, String stopId, String stopSequence) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTme = departureTme;
        this.stopId = stopId;
        StopSequence = stopSequence;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTme() {
        return departureTme;
    }

    public void setDepartureTme(String departureTme) {
        this.departureTme = departureTme;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(String stopSequence) {
        StopSequence = stopSequence;
    }

    @Override
    public String toString() {
        return "StopTime{" +
                "tripId:" + tripId +
                ", arrivalTime:'" + arrivalTime + '\'' +
                ", departureTme:'" + departureTme + '\'' +
                ", stopId:" + stopId +
                ", StopSequence:'" + StopSequence + '\'' +
                '}';
    }

    public String sql() {
        return "("+tripId+",'"+arrivalTime+"','"+departureTme+"',"+stopId+",'"+StopSequence+"')";
    }
}
