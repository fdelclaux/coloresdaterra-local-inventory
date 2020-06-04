 package Database;

 import com.itextpdf.text.PageSize;
 import com.itextpdf.text.pdf.PdfWriter;
 import com.itextpdf.text.FontFactory;
 import com.itextpdf.text.Document;
 import com.itextpdf.text.DocumentException;
 import com.itextpdf.text.Font;
 import com.itextpdf.text.Paragraph;
 import com.itextpdf.text.Phrase;
 import com.itextpdf.text.pdf.CMYKColor;
 import com.itextpdf.text.pdf.PdfPCell;
 import com.itextpdf.text.pdf.PdfPTable;
 import java.awt.Desktop;
 import objects.Item;

 import java.io.FileOutputStream;
 import java.util.Date;
 import java.util.ArrayList;
 import objects.Item;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.nio.file.Path;
 import java.text.DecimalFormat;
 import java.text.NumberFormat;
 import java.text.SimpleDateFormat;
 import java.util.Collections;
 import java.util.Comparator;

 /**
 *
 * @author felipedelclaux
 */
 public class Inventory {
 public static ArrayList<Item> inventory = new ArrayList<>();
 public static Date lastSaved;
 public static boolean savedDate;

 public static int findIndex(int r){//finds index when given reference
    int reference = r;
    int index = -1;
    for(int i = 0; i<inventory.size();i++){
        if(inventory.get(i).reference == reference){
            index = i;
            break;
        }
    }
    return index;
 }

 public static boolean firstTimeAdd(String d, int a, double c, String l, File image) //Algorithm when you add a new item
 {
    boolean success = true;
    String description = d;
    int amount = a;
    double cost = c;
    String location = l;
    File imageLoc = image;
    ArrayList p = new ArrayList();//adds the plateobjects to this arraylist in order to pass it to the Item and add it to the Inventory
    for(int i = 0; i<amount;i++){
        p.add(p.size(), new objects.PlateObject(cost, 0, location, null));
    }
    try{
        inventory.add(inventory.size(), new objects.Item(referenceGenerator(), description, null, imageLoc, p));
    }
    catch (Exception e){
        System.out.print(e);
        success = false;
    }
    return success;
 }

 public static int referenceGenerator(){
    DecimalFormat nf = new DecimalFormat("0000");
    boolean unique = false;
    int reference = 0;
    if(inventory.size()==0){
        reference = 1+(int)(Math.random()*9998);//creates random number
    }
    else{
        while(!unique){ //makes sure reference is unique
            reference = 1 + (int)(Math.random()*9998);
            for(int i = 0; i<inventory.size();i++){
                if(!(reference == inventory.get(i).reference)){
                    unique = true;
                }
            }
        }
    }
    return Integer.parseInt(nf.format(reference));
}

 public static boolean addStock(int a, double c, String l, objects.Item item){
    boolean success = false;
    objects.Item select = item;
    int amount = a;
    double cost = c;
    String location = l;
    for(int i = 0;i<amount;i++){
        select.plates.add(select.plates.size(), new objects.PlateObject(cost, 0, location, null));
        success= true; 
    }
    return success;
}

public static void print(int n, String o) throws FileNotFoundException, DocumentException, IOException{ 
    int numb = n;
    String order = o;
    Document pdfFile = new Document(PageSize.A4, 50, 50, 50, 50); //sets outs page size
    String path = "InventoryPrintout.pdf"; //creates file name
    PdfWriter writer = PdfWriter.getInstance(pdfFile, new FileOutputStream(path)); //creates file
    pdfFile.open();
    pdfFile.add(new Paragraph("Colores Da Terra",FontFactory.getFont(FontFactory.TIMES, 24, Font.BOLD))); //Headings
    pdfFile.add(new Paragraph("Print out of "+n+" objects in order: "+o+"\n\n",FontFactory.getFont(FontFactory.TIMES, 16, Font.NORMAL)));//headings
    pdfFile.add(new Paragraph(" ",FontFactory.getFont(FontFactory.TIMES, 16, Font.NORMAL)));
    PdfPTable table = new PdfPTable(5);//creates table with 5 columns
    PdfPCell reference = new PdfPCell(new Phrase ("Reference", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); 
    table.addCell(reference);//adds cell
    PdfPCell description = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    table.addCell(description);//adds cell
    PdfPCell totalStock = new PdfPCell(new Phrase("Total in Stock", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    table.addCell(totalStock);//adds cell
    PdfPCell avgCost = new PdfPCell(new Phrase("Avg. Cost(€)", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    table.addCell(avgCost);//adds cell
    PdfPCell totalCost = new PdfPCell(new Phrase("Total Cost(€)", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    table.addCell(totalCost);
    ArrayList<Item> printlist = inventory;
    switch(order){
        case "Avg. Cost: Low-High":
            Collections.sort(printlist, new Comparator<Item>() {
            @Override
            public int compare( Item p1, Item p2){
                return (int) (p1.avgCost() - p2.avgCost()); 
            }
            });
            break;
            
        case "Avg. Cost: High-Low":
            Collections.sort(printlist, new Comparator<Item>() { 
                @Override
                public int compare( Item p1, Item p2){
                    return (int) (p2.avgCost() - p1.avgCost());
                } 
            });
            break;
            
        case "Stock: Low-High":
            Collections.sort(printlist, new Comparator<Item>() { 
                @Override
                public int compare( Item p1, Item p2){
                    return (p1.totalStock() - p2.totalStock());
                } 
            });
            break;
            
            case "Stock: High-Low":
            Collections.sort(printlist, new Comparator<Item>() { 
                @Override
                public int compare( Item p1, Item p2){ 
                    return (p2.totalStock() - p1.totalStock());
                } 
            });
            break; 
        default:
            break;
    }
    
    DecimalFormat df = new DecimalFormat("##.00");
    for(int i =0; i<numb;i++){
        table.addCell(Integer.toString(printlist.get(i).reference)); table.addCell(printlist.get(i).description); 
        table.addCell(Integer.toString(printlist.get(i).totalStock())); 
        table.addCell(df.format(printlist.get(i).avgCost())); 
        table.addCell(df.format(printlist.get(i).getTotalCost()));
    }
    table.addCell("");
    table.addCell("");
    table.addCell("");
    table.addCell(new Phrase("Total:", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD))); //shows total cost of all items printed
    int total = 0;
    for(int i = 0; i<numb;i++){
        total += printlist.get(i).getTotalCost(); 
    }
    table.addCell(new Phrase(Double.toString(total), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
    pdfFile.add(table);
    pdfFile.close(); 
    Desktop.getDesktop().open(new File(path)); //opens PDF
}

public static boolean fileWriter(){ 
    boolean save = true;
    try{
        FileWriter fw = new FileWriter("resources/Files/SaveInventory.txt"); PrintWriter pw = new PrintWriter(fw);
        String sv;
        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        sv = datef.format(lastSaved);
        pw.println(sv);
        sv="";
        for(int i = 0;i < inventory.size();i++){
            if(inventory.get(i).lastSold == null){//different conditions as variables image and lastSold might be null 
                if(inventory.get(i).image == null){
                    sv = inventory.get(i).reference +","+ inventory.get(i).description +","+" "+","+ " ";//if both null fills them with blanks
                }
                else{
                    sv = inventory.get(i).reference +","+ inventory.get(i).description +","+" "+","+ inventory.get(i).image.getCanonicalPath();
                } 
            }
            else{
                if(inventory.get(i).image ==null){
                    sv = inventory.get(i).reference +","+ inventory.get(i).description +","+datef.format(inventory.get(i).lastSold)+","+" "; 
                }
                else{
                    sv = inventory.get(i).reference +","+ inventory.get(i).description +","+datef.format(inventory.get(i).lastSold)+","+inventory.get(i).image.getCanonicalPath(); 
                }
            }
            for(int p = 0;p<inventory.get(i).plates.size();p++){
                sv = sv + ","+inventory.get(i).plates.get(p).cost+","+inventory.get(i).plates.get(p).price+","+inventory.get(i).plates.get(p).location;
            }
            pw.println(sv); 
        } 
        pw.close();
    }

    catch(Exception e){
        System.out.println(e); 
        save = false;
    }
    return save; 
}

public static void fileReader(){ 
    try{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
        FileReader fr = new FileReader("resources/Files/SaveInventory.txt"); 
        BufferedReader br = new BufferedReader(fr); //wraps filereader in bufferedreader
        String temp = br.readLine();
        try{
            lastSaved = (java.util.Date) formatter.parse(temp); //tries to parse date, if not available will fail
            savedDate = true;
        }
        catch(Exception e){
            savedDate = false; 
        }
        temp = br.readLine();
        while(temp != null){
            String store [] = temp.split(",");
            int reference = Integer.parseInt(store[0]);
            String description = store[1];
            java.util.Date lastSold;
            if(store[2].equals(" ")){
                lastSold = null; 
            }
            else{
                lastSold = (java.util.Date) formatter.parse(store[2]);
            }
            File image; 
            if(store[3].equals(" ")){
                image = null; 
            }
            else{
                String imageLocation= store[3]; image = new File(imageLocation);
            }
            ArrayList p = new ArrayList(); 
            for(int j = 4; j<store.length;j+=3){ //loop that increases by 3 in order to read cost, price, and location.
                double cost = Double.parseDouble(store[j]);
                double price = Double.parseDouble(store[j+1]);
                String location = store[j+2];
                p.add(p.size(), new objects.PlateObject(cost,price,location,null));
            }
            inventory.add(inventory.size(), new objects.Item(reference, description, lastSold, image, p)); //adds item
            temp = br.readLine();
        }
    }
    catch(Exception e){ 
        System.out.println(e);
    }
}

public static void check(){
    for(int i = 0;i<inventory.size();i++){
        if(inventory.get(i).plates.size() == 0) 
            inventory.remove(i);
        } 
    }
}