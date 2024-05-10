
package br.com.digio.api.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "codigo",
    "tipo_vinho",
    "preco",
    "safra",
    "ano_compra"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @JsonProperty("codigo")
    private Integer codigo;
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    @JsonProperty("preco")
    private BigDecimal preco;
    @JsonProperty("safra")
    private String safra;
    @JsonProperty("ano_compra")
    private Integer anoCompra;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("codigo")
    public Integer getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("tipo_vinho")
    public String getTipoVinho() {
        return tipoVinho;
    }

    @JsonProperty("tipo_vinho")
    public void setTipoVinho(String tipoVinho) {
        this.tipoVinho = tipoVinho;
    }

    @JsonProperty("preco")
    public BigDecimal getPreco() {
        return preco;
    }

    @JsonProperty("preco")
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @JsonProperty("safra")
    public String getSafra() {
        return safra;
    }

    @JsonProperty("safra")
    public void setSafra(String safra) {
        this.safra = safra;
    }

    @JsonProperty("ano_compra")
    public Integer getAnoCompra() {
        return anoCompra;
    }

    @JsonProperty("ano_compra")
    public void setAnoCompra(Integer anoCompra) {
        this.anoCompra = anoCompra;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
