import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Task_03 extends JFrame {
    private ArrayList<Contact> contacts;
    private JTextField nameField, phoneField, emailField;
    private JPanel contactPanel;
    private JButton addButton;

    public Task_03() {
        setTitle("Contact Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 300));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });
        inputPanel.add(addButton);

        JButton viewButton = new JButton("View Contacts");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewContacts();
            }
        });
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contactPanel);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); 
        loadContacts();
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            contacts.add(new Contact(name, phone, email));
            saveContacts();
            clearFields();
            viewContacts();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewContacts() {
        contactPanel.removeAll();
        for (Contact contact : contacts) {
            JPanel contactInfoPanel = new JPanel();
            contactInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            contactInfoPanel.add(new JLabel(contact.toString()));

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editContact(contact);
                }
            });

            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteContact(contact);
                }
            });

            contactInfoPanel.add(editButton);
            contactInfoPanel.add(deleteButton);
            contactPanel.add(contactInfoPanel);
        }
        revalidate();
        repaint();
    }

    private void editContact(Contact contact) {
        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());

        // Remove existing ActionListener from the "Add Contact" button
        for (ActionListener al : addButton.getActionListeners()) {
            addButton.removeActionListener(al);
        }

        // Add ActionListener to the "Add Contact" button to update the contact
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact(contact);
            }
        });
    }

    private void updateContact(Contact contact) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            saveContacts();
            clearFields();
            viewContacts(); 
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteContact(Contact contact) {
        contacts.remove(contact);
        saveContacts();
        viewContacts(); 
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.dat"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContacts() {
        File file = new File("contacts.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contacts = (ArrayList<Contact>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            contacts = new ArrayList<>(); 
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task_03().setVisible(true);
            }
        });
    }
}

class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}
