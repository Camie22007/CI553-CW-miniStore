package clients.utils;

public class CatalogueSelection {
    private static String selectedProductNum = null;

    public static void setSelectedProductNum(String ProductNum) {
        selectedProductNum = ProductNum;
    }

    public static String getSelectedProductNum() {
        return selectedProductNum;
    }

    public static void clearSelectedProductNum() {
        selectedProductNum = null;
    }
}
