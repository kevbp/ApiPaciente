package Clinica.ApiPaciente;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- importa esto
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pacientes")
public class PacienteUIController {

    private final Servicio servicio;

    public PacienteUIController(Servicio servicio) {
        this.servicio = servicio;
    }

    // BUSCAR
    @GetMapping("/buscar")

    public String buscar(@RequestParam("dato") String dato, Model model) {
        String q = dato == null ? "" : dato.trim().toLowerCase();

        // Traemos todos los pacientes del servicio
        List<Paciente> base = servicio.listar();

        // Filtramos según coincidencias
        List<Paciente> pacientes = base.stream()
                .filter(p -> {

                    boolean matchNom = p.getNom() != null && p.getNom().toLowerCase().contains(q);

                    boolean matchEstado = p.getEst() != null && p.getEst().toLowerCase().contains(q);

                    boolean matchDni = String.valueOf(p.getDni()).contains(q);

                    boolean matchId = false;
                    try {
                        Long id = Long.parseLong(q);
                        matchId = p.getId() != null && p.getId().equals(id);
                    } catch (NumberFormatException ignored) {
                    }

                    // Retorna si hay alguna coincidencia con los atributos
                    return matchNom || matchEstado || matchDni || matchId;
                })
                .toList();

        // Enviamos los resultados a la vista
        model.addAttribute("pacientes", pacientes);
        return "pacientes";
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", servicio.listar());
        return "pacientes";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("modo", "crear");
        return "paciente";
    }

    // CREAR
    @PostMapping
    public String crear(@Valid @ModelAttribute Paciente pac,
            BindingResult br,
            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "crear");
            return "paciente";
        }
        servicio.grabar(pac);
        return "redirect:/pacientes";
    }

    // EDITAR (form)
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", servicio.buscar(id));
        model.addAttribute("modo", "editar");
        return "paciente";
    }

    // ACTUALIZAR (único)
    @PostMapping(path = "/{id}", params = "_method=put")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute Paciente pac,
            BindingResult br,
            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "editar");
            return "paciente";
        }
        // opcional: asegurar que el id del objeto coincida
        if (pac.getId() == null || !pac.getId().equals(id)) {
            pac.setId(id);
        }
        servicio.actualizar(id, pac);
        return "redirect:/pacientes";
    }

    // ELIMINAR
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/pacientes";
    }
}
