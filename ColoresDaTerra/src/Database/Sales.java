 package Database;

 import static Database.Inventory.inventory;
 import GUI.ColoresDaTerra;
 import com.itextpdf.text.Document;
 import com.itextpdf.text.DocumentException;
 import com.itextpdf.text.Font;
 import com.itextpdf.text.FontFactory;
 import com.itextpdf.text.PageSize;
 import com.itextpdf.text.Paragraph;
 import com.itextpdf.text.Phrase;
 import com.itextpdf.text.pdf.PdfPCell;
 import com.itextpdf.text.pdf.PdfPTable;
 import com.itextpdf.text.pdf.PdfWriter;
 import java.awt.Desktop;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.Date;
 import javax.swing.JOptionPane;
 import objects.Item;
 import objects.PlateObject;

 /**
 *
 * @author felipedelclaux
 */
 public class Sales {
 public static objects.Item item;


 public static ArrayList<Item> sales = new ArrayList<>();
 
 public static int findIndex(int r){ //same Algorithm as Inventory but to find items in Sales
    int reference = r;
    int index = -1;
    for(int i = 0; i<sales.size();i++){
        if (sales.get(i).reference == reference){
            index = i;
            break;
        }
    }
    return index;
}

public static boolean referenceVerif(Item r, int a){ //checks if items trying to be sold are less than total stock
    boolean sale = false;
    if(r.totalStock() >= a){
        sale = true;
    }
    return sale;
}

public static boolean referenceCheck(int r){ // checks if item has already been sold before
    boolean present = false;
    if(sales.size()==0){
        present=false;
    }
    else{
        for(int i = 0; i<sales.size();i++){
            if(sales.get(i).reference == r)
            present =true;
        }
    }
    return present;   
}

 public static boolean recordSale(double p, int a, String l, int r){ //sale of item that has been sold before
    boolean success;
    double price = p;
    int amount = a;
    int reference = r;
    String location =l;
    int ii = Inventory.findIndex(reference); //index item(which you are selling) in inventory
    try{
        int is = findIndex(reference);//index of item in Sales
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        ft.format(d);
        for(int count = 0; count<amount;count++) {
            double cost = inventory.get(ii).plates.get(0).cost;
            sales.get(is).plates.add(new PlateObject(cost,price,location,d)); //adds items to sale
            inventory.get(ii).plates.remove(0); //removes items from inventory
        }
        inventory.get(ii).lastSold = new Date(); //updates last sold in inventory
        ft.format(inventory.get(ii).lastSold); 
        sales.get(is).lastSold = new Date(); //updates last sold in sales
        ft.format(sales.get(is).lastSold);
        success = true;
    }
    catch(Exception e){
        System.out.print(e);
        success = false; 
    }
    return success;
}
 
public static boolean recordFirstSale(double p, int a, String l, int r){ 
    double price = p;
    int amount = a;
    String location =l;
    int reference = r;
    int ii = Database.Inventory.findIndex(reference); 
    boolean success;
    try{
        ArrayList<PlateObject> plts = new ArrayList<>(); //creates arraylist to hold plates being sold.
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        ft.format(d);
        for(int i = 0; i<amount;i++){
            double cost = inventory.get(ii).plates.get(0).cost;
            plts.add(new PlateObject(cost,price,location,d)); 
            inventory.get(ii).plates.remove(0); //removes items from inventory
        }
        inventory.get(ii).lastSold = new Date();
        ft.format(inventory.get(ii).lastSold);
        String descrip = inventory.get(ii).description;
        File image = inventory.get(ii).image;
        sales.add(new Item(reference,descrip, inventory.get(ii).lastSold,image,plts)); 
        success = true;
    }
    catch(Exception e){
        System.out.println(e);
        success=false; 
    }
    return success; 
}

public static void print(Date s, Date e)throws FileNotFoundException, DocumentException, IOException{
    Date start = s;
    Date end = e;
    SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<Item> provi = new ArrayList<>();//This arrayList is going to contain all items which have plates between selected dates. 
    for(int i = 0; i<sales.size();i++){
        int size = sales.get(i).plates.size();
        boolean contains=false;
        for(int j = 0; j<size;j++){ //checks if this item has any plates sold between the selected dates
            if(sales.get(i).plates.get(j).dateSold.after(start) && sales.get(i).plates.get(j).dateSold.before(end)){
                contains =true;
                break;
            }   
        }
        if(contains){
            ArrayList<PlateObject> provp = new ArrayList<>();//this arraylist is going to contain all the plates in this item that are between the selected dates
            for(int p = 0;p<size;p++){
                if(sales.get(i).plates.get(p).dateSold.after(start) && sales.get(i).plates.get(p).dateSold.before(end)){
                    provp.add(sales.get(i).plates.get(p));
                }
            }
            provi.add(new Item(sales.get(i).reference, sales.get(i).description, sales.get(i).lastSold, sales.get(i).image, provp));
        }
    }
    Document pdfFile = new Document();
    String path = "SalesPrintout.pdf";
    PdfWriter writer = PdfWriter.getInstance(pdfFile, new FileOutputStream(path));
    pdfFile.open();
    pdfFile.add(new Paragraph("Colores Da Terra Sales",FontFactory.getFont(FontFactory.TIMES, 24, Font.BOLD))); 
    pdfFile.add(new Paragraph("Print out of objects between "+datef.format(s)+" - "+datef.format(e),FontFactory.getFont(FontFactory.TIMES, 16, Font.NORMAL)));
    PdfPTable t = new PdfPTable(6);
    t.setSpacingBefore(15f);//sets spacing before table
    t.setWidthPercentage(100); // re-sizes table to look good
    PdfPCell c1 = new PdfPCell(new Phrase ("Reference", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c1);
    PdfPCell c2 = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c2);
    PdfPCell c3 = new PdfPCell(new Phrase("Total Sold", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c3);
    PdfPCell c4 = new PdfPCell(new Phrase("Total Cost", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c4);
    PdfPCell c5 = new PdfPCell(new Phrase("Total Revenue", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c5);
    PdfPCell c6 = new PdfPCell(new Phrase("Total Profit", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); t.addCell(c6);
    DecimalFormat df = new DecimalFormat("##.00");
    for(int i =0; i<provi.size();i++){
        t.addCell(Integer.toString(provi.get(i).reference));
        t.addCell(provi.get(i).description);
        t.addCell(Integer.toString(provi.get(i).plates.size()));
        t.addCell(df.format(provi.get(i).getTotalCost()));
        t.addCell(df.format(provi.get(i).getTotalRevenue()));
        t.addCell(df.format(provi.get(i).getTotalProfit()));
    }
    t.addCell("");
    t.addCell("");
    t.addCell(new Phrase("Total:", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    double totalCost = 0;
    double totalProfit =0;
    double totalRevenue = 0;
    for(int i = 0; i< provi.size();i++){
            totalCost += provi.get(i).getTotalCost();
            totalProfit += provi.get(i).getTotalProfit();
            totalRevenue += provi.get(i).getTotalRevenue();
        }
    DecimalFormat decf = new DecimalFormat("##.00");
    t.addCell(new Phrase(decf.format(totalCost), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    t.addCell(new Phrase(decf.format(totalRevenue), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    t.addCell(new Phrase(decf.format(totalProfit), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));   
    pdfFile.add(t); 
    pdfFile.close();
    Desktop.getDesktop().open(new File(path)); 
}



public static boolean fileWriter(){
boolean save = true;
    try{
        FileWriter fw = new FileWriter("resources/Files/SaveSales.txt"); PrintWriter pw = new PrintWriter(fw);
        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy"); String sv = "";
        for(int i = 0;i < sales.size();i++){ 
            if(sales.get(i).lastSold == null){ //checks to see if either lastSold or the image are null in order to avoid NullExceptions
                if(sales.get(i).image == null){
                    sv = sales.get(i).reference +","+ sales.get(i).description +","+" "+","+ " ";
                } 
                else{
                    sv = sales.get(i).reference +","+ sales.get(i).description +","+" "+","+ sales.get(i).image.getCanonicalPath(); }
                } 
            else{
                if(sales.get(i).image == null){
                    sv = sales.get(i).reference +","+ sales.get(i).description +","+datef.format(sales.get(i).lastSold)+","+" ";
                }   
                else{
                    sv = sales.get(i).reference +","+ sales.get(i).description +","+datef.format(sales.get(i).lastSold)+","+sales.get(i).image.getCanonicalPath();
                }
            }
            for(int p = 0;p < sales.get(i).plates.size();p++){
                sv = sv +","+ sales.get(i).plates.get(p).cost+","+sales.get(i).plates.get(p).price+","+sales.get(i).plates.get(p).location+","+datef.format(sales.get(i).plates.get(p).dateSold); 
            }
        pw.println(sv); 
        }
    pw.close(); 
    }
    catch(Exception e){ System.out.println(e);
        save = false;
    }
    return save; 
}

public static void fileReader(){ 
    try{
        FileReader fr = new FileReader("resources/Files/SaveSales.txt"); 
        BufferedReader br = new BufferedReader(fr);
        String temp = br.readLine();
        while(temp != null)
        {
            String store [] = temp.split(",");
            int reference = Integer.parseInt(store[0]); 
            String description = store[1]; 
            java.util.Date lastSold; 
            if(store[2].equals(" ")){
                lastSold = null; 
            }
            else{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                lastSold = (java.util.Date) formatter.parse(store[2]);
            }
            File image; 
            if(store[3].equals(" ")){
                image = null; 
            }
            else{
                String imageLocation= store[3]; 
                image = new File(imageLocation);
            }
            ArrayList p = new ArrayList(); 
            for(int j = 4; j<store.length;j+=4){//loops that increases by 4 to add cost,price, location, and date sold
                double cost = Double.parseDouble(store[j]); 
                double price = Double.parseDouble(store[j+1]); 
                String location = store[j+2];
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //creates formatter to fromat the date
                Date d = (java.util.Date) formatter.parse(store[j+3]); //parses from string to date
                p.add(new objects.PlateObject(cost,price,location, d));
            }
            sales.add(new objects.Item(reference, description, lastSold, image, p)); 
            temp = br.readLine();
        }
    }
    catch(Exception e){ 
        System.out.println(e);
    }
}
}
