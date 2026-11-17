package kz.iitu.clinic.model;

public class Appointment {
    private Long id;
    private String patientName;
    private String phone;
    private String doctorName;

    public Appointment() {}

    public Appointment(Long id, String patientName, String phone, String doctorName) {
        this.id = id;
        this.patientName = patientName;
        this.phone = phone;
        this.doctorName = doctorName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
}
