package com.pedidos360.bff.repository;

import com.pedidos360.bff.entity.OtItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtItemRepository extends JpaRepository<OtItem, Long> {

    List<OtItem> findByOtId(String otId);
}