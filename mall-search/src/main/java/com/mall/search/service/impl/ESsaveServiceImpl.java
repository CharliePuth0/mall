package com.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.mall.common.es.SkuEsModel;
import com.mall.search.service.ESsaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("ESsaveService")
@Slf4j
public class ESsaveServiceImpl implements ESsaveService {

    @Autowired
    private RestHighLevelClient esRestClient;

    @Override
    public boolean saveProduct(List<SkuEsModel> skuEsModels) throws IOException {


        //批量保存
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel e: skuEsModels) {
            IndexRequest indexRequest = new IndexRequest("mall-product");
            indexRequest.id(e.getSkuId().toString());
            String jsonString = JSON.toJSONString(e);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulk = esRestClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        boolean b = bulk.hasFailures();

        List<String> collect = Arrays.asList(bulk.getItems()).stream().map(item -> {
            return item.getId();
        }).collect(Collectors.toList());

        log.info("商品上架完成",collect);

        return b;


    }
}
