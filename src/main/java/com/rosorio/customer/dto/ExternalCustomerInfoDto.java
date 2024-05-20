package com.rosorio.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ExternalCustomerInfoDto {
    @JsonProperty("fec_alta")
    private String fecAlta;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("codigo_zip")
    private String codigoZip;

    @JsonProperty("credit_card_num")
    private String creditCardNum;

    @JsonProperty("credit_card_ccv")
    private String creditCardCcv;

    @JsonProperty("cuenta_numero")
    private String cuentaNumero;

    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("geo_latitud")
    private double geoLatitud;

    @JsonProperty("geo_longitud")
    private double geoLongitud;

    @JsonProperty("color_favorito")
    private String colorFavorito;

    @JsonProperty("foto_dni")
    private String fotoDni;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("auto")
    private String auto;

    @JsonProperty("auto_modelo")
    private String autoModelo;

    @JsonProperty("auto_tipo")
    private String autoTipo;

    @JsonProperty("auto_color")
    private String autoColor;

    @JsonProperty("cantidad_compras_realizadas")
    private int cantidadComprasRealizadas;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("fec_birthday")
    private String fecBirthday;

    @JsonProperty("id")
    private String id;
}
