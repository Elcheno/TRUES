package com.elcheno.trues.model.dto;

import com.elcheno.trues.model.domain.Product;

public class ProductInfoDTO {
    /**
     * This class is a DTO(Singleton) for the Product class.
     * @see Product
     * @author Elcheno
     */

    private static ProductInfoDTO _newInstance;
    private static Product _product;

    private ProductInfoDTO(Product product) {
        _product = product;
    }

    public static ProductInfoDTO getInstance(){
        if(_newInstance == null){
            _newInstance = new ProductInfoDTO(null);
        }
        return _newInstance;
    }
    public static void setProduct(Product product){
        _product = product;
    }
    public static Product getProduct(){
        return _product;
    }
}
