/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clinica.ApiPaciente;

import org.springframework.data.jpa.repository.JpaRepository;


public interface Repositorio extends JpaRepository<Paciente, Long>{
    
    
}
