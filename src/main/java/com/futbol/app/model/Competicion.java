package com.futbol.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "competiciones")
public class Competicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int montoPremio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getMontoPremio() { return montoPremio; }
    public void setMontoPremio(int montoPremio) { this.montoPremio = montoPremio; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}
