package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClinicaGUI extends JFrame {
    private JComboBox<String> tableComboBox;
    private JTextArea resultTextArea;
    private JButton insertButton, updateButton, queryButton, viewButton, functionButton, triggerButton, procedureButton;

    public ClinicaGUI() {
        setTitle("Clínica Database Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para seleccionar la tabla
        JPanel topPanel = new JPanel();
        String[] tables = {"MEDICOS", "ESPECIALISTAS", "ASISTENTES", "PROVEEDORES", "MEDICAMENTOS", "RECETAS", "PACIENTES", "CITAS"};
        tableComboBox = new JComboBox<>(tables);
        topPanel.add(new JLabel("Selecciona la tabla:"));
        topPanel.add(tableComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Panel central para mostrar resultados
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel bottomPanel = new JPanel();
        insertButton = new JButton("Insertar");
        updateButton = new JButton("Actualizar");
        queryButton = new JButton("Consultar");
        viewButton = new JButton("Ver Vistas");
        functionButton = new JButton("Ejecutar Funciones");
        triggerButton = new JButton("Ejecutar Triggers");
        procedureButton = new JButton("Ejecutar Procedimientos");

        bottomPanel.add(insertButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(queryButton);
        bottomPanel.add(viewButton);
        bottomPanel.add(functionButton);
        bottomPanel.add(triggerButton);
        bottomPanel.add(procedureButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Agregar listeners a los botones
        insertButton.addActionListener(new InsertListener());
        updateButton.addActionListener(new UpdateListener());
        queryButton.addActionListener(new QueryListener());
        viewButton.addActionListener(new ViewListener());
        functionButton.addActionListener(new FunctionListener());
        triggerButton.addActionListener(new TriggerListener());
        procedureButton.addActionListener(new ProcedureListener());
    }

    private class InsertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String table = (String) tableComboBox.getSelectedItem();
            switch (table) {
                case "MEDICOS":
                    insertMedico();
                    break;
                case "ESPECIALISTAS":
                    insertEspecialista();
                    break;
                case "ASISTENTES":
                    insertAsistente();
                    break;
                case "PROVEEDORES":
                    insertProveedor();
                    break;
                case "MEDICAMENTOS":
                    insertMedicamento();
                    break;
                case "RECETAS":
                    insertReceta();
                    break;
                case "PACIENTES":
                    insertPaciente();
                    break;
                case "CITAS":
                    insertCita();
                    break;
            }
        }
    }

    private void insertMedico() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField areaField = new JTextField(10);
        JTextField idEspecialistaField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(new JLabel("ID Especialista:"));
        panel.add(idEspecialistaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Médico", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || telefonoField.getText().isEmpty() || areaField.getText().isEmpty() || idEspecialistaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO MEDICOS (id_medico, nombre, primer_ap, segundo_ap, telefono, area, id_especialista) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, primerApField.getText());
                stmt.setString(4, segundoApField.getText());
                stmt.setString(5, telefonoField.getText());
                stmt.setString(6, areaField.getText());
                stmt.setString(7, idEspecialistaField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Médico insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el médico: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertEspecialista() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField areaField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Especialista", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || areaField.getText().isEmpty() || telefonoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO ESPECIALISTAS (id_especialista, nombre, primer_ap, segundo_ap, area, telefono) VALUES (?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, primerApField.getText());
                stmt.setString(4, segundoApField.getText());
                stmt.setString(5, areaField.getText());
                stmt.setString(6, telefonoField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Especialista insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el especialista: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertAsistente() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Asistente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO ASISTENTES (id_asistente, nombre, primer_ap, segundo_ap) VALUES (?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, primerApField.getText());
                stmt.setString(4, segundoApField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Asistente insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el asistente: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertProveedor() {
        JTextField idField = new JTextField(10);
        JTextField noProveedorField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField direccionField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField rfcField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("No. Proveedor:"));
        panel.add(noProveedorField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccionField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("RFC:"));
        panel.add(rfcField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Proveedor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || noProveedorField.getText().isEmpty() || nombreField.getText().isEmpty() || direccionField.getText().isEmpty() || telefonoField.getText().isEmpty() || rfcField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO PROVEEDORES (id_proveedor, no_proveedor, nombre, direccion, telefono, rfc) VALUES (?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, noProveedorField.getText());
                stmt.setString(3, nombreField.getText());
                stmt.setString(4, direccionField.getText());
                stmt.setString(5, telefonoField.getText());
                stmt.setString(6, rfcField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proveedor insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el proveedor: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertMedicamento() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField viaAdminField = new JTextField(10);
        JTextField idProveedorField = new JTextField(10);
        JTextField descripcionField = new JTextField(10);
        JTextField precioField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Vía de Administración:"));
        panel.add(viaAdminField);
        panel.add(new JLabel("ID Proveedor:"));
        panel.add(idProveedorField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Medicamento", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || viaAdminField.getText().isEmpty() || idProveedorField.getText().isEmpty() || descripcionField.getText().isEmpty() || precioField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO MEDICAMENTOS (id_medicamento, nombre, via_admin, id_proveedor, descripcion, precio) VALUES (?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, viaAdminField.getText());
                stmt.setString(4, idProveedorField.getText());
                stmt.setString(5, descripcionField.getText());
                stmt.setString(6, precioField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medicamento insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el medicamento: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertReceta() {
        JTextField idField = new JTextField(10);
        JTextField indicacionesField = new JTextField(10);
        JTextField dosisField = new JTextField(10);
        JTextField idMedicamentoField = new JTextField(10);
        JTextField idCitaField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Indicaciones:"));
        panel.add(indicacionesField);
        panel.add(new JLabel("Dosis:"));
        panel.add(dosisField);
        panel.add(new JLabel("ID Medicamento:"));
        panel.add(idMedicamentoField);
        panel.add(new JLabel("ID Cita:"));
        panel.add(idCitaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Receta", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || indicacionesField.getText().isEmpty() || dosisField.getText().isEmpty() || idMedicamentoField.getText().isEmpty() || idCitaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO RECETAS (id_receta, indicaciones, dosis, id_medicamento, id_cita) VALUES (?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, indicacionesField.getText());
                stmt.setString(3, dosisField.getText());
                stmt.setString(4, idMedicamentoField.getText());
                stmt.setString(5, idCitaField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Receta insertada correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar la receta: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertPaciente() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField correoField = new JTextField(10);
        JTextField direccionField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || telefonoField.getText().isEmpty() || correoField.getText().isEmpty() || direccionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO PACIENTES (id_paciente, nombre, primer_ap, segundo_ap, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, idField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, primerApField.getText());
                stmt.setString(4, segundoApField.getText());
                stmt.setString(5, telefonoField.getText());
                stmt.setString(6, correoField.getText());
                stmt.setString(7, direccionField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Paciente insertado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar el paciente: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void insertCita() {
        JTextField idField = new JTextField(10);
        JTextField fechaField = new JTextField(10);
        JTextField horaField = new JTextField(10);
        JTextField consultorioField = new JTextField(10);
        JTextField idMedicoField = new JTextField(10);
        JTextField idPacienteField = new JTextField(10);
        JTextField idAsistenteField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Fecha:"));
        panel.add(fechaField);
        panel.add(new JLabel("Hora:"));
        panel.add(horaField);
        panel.add(new JLabel("Consultorio:"));
        panel.add(consultorioField);
        panel.add(new JLabel("ID Médico:"));
        panel.add(idMedicoField);
        panel.add(new JLabel("ID Paciente:"));
        panel.add(idPacienteField);
        panel.add(new JLabel("ID Asistente:"));
        panel.add(idAsistenteField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Insertar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || fechaField.getText().isEmpty() || horaField.getText().isEmpty() || consultorioField.getText().isEmpty() || idMedicoField.getText().isEmpty() || idPacienteField.getText().isEmpty() || idAsistenteField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            try {
                Date fecha = dateFormat.parse(fechaField.getText());
                Date hora = timeFormat.parse(horaField.getText());

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO CITAS (id_cita, fecha, hora, consultorio, id_medico, id_paciente, id_asistente) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    stmt.setString(1, idField.getText());
                    stmt.setDate(2, new java.sql.Date(fecha.getTime()));
                    stmt.setTime(3, new java.sql.Time(hora.getTime()));
                    stmt.setString(4, consultorioField.getText());
                    stmt.setString(5, idMedicoField.getText());
                    stmt.setString(6, idPacienteField.getText());
                    stmt.setString(7, idAsistenteField.getText());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cita insertada correctamente");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al insertar la cita: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de fecha/hora incorrecto");
                e.printStackTrace();
            }
        }
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String table = (String) tableComboBox.getSelectedItem();
            switch (table) {
                case "MEDICOS":
                    updateMedico();
                    break;
                case "ESPECIALISTAS":
                    updateEspecialista();
                    break;
                case "ASISTENTES":
                    updateAsistente();
                    break;
                case "PROVEEDORES":
                    updateProveedor();
                    break;
                case "MEDICAMENTOS":
                    updateMedicamento();
                    break;
                case "RECETAS":
                    updateReceta();
                    break;
                case "PACIENTES":
                    updatePaciente();
                    break;
                case "CITAS":
                    updateCita();
                    break;
            }
        }
    }

    private void updateMedico() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField areaField = new JTextField(10);
        JTextField idEspecialistaField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(new JLabel("ID Especialista:"));
        panel.add(idEspecialistaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Médico", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || telefonoField.getText().isEmpty() || areaField.getText().isEmpty() || idEspecialistaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE MEDICOS SET nombre = ?, primer_ap = ?, segundo_ap = ?, telefono = ?, area = ?, id_especialista = ? WHERE id_medico = ?")) {

                stmt.setString(1, nombreField.getText());
                stmt.setString(2, primerApField.getText());
                stmt.setString(3, segundoApField.getText());
                stmt.setString(4, telefonoField.getText());
                stmt.setString(5, areaField.getText());
                stmt.setString(6, idEspecialistaField.getText());
                stmt.setString(7, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Médico actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el médico: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateEspecialista() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField areaField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Especialista", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || areaField.getText().isEmpty() || telefonoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE ESPECIALISTAS SET nombre = ?, primer_ap = ?, segundo_ap = ?, area = ?, telefono = ? WHERE id_especialista = ?")) {

                stmt.setString(1, nombreField.getText());
                stmt.setString(2, primerApField.getText());
                stmt.setString(3, segundoApField.getText());
                stmt.setString(4, areaField.getText());
                stmt.setString(5, telefonoField.getText());
                stmt.setString(6, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Especialista actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el especialista: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateAsistente() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Asistente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE ASISTENTES SET nombre = ?, primer_ap = ?, segundo_ap = ? WHERE id_asistente = ?")) {

                stmt.setString(1, nombreField.getText());
                stmt.setString(2, primerApField.getText());
                stmt.setString(3, segundoApField.getText());
                stmt.setString(4, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Asistente actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el asistente: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateProveedor() {
        JTextField idField = new JTextField(10);
        JTextField noProveedorField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField direccionField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField rfcField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("No. Proveedor:"));
        panel.add(noProveedorField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccionField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("RFC:"));
        panel.add(rfcField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Proveedor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || noProveedorField.getText().isEmpty() || nombreField.getText().isEmpty() || direccionField.getText().isEmpty() || telefonoField.getText().isEmpty() || rfcField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE PROVEEDORES SET no_proveedor = ?, nombre = ?, direccion = ?, telefono = ?, rfc = ? WHERE id_proveedor = ?")) {

                stmt.setString(1, noProveedorField.getText());
                stmt.setString(2, nombreField.getText());
                stmt.setString(3, direccionField.getText());
                stmt.setString(4, telefonoField.getText());
                stmt.setString(5, rfcField.getText());
                stmt.setString(6, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateMedicamento() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField viaAdminField = new JTextField(10);
        JTextField idProveedorField = new JTextField(10);
        JTextField descripcionField = new JTextField(10);
        JTextField precioField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Vía de Administración:"));
        panel.add(viaAdminField);
        panel.add(new JLabel("ID Proveedor:"));
        panel.add(idProveedorField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Medicamento", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || viaAdminField.getText().isEmpty() || idProveedorField.getText().isEmpty() || descripcionField.getText().isEmpty() || precioField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE MEDICAMENTOS SET nombre = ?, via_admin = ?, id_proveedor = ?, descripcion = ?, precio = ? WHERE id_medicamento = ?")) {

                stmt.setString(1, nombreField.getText());
                stmt.setString(2, viaAdminField.getText());
                stmt.setString(3, idProveedorField.getText());
                stmt.setString(4, descripcionField.getText());
                stmt.setString(5, precioField.getText());
                stmt.setString(6, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medicamento actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el medicamento: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateReceta() {
        JTextField idField = new JTextField(10);
        JTextField indicacionesField = new JTextField(10);
        JTextField dosisField = new JTextField(10);
        JTextField idMedicamentoField = new JTextField(10);
        JTextField idCitaField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Indicaciones:"));
        panel.add(indicacionesField);
        panel.add(new JLabel("Dosis:"));
        panel.add(dosisField);
        panel.add(new JLabel("ID Medicamento:"));
        panel.add(idMedicamentoField);
        panel.add(new JLabel("ID Cita:"));
        panel.add(idCitaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Receta", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || indicacionesField.getText().isEmpty() || dosisField.getText().isEmpty() || idMedicamentoField.getText().isEmpty() || idCitaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE RECETAS SET indicaciones = ?, dosis = ?, id_medicamento = ?, id_cita = ? WHERE id_receta = ?")) {

                stmt.setString(1, indicacionesField.getText());
                stmt.setString(2, dosisField.getText());
                stmt.setString(3, idMedicamentoField.getText());
                stmt.setString(4, idCitaField.getText());
                stmt.setString(5, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Receta actualizada correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar la receta: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updatePaciente() {
        JTextField idField = new JTextField(10);
        JTextField nombreField = new JTextField(10);
        JTextField primerApField = new JTextField(10);
        JTextField segundoApField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);
        JTextField correoField = new JTextField(10);
        JTextField direccionField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(primerApField);
        panel.add(new JLabel("Segundo Apellido:"));
        panel.add(segundoApField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nombreField.getText().isEmpty() || primerApField.getText().isEmpty() || segundoApField.getText().isEmpty() || telefonoField.getText().isEmpty() || correoField.getText().isEmpty() || direccionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE PACIENTES SET nombre = ?, primer_ap = ?, segundo_ap = ?, telefono = ?, correo = ?, direccion = ? WHERE id_paciente = ?")) {

                stmt.setString(1, nombreField.getText());
                stmt.setString(2, primerApField.getText());
                stmt.setString(3, segundoApField.getText());
                stmt.setString(4, telefonoField.getText());
                stmt.setString(5, correoField.getText());
                stmt.setString(6, direccionField.getText());
                stmt.setString(7, idField.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Paciente actualizado correctamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el paciente: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateCita() {
        JTextField idField = new JTextField(10);
        JTextField fechaField = new JTextField(10);
        JTextField horaField = new JTextField(10);
        JTextField consultorioField = new JTextField(10);
        JTextField idMedicoField = new JTextField(10);
        JTextField idPacienteField = new JTextField(10);
        JTextField idAsistenteField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Fecha:"));
        panel.add(fechaField);
        panel.add(new JLabel("Hora:"));
        panel.add(horaField);
        panel.add(new JLabel("Consultorio:"));
        panel.add(consultorioField);
        panel.add(new JLabel("ID Médico:"));
        panel.add(idMedicoField);
        panel.add(new JLabel("ID Paciente:"));
        panel.add(idPacienteField);
        panel.add(new JLabel("ID Asistente:"));
        panel.add(idAsistenteField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || fechaField.getText().isEmpty() || horaField.getText().isEmpty() || consultorioField.getText().isEmpty() || idMedicoField.getText().isEmpty() || idPacienteField.getText().isEmpty() || idAsistenteField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            try {
                Date fecha = dateFormat.parse(fechaField.getText());
                Date hora = timeFormat.parse(horaField.getText());

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("UPDATE CITAS SET fecha = ?, hora = ?, consultorio = ?, id_medico = ?, id_paciente = ?, id_asistente = ? WHERE id_cita = ?")) {

                    stmt.setDate(1, new java.sql.Date(fecha.getTime()));
                    stmt.setTime(2, new java.sql.Time(hora.getTime()));
                    stmt.setString(3, consultorioField.getText());
                    stmt.setString(4, idMedicoField.getText());
                    stmt.setString(5, idPacienteField.getText());
                    stmt.setString(6, idAsistenteField.getText());
                    stmt.setString(7, idField.getText());

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cita actualizada correctamente");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la cita: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de fecha/hora incorrecto");
                e.printStackTrace();
            }
        }
    }

    private class QueryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String table = (String) tableComboBox.getSelectedItem();
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table);
                 ResultSet rs = stmt.executeQuery()) {

                resultTextArea.setText("");
                while (rs.next()) {
                    resultTextArea.append(rs.getString(1) + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al consultar la tabla: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private class ViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] views = {"VISTA_RECETAS_MEDICAMENTOS", "VISTA_PROVEEDORES_MEDICAMENTOS"};
            JComboBox<String> viewComboBox = new JComboBox<>(views);
            int result = JOptionPane.showConfirmDialog(null, viewComboBox, "Selecciona la vista", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedView = (String) viewComboBox.getSelectedItem();
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + selectedView);
                     ResultSet rs = stmt.executeQuery()) {

                    resultTextArea.setText("");
                    while (rs.next()) {
                        resultTextArea.append(rs.getString(1) + "\n");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al consultar la vista: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private class FunctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] functions = {"FN_Contar_Recetas_Medicamento", "FN_Validar_RFC"};
            JComboBox<String> functionComboBox = new JComboBox<>(functions);
            int result = JOptionPane.showConfirmDialog(null, functionComboBox, "Selecciona la función", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String selectedFunction = (String) functionComboBox.getSelectedItem();

                if (selectedFunction.equals("FN_Contar_Recetas_Medicamento")) {
                    JTextField idMedicamentoField = new JTextField(10);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("ID Medicamento:"));
                    panel.add(idMedicamentoField);

                    int inputResult = JOptionPane.showConfirmDialog(null, panel, "Ingresa el ID del medicamento", JOptionPane.OK_CANCEL_OPTION);
                    if (inputResult == JOptionPane.OK_OPTION) {
                        try (Connection conn = DatabaseConnection.getConnection();
                             CallableStatement stmt = conn.prepareCall("{ ? = call FN_Contar_Recetas_Medicamento(?) }")) {

                            stmt.registerOutParameter(1, Types.INTEGER);
                            stmt.setString(2, idMedicamentoField.getText().trim());
                            stmt.execute();

                            int count = stmt.getInt(1);
                            JOptionPane.showMessageDialog(null, "Total de recetas para el medicamento: " + count);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al ejecutar la función: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                } else if (selectedFunction.equals("FN_Validar_RFC")) {
                    JTextField rfcField = new JTextField(10);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("RFC:"));
                    panel.add(rfcField);

                    int inputResult = JOptionPane.showConfirmDialog(null, panel, "Ingresa el RFC", JOptionPane.OK_CANCEL_OPTION);
                    if (inputResult == JOptionPane.OK_OPTION) {
                        try (Connection conn = DatabaseConnection.getConnection();
                             CallableStatement stmt = conn.prepareCall("{ ? = call FN_Validar_RFC(?) }")) {

                            stmt.registerOutParameter(1, Types.NUMERIC);
                            stmt.setString(2, rfcField.getText().trim());
                            stmt.execute();

                            int isValidNumeric = stmt.getInt(1);
                            boolean isValid = (isValidNumeric == 1);

                            JOptionPane.showMessageDialog(null, "RFC válido: " + isValid);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al ejecutar la función: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private class TriggerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Los triggers se ejecutan automáticamente al realizar operaciones de inserción o actualización en las tablas correspondientes.");
        }
    }

    private class ProcedureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] procedures = {"SP_Generar_Receta", "ProgramarCita"};
            JComboBox<String> procedureComboBox = new JComboBox<>(procedures);
            int result = JOptionPane.showConfirmDialog(null, procedureComboBox, "Selecciona el procedimiento", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedProcedure = (String) procedureComboBox.getSelectedItem();
                if (selectedProcedure.equals("SP_Generar_Receta")) {
                    JTextField idCitaField = new JTextField(10);
                    JTextField idMedicamentoField = new JTextField(10);
                    JTextField indicacionesField = new JTextField(10);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("ID Cita:"));
                    panel.add(idCitaField);
                    panel.add(new JLabel("ID Medicamento:"));
                    panel.add(idMedicamentoField);
                    panel.add(new JLabel("Indicaciones:"));
                    panel.add(indicacionesField);
                    int inputResult = JOptionPane.showConfirmDialog(null, panel, "Ingresa los datos para generar la receta", JOptionPane.OK_CANCEL_OPTION);
                    if (inputResult == JOptionPane.OK_OPTION) {
                        try (Connection conn = DatabaseConnection.getConnection();
                             CallableStatement stmt = conn.prepareCall("{ call SP_Generar_Receta(?, ?, ?) }")) {

                            stmt.setString(1, idCitaField.getText());
                            stmt.setString(2, idMedicamentoField.getText());
                            stmt.setString(3, indicacionesField.getText());
                            stmt.execute();
                            JOptionPane.showMessageDialog(null, "Receta generada correctamente");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al ejecutar el procedimiento: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                } else if (selectedProcedure.equals("ProgramarCita")) {
                    JTextField fechaField = new JTextField(10);
                    JTextField horaField = new JTextField(10);
                    JTextField consultorioField = new JTextField(10);
                    JTextField idMedicoField = new JTextField(10);
                    JTextField idPacienteField = new JTextField(10);
                    JTextField idAsistenteField = new JTextField(10);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Fecha:"));
                    panel.add(fechaField);
                    panel.add(new JLabel("Hora:"));
                    panel.add(horaField);
                    panel.add(new JLabel("Consultorio:"));
                    panel.add(consultorioField);
                    panel.add(new JLabel("ID Médico:"));
                    panel.add(idMedicoField);
                    panel.add(new JLabel("ID Paciente:"));
                    panel.add(idPacienteField);
                    panel.add(new JLabel("ID Asistente:"));
                    panel.add(idAsistenteField);
                    int inputResult = JOptionPane.showConfirmDialog(null, panel, "Ingresa los datos para programar la cita", JOptionPane.OK_CANCEL_OPTION);
                    if (inputResult == JOptionPane.OK_OPTION) {
                        try (Connection conn = DatabaseConnection.getConnection();
                             CallableStatement stmt = conn.prepareCall("{ call ProgramarCita(?, ?, ?, ?, ?, ?) }")) {

                            stmt.setString(1, fechaField.getText());
                            stmt.setString(2, horaField.getText());
                            stmt.setInt(3, Integer.parseInt(consultorioField.getText()));
                            stmt.setString(4, idMedicoField.getText());
                            stmt.setString(5, idPacienteField.getText());
                            stmt.setString(6, idAsistenteField.getText());
                            stmt.execute();
                            JOptionPane.showMessageDialog(null, "Cita programada correctamente");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al ejecutar el procedimiento: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClinicaGUI gui = new ClinicaGUI();
            gui.setVisible(true);
        });
    }
}
