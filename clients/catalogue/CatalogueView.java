package clients.catalogue;

import catalogue.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class CatalogueView implements Observer {
    private static final int H = 500;
    private static final int W = 600;

    private JPanel productPanel = new JPanel();
    private CatalogueController controller;
    private String mode;
    private CatalogueModel model;

    public CatalogueView(RootPaneContainer rpc, String mode)
    {
        this.mode = mode;
        Container cp = rpc.getContentPane();
        cp.setLayout(new BorderLayout());
        rpc.getContentPane().setPreferredSize(new Dimension(W, H));

        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productPanel);

        cp.add(new JLabel("Product Catalogue - Mode: "+ mode, SwingConstants.CENTER), BorderLayout.NORTH);
        cp.add(scrollPane, BorderLayout.CENTER);

    }

    public void setController(CatalogueController controller){
        this.controller = controller;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof List<?>)
        {
//            List<?> productList = (List<?>) arg;
            productPanel.removeAll();


            List productList = model.LoadAllProducts();

            for (Object obj : productList)
            {
                if (obj instanceof Product)
                {
                    Product product = (Product) obj;
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                    panel.add(new JLabel(product.getDescription()));
                    panel.add(new JLabel(String.format("£%.2f", product.getPrice())));

                    if (!mode.equals("CUSTOMER"))
                    {
                        panel.add(new JLabel("Stock: " + product.getQuantity()));
                        System.out.println("NOT CUSTOMER");
                    }

                    if (mode.equals("CASHIER"))
                    {
                        JButton addToCartBtn = new JButton("Add to Cart");
                        // Add ActionListener to handle cart adding later
                        panel.add(addToCartBtn);
                        System.out.println("IS CASHIER");
                    }

                    productPanel.add(panel);
                }
            }
            productPanel.revalidate();
            productPanel.repaint();
        }
    }

}
