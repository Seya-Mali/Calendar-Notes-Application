# Monthly Calendar App

This Java-based application provides a simple graphical interface to display a calendar for any given year and month. It allows users to view the days of the month, navigate between months, and add notes to specific days, which are saved in a text file for persistence.

## Features:
- **View Calendar by Month and Year**: Displays a calendar for the selected year and month, showing the days of the week and day numbers.
- **Add Notes to Specific Days**: Users can add notes to any given day by clicking on the day and opening a note editor.
- **Save and Load Notes**: Notes are saved to a `.txt` file, allowing them to persist across sessions.

## Key Components:
- **Month Navigation**: Buttons for navigating to the previous and next months, along with a dropdown to select the month.
- **Notes Window**: Each day has an associated note editor where users can view, edit, and save notes.
- **Persistent Notes Storage**: Notes are saved in a text file (`notes.txt`), making them available every time the application is opened.

## Technologies Used:
- **Java Swing**: For building the graphical user interface.
- **Java I/O**: To load and save notes from/to a file.

## How to Use:
1. **Run the Application**: Once compiled, run the JAR file.
2. **Select a Month**: Use the dropdown or navigate to the desired month using the previous and next buttons.
3. **Add Notes**: Click on any day to open the note editor for that day. Enter your notes and click "Save" to store them.
4. **Persist Notes**: All notes are saved in `notes.txt` and will be reloaded the next time you run the application.

## How to Run:
1. Compile the code using `javac MonthlyCalendarView.java`.
2. Package the code into a JAR using the `jar` command:
   ```bash
   jar cfm MonthlyCalendarView.jar Manifest.txt -C classes/ .
