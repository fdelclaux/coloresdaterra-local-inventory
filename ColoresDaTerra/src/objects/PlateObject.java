package objects; 
 
import java.util.Date;

/**
*
* @author felipedelclaux */
public class PlateObject{
    public double cost;
    public double price;
    public String location;
    public Date dateSold;

    public PlateObject(double c, double p, String l, Date d){
        cost = c;
        price = p;
        location = l; 
        dateSold = d;
    }
}
