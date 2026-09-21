package com.pedidos360.bff.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OT_ITEM")
public class OtItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "OT_ID", nullable = false, length = 24)
    private String otId;

    @Column(name = "CONCEPTO", nullable = false, length = 40)
    private String concepto;

    @Column(name = "CANTIDAD", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "PRECIO_UNIT", nullable = false, precision = 12)
    private BigDecimal precioUnit;

    @Column(name = "SUBTOTAL", insertable = false, updatable = false, precision = 12)
    private BigDecimal subtotal;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getItemId() {
        return itemId;
    }

    public String getOtId() {
        return otId;
    }

    public String getConcepto() {
        return concepto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnit() {
        return precioUnit;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}