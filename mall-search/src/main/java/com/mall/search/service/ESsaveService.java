package com.mall.search.service;

import com.mall.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ESsaveService{

    boolean saveProduct(List<SkuEsModel> skuEsModels) throws IOException;
}
