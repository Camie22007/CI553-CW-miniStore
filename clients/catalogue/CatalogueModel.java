package clients.catalogue;

import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CatalogueModel extends Observable {
    private static final Logger logger = LogManager.getLogger(CatalogueModel.class);

    private StockReader theStock;
    private List<Product> productList = new ArrayList<>();

    public CatalogueModel(MiddleFactory mf) {
        try {
            theStock = mf.makeStockReader();
        } catch (Exception e) {
            DEBUG.error("CatalogueModel.constructor\n%s\n", e.getMessage());
        }
    }

    public List LoadAllProducts() {
        productList.clear();

        logger.info("CatalogueModel.loadAllProducts()");

        int maxProducts = 10;
        for (int i = 0; i < maxProducts; i++) {
            String productId = String.format("%04d", i);
            try {
                if (theStock.exists(productId)) {
                    Product product = theStock.getDetails(productId);
                    productList.add(product);
                }
            } catch (StockException e) {
                DEBUG.error("CatalogueModel.LoadAllProducts\n%s\n", e.getMessage());
            }
        }

        setChanged();
        notifyObservers(productList);  // <-- THIS IS MISSING, ADD IT BACK
        return productList;
    }

    public ImageIcon getProductImage(String productId){
        try{
            ImageIcon icon = theStock.getImage(productId);
            if (icon != null){
                Image scaledImage = icon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (StockException e) {
            DEBUG.error("CatalogueModel.LoadAllProducts\n%s\n", e.getMessage());
        }
        return null;
    }
}

