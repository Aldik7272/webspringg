package kz.iitu.clinic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kz.iitu.clinic.model.Appointment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    private final File jsonFile = new File("appointments.json");
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private List<Appointment> appointments = new ArrayList<>();
    private long nextId = 1;

    public ClinicService() {
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            if (jsonFile.exists() && jsonFile.length() > 0) {
                appointments = mapper.readValue(jsonFile, new TypeReference<List<Appointment>>() {});
                nextId = appointments.stream()
                        .mapToLong(a -> a.getId() != null ? a.getId() : 0)
                        .max()
                        .orElse(0) + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            mapper.writeValue(jsonFile, appointments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAll(String search) {
        if (search == null || search.isBlank()) {
            return appointments;
        }
        String lower = search.toLowerCase();
        return appointments.stream()
                .filter(a -> (a.getPatientName() != null && a.getPatientName().toLowerCase().contains(lower)) ||
                             (a.getDoctorName() != null && a.getDoctorName().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }

    public Optional<Appointment> getById(Long id) {
        return appointments.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Appointment create(Appointment appointment) {
        appointment.setId(nextId++);
        appointments.add(appointment);
        saveToFile();
        return appointment;
    }

    public Optional<Appointment> update(Long id, Appointment newDetails) {
        return getById(id).map(existing -> {
            existing.setPatientName(newDetails.getPatientName());
            existing.setPhone(newDetails.getPhone());
            existing.setDoctorName(newDetails.getDoctorName());
            saveToFile();
            return existing;
        });
    }

    public boolean delete(Long id) {
        boolean removed = appointments.removeIf(a -> a.getId().equals(id));
        if (removed) {
            saveToFile();
        }
        return removed;
    }
}
