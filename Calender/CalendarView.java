import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class CalendarView extends JFrame {
    private JPanel monthPanel;
    private JLabel monthLabel;
    private JPanel dayPanel;
    private Map<LocalDate, String> dayNotesMap; // To store notes for each day
    private final String NOTES_FILE = "notes.txt"; // File to store notes

    public CalendarView() {
        // Set up frame
        setTitle("Monthly & Weekly Calendar View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the map to store notes for each day
        dayNotesMap = new HashMap<>();

        // Load notes from file
        loadNotesFromFile();

        // Create month label
        monthLabel = new JLabel();
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        monthLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Create month panel for days of the week
        monthPanel = new JPanel();
        monthPanel.setLayout(new BorderLayout());

        // Panel to hold day cells
        dayPanel = new JPanel();
        dayPanel.setLayout(new GridLayout(0, 7)); // 7 columns for 7 days a week

        // Add day names header (Sun, Mon, etc.)
        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            headerPanel.add(new JLabel(dayName, SwingConstants.CENTER));
        }
        monthPanel.add(headerPanel, BorderLayout.NORTH);

        // Add dayPanel to monthPanel
        monthPanel.add(dayPanel, BorderLayout.CENTER);

        // Add components to frame
        add(monthLabel, BorderLayout.NORTH);
        add(monthPanel, BorderLayout.CENTER);

        // Load the current month view
        loadMonthView(LocalDate.now());

        setVisible(true);
    }

    // Method to load the view of a given month
    private void loadMonthView(LocalDate date) {
        dayPanel.removeAll(); // Clear the old days

        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonthValue());
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate today = LocalDate.now(); // Get today's date

        // Update month label
        monthLabel.setText(date.getMonth() + " " + date.getYear());

        // Determine the start day and number of days in the month
        int daysInMonth = yearMonth.lengthOfMonth();
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // Monday=1, Sunday=7

        // Add empty cells for days before the start of the month
        for (int i = 1; i < startDayOfWeek; i++) {
            dayPanel.add(new JLabel(""));
        }

        // Add day buttons
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonthValue(), day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setOpaque(true);
            dayButton.setBackground(Color.LIGHT_GRAY);

            // Highlight today in gray
            if (currentDate.equals(today)) {
                dayButton.setBackground(Color.GRAY);
                dayButton.setForeground(Color.WHITE);
            }

            // Add action listener for opening a new window for notes
            dayButton.addActionListener(e -> openDayNotesWindow(currentDate));

            dayPanel.add(dayButton);
        }

        // Refresh the panel
        dayPanel.revalidate();
        dayPanel.repaint();
    }

    // Method to open a new window for adding/viewing notes for the selected day
    private void openDayNotesWindow(LocalDate date) {
        JFrame notesFrame = new JFrame("Notes for " + date);
        notesFrame.setSize(300, 200);
        notesFrame.setLayout(new BorderLayout());

        // Text area for adding notes
        JTextArea notesArea = new JTextArea();
        String existingNotes = dayNotesMap.getOrDefault(date, "");
        notesArea.setText(existingNotes); // Load any existing notes for the day

        // Save button to save notes for the selected day
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            dayNotesMap.put(date, notesArea.getText());
            saveNotesToFile(); // Save notes to the file
            notesFrame.dispose(); // Close the window after saving
        });

        // Add components to the frame
        notesFrame.add(new JLabel("Add notes for " + date), BorderLayout.NORTH);
        notesFrame.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        notesFrame.add(saveButton, BorderLayout.SOUTH);

        // Make the frame visible
        notesFrame.setVisible(true);
    }

    // Method to load notes from the file
    private void loadNotesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOTES_FILE))) {
            dayNotesMap = (Map<LocalDate, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing notes found or error reading notes: " + e.getMessage());
        }
    }

    // Method to save notes to the file
    private void saveNotesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOTES_FILE))) {
            oos.writeObject(dayNotesMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarView::new);
    }
}
