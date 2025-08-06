package clients.catalogue;

import clients.PosOnScrn;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class CatalogueClient {
    private static final Logger logger = LogManager.getLogger(CatalogueClient.class);
    public static void main (String args[])
    {
        String stockURL = args.length < 1         // URL of stock R
                ? Names.STOCK_R           //  default  location
                : args[0];                //  supplied location

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRInfo( stockURL );

        String mode = args.length > 0 ? args[0] : "CUSTOMER";

        logger.info("CALLING GUI DISPLAY");
        displayGUI(mrf, mode);  // Create GUI
    }

    public static void displayGUI(MiddleFactory mf, String mode)
    {
        logger.info("STARTING TO DISPLAY GUI");

        JFrame window = new JFrame();
        window.setTitle("Catalogue View - " + mode);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        CatalogueModel model = new CatalogueModel(mf);
        CatalogueView view = new CatalogueView(window, mode, pos.width, pos.height, model);
        CatalogueController controller = new CatalogueController(model, view);
        view.setController(controller);

        model.addObserver(view);

        logger.info("LOADING ALL PRODUCTS");
        model.LoadAllProducts();

        window.setVisible(true);
    }
}
