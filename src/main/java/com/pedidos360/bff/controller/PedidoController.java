package com.pedidos360.bff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @GetMapping
    public ResponseEntity<?> obtenerPedidos(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(List.of(
            Map.of("id", 101, "cliente", usuario != null ? usuario : "Usuario Autenticado", "monto", 45000, "estado", "Procesado"),
            Map.of("id", 102, "cliente", "Cliente AWS", "monto", 120000, "estado", "Enviado")
        ));
    }
}
