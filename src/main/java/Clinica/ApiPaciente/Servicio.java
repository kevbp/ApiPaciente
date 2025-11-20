/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clinica.ApiPaciente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio repo;

    public Paciente grabar(Paciente pac) {
        return repo.save(pac);
    }

    public Paciente buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Paciente> listar() {
        return repo.findAll();
    }

    public Paciente actualizar(Long id, Paciente pac) {
        return repo.findById(id).map(existing -> {
            existing.setNom(pac.getNom());
            existing.setDni(pac.getDni());
            existing.setPes(pac.getPes());
            existing.setTal(pac.getTal());
            existing.setEda(pac.getEda());
            existing.setEst(pac.getEst());
            return repo.save(existing);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);

    }

}
