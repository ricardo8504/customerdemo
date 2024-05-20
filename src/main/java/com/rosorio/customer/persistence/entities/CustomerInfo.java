package com.rosorio.customer.persistence.entities;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class CustomerInfo {
    private String id;
    private String user_id;
    private String fecAlta;
    private String userName;
    private String codigoZip;
    private String creditCardNum;
    private String creditCardCcv;
    private String cuentaNumero;
    private String direccion;
    private double geoLatitud;
    private double geoLongitud;
    private String colorFavorito;
    private String fotoDni;
    private String ip;
    private String auto;
    private String autoModelo;
    private String autoTipo;
    private String autoColor;
    private int cantidadComprasRealizadas;
    private String avatar;
    private String fecBirthday;
}

