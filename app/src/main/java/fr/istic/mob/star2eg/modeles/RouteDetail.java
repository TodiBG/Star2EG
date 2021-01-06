package fr.istic.mob.star2eg.modeles;
/**
 * Created by Bonaventure Gbehe && Rebecca Ehua on 31/12/2020.
 */
public class RouteDetail {
    public String name ;
    public String hour ;

    public RouteDetail( String name, String hour ) {
        this.name = name;
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public String getHour() {
        return hour;
    }

}
