package objects;

 import java.io.File;
 import static java.lang.String.format;
 import java.util.ArrayList;
 import java.util.Date;
 import java.text.SimpleDateFormat;
 import java.text.DecimalFormat;

 /**
  *
  * @author felipedelclaux
  */
 public class Item {
     public int reference;
     public Date lastSold;
     public String description;
     public File image;
     public ArrayList<PlateObject> plates;


     public Item(){

        reference = 0;
        lastSold = null;
        description = "00";
        image = null;
        plates = null;
     }
     public Item(int r,  String d, Date l, File i, ArrayList<PlateObject> p){
        reference = r;
        lastSold = l;
        description = d;
        image = i;
        plates = p;
     }

    public double avgCost(){
        double total = 0;
        int count = 0;
        for(int i = 0; i<plates.size();i++){
            total = total + plates.get(i).cost;
            count++;
        }
        double avgCost = total/count;
        return avgCost;
    }
    
    public int totalStock(){
        int tot = plates.size();
        return tot;          
    }

    public double getTotalCost(){
        double totalCost = 0;
        for(int i = 0; i<plates.size();i++){
            totalCost += plates.get(i).cost;
        }
        return totalCost;
    }

    public double getTotalRevenue(){
        double totalRevenue = 0;
        for(int j = 0; j<plates.size();j++){
            totalRevenue += plates.get(j).price;	
        }
        return totalRevenue;	
    }

    public double getTotalProfit(){
        double totalProfit = 0;
        for(int i = 0; i<plates.size();i++){
            totalProfit += (plates.get(i).price - plates.get(i).cost);	
        }
        return totalProfit;	
    }
}


