package com.pedidos360.bff.controller;

import com.pedidos360.bff.entity.OrdenTrabajo;
import com.pedidos360.bff.repository.OrdenTrabajoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final OrdenTrabajoRepository ordenTrabajoRepository;

    public PedidoController(OrdenTrabajoRepository ordenTrabajoRepository) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenerPedidos(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("preferred_username");
        System.out.println("Pedidos consultados por: " + usuario);

        List<Map<String, Object>> pedidos = new ArrayList<>();
        for (OrdenTrabajo ot : ordenTrabajoRepository.findAll()) {
            pedidos.add(Map.of(
                "id", ot.getOtId(),
                "cliente", ot.getClienteId(),
                "monto", ot.getTotal() != null ? ot.getTotal() : java.math.BigDecimal.ZERO,
                "estado", "Registrada",
                "descripcion", ot.getDescripcion() != null ? ot.getDescripcion() : ""
            ));
        }

        return ResponseEntity.ok(pedidos);
    }
}