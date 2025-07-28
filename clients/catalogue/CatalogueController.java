package clients.catalogue;

public class CatalogueController {
    private CatalogueModel model;
    private CatalogueView view;

    public CatalogueController(CatalogueModel model, CatalogueView view){
        this.model = model;
        this.view = view;
    }
}
