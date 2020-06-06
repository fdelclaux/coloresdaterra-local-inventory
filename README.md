# coloresdaterra-local-inventory

GUI based Desktop application built using Java.

 Inventory record for crockery company (Colores Da Terra) in Spain.

 Desktop application to locally save inventory and record sales making it manageable for the small comapny owner to keep track of her
 inventories and her profits.

 Functionality:

 Add new inventory, with the ability to fill in a description and upload an image of the product. Then choose price, amount and location of the product. You can also add inventory to an exisiting product in the current inventory. 

 Record sales of any product in inventory. Will calculate tha marginal profit of your sale and adjust your inventory. 

 Create PDF summary reports of your sales. 

========================
BUILD OUTPUT DESCRIPTION
========================

When you build a Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and
type the following:

java -jar "ColoresDaTerra.jar" 

To distribute this project, zip up the dist folder (including the lib folder)
and distribute the ZIP file.

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
