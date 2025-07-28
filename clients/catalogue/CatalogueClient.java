package clients.catalogue;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

public class CatalogueClient {
    public static void main (String args[])
    {
        String stockURL = args.length < 1         // URL of stock R
                ? Names.STOCK_R           //  default  location
                : args[0];                //  supplied location

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRInfo( stockURL );

        String mode = args.length < 2 ? "CUSTOMER" : args[1];  // Mode passed as arg 2
        displayGUI(mrf, mode);  // Create GUI
    }

    private static void displayGUI(MiddleFactory mf, String mode)
    {
        System.out.println("STARTING TO DISPLAY GUI");

        JFrame window = new JFrame();
        window.setTitle( "Catalogue View - " + mode );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        CatalogueModel model = new CatalogueModel(mf);
        CatalogueView view  = new CatalogueView( window, mode);
        CatalogueController controller  = new CatalogueController( model, view );
        view.setController( controller );

        model.addObserver( view );  // Add observer to the model

        System.out.println("LOADING ALL PRODUCTS");

        model.LoadAllProducts();  // Load all products
        window.setVisible(true);         // Display Scree
    }
}
