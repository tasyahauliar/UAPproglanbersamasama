package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BikeRentalApp {

    private static final ArrayList<Bike> bikes = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize bike data
        bikes.add(new Bike("S-01", 20000, "tersedia"));
        bikes.add(new Bike("S-02", 20000, "tersedia"));
        bikes.add(new Bike("S-03", 20000, "tersedia"));
        bikes.add(new Bike("S-04", 20000, "tersedia"));

        SwingUtilities.invokeLater(MainMenu::new);
    }

    // API untuk menyewa sepeda
    public static boolean rentBike(String bikeId, String customerName, String phoneNumber) {
        if (bikeId == null || bikeId.isEmpty()) {
            System.out.println("ID sepeda tidak valid.");
            return false;
        }
        if (customerName == null || customerName.isEmpty() || phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("Nama dan nomor telepon harus diisi.");
            return false;
        }

        for (Bike bike : bikes) {
            if (bike.id.equals(bikeId) && bike.status.equals("tersedia")) {
                bike.status = "disewa";
                return true;
            }
        }
        return false; // Jika sepeda tidak tersedia
    }

    // API untuk mengembalikan sepeda
    public static boolean returnBike(String bikeId) {
        for (Bike bike : bikes) {
            if (bike.id.equals(bikeId) && bike.status.equals("disewa")) {
                bike.status = "tersedia";
                return true;
            }
        }
        return false;
    }

    // API untuk membatalkan sewa sepeda
    public static boolean cancelRental(String bikeId) {
        for (Bike bike : bikes) {
            if (bike.id.equals(bikeId) && bike.status.equals("disewa")) {
                bike.status = "tersedia";
                return true;
            }
        }
        return false;
    }

    // Main Menu Class
    static class MainMenu {
        JFrame frame;

        MainMenu() {
            frame = new JFrame("Penyewaan Sepeda");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 1, 10, 10)); // Satu kolom, tombol tersusun vertikal
            panel.setBackground(new Color(173, 216, 230)); // Soft blue background

            JButton daftarBtn = new JButton("Daftar Sepeda");
            JButton sewaBtn = new JButton("Sewa Sepeda");
            JButton kembalikanBtn = new JButton("Kembalikan Sepeda");
            JButton batalkanBtn = new JButton("Batalkan Sewa");
            JButton keluarBtn = new JButton("Keluar");

            daftarBtn.addActionListener(e -> new BikeList(frame));
            sewaBtn.addActionListener(e -> new RentBike(frame));
            kembalikanBtn.addActionListener(e -> new ReturnBike(frame));
            batalkanBtn.addActionListener(e -> new CancelBike(frame));
            keluarBtn.addActionListener(e -> System.exit(0));

            // Menambahkan tombol ke panel
            daftarBtn.setBackground(Color.WHITE);
            sewaBtn.setBackground(Color.WHITE);
            kembalikanBtn.setBackground(Color.WHITE);
            batalkanBtn.setBackground(Color.WHITE);
            keluarBtn.setBackground(Color.WHITE);

            panel.add(daftarBtn);
            panel.add(sewaBtn);
            panel.add(kembalikanBtn);
            panel.add(batalkanBtn);
            panel.add(keluarBtn);

            // Menambahkan panel ke frame
            frame.add(panel);
            frame.setVisible(true);
        }
    }

    // Bike Class
    static class Bike {
        String id;
        int price;
        String status;

        Bike(String id, int price, String status) {
            this.id = id;
            this.price = price;
            this.status = status;
        }

        @Override
        public String toString() {
            return id + "\t" + price + "/jam\t" + status;
        }
    }

    // Bike List Class
    static class BikeList {
        BikeList(JFrame parent) {
            try {
                JFrame frame = new JFrame("Daftar Sepeda");
                frame.setSize(400, 200);

                String[] columns = {"ID Sepeda", "Harga", "Keterangan"};
                String[][] data = new String[bikes.size()][3];

                for (int i = 0; i < bikes.size(); i++) {
                    Bike bike = bikes.get(i);
                    data[i][0] = bike.id;
                    data[i][1] = bike.price + "/jam";
                    data[i][2] = bike.status;
                }

                JTable table = new JTable(data, columns);
                JScrollPane scrollPane = new JScrollPane(table);

                frame.add(scrollPane);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Terjadi kesalahan saat menampilkan daftar sepeda.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Rent Bike Class
    static class RentBike {
        RentBike(JFrame parent) {
            try {
                JFrame frame = new JFrame("Sewa Sepeda");
                frame.setSize(400, 300);

                JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
                panel.setBackground(new Color(173, 216, 230)); // Soft blue background

                JLabel nameLabel = new JLabel("Nama:");
                JTextField nameField = new JTextField();
                JLabel phoneLabel = new JLabel("No Handphone:");
                JTextField phoneField = new JTextField();
                JLabel durationLabel = new JLabel("Durasi Sewa (jam):");
                JTextField durationField = new JTextField();
                JLabel bikeLabel = new JLabel("Sepeda yang disewa:");
                JComboBox<String> bikeComboBox = new JComboBox<>();
                for (Bike bike : bikes) {
                    if (bike.status.equals("tersedia")) {
                        bikeComboBox.addItem(bike.id);
                    }
                }
                JLabel totalLabel = new JLabel("Total:");
                JLabel totalAmount = new JLabel("0");

                JButton calculateBtn = new JButton("Hitung Total");
                calculateBtn.setBackground(Color.WHITE);
                calculateBtn.addActionListener(e -> {
                    try {
                        int duration = Integer.parseInt(durationField.getText());
                        if (duration <= 0) throw new NumberFormatException();
                        int total = duration * 20000;
                        totalAmount.setText(String.valueOf(total));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Durasi harus berupa angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                JButton finishBtn = new JButton("Selesai");
                finishBtn.setBackground(Color.WHITE);
                finishBtn.addActionListener(e -> {
                    try {
                        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                            throw new Exception("Nama dan nomor handphone harus diisi.");
                        }

                        String selectedBike = (String) bikeComboBox.getSelectedItem();
                        boolean success = rentBike(selectedBike, nameField.getText(), phoneField.getText());
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Terima kasih telah menyewa sepeda!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Sepeda tidak tersedia.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(phoneLabel);
                panel.add(phoneField);
                panel.add(durationLabel);
                panel.add(durationField);
                panel.add(bikeLabel);
                panel.add(bikeComboBox);
                panel.add(totalLabel);
                panel.add(totalAmount);
                panel.add(calculateBtn);
                panel.add(finishBtn);

                frame.add(panel);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Terjadi kesalahan saat membuka menu penyewaan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Return Bike Class
    static class ReturnBike {
        ReturnBike(JFrame parent) {
            try {
                JFrame frame = new JFrame("Kembalikan Sepeda");
                frame.setSize(300, 200);

                JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
                panel.setBackground(new Color(173, 216, 230)); // Soft blue background

                JLabel nameLabel = new JLabel("Nama:");
                JTextField nameField = new JTextField();
                JButton finishBtn = new JButton("Selesai");
                finishBtn.setBackground(Color.WHITE);

                finishBtn.addActionListener(e -> {
                    try {
                        if (nameField.getText().isEmpty()) {
                            throw new Exception("Nama harus diisi.");
                        }

                        boolean success = returnBike(nameField.getText());
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Terima kasih telah mengembalikan sepeda!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Tidak ada sepeda yang sedang disewa.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(finishBtn);

                frame.add(panel);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Terjadi kesalahan saat membuka menu pengembalian.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cancel Rental Class
    static class CancelBike {
        CancelBike(JFrame parent) {
            try {
                JFrame frame = new JFrame("Batalkan Sewa");
                frame.setSize(300, 200);

                JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
                panel.setBackground(new Color(173, 216, 230)); // Soft blue background

                JLabel nameLabel = new JLabel("Nama:");
                JTextField nameField = new JTextField();
                JButton finishBtn = new JButton("Batalkan");
                finishBtn.setBackground(Color.WHITE);

                finishBtn.addActionListener(e -> {
                    try {
                        if (nameField.getText().isEmpty()) {
                            throw new Exception("Nama harus diisi.");
                        }

                        boolean success = cancelRental(nameField.getText());
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Penyewaan sepeda dibatalkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Tidak ada penyewaan yang dibatalkan.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(finishBtn);

                frame.add(panel);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Terjadi kesalahan saat membuka menu pembatalan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}