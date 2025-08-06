package clients.catalogue;

import catalogue.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import clients.utils.CatalogueSelection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CatalogueView implements Observer {
    private static final Logger logger = LogManager.getLogger(CatalogueView.class);
    private static final int H = 300;
    private static final int W = 400;

    private JPanel productPanel = new JPanel();
    private final JLabel pageTitle = new JLabel();
    private CatalogueController controller;
    private CatalogueSelection selection;
    private String mode;
    private CatalogueModel model;
    private final JPanel   productListPanel  = new JPanel();
    private final JScrollPane scrollPane      = new JScrollPane();

    public CatalogueView(RootPaneContainer rpc, String mode, int x, int y, CatalogueModel model) {

        this.mode = mode;
        this.model = model;
        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
        cp.setLayout(null);
        rootWindow.setSize(W, H);
        rootWindow.setLocation(x, y);

        cp.setLayout(new BorderLayout());

        Font f = new Font("Monospaced", Font.PLAIN, 12);

        JPanel titlePanel = new JPanel();
        pageTitle.setText("Product Catalogue - Mode: " + mode);
        titlePanel.add(pageTitle);
        cp.add(titlePanel, BorderLayout.NORTH);

//        pageTitle.setBounds(110, 0, 270, 20);
//        pageTitle.setText("Product Catalogue - Mode: " + mode);
//        cp.add(pageTitle);

//        theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane


        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(productListPanel);
//        scrollPane.setBounds(110, 100, 270, 160);
        cp.add(scrollPane, BorderLayout.CENTER);


    }

    public void setController(CatalogueController controller) {
        this.controller = controller;
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.info("CatalogueView.update() CALLED with arg: " + arg);
        productListPanel.removeAll();

        if (arg instanceof List<?>) {
            logger.info("CatalogueView.update(), iterating through product list");
            List<?> productList = (List<?>) arg;

            for (Object obj : productList) {
                if (obj instanceof Product) {
                    Product product = (Product) obj;

                    JPanel productCard = new JPanel();
                    productCard.setLayout(new BoxLayout(productCard, BoxLayout.Y_AXIS));
                    productCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    productCard.setMaximumSize(new Dimension(300,100));

                    JLabel descriptionLabel = new JLabel(product.getDescription());
                    JLabel priceLabel = new JLabel(String.format("Price: £%.2f\n", product.getPrice()));

                    productCard.add(descriptionLabel);
                    productCard.add(priceLabel);

                    if (mode.equals("CUSTOMER")) {
                        String stockStatus = (product.getQuantity() > 0) ? "In Stock" : "Out of Stock";
                        JLabel stockLabel = new JLabel(stockStatus);
                        JButton selectButton = new JButton("Select");
                        selectButton.addActionListener( e -> {
                            selection.setSelectedProductNum(product.getProductNum());
                            SwingUtilities.getWindowAncestor(selectButton).dispose();
                        });

                        productCard.add(stockLabel);
                        productCard.add(selectButton);
                    }

                    if (mode.equals("CASHIER")) {
                        JLabel stockLabel = new JLabel("Stock: " + product.getQuantity());
                        productCard.add(stockLabel);
                        JButton selectButton = new JButton("Select");
                        selectButton.addActionListener( e -> {
                            selection.setSelectedProductNum(product.getProductNum());
                            SwingUtilities.getWindowAncestor(selectButton).dispose();
                        });
                        productCard.add(selectButton);
                    }

                    if (mode.equals("BACKDOOR")) {
                        JLabel idLabel = new JLabel("ID: " + product.getProductNum());
                        JLabel stocklabel = new JLabel("Stock: " + product.getQuantity());
                        JButton selectButton = new JButton("Select");
                        selectButton.addActionListener( e -> {
                            selection.setSelectedProductNum(product.getProductNum());
                            SwingUtilities.getWindowAncestor(selectButton).dispose();
                        });
                        productCard.add(idLabel);
                        productCard.add(stocklabel);
                        productCard.add(selectButton);
                    }

                    JLabel imageLabel = new JLabel();
                    ImageIcon productImage = model.getProductImage(product.getProductNum());
                    if (productImage != null) {
                        imageLabel.setIcon(productImage);
                        productCard.add(imageLabel);
                    }

                    productListPanel.add(productCard);
                    productListPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                }
            }
        } else {
            System.err.println("Update called with wrong argument type.");
        }
        productListPanel.revalidate();
        productListPanel.repaint();
    }

}
