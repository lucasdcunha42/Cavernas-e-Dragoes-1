package com.cavernasedragoes.domain.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String primeiraProeficiencia;

    @Column(nullable = false)
    private String segundaProeficiencia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimeiraProeficiencia() {
        return primeiraProeficiencia;
    }

    public void setPrimeiraProeficiencia(String primeiraProeficiencia) {
        this.primeiraProeficiencia = primeiraProeficiencia;
    }

    public String getSegundaProeficiencia() {
        return segundaProeficiencia;
    }

    public void setSegundaProeficiencia(String segundaProeficiencia) {
        this.segundaProeficiencia = segundaProeficiencia;
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
        Raca other = (Raca) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
