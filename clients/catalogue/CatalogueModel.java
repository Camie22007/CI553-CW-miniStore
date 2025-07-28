package clients.catalogue;

import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CatalogueModel extends Observable {
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
        return productList;
//        notifyObservers(productList);
    }
}
