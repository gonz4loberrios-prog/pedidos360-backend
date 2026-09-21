package com.pedidos360.bff.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OT")
public class OrdenTrabajo {

    @Id
    @Column(name = "OT_ID", length = 24)
    private String otId;

    @Column(name = "CLIENTE_ID", nullable = false, length = 20)
    private String clienteId;

    @Column(name = "PATENTE", nullable = false, length = 10)
    private String patente;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "TOTAL", precision = 12)
    private BigDecimal total;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public String getOtId() {
        return otId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getPatente() {
        return patente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}