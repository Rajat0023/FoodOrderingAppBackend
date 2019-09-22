package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.ItemRepository;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     *
     * @param id
     * @return
     * @throws ItemNotFoundException
     */
    public ItemEntity getItemForItemId(String id) throws ItemNotFoundException {
        ItemEntity itemEntity= itemRepository.getItemById(id);
        if(itemEntity==null){
            throw new ItemNotFoundException("INF-003","No item by this id exist");
        }
        return itemEntity;

    }
}
