package com.app.jwt_spring_security.controllers.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse { // Clase para la respuesta independientemente de si es el registro o el login. Nos interesa que nos devuelva el token
    String token;
}
