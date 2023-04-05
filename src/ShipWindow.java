//Author: Ngoc Bui
//Date: 2/24/2023

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class ShipWindow extends JFrame {
    private int win_wid = 650;
    private int win_hei = 240;

    ArrayList<Ship> flotilla;
    JComboBox selectShipBox;
    JTextField name_field, country_field, year_field, length_field, draft_field, beam_field;

    private int gridRows = 3;
    private int gridCols = 4;

    public ShipWindow() {
        this.setTitle("Shipping new" + "      ".repeat(10) + "Ngoc Bui");
        this.setSize(win_wid, win_hei);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeShips("Tia_RosasFleet.csv");
        initSelectPanel();
        initButtonPan();
        initInfoPanel();

        this.setVisible(true);
    }

    private void initializeShips(String fileName) {
        File inFile = new File(fileName);
        flotilla = new ArrayList<>();

        try {
            Scanner inScan = new Scanner(inFile).useDelimiter("[\n,]");

            while (inScan.hasNext()) {
                String name = inScan.next();
                String nation = inScan.next();
                int year = inScan.nextInt();
                int length = inScan.nextInt();
                int draft = inScan.nextInt();
                int beam = inScan.nextInt();

                flotilla.add(new Ship(name, nation, year, length, draft, beam));

            }
            inScan.close();

        } catch (IOException ioe) {
            String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                    "Trouble opening file: " + fileName +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!";

            JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                    "File missing", 2);
            System.exit(0);
        }
    }

    private void initSelectPanel() {
        int font_size = 26;
        Font bigFont = new Font("Arial", 1, font_size);
        JLabel label = new JLabel("Select a ship");
        label.setFont(bigFont);

        selectShipBox = new JComboBox();
        selectShipBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                changeItem(ie);
            }
        });

        String[] tempList = new String[flotilla.size()];
        for (int dex = 0; dex < flotilla.size(); dex++) {
            tempList[dex] = flotilla.get(dex).getName();
        }
        selectShipBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempList));

        ImageIcon icon = new ImageIcon("Yacht_big.png");
        int scale_x = 164, scale_y = 82;

        JLabel iconLabel = new JLabel() {
            public void paintComponent(Graphics g) {
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, scale_x, scale_y, this);

            }
        };

        iconLabel.setPreferredSize(new Dimension(scale_x, scale_y));
        iconLabel.setIcon(icon);

        JPanel north_pan = new JPanel();
        north_pan.add(iconLabel);
        north_pan.add(label);
        north_pan.add(selectShipBox);
        this.add(north_pan, BorderLayout.NORTH);

    }
    private void changeItem(ItemEvent ie){
        if (ie.getStateChange() == ItemEvent.SELECTED) {
            String name = (String) ie.getItem();

            Ship ship_selected;
            for (int dex = 0; dex < flotilla.size(); dex++) {
                if (flotilla.get(dex).getName().equals(name)) {
                    ship_selected = flotilla.get(dex);
                    name_field.setText(ship_selected.getName());
                    country_field.setText(ship_selected.getNation());
                    year_field.setText(Integer.toString(ship_selected.getYearBuilt()));
                    length_field.setText(Integer.toString(ship_selected.getLength()));
                    draft_field.setText(Integer.toString(ship_selected.getDraft()));
                    beam_field.setText(Integer.toString(ship_selected.getBeam()));
                }
            }
        }
    }

    private void initInfoPanel() {
        JPanel center_pan = new JPanel(new GridLayout(gridRows, gridCols));

        JLabel name_label = new JLabel("Ship Name: ");
        JLabel country_label = new JLabel("Country of Registration: ");
        JLabel year_label = new JLabel("Year of Construction: ");
        JLabel length_label = new JLabel("Length at waterline: ");
        JLabel draft_label = new JLabel("Draft at waterline: ");
        JLabel beam_label = new JLabel("Beam at waterline: ");

        name_field = new JTextField();
        country_field = new JTextField();
        year_field = new JTextField();
        length_field = new JTextField();
        draft_field = new JTextField();
        beam_field = new JTextField();


        center_pan.add(name_label);
        center_pan.add(name_field);
        center_pan.add(length_label);
        center_pan.add(length_field);
        center_pan.add(country_label);
        center_pan.add(country_field);
        center_pan.add(draft_label);
        center_pan.add(draft_field);
        center_pan.add(year_label);
        center_pan.add(year_field);
        center_pan.add(beam_label);
        center_pan.add(beam_field);

        int font_size = 12;
        Font medFont = new Font("Arial", 1, font_size);

        name_label.setFont(medFont);
        country_label.setFont(medFont);
        year_label.setFont(medFont);
        length_label.setFont(medFont);
        draft_label.setFont(medFont);
        beam_label.setFont(medFont);

        name_field.setFont(medFont);
        country_field.setFont(medFont);
        year_field.setFont(medFont);
        length_field.setFont(medFont);
        draft_field.setFont(medFont);
        beam_field.setFont(medFont);

        name_label.setHorizontalAlignment(JLabel.RIGHT);
        country_label.setHorizontalAlignment(JLabel.RIGHT);
        year_label.setHorizontalAlignment(JLabel.RIGHT);
        length_label.setHorizontalAlignment(JLabel.RIGHT);
        draft_label.setHorizontalAlignment(JLabel.RIGHT);
        beam_label.setHorizontalAlignment(JLabel.RIGHT);

        name_field.setHorizontalAlignment(SwingConstants.CENTER);
        country_field.setHorizontalAlignment(SwingConstants.CENTER);
        year_field.setHorizontalAlignment(SwingConstants.CENTER);
        length_field.setHorizontalAlignment(SwingConstants.CENTER);
        draft_field.setHorizontalAlignment(SwingConstants.CENTER);
        beam_field.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(center_pan, BorderLayout.CENTER);

    }

    private void initButtonPan() {
        JButton clear_button = new JButton("Clear");
        JButton quit_button = new JButton("Quit");
        JButton add_button = new JButton("Add");

        JPanel south_pan = new JPanel();
        south_pan.add(clear_button);
        south_pan.add(quit_button);
        south_pan.add(add_button);

        clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clearText();
            }
        });

        quit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileName = new File("Tia_RosasFleet.csv");
                addShipToFile(fileName);
            }
        });

        this.add(south_pan, BorderLayout.SOUTH);

    }
    private void clearText(){
        name_field.setText("");
        country_field.setText("");
        year_field.setText("");
        length_field.setText("");
        draft_field.setText("");
        beam_field.setText("");
    }

    private void addShipToFile(File fileName) {
        String new_ship;

        int invalid_ship = 0;
        for(int dex = 0; dex < flotilla.size(); dex++) {
            if (name_field.getText().equals(flotilla.get(dex).getName())||name_field.getText().equals("")) {
                invalid_ship = 1;
            }
        }
        if(invalid_ship == 0){
            new_ship = "\n" + name_field.getText() + "," + country_field.getText() +
                    "," + year_field.getText() + "," + length_field.getText() +
                    "," + draft_field.getText() + "," + beam_field.getText();

            try {
                FileWriter write_to_file = new FileWriter(fileName, true);
                BufferedWriter writing = new BufferedWriter(write_to_file);
                writing.write(new_ship);

                Ship update_ship = new Ship(name_field.getText(), country_field.getText(),
                        Integer.parseInt(year_field.getText()), Integer.parseInt(length_field.getText()),
                        Integer.parseInt(draft_field.getText()), Integer.parseInt(beam_field.getText()));
                flotilla.add(update_ship);
                selectShipBox.addItem(name_field.getText());
                writing.close();

            } catch (IOException ioe) {
                String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                        "Trouble writing file: " + fileName +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!";

                JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                        "File missing", 2);
                System.exit(0);
            }
        }
    }

    public static void main (String[]args){
        ShipWindow aw = new ShipWindow();
    }
}

