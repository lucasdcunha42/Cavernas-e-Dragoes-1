package com.cavernasedragoes.domain.model;


import javax.persistence.*;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer dadoVida;

    @Column(nullable = false)
    private String habilidadePrimaria;

    @Column(nullable = false)
    private String proeficienciaResistencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDadoVida() {
        return dadoVida;
    }

    public void setDadoVida(Integer dadoVida) {
        this.dadoVida = dadoVida;
    }

    public String getHabilidadePrimaria() {
        return habilidadePrimaria;
    }

    public void setHabilidadePrimaria(String habilidadePrimaria) {
        this.habilidadePrimaria = habilidadePrimaria;
    }

    public String getProeficienciaResistencia() {
        return proeficienciaResistencia;
    }

    public void setProeficienciaResistencia(String proeficienciaResistencia) {
        this.proeficienciaResistencia = proeficienciaResistencia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Classe other = (Classe) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
