# Note-Taking Application

A modern, feature-rich note-taking application built with Java and JavaFX, implementing various design patterns and following clean architecture principles.

## Features

- **User Authentication**
  - Secure login and registration
  - Password validation and security
  - Session management

- **Note States**
  - Draft: Initial state for new notes
  - Published: Publicly available notes
  - Archived: Archived notes for future reference
  - State transitions with proper validation

- **Premium Features**
  - Premium subscription support
  - Enhanced note management
  - Additional formatting options

- **Modern UI/UX**
  - Material Design using JFoenix
  - Responsive layout
  - Intuitive navigation
  - Real-time validation
  - Smooth transitions

## Design Patterns Implemented

### 1. Singleton Pattern
**Purpose**: Ensures a class has only one instance and provides global access point.
**Implementation**: `UserSession`
```java
public class UserSession {
    private static UserSession instance;
    private String username;
    private boolean isPremium;

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
    .setCreatedAt(System.currentTimeMillis())
    .build();
```
**Benefits**:
- Flexible object construction
- Parameter validation
- Immutable objects
- Clear separation of construction and representation

### 3. State Pattern
**Purpose**: Allows an object to alter its behavior when its internal state changes.
**Implementation**: `NoteState` interface with concrete states
```java
public interface NoteState {
    void draft(Note note);
    void publish(Note note);
    void archive(Note note);
    String getStateName();
}
```
**Benefits**:
- Encapsulated state-specific behavior
- Simplified state transitions
- Easy to add new states

### 4. Strategy Pattern
**Purpose**: Defines a family of algorithms and makes them interchangeable.
**Implementation**: `NoteViewStrategy`
```java
public interface NoteViewStrategy {
    Node createNoteNode(Note note, EventHandler<ActionEvent> onDelete);
}
```
**Concrete Strategies**:
- `ListViewStrategy`: Displays notes in a vertical list
- `GridViewStrategy`: Displays notes in a grid layout

### 5. Facade Pattern
**Purpose**: Provides a simplified interface to a complex subsystem.
**Implementation**: `NoteFacade`
```java
public class NoteFacade {
    public Note createNote(String title, String content, String category) {
        // Simplified note creation process
    }
}
```
**Benefits**:
- Simplified interface
- Reduced dependencies
- Improved maintainability

### 6. Proxy Pattern
**Purpose**: Provides a surrogate or placeholder for another object.
**Implementation**: `NoteProxy`
```java
public class NoteProxy {
    private Note realNote;
    // Lazy loading of note content
}
```
**Benefits**:
- Lazy loading
- Access control
- Resource management

### 7. Decorator Pattern
**Purpose**: Attaches additional responsibilities to objects dynamically.
**Implementation**: `NoteDecorator`
```java
public abstract class NoteDecorator {
    protected Note note;
    public abstract String getFormattedContent();
}
```
**Benefits**:
- Flexible extension of functionality
- Single responsibility principle
- Open/closed principle

## Project Structure
```
src/main/java/com/notetaking/
├── controllers/         # JavaFX controllers
├── models/             # Domain models
├── patterns/           # Design pattern implementations
│   ├── builder/       # Builder pattern
│   ├── command/       # Command pattern
│   ├── decorator/     # Decorator pattern
│   ├── facade/        # Facade pattern
│   ├── proxy/         # Proxy pattern
│   ├── singleton/     # Singleton pattern
│   ├── state/         # State pattern
│   └── strategy/      # Strategy pattern
└── utils/             # Utility classes
```

