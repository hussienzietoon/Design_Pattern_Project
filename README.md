# Note-Taking Application

A modern, feature-rich note-taking application built with Java and JavaFX, implementing various design patterns and following clean architecture principles.

## Features

- User Authentication
- Note Management (Create, Read, Delete)
- Note Templates
- Markdown Formatting
- Note States (Draft, Published, Archived)
- Premium Subscription Support
- Modern Material Design UI using JFoenix
- Responsive Layout

## Design Patterns Used

### 1. Singleton Pattern

**Purpose**: Ensures a class has only one instance and provides global access point.
**Implementation**: `UserSession`

```java
public class UserSession {
    private static UserSession instance;
    private String username;

    private UserSession() {} // Private constructor

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
}
```

**Benefits**:

- Single source of truth for user state
- Controlled access to shared resources
- Memory efficient

### 2. Builder Pattern

**Purpose**: Constructs complex objects step by step.
**Implementation**: `Note` with `NoteBuilder`

```java
Note note = new Note.NoteBuilder()
    .setTitle("Meeting Notes")
    .setContent("Discussion points...")
    .setCategory("Work")
    .setCreatedAt(System.currentTimeMillis())
    .build();
```

**Benefits**:

- Flexible object construction
- Parameter validation
- Immutable objects
- Clear separation of construction and representation

### 3. Strategy Pattern

**Purpose**: Defines a family of algorithms and makes them interchangeable.
**Implementation**: `NoteViewStrategy`

```java
public interface NoteViewStrategy {
    Node createNoteNode(Note note, EventHandler<ActionEvent> onDelete);
}
```

**Concrete Strategies**:

- `ListViewStrategy`: Displays notes in a vertical list
- Easily extendable for new view types
  **Benefits**:
- Runtime behavior switching
- Encapsulated algorithms
- Eliminates complex conditionals

### 4. Prototype Pattern

**Purpose**: Creates new objects by cloning an existing instance.
**Implementation**: `NoteTemplate`

```java
NoteTemplate meetingTemplate = new NoteTemplate(
    "Meeting",
    "Meeting Notes",
    "## Agenda\n## Participants\n## Action Items",
    "Work"
);
Note newNote = meetingTemplate.clone().createNote();
```

**Benefits**:

- Efficient object creation
- Predefined templates
- Reduced subclassing

### 5. Decorator Pattern

**Purpose**: Adds new behaviors to objects dynamically.
**Implementation**: `NoteDecorator` and `MarkdownDecorator`

```java
public class MarkdownDecorator extends NoteDecorator {
    @Override
    public String getFormattedContent() {
        String content = note.getContent();
        content = content.replaceAll("\\*\\*(.+?)\\*\\*", "<b>$1</b>");
        return content;
    }
}
```

**Benefits**:

- Dynamic formatting
- Open for extension
- Single Responsibility Principle

### 6. Proxy Pattern

**Purpose**: Controls access to objects.
**Implementation**: `NoteProxy`

```java
public class NoteProxy {
    private Note realNote;
    private final long noteId;

    public Note getFullNote() {
        if (realNote == null) {
            realNote = loadNoteFromStorage();
        }
        return realNote;
    }
}
```

**Benefits**:

- Lazy loading
- Access control
- Memory optimization

### 7. Facade Pattern

**Purpose**: Provides unified interface to a set of interfaces.
**Implementation**: `NoteFacade`

```java
public class NoteFacade {
    public Note createNote(String title, String content) {
        Note note = new NoteBuilder().setTitle(title).build();
        decorator.format(note);
        storage.save(note);
        return note;
    }
}
```

**Benefits**:

- Simplified client interface
- Decoupled subsystems
- Reduced dependencies

### 8. Command Pattern

**Purpose**: Encapsulates a request as an object.
**Implementation**: `NoteCommand` and `CreateNoteCommand`

```java
public interface NoteCommand {
    void execute();
    void undo();
}
```

**Benefits**:

- Undo/Redo support
- Queue operations
- Decoupled sender and receiver

### 9. State Pattern

**Purpose**: Allows an object to alter its behavior when its internal state changes.
**Implementation**: `NoteState` with concrete states

```java
public interface NoteState {
    void draft(Note note);
    void publish(Note note);
    void archive(Note note);
}
```

**States**:

- `DraftState`: Initial state for new notes
- `PublishedState`: Notes visible to others
- `ArchivedState`: Notes in archive
  **Benefits**:
- Clean state transitions
- Encapsulated state behavior
- Easy to add new states

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Dependencies

- JavaFX 17.0.2
- JFoenix 9.0.10

## Installation

1. Clone the repository:

```bash
git clone <repository-url>
```

2. Navigate to the project directory:

```bash
cd note-taking-app
```

3. Build the project:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn javafx:run
```

## Project Structure

```
src/main/java/com/notetaking/
├── controllers/
│   ├── DashboardController.java
│   ├── LoginController.java
│   ├── RegistrationController.java
│   └── PremiumController.java
├── models/
│   └── Note.java
├── patterns/
│   ├── singleton/
│   │   └── UserSession.java
│   ├── strategy/
│   │   ├── NoteViewStrategy.java
│   │   └── ListViewStrategy.java
│   ├── prototype/
│   │   └── NoteTemplate.java
│   ├── decorator/
│   │   ├── NoteDecorator.java
│   │   └── MarkdownDecorator.java
│   ├── proxy/
│   │   └── NoteProxy.java
│   ├── facade/
│   │   └── NoteFacade.java
│   ├── command/
│   │   ├── NoteCommand.java
│   │   └── CreateNoteCommand.java
│   └── state/
│       ├── NoteState.java
│       ├── DraftState.java
│       ├── PublishedState.java
│       └── ArchivedState.java
└── Main.java
```

## Usage

1. **Login/Registration**

   - Launch the application
   - Create a new account or login with existing credentials

2. **Creating Notes**

   - Click "New Note" button
   - Enter title and content
   - Click "Save"
   - Optionally use templates for predefined structures

3. **Managing Notes**

   - View all notes in list format
   - Delete notes using the delete button
   - Notes are automatically sorted by creation date
   - Use markdown formatting in note content
   - Manage note states (Draft, Published, Archived)

4. **Using Templates**

   - Choose from predefined templates
   - Meeting notes template
   - Todo list template
   - Customize template content

5. **Premium Features**
   - Access additional features through premium subscription
   - Multiple payment options available

## Design Pattern Benefits

1. **Maintainability**

   - Clear separation of concerns
   - Modular design
   - Easy to extend

2. **Flexibility**

   - Multiple view options
   - Customizable templates
   - Extensible formatting

3. **Performance**
   - Lazy loading of content
   - Efficient state management
   - Optimized memory usage

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- JavaFX for the UI framework
- JFoenix for Material Design components
- Maven for dependency management
